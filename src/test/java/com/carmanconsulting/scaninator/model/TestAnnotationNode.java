package com.carmanconsulting.scaninator.model;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class TestAnnotationNode
{
    @Test
    public void testAnnotatedClasses()
    {
        AnnotationNode parent = new AnnotationNode("A");
        ClassNode annotated = new ClassNode("B");
        parent.addAnnotatedClass(annotated);
        
        assertEquals(parent.getAnnotatedClasses().size(), 1);
        assertSame(parent.getAnnotatedClasses().iterator().next(), annotated);
    }

    @Test
    public void testAnnotatedInterfaces()
    {
        AnnotationNode parent = new AnnotationNode("A");
        InterfaceNode annotated = new InterfaceNode("B");
        parent.addAnnotatedInterface(annotated);

        assertEquals(parent.getAnnotatedInterfacees().size(), 1);
        assertSame(parent.getAnnotatedInterfacees().iterator().next(), annotated);
    }

    @Test
    public void testAnnotatedAnnotations()
    {
        AnnotationNode parent = new AnnotationNode("A");
        AnnotationNode annotated = new AnnotationNode("B");
        parent.addAnnotatedAnnotation(annotated);

        assertEquals(parent.getAnnotatedAnnotations().size(), 1);
        assertSame(parent.getAnnotatedAnnotations().iterator().next(), annotated);
    }
}
