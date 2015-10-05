package view;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public class MazeWindow extends BasicWindow {

	protected Maze3d currentMaze;
	protected Position currentPosition;
	protected ArrayList<Maze3dViewDisplayer> mazeWidgetDisplayer;
	Button startButton;
	Button solveButton;
	Button upButton;
	Button downButton;
	Button leftButton;
	Button rightButton;

	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
		// startButton = new Button(shell, SWT.PUSH);
		// solveButton = new Button(shell, SWT.PUSH);
		upButton = new Button(shell, 0);
//		downButton = new Button(shell, SWT.ARROW | SWT.DOWN);
//		leftButton = new Button(shell, SWT.ARROW | SWT.LEFT);
//		rightButton = new Button(shell, SWT.ARROW | SWT.RIGHT);

		this.mazeWidgetDisplayer = new ArrayList<Maze3dViewDisplayer>();
		widgetRefresh();
	}

	public Maze3d getCurrentMaze() {
		return currentMaze;
	}

	public void setCurrentMaze(Maze3d currentMaze) {
		this.currentMaze = currentMaze;
		widgetRefresh();
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = currentPosition;
		widgetRefresh();
	}

	public void setKeyListener(KeyListener key) {
		this.key = key;
	}

	private void widgetRefresh() {
		for (Maze3dViewDisplayer display : mazeWidgetDisplayer) {
			if (currentMaze != null)
				display.setMazeData(currentMaze);
			if (currentPosition != null)
				display.setPositionData(currentPosition);
		}
	}

	@Override
	public void initWidgets() {
		shell.setLayout(new GridLayout(3, false));

		menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");

		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveItem.setText("&Save");

		fileLoadItem = new MenuItem(fileMenu, SWT.PUSH);
		fileLoadItem.setText("&Load");

		fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("&Exit");

		gameMenu = new Menu(shell, SWT.DROP_DOWN);
		gameMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		gameMenuHeader.setText("&Game");
		gameMenuHeader.setMenu(gameMenu);
		gameGenerateItem = new MenuItem(gameMenu, SWT.PUSH);
		gameGenerateItem.setText("&Generate");

		gameSolveItem = new MenuItem(gameMenu, SWT.PUSH);
		gameSolveItem.setText("&Solve");

		helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		helpMenuHeader.setText("&Help");

		helpMenu = new Menu(shell, SWT.DROP_DOWN);
		helpMenuHeader.setMenu(helpMenu);

		helpGetHelpItem = new MenuItem(helpMenu, SWT.PUSH);
		helpGetHelpItem.setText("&Get Help");

		fileExitItem.addSelectionListener(exitListener);
		gameGenerateItem.addSelectionListener(generateListener);
		gameSolveItem.addSelectionListener(solveListener);

		shell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				event.doit = true;
			}
		});
		
		Maze3dViewDisplayer maze = new Maze3D(shell, SWT.BORDER,'x');
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		this.mazeWidgetDisplayer.add(maze);
		
		upButton.addKeyListener(key);
		
		
	}

	public void setCharacterPosition(int x, int y, int z) {
		if (x >= 0 && x < currentMaze.getX() && y >= 0 && y < currentMaze.getY() && z >= 0 && z < currentMaze.getZ()) {
			if (currentMaze.getMaze3d()[x][y][z] == 0) {
				currentPosition.setX(x);
				currentPosition.setY(y);
				currentPosition.setZ(z);
				widgetRefresh();
			}
		}
	}

	public void moveUp() {
		int x = currentPosition.getX();
		x++;
		if (x < currentMaze.getX())
			setCharacterPosition(x, currentPosition.getY(), currentPosition.getZ());

	}

	public void moveDown() {
		int x = currentPosition.getX();
		x--;
		if (x >= 0)
			setCharacterPosition(x, currentPosition.getY(), currentPosition.getZ());

	}

	public void moveLeft() {
		int z = currentPosition.getZ();
		z--;
		if (z >= 0)
			setCharacterPosition(currentPosition.getX(), currentPosition.getY(), z);
	}

	public void moveRight() {
		int z = currentPosition.getZ();
		z++;
		if (z < currentMaze.getZ())
			setCharacterPosition(currentPosition.getX(), currentPosition.getY(), z);

	}

	public void moveForward() {
		int y = currentPosition.getY();
		y++;
		if (y < currentMaze.getY())
			setCharacterPosition(currentPosition.getX(), y, currentPosition.getZ());
	}

	public void moveBackward() {
		int y = currentPosition.getY();
		y--;
		if (y >= 0)
			setCharacterPosition(currentPosition.getX(), y, currentPosition.getZ());

	}

	@Override
	public void displayPopUp(String str) {
		MessageBox popUpWindow = new MessageBox(shell, SWT.ICON_INFORMATION);
		popUpWindow.setText("pop");
		popUpWindow.setMessage(str);
		popUpWindow.open();

	}

}
