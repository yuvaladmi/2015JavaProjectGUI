package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

public interface Model {
    public void dirToPath(String[] arr);

    public void generateMaze(String[] arr);

    public Maze3d sendGame(String str);

    public int[][] crossSection(String[] arr);

    public void save(String[] arr);
    
    public void saveZip();

    public void load(String[] arr);
    
    public void loadZip();

    public void solve(String[] arr);

    public Solution<Position> bringSolution();

    public void gameSize(String[] arr);

    public void fileSize(String[] arr);

    public void close();
    
	public void moveUp();

	public void moveDown();

	public void moveLeft();

	public void moveRight();

	public void moveBackward();

	public void moveForward();

	public void setProperties(Properties properties);
	
	public Properties getProperties();
   
	public Position getHPosition(String name);
}
