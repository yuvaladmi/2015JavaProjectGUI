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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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
import presenter.Properties;

/**
 * @author Yuval Admi & Afek Ben Simon
 * @since 08.10.2015 This class extends the abstract class - CommonModel. It
 *        should get a command from the presenter, do it and send a notification
 *        when it ends.
 * 
 */
public class Maze3dModel extends abstractModel {

	/**
	 * CTOR
	 */
	public Maze3dModel() {
		hMaze = new HashMap<String, Maze3d>();
		hSol = new HashMap<Maze3d, Solution<Position>>();
		hPosition = new HashMap<String, Position>();
		sb = new StringBuilder();
		numOfThread = 10;
		threadpool = Executors.newFixedThreadPool(numOfThread);
		properties = new Properties();
		// Loads the maps from previous plays
		loadZip();
	}

	
	@Override
	public void generateMaze(String[] arr) {
		int x = properties.getSizeX();
		int y = properties.getSizeY();
		int z = properties.getSizeZ();
		String name = properties.getName();
		System.out.println(x + " " + y + " " + z + " " + name);

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

	
	@Override
	public Maze3d sendGame(String str) {
		String name = str;
		Maze3d temp = hMaze.get(name);
		return temp;
	}

	
	@Override
	public int[][] crossSection(String[] arr) {
		String name = properties.getName();
		int index = Integer.parseInt(arr[arr.length - 1]);
		char crossBy = arr[arr.length - 2].charAt(0);
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
			notifyObservers("Error, please try again");
			break;
		}
		return myMaze;

	}

	
	@Override
	public void save(String[] arr) {
		String name = properties.getName();
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

	
	@Override
	public void load(String[] arr) {
		String name = arr[arr.length - 1];
		String fileName = arr[arr.length - 2];
		properties.setName(name);

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
			hPosition.put(name, maze.getStart());
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

	
	@Override
	public void solve(String[] arr) {
		String nameAlg = properties.getSolvingAlgo();
		String name = properties.getName();
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

	
	@Override
	public Solution<Position> bringSolution() {
		Maze3d maze = hMaze.get(properties.getName());
		// Solution<Position> s = hSol.get(maze);
		if (maze != null) {
			Solution<Position> sol = hSol.get(maze);
			return sol;
		}
		notifyObservers("Solution do not exist for " + properties.getName() + " maze.");
		return null;
	}

	
	@Override
	public void gameSize(String[] arr) {
		Maze3d temp = hMaze.get(properties.getName());
		try {
			int size = (temp.toByteArray()).length;
			setChanged();
			notifyObservers("Maze " + properties.getName() + " size is:" + size);
		} catch (IOException e) {
			setChanged();
			notifyObservers("Error, please try again");
		}
	}

	
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

	
	@Override
	public void close() {
		saveZip();
		setChanged();
		notifyObservers("shutting down");
		threadpool.shutdown();
		// wait 10 seconds over and over again until all running jobs have
		// finished
		try {
			@SuppressWarnings("unused")
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
	public void moveUp() {
		String name = properties.getName();
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
	public void moveDown() {
		String name = properties.getName();
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
	public void moveLeft() {
		String name = properties.getName();
		Position currentPosition = hPosition.get(name);
		Maze3d currentMaze = hMaze.get(name);
		ArrayList<String> moves = currentMaze.getPossibleMoves(currentPosition);
		for (String move : moves) {
			if (move == "left") {
				currentPosition.setZ(currentPosition.getZ() - 1);
				hPosition.put(name, currentPosition);
				setChanged();
				notifyObservers(("move " + name).split(" "));
			}
		}
	}

	@Override
	public void moveRight() {
		String name = properties.getName();
		Position currentPosition = hPosition.get(name);
		Maze3d currentMaze = hMaze.get(name);
		ArrayList<String> moves = currentMaze.getPossibleMoves(currentPosition);
		for (String move : moves) {
			if (move == "right") {
				currentPosition.setZ(currentPosition.getZ() + 1);
				hPosition.put(name, currentPosition);
				setChanged();
				notifyObservers(("move " + name).split(" "));
			}
		}
	}

	@Override
	public void moveBackward() {
		String name = properties.getName();
		Position currentPosition = hPosition.get(name);
		Maze3d currentMaze = hMaze.get(name);
		ArrayList<String> moves = currentMaze.getPossibleMoves(currentPosition);
		for (String move : moves) {
			if (move == "backward") {
				currentPosition.setY(currentPosition.getY() - 1);
				hPosition.put(name, currentPosition);
				setChanged();
				notifyObservers(("move " + name).split(" "));
			}
		}

	}

	@Override
	public void moveForward() {
		String name = properties.getName();
		Position currentPosition = hPosition.get(name);
		Maze3d currentMaze = hMaze.get(name);
		ArrayList<String> moves = currentMaze.getPossibleMoves(currentPosition);
		for (String move : moves) {
			if (move == "forward") {
				currentPosition.setY(currentPosition.getY() + 1);
				hPosition.put(name, currentPosition);
				setChanged();
				notifyObservers(("move " + name).split(" "));
			}
		}

	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public Position getHPosition(String name) {

		return hPosition.get(name);
	}

}
