package NYLP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

public class NYLP {
	public static void main(String []args) throws IOException
	{
		NYLP k = new NYLP();
		String ss = "��ʦ˵��������";
		String str1 = "�μ��룺���α���˾���³��������³������й�����ԺԺʿ������ϡ�н��������о�ԺԺ���������Ƽ���ѧ��ְ���ڡ����ϴ�ѧ��ְ���ڡ����Ĵ�ѧ��ְ���ڡ����Ļ����������˴�ί��ίԱ�����α���˾���¡�";
		String str2 = "����Ůʿ���������̩����п������޹�˾����ְԱ�����ܡ��������������̩�Ｏ�����޹�˾���տ������ĺϹ����󲿾���";
		String str3 = "��ӭ����ר���ʦ,���κ��������Ӽ������޹�˾����ƿƳ������������ӹɷ����޹�˾����ƿƳ����������ӹɷ����޹�˾���ܻ��ʦ����񲿾���";
		k.cut(ss, 1);
	}
	
public NYLP()
{
	
}
public void cut(String str, int flag) throws IOException
{
	HMM qudong = new HMM(); //**************************************�����������֮��**************************************
	//Character_Base_Probability qudong = new Character_Base_Probability();
	int leng = str.length();
	String finalresult[] = new String[leng];
	int finalindex = 0;
	String []temp = str.split("��|��|��");
	for(int i = 0; i < temp.length; i++)
	{
		N_gram ngram = new N_gram();
	    String [][]result = ngram.WordSegment(temp[i], 10);//***************************************************��λ���� ��¼�������зִʴַּ��Ľ��
	    double probability[] = new double[result.length];
	    for(int j = 0 ;j < result.length ; j++) //result.lengthΪ��ά���������
	    {
	    	//HMM qudong = new HMM(); //**************************************�����������֮��**************************************
	    	//Character_Base_Probability qudong = new Character_Base_Probability();
	    	int realnum;
	    	for(realnum = 0 ; realnum < result[0].length; realnum++)//ͳ���ַ�����������Ч���ַ�������
	    	{
	    		if(result[j][realnum] != null)
	    			realnum++;
	    		else break;
	    	}
	    	String []juzi = new String[realnum - 1]; //juzi��¼����ÿһ�����ӵ���Ч���֣����ಿ����null �Ͳ���¼��juzi��
	    	//realnum ��¼��ÿһ���ַֽ������Ч�ķִ���
	    	for(int m = 0; m < realnum - 1; m++)
	    	{
	    		juzi[m] = result[j][m];
	    		System.out.print(juzi[m]+" ");
	    	}
	    	System.out.println("                       "+juzi.length);
	    	probability[j] = qudong.getProbability(juzi);//��ȡjuzi�еķִʽ���ĸ���
	    }
	    double max = -1;
	    int index = -1;
	    for(int j = 0 ;j < result.length ; j++) //ͳ�Ʒִʽ���ĸ��ʣ���ȡ���ֵ
	    {
	    	if(probability[j] > max)
	    	{
	    		max = probability[j];
	    		index = j; 
	    	}
	    }
		for(int num = 0 ; num < result[0].length; num++)//ͳ���ַ�����������Ч���ַ�������
		{
			if(result[index][num] != null)
			{
				finalresult[finalindex++] = result[index][num];
			}
    		else break;
		} 		
	}
	//finalresult[] ��¼�����еķִʽ��    finalindexΪ����
	String rr[] = new String [finalindex];
	for(int i = 0 ; i < finalindex; i++)
	{
		//System.out.print(finalresult[i]+"  ");
		rr[i] = finalresult[i];
	}
	String rrpos[] = qudong.getresult(rr);
	System.out.println("\r\n\r\n������������Ʒ�ģ�ͽ���ʶ���ķִʽ��Ϊ��");
	File lp = new File("D://CRF++//NYLP//fenci.data");
	FileOutputStream o2 = new FileOutputStream(lp);
	for(int i = 0; i < rr.length; i++)
	{
		System.out.print(rr[i]+ "/" + rrpos[i]+", ");
		String te = rr[i] + "	" + rrpos[i] + "	B" + "\r\n";
		o2.write(te.getBytes());
	}
	o2.close();
	System.out.println("\r\n");
	if(flag == 1)
	{
		System.out.println("�������������������ģ�ͽ���ʶ��ʵ�������ķִʽ��Ϊ�� ");
		CRFPoser y = new CRFPoser();
		y.CRFpos();
	}
	System.out.println("\r\n");
	System.out.println("HANLP�ִ����ִʽ��:");	
	Segment segment = HanLP.newSegment().enableOrganizationRecognize(true);
	List<Term> termList = segment.seg(str);
	String alpha = termList.toString();
	alpha = alpha.substring(1,alpha.length()-1);
	System.out.println(alpha);
}


}
