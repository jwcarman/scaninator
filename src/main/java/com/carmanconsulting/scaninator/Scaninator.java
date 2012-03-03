package com.carmanconsulting.scaninator;

import com.carmanconsulting.scaninator.listener.TypeHierarchyListener;
import com.carmanconsulting.scaninator.model.*;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;

import java.io.IOException;
import java.lang.annotation.RetentionPolicy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Scaninator
{
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    public static final String CLASS_FILE_SUFFIX = ".class";
    private final List<ScaninatorListener> listeners = new CopyOnWriteArrayList<ScaninatorListener>();
    private final Set<String> ignoredPackages = new TreeSet<String>();

//----------------------------------------------------------------------------------------------------------------------
// Static Methods
//----------------------------------------------------------------------------------------------------------------------

    private static String relativePath(FileObject parent, FileObject child)
    {
        String parentPath = parent.getName().getPath();
        String childPath = child.getName().getPath();
        return childPath.startsWith(parentPath) ? childPath.substring(parentPath.length()) : null;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public Scaninator addListener(ScaninatorListener listener)
    {
        this.listeners.add(listener);
        return this;
    }

    public Scaninator ignore(String... packageNames)
    {
        ignoredPackages.addAll(Arrays.asList(packageNames));
        return this;
    }

    private void scanFile(FileObject root) throws IOException
    {
        Queue<FileObject> queue = new LinkedList<FileObject>();

        queue.add(root);

        while (!queue.isEmpty())
        {
            FileObject file = queue.remove();
            if (file.getType().hasChildren())
            {
                final String packageName = relativePath(root, file).replaceAll("/", ".");
                if (!ignoredPackages.contains(packageName))
                {
                    queue.addAll(Arrays.asList(file.getChildren()));
                }
            }
            else if (file.getName().getBaseName().endsWith(CLASS_FILE_SUFFIX))
            {
                String className = relativePath(root, file).replaceAll("/", ".").replace(CLASS_FILE_SUFFIX, "");
                if(className.startsWith("."))
                {
                    className = className.substring(1);
                }
                for (ScaninatorListener listener : listeners)
                {
                    listener.classFileFound(className, file);
                }
            }
        }
    }

//----------------------------------------------------------------------------------------------------------------------
// main() method
//----------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final Scaninator scaninator = new Scaninator();
        final TypeHierarchyListener listener = new TypeHierarchyListener();
        scaninator.addListener(listener);
        scaninator.ignore("javax", "java", "com.sun", "com.intellij", "sun", "javassist", "com.oracle", "hidden", "org.codehaus", "org.omg");
        if (classLoader instanceof URLClassLoader)
        {
            URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
            for (URL url : urlClassLoader.getURLs())
            {
                FileObject file = null;
                if (url.getPath().endsWith(".jar"))
                {
                    file = VFS.getManager().resolveFile(url.toExternalForm().replace("file:/", "jar:/"));
                }
                else
                {
                    file = VFS.getManager().resolveFile(url.toExternalForm());
                }
                scaninator.scanFile(file);
            }
        }
        AnnotationNode node = listener.getTypeHierarchy().getAnnotationNode(RetentionPolicy.class.getName());
    }
}
