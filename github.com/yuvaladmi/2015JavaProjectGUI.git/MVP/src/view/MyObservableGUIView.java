package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public class MyObservableGUIView extends MyAbstractObservableGuiView {

	public MyObservableGUIView(String title, int width, int height) {
		window = new MazeWindow(title, width, height);
		window.setGenerateListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers(("generate 3d maze myMaze 31 31 31").split(" "));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		window.setSolveListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers(("solve myMaze BFS").split(" "));

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		window.setExitListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers(("exit GUI").split(" "));
				window.shell.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		window.setSaveListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers(("save zip").split(" "));

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		window.setLoadListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers(("load zip").split(" "));

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		window.setKey(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				switch (arg0.keyCode) {
				case SWT.ARROW_UP:
					setChanged();
					notifyObservers("move UP myMaze");
					break;
				case SWT.ARROW_DOWN:
					setChanged();
					notifyObservers("move DOWN myMaze");
					break;
				case SWT.ARROW_RIGHT:
					setChanged();
					notifyObservers("move RIGHT myMaze");
					break;
				case SWT.ARROW_LEFT:
					setChanged();
					notifyObservers("move LEFT myMaze");
					break;
				case SWT.PAGE_UP:
					setChanged();
					notifyObservers("move FORWARD myMaze");
					break;
				case SWT.PAGE_DOWN:
					setChanged();
					notifyObservers("move BACKWARD myMaze");
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
	public void notifyMessege(String[] arr) {
		// TODO Auto-generated method stub

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
		((MazeWindow) window).setCurrentMaze(sendGame);

	}

	@Override
	public void displayPosition(Position p) {
		((MazeWindow) window).setCurrentPosition(p);

	}

}
