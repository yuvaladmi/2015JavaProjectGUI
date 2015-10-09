package view;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
/**
 * 
 * @author Yuval Admi & Afek Ben Simon
 * This class extends Canvas, responsible to the maze widget
 *
 */
public abstract class Maze3dViewDisplayer extends Canvas {

	protected Maze3d maze;
	protected Position position;
	protected Solution<Position> solution;
	protected Image image;
	protected Image imageGoal;
	protected Image imageHint;
	protected Image imageBackround;
	protected Image imageWay;
	protected Image imageWall;
	protected int imageHeight;
	protected int imageWidth;

	public Maze3dViewDisplayer(Composite parent, int style) {
		super(parent, style);
		position = new Position(0, 0, 0);
	}

	public Image getImageBackround() {
		return imageBackround;
	}

	public void setImageBackroundString(String str) {
		imageBackround = new Image(getDisplay(), str);
	}

	public Image getImageWall() {
		return imageWall;
	}

	public void setImageWallString(String str) {
		this.imageWall = new Image(getDisplay(), str);
	}

	public Image getImage() {
		return image;
	}

	public void setImageString(String str) {
		image = new Image(getDisplay(), str);
	}

	public Image getImageGoal() {
		return imageGoal;
	}

	public void setImageGoalString(String str) {
		imageGoal = new Image(getDisplay(), str);
	}

	public Image getImageHint() {
		return imageHint;
	}

	public void setImageHintString(String str) {
		imageHint = new Image(getDisplay(), str);
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

	public Image getImageWay() {
		return imageWay;
	}

	public void setImageWayString(String str) {
		this.imageWay = new Image(getDisplay(), str);
	}

}
