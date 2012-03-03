package com.carmanconsulting.scaninator.model;

import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.*;

public class TestTypeHierarchy
{
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    public void testGerOrCreateClassNode()
    {
        TypeHierarchy typeHierarchy = new TypeHierarchy();
        ClassNode a = typeHierarchy.getClassNode("A");
        assertNull(a);
        a = typeHierarchy.getOrCreateClassNode("A");
        assertNotNull(a);
        assertEquals(a.getTypeName(), "A");
        a = typeHierarchy.getClassNode("A");
        assertNotNull(a);
        assertEquals(a.getTypeName(), "A");
    }

    @Test
    public void testGerOrCreateInterfaceNode()
    {
        TypeHierarchy typeHierarchy = new TypeHierarchy();
        InterfaceNode a = typeHierarchy.getInterfaceNode("A");
        assertNull(a);
        a = typeHierarchy.getOrCreateInterfaceNode("A");
        assertNotNull(a);
        assertEquals(a.getTypeName(), "A");
        a = typeHierarchy.getInterfaceNode("A");
        assertNotNull(a);
        assertEquals(a.getTypeName(), "A");
    }
}
