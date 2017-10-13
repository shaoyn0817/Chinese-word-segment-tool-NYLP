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
		String ss = "老师说明天下雨";
		String str1 = "何季麟：曾任本公司董事长、副董事长。任中国工程院院士、西北稀有金属材料研究院院长、北京科技大学兼职教授、中南大学兼职教授、宁夏大学兼职教授、宁夏回族自治区人大常委会委员。现任本公司董事。";
		String str2 = "董焱女士：历任天津泰达城市开发有限公司财务部职员、主管、副经理。现任天津泰达集团有限公司风险控制中心合规内审部经理。";
		String str3 = "邓迎春大专会计师,历任黑龙江龙涤集团有限公司财务科科长，黑龙江龙涤股份有限公司财务科科长，现任龙涤股份有限公司副总会计师兼财务部经理。";
		k.cut(ss, 1);
	}
	
public NYLP()
{
	
}
public void cut(String str, int flag) throws IOException
{
	HMM qudong = new HMM(); //**************************************驱动程序更换之处**************************************
	//Character_Base_Probability qudong = new Character_Base_Probability();
	int leng = str.length();
	String finalresult[] = new String[leng];
	int finalindex = 0;
	String []temp = str.split("？|。|！");
	for(int i = 0; i < temp.length; i++)
	{
		N_gram ngram = new N_gram();
	    String [][]result = ngram.WordSegment(temp[i], 10);//***************************************************二位数组 记录句子所有分词粗分集的结果
	    double probability[] = new double[result.length];
	    for(int j = 0 ;j < result.length ; j++) //result.length为二维数组的行数
	    {
	    	//HMM qudong = new HMM(); //**************************************驱动程序更换之处**************************************
	    	//Character_Base_Probability qudong = new Character_Base_Probability();
	    	int realnum;
	    	for(realnum = 0 ; realnum < result[0].length; realnum++)//统计字符串数组中有效的字符串个数
	    	{
	    		if(result[j][realnum] != null)
	    			realnum++;
	    		else break;
	    	}
	    	String []juzi = new String[realnum - 1]; //juzi记录的是每一个句子的有效部分，其余部分是null 就不记录在juzi中
	    	//realnum 记录着每一个粗分结果的有效的分词数
	    	for(int m = 0; m < realnum - 1; m++)
	    	{
	    		juzi[m] = result[j][m];
	    		System.out.print(juzi[m]+" ");
	    	}
	    	System.out.println("                       "+juzi.length);
	    	probability[j] = qudong.getProbability(juzi);//获取juzi中的分词结果的概率
	    }
	    double max = -1;
	    int index = -1;
	    for(int j = 0 ;j < result.length ; j++) //统计分词结果的概率，获取最大值
	    {
	    	if(probability[j] > max)
	    	{
	    		max = probability[j];
	    		index = j; 
	    	}
	    }
		for(int num = 0 ; num < result[0].length; num++)//统计字符串数组中有效的字符串个数
		{
			if(result[index][num] != null)
			{
				finalresult[finalindex++] = result[index][num];
			}
    		else break;
		} 		
	}
	//finalresult[] 记录了所有的分词结果    finalindex为长度
	String rr[] = new String [finalindex];
	for(int i = 0 ; i < finalindex; i++)
	{
		//System.out.print(finalresult[i]+"  ");
		rr[i] = finalresult[i];
	}
	String rrpos[] = qudong.getresult(rr);
	System.out.println("\r\n\r\n第两层隐马尔科夫模型解码识别后的分词结果为：");
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
		System.out.println("开启第三层条件随机场模型解码识别实体机构后的分词结果为： ");
		CRFPoser y = new CRFPoser();
		y.CRFpos();
	}
	System.out.println("\r\n");
	System.out.println("HANLP分词器分词结果:");	
	Segment segment = HanLP.newSegment().enableOrganizationRecognize(true);
	List<Term> termList = segment.seg(str);
	String alpha = termList.toString();
	alpha = alpha.substring(1,alpha.length()-1);
	System.out.println(alpha);
}


}
