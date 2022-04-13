package StableV;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class MenuPanel extends JPanel {

	static final int SIZE = 400;
	static final Dimension SCREEN_SIZE = new Dimension(SIZE, SIZE);

	public static DisplayFrame buildMaze;
	Image image;
	Graphics graphics;
	Thread menuThread;
	JButton b1, b2, b3, b4, b5, b6; // the buttons for the different oprations
	JLabel l2, l1;
	int[][] generatedBoard = new int[20][20];

	MenuPanel(int[][] arr1, int[][] arr2, int[][] arr3) {

		this.setFocusable(true);
		this.setPreferredSize(SCREEN_SIZE);
		this.setLayout(null);

		SpinnerModel model = new SpinnerNumberModel(20, 5, 43, 1);
		JSpinner spinner = new JSpinner(model);
		spinner.setBounds(85, 280, 50, 30);
		this.add(spinner);

		l1 = new JLabel("HEIGHT FOR CUSTOM MAP");
		l1.setBounds(145, 280, 200, 30);
		this.add(l1);

		l2 = new JLabel("WIDTH FOR CUSTOM MAP");
		l2.setBounds(145, 320, 200, 30);
		this.add(l2);

		SpinnerModel model2 = new SpinnerNumberModel(20, 5, 43, 1);
		JSpinner spinner2 = new JSpinner(model2);
		spinner2.setBounds(85, 320, 50, 30);
		this.add(spinner2);

		b1 = new JButton("Solve Example 1"); // triggers the solving of a .csv file received as input
		b1.setBounds(125, 40, 150, 30);
		this.add(b1);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildMaze = new DisplayFrame(arr1, 0); // we generate the solved example with the paths highlighted
			}
		});

		b5 = new JButton("Solve Example 2"); // triggers the solving of a .csv file received as input
		b5.setBounds(125, 80, 150, 30);
		this.add(b5);
		b5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildMaze = new DisplayFrame(arr2, 0); // we generate the solved example with the paths highlighted
			}
		});

		b6 = new JButton("Check Step_3"); // triggers the solving of a .csv file received as input
		b6.setBounds(125, 120, 150, 30);
		this.add(b6);
		b6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildMaze = new DisplayFrame(arr3, 0); // we generate the solved example with the paths highlighted
			}
		});

		b2 = new JButton("Custom Netlist"); // a UI that allows the user to mark desired netPoints
		b2.setBounds(110, 160, 180, 30);
		this.add(b2);
		b2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				generatedBoard = new int[(int) spinner.getValue()][(int) spinner2.getValue()];
				new DisplayFrame(generatedBoard, 1);
			}

		});

		b3 = new JButton("Solve Custom Netlist"); // a solution offered by the algorithm to the custom netList
		b3.setBounds(110, 200, 180, 30);
		this.add(b3);
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				List<ValuesXY> listO = new ArrayList<ValuesXY>();

				for (int i = 0; i < generatedBoard.length; i++) {
					for (int j = 0; j < generatedBoard[0].length; j++) {
						if (generatedBoard[i][j] != 0 && generatedBoard[i][j] != -2) {
							ValuesXY temp2 = new ValuesXY(generatedBoard[i][j]);
							temp2.l.add(new Coords(i, j));
							listO.add(temp2);
						}
					}
				}

				generatedBoard = TestInOut.solveMapping(generatedBoard, listO, -1, -1);

				new DisplayFrame(generatedBoard, 0);
			}
		});



	}

}
