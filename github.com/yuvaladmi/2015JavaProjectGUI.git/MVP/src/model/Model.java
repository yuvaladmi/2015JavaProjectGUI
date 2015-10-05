package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public interface Model {
    public void dirToPath(String[] arr);

    public void generateMaze(String[] arr);

    public Maze3d sendGame(String arr);

    public int[][] crossSection(String[] arr);

    public void save(String[] arr);
    
    public void saveZip();

    public void load(String[] arr);
    
    public void loadZip();

    public void solve(String[] arr);

    public String bringSolution(String arr);

    public void gameSize(String[] arr);

    public void fileSize(String[] arr);

    public void close();
    
	public void moveUp(String name);

	public void moveDown(String name);

	public void moveLeft(String name);

	public void moveRight(String name);

	public void moveBackward(String name);

	public void moveForward(String name);

	public Position getHPosition(String name);
   
}
