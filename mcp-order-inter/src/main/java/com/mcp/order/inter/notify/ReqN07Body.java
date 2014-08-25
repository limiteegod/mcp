/**
 * 
 */
package com.mcp.order.inter.notify;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.model.jc.JOdds;

import java.util.List;


/**
 * @author ming.li
 *
 */
public class ReqN07Body extends ReqBody {
	
	private List<JOdds> oddsList;
	
	public List<JOdds> getOddsList() {
		return oddsList;
	}

	public void setOddsList(List<JOdds> oddsList) {
		this.oddsList = oddsList;
	}

	@Override
	public void validate() throws CoreException {
		
	}
}
