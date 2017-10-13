package NYLP;

import java.util.HashMap;

public class Poslist {
	private HashMap dic = new HashMap();
	String [] s;
	public Poslist(){
    //String t ="nr ns nt Ag nx nz Vg Tg Rg Ng a b c ad Dg d Bg e f h i j k l m an n vd Yg o vvn p q r s na t u v w vn y z Mg";
    String t ="nr ns nt Ag nx nz Vg Tg Rg Ng a b Dg ad c d Bg e f h i j k l m an n vd Yg o vvn p q r s na t u v w vn y z Mg";
    String k[] = t.split(" ");
    s = k;
    for(int i = 0; i < s.length; i++)
    {
    	dic.put(s[i], new Integer(i));
    }
	}
	public static void main(String []args)
	{
		Poslist p = new Poslist();
		System.out.println(p.cixing_number("v"));
	}
	
	public int cixing_number(String cixing){//在词典中查找词性
		if (dic.get(cixing) == null)
			{
			System.out.println("没有该词性:  " + cixing);
			
		    return 0;
		    }
		int num = (int) dic.get(cixing);
		return num;
	}
	public String number_cixing(int num)
	{
		return s[num];
	}
}
