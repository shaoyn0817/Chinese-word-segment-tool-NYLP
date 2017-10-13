package NYLP;
//一共44个词性
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Convertmatrix {
	public static void main(String []args)
	{
		Convertmatrix a = new Convertmatrix("NYLP\\zhuanyi.txt");
		double [][]test =  a.ProbabilityMatrix();
		for(int i = 0 ; i < test.length; i++)
		{
			for(int j = 0; j < test.length; j++)
			{
			    System.out.println(test[i][j]);	
			}
			System.out.println();
		}
		//double Num = Double.valueOf(s); *********************************读取一个DOUBLE型数
	}
	
final int ciNum = 44;
Poslist dic = new Poslist();
String k[] = new String[ciNum];
public Convertmatrix(String filename)
{
	File file = new File(filename);
	Long filelength = file.length();
	byte[] filecontent = new byte[filelength.intValue()];  
	try {
		FileInputStream in = new FileInputStream(file);
		try {
			in.read(filecontent);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String s = new String(filecontent);
	//System.out.println(s);
    String t[] = s.split(" ");
	for(int i = 0; i < t.length; i++)  //处理掉回车符
	{
		if((i+1) % 3 == 1 && i != 0)
			{
			t[i] = t[i].substring(1);
			}
	}
	k = t;
}

public double[][] ProbabilityMatrix(){
	double result[][] = new double[ciNum][ciNum];
	for(int i = 0; i < k.length-2; i += 3)  //******x在前面，y在后面  转移序列为（x,y）
	{
		double x = dic.cixing_number(k[i]);
		double y = dic.cixing_number(k[i+1]);
		result[(int)x][(int)y] = Double.valueOf(k[i+2]);
	}
	return result;
	
}

}
