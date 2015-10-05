package view;

import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public abstract class BasicWindow implements Runnable {

	protected Display display;
	protected Shell shell;

	protected Menu menuBar, fileMenu, helpMenu;
	protected MenuItem fileMenuHeader, helpMenuHeader;
	protected MenuItem fileExitItem, fileSaveItem, helpGetHelpItem,fileLoadItem;

	protected Label label;
	protected KeyListener key;
	protected SelectionListener generateListener;

	protected SelectionListener solveListener;
	protected SelectionListener exitListener;
	protected SelectionListener saveListener;
	protected SelectionListener loadListener;

	public SelectionListener getLoadListener() {
		return loadListener;
	}

	public void setLoadListener(SelectionListener loadListener) {
		this.loadListener = loadListener;
	}

	public SelectionListener getExitListener() {
		return exitListener;
	}

	public void setExitListener(SelectionListener exitListener) {
		this.exitListener = exitListener;
	}

	public KeyListener getKey() {
		return key;
	}

	public void setKey(KeyListener key) {
		this.key = key;
	}

	public SelectionListener getGenerateListener() {
		return generateListener;
	}

	public void setGenerateListener(SelectionListener startKey) {
		this.generateListener = startKey;
	}

	public SelectionListener getSolveListener() {
		return solveListener;
	}

	public void setSolveListener(SelectionListener solveKey) {
		this.solveListener = solveKey;
	}

	public SelectionListener getSaveListener() {
		return saveListener;
	}

	public void setSaveListener(SelectionListener saveListener) {
		this.saveListener = saveListener;

	}

	public BasicWindow(String title, int width, int height) {
		display = new Display();
		shell = new Shell(display);
		shell.setSize(width, height);
		shell.setText(title);
	}

	public abstract void initWidgets();

	@Override
	public void run() {
		initWidgets();
		shell.open();
		// main event loop
		while (!shell.isDisposed()) { // while window isn't closed

			// 1. read events, put then in a queue.
			// 2. dispatch the assigned listener
			if (!display.readAndDispatch()) { // if the queue is empty
				display.sleep(); // sleep until an event occurs
			}

		} // shell is disposed

		display.dispose(); // dispose OS components
	}

	public abstract void moveUp();

	public abstract void moveDown();

	public abstract void moveLeft();

	public abstract void moveRight();

	public abstract void moveBackward();

	public abstract void moveForward();

	public abstract void displayPopUp(String str);

}
