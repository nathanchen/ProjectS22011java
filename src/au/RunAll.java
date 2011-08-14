package au;

import au.edu.usyd.it.ceddfcth.GlobalFeatureGen;
import au.edu.usyd.it.globalFeatureClusteringKmeans.GlobalFeatureKmeans;
import au.edu.usyd.it.globalFeatureClusteringKmeans.GlobalFeatureKmeansMoveFrames;
import au.edu.usyd.it.globalFeatureClusteringKmeans.GlobalFeatureKmeansPreprocessing;
import au.edu.usyd.it.globalFeaturePerShot.GlobalFeat;




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
	final static String globalFeatures = imageFiles + "globalFeatures/";
	final static int NUMCLUSTERS = 2;
	
	
	
	
	public static void main(String[] args) 
	{
		GlobalFeatureGen globalFeatureGen = new GlobalFeatureGen();
		GlobalFeat globalFeat = new GlobalFeat();
		GlobalFeatureKmeansPreprocessing globalFeatureKmeansPreprocessing = new GlobalFeatureKmeansPreprocessing();
		GlobalFeatureKmeans globalFeatureKmeans = new GlobalFeatureKmeans();
		GlobalFeatureKmeansMoveFrames globalFeatureKmeansMoveFrames = new GlobalFeatureKmeansMoveFrames();
		
		
		globalFeatureGen.generateGlobalFeatures(imageFiles);
		globalFeat.globalFeat(globalFeatures);
		globalFeatureKmeansPreprocessing.globalFeatureKmeansPreprocessing(globalFeatures);
		globalFeatureKmeans.globalFeatureKmeans(globalFeatures, NUMCLUSTERS);
		globalFeatureKmeansMoveFrames.globalFeatureKmeansMoveFrames(imageFiles, globalFeatures + "cluster", NUMCLUSTERS);
		
		
	}

}
