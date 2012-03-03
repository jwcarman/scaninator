package com.carmanconsulting.scaninator.model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class AnnotationNode extends TypeHierarchyNode
{
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final Set<ClassNode> annotatedClasses = new TreeSet<ClassNode>();
    private final Set<InterfaceNode> annotatedInterfaces = new TreeSet<InterfaceNode>();
    private final Set<AnnotationNode> annotatedAnnotations = new TreeSet<AnnotationNode>();

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public AnnotationNode(String typeName)
    {
        super(typeName);
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public AnnotationNode addAnnotatedAnnotation(AnnotationNode annotatedAnnotation)
    {
        annotatedAnnotations.add(annotatedAnnotation);
        return this;
    }

    public AnnotationNode addAnnotatedClass(ClassNode annotatedClass)
    {
        annotatedClasses.add(annotatedClass);
        return this;
    }

    public AnnotationNode addAnnotatedInterface(InterfaceNode annotatedInterface)
    {
        annotatedInterfaces.add(annotatedInterface);
        return this;
    }

    public Set<AnnotationNode> getAnnotatedAnnotations()
    {
        return Collections.unmodifiableSet(annotatedAnnotations);
    }

    public Set<ClassNode> getAnnotatedClasses()
    {
        return Collections.unmodifiableSet(annotatedClasses);
    }

    public Set<InterfaceNode> getAnnotatedInterfacees()
    {
        return Collections.unmodifiableSet(annotatedInterfaces);
    }
}
