package au.edu.usyd.it.combineGlobalFeatures;
import java.io.*;
import java.util.Scanner;

/*
 * combine each frame's cedd and fcth attribute and store into a new file, which is named end with ".combine"
 * 
 * 
 * 
 * 
 * */
public class CombineGlobalFeatures 
{

	static String fileDir = "/Users/natechen/Desktop/keyframe/globalFeatures/";
	static String saveToDir = "";
	
	final static int CEDDLENGTH = 146;
	final static int FCTHLENGTH = 194;
	/*
	 * input:
	 * @param: fileDir -> directory, where all .cedd and .fcth files are stored
	 * 
	 * output:
	 * ".combine" file for each frame
	 * 
	 * */
	public void combineGlobalFeatures(String fileDir) 
	{
		System.out.println("Combining '.cedd' and '.fcth' files for all frames....");
		File dir = new File(fileDir);
		for(String str : dir.list())
		{
			if(str.endsWith(".cedd"))
			{
				try
				{
					String fileName = str.substring(0, str.lastIndexOf(".cedd"));
					File fcthFile = new File(fileDir + fileName + ".fcth");
					Scanner scan = new Scanner(fcthFile);
					String fcthContent = "";
					while (scan.hasNext())
					{
						fcthContent = scan.nextLine();
					}
					File ceddFile = new File(fileDir + str);
					Scanner scn = new Scanner(ceddFile);
					String ceddContent = "";
					while (scn.hasNext())
					{
						ceddContent = ceddContent + scn.nextLine();;
					}
					String [] cedd = new String[CEDDLENGTH];
					cedd = ceddContent.split(" ");
					System.err.println(cedd[0]);
					
					String [] fcth = new String[FCTHLENGTH];
					fcth = fcthContent.split(" ");
					String result = "";
					for(int i= 2; i < CEDDLENGTH; i++)
					{
						result = result + cedd[i] + " ";
					}
//					result = result + "\n";
					for(int i = 2; i < FCTHLENGTH; i++)
					{
						result = result + fcth[i] + " ";
					}
					File combination = new File(fileDir + fileName + ".combine");
					PrintWriter out = new PrintWriter(combination);
					out.println(result);
					out.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		System.out.println("'.cedd' and '.fcth' files for all frames have been combined");
	}
}
