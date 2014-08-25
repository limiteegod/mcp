package com.mcp.order.inter.scheme;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;
import com.mcp.scheme.model.SchemeHm;
import org.apache.log4j.Logger;

public class ReqS04Body extends ReqBody {
	
	private static Logger log = Logger.getLogger(ReqS04Body.class);
	
	private SchemeHm scheme;

	public SchemeHm getScheme() {
		return scheme;
	}

	public void setScheme(SchemeHm scheme) {
		this.scheme = scheme;
	}

	@Override
	public void validate() throws CoreException {

	}
}
