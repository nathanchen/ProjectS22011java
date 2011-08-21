package au.edu.usyd.it.globalFeatureClusteringKmeans;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import org.apache.commons.io.FileUtils;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/*
 * perform kmeans on Global features using Weka api
 * manually change from .arff to .cedd 
 * 
 * 
 * */
public class GlobalFeatureKmeans 
{
	/*
	 *  -N <num>
	 *  number of clusters.(default 2).
	 *  
	 *  -V
	 *  Display std. deviations for centroids.
	 *  
	 *  -M
	 *  Replace missing values with mean/mode.
	 *  
	 *  -S <num>
	 *  Random number seed. (default 10)
	 *  
	 *  -A <classname and options>
	 *  Distance function to be used for instance comparison (default weka.core.EuclidianDistance)
	 *  
	 *  -I <num>
	 *  Maximum number of iterations. 
	 *  
	 *  -O
	 *  Preserve order of instances.
	 * */
	static final int NUMCLUSTERS = 6;
	static final String DIR = "/Users/natechen/Desktop/keyframe/globalFeatures/";
	static final String SCREENSHOT = "/Users/natechen/Desktop/keyframe/";
	/*
	 * input:
	 * @param: ceddAll.arff or fcthAll.arff
	 * @param: logfile, which indicates the order of data in the ceddAll/fcth.arff file
	 * output:
	 * 
	 * move the original _0.cedd files or _0.fcth files to new folders, which represents each cluster
	 * (delete the original files)
	 * */
//	public static void main(String[] args)
	public void globalFeatureKmeans(String DIR, int NUMCLUSTERS, String imageFiles) 
	{
		String filename = DIR + "ceddAll.arff";
		String LOGFILE = DIR + "ceddLog.txt";
		String clusterDis = DIR + "clusterDis.txt";
		File clusterDisFile = new File(clusterDis);
		ArrayList<Distribution> al = new ArrayList<Distribution>();
		SimpleKMeans sk = new SimpleKMeans();
		try
		{
			
			/*
			 * Create new folders to store final results
			 * 
			 * */
			for(int i = 1; i <=NUMCLUSTERS; i++)
			{
				File file = new File(DIR + "cluster " + i);
				if(!file.exists())
				{
					file.mkdir();
				}
			}
			
			
			DataSource source = new DataSource(filename);
			Instances instances = source.getDataSet();
			
			sk.setPreserveInstancesOrder(true);
			sk.setNumClusters(NUMCLUSTERS);
			sk.buildClusterer(instances);
			System.out.println(sk.toString());
			int[] arr = sk.getAssignments();
			File log = new File(LOGFILE);
			Scanner scn = new Scanner(log);
			int n = 0;
			while(scn.hasNext())
			{
				String fileName = scn.nextLine();
				File file = new File(DIR + fileName);
				File des = new File(DIR + "cluster " + arr[n]);
//				FileUtils.moveFileToDirectory(file, des, true);
				Distribution d = new Distribution();
				d.setThumbnailLoc(imageFiles + fileName.substring(0, fileName.lastIndexOf("_0.cedd")) + "_2.jpg");
				d.setWhichCluster(arr[n]);
				al.add(d);
				n++;
			}
			
			System.err.println(al.size());
			PrintWriter pw = new PrintWriter(clusterDisFile);
			mergesort(al,0, al.size() - 1);
			pw.println("This is cluster 0");
			boolean newCluster = false;
			pw.println("Tags are abc abc");
			for(int i = 0; i < al.size() - 1; i++)
			{
				if(newCluster )
				{
					pw.println("END");
					pw.println("This is cluster " + al.get(i).getWhichCluster());
					/*
					 * tags' info to be changed
					 * 
					 * */
					pw.println("Tags are abc abc");
					newCluster = false;
				}
				if(!newCluster)
				{
					pw.println(al.get(i).getThumbnailLoc() + " " + al.get(i).getVideoFileLoc());
				}
				if(al.get(i).getWhichCluster() != al.get(i + 1).getWhichCluster())
				{
					newCluster = true;
				}
			}
			pw.println("END");
			pw.close();
			
//			for(int i : sk.getAssignments())
//			{
//				System.out.println(i);
//			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void mergesort(ArrayList<Distribution> al, int low, int high)
	{
		if(low < high)
		{
			int middle = (low + high) / 2;
			mergesort(al, low, middle);
			mergesort(al, middle + 1, high);
			merge(al, low, middle, high);
		}
	}
	
	private static void merge(ArrayList<Distribution> al, int low, int middle, int high)
	{
		ArrayList<Distribution> helper = new ArrayList<Distribution>();
		for(Distribution d : al)
		{
			helper.add(d);
		}
		
		int i = low;
		int j = middle + 1;
		int k = low;
		
		while(i <= middle && j <= high)
		{
			if(helper.get(i).getWhichCluster() <= helper.get(j).getWhichCluster())
			{
				al.set(k, helper.get(i));
				i++;
			}
			else
			{
				al.set(k, helper.get(j));
				j++;
			}
			k++;
		}
		
		while(i <= middle)
		{
			al.set(k, helper.get(i));
			k++;
			i++;
		}
		helper = null;
	}
}
