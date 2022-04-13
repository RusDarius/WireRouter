package StableV;

import java.awt.*;
import java.awt.geom.*;

public class Cell extends Rectangle {

	int x; // row
	int y; // column
	int size; // lenght / width of cell
	int val;
	public int selected = 0;

	Cell(int x, int y, int size, int val) {
		super(x * size + 1, y * size + 1, size, size);
		this.x = x * size;
		this.y = y * size;
		this.size = size;
		this.val = val;
	}

	public void draw(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		Color myColor = Color.decode("#99ff66");
		Color badColor = Color.decode("#ff5050");

		g2.setPaint(new Color(0, 0, 0));
		g2.setStroke(new BasicStroke(3));
		g2.draw(new Line2D.Float(x, y, x + size, y)); // top
		g2.draw(new Line2D.Float(x, y, x, y + size)); // left
		g2.draw(new Line2D.Float(x + size, y, x + size, y + size)); // right
		g2.draw(new Line2D.Float(x, y + size, x + size, y + size)); // bottom

		if (val > 0 || selected == 1) {
			g2.setPaint(myColor);
			g2.fillRect(x + 1, y + 1, size, size);
		}
		
		if(val == -3) {
			g2.setPaint(badColor);
			g2.fillRect(x + 1, y + 1, size, size);
		}
		
		if(val == -2) {
			g2.setPaint(Color.decode("#0000ff"));
			g2.fillRect(x + 1, y + 1, size, size);
		}
		
		g2.setPaint(new Color(0, 0, 0));
		g2.drawString(String.valueOf(val), x + size - 15, y + size - 3);

	}

	
	
}