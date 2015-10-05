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
	public int exitX = 0;
	public int exitY = 2;
	static GC shellGC;
	static Color shellBackground;
	static ImageLoader loader;
	static ImageData[] imageDataArray;
	static Thread animateThread;
	static Image image;
	static final boolean useGIFBackground = false;

	private void paintCube(double[] p, double h, PaintEvent e) {
		int[] f = new int[p.length];
		for (int k = 0; k < f.length; f[k] = (int) Math.round(p[k]), k++)
			;

		e.gc.drawPolygon(f);

		int[] r = f.clone();
		for (int k = 1; k < r.length; r[k] = f[k] - (int) (h), k += 2)
			;

		int[] b = { r[0], r[1], r[2], r[3], f[2], f[3], f[0], f[1] };
		e.gc.drawPolygon(b);
		int[] fr = { r[6], r[7], r[4], r[5], f[4], f[5], f[6], f[7] };
		e.gc.drawPolygon(fr);

		e.gc.fillPolygon(r);

	}

	public Maze3D(Composite parent, int style) {
		super(parent, style);

		final Color white = new Color(null, 255, 255, 255);
		final Color black = new Color(null, 150, 150, 150);
		setBackground(white);
		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if(maze!=null){
				e.gc.setForeground(new Color(null, 0, 0, 0));
				e.gc.setBackground(new Color(null, 0, 0, 0));

				int width = getSize().x;
				int height = getSize().y;

				int mx = width / 2;
				int firstSize = 0;
				int secondSize = 0;
				int index = 0;
				int[][] mazeData = null;
				firstSize = maze.getY();
				secondSize = maze.getZ();
				index = position.getX();
				mazeData = maze.getCrossSectionByX(index);
				double w = (double) width / mazeData[0].length;
				double h = (double) height / mazeData.length;

				for (int i = 0; i < mazeData.length; i++) {
					double w0 = 0.7 * w + 0.3 * w * i / mazeData.length;
					double w1 = 0.7 * w + 0.3 * w * (i + 1) / mazeData.length;
					double start = mx - w0 * mazeData[i].length / 2;
					double start1 = mx - w1 * mazeData[i].length / 2;
					for (int j = 0; j < mazeData[i].length; j++) {
						double[] dpoints = { start + j * w0, i * h, start + j * w0 + w0, i * h, start1 + j * w1 + w1,
								i * h + h, start1 + j * w1, i * h + h };
						double cheight = h / 2;
						if (mazeData[i][j] != 0)
							paintCube(dpoints, cheight, e);

						if (i == characterY && j == characterX) {
							e.gc.setBackground(new Color(null, 200, 0, 0));
							e.gc.fillOval((int) Math.round(dpoints[0]), (int) Math.round(dpoints[1] - cheight / 2),
									(int) Math.round((w0 + w1) / 2), (int) Math.round(h));
							e.gc.setBackground(new Color(null, 255, 0, 0));
							e.gc.fillOval((int) Math.round(dpoints[0] + 2),
									(int) Math.round(dpoints[1] - cheight / 2 + 2),
									(int) Math.round((w0 + w1) / 2 / 1.5), (int) Math.round(h / 1.5));
							e.gc.setBackground(new Color(null, 0, 0, 0));
						}
					}
				}
				
//				String fileName = "resources/taz-walk.gif";
//				if (fileName != null) {
//					loader = new ImageLoader();
//					try {
//						imageDataArray = loader.load(fileName);
//						if (imageDataArray.length > 1) {
//							animateThread = new Thread("Animation") {
//								@Override
//								public void run() {
//									/* Create an off-screen image to draw on, and fill it with the shell background. */
//									Image offScreenImage = new Image(display, loader.logicalScreenWidth, loader.logicalScreenHeight);
//									GC offScreenImageGC = new GC(offScreenImage);
//									offScreenImageGC.setBackground(shellBackground);
//									offScreenImageGC.fillRectangle(0, 0, loader.logicalScreenWidth, loader.logicalScreenHeight);
//										
//									try {
//										/* Create the first image and draw it on the off-screen image. */
//										int imageDataIndex = 0;	
//										ImageData imageData = imageDataArray[imageDataIndex];
//										if (image != null && !image.isDisposed()) image.dispose();
//										image = new Image(display, imageData);
//										offScreenImageGC.drawImage(
//											image,
//											0,
//											0,
//											imageData.width,
//											imageData.height,
//											imageData.x,
//											imageData.y,
//											imageData.width,
//											imageData.height);
//
//										/* Now loop through the images, creating and drawing each one
//										 * on the off-screen image before drawing it on the shell. */
//										int repeatCount = loader.repeatCount;
//										while (loader.repeatCount == 0 || repeatCount > 0) {
//											switch (imageData.disposalMethod) {
//											case SWT.DM_FILL_BACKGROUND:
//												/* Fill with the background color before drawing. */
//												Color bgColor = null;
//												if (useGIFBackground && loader.backgroundPixel != -1) {
//													bgColor = new Color(display, imageData.palette.getRGB(loader.backgroundPixel));
//												}
//												offScreenImageGC.setBackground(bgColor != null ? bgColor : shellBackground);
//												offScreenImageGC.fillRectangle(imageData.x, imageData.y, imageData.width, imageData.height);
//												if (bgColor != null) bgColor.dispose();
//												break;
//											case SWT.DM_FILL_PREVIOUS:
//												/* Restore the previous image before drawing. */
//												offScreenImageGC.drawImage(
//													image,
//													0,
//													0,
//													imageData.width,
//													imageData.height,
//													imageData.x,
//													imageData.y,
//													imageData.width,
//													imageData.height);
//												break;
//											}
//																
//											imageDataIndex = (imageDataIndex + 1) % imageDataArray.length;
//											imageData = imageDataArray[imageDataIndex];
//											image.dispose();
//											image = new Image(display, imageData);
//											offScreenImageGC.drawImage(
//												image,
//												0,
//												0,
//												imageData.width,
//												imageData.height,
//												imageData.x,
//												imageData.y,
//												imageData.width,
//												imageData.height);
//											
//											/* Draw the off-screen image to the shell. */
//											shellGC.drawImage(offScreenImage, 0, 0);
//											
//											/* Sleep for the specified delay time (adding commonly-used slow-down fudge factors). */
//											try {
//												int ms = imageData.delayTime * 10;
//												if (ms < 20) ms += 30;
//												if (ms < 30) ms += 10;
//												Thread.sleep(ms);
//											} catch (InterruptedException e) {
//											}
//											
//											/* If we have just drawn the last image, decrement the repeat count and start again. */
//											if (imageDataIndex == imageDataArray.length - 1) repeatCount--;
//										}
//									} catch (SWTException ex) {
//										System.out.println("There was an error animating the GIF");
//									} finally {
//										if (offScreenImage != null && !offScreenImage.isDisposed()) offScreenImage.dispose();
//										if (offScreenImageGC != null && !offScreenImageGC.isDisposed()) offScreenImageGC.dispose();
//										if (image != null && !image.isDisposed()) image.dispose();
//									}
//								}
//							};
//							animateThread.setDaemon(true);
//							animateThread.start();
//						}
//					} catch (SWTException ex) {
//						System.out.println("There was an error loading the GIF");
//					}
//				}
				image=new Image (getDisplay(),"resources/minion.jpg");
				imageWidth=image.getBounds().width;
				imageHeight=image.getBounds().height;
				e.gc.drawImage(image, 0, 0,100,100,200,200, 100, 100);
//				e.gc.drawImage(image,position.getY(), position.getZ(), imageWidth, imageHeight,position.getY()+50 ,position.getZ()+50 ,position.getY()*width ,position.getZ()*height);
			}
			}
		});
	}
	// protected char pivot;
	// protected int index;
	// protected Image image;
	//
	// public Maze3D(Composite parent, int style, char pivot) {
	// super(parent, style);
	// this.pivot = pivot;
	// this.index = 0;
	// // set a white background (red, green, blue)
	// setBackground(new Color(null, 255, 255, 255));
	// addPaintListener(new PaintListener() {
	//
	// @Override
	// public void paintControl(PaintEvent e) {
	// if (maze != null) {
	// e.gc.setForeground(new Color(null, 0, 0, 0));
	// e.gc.setBackground(new Color(null, 0, 0, 0));
	//
	// int width = getSize().x;
	// int height = getSize().y;
	// int firstSize = 0;
	// int secondSize = 0;
	//
	// int[][] crossMaze = null;
	// switch (pivot) {
	// case 'x':
	// firstSize = maze.getY();
	// secondSize = maze.getZ();
	// index = position.getX();
	// crossMaze = maze.getCrossSectionByX(index);
	// break;
	// case 'y':
	// firstSize = maze.getX();
	// secondSize = maze.getZ();
	// index = position.getY();
	// crossMaze = maze.getCrossSectionByY(index);
	//
	// break;
	// case 'z':
	// firstSize = maze.getX();
	// secondSize = maze.getY();
	// index = position.getZ();
	// crossMaze = maze.getCrossSectionByZ(index);
	//
	// break;
	// default:
	// firstSize = maze.getY();
	// secondSize = maze.getZ();
	// index = position.getX();
	// crossMaze = maze.getCrossSectionByX(index);
	//
	// break;
	// }
	//
	// int w = width / firstSize;
	// int h = height / secondSize;
	// for (int i = 0; i < firstSize; i++)
	// for (int j = 0; j < secondSize; j++) {
	// int x = j * w;
	// int y = i * h;
	// if (crossMaze[i][j] != 0)
	// e.gc.fillRectangle(x, y, w, h);
	// }
	// }
	// }
	// });
	//
	// }

}
