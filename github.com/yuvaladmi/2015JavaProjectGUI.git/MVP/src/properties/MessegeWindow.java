package properties;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import presenter.Properties;

public class MessegeWindow extends BasicWindow {

	protected Properties properties;

	public MessegeWindow(String title, int width, int height) {
		super(title, width, height);
		properties = new Properties();
	}

	@Override
	public void initWidgets() {
		shell.setLayout(new GridLayout(2, false));

		new Label(shell, SWT.NONE).setText("Enter a name:");
		final Text name = new Text(shell, SWT.BORDER);
		name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(shell, SWT.NONE).setText("How many levels:");
		final Text sizeX = new Text(shell, SWT.BORDER);
		sizeX.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(shell, SWT.NONE).setText("How many lines:");
		final Text sizeY = new Text(shell, SWT.BORDER);
		sizeY.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(shell, SWT.NONE).setText("How many columns:");
		final Text sizeZ = new Text(shell, SWT.BORDER);
		sizeZ.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(shell, SWT.NONE).setText("Num of threads:");
		final Text numOfThreads = new Text(shell, SWT.BORDER);
		numOfThreads.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		new Label(shell, SWT.NONE).setText("Choose pivot:");
		final Combo pivot = new Combo(shell, SWT.DROP_DOWN | SWT.PUSH | SWT.BORDER);
		pivot.add("x");
		pivot.add("y");
		pivot.add("z");
		pivot.select(0);

		new Label(shell, SWT.NONE).setText("Choose solving algorithm:");
		final Combo icons = new Combo(shell, SWT.DROP_DOWN | SWT.PUSH | SWT.BORDER);
		icons.add("BFS");
		icons.add("AStar");
		icons.select(0);

		new Label(shell, SWT.NONE).setText("Choose view method:");
		final Combo icons1 = new Combo(shell, SWT.DROP_DOWN | SWT.PUSH | SWT.BORDER);
		icons1.add("GUI");
		icons1.add("CLI");
		icons1.select(0);

		Button okButton = new Button(shell, SWT.PUSH);
		okButton.setText("OK");
		okButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				properties.setName(name.getText());
				if ((Integer.parseInt(sizeX.getText()) % 2 != 0) && (Integer.parseInt(sizeX.getText()) >= 3)) {
					properties.setSizeX(Integer.parseInt(sizeX.getText()));
				} else {
					displayError("Invalid 'x' value");
					properties.setSizeX(7);
				}
				if ((Integer.parseInt(sizeY.getText()) % 2 != 0) && (Integer.parseInt(sizeY.getText()) >= 3)) {
					properties.setSizeY(Integer.parseInt(sizeY.getText()));
				} else {
					displayError("Invalid 'y' value");
					properties.setSizeY(7);
				}
				if ((Integer.parseInt(sizeZ.getText()) % 2 != 0) && (Integer.parseInt(sizeZ.getText()) >= 3)) {
					properties.setSizeZ(Integer.parseInt(sizeZ.getText()));
				} else {
					displayError("Invalid 'z' value");
					properties.setSizeZ(7);
				}
				if ((Integer.parseInt(numOfThreads.getText()) >= 1)) {
					properties.setNumOfThreads(Integer.parseInt(numOfThreads.getText()));
				} else {
					displayError("Invalid number of threads value");
					properties.setNumOfThreads(10);
				}
				properties.setPivot(pivot.getText().charAt(0));
			
				properties.setViewStyle(icons1.getText());

				properties.setSolvingAlgo(icons.getText());
				try {
					XMLEncoder xml = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("properties.xml")));
					xml.writeObject(properties);
					xml.flush();
					xml.close();
				} catch (FileNotFoundException e) {

				}
				shell.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void displayError(String str) {
		MessageBox popUpWindow = new MessageBox(shell, SWT.ICON_INFORMATION);
		popUpWindow.setText("ERROR!");
		popUpWindow.setMessage(str);
		popUpWindow.open();
	}

}
