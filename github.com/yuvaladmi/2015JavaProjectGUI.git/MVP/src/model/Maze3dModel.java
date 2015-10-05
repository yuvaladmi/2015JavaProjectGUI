package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.CommonSearcher;
import algorithms.search.MazeManhattenDistance;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

public class Maze3dModel extends abstractModel {

	public Maze3dModel() {
		hMaze = new HashMap<String, Maze3d>();
		hSol = new HashMap<Maze3d, Solution<Position>>();
		hPosition = new HashMap<String, Position>();
		sb = new StringBuilder();
		numOfThread = 10;
		threadpool = Executors.newFixedThreadPool(numOfThread);
		loadZip();
	}

	public void addObservers(Observer o) {
		addObserver(o);
	}

	/**
	 * This method create a new Maze3d in a thread. All the mazes saved in an
	 * HashMap.
	 */
	@Override
	public void generateMaze(String[] arr) {
		int z = Integer.parseInt(arr[arr.length - 1]);
		int y = Integer.parseInt(arr[arr.length - 2]);
		int x = Integer.parseInt(arr[arr.length - 3]);
		String name = arr[arr.length - 4];
		Future<Maze3d> fCallMaze = threadpool.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				MyMaze3dGenerator mg = new MyMaze3dGenerator(x, y, z);
				Maze3d m = mg.generate(x, y, z);
				return m;
			}
		});
		try {
			hMaze.put(name, fCallMaze.get());
			hPosition.put(name, fCallMaze.get().getStart());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] messege = ("maze is ready:" + name).split(":");
		setChanged();
		notifyObservers(messege);
	}

	/**
	 * This method gets a Maze name and sends the Controller this maze.
	 */
	@Override
	public Maze3d sendGame(String arr) {
		Maze3d temp = hMaze.get(arr);
		return temp;
	}

	/**
	 * This method gets a name of a maze and sends the Controller a CrossSection
	 * of this maze
	 * 
	 * @return
	 */
	@Override
	public int[][] crossSection(String[] arr) {
		sb = new StringBuilder();
		int i = arr.length - 2;
		if (arr[i].equals("for")) {
			for (int j = i + 1; j < arr.length; j++) {
				sb.append(arr[j]);
			}
		}
		String name = sb.toString();
		int index = (arr[--i].charAt(0)) - 48;
		char crossBy = arr[--i].charAt(0);
		Maze3d maze = hMaze.get(name);
		int[][] myMaze = null;
		switch (crossBy) {
		case 'x':
			myMaze = maze.getCrossSectionByX(index);
			break;
		case 'X':
			myMaze = maze.getCrossSectionByX(index);
			break;
		case 'y':
			myMaze = maze.getCrossSectionByY(index);
			break;
		case 'Y':
			myMaze = maze.getCrossSectionByY(index);
			break;
		case 'z':
			myMaze = maze.getCrossSectionByZ(index);
			break;
		case 'Z':
			myMaze = maze.getCrossSectionByZ(index);
			break;
		default:
			setChanged();
			notifyObservers("Error, pkease try again");
			break;
		}
		return myMaze;

	}

	/**
	 * This methods gets a name of a file and a maze and save this maze in the
	 * file.
	 */
	@Override
	public void save(String[] arr) {
		String name = arr[arr.length - 2];
		String fileName = arr[arr.length - 1];
		Maze3d m = hMaze.get(name);
		try {
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			out.write(m.toByteArray());
			out.close();
			setChanged();
			notifyObservers("Maze " + name + " is saved");
		} catch (FileNotFoundException e) {
			setChanged();
			notifyObservers("Error - the file was not found");
		} catch (IOException e) {
			setChanged();
			notifyObservers("Error - IOException");
		}
	}

	/**
	 * This methods gets a name of a file and a maze and load to the new maze
	 * the objects from the file.
	 */
	@Override
	public void load(String[] arr) {
		String name = arr[arr.length - 1];
		String fileName = arr[arr.length - 2];
		try {
			byte[] temp = new byte[4096];
			InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName));
			int numOfBytes = in.read(temp);
			in.close();
			byte[] b = new byte[numOfBytes];
			for (int i = 0; i < b.length; i++) {
				b[i] = temp[i];
			}
			Maze3d maze = new Maze3d(b);
			hMaze.put(name, maze);
			setChanged();
			notifyObservers(("load:" + name).split(":"));
		} catch (FileNotFoundException e) {
			setChanged();
			notifyObservers("Error - the file was not found");
		} catch (IOException e) {
			setChanged();
			notifyObservers("Error - IOException");
		}
	}

	/**
	 * This method gets a name of a maze and solving algorithm and solves it in
	 * a Thread. All the solutions saved in an HashMap.
	 */
	@Override
	public void solve(String[] arr) {
		String nameAlg = arr[arr.length - 1];
		sb = new StringBuilder();
		for (int i = 1; i < arr.length - 1; i++) {
			sb.append(arr[i]);
		}
		String name = sb.toString();
		Maze3d tempMaze = hMaze.get(name);
		if ((hSol.get(tempMaze)) != null) {
			setChanged();
			notifyObservers(("solution:" + name).split(":"));
		}
		Future<Solution<Position>> fCallSolution = threadpool.submit(new Callable<Solution<Position>>() {

			@Override
			public Solution<Position> call() throws Exception {

				Maze3d m = hMaze.get(name);
				SearchableMaze sMaze = new SearchableMaze(m);
				CommonSearcher<Position> cs;
				Solution<Position> s = new Solution<Position>();
				switch (nameAlg) {
				case "Astar":
					cs = new AStar<Position>(new MazeManhattenDistance());
					s = cs.search(sMaze);
					break;
				case "A*":
					cs = new AStar<Position>(new MazeManhattenDistance());
					s = cs.search(sMaze);
					break;
				case "BFS":
					cs = new BFS<Position>();
					s = cs.search(sMaze);
					break;
				}
				return s;
			}
		});
		try {
			hSol.put(tempMaze, fCallSolution.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setChanged();
		notifyObservers(("solution:" + name).split(":"));
	}

	/**
	 * This method gets a name of a maze and sends the Controller its solution
	 */
	@Override
	public String bringSolution(String arr) {
		Maze3d maze = hMaze.get(arr);
		// Solution<Position> s = hSol.get(maze);
		if (maze != null) {
			Stack<Position> sol = hSol.get(maze).getSolution();
			sb = new StringBuilder();
			while (!sol.isEmpty()) {
				sb.append(sol.pop());
			}
			return sb.toString();
		}
		return "Solution do not exist for " + arr + " maze.";
	}

	/**
	 * This method gets a name of a maze and sends the Controller its size.
	 */
	@Override
	public void gameSize(String[] arr) {
		Maze3d temp = hMaze.get(arr[arr.length - 1]);
		try {
			int size = (temp.toByteArray()).length;
			setChanged();
			notifyObservers("Maze " + arr[arr.length - 1] + " size is:" + size);
		} catch (IOException e) {
			setChanged();
			notifyObservers("Error, please try again");
		}
	}

	/**
	 * This method gets a name of a file and sends the Controller its size.
	 */
	@Override
	public void fileSize(String[] arr) {
		File f = new File(arr[arr.length - 1]);
		if (f.exists()) {
			long size = f.length();
			setChanged();
			notifyObservers("File " + arr[arr.length - 1] + " size is:" + size);
		} else {
			setChanged();
			notifyObservers("Error, please try again");
		}
	}

	@Override
	public void saveZip() {
		try {
			FileOutputStream fileMaze = new FileOutputStream("mazes.zip");
			ObjectOutputStream objMaze = new ObjectOutputStream(new GZIPOutputStream(fileMaze));
			objMaze.writeObject(hMaze);
			objMaze.writeObject(hSol);
			objMaze.flush();
			objMaze.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void loadZip() {
		try {
			FileInputStream fileMaze = new FileInputStream("mazes.zip");
			ObjectInputStream objMaze = new ObjectInputStream(new GZIPInputStream(fileMaze));
			hMaze = (HashMap<String, Maze3d>) objMaze.readObject();
			hSol = (HashMap<Maze3d, Solution<Position>>) objMaze.readObject();
			objMaze.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method closes all the open threads.
	 */
	@Override
	public void close() {
		saveZip();
		setChanged();
		notifyObservers("shutting down");
		threadpool.shutdown();
		// wait 10 seconds over and over again until all running jobs have
		// finished
		try {
			boolean allTasksCompleted = false;
			while (!(allTasksCompleted = threadpool.awaitTermination(10, TimeUnit.SECONDS)))
				;
			setChanged();
			notifyObservers("Server is safely closed");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dirToPath(String[] arr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveUp(String name) {
		Position currentPosition = hPosition.get(name);
		Maze3d currentMaze = hMaze.get(name);
		ArrayList<String> moves = currentMaze.getPossibleMoves(currentPosition);
		for (String move : moves) {
			if (move == "up") {
				currentPosition.setX(currentPosition.getX() + 1);
				hPosition.put(name, currentPosition);
				setChanged();
				notifyObservers(("move " + name).split(" "));
			}
		}

	}

	@Override
	public void moveDown(String name) {
		Position currentPosition = hPosition.get(name);
		Maze3d currentMaze = hMaze.get(name);
		ArrayList<String> moves = currentMaze.getPossibleMoves(currentPosition);
		for (String move : moves) {
			if (move == "down") {
				currentPosition.setX(currentPosition.getX() - 1);
				hPosition.put(name, currentPosition);
				setChanged();
				notifyObservers(("move " + name).split(" "));
			}
		}

	}

	@Override
	public void moveLeft(String name) {
		Position currentPosition = hPosition.get(name);
		Maze3d currentMaze = hMaze.get(name);
		ArrayList<String> moves = currentMaze.getPossibleMoves(currentPosition);
		for (String move : moves) {
			if (move == "left") {
				currentPosition.setX(currentPosition.getZ() - 1);
				hPosition.put(name, currentPosition);
				setChanged();
				notifyObservers(("move " + name).split(" "));
			}
		}
	}

	@Override
	public void moveRight(String name) {
		Position currentPosition = hPosition.get(name);
		Maze3d currentMaze = hMaze.get(name);
		ArrayList<String> moves = currentMaze.getPossibleMoves(currentPosition);
		for (String move : moves) {
			if (move == "right") {
				currentPosition.setX(currentPosition.getZ() + 1);
				hPosition.put(name, currentPosition);
				setChanged();
				notifyObservers(("move " + name).split(" "));
			}
		}
	}

	@Override
	public void moveBackward(String name) {
		Position currentPosition = hPosition.get(name);
		Maze3d currentMaze = hMaze.get(name);
		ArrayList<String> moves = currentMaze.getPossibleMoves(currentPosition);
		for (String move : moves) {
			if (move == "backward") {
				currentPosition.setX(currentPosition.getY() - 1);
				hPosition.put(name, currentPosition);
				setChanged();
				notifyObservers(("move " + name).split(" "));
			}
		}

	}

	@Override
	public void moveForward(String name) {
		Position currentPosition = hPosition.get(name);
		Maze3d currentMaze = hMaze.get(name);
		ArrayList<String> moves = currentMaze.getPossibleMoves(currentPosition);
		for (String move : moves) {
			if (move == "forward") {
				currentPosition.setX(currentPosition.getY() + 1);
				hPosition.put(name, currentPosition);
				setChanged();
				notifyObservers(("move " + name).split(" "));
			}
		}

	}

	@Override
	public Position getHPosition(String name) {

		return hPosition.get(name);
	}

}
