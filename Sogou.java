package NYLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
 
public class Sogou {
 
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\matrix\\��Ʊ����ʿ��ȫ.scel") ;
        FileInputStream input = new FileInputStream(file);
        byte[] str = new byte[128];
        Map<Integer , String> pyDic = new HashMap<Integer,String>() ;
        List<WordLibrary> pyAndWord = new ArrayList<WordLibrary>();
         
         
        byte[] num;
        int hzPosition = 0;
        input.read(str, 0, 128) ;
         
        if (str[4] == 0x44)
        {
            hzPosition = 0x2628;
        }
        if (str[4] == 0x45)
        {
            hzPosition = 0x26C4;
        }
        /********************�ֿ����ƿ�ʼ**************************/
        input.getChannel().position(0x130);
        int lenc = input.read(str, 0, 128);
        System.out.println("�ֿ�����:" + toString(str,lenc));
         
        /********************�ֿ����ƽ���*************************/
 
 
        /********************�ֿ����ʼ**************************/
        input.getChannel().position(0x338);
        lenc = input.read(str, 0, 128);
        System.out.println("�ֿ����:" + toString(str,lenc));
         
        /********************�ֿ�������*************************/
         
        /********************�ֿ���Ϣ��ʼ**************************/
        input.getChannel().position(0x540);
        lenc = input.read(str, 0, 128);
        System.out.println("�ֿ���Ϣ:" + toString(str,lenc));
         
        /********************�ֿ���Ϣ����*************************/
         
        /********************�ֿ�ʾ����ʼ**************************/
        input.getChannel().position(0xd40);
        lenc = input.read(str, 0, 128);
        System.out.println("�ֿ�ʾ��:" + toString(str,lenc));
         
        /********************�ֿ�ʾ������*************************/
         
        input.getChannel().position(0x1540) ;
        str = new byte[4];
        input.read(str, 0, 4);
 
        while (true)
        {
            num = new byte[4];
            input.read(num, 0, 4);
            int mark = num[0] + num[1]*256;
            str = new byte[128];
            if(num[2]>0){
                input.read(str, 0, (num[2]));
                String py = new String(str,0,num[2]);
                py = py.replaceAll("\0", "");
                pyDic.put(mark, py);
                if (py == "zuo") //���һ��ƴ��
                {
                    break;
                }
            }else{
                break ;
            }
        }
        input.getChannel().position(hzPosition);
        int i = 0, count = 0, samePYcount = 0;
        while (true)
        {
            num = new byte[4];
            input.read(num, 0, 4);
            samePYcount = num[0] + num[1]*256;
            count = num[2] + num[3]*256;
            //��������ƴ��
            str = new byte[256];
            for (i = 0; i < count; i++)
            {
                str[i] = (byte) input.read();
            }
            String wordPY = "";
            for (i = 0; i < count/2; i++)
            {
                int key = str[i*2] + str[i*2 + 1]*256;
                wordPY += pyDic.get(key) + " ";
            }
            //������������
            for (int s = 0; s < samePYcount; s++) //ͬ���ʣ�ʹ��ǰ����ͬ��ƴ��
            {
                num = new byte[2];
                input.read(num, 0, 2);
                int hzBytecount = num[0] + num[1]*256;
                str = new byte[hzBytecount];
                int len = input.read(str, 0, hzBytecount);
                String word = toString(str , len);
                pyAndWord.add(new WordLibrary(word , wordPY));
                byte[] temp = new byte[12];
                for (i = 0; i < 12; i++)
                {
                    temp[i] = (byte) input.read();
                }
            }
            if (file.length() == input.getChannel().position()) //�ж��ļ�����
            {
                input.close();
                break;
            }
        }
        String fullFilename = "D://���ڻ���3.txt";
    	File newTextFile = new File(fullFilename);
        FileWriter fw ;
        fw = new FileWriter(newTextFile);
        for (WordLibrary w : pyAndWord)
        {
        	String k = w.getWord();
        	String t = "\r\n";
        	k = k+t;
        	fw.write(k);
            
        }
        fw.close();
    }
    /**
     * 
     * @param data
     * @return
     */
    public static String toString(byte[] data , int len){
        StringBuffer strb = new StringBuffer() ;
        for(int ix=0 ; ix<len ; ix+=2){
            if(data[ix+1]<=0 && data[ix]<=0){
                continue ;
            }
            int d = data[ix]+data[ix+1]*256 ;
            if(data[ix]<=0){
                d = d+256 ;
            }
            if(d!=0){
                strb.append((char)d);
            }
        }
        return strb.toString() ;
    }
}
class WordLibrary
{
    private int count = 1;
    private String[] pinYin;
    private String pinYinString = "";
    private String word;
    public WordLibrary(String word , String pinYinString){
        this.word = word ;
        this.pinYinString = pinYinString ;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String[] getPinYin() {
        return pinYin;
    }
    public void setPinYin(String[] pinYin) {
        this.pinYin = pinYin;
    }
    public String getPinYinString() {
        return pinYinString;
    }
    public void setPinYinString(String pinYinString) {
        this.pinYinString = pinYinString;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
 
}