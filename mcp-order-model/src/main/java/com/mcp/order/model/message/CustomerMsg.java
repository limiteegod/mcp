/**
 * 
 */
package com.mcp.order.model.message;

import com.mcp.order.util.JsonDateSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;


/**
 * @author ming.li
 *
 */
public class CustomerMsg implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8489868958609360287L;

	private String content;
	
	private Date time;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
