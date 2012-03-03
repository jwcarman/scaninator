package com.carmanconsulting.scaninator;

import org.apache.commons.vfs2.FileObject;

public interface ScaninatorListener
{
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    void classFileFound(String className, FileObject classFile);
}
