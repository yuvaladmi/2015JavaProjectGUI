package view;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;
import properties.MessegeWindow;
/**
 * 
 * @author Yuval Admi & Afek Ben Simon
 * This class is responsible to start the project in a GUI mode
 * */
public class MyObservableGUIView extends MyAbstractObservableGuiView {

	protected MazeWindow window;
/**
 * CTOR
 * sets all the listeners
 * @param title
 * @param width
 * @param height
 */
	public MyObservableGUIView(String title, int width, int height) {
		window = new MazeWindow(title, width, height);
		//Sets the selectionListener who responsible to generate the maze
		window.setGenerateListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers(("generate 3d maze").split(" "));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		//Sets the selectionListener who responsible to solve the maze
		window.setSolveListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers(("solve").split(" "));

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
		//Sets the selectionListener who responsible to close the program
		window.setExitListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				window.shell.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		//Sets the selectionListener who responsible to the properties
		window.setPropertiesListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						MessegeWindow wm = new MessegeWindow("Properties", 300, 300);
						wm.run();
						Properties result = new Properties();
						try {
							XMLDecoder xmlD = new XMLDecoder(
									new BufferedInputStream(new FileInputStream("properties.xml")));
							result = (Properties) xmlD.readObject();
							xmlD.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						setChanged();
						notifyObservers(result);
					}
				}).start();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		//Sets the selectionListener who responsible to save the mazes
		window.setSaveListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String fileName = window.getFileName();
				setChanged();
				notifyObservers(("save "+fileName).split(" "));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		//Sets the selectionListener who responsible to load of mazes
		window.setLoadListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String fileName = window.getFileName();
				fileName = fileName.replace("\\", "/");
				String[] file = (fileName.split("/"));
				setChanged();
				notifyObservers(("load "+fileName+" "+file[file.length-1]).split(" "));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		//Sets the disposeListener
		window.setExitDispose(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				setChanged();
				notifyObservers(("exit GUI").split(" "));

			}
		});
		//Sets the keyListener who responsible to the movements of the characters
		window.setKey(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {

				switch (arg0.keyCode) {
				case SWT.ARROW_UP:
					setChanged();
					notifyObservers(("move BACKWARD").split(" "));
					System.out.println("Up");
					break;
				case SWT.ARROW_DOWN:
					setChanged();
					notifyObservers(("move FORWARD").split(" "));
					System.out.println("Down");
					break;
				case SWT.ARROW_RIGHT:
					setChanged();
					notifyObservers(("move RIGHT").split(" "));
					System.out.println("Right");
					break;
				case SWT.ARROW_LEFT:
					setChanged();
					notifyObservers(("move LEFT").split(" "));
					System.out.println("Left");
					break;
				case SWT.PAGE_UP:
					setChanged();
					notifyObservers(("move UP").split(" "));
					System.out.println("LVLUp");
					break;
				case SWT.PAGE_DOWN:
					setChanged();
					notifyObservers(("move DOWN").split(" "));
					System.out.println("Down");
					break;
				}
			}
		});

	}

	@Override
	public void start() {
		window.run();
	}

	

	@Override
	public void displayByte(byte[] arr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayString(String arr) {
		window.displayPopUp(arr);

	}

	@Override
	public void displayInt(int[][] arr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayMaze(Maze3d sendGame) {
		window.setCurrentMaze(sendGame);

	}

	@Override
	public void displayPosition(Position p) {
		window.setCurrentPosition(p);

	}

	@Override
	public void displaySolution(Solution<Position> s) {
		window.setCurrentSolution(s);

	}

}
