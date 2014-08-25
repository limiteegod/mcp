/**
 * 
 */
package com.mcp.core.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ming.li
 *
 */
public class FileUtil {
	
	/**
	 * 把字符串追加到文件的末尾
	 * @param file
	 * @param content
	 */
	public static void appendContent(File file, String content) throws IOException
	{
		String oldContent = "";
		if(file.exists())
		{
			oldContent = getContent(file);
		}
		String newContent = oldContent + content;
		writeContent(file, newContent);
	}
	
	/**
	 * 把字符串写入文件
	 * @param file
	 * @param content
	 */
	public static void writeContent(File file, String content) throws IOException
	{
		if (file.getParent() != null) {
			new File(file.getParent()).mkdirs();
		}
		
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(content.getBytes(Charset.forName("UTF-8")));
		fos.close();
	}
	
	/**
	 * 读取文件的所有内容
	 * @param file
	 */
	public static String getContent(File file) throws IOException
	{
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int size = 0;
		StringBuffer sb = new StringBuffer();
		while( (size = fis.read(buffer)) > 0)
		{
			sb.append(new String(buffer, 0, size, "UTF-8"));
		}
		fis.close();
		return sb.toString();
	}
	
	/**
	 * 合并新文件，合并规则如下：
	 * fLine作为新文件的第一行，sLine作为新文件的第二行，fromFile中的内容再追加写在
	 * 新文件toFile的末尾
	 * @param fLine
	 * @param sLine
	 * @param fromFile
	 * @param toFile
	 */
	public static void gather(String fLine, String sLine, String fromFile, String toFile) throws IOException
	{
		FileWriter writer = new FileWriter(toFile);
		BufferedWriter bw = new BufferedWriter(writer);
		bw.write(fLine + "\r\n");
		bw.write(sLine + "\r\n");
		
		FileReader reader = new FileReader(fromFile);
		BufferedReader br = new BufferedReader(reader);
		String str = null;
		while((str = br.readLine()) != null) {
			bw.write(str + "\r\n");
		}
		br.close();
		reader.close();
		
		bw.close();
		writer.close();
	}
	
	/**
	 * 把fromFile中的内容追加到toFile中，按行模式追加，并返回追加的行数
	 * @param fromFile
	 * @param toFile
	 */
	public static int append(String fromFile, String toFile) throws IOException
	{
		int count = 0;
		FileWriter writer = new FileWriter(toFile, true);
		BufferedWriter bw = new BufferedWriter(writer);
		
		FileReader reader = new FileReader(fromFile);
		BufferedReader br = new BufferedReader(reader);
		String str = null;
		while((str = br.readLine()) != null) {
			bw.write(str + "\r\n");
			count++;
		}
		br.close();
		reader.close();
		
		bw.close();
		writer.close();
		return count;
	}
	
	/**
	 * 分解文件，来源文件的第一、第二行存储在list中返回，第三行及第三行以后的内容写在新的文件中
	 * @param fromFile
	 * @param toFile
	 * @return
	 * @throws Exception
	 */
	public static List<String> split(String fromFile, String toFile, boolean append) throws IOException
	{
		List<String> strList = new ArrayList<String>();
		int count = 0;
		FileWriter writer = new FileWriter(toFile, append);
		BufferedWriter bw = new BufferedWriter(writer);
		
		FileReader reader = new FileReader(fromFile);
		BufferedReader br = new BufferedReader(reader);
		String str = null;
		while((str = br.readLine()) != null) {
			if(count < 2)
			{
				strList.add(str);
			}
			else
			{
				bw.write(str + "\r\n");
			}
			count++;
		}
		br.close();
		reader.close();
		
		bw.close();
		writer.close();
		return strList;
	}
	
	/**
	 * fFile,sFile合成新文件tFile，合成规则：
	 * 新文件第一行为sFile的第一行
	 * 新文件的第二行为fFile的第一行
	 * 其余内容为sFile的内容
	 * @param fFile
	 * @param sFile
	 * @param tFile
	 * @throws Exception
	 */
	public static void gather(String fFile, String sFile, String tFile) throws IOException
	{
		String md5 = MD5Util.getFileMD5String(new File(sFile));
		FileReader reader = new FileReader(fFile);
		BufferedReader br = new BufferedReader(reader);
		String str = null;
		while((str = br.readLine()) != null) {
			break;
		}
		br.close();
		reader.close();
		gather(md5, str, sFile, tFile);
	}
}
