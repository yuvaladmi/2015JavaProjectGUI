package view;

import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public abstract class abstractView extends Observable implements View {

	public abstract void displayByte(byte[] arr);

	public abstract void displayString(String arr);

	public abstract void displayInt(int[][] arr);

	public abstract void start();

	public abstract void exit();

	public abstract void displayMaze(Maze3d sendGame);

	public abstract void displayPosition(Position p);

}
