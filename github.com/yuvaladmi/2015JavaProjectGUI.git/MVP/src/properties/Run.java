package properties;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import presenter.Properties;

public class Run {

    public static void main(String[] args) {
	BasicWindow window = new MessegeWindow("Properties", 300, 300);
	window.run();
	Properties properties = new Properties();
	properties.setDimensions(3);
	properties.setSizeX(9);
	properties.setSizeY(9);
	properties.setSizeZ(9);
	properties.setNumOfThreads(10);
	properties.setSolvingAlgo("BFS");
	try {
	    XMLEncoder xml;
	    xml = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("properties.xml")));
	    xml.writeObject(properties);
	    xml.flush();
	    xml.close();	
	} catch (FileNotFoundException e) {
	}
	try {
	    XMLDecoder xmlD;
	    xmlD = new XMLDecoder(new BufferedInputStream(new FileInputStream("properties.xml")));
	    Properties result = (Properties) xmlD.readObject();
	    xmlD.close();
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
