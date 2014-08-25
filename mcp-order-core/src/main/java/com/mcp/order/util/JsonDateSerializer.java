package com.mcp.order.util;

import com.mcp.order.common.Constants;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateSerializer extends JsonSerializer<Date> {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);

	@Override
	public void serialize(Date date, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		 String formattedDate = dateFormat.format(date);
		 jgen.writeString(formattedDate);
	}

}
