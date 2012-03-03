package com.carmanconsulting.scaninator.listener;

import com.carmanconsulting.scaninator.ScaninatorListener;
import com.carmanconsulting.scaninator.model.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;

public class TypeHierarchyListener implements ScaninatorListener
{
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final String DEFAULT_PACKAGE = "";

    private final TypeHierarchy typeHierarchy = new TypeHierarchy();

//----------------------------------------------------------------------------------------------------------------------
// ScaninatorListener Implementation
//----------------------------------------------------------------------------------------------------------------------


    @Override
    public void classFileFound(String className, FileObject file)
    {
        try
        {
            ClassFile classFile = new ClassFile(new DataInputStream(file.getContent().getInputStream()));
            final int ndx = className.lastIndexOf('.');
            final String packageName = ndx == -1 ? DEFAULT_PACKAGE : className.substring(0, ndx);
            if (!DEFAULT_PACKAGE.equals(packageName))
            {
                addPackages(packageName);
            }
            if (classFile.isInterface())
            {
                visitInterface(packageName, className, classFile);
            }
            else if (Annotation.class.getName().equals(classFile.getSuperclass()))
            {
                visitAnnotation(packageName, className, classFile);
            }
            else
            {
                visitClass(packageName, className, classFile);
            }
        }
        catch (IOException e)
        {
        }
    }

//----------------------------------------------------------------------------------------------------------------------
// Getter/Setter Methods
//----------------------------------------------------------------------------------------------------------------------

    public TypeHierarchy getTypeHierarchy()
    {
        return typeHierarchy;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    private void addPackages(String packageName)
    {
        PackageNode packageNode = typeHierarchy.getPackageNode(packageName);
        if (packageNode == null)
        {
            String[] packageComponents = packageName.split("\\.");
            StringBuilder parentPackage = new StringBuilder(DEFAULT_PACKAGE);
            for (String packageComponent : packageComponents)
            {
                PackageNode parent = typeHierarchy.getOrCreatePackageNode(parentPackage.toString());
                if (!DEFAULT_PACKAGE.equals(parentPackage.toString()))
                {
                    parentPackage.append('.');
                }
                parentPackage.append(packageComponent);
                parent.addSubpackage(typeHierarchy.getOrCreatePackageNode(parentPackage.toString()));
            }
        }
    }

    private void visitAnnotation(String packageName, String className, ClassFile classFile)
    {
        final AnnotationNode thisNode = typeHierarchy.getOrCreateAnnotationNode(className);
        addAnnotatedAnnotations(thisNode, (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag));
        addAnnotatedAnnotations(thisNode, (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.invisibleTag));
        typeHierarchy.getOrCreatePackageNode(packageName).addAnnotationMember(thisNode);
    }

    private void addAnnotatedAnnotations(AnnotationNode thisNode, AnnotationsAttribute attribute)
    {
        javassist.bytecode.annotation.Annotation[] annotations = getAnnotations(attribute);
        if (annotations != null)
        {
            for (javassist.bytecode.annotation.Annotation annotation : annotations)
            {
                typeHierarchy.getOrCreateAnnotationNode(annotation.getTypeName()).addAnnotatedAnnotation(thisNode);
            }
        }
    }

    private void visitClass(String packageName, String className, ClassFile classFile)
    {
        ClassNode thisNode = typeHierarchy.getOrCreateClassNode(className);
        String[] interfaceNames = classFile.getInterfaces();
        for (String interfaceName : interfaceNames)
        {
            typeHierarchy.getOrCreateInterfaceNode(interfaceName).addImplementer(thisNode);
        }
        final String superClassName = classFile.getSuperclass();
        if(superClassName != null)
        {
            typeHierarchy.getOrCreateClassNode(superClassName).addSubclass(thisNode);
        }
        addAnnotatedClasses(thisNode, (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag));
        addAnnotatedClasses(thisNode, (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.invisibleTag));
        typeHierarchy.getOrCreatePackageNode(packageName).addClassMember(thisNode);
    }

    private void addAnnotatedClasses(ClassNode thisNode, AnnotationsAttribute attribute)
    {
        javassist.bytecode.annotation.Annotation[] annotations = getAnnotations(attribute);
        if (annotations != null)
        {
            for (javassist.bytecode.annotation.Annotation annotation : annotations)
            {
                typeHierarchy.getOrCreateAnnotationNode(annotation.getTypeName()).addAnnotatedClass(thisNode);
            }
        }
    }

    private void visitInterface(String packageName, String className, ClassFile classFile)
    {
        InterfaceNode thisNode = typeHierarchy.getOrCreateInterfaceNode(className);
        String[] interfaceNames = classFile.getInterfaces();
        for (String interfaceName : interfaceNames)
        {
            typeHierarchy.getOrCreateInterfaceNode(interfaceName).addSubinterface(thisNode);
        }
        addAnnotatedInterfaces(thisNode, (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag));
        addAnnotatedInterfaces(thisNode, (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.invisibleTag));
        typeHierarchy.getOrCreatePackageNode(packageName).addInterfaceMember(thisNode);
    }

    private void addAnnotatedInterfaces(InterfaceNode thisNode, AnnotationsAttribute attribute)
    {
        javassist.bytecode.annotation.Annotation[] annotations = getAnnotations(attribute);
        if (annotations != null)
        {
            for (javassist.bytecode.annotation.Annotation annotation : annotations)
            {
                typeHierarchy.getOrCreateAnnotationNode(annotation.getTypeName()).addAnnotatedInterface(thisNode);
            }
        }
    }

    private static javassist.bytecode.annotation.Annotation[] getAnnotations(AnnotationsAttribute attribute)
    {
        return attribute == null ? null : attribute.getAnnotations();
    }
}
