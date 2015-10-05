package boot;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;



import model.Maze3dModel;
import presenter.Presenter;
import view.Maze3dViewCLI;
import view.MyObservableGUIView;

public class Run {
    public static void main(String[] args) {
	 Maze3dModel m = new Maze3dModel();
//	 Maze3dViewCLI view = new Maze3dViewCLI(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out));
	 MyObservableGUIView view = new MyObservableGUIView("MyMaze", 500, 300);
	 Presenter p = new Presenter(m, view);
	 m.addObserver(p);
	 view.addObserver(p);
	 view.start();
//	BasicWindow bw = new MazeWindow("Maze Y&A", 500, 300);
//	bw.run();
    }
}