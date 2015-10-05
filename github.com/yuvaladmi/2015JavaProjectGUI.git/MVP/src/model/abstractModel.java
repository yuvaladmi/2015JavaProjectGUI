package model;

import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutorService;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public abstract class abstractModel extends Observable implements Model {

	public StringBuilder sb;
	public ExecutorService threadpool;
	int numOfThread;
	public HashMap<String, Maze3d> hMaze;
	public HashMap<String, Position> hPosition;
	public HashMap<Maze3d, Solution<Position>> hSol;

	public abstract void dirToPath(String[] arr);

	public abstract void generateMaze(String[] arr);

	public abstract Maze3d sendGame(String arr);

	public abstract int[][] crossSection(String[] arr);

	public abstract void save(String[] arr);
	
	public abstract void saveZip();

	public abstract void load(String[] arr);
	
	public abstract void loadZip();

	public abstract void solve(String[] arr);

	public abstract String bringSolution(String arr);

	public abstract void gameSize(String[] arr);

	public abstract void fileSize(String[] arr);

	public abstract void close();

	public abstract void moveUp(String name);

	public abstract void moveDown(String name);

	public abstract void moveLeft(String name);

	public abstract void moveRight(String name);

	public abstract void moveBackward(String name);

	public abstract void moveForward(String name);

	public abstract Position getHPosition(String name);
}
