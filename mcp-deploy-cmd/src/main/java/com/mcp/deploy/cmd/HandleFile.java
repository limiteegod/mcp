package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.ResourceUtil;
import com.mcp.core.util.StringUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;

/**
 * Created by ming.li on 2014/6/24.
 */
public class HandleFile {

    public static void main(String[] args) throws Exception
    {
        String fileName = "target.txt";
        URL url = ResourceUtil.getURL(fileName);
        FileReader fileReader = new FileReader(url.getPath());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        String ids = "(";
        int count = 0;
        while((line = bufferedReader.readLine()) != null)
        {
            if(!StringUtil.isEmpty(line))
            {
                String sql = "update tticket set status=1100 where id='" + line.substring(0, 32) + "';";
                System.out.println(sql);
            }
        }
        bufferedReader.close();
        //generateMdb();

        //undoMore();
    }

    public static void generate() throws Exception
    {
        String fileName = "target.txt";
        URL url = ResourceUtil.getURL(fileName);
        FileReader fileReader = new FileReader(url.getPath());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        int count = 1;
        while((line = bufferedReader.readLine()) != null)
        {
            String tId = line.substring(0, 32);
            String tCode = line.substring(33);
            String sql = "db.term_success_ticket_T51.save({ \"_id\" : NumberLong(" + count + "), \"ticketId\" : \"" + tId + "\", \"termCode\" : \"" + tCode + "\", \"finishedCount\" : 0 });";
            System.out.println(sql);
            count++;
        }
        bufferedReader.close();
    }

    public static void generateMdb() throws Exception
    {
        String fileName = "target.txt";
        URL url = ResourceUtil.getURL(fileName);
        FileReader fileReader = new FileReader(url.getPath());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        int count = 1;
        while((line = bufferedReader.readLine()) != null)
        {
            String tId = line.substring(0, 32);
            String tCode = line.substring(33);
            String sql = "db.term_success_ticket_T51.save({ \"_id\" : NumberLong(" + count + "), \"ticketId\" : \"" + tId + "\", \"termCode\" : \"" + tCode + "\", \"finishedCount\" : 0 });";
            System.out.println(sql);
            count++;
        }
        bufferedReader.close();
    }

    public static void undoMore() throws Exception
    {
        String fileName = "ex_more.txt";
        URL url = ResourceUtil.getURL(fileName);
        FileReader fileReader = new FileReader(url.getPath());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        int count = 1;
        String ids = "(";
        while((line = bufferedReader.readLine()) != null)
        {
            if(count%2 == 1)
            {
                String tId = line.substring(82);
                String sql = "update tticket set bonus=0,bonusbeforetax=0,finishedcount=finishedcount-1,status=1300 where id='" + tId + "';";
                System.out.println(sql);

                if(count > 1)
                {
                    ids += "," + "'" + tId + "'";
                }
                else
                {
                    ids +=  "'" + tId + "'";
                }
            }
            count++;
        }
        ids += ")";
        String sql = "select id,termcode from tticket where id in " + ids + ";";
        System.out.println(sql);
        bufferedReader.close();
    }
}
