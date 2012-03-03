package com.carmanconsulting.scaninator.model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class ClassNode extends TypeHierarchyNode
{
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final Set<ClassNode> subclasses = new TreeSet<ClassNode>();

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    ClassNode(String name)
    {
        super(name);
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public ClassNode addSubclass(ClassNode subclassNode)
    {
        subclasses.add(subclassNode);
        return this;
    }

    public Set<ClassNode> getSubclasses()
    {
        return Collections.unmodifiableSet(subclasses);
    }
}
