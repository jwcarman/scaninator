package com.carmanconsulting.scaninator.model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class InterfaceNode extends TypeHierarchyNode
{
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final Set<ClassNode> implementers = new TreeSet<ClassNode>();
    private final Set<InterfaceNode> subinterfaces = new TreeSet<InterfaceNode>();

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    InterfaceNode(String name)
    {
        super(name);
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public InterfaceNode addImplementer(ClassNode implementorNode)
    {
        implementers.add(implementorNode);
        return this;
    }

    public InterfaceNode addSubinterface(InterfaceNode subinterfaceNode)
    {
        subinterfaces.add(subinterfaceNode);
        return this;
    }

    public Set<ClassNode> getImplementers()
    {
        return Collections.unmodifiableSet(implementers);
    }

    public Set<InterfaceNode> getSubinterfaces()
    {
        return Collections.unmodifiableSet(subinterfaces);
    }
}
