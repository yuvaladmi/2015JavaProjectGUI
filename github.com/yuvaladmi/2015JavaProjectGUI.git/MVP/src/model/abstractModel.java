package model;

import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutorService;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

public abstract class abstractModel extends Observable implements Model {

	public StringBuilder sb;
	public ExecutorService threadpool;
	int numOfThread;
	public HashMap<String, Maze3d> hMaze;
	public HashMap<String, Position> hPosition;
	public HashMap<Maze3d, Solution<Position>> hSol;
	protected Properties properties;

	public abstract void dirToPath(String[] arr);

	public abstract void generateMaze(String[] arr);

	public abstract Maze3d sendGame(String str);

	public abstract int[][] crossSection(String[] arr);

	public abstract void save(String[] arr);
	
	public abstract void saveZip();

	public abstract void load(String[] arr);
	
	public abstract void loadZip();

	public abstract void solve(String[] arr);

	public abstract Solution<Position> bringSolution();

	public abstract void gameSize(String[] arr);

	public abstract void fileSize(String[] arr);

	public abstract void close();

	public abstract void moveUp();

	public abstract void moveDown();

	public abstract void moveLeft();

	public abstract void moveRight();

	public abstract void moveBackward();

	public abstract void moveForward();

	public abstract void setProperties(Properties properties);
	
	public abstract Properties getProperties();
	
	public abstract Position getHPosition(String name);
}
