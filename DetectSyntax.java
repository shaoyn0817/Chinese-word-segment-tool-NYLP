package NYLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DetectSyntax {
public static void main(String []args) throws IOException
{
	File f1 = new File("NYLP\\zhuanyi.txt");
	Long filelength = f1.length();
	byte[] filecontent = new byte[filelength.intValue()];
	FileInputStream in = new FileInputStream(f1);
	in.read(filecontent);
	String t = new String(filecontent);
	String []s = t.split(" ");
	for(int i = 0 ; i < 1000 ; i++)
	{
		if(s[i].charAt(0)=='\n')
			System.out.println("you n");
		if((i+1) % 3 == 1 && i != 0)
		{
		System.out.println(s[i].charAt(0)+"asd");
		s[i] = s[i].substring(1);
		}
	}
}
}
