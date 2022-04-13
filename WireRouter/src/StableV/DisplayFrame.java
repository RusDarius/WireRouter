package StableV;


import javax.swing.JFrame;

public class DisplayFrame extends JFrame { // a frame to display UI results or components

	DisplayPanel panel;
	

	DisplayFrame(int arr[][], int editable) {

		panel = new DisplayPanel(arr, arr[0].length, arr.length, editable);

		this.add(panel);
		this.setTitle("Result");
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // we desire to remain in the app so we only dispose of a display frame on close operation
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);

	}

}
