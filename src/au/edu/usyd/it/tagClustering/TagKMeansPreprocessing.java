package au.edu.usyd.it.tagClustering;
import java.util.*;
import java.io.*;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import au.edu.usyd.it.siftClusteringKmeans.LocalFeatureKmeans;
import au.edu.usyd.it.siftClusteringKmeans.Matrix;



public class TagKMeansPreprocessing 
{
	static ArrayList<Tag> tags = new ArrayList<Tag>();
	static final int NUMCLUSTERS = 2;
	
	static final String dir = "/Users/natechen/Desktop/untitled folder/";
	public static void main(String[] args)	 
	{
		System.out.println("Performing Kmeans on tags' information....");
		PrintWriter clustering = null;
		HashSet<String> hash = new HashSet<String>();
		File directory = new File(dir);
		LocalFeatureKmeans lfk = new LocalFeatureKmeans();
		try
		{
			clustering = new PrintWriter(dir + "tagClustering.arff");
			clustering.println("@relation TAGClustering");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		for(String str : directory.list())
		{
			if(str.endsWith(".xml"))
			{
				File file = new File(dir + str);
				System.out.println(file.getName());
				parseXML(file);
			}
		}
		for(int i = 0; i < tags.size(); i++)
		{
			clustering.println("@attribute a" + i + " real");
		}
		clustering.println("@data");
		for(int i = 0; i < tags.size(); i++)
		{
			String line = tags.get(i).getKeywords().replace(", ", " ");
			String[] arr = line.split(" ");
			tags.get(i).setArr(arr);
			for(int j = 0; j < arr.length; j++)
			{
				hash.add(arr[j]);
			}
		}
		
//		google+%E4%BD%A0%E5%A5%BD+is+fast
//		String[] trans = (String[])hash.toArray();
		String str1 = hash.toString();
		System.out.println(str1);
		String[] trans = str1.substring(1, str1.lastIndexOf("]")).split(", ");
		
		String translate = " ";
		String[] english;
		Translate tr = new Translate();
		for(int i = 0; i < trans.length; i++)
		{
			System.out.println();
			System.err.println(trans[i]);
			english = tr.translate(trans[i]);
			translate = translate + " " + english;
			english = null;
		}
		
		hash = new HashSet<String>();
		String[] tra = translate.split(" ");
		for(int i = 0; i < tra.length; i++)
		{
			hash.add(tra[i]);
		}
		System.out.println();
//		Matrix matrix = new Matrix(hash.size(), tags.size());
		double[][] mat = new double[hash.size()][tags.size()];
//		String[] str = (String[]) hash.toArray();
		str1 = hash.toString();
		String[] str = str1.substring(1, str1.lastIndexOf("]")).split(",");
		double[] store = new double[hash.size()];
		double[] distri = new double[hash.size()];
		int total = 0;
		for(int i = 0; i < tags.size(); i++)
		{
			for(int j = 0; j < hash.size(); j++)
			{
				store[j] = numOfMatch(str[j], tags.get(i).getArr());
				mat[j][i] = store[j];
//				total = store[j] + total;
			}
//			for(int j = 0; j < hash.size(); j++)
//			{
//				 distri[j] = store[j] / total;
//			}
//			matrix.setMatrix(i, store);
			
		}
		double[][] score = lfk.scoreMatrix(mat, tags.size(), hash.size());
		String result = "";
		for(int i = 0; i < tags.size(); i++)
		{
			for(int j = 0; j < hash.size(); j++)
			{
				result = result + score[j][i] + ",";
			}
			clustering.append(result.substring(0, result.lastIndexOf(",")));
			result = "";
		}
		
		clustering.close();
		try
		{
			SimpleKMeans k = new SimpleKMeans();
			DataSource src = new DataSource(dir + "tagClustering.arff");
			Instances in = src.getDataSet();
			k.setNumClusters(NUMCLUSTERS);
			k.setPreserveInstancesOrder(true);
			k.buildClusterer(in);
			int[] arr = k.getAssignments();
			for(int i = 0; i < tags.size(); i++)
			{
				System.out.println(tags.get(i) + "\t" + arr[i]);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	/*
	 * parse .xml files one by one
	 * 
	 * store into tag format and put it into an arraylist
	 * 
	 * */
	
	private static void parseXML(File xmlFile)
	{
		Tag tag = new Tag();
		tag.setName(xmlFile.getName());
		String keywords = xmlFile.getName();
		try
		{
			Scanner scan = new Scanner(xmlFile);
			while(scan.hasNext())
			{
				String line = scan.nextLine();
				if(line.contains("<Keywords>") || line.contains("<Title>"))
				{
					String str = line.substring(line.indexOf(">") + 1, line.lastIndexOf("</"));
					
							
					keywords = keywords + " " +  str;
				}
			}
			tag.setKeywords(keywords);
			tags.add(tag);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static double numOfMatch(String str, String[] arr)
	{
		double num = 0;
		for(int i = 0; i < arr.length; i++)
		{
			if(str.trim().equalsIgnoreCase(arr[i].trim()))
			{
				num++;
			}
		}
		if(num == 0)
		{
			for(int i = 0; i < arr.length; i++)
			{
				if(str.trim().contains(arr[i].trim()))
				{
					num++;
				}
			}
		}
		return num;
	}

}
