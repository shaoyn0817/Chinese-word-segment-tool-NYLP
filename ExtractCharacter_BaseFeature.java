package NYLP;
// F为句首  S为句中 T为句末   <ST>开始  <EN>结尾
//ST:1 F:2 S:3 T:4 <EN>:5
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class ExtractCharacter_BaseFeature {
String k[];
String kt[]; 
String a[];
double minlaunch = 999;
double minconvert = 999;
double minprobability = 999;
public static void main(String []args) throws IOException
{
	ExtractCharacter_BaseFeature s = new ExtractCharacter_BaseFeature(); 
	s.ConvertMatrix();
	HashMap k[] = s.LaunchMatrix();
	System.out.println(k[0].get('日'));
	System.out.println(k[1].get('日'));
	System.out.println(k[2].get('日'));
}

public ExtractCharacter_BaseFeature() throws IOException
{
	File file = new File("NYLP\\语料库.txt");
	Long filelength = file.length();
	byte[] filecontent = new byte[filelength.intValue()]; 
	FileInputStream in = new FileInputStream(file);
	in.read(filecontent);
	in.close();
	String t = new String(filecontent);
	String []s = t.split("  ");
	k = s;
	kt = s;
		//System.out.print(s[19]+"  "); 19为句子分段处，格式为：    。/n本       三个一体 
	int num = 2; 
	for(int i = 0; i < k.length; i++)
	{
		for(int j = 0; j < k[i].length(); j++)
		{
		    if(k[i].charAt(j) == '\n' && i != k.length - 1)
		    	num += 4; //遇见句子结尾 添加<EN><EN><ST><ST>
		    else if(k[i].charAt(j) == '\n' && i == k.length - 1)
		    	num+=2;
		    else num++;
		}
	}
	a = new String[num];
	//System.out.println(num);
	num = 2;
	a[0] = "<ST>";
	a[1] = "<ST>";
	for(int i = 0; i < k.length; i++)  //把语料库转换为一个String数组，放置字标识符
	{
		for(int j = 0; j < k[i].length(); j++)
		{
			if(k[i].charAt(j) == '\n' && i != k.length - 1)
				{
				a[num++] = "<ST>";
				a[num++] = "<ST>";
				a[num++] = "<EN>";
				a[num++] = "<EN>";
				}
			else if(k[i].charAt(j) == '\n' && i == k.length - 1)
			{
				//System.out.println("OK");
				a[num++] = "<EN>";
				a[num++] = "<EN>";
			}
			else if(j == 0)
		    	a[num++] = "F";
		    else if(j == k[i].length() - 1)
		    	a[num++] = "T";
		    else a[num++] = "S";
		}
	}  
}


public HashMap ConvertMatrix()
{
	int number = 0;
    int[] record = new int[125];
    int score = 0;
    int total = 0;
    for(int i = 0; i < a.length - 2; i++)  //最后两个为<en>标识符
    {
    	score = 0;
    	if(a[i] == "<ST>") //百位
    		score += 0;
    	else if(a[i] == "F")
    		score += 25;
    	else if(a[i] == "S")
    		score += 50;
    	else if(a[i] == "T")
    		score += 75;
    	else if(a[i] == "<EN>")
    		score += 100;
    	if(a[i+1] == "<ST>")  //十位
    		score += 0;
    	else if(a[i+1] == "F")
    		score += 5;
    	else if(a[i+1] == "S")
    		score += 10;
    	else if(a[i+1] == "T")
    		score += 15;
    	else if(a[i+1] == "<EN>")
    		score += 20;
    	if(a[i+2] == "<ST>")  //个位
    		score += 0;
    	else if(a[i+2] == "F")
    		score += 1;
    	else if(a[i+2] == "S")
    		score += 2;
    	else if(a[i+2] == "T")
    		score += 3;
    	else if(a[i+2] == "<EN>")
    		score += 4;
    	record[score] ++;  //将权值对应的record数组中的数字加1，记录该权值出现的次数
    }
    number = 0; //26个词性序列
    String [][]matrix = new String[26][4]; //所有词性转移序列
    HashMap result = new HashMap();
    int index = 0;
    for(int l = 0; l < 5; l++) //最外层向内扩展
    {
    	for(int m = 0; m < 5; m++)
    	{
    		for(int n = 0; n < 5; n++)
    		{
    			number = n + m * 5 + l * 25;
    			if(record[number] != 0)
    			{

    				if(n == 0)
    				   matrix[index][2] = "<ST>";
    				else if(n == 1)
     				   matrix[index][2] = "F";
    				else if(n == 2)
     				   matrix[index][2] = "S";
    				else if(n == 3)
     				   matrix[index][2] = "T";
    				else if(n == 4)
     				   matrix[index][2] = "<EN>";
    				if(m == 0)
     				   matrix[index][1] = "<ST>";
     				else if(m == 1)
      				   matrix[index][1] = "F";
     				else if(m == 2)
      				   matrix[index][1] = "S";
     				else if(m == 3)
      				   matrix[index][1] = "T";
     				else if(m == 4)
      				   matrix[index][1] = "<EN>";
    				if(l == 0)
      				   matrix[index][0] = "<ST>";
      				else if(l == 1)
       				   matrix[index][0] = "F";
      				else if(l == 2)
       				   matrix[index][0] = "S";
      				else if(l == 3)
       				   matrix[index][0] = "T";
      				else if(l == 4)
       				   matrix[index][0] = "<EN>";
    				matrix[index][3] = Double.toString((double)record[number] / (a.length + 2)); 
    				index++;
    			}
    		}
    	}
    }
    for(int i = 0; i < 26; i++)
    {
    	String s = matrix[i][0] + matrix[i][1] + matrix[i][2];
    	result.put(s, matrix[i][3]);
    	if(Double.valueOf(matrix[i][3]) < minconvert)
    		minconvert = Double.valueOf(matrix[i][3]);
    }
	//for(int j = 0; j < 26; j++)
	//{
		//System.out.println(matrix[j][0]+" "+matrix[j][1]+" "+matrix[j][2]+" : "+matrix[j][3]);
	//}

    return result;
}

public HashMap[] LaunchMatrix()
{
	HashMap []a = new HashMap[3];
	for(int i = 0; i < 3; i++)
	{
		a[i] = new HashMap();
	}
	int o ; //记录每次递增的变量值
	int total = 0;
	for(int i = 0; i < k.length; i++)
	{
		for(int j = 0; j < k[i].length(); j++)
		{
			if(k[i].charAt(j) == '\n')
				{
				continue;
				}
			else if(j == 0)
			{
				if(a[0].get(k[i].charAt(j)) == null)
					{
					a[0].put(k[i].charAt(j), 1);
					total++;
					}
				else 
				{   
					o = (int) a[0].get(k[i].charAt(j)) + 1;
					a[0].put(k[i].charAt(j), o);
					total++;
				}
			}
			else if(j == k[i].length() - 1)
			{
				if(a[2].get(k[i].charAt(j)) == null)
				{
					a[2].put(k[i].charAt(j), 1);
				    total++;
				}
				else 
				{
					o = (int) a[2].get(k[i].charAt(j)) + 1;
					a[2].put(k[i].charAt(j), o);
					total++;
				}
			}
			else
			{
				if(a[1].get(k[i].charAt(j)) == null)
					{
					 a[1].put(k[i].charAt(j), 1);
					 total++;
					}
				else 
				{
					o = (int) a[1].get(k[i].charAt(j)) + 1;
					a[1].put(k[i].charAt(j), o);
					total++;
				}
			}
		}
	}  
	int value = 0;
	for(int i = 0; i < 3; i++)
	{
		Iterator p = a[i].keySet().iterator();
		while(p.hasNext())
		{
			Object ol =  p.next();
			char ss = (char) ol;
		    int v = (int) a[i].get(ol);
		    double k = ((double)v / (double)total);
		    if(k < minlaunch)
		    	minlaunch = k;
		    a[i].put(ol, k);
		}
	}
	return a;	
}


public double getMinlaunch()
{
	return (minlaunch/2);
}

public double getMinConvert()
{
	return (minconvert/100);
}

public double getMinProbability()
{
	return minprobability/2;
}
public HashMap getWordProbability()
{
	HashMap a = new HashMap();
	int total = 0;
	int flag = 0;
	for(int i = 0; i < kt.length; i++)
	{
		for(int j = 0; j < kt[i].length(); j++)
		{
		    if(kt[i].charAt(j) == '\n')
		    {
		    	flag = 1;
		    	String b1 = kt[i].substring(0,j);
		    	String b2 = kt[i].substring(j+1);
		    	if(a.get(b1) == null)
		    		a.put(b1, new Integer(1));
		    	else
		    	{
		    		a.put(b1, ((int)a.get(b1)+1));
		    	}
		    	if(a.get(b2) == null)
		    		a.put(b2, new Integer(1));
		    	else
		    	{
		    		a.put(b2, ((int)a.get(b2)+1));
		    	}
		    	total += 2;
		    }
		}
		if(flag == 1)
	    	flag = 0;
	    else 
	    {
	    	if(a.get(kt[i]) == null)
	    		a.put(kt[i], new Integer(1));
	    	else
	    	{
	    		a.put(kt[i], ((int)a.get(kt[i])+1));
	    	}
	    	total += 1;
	    }
	}
	Iterator p = a.keySet().iterator();
	while(p.hasNext())
	{
		Object ol =  p.next();
		String ss = (String) ol;
	    int v = (int) a.get(ol);
	    double k = (double)v/total;
	    if(k < minprobability)
	    	minprobability = k;
	    a.put(ol, k);
	}
	return a;
}


}
