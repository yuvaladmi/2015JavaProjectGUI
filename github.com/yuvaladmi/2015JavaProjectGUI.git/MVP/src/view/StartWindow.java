package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;

public class StartWindow extends BasicWindow {

	protected Button loadButton;
	protected Button startButton;

	public StartWindow(String title, int width, int height) {
		super(title, width, height);
		this.startButton = new Button(shell, SWT.PUSH);
		this.loadButton = new Button(shell, SWT.PUSH);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initWidgets() {
		shell.setLayout(new GridLayout(2, false));
		

	}

	@Override
	public void displayPopUp(String str) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		StartWindow sw = new StartWindow("start", 500, 300);
		sw.run();
	}

}
