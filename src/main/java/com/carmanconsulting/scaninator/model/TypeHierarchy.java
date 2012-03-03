package com.carmanconsulting.scaninator.model;

import java.util.*;

public class TypeHierarchy
{
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final Map<String, ClassNode> classNodes = new TreeMap<String, ClassNode>();
    private final Map<String, InterfaceNode> interfaceNodes = new TreeMap<String, InterfaceNode>();
    private final Map<String,AnnotationNode> annotationNodes = new TreeMap<String, AnnotationNode>();
    private final Map<String,PackageNode> packageNodes = new TreeMap<String, PackageNode>();

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public AnnotationNode getAnnotationNode(String annotationName)
    {
        return annotationNodes.get(annotationName);
    }
    
    public ClassNode getClassNode(String className)
    {
        return classNodes.get(className);
    }

    public InterfaceNode getInterfaceNode(String interfaceName)
    {
        return interfaceNodes.get(interfaceName);
    }
    
    public AnnotationNode getOrCreateAnnotationNode(String annotationName)
    {
        AnnotationNode annotationNode = annotationNodes.get(annotationName);
        if(annotationNode == null)
        {
            annotationNode = new AnnotationNode(annotationName);
            annotationNodes.put(annotationName, annotationNode);
        }
        return annotationNode;
    }

    public ClassNode getOrCreateClassNode(String className)
    {
        ClassNode classNode = classNodes.get(className);
        if (classNode == null)
        {
            classNode = new ClassNode(className);
            classNodes.put(className, classNode);
        }
        return classNode;
    }

    public InterfaceNode getOrCreateInterfaceNode(String interfaceName)
    {
        InterfaceNode interfaceNode = interfaceNodes.get(interfaceName);
        if (interfaceNode == null)
        {
            interfaceNode = new InterfaceNode(interfaceName);
            interfaceNodes.put(interfaceName, interfaceNode);
        }
        return interfaceNode;
    }
    
    public PackageNode getOrCreatePackageNode(String packageName)
    {
        PackageNode packageNode = getPackageNode(packageName);
        if(packageNode==null)
        {
            packageNode = new PackageNode(packageName);
            packageNodes.put(packageName, packageNode);
        }
        return packageNode;
    }
    
    public PackageNode getPackageNode(String packageName)
    {
        return packageNodes.get(packageName);
    }
}
