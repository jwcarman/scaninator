package com.carmanconsulting.scaninator.model;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

public class TestClassNode
{
    @Test
    public void testSubclasses()
    {
        ClassNode parent = new ClassNode("A");
        ClassNode child = new ClassNode("B");

        parent.addSubclass(child);

        assertEquals(parent.getSubclasses().size(), 1);
        assertSame(parent.getSubclasses().iterator().next(), child);
    }
}
