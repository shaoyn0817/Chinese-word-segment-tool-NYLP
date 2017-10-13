package NYLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class Pro98corpus {
 String s[];
public Pro98corpus(String str) throws Exception   //substring start 为其实字符   end 为截止字符 不输出
{
	String path = "D:\\邵轶男\\数据包\\NYLP语料库\\98年语料库";
	File file = new File(path);
	String encoding = "UTF-8"; 
	Long filelength = file.length();
	byte[] filecontent = new byte[filelength.intValue()];
	FileInputStream in = new FileInputStream(file);
	in.read(filecontent);
	String t = new String(filecontent,encoding);
	String []k = t.split("  ");
	s = k;
}

public String[][] getLaunchMatrix()
{
	HashMap table = new HashMap();
	for(int i = 0; i < s.length; i++)
	{
		String group; //统计二元组
		int start = 0;
		int end = 0;
		if(s[i].indexOf(0) == '[')
			start = 1;
		for(int j = 0; j < s[i].length(); j++)
		{
			if(s[i].indexOf(j) == ']')
				end = j;
		}
		group = s[i].substring(start, end);
		
	}
	
	return null;
}
}
