package StableV;

import javax.swing.*;

public class MainFrame extends JFrame{ // this is the main frame of the application that contains the menu as a JPanel 

	MenuPanel panel;
	
	public MainFrame(int[][] arr, int[][] arr2, int[][] arr3) {

		panel = new MenuPanel(arr, arr2, arr3);
		this.add(panel);
		this.setTitle("Netlist Solver 1.0");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}
	
}