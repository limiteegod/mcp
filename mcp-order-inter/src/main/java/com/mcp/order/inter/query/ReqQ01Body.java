package com.mcp.order.inter.query;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

import java.util.List;

public class ReqQ01Body extends ReqBody {
	
	private int type;
    private String gameCode;
    private int startIndex;
    private int size;
    private List<ReqQ01Term> terms;
    
    private boolean showGrade = false;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ReqQ01Term> getTerms() {
        return terms;
    }

    public void setTerms(List<ReqQ01Term> terms) {
        this.terms = terms;
    }

    public boolean isShowGrade() {
		return showGrade;
	}

	public void setShowGrade(boolean showGrade) {
		this.showGrade = showGrade;
	}

	/* (non-Javadoc)
         * @see com.mcp.core.inter.define.Body#validate()
         */
    @Override
    public void validate() throws CoreException {

    }
}
