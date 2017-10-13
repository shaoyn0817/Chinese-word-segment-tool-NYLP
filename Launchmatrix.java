package NYLP;

import java.io.*;
import java.util.*;
 
public class Launchmatrix{
	
	public static void main(String []args) throws IOException
	{
		Launchmatrix a = new Launchmatrix("NYLP\\fashe.txt");
		String [][] test = a.ProbabilityMatrix(0);
		double o = 0;
		for(int i = 0; i < 20; i++){}
			//System.out.println(test[0][i]+":  "+test[1][i]);
		System.out.println(test[0].length);
		for(int index = 0; index < test[0].length; index++)
			{
			    if(index < 20)
			    //System.out.println(test[0][index]);
				if(test[0][index].equals("一丁") )
				{
				//System.out.println("初始值为："+Double.valueOf(test[1][index]));
				o =  10000 *  (Double.valueOf(test[1][index]));
				//System.out.println(o);
				}
			}
		double min = 999;
		for(int i = 0; i < 44; i ++)
		{
			String [][]t = a.ProbabilityMatrix(i);
			for(int j = 0; j < t[0].length; j++)
			{
				if((Double.valueOf(t[1][j]) )< min)
					min = Double.valueOf(t[1][j]);
			}
		}
		System.out.println("最小值为：" + min/2);
		
    }
	final int ciNum = 44;
	private HashMap []dictionary = new HashMap[ciNum];//**44个词性
	private ArrayList []key = new ArrayList[ciNum];
	Poslist dic = new Poslist();

	public Launchmatrix(String filename) throws IOException{//将词典读入HashMap中
		File file = new File(filename);
		for(int i = 0; i < ciNum; i++)
		{
			dictionary[i] = new HashMap();
			key[i] = new ArrayList();
		}
		String str;
		String encoding = "UTF-8"; 
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		FileInputStream in = new FileInputStream(file);
		in.read(filecontent);
		String t = new String(filecontent,encoding);
		String []s = t.split(" ");
		for(int i = 0; i < s.length; i++) //处理回车符
		{
			if((i+1) % 3 == 1 && i != 0)
				{
				//System.out.println("此段有效果");
				s[i] = s[i].substring(1);
				}
		}
		for(int i = 0; i < s.length - 3; i += 3)
		{
			if((i+1) % 3 == 1)
			{
				int index = dic.cixing_number(s[i]);
				//System.out.println("本处出现错误 :" + s[i]) ;
				String zi = s[i+1];
				String number = s[i+2];
				//System.out.println(index);
				key[index].add(zi);
				dictionary[index].put(zi, number);
			}
		}
		/*int total = 0;
		for(int i = 0; i < ciNum; i++)
		{
			total += key[i].size();
		}
		System.out.println(total+"  "+s.length/3);*/
	}
	public String[][] ProbabilityMatrix(int index){//在词典中查找词性标记 返回的标记是两行 N列 返回的是String类型
		String [][] record = new String[2][key[index].size()];
		for(int i = 0; i < key[index].size(); i++)
		{
			record[0][i] = (String) key[index].get(i);
			record[1][i] = (String) dictionary[index].get(key[index].get(i));
		}
		return record;
	}
	
	
}
