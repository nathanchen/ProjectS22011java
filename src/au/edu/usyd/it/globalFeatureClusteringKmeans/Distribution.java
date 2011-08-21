package au.edu.usyd.it.globalFeatureClusteringKmeans;

public class Distribution 
{

	int whichCluster;
	String thumbnailLoc;
	String videoFileLoc;
	
	public int getWhichCluster() {
		return whichCluster;
	}
	public void setWhichCluster(int whichCluster) {
		this.whichCluster = whichCluster;
	}
	public String getThumbnailLoc() {
		return thumbnailLoc;
	}
	public void setThumbnailLoc(String thumbnailLoc) {
		this.thumbnailLoc = thumbnailLoc;
	}
	public String getVideoFileLoc() {
		return videoFileLoc;
	}
	public void setVideoFileLoc(String videoFileLoc) {
		this.videoFileLoc = videoFileLoc;
	}
}
