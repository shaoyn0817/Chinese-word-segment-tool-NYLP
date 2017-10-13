package NYLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Preprocess {
	public static void main(String []args) throws IOException
	{
		Preprocess a = new Preprocess();
		//String s = "asdasdasd";
		//char[] v = s.toCharArray();
		//System.out.println(v[1]);
		//String m = (String)v.toString();
		//System.out.println(m);
	}
public Preprocess() throws IOException
{
	File file = new File("NYLP\\199801.txt");
	Long filelength = file.length();
	byte[] filecontent = new byte[filelength.intValue()]; 
	FileInputStream in = new FileInputStream(file);
	File tt = new File("NYLP\\clear199801.txt");
	FileOutputStream out=new FileOutputStream(tt);  
	in.read(filecontent);
	in.close();
	String t = new String(filecontent);
	char []p = t.toCharArray();
	int index = 0;
	int len = p.length;
	//System.out.println(p.length);
	for(int i = 0; i < p.length;)
	{
		if(p[i] == ' ' && p[i+1] == ' ')
		{
			p[i] = '*';
			i = i + 1;
			len--;
			continue;
		}
		else if(i < p.length - 3 && p[i] == '\r' && p[i+1] == '\n' && p[i+2] == '\r' && p[i+3] == '\n')
		{
			p[i] = '*';
			p[i+1] = '*';
			i = i + 2;
			len -= 2;
			continue;
		}
		else i++;
	}
	char []q = new char[len];
	for(int i = 0; i < p.length; i++)
	{
		if(p[i] != '*')
		   q[index++] = p[i];
	}
	//System.out.println(index);
	String l = new String(q);
	//System.out.println(l);
	byte m[] = l.getBytes();
	out.write(m);
	in.close();
	out.close();
}
}
