package au.edu.usyd.it.moveShotFiles;
import java.io.*;
import java.util.*;

public class MoveShotFiles 
{
	static final String dir = "C:\\YouTube_Videos\\completed_Japan_Earthquake_2011\\Shots\\";
	static final String destination = "C:\\Users\\!shiyang\\Desktop\\destination\\completed_Japan_Earthquake_2011\\";
	public static void main(String[] args) 
	{
		File directory = new File(dir);
		for(String str : directory.list())
		{
			File sub = new File(dir + str);
			System.err.println(dir + str);
			
			/*
			 * C:\Documents and Settings\qche7764\Desktop\New Folder\-09nbtTZavvo.flv
			 * str -> -09nbtTZavvo.flv
			 * 
			 * */
			/*
			 * fileName = --HRtcsLuG3U.mp4_shotNum_keyrame.jpg	
			 * 
			 * */
			for(String s : sub.list())
			{
				File shot = new File(dir + str + "\\" + s);
				
				/*
				 * C:\Documents and Settings\qche7764\Desktop\New Folder\-09nbtTZavvo.flv\shot0
				 * s -> shot0
				 * 
				 * */
				int shotNum = 0;
				for(String st : shot.list())
				{
					File file = new File(dir + str + "\\" + s + "\\" + st);
					file.renameTo(new File(destination + str + "_"+ s.substring(s.lastIndexOf("_") + 1,s.length()) + "_" + shotNum + ".ppm"));
					System.out.println(destination + str + "_"+ s.substring(s.lastIndexOf("_") + 1,s.length()) + "_" + shotNum + ".ppm");
					shotNum ++;
				}
			}
		}
	}
}
