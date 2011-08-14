package au;

import au.edu.usyd.it.ceddfcth.GlobalFeatureGen;
import au.edu.usyd.it.globalFeatureClusteringKmeans.GlobalFeatureKmeans;
import au.edu.usyd.it.globalFeatureClusteringKmeans.GlobalFeatureKmeansMoveFrames;
import au.edu.usyd.it.globalFeatureClusteringKmeans.GlobalFeatureKmeansPreprocessing;
import au.edu.usyd.it.globalFeaturePerShot.GlobalFeat;
import au.edu.usyd.it.siftClusteringKmeans.LocalFeatureKmeans;
import au.edu.usyd.it.siftClusteringKmeans.LocalFeatureKmeansPreprocessing;




/*
 * 1. perform cedd, fcth and global feature clustering (kmeans) on images
 * 2. perform sift clustering (tf-idf and then kmeans) on images
 * 
 * 
 * 
 * */
public class RunAll 
{
	/*
	 * the directory that stores image files
	 * */
	final static String imageFiles = "/Users/natechen/Desktop/keyframe/";
	final static String siftFiles = "";
	
	final static String globalFeatures = imageFiles + "globalFeatures/";
	final static int NUMCLUSTERS = 2;
	final static String siftAllFile = siftFiles + "siftAll.arff";
	
	
	
	public static void main(String[] args) 
	{
		GlobalFeatureGen globalFeatureGen = new GlobalFeatureGen();
		GlobalFeat globalFeat = new GlobalFeat();
		GlobalFeatureKmeansPreprocessing globalFeatureKmeansPreprocessing = new GlobalFeatureKmeansPreprocessing();
		GlobalFeatureKmeans globalFeatureKmeans = new GlobalFeatureKmeans();
		GlobalFeatureKmeansMoveFrames globalFeatureKmeansMoveFrames = new GlobalFeatureKmeansMoveFrames();
		LocalFeatureKmeansPreprocessing localFeatureKmeansPreprocessing = new LocalFeatureKmeansPreprocessing();
		LocalFeatureKmeans localFeatureKmeans = new LocalFeatureKmeans();
		
		globalFeatureGen.generateGlobalFeatures(imageFiles);
		globalFeat.globalFeat(globalFeatures);
		globalFeatureKmeansPreprocessing.globalFeatureKmeansPreprocessing(globalFeatures);
		globalFeatureKmeans.globalFeatureKmeans(globalFeatures, NUMCLUSTERS);
		globalFeatureKmeansMoveFrames.globalFeatureKmeansMoveFrames(imageFiles, globalFeatures + "cluster", NUMCLUSTERS);
		localFeatureKmeansPreprocessing.localFeatureKmeansPreprocessing(siftFiles);
		localFeatureKmeans.localFeatureKmeans(siftFiles, NUMCLUSTERS);
	}
}
