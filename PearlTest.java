package NYLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PearlTest {
public static void main(String []args) throws IOException
{
	PearlTest a = new PearlTest();
}

public PearlTest() throws IOException
{
	File f = new File("D://CRF++//NYLP//result2.txt");
	FileInputStream in = new FileInputStream(f);
	Long len = f.length();
	byte bb[] = new byte[len.intValue()];
	in.read(bb);
	String ss = new String(bb);
	ss = ss.replace("	"," " );
	ss = ss.replace("\r\n\r\n", "\r\n");
	File fo = new File("D://CRF++//NYLP//Finalresult2.txt");
	FileOutputStream out = new FileOutputStream(fo);
	out.write(ss.getBytes());
}
}
