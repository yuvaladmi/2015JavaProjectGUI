package view;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import properties.MessegeWindow;

public class MazeWindow extends BasicWindow {

	protected Maze3d currentMaze;
	protected Position currentPosition;
	protected Solution<Position> solution;
	protected ArrayList<Maze3dViewDisplayer> mazeWidgetDisplayer;
	Button startButton;
	Button solveButton;
	Button upButton;

	protected Menu gameMenu;
	protected MenuItem gameMenuHeader;
	protected MenuItem  gameGenerateItem,gameSolveItem;
	
	protected SelectionListener generateListener;
	protected SelectionListener solveListener;
	
	protected Menu menuBar, fileMenu;
	protected MenuItem fileMenuHeader;
	protected MenuItem fileExitItem, filePropertiesItem;

	protected Label label;
	protected KeyListener key;
	
	protected SelectionListener exitListener;
	protected SelectionListener propertiesListener;
	
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
	
	public void setCurrentSolution(Solution<Position> solution) {
		this.solution = solution;
		widgetRefresh();
	}

	public void setKeyListener(KeyListener key) {
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


	private void widgetRefresh() {
		for (Maze3dViewDisplayer display : mazeWidgetDisplayer) {
			if (currentMaze != null)
				display.setMazeData(currentMaze);
			if (currentPosition != null)
				display.setPositionData(currentPosition);
			if(solution != null)
				display.setSolutionData(solution);
		}
	}

	@Override
	public void initWidgets() {
		shell.setLayout(new GridLayout(3, false));
//		shell.setBackgroundImage(new Image(display, "resources/red.jpg"));

		menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");

		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		filePropertiesItem = new MenuItem(fileMenu, SWT.PUSH);
		filePropertiesItem.setText("&Properties");

		fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("&Exit");
		filePropertiesItem.addSelectionListener(propertiesListener);
		

		gameMenu = new Menu(shell, SWT.DROP_DOWN);
		gameMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		gameMenuHeader.setText("&Game");
		gameMenuHeader.setMenu(gameMenu);
		gameGenerateItem = new MenuItem(gameMenu, SWT.PUSH);
		gameGenerateItem.setText("&Generate");

		gameSolveItem = new MenuItem(gameMenu, SWT.PUSH);
		gameSolveItem.setText("&Solve");


		fileExitItem.addSelectionListener(exitListener);
		gameGenerateItem.addSelectionListener(generateListener);
		gameSolveItem.addSelectionListener(solveListener);

		
		
		Maze3dViewDisplayer maze = new Maze3D(shell, SWT.BORDER,'x');
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		this.mazeWidgetDisplayer.add(maze);
		maze.setImageString("resources/character.png");
		maze.setImageGoalString("resources/win.png");
		maze.setImageHintString("resources/banana.gif");
		maze.setImageBackroundString("resources/blue.jpg");
		maze.setImageWallString("resources/silver1.gif");
		
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


	@Override
	public void displayPopUp(String str) {
		MessageBox popUpWindow = new MessageBox(shell, SWT.ICON_INFORMATION);
		popUpWindow.setText("pop");
		popUpWindow.setMessage(str);
		popUpWindow.open();

	}
	public SelectionListener getPropertiesListener() {
		return propertiesListener;
	}
	
	public void setPropertiesListener(SelectionListener propertiesListener) {
		this.propertiesListener = propertiesListener;
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

}
