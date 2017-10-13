package NYLP;

import java.io.*;
import java.util.*;

public class Dictionary{
	private HashMap dic = new HashMap();
	private int maxWordLength = 0;
	private long numbers=0;//词典中词语个数
	
	public Dictionary(File file){//将词典读入HashMap中
		String str;
		try{
		InputStreamReader  in = new InputStreamReader (new FileInputStream(file));
		BufferedReader reader=new BufferedReader(in); 
		while((str=reader.readLine())!= null){
			if(str.length() > maxWordLength)
				maxWordLength = str.length();
			dic.put(str,new Integer(1));
			//测试词典输出内容 if(numbers%1000 == 0)
				//System.out.println(str);
			numbers++;
		}
		//System.out.println(numbers);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public boolean FindWord(String word){//在词典中查找word
		if (dic.get(word) == null)
			return false;
		else
			return true;
	}
	public HashMap getDict(){
		return dic;
	}
	public int getMaxWordLength(){
		return maxWordLength;
	}
	public long getNumbers(){
		return numbers;
	}
}
