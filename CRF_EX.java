package NYLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CRF_EX {
	public static void main(String []args) throws IOException
	{
		CRF_EX a = new CRF_EX();
	}
public CRF_EX() throws IOException
{
	File file = new File("NYLP\\clear199801.txt");
	Long filelength = file.length();
	byte[] filecontent = new byte[filelength.intValue()]; 
	FileInputStream in = new FileInputStream(file);
	File tt = new File("D:\\CRF++\\chunk\\nontrain.data");
	FileOutputStream out=new FileOutputStream(tt);  
	in.read(filecontent);
	in.close();
	String t = new String(filecontent);
	String []s = t.split(" ");
	int num = 0;
	int start = 0;
	int end = 0;

	//System.out.println(s[14]);
	for(int i = 0; i < s.length; )
	{
		
		//System.out.println(s[i]);

		if(s[i].charAt(0) == '[') 
		{
			//System.out.println(s[i]);
			for(int m =  i + 1; m < s.length; m++)
			{
				
				if(s[m].charAt(s[m].length() - 3) == ']')
				{
					 //String chunk2 = s[m].substring(s[m].length() - 2);
					 //B开头的
					 s[i] = s[i].substring(1);
					 for(int o = 0; o < s[i].length(); o++)
					 {
						 if(s[i].charAt(o) == '/')
						 {
							 String name = s[i].substring(0, o);
							 //System.out.println(name);
							 String pos = s[i].substring(o + 1);
							 String chunk1 = "B";
							 String te = name + " " + pos + " " + chunk1+ "\r\n";
							 byte[] buff=new byte[]{};
							 buff = te.getBytes();
							 out.write(buff);
                             //i++;
                             break;
						 }
					 }
					 for(int n = i + 1; n < m; n++)  //中间部分
					 {
						 
						 for(int k = 0; k < s[n].length(); k++)
						 {
							 if(s[n].charAt(k) == '/')
							 {
								 String te = "";
								 String name = s[n].substring(0, k);
								 String pos = s[n].substring(k + 1);
								 String chunk1 = "I";
								 te = name + " " + pos + " " + chunk1+ "\r\n";
								 byte[] buff=new byte[]{};
								 buff = te.getBytes();
								 out.write(buff);
								 //i++;
								 break;
							 }
						 }
					 }
					 //处理结尾的词
					 s[m] = s[m].substring(0,s[m].length() - 3);
					 for(int o = 0; o < s[m].length(); o++)
					 {
						 if(s[m].charAt(o) == '/')
						 {
							 String name = s[m].substring(0, o);
							 String pos = s[m].substring(o + 1);
							 String chunk1 = "I";
							 String te = name + " " + pos + " " + chunk1+ "\r\n";
							 byte[] buff=new byte[]{};
							 buff = te.getBytes();
							 out.write(buff);
							 //num++;
							 //i++;
							 break;
						 }
					 }
				i = m + 1;
				break;
				}
			}
			
			continue;
		}
		//处理每个句子首部
		else if(s[i].substring(0, 2).equals("\r\n"))
		{
			 //int startpoint = 0;
			 //for(int op = 0; op < s[i].length(); op++)
			 //{
				//if(s[i].charAt(op) != '\r' || s[i].charAt(op) != '\n')
			      //  startpoint = op;
			 //}
			 
			 byte[] buff=new byte[]{};
			 //buff = "   \r\n".getBytes();
			 //out.write(buff);
			 //num++;
			 //buff = "\r\n".getBytes();
			 //out.write(buff);
			 s[i] = s[i].substring(2);
			 for(int k = 0; k < s[i].length(); k++)
			 {
				 if(s[i].charAt(k) == '/')
				 {
					 String te = "";
					 String name = s[i].substring(0, k);
					 String pos = s[i].substring(k + 1);
					 String chunk1 = "B";
					 te = name + " " + pos + " " + chunk1 + "\r\n";
					 buff = te.getBytes();
					 out.write(buff);
					 //num++;
					 i ++ ;
					 break;
				 }
			 }
		}
		//处理非实体部分的单词 注意句子切分
		else
		for(int j = 0; j < s[i].length(); j++)
		{
			//System.out.println(i+1);
	     if(s[i].charAt(j) == '/')
	     {
	    	 String name = s[i].substring(0, j);
	    	 String pos = s[i].substring(j + 1);
	    	 String chunk = null;
	    	 int flag = 0;
	    	 if(i < s.length - 2)
	    	 {
	    	 //System.out.println(s[i]+"  "+s[i+2].length());
	         //System.out.println(s[i]);
	    	 if(s[i+1].substring(0, 2).equals("\r\n"))
	    	 {   
	    		 if(name.equals("。")||name.equals("！")||name.equals("？"))
	    			 chunk = "O";
	    		 else
	    		 {
	    			 chunk = "B";
	    			 flag = 1;
	    		 }
	    	 }
	    	 else if(s[i+1].substring(0,2).equals("\r\n"))
	    	 {   
	    		 if(name.equals("。")||name.equals("！")||name.equals("？"))
	    			 chunk = "O";
	    		 else
	    		 {
	    			 chunk = "B";
	    			 flag = 1;
	    		 }
	    	 }
	    	 else
		     chunk = "B";
	    	 }
	    	 else 
	    	 {
	    		 if(name.equals("。")||name.equals("！")||name.equals("？"))
	    			 chunk = "O";
	    		 else
	    			 {
	    			 chunk = "B";
	    			 flag = 1;
	    			 }
	    	 }
	    	 String te = name + " " + pos + " " + chunk + "\r\n";
	    	 byte[] buff=new byte[]{};
			 buff = te.getBytes();
			 out.write(buff);
			 if(flag == 1)
			 {
				 buff=new byte[]{};
				 buff = "。 w O\r\n".getBytes();
				 out.write(buff);
				 flag = 0;
			 }
			 num++;
			 i++;
			 break;
	     }
		}
	}

}
}
