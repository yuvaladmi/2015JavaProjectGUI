package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;
import model.Model;
import view.View;

public class Presenter implements Observer {

	View v;
	Model m;
	HashMap<String, Command> hCommands;

	public Presenter(Model m, View v) {
		this.v = v;
		this.m = m;
		this.hCommands = new HashMap<String, Command>();
		createHashMap();
	}

	public void createHashMap() {
		hCommands.put("generate", new Command() {

			@Override
			public void doCommand(String[] arr) {
				m.generateMaze(arr);
			}
		});
		hCommands.put("display", new Command() {

			@Override
			public void doCommand(String[] arr) {
				switch (arr[1]) {
				case "cross":
					v.displayInt(m.crossSection(arr));
					break;
				case "solution":
					v.displayString(m.bringSolution(arr[arr.length - 1]));
					break;
				default:
					Maze3d maze = m.sendGame(arr[arr.length - 1]);
					v.displayMaze(maze);
					v.displayPosition(maze.getStart());
					break;
				}
			}
		});
		hCommands.put("save", new Command() {

			@Override
			public void doCommand(String[] arr) {
				if (arr[1] == "zip") {
					m.saveZip();
				} else
					m.save(arr);
			}
		});
		hCommands.put("load", new Command() {

			@Override
			public void doCommand(String[] arr) {
				if (arr[1] == "zip") {
					m.loadZip();
				} else
					m.load(arr);

			}
		});
		hCommands.put("solve", new Command() {

			@Override
			public void doCommand(String[] arr) {
				m.solve(arr);
			}
		});

		hCommands.put("maze", new Command() {

			@Override
			public void doCommand(String[] arr) {
				m.gameSize(arr);

			}
		});
		hCommands.put("file", new Command() {

			@Override
			public void doCommand(String[] arr) {
				m.fileSize(arr);

			}
		});
		hCommands.put("exit", new Command() {

			@Override
			public void doCommand(String[] arr) {
				m.close();

			}
		});
		hCommands.put("move", new Command() {

			@Override
			public void doCommand(String[] arr) {
				switch (arr[1]) {
				case "UP":
					m.moveUp(arr[2]);
					break;
				case "DOWN":
					m.moveDown(arr[2]);
					break;
				case "LEFT":
					m.moveLeft(arr[2]);
					break;
				case "RIGHT":
					m.moveRight(arr[2]);
					break;
				case "BACKWARD":
					m.moveBackward(arr[2]);
					break;
				case "FOREWARD":
					m.moveForward(arr[2]);
					break;

				default:
					v.displayString("Error!");
					break;
				}
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == v) {
			if (((arg.getClass()).getName()).equals("[Ljava.lang.String;")) {
				String[] s1 = (String[]) arg;
				Command command = hCommands.get(s1[0]);
				command.doCommand(s1);
			}
		} else {
			if (o == m) {
				if (((arg.getClass()).getName()).equals("[Ljava.lang.String;")) {
					String[] temp = (String[]) arg;
					Command command;
					switch (temp[0]) {
					case "maze is ready":
						command = hCommands.get("display");
						command.doCommand(("display maze " + temp[1]).split(" "));
						break;
					case "load":
						command = hCommands.get("display");
						command.doCommand(("display maze " + temp[1]).split(" "));
						break;
					case "solution":
						command = hCommands.get("display");
						command.doCommand(("display solution " + temp[1]).split(" "));
						break;
					case "move":
						v.displayPosition(m.getHPosition(temp[1]));
						break;

					default:
						break;
					}
				}
				if (((arg.getClass()).getName()).equals("java.lang.String")) {
					String s = (String) arg;
					v.displayString(s);
				}
			}
		}
	}
}
