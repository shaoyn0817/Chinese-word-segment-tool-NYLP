package NYLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CRF_EXAMINE {
	public static void main(String []args) throws IOException
	{
		CRF_EXAMINE a = new CRF_EXAMINE();
	}
public CRF_EXAMINE() throws IOException
{
	File f = new File("D:\\CRF++\\chunk\\train2.data");
	File o = new File("D:\\CRF++\\chunk\\train2examine.data");
	Long filelength = f.length();
	Long olength = o.length();
	byte[] filecontent = new byte[filelength.intValue()];
	byte[] fileo = new byte[olength.intValue()];
	FileInputStream in = new FileInputStream(f);
	FileOutputStream out = new FileOutputStream(o);
	in.read(filecontent);
	String s = new String(filecontent);
	String []str = s.split("\r\n");
	System.out.println(str.length);
	for(int i = 0; i < str.length; i++)
	{
		String []temp = str[i].split(" ");
		String record = "";
		
		if(temp.length == 3)
		{
			for(int k = 0; k < 2; k++)
			{
				record = record + temp[k] + " ";
			}
			record = record + temp[2] + "\r\n";
			byte[] re = new byte[]{};
			re = record.getBytes();
			out.write(re);
		
		}
		else if(temp.length != 3)
		{
			System.out.println(i+" " + str[i+1] + " length:" + temp.length);
		}
		
	}
	in.close();
	out.close();
}
}
