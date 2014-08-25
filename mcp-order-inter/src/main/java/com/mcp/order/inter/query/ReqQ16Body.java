package com.mcp.order.inter.query;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

public class ReqQ16Body extends ReqBody {

    private String gameCode;

    private String termCode;

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    /* (non-Javadoc)
             * @see com.mcp.core.inter.define.Body#validate()
             */
    @Override
    public void validate() throws CoreException {

    }
}
