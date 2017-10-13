package NYLP;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

class N_gram {
String [][]k; //存放总的结果
int num = 0;   //存放总的结果数
private class Node{
	int value;
	int connect;
}
private class Table{
	public Table(int idx, int lengthx)
	{
		id = idx;
		length = lengthx;
	}
	public Table()
	{
		id = 0;
		length = 0;
		prenode = new int[50][2];
	}
	int id;
	double length;
	int [][]prenode;
}




private String[][] Dijkstra(Node [][]graph, int length, int n, String str) throws IOException  //length 代表节点数
{
	Table [][]a = new Table[n][length]; //记录总的表 记录完全的表a参考书P113所绘制的图
	ExtractCharacter_BaseFeature huangxiaochun = new ExtractCharacter_BaseFeature();
	HashMap shao = huangxiaochun.getWordProbability(); //用于记录无向图的边的概率
	double minprobability = huangxiaochun.getMinProbability();
	for(int i = 0; i < n; i++)
	{
		for(int j = 0; j < length; j++)
		{
			a[i][j] = new Table();
		}
	}
	a[0][0].id = 50;   //标记初始点
	a[0][0].length = 1;
	a[0][1].id = 50;   //标记初始点
	a[0][1].length = 1;
	a[0][1].prenode[0][0] = 0; //前驱结点号
	a[0][1].prenode[0][1] = 50;   //前驱结点中的编号

	for(int j = 2; j < length; j++)  //代表列，表示探索的路径长度
	{
		double record[][] = new double[50][3]; //第一列代表prenode的节点号,第二列代表prenode中的ID标号,第三列表示长度
		int recordx = 0;
		int BoolPre[] = new int[j];//记录前驱节点
		int booindex = 0;//记录有几个前驱结点
		for(int l = 0; l < j; l++)
		{
			if(graph[l][j].connect == 1)
				BoolPre[booindex++] = l;
		}
		for(int t = 0; t < booindex; t++)
		{
		int preNumber = BoolPre[t]; //获取当前节点的prenode的节点
			//System.out.println(recordx+" gfjy");
		for(int i = 0; i < n; i++)  //table表中的编号,找寻前一个表中的数据，复制到record里，为构建当前table做准备
		{
			if(a[i][preNumber].id > 0)
			{
				for(int k = 0; k < 50; k++)
				{  //System.out.println(recordx+"ddd");
			 	    if(a[i][preNumber].prenode[k][1] != 0)  //前驱节点table中的编号
					{	
						record[recordx][0] = preNumber;  // 0为节点号,1为前驱结点表中的ID标号,3为长度
 						if(i == 0)
 							record[recordx][1] = 50;
 						else
 							record[recordx][1] = i;  
 						String tt = str.substring(preNumber,j);
 						if(shao.get(tt) == null)
					    record[recordx ++][2] = a[i][preNumber].length - Math.log10(minprobability); // *************这里的1为暂定边长，后续按几率设置 4处					
 						else
 						record[recordx ++][2] = a[i][preNumber].length - Math.log10((double)shao.get(tt));	
					}
			 	    else if(preNumber == 0 && i == 0)
			 	    {
			 	    	record[recordx][0] = preNumber;
			 	    	record[recordx][1] = 50;
			 	    	String tt = str.substring(preNumber,j);
			 	    	if(shao.get(tt) == null)
			 	    	record[recordx++][2] = a[i][preNumber].length - Math.log10(minprobability);  //*****指定边长为1 后续更改
			 	    	else
			 	    	record[recordx++][2] = a[i][preNumber].length - Math.log10((double)shao.get(tt));
			 	    	break;
			 	    }
					else	
					  break;
				}
			}
			else break;
		}
		}

		//已经查找完所有prenode到该节点的路径，接下来进行排序，选出前n个长度
		for(int x = 0; x < n; x++)    //x表示当前表中的编号 j为当前是几号节点
		{
		
			double min = 9999;
			for(int y = 0; y < 50; y++)
			{
		
				if(record[y][1] > 0)
				{
					if(record[y][2] < min)
						min = record[y][2];
				}
				else if(record[y][1] > 9000)
					break;
			}
		    int prenodex = 0;////////////////////////////////////
			for(int y = 0; y < 50; y++)
			{
				if(record[y][1] <= 0)
					break;
				if(min > 9000)
					break;
				else if(record[y][2] == min )
				{
					double temp = 0;
					if(x == 0)              //x表示当前表中的编号 j为当前是几号节点
						a[x][j].id = 50;
					else 
						a[x][j].id = x;
					if(record[y][1] == 0)
						temp = 50;
					else 
						temp = record[y][1];					
					if(prenodex >= 1 && a[x][j].prenode[prenodex-1][1] == temp && a[x][j].prenode[prenodex-1][0] == record[y][0])
						{
						record[y][2] = 9999;
						continue;
						}
					a[x][j].id = (int)temp;
					a[x][j].length = min; //x为当前遍历Ngram中n的值
					a[x][j].prenode[prenodex][0] = (int)record[y][0];
					a[x][j].prenode[prenodex++][1] = (int)temp;
					//System.out.println("第"+j+"个节点:  "+ (x+1)+  "  "+min+" ("+record[y][0]+","+record[y][1]+")");
					/*{
						if(x == 0)
							b[x][j].id = 50;
						else
							b[x][j].id = x;
						b[x][j].length = min;
						b[x][j].prenode[prenodeb][0] = record[y][0];
						if(record[y][1] == 0)
							b[x][j].prenode[prenodeb++][1] = 50;
						else 
							b[x][j].prenode[prenodeb++][1] = record[y][1];
					}*/
					record[y][2] = 9999;
				}
			}
		}
	}	

	k = new String[1000][length]; //********输出的总结果
	for(int i = 0; i < n ; i++)
	{
		if(a[i][length-1].id == 0)
			break;
		else 
		{
			for(int j = 0; j < 50; j++)
			{
			if(a[i][length - 1].prenode[j][1] == 0)
				break;
			else
			   {
				String []temp =new String[length];
			    temp[0] = str.substring(a[i][length-1].prenode[j][0],length - 1);
		        FindPath(a,n,length,str,a[i][length-1].prenode[j][0],a[i][length - 1].prenode[j][1],1,temp);//此处的length是词数+1 以书上的例子 length = 8
			   }
			}
		}
	}
	//System.out.println(num);
	//for(int i = 0; i < num; i++)
	//{
		//for(int j = 0; j < length; j++)
		//{
		    //if(k[i][j] != null)
		    	//System.out.print(k[i][j]+"  ");
		//}
		//System.out.println();
	//}
	String result[][] = new String[num][length];
	for(int i = 0; i < num; i++)
		result[i] = k[i];
    return result;
}


private void FindPath(Table b[][], int n, int length, String str,int tablenum, int tableindex,int position,String[] temp)//n代表ngram,nowlength代表现在在哪张table里
{
	if(tablenum == 0)
	{
		for(int i = position - 1; i >= 0; i--)
		{
			k[num][i] = temp[position -1 - i];
		}
		for(int t = position - 1; t >= 0; t--)
		{
			System.out.print(temp[t]+"   ");
		}
		System.out.println();
		num++;
		return;
	}
			for(int j = 0; j < 50; j++)
			{
				if(tableindex == 50)
					tableindex = 0;
				if(b[tableindex][tablenum].prenode[j][1] == 0)
					return;
				else 
				{
					temp[position] = str.substring(b[tableindex][tablenum].prenode[j][0], tablenum);
					FindPath(b,n,length,str,b[tableindex][tablenum].prenode[j][0],b[tableindex][tablenum].prenode[j][1],position+1,temp);
				}
			}
}


public String[][] WordSegment(String str,int n) 
{
	File f = new File("NYLP\\dictionary.txt");
	Dictionary dic = new Dictionary(f);    //建立字典 
	int max_word_length = dic.getMaxWordLength();
	int length = str.length();
	Table a[] = new Table[length];  //各字的节点专用table
	Node graph[][] = new Node[length + 1][length + 1];
	for(int i = 0; i < length + 1; i ++)
	{
		for(int j = 0; j < length + 1; j++)
		{
			graph[i][j] = new Node();
			graph[i][j].connect = 0;
			graph[i][j].value = 0;
		}
	}
	for(int i = 2; i <= max_word_length; i++)
	{
		for(int j = 0; j < length - i + 1; j++)
		{
			String testString = str.substring(j, j + i);
			//System.out.println(testString);
			if(dic.FindWord(testString))
			{
				//System.out.println(testString);
				graph[j][j + i].connect = 1;
			}
			
		}
	    
	}
	for(int i = 0; i < length ; i++)
	{
		graph[i][i+1].connect = 1;
		graph[i][i+1].value = 1;
	}
	//for(int i = 0; i < length + 1; i ++)  //输出二维数组 显示图的联通状况
	//{
		//for(int j = 0; j < length + 1; j++)
		//{
			//System.out.print(graph[i][j].connect);
		//}
		//System.out.println();
	//} 
	String result[][] = null;
	try {
		result = Dijkstra(graph, length + 1, n, str);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return result;
}
    public static void main(String []args)
    {
    	N_gram testPro = new N_gram();
    	testPro.WordSegment("老师说明天下雨",6);
    }


    
}
