package NYLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Test {
public static void main(String []args) throws IOException
{ 
	Test a = new Test();
	File file = new File("D:\\ÉÛéóÄÐ\\Êý¾Ý°ü\\199801.txt");
	Long filelength = file.length();
	byte[] filecontent = new byte[filelength.intValue()]; 
	FileInputStream in = new FileInputStream(file);
	in.read(filecontent);
	in.close();
	String t = new String(filecontent);
	String []s = t.split("  ");
	if(s[16].charAt(s[16].length() - 1) == '\n')
		System.out.println("\\n");
	if(s[16].substring(s[16].length()-2) == "\r\n")
		System.out.println("\\r\\n");
	if(s[17].charAt(1) == '\n')
	System.out.println("asdasd" );
	System.out.println(s[17].charAt(2) );
}
}
