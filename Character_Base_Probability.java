package NYLP;

import java.io.IOException;
import java.util.HashMap;

public class Character_Base_Probability{	
	
	public static void main(String []args) throws IOException
	{
		double max = 0;
		int indexall = 0;
		N_gram b = new N_gram();
		Character k = new Character();
		String [][] record = b.WordSegment("何季麟：曾任本公司董事长、副董事长。任中国工程院院士、西北稀有金属材料研究院院长、北京科技大学兼职教授、中南大学兼职教授、宁夏大学兼职教授、宁夏回族自治区人大常委会委员。现任本公司董事。", 6);		
		for(int i = 0; i < record.length; i++)
		{
			int index = 0;
			for(int j = 0; j < record[i].length; j++)
			{
				if(record[i][j] == null)
				{
					index = j+1;
					break;
				}
			}
			String []temp = new String[index];
			for(int j = 0; j < record[i].length; j++)
			{
				if(record[i][j] == null)
					break;
				else
					temp[j] = record[i][j];
			}
			double lin = k.getProbability(temp);
			System.out.println("概率为： "+lin);
			if(lin > max)
				{
				max = lin;
				indexall = i;
				}
		}
		for(int i = 0; i < record[indexall].length; i++)
		{
			if(record[indexall][i] == null)
				break;
			else
				System.out.print(record[indexall][i]+" ");
		}
		
		
	}
	
public Character_Base_Probability() throws IOException
{
}

public double getProbability(String[] str) throws IOException //以句子为单位切分
{
	ExtractCharacter_BaseFeature a = new ExtractCharacter_BaseFeature();
	HashMap []launchmatrix = a.LaunchMatrix();
	HashMap convertmatrix = a.ConvertMatrix();
	double minconvert = a.getMinConvert();
	double minlaunch = a.getMinlaunch();
	//System.out.println("MINCONVERT:" +minconvert);
	//System.out.println(minlaunch);
	String record[] = new String[500];
	char recordWord[] = new char[500];
	record[0] = "<ST>";
	record[1] = "<ST>";
	int num = 2;
	int wordnum = 0;
	for(int i = 0; i < str.length; i++)
	{
		if(str[i] == null)
			break;
		for(int j = 0; j < str[i].length(); j++)
		{
		 recordWord[wordnum++] = str[i].charAt(j);
		 if(j == 0)
		 {
			record[num++] = "F";
		 }
		 else if(j == str[i].length() - 1)
		 {
			record[num++] = "T"; 
		 }
		else
		 {
			record[num++] = "S";
		 }
		}
	}
	record[num++] = "<EN>";
	record[num++] = "<EN>";
    //for(int lo = 0; lo < num; lo++)
    	//System.out.print(record[lo]+"  ");
	//此时NUM代表一共所有的字标记数目  recordWord代表中文字的字数
	double pro = 2; //先计算转移概率
	int index = 0; //记录中文字数组的字对应字符标记

	for(int i = 0; i < num - 2; i++)
	{
		String s = record[i] + record[i+1] + record[i+2];
		//System.out.println("OK");
		if(convertmatrix.get(s) == null) //没有找到转移序列 即用平滑概率代替
		{
		pro = pro * minconvert;
		}
		else 
		{
		pro = pro * Double.valueOf((String)convertmatrix.get(s));
		}
		//System.out.println(pro);
		if(record[i+2].equals("<EN>"))
			continue;
		else
		{
			int cixing = 0;
			if(record[i+2].equals("F"))
				cixing = 0;
			else if(record[i+2].equals("S"))
				cixing = 1;
			else if(record[i+2].equals("T"))
				cixing = 2;
			if(launchmatrix[cixing].get(recordWord[index]) == null) //没有找到发射概率 用平滑概率代替
				{
				pro = pro * minlaunch;
				index++;
				}
			else
			{
			pro = pro * (double)launchmatrix[cixing].get(recordWord[index++]);
		    //System.out.println("找到发射概率： "+ (double)launchmatrix[cixing].get(recordWord[index-1]));
			}
	     }
	}
	System.out.println("概率为： " + pro);
	return pro;
   }
  }

