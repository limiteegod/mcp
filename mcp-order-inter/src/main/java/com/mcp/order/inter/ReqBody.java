/**
 * 
 */
package com.mcp.order.inter;

import com.mcp.order.exception.CoreException;


/**
 * @author ming.li
 *
 */
public abstract class ReqBody implements Body {
	
	private int flag = -1;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * 唯一id
     */
    private String uniqueId = null;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public abstract void validate() throws CoreException;
}
