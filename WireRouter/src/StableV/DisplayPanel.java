package StableV;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class DisplayPanel extends JPanel {

	static int SIZE_WIDTH = 650; // default
	static int SIZE_HEIGHT = 650; // default
	static Dimension SCREEN_SIZE = new Dimension(SIZE_WIDTH, SIZE_HEIGHT);
	public static Cell[][] maze; // each cell in the 2dArray of the Netlist is a Cell obj that extends Rectangle
									// and is display individually
	static final int CELL_SIZE = 18; // the size for a singular cell
	static int xS, yS;

	Image image;
	Graphics graphics;

	DisplayPanel(int arr[][], int sideW, int sideH, int editable) { // the constructor gets the array representation of
																	// the .csv file and dimensions and state of the
																	// frame it is in

		maze = new Cell[sideH][sideW]; // we create the UI array of objects
		SIZE_HEIGHT = sideH * CELL_SIZE; // we size the "board"
		SIZE_WIDTH = sideW * CELL_SIZE;
		SCREEN_SIZE = new Dimension(SIZE_WIDTH, SIZE_HEIGHT); // we give the necesary size
		this.setFocusable(true);
		this.setPreferredSize(SCREEN_SIZE);

		for (int i = 0; i < maze.length; i++)
			for (int j = 0; j < maze[0].length; j++)
				maze[i][j] = new Cell(j, i, CELL_SIZE, arr[i][j]); // we create each obj for the array
		if (editable == 1) { // if 1 -> we are in custom Netlist and we wish to interact with the board
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent me) {
					super.mouseClicked(me);
					for (int i = 0; i < maze.length; i++) {
						for (int j = 0; j < maze[0].length; j++) {
							maze[i][j].selected = 0; // we clean the selection
						}
					}

					for (int i = 0; i < maze.length; i++) {
						for (int j = 0; j < maze[0].length; j++) {
							if (maze[i][j].contains(me.getPoint())) {
								maze[i][j].selected = 1; // we select the Cell clicked
								repaint();
							}
						}
					}

				}
			});

			this.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent evt) {

					if ((evt.getKeyChar() <= '9' && evt.getKeyChar() >= '0') || (evt.getKeyChar() == 'z'))

						for (int i = 0; i < maze.length; i++) {
							for (int j = 0; j < maze[0].length; j++) {
								if (maze[i][j].selected == 1) {
									if (evt.getKeyChar() == 'z') {
										maze[i][j].val = -2;
										arr[i][j] = maze[i][j].val;
										repaint();
									} else {
										maze[i][j].val = maze[i][j].val * 10
												+ Integer.parseInt(String.valueOf(evt.getKeyChar()));
										arr[i][j] = maze[i][j].val;
										repaint(); // once we click a cell we can change it to a netpoint if we so
													// desire
										if (maze[i][j].val > 99) {
											maze[i][j].val = 0;
											arr[i][j] = 0;
											repaint();
										}
									}
								}
							}
						}
				}
			});

		}

	}

	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
	}

	private void draw(Graphics graphics2) {
		for (int i = 0; i < maze[0].length; i++)
			for (int j = 0; j < maze.length; j++) {
				maze[j][i].draw(graphics2);
			}
	}

}
