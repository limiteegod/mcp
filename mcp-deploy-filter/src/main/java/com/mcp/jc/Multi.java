/**
 * 
 */
package com.mcp.jc;

/**
 * @author ming.li
 *
 */
public class Multi {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double a = 1.30;
		double b = 1.40;
		
		long t = 200;
		
		double m = a*b;
		System.out.println(t*m);
		t = (long)(t*m);
		
		System.out.println(m);
		System.out.println(t);
	}

}
