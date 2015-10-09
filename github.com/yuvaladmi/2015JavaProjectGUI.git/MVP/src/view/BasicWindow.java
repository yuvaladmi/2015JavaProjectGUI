package view;

import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
/**
 * 
 * @author Yuval Admi & Afek Ben Simon
 * This class open a basic window with a basic data
 *
 */
public abstract class BasicWindow implements Runnable {

	protected Display display;
	protected Shell shell;

	protected DisposeListener exitDispose;
	
/**
 * CTOR
 * @param title
 * @param width
 * @param height
 */
	public BasicWindow(String title, int width, int height) {
		display = new Display();
		shell = new Shell(display);
		shell.setSize(width, height);
		shell.setText(title);
	}
	/**
	 * This method display a popUp window with a messege
	 * @param str
	 */
	public abstract void displayPopUp(String str);
	/**
	 * This abstract method should insert widgets to the window
	 */
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
	
	public DisposeListener getExitDispose() {
		return exitDispose;
	}

	public void setExitDispose(DisposeListener exitDispose) {
		this.exitDispose = exitDispose;
		shell.addDisposeListener(exitDispose);
	}
	





}
