/**
 * 
 */
package com.mcp.jc.common;

import com.mcp.jc.validator.JcDefaultValidator;

public class AppMain {
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JcDefaultValidator v = new JcDefaultValidator();
		String number = "$01|201312172001|1@1.0,2&02|201312172001|1@1.0;21|201312172002|1@1.1,2;21|201312172003|1@1.2,3,0&22|201312172003|1@1.2,3;21|201312172005|1@1.3,2,3;21|201312172006|1@1.4,2,3;21|201312172006|1@1.5";
		//String number = "01|201312172001|1@1.0,2";
		
		v.validator(number, 5, 16);
	}
}	