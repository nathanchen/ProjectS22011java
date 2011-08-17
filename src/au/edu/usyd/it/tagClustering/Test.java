package au.edu.usyd.it.tagClustering;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test 
{

	public static void main(String[] args) 
	{
//		String input = "中文语言fd何as最近几天dafds工作dfds效率不高fdsf番dfsfdsa啊"; 
//		String temp = null; 
//		Pattern p = Pattern.compile("[u4E00-u9FA5]+"); 
//		Matcher m = p.matcher(input); 
//		while (m.find()) 
//		{ 
//			temp = m.group(0); 
//			System.out.println(temp + ":" + temp.length()); 
//		} 

		String str = "rwewe*rq-dfd+f+gs!sdsd?grgr|:";
		char[] ch = null;
		String string = str;
		for(int i = 0; i < str.length(); i++)
		{
			if((str.charAt(i) < 64) || (str.charAt(i) > 90 && str.charAt(i) < 97) || (str.charAt(i) > 122))
			{
				ch = str.toCharArray();
				ch[i] = 32;
				str = "" + ch.toString();
				System.err.println(ch);
			}
		}
		System.out.println(str);
	}

}
