package presenter;

import java.io.Serializable;
/**
 * 
 * @author Yuval Admi & Afek Ben Simon
 * This class implements Serializable, has Getters & Setters to its parameters
 *
 */
public class Properties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String name;
	String solvingAlgo;
	String viewStyle;
	int numOfThreads;
	int sizeX;
	int sizeY;
	int sizeZ;
	int port;
	String host;
	
	public Properties() {
		this.port=5400;
		this.host="127.0.0.1";
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
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
