package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

public class Maze3D extends Maze3dViewDisplayer {
	public int characterX = 0;
	public int characterY = 2;

	protected char pivot;
	protected int index;
	protected Image image;

	public Maze3D(Composite parent, int style, char pivot) {
		super(parent, style);
		this.pivot = pivot;
		this.index = 0;
		// set a white background (red, green, blue)
		setBackground(new Color(null, 255, 255, 255));
		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (maze != null) {
					e.gc.setForeground(new Color(null, 0, 0, 0));
					e.gc.setBackground(new Color(null, 0, 0, 0));

					int width = getSize().x;
					int height = getSize().y;
					int firstSize = 0;
					int secondSize = 0;
					int pos1=0;
					int pos2=0;

					int[][] crossMaze;
					switch (pivot) {
					case 'x':
						firstSize = maze.getY();
						secondSize = maze.getZ();
						index = position.getX();
						crossMaze = maze.getCrossSectionByX(index);
						pos1=position.getY();
						pos2=position.getZ();
						break;
					case 'y':
						firstSize = maze.getX();
						secondSize = maze.getZ();
						index = position.getY();
						crossMaze = maze.getCrossSectionByY(index);
						pos1=position.getX();
						pos2=position.getZ();

						break;
					case 'z':
						firstSize = maze.getX();
						secondSize = maze.getY();
						index = position.getZ();
						crossMaze = maze.getCrossSectionByZ(index);
						pos1=position.getX();
						pos2=position.getY();

						break;
					default:
						firstSize = maze.getY();
						secondSize = maze.getZ();
						index = position.getX();
						crossMaze = maze.getCrossSectionByX(index);
						pos1=position.getY();
						pos2=position.getZ();
						
						break;
					}

					int w = width / firstSize;
					int h = height / secondSize;
					image = new Image(getDisplay(), "resources/minion.jpg");
					imageHeight = image.getBounds().height;
					imageWidth = image.getBounds().width;
					for (int i = 0; i < crossMaze.length; i++)
						for (int j = 0; j < crossMaze[i].length; j++) {
							int x = j * w;
							int y = i * h;
							if (crossMaze[i][j] != 0)
								e.gc.fillRectangle(x, y, w, h);
							if (i == pos1 && j == pos2)
								e.gc.drawImage(image, 0, 0, imageWidth, imageHeight, x, y, w, h);
						}
				}
			}
		});

	}

}
