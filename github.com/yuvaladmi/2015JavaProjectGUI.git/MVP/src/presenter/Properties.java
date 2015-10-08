package presenter;

import java.io.Serializable;

public class Properties implements Serializable {

	String name;
	String solvingAlgo;
	String viewStyle;
	int numOfThreads;
	int sizeX;
	int sizeY;
	int sizeZ;
	char pivot;
	
	public char getPivot() {
		return pivot;
	}
	public void setPivot(char pivot) {
		this.pivot = pivot;
	}
	public String getViewStyle() {
		return viewStyle;
	}
	public void setViewStyle(String viewStyle) {
		this.viewStyle = viewStyle;
	}
	public String getName() {
	    return name;
	}
	public void setName(String name) {
	    this.name = name;
	}
	public String getSolvingAlgo() {
		return solvingAlgo;
	}
	public void setSolvingAlgo(String solvingAlgo) {
		this.solvingAlgo = solvingAlgo;
	}
	public int getNumOfThreads() {
		return numOfThreads;
	}
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}
	public int getSizeX() {
		return sizeX;
	}
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}
	public int getSizeY() {
		return sizeY;
	}
	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}
	public int getSizeZ() {
		return sizeZ;
	}
	public void setSizeZ(int sizeZ) {
		this.sizeZ = sizeZ;
	}

	
	
	
}
