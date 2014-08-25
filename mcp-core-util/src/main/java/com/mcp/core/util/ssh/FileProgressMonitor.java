package com.mcp.core.util.ssh;

import com.jcraft.jsch.SftpProgressMonitor;
import org.apache.log4j.Logger;

public class FileProgressMonitor implements SftpProgressMonitor {
	
	private static final Logger log = Logger.getLogger(FileProgressMonitor.class);
	
    private long transfered;
    
    public FileProgressMonitor()
    {
    }
    
    public boolean count(long count) {
        transfered = transfered + count;
        log.info("Currently transferred total size: " + transfered + " bytes");
        return true;
    }

    public void end() {
    	log.info("Transferring done.");
    }

    public void init(int op, String src, String dest, long max) {
    	log.info("Transferring begin.");
    }
}