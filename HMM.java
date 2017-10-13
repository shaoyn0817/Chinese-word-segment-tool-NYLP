package NYLP;

import java.io.IOException;

public class HMM {
	public static void main(String []args) throws Exception 
	{
		HMM a = new HMM();
		String k[] = {"你","到底","要","干嘛","邵轶男"};
		a.getProbability(k);
	}
final double min = 2.111397322748195E-6/2 ;	
final int ciNum = 44;
Poslist dic  = new Poslist();
Launchmatrix lau = new Launchmatrix("NYLP\\fashe.txt"); //定义发射矩阵词典所在的位置
Convertmatrix ConMatrix = new Convertmatrix("NYLP\\zhuanyi.txt"); //定义转移矩阵词典所在位置
double convert[][] = ConMatrix.ProbabilityMatrix();
public HMM() throws IOException
{
	
}
public double getProbability(String []str)
{
	double []prob = new double[ciNum]; //记录发射概率
	double [][]record = new double[ciNum][str.length];//记录后向变量，用于传递
	double []record2 = new double[ciNum];//记录后向变量，用于传递
	double []linshi = new double[ciNum]; //用于暂存 以便选出最大值
	int bianhao[][] = new int[ciNum][str.length]; //记录最大值的编号和序号
	
	
	for(int i = 0; i < ciNum; i++)
		{
		record2[i] = 0;
		linshi[i] = 1;
		}
    for(int i = 0; i < 1; i++) //初始化概率 算出观察概率
    {
    	for(int j = 0; j < ciNum; j++) 
		{
			String s[][] = lau.ProbabilityMatrix(j);
			for(int index = 0; index < s[0].length; index++)
			{
				if(s[0][index].equals(str[i]) )
				{
				record[j][0] =  (Double.valueOf(s[1][index]) );
				}
			}
		}
    }
    for(int i = 0; i < ciNum; i++)  //平滑处理
    {
    	if(record[i][0] == 0)
    	{
    		record[i][0] = min;
    	}
    }
    //输出第一列后向变量
    /*
    for(int i = 0; i < ciNum; i++)
    {
    	System.out.println("第"+i+"个为"+record[i][0]);
    }
    */
	for(int i = 1; i < str.length; i++) //动态规划计算概率
	{
		prob = new double[ciNum];
		for(int j = 0; j < ciNum; j++)  //计算发射概率  
		{
			String s[][] = lau.ProbabilityMatrix(j);
			for(int index = 0; index < s[0].length; index++){
				if(s[0][index].equals(str[i])){
					prob[j] = Double.valueOf(s[1][index]);
					break;
				}
			}
		}
		
	    for(int k = 0; k < ciNum; k++)  //平滑处理 发射概率
	    {
	    	if(prob[k] == 0)
	    	{
	    		prob[k] = min;
	    	}
	    }
		/*
		if(i == str.length - 1)
		{
			for(int l = 0; l < prob.length; l++)
			{
				System.out.println("最后一列的发射概率 "+dic.number_cixing(l)+"词性的概率为： "+ prob[l]);
			}
		}
        */
		
		  for(int m = 0; m < ciNum; m++) //m为主词性  n为副词性 n变动 m不动 
		  {
			int lnum = 0;
			for(int n = 0; n < ciNum; n++)
			{
				linshi[lnum++] =  record[n][i-1] * convert[n][m] * prob[m]; //hmm公式
			}
			double max = -1;
			for(int k = 0; k < ciNum; k++)
			{
				if(linshi[k] > max)
				{
					//记录编号
					bianhao[m][i] = k;
					max = linshi[k];
				}
			}
			record2[m] = max;
		  }
		  for(int t = 0; t < ciNum; t++)
		  {
		  record[t][i] = record2[t];
		  }
		  /*测试后向变量是否正确
		  if( i == 1)
			  for(int k = 0; k < ciNum; k++)
				{
					System.out.println("第"+k+"个为："+record[k][i]);
				}
		  */
	}
	

		
	/*for(int i = 0; i < ciNum ;i++)
	{
		for(int j = 0; j < str.length; j++)
		{
			System.out.print(bianhao[i][j]+"       ");
		}
		System.out.println();
	}*/

		double max = -1;
		int index = -1;
		for(int k = 0; k < ciNum; k++)
		{
			if(record[k][str.length - 1] > max)
			{
				//记录编号
				index = k;
                max = record[k][str.length - 1];
			}
		}
		int shuchu[] = new int[str.length + 1]; //记录输出的编号
		shuchu[str.length] = index;
		for(int i = str.length - 1; i > 0 ; i--)
		{
			shuchu[i] = bianhao[shuchu[i+1]][i];
		}
    // String r[] = new String[shuchu.]
	//结果可视化
	/*	for(int i = 1; i < shuchu.length; i++)
	{
		System.out.println("第"+i+"号词性为"+dic.number_cixing(shuchu[i]));
		
	}
	*/
	//return 0;
	return max;
}


public String[] getresult(String []str)
{
	double []prob = new double[ciNum]; //记录发射概率
	double [][]record = new double[ciNum][str.length];//记录后向变量，用于传递
	double []record2 = new double[ciNum];//记录后向变量，用于传递
	double []linshi = new double[ciNum]; //用于暂存 以便选出最大值
	int bianhao[][] = new int[ciNum][str.length]; //记录最大值的编号和序号
	
	
	for(int i = 0; i < ciNum; i++)
		{
		record2[i] = 0;
		linshi[i] = 1;
		}
    for(int i = 0; i < 1; i++) //初始化概率 算出观察概率
    {
    	for(int j = 0; j < ciNum; j++) 
		{
			String s[][] = lau.ProbabilityMatrix(j);
			for(int index = 0; index < s[0].length; index++)
			{
				if(s[0][index].equals(str[i]) )
				{
				record[j][0] =  (Double.valueOf(s[1][index]) );
				}
			}
		}
    }
    for(int i = 0; i < ciNum; i++)  //平滑处理
    {
    	if(record[i][0] == 0)
    	{
    		record[i][0] = min;
    	}
    }
    //输出第一列后向变量
    /*
    for(int i = 0; i < ciNum; i++)
    {
    	System.out.println("第"+i+"个为"+record[i][0]);
    }
    */
	for(int i = 1; i < str.length; i++) //动态规划计算概率
	{
		prob = new double[ciNum];
		for(int j = 0; j < ciNum; j++)  //计算发射概率  
		{
			String s[][] = lau.ProbabilityMatrix(j);
			for(int index = 0; index < s[0].length; index++){
				if(s[0][index].equals(str[i])){
					prob[j] = Double.valueOf(s[1][index]);
					break;
				}
			}
		}
		
	    for(int k = 0; k < ciNum; k++)  //平滑处理 发射概率
	    {
	    	if(prob[k] == 0)
	    	{
	    		prob[k] = min;
	    	}
	    }
		/*
		if(i == str.length - 1)
		{
			for(int l = 0; l < prob.length; l++)
			{
				System.out.println("最后一列的发射概率 "+dic.number_cixing(l)+"词性的概率为： "+ prob[l]);
			}
		}
        */
		
		  for(int m = 0; m < ciNum; m++) //m为主词性  n为副词性 n变动 m不动 
		  {
			int lnum = 0;
			for(int n = 0; n < ciNum; n++)
			{
				linshi[lnum++] =  record[n][i-1] * convert[n][m] * prob[m]; //hmm公式
			}
			double max = -1;
			for(int k = 0; k < ciNum; k++)
			{
				if(linshi[k] > max)
				{
					//记录编号
					bianhao[m][i] = k;
					max = linshi[k];
				}
			}
			record2[m] = max;
		  }
		  for(int t = 0; t < ciNum; t++)
		  {
		  record[t][i] = record2[t];
		  }
		  /*测试后向变量是否正确
		  if( i == 1)
			  for(int k = 0; k < ciNum; k++)
				{
					System.out.println("第"+k+"个为："+record[k][i]);
				}
		  */
	}
	

		
	/*for(int i = 0; i < ciNum ;i++)
	{
		for(int j = 0; j < str.length; j++)
		{
			System.out.print(bianhao[i][j]+"       ");
		}
		System.out.println();
	}*/

		double max = -1;
		int index = -1;
		for(int k = 0; k < ciNum; k++)
		{
			if(record[k][str.length - 1] > max)
			{
				//记录编号
				index = k;
                max = record[k][str.length - 1];
			}
		}
		int shuchu[] = new int[str.length + 1]; //记录输出的编号
		shuchu[str.length] = index;
		for(int i = str.length - 1; i > 0 ; i--)
		{
			shuchu[i] = bianhao[shuchu[i+1]][i];
		}
    String r[] = new String[shuchu.length - 1];
	
    for(int i = 1; i < shuchu.length; i++)
	{
		//System.out.println("第"+i+"号词性为"+dic.number_cixing(shuchu[i]));
		r[i-1] = dic.number_cixing(shuchu[i]);
	}
	
	return r;
	
}
}
