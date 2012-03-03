package com.carmanconsulting.scaninator.model;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestInterfaceNode
{
    @Test
    public void testSubinterfaces()
    {
        InterfaceNode parent = new InterfaceNode("A");
        InterfaceNode child = new InterfaceNode("B");

        parent.addSubinterface(child);

        assertEquals(parent.getSubinterfaces().size(), 1);
        assertSame(parent.getSubinterfaces().iterator().next(), child);
    }

    @Test
    public void testImplementers()
    {
        InterfaceNode parent = new InterfaceNode("A");
        ClassNode child = new ClassNode("B");

        parent.addImplementer(child);

        assertEquals(parent.getImplementers().size(), 1);
        assertSame(parent.getImplementers().iterator().next(), child);
    }
}
