package view;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public abstract class Maze3dViewDisplayer extends Canvas {

	protected Maze3d maze;
	protected Position position;
	protected Solution<Position> solution;
	protected Image image;
	protected Image imageGoal;
	protected Image imageHint;
	protected int imageWidth;
	protected int imageHeight;

	public Maze3dViewDisplayer(Composite parent, int style) {
		super(parent, style);
		position = new Position(0, 0, 0);
		image = new Image(getDisplay(), "resources/minion.jpg");
		imageGoal = new Image(getDisplay(), "resources/timthumb.png");
		imageHint = new Image(getDisplay(), "resources/banana.jpg");
	}

	public Maze3d getMaze() {
		return maze;
	}

	public Position getPosition() {
		return position;
	}

	public void setMazeData(Maze3d m) {
		this.maze = m;
		getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				redraw();
			}
		});
	}

	public void setPositionData(Position p) {
		this.position = p;
		getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				redraw();
			}
		});
	}
	
	public void setSolutionData(Solution<Position> s) {
		this.solution = s;
		getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				redraw();
			}
		});
	}
}
