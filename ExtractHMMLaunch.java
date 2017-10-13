package NYLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class ExtractHMMLaunch {
int ciNum = 44;
	public static void main(String []args) throws Exception
	{
		ExtractHMMLaunch r = new ExtractHMMLaunch();
		r.Extract();
	}
	
	public void Extract() throws IOException
	{
	    double number[] = new double[ciNum];
	    HashMap record[] = new HashMap[ciNum];
	    for(int i = 0; i < ciNum; i++)
	    {
	    	record[i] = new HashMap();
	    }
	    Poslist a = new Poslist();
		File f = new File("NYLP\\clear199801.txt");
		FileInputStream in = new FileInputStream(f);
		Long len = f.length();
		byte bb[] = new byte[len.intValue()];
		in.read(bb);
		String ss = new String(bb);
		String s[] = ss.split("\r\n");
		double num = 0;
		for(int i = 0; i < s.length; i++)//每句话
		{   
			System.out.println("一共："+s.length+"句话   当前第"+(i+1)+"句话");
			String temp [] = s[i].split(" ");
			for(int j = 0; j < temp.length ; j++)//每个词
			{   
			    if(temp[j].charAt(0) == '[')
			    {
			    	int start = 1;
			    	int end = 0;
			    	for(int l = 1; l < temp[j].length(); l++)
			    	{
			    		if(temp[j].charAt(l) == '/')
			    		{
			    			end = l;
			    			break;
			    		}
			    	}
			    	String word = temp[j].substring(start, end);
			    	String pos = temp[j].substring(end+1);
			    	int index = a.cixing_number(pos);
			    	number[index] = number[index]+1;
			    	if(record[index].get(word) == null)
			    	{
			    		record[index].put(word,(double)1);
			    	}
			    	else
			    	{
			    		record[index].put(word,(double)record[index].get(word)+1);
			    	}
			    	break;
			    }
			    else if(temp[j].charAt(temp[j].length()-2) == ']')
			    {
			    	for(int u = 0; u < temp[j].length()-2;u++)
			    	{
			    		if(temp[j].charAt(u) == '/')
			    		{
			    			String word = temp[j].substring(0, u);
			    			String pos = temp[j].substring(u+1,temp[j].length()-2);
			    			int index = a.cixing_number(pos);
					    	number[index] = number[index]+1;
					    	if(record[index].get(word) == null)
					    	{
					    		record[index].put(word,(double)1);
					    	}
					    	else
					    	{
					    		record[index].put(word,(double)record[index].get(word)+1);
					    	}
					    	break;
			    		}
			    	}
			    }
			    else if(temp[j].charAt(temp[j].length()-3) == ']')
			    {
			    	for(int u = 0; u < temp[j].length()-3;u++)
			    	{
			    		if(temp[j].charAt(u) == '/')
			    		{
			    			String word = temp[j].substring(0, u);
			    			String pos = temp[j].substring(u+1,temp[j].length()-3);
			    			int index = a.cixing_number(pos);
					    	number[index] = number[index]+1;
					    	if(record[index].get(word) == null)
					    	{
					    		record[index].put(word,(double)1);
					    	}
					    	else
					    	{
					    		record[index].put(word,(double)record[index].get(word)+1);
					    	}
					    	break;
			    		}
			    	}
			    }
			    else
			    {
			    	for(int u = 0; u < temp[j].length();u++)
			    	{
			    		if(temp[j].charAt(u) == '/')
			    		{
			    			String word = temp[j].substring(0, u);
			    			String pos = temp[j].substring(u+1);
			    			int index = a.cixing_number(pos);
					    	number[index] = number[index]+1;
					    	if(record[index].get(word) == null)
					    	{
					    		record[index].put(word,(double)1);
					    	}
					    	else
					    	{
					    		record[index].put(word,(double)record[index].get(word)+1);
					    	}
					    	break;
			    		}
			    	}
			    }
			}
		}
			
		File ff = new File("NYLP\\fashe.txt");
		FileOutputStream oo = new FileOutputStream(ff);
	    for(int i = 0 ; i < ciNum; i++)
	    {
	    	Iterator p = record[i].keySet().iterator();
	    	while(p.hasNext())
	    	{
	    		String word = (String)p.next();
	    		String pos = a.number_cixing(i);
	    		double fre = (double)record[i].get(word) / (double)number[i];
	    		String kk = pos + " " + word + " " + Double.toString(fre) + " \n";
	    		oo.write(kk.getBytes());
	    	}
	    	
	    }
	}
}
