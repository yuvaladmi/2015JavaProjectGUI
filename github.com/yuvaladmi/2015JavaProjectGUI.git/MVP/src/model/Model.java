package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

public interface Model {
    public void dirToPath(String[] arr);
    /**
	 * This method create a new Maze3d in a thread. All the mazes saved in an
	 * HashMap.
	 */
    public void generateMaze(String[] arr);
    /**
	 * This method gets a Maze name and sends the presenter this maze.
	 * @return Maze3d
	 */
    public Maze3d sendGame(String str);
    /**
	 * This method gets a name, pivot and index of a maze and make a 2D array
	 * from the parameters it gets
	 * 
	 * @return the presenter a CrossSection of the maze
	 */
    public int[][] crossSection(String[] arr);
    /**
	 * This method gets a name of a file and a maze and save this maze in the
	 * file.
	 */
    public void save(String[] arr);
    /**
     * This method save the hashMaps in a Zip file
     */
    public void saveZip();
    /**
	 * This method gets a name of a file and a maze and loads to the new maze
	 * the objects from the file.
	 */
    public void load(String[] arr);
    /**
     * This method loads the hashMaps from an existing zip file
     */
    public void loadZip();
    /**
	 * This method gets a name of a maze and solving algorithm and solves it in
	 * a Thread. All the solutions saved in an HashMap.
	 */
    public void solve(String[] arr);
    /**
	 * This method gets a name of a maze and sends the Controller its solution
	 */
    public Solution<Position> bringSolution();
    /**
	 * This method gets a name of a maze and sends the Controller its size.
	 */
    public void gameSize(String[] arr);
    /**
	 * This method gets a name of a file and sends the Controller its size.
	 */
    public void fileSize(String[] arr);
    /**
	 * This method closes all the open threads.
	 */
    public void close();
    /**
     * This method checks in a specific maze if you can move up
     */
	public void moveUp();
	/**
     * This method checks in a specific maze if you can move down
     */
	public void moveDown();
	/**
     * This method checks in a specific maze if you can move left
     */
	public void moveLeft();
	/**
     * This method checks in a specific maze if you can move right
     */
	public void moveRight();
	/**
     * This method checks in a specific maze if you can move backward
     */
	public void moveBackward();
	/**
     * This method checks in a specific maze if you can move forward
     */
	public void moveForward();

	public void setProperties(Properties properties);
	
	public Properties getProperties();
   
	public Position getHPosition(String name);
}
