package NYLP;
//46个词性
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class ExtractHMMConvert {
	public static void main(String []args) throws IOException{
		ExtractHMMConvert a = new ExtractHMMConvert();
		a.Extract();
		//a.findCiNum();
	}
	
public ExtractHMMConvert() throws IOException {
	
}

public void Extract() throws IOException
{
	HashMap dict = new HashMap();
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
		String pos = "";
		System.out.println("一共："+s.length+"句话   当前第"+(i+1)+"句话");
		String temp [] = s[i].split(" ");
		for(int j = 0; j < temp.length ; j++)//每个词
		{   
			//System.out.println("第"+(i+1)+"句话的第 " + j +"个词");
			int flag = 0;
			for(int l =0; l < temp[j].length(); l++)
			{
				if(temp[j].charAt(0) == '[')
				{
					for(int z = 0 ; z < temp[j].length(); z++)
					{
						if(temp[j].charAt(z) == '/')
						{
							String cx = temp[j].substring(z+1);
							pos = pos + cx + " ";
							break;
						}
					}
					
					for(int d = j+1; d < temp.length; d++)
					{
						if(temp[d].charAt(temp[d].length() - 3) == ']')
						{
							for(int u = 0; u < temp[d].length(); u++)
							{
								if(temp[d].charAt(u) == '/')
								{
									String cx = temp[d].substring(u+1,temp[d].length() - 3);
									pos = pos + cx + " ";
									break;
								}
							}
							j = d;
							flag = 1;
							break;
						}
						else if(temp[d].charAt(temp[d].length() - 2) == ']')
						{
							String cx = temp[d].substring(temp[d].length() - 1);
							pos = pos + cx + " ";
							j = d;
							flag = 1;
							break;
						}
						else
						{
						for(int u = 0; u < temp[d].length(); u++)
						{
							if(temp[d].charAt(u) == '/')
							{
								String cx = temp[d].substring(u+1);
								pos = pos + cx + " ";
								break;
							}
						}
						}
					}
				}
				if(temp[j].charAt(l) == '/')
				{ 		
					String cx = temp[j].substring(l+1);
					//System.out.println(cx);
					pos = pos + cx + " ";
					break;
				}
				else if(flag == 1)
					break;
			}
		}
		
		
		//遍历完一句话 开始处理
		String p[] = pos.split(" ");
		//System.out.println(pos);
		for(int t = 0 ; t < p.length - 1; t++)
		{
			String c = p[t] + " " + p[t + 1];
			if(dict.get(c) == null)
			{
				dict.put(c, (double) 1);
				num ++;
			}
			else
			{
				dict.put(c,(double)dict.get(c)+1);
				num++;
			}
		}
	}
	File ff = new File("NYLP\\zhuanyi.txt");
	FileOutputStream oo = new FileOutputStream(ff);
	Iterator q = dict.keySet().iterator();
	while(q.hasNext())
	{
		String word = (String) q.next();
		double number = (double)dict.get(word) / (double)num;
		String wre = word + " " + Double.toString(number)+" \n";
		System.out.println(word);
		oo.write(wre.getBytes());
	}
}

public void findCiNum() throws IOException
{
	HashMap dicnum = new HashMap();
	File f = new File("NYLP\\zhuanyi.txt");
	FileInputStream in = new FileInputStream(f);
	Long len = f.length();
	byte bb[] = new byte[len.intValue()];
	in.read(bb);
	String ss = new String(bb);
	String s[] = ss.split(" ");
	for(int i = 0; i < s.length; i++)
	{
		if(s[i].length() <= 6)
			dicnum.put(s[i], 1);
	}
	Iterator q = dicnum.keySet().iterator();
	int num = 0;
	String cilist[] = new String[46];
	while(q.hasNext())
	{
		cilist[num] = (String)q.next();
		System.out.print(cilist[num++] + " ");
	}
	System.out.println("\r\n"+dicnum.size()+"  "+num );
}
}
