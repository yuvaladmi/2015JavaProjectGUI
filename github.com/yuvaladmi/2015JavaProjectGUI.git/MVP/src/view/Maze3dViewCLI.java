package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * @author Yuval Admi & Afek Ben Simon
 * @since 01.09.2015 This class is responsible to start the all system. It gets
 *        a command, checks if it exists and sends an order to do it.
 *
 * 
 */
public class Maze3dViewCLI extends MyObservableCLIView {

	public Maze3dViewCLI(BufferedReader in, PrintWriter out) {
		super(in, out);
	}
	/**
     * This method starts the whole project.
     */
	public void start() {

		// the main thread, asking the client what does he want to do next.
		mainThread = new Thread(new Runnable() {
			public void run() {
				String line;
				try {
					out.println("enter new command:");
					out.flush();
					while ((line = in.readLine().intern()) != "exit") {
						setChanged();
						notifyObservers(line.split(" "));
					}
					out.println("bye bye");
					out.flush();
					setChanged();
					notifyObservers("exit view".split(" "));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		mainThread.start();
	}

	@Override
	public void displayString(String arr) {
		System.out.println(arr);

	}

	@Override
	public void displayInt(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}

	}

	@Override
	public void exit() {
		System.out.println("Server is safely closed");
	}

	

	@Override
	public void displayMaze(Maze3d sendGame) {
		System.out.println(sendGame);

	}

	@Override
	public void displayPosition(Position p) {
		System.out.println(p);
	}

	@Override
	public void displaySolution(Solution<Position> s) {
		System.out.println(s);
	}
	@Override
	public void displayByte(byte[] arr) {
		// TODO Auto-generated method stub
		
	}

}
