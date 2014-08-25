package com.mcp.order.inter.query;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

public class ReqQ17Body extends ReqBody {

    private int size = 10;

    private int startIndex = 0;

    private long minId = 0;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public long getMinId() {
        return minId;
    }

    public void setMinId(long minId) {
        this.minId = minId;
    }

    /* (non-Javadoc)
                 * @see com.mcp.core.inter.define.Body#validate()
                 */
    @Override
    public void validate() throws CoreException {

    }
}
