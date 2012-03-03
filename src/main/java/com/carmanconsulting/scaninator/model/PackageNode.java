package com.carmanconsulting.scaninator.model;

import java.util.Set;
import java.util.TreeSet;

public class PackageNode extends TypeHierarchyNode
{
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final Set<ClassNode> classMembers = new TreeSet<ClassNode>();
    private final Set<InterfaceNode> interfaceMembers = new TreeSet<InterfaceNode>();
    private final Set<AnnotationNode> annotationMembers = new TreeSet<AnnotationNode>();
    private final Set<PackageNode> subpackages = new TreeSet<PackageNode>();

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public PackageNode(String typeName)
    {
        super(typeName);
        if(typeName.startsWith("."))
        {
            System.out.println("Oops!");
        }
    }

    public PackageNode addSubpackage(PackageNode packageNode)
    {
        subpackages.add(packageNode);
        return this;
    }

    public PackageNode addClassMember(ClassNode classMember)
    {
        classMembers.add(classMember);
        return this;
    }

    public PackageNode addInterfaceMember(InterfaceNode interfaceMember)
    {
        interfaceMembers.add(interfaceMember);
        return this;
    }

    public PackageNode addAnnotationMember(AnnotationNode annotationMember)
    {
        annotationMembers.add(annotationMember);
        return this;
    }
    
    
}
