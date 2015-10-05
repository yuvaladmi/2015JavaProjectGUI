package properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class MessegeWindow extends BasicWindow {

    public MessegeWindow(String title, int width, int height) {
	super(title, width, height);
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

	new Label(shell, SWT.NONE).setText("Choose solving algorithm:");
	final Combo icons = new Combo(shell, SWT.DROP_DOWN | SWT.PUSH | SWT.BORDER);
	icons.add("BFS");
	icons.add("AStar");
	icons.select(0);
	
	Button okButton = new Button(shell, SWT.PUSH);
	okButton.setText("Confirm");
	okButton.addSelectionListener(new SelectionListener() {
	    
	    @Override
	    public void widgetSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	    }
	    
	    @Override
	    public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	    }
	});
    }

}
