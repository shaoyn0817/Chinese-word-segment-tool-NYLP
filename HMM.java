package NYLP;

import java.io.IOException;

public class HMM {
	public static void main(String []args) throws Exception 
	{
		HMM a = new HMM();
		String k[] = {"��","����","Ҫ","����","������"};
		a.getProbability(k);
	}
final double min = 2.111397322748195E-6/2 ;	
final int ciNum = 44;
Poslist dic  = new Poslist();
Launchmatrix lau = new Launchmatrix("NYLP\\fashe.txt"); //���巢�����ʵ����ڵ�λ��
Convertmatrix ConMatrix = new Convertmatrix("NYLP\\zhuanyi.txt"); //����ת�ƾ���ʵ�����λ��
double convert[][] = ConMatrix.ProbabilityMatrix();
public HMM() throws IOException
{
	
}
public double getProbability(String []str)
{
	double []prob = new double[ciNum]; //��¼�������
	double [][]record = new double[ciNum][str.length];//��¼������������ڴ���
	double []record2 = new double[ciNum];//��¼������������ڴ���
	double []linshi = new double[ciNum]; //�����ݴ� �Ա�ѡ�����ֵ
	int bianhao[][] = new int[ciNum][str.length]; //��¼���ֵ�ı�ź����
	
	
	for(int i = 0; i < ciNum; i++)
		{
		record2[i] = 0;
		linshi[i] = 1;
		}
    for(int i = 0; i < 1; i++) //��ʼ������ ����۲����
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
    for(int i = 0; i < ciNum; i++)  //ƽ������
    {
    	if(record[i][0] == 0)
    	{
    		record[i][0] = min;
    	}
    }
    //�����һ�к������
    /*
    for(int i = 0; i < ciNum; i++)
    {
    	System.out.println("��"+i+"��Ϊ"+record[i][0]);
    }
    */
	for(int i = 1; i < str.length; i++) //��̬�滮�������
	{
		prob = new double[ciNum];
		for(int j = 0; j < ciNum; j++)  //���㷢�����  
		{
			String s[][] = lau.ProbabilityMatrix(j);
			for(int index = 0; index < s[0].length; index++){
				if(s[0][index].equals(str[i])){
					prob[j] = Double.valueOf(s[1][index]);
					break;
				}
			}
		}
		
	    for(int k = 0; k < ciNum; k++)  //ƽ������ �������
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
				System.out.println("���һ�еķ������ "+dic.number_cixing(l)+"���Եĸ���Ϊ�� "+ prob[l]);
			}
		}
        */
		
		  for(int m = 0; m < ciNum; m++) //mΪ������  nΪ������ n�䶯 m���� 
		  {
			int lnum = 0;
			for(int n = 0; n < ciNum; n++)
			{
				linshi[lnum++] =  record[n][i-1] * convert[n][m] * prob[m]; //hmm��ʽ
			}
			double max = -1;
			for(int k = 0; k < ciNum; k++)
			{
				if(linshi[k] > max)
				{
					//��¼���
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
		  /*���Ժ�������Ƿ���ȷ
		  if( i == 1)
			  for(int k = 0; k < ciNum; k++)
				{
					System.out.println("��"+k+"��Ϊ��"+record[k][i]);
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
				//��¼���
				index = k;
                max = record[k][str.length - 1];
			}
		}
		int shuchu[] = new int[str.length + 1]; //��¼����ı��
		shuchu[str.length] = index;
		for(int i = str.length - 1; i > 0 ; i--)
		{
			shuchu[i] = bianhao[shuchu[i+1]][i];
		}
    // String r[] = new String[shuchu.]
	//������ӻ�
	/*	for(int i = 1; i < shuchu.length; i++)
	{
		System.out.println("��"+i+"�Ŵ���Ϊ"+dic.number_cixing(shuchu[i]));
		
	}
	*/
	//return 0;
	return max;
}


public String[] getresult(String []str)
{
	double []prob = new double[ciNum]; //��¼�������
	double [][]record = new double[ciNum][str.length];//��¼������������ڴ���
	double []record2 = new double[ciNum];//��¼������������ڴ���
	double []linshi = new double[ciNum]; //�����ݴ� �Ա�ѡ�����ֵ
	int bianhao[][] = new int[ciNum][str.length]; //��¼���ֵ�ı�ź����
	
	
	for(int i = 0; i < ciNum; i++)
		{
		record2[i] = 0;
		linshi[i] = 1;
		}
    for(int i = 0; i < 1; i++) //��ʼ������ ����۲����
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
    for(int i = 0; i < ciNum; i++)  //ƽ������
    {
    	if(record[i][0] == 0)
    	{
    		record[i][0] = min;
    	}
    }
    //�����һ�к������
    /*
    for(int i = 0; i < ciNum; i++)
    {
    	System.out.println("��"+i+"��Ϊ"+record[i][0]);
    }
    */
	for(int i = 1; i < str.length; i++) //��̬�滮�������
	{
		prob = new double[ciNum];
		for(int j = 0; j < ciNum; j++)  //���㷢�����  
		{
			String s[][] = lau.ProbabilityMatrix(j);
			for(int index = 0; index < s[0].length; index++){
				if(s[0][index].equals(str[i])){
					prob[j] = Double.valueOf(s[1][index]);
					break;
				}
			}
		}
		
	    for(int k = 0; k < ciNum; k++)  //ƽ������ �������
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
				System.out.println("���һ�еķ������ "+dic.number_cixing(l)+"���Եĸ���Ϊ�� "+ prob[l]);
			}
		}
        */
		
		  for(int m = 0; m < ciNum; m++) //mΪ������  nΪ������ n�䶯 m���� 
		  {
			int lnum = 0;
			for(int n = 0; n < ciNum; n++)
			{
				linshi[lnum++] =  record[n][i-1] * convert[n][m] * prob[m]; //hmm��ʽ
			}
			double max = -1;
			for(int k = 0; k < ciNum; k++)
			{
				if(linshi[k] > max)
				{
					//��¼���
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
		  /*���Ժ�������Ƿ���ȷ
		  if( i == 1)
			  for(int k = 0; k < ciNum; k++)
				{
					System.out.println("��"+k+"��Ϊ��"+record[k][i]);
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
				//��¼���
				index = k;
                max = record[k][str.length - 1];
			}
		}
		int shuchu[] = new int[str.length + 1]; //��¼����ı��
		shuchu[str.length] = index;
		for(int i = str.length - 1; i > 0 ; i--)
		{
			shuchu[i] = bianhao[shuchu[i+1]][i];
		}
    String r[] = new String[shuchu.length - 1];
	
    for(int i = 1; i < shuchu.length; i++)
	{
		//System.out.println("��"+i+"�Ŵ���Ϊ"+dic.number_cixing(shuchu[i]));
		r[i-1] = dic.number_cixing(shuchu[i]);
	}
	
	return r;
	
}
}
