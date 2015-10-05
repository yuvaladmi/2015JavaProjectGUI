package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public interface View {
	public void start();

	public void displayByte(byte[] arr);

	public void displayString(String arr);

	public void displayInt(int[][] arr);

	public void notifyMessege(String[] arr);

	public void exit();

	public void displayMaze(Maze3d sendGame);

	public void displayPosition(Position p);

}
