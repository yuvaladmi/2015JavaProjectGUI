package boot;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.Maze3dModel;
import presenter.Presenter;
import presenter.Properties;
import properties.BasicWindow;
import properties.MessegeWindow;
import view.Maze3dViewCLI;
import view.MyObservableGUIView;
import view.abstractView;

public class Run {
	public static void main(String[] args) {
		Thread propWindow = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				BasicWindow window = new MessegeWindow("Properties", 300, 300);
				window.run();	
			}
		});
		
		Thread mainWindow = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					propWindow.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// TODO Auto-generated method stub
				Properties result = new Properties();
		try {
		    XMLDecoder xmlD = new XMLDecoder(new BufferedInputStream(new FileInputStream("properties.xml")));
		    result = (Properties) xmlD.readObject();
		    xmlD.close();
		} catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		Maze3dModel m = new Maze3dModel();
		m.setProperties(result);
		abstractView view = null;
		switch (result.getViewStyle()) {
		case "GUI":
			view = new MyObservableGUIView("MyMaze", 500, 300);
			break;
		case "CLI":
			view = new Maze3dViewCLI(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out));
			break;
		default:
			break;
		}
		Presenter p = new Presenter(m, view);
		m.addObserver(p);
		view.addObserver(p);
		view.start();
			}
		});
		propWindow.run();
		mainWindow.run();
		
		
	}
}