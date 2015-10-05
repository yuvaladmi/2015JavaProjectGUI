package view;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public class Maze3dViewCLI extends MyObservableCLIView {

    
    
    public Maze3dViewCLI(BufferedReader in, PrintWriter out) {
	super(in, out);
    }

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

    public void displayByte(byte[] arr) {
	ByteArrayInputStream bArr = new ByteArrayInputStream(arr);
	DataInputStream data = new DataInputStream(bArr);
	try {
	    int x = data.readInt();
	    int y = data.readInt();
	    int z = data.readInt();
	    System.out.println("Start Position: " + data.readInt() + "," + data.readInt() + "," + data.readInt());
	    System.out.println("Goal Position: " + data.readInt() + "," + data.readInt() + "," + data.readInt());

	    System.out.println("Maze size: " + x + "," + y + "," + z);
	    System.out.println();
	    for (int i = 0; i < x; i++) {
		for (int j = 0; j < y; j++) {
		    for (int k = 0; k < z; k++) {
			System.out.print(data.read());
		    }
		    System.out.println();
		}
		System.out.println();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
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
    public void notifyMessege(String[] arr) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void displayMaze(Maze3d sendGame) {
	System.out.println(sendGame);
	
    }

    @Override
    public void displayPosition(Position p) {
	System.out.println(p);
    }

}
