package view;

import java.util.ArrayList;
import java.util.Stack;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import algorithms.mazeGenerators.Position;
/**
 * 
 * @author Yuval Admi & Afek Ben Simon
 * This class paints a widget of a maze
 *
 */
public class Maze3D extends Maze3dViewDisplayer {
	public int characterX = 0;
	public int characterY = 2;

	protected char pivot;
	protected int index;
	protected int flag = 0;
	protected ArrayList<Position> solArray;

	public Maze3D(Composite parent, int style, char pivot) {
		super(parent, style);
		this.pivot = pivot;
		this.index = 0;
		// set a white background (red, green, blue)
		setBackground(new Color(getDisplay(), 255, 255, 255));
		setBackgroundImage(imageBackround);

		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(new Color(null,150,150,150));
				e.gc.setBackground(new Color(null,150,150,150));
				Stack<Position> stack = null;
				if (solution != null) {
					flag = 1;
					stack = solution.getSolution();

				} else
					flag = 0;
				if (maze != null) {

					Position temp = new Position(0, 0, 0);

					int width = getSize().x;
					int height = getSize().y;
					int firstSize = 0;
					int secondSize = 0;
					int goal1 = 0;
					int goal2 = 0;
					int goal3 = 0;
					int pos1 = 0;
					int pos2 = 0;

					int[][] crossMaze;
					switch (pivot) {
					case 'x':
						firstSize = maze.getY();
						secondSize = maze.getZ();
						goal1 = maze.getEnd().getY();
						goal2 = maze.getEnd().getZ();
						goal3 = maze.getEnd().getX();
						index = position.getX();
						crossMaze = maze.getCrossSectionByX(index);
						pos1 = position.getY();
						pos2 = position.getZ();
						break;
					case 'y':
						firstSize = maze.getX();
						secondSize = maze.getZ();
						goal1 = maze.getEnd().getX();
						goal2 = maze.getEnd().getZ();
						goal3 = maze.getEnd().getY();
						index = position.getY();
						crossMaze = maze.getCrossSectionByY(index);
						pos1 = position.getX();
						pos2 = position.getZ();

						break;
					case 'z':
						firstSize = maze.getX();
						secondSize = maze.getY();
						goal1 = maze.getEnd().getX();
						goal2 = maze.getEnd().getY();
						goal3 = maze.getEnd().getZ();
						index = position.getZ();
						crossMaze = maze.getCrossSectionByZ(index);
						pos1 = position.getX();
						pos2 = position.getY();

						break;
					default:
						firstSize = maze.getY();
						secondSize = maze.getZ();
						goal1 = maze.getEnd().getY();
						goal2 = maze.getEnd().getZ();
						index = position.getX();
						crossMaze = maze.getCrossSectionByX(index);
						pos1 = position.getY();
						pos2 = position.getZ();

						break;
					}

					int w = width / firstSize;
					int h = height / secondSize;

					imageHeight = image.getBounds().height;
					imageWidth = image.getBounds().width;
					if (flag == 1 && !stack.isEmpty()) {
						temp = stack.pop();
					}
					for (int i = 0; i < crossMaze.length; i++) {
						for (int j = 0; j < crossMaze[i].length; j++) {
							int x = j * w;
							int y = i * h;
							if (crossMaze[i][j] == 0)
								 e.gc.fillRectangle(x,y,w,h);
							else{
								e.gc.drawImage(imageWall, 0, 0,	imageWall.getBounds().width, imageWall.getBounds().height, x, y, w, h);
							}
							if (i == pos1 && j == pos2)
								e.gc.drawImage(image, 0, 0, imageWidth, imageHeight, x, y, w, h);
							if (i == goal1 && j == goal2 && index == goal3)
								e.gc.drawImage(imageGoal, 0, 0, imageGoal.getBounds().width,
										imageGoal.getBounds().height, x, y, w, h);
							if (flag == 1) {
								Position p = new Position(temp);
								switch (pivot) {
								case 'x':
									p = new Position(index, i, j);
									if ((stack.contains(p) && !p.equals(maze.getEnd())))
										e.gc.drawImage(imageHint, 0, 0, imageHint.getBounds().width,
												imageHint.getBounds().height, x, y, w, h);
									break;
								case 'y':
									p = new Position(i, index, j);
									if ((stack.contains(p) && !p.equals(maze.getEnd())))
										e.gc.drawImage(imageHint, 0, 0, imageHint.getBounds().width,
												imageHint.getBounds().height, x, y, w, h);
									break;
								case 'z':
									p = new Position(i, j, index);
									if ((stack.contains(p) && !p.equals(maze.getEnd())))
										e.gc.drawImage(imageHint, 0, 0, imageHint.getBounds().width,
												imageHint.getBounds().height, x, y, w, h);
									break;
								}

							}
						}

						if (position.equals(maze.getEnd())) {
							Image image = new Image(getDisplay(), "resources/YOU WIN.png");
							e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0,
									getSize().x, getSize().y);
						}
					}

				}
			}

		});

	}
}
