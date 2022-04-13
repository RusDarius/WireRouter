package StableV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * 
 * A description file has been added to help the user / reader to use the UI and understand the solution I attempted
 * 
 */

class ValuesXY { // object that can contain a value and it's coords to be routed based on them

	List<Coords> l;
	int value;
	int cnt = -1;

	public ValuesXY(int value) {
		this.value = value;
		this.l = new ArrayList<Coords>();
	}

	public ValuesXY(ValuesXY toCopy) {
		this.value = toCopy.value;
		this.l = new ArrayList<Coords>(toCopy.l);
		this.cnt = toCopy.cnt;
	}

}

class Coords { // coordinates are stored for each value / pin

	int x, y;

	Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}

}

class node { // used to generate a path in bfs (Lee's algorithm version)
	int x, y, distance;

	node(int x, int y, int dist) {
		this.x = x;
		this.y = y;
		this.distance = dist;
	}

	public int getDist() {
		return this.distance;
	}
}

public class TestInOut { // driver class

	static void unMark(int[][] arr) { // a method to remove the "walls" to protect each path from intersecting
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				if (arr[i][j] == -1)
					arr[i][j] = 0;
			}
		}
	}

	static void markWallsS(int key, int[][] arr) { // marking the "walls" around a value from a path to keep it from
													// getting intersected
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				int aux = -1;
				if (arr[i][j] != key && arr[i][j] != 0 && arr[i][j] != aux) {
					int x2 = i;
					int y2 = j;
					if (x2 - 1 >= 0) { // go up
						if (arr[x2 - 1][y2] == 0)
							arr[x2 - 1][y2] = aux;
					}
					if (y2 - 1 >= 0) { // go left
						if (arr[x2][y2 - 1] == 0)
							arr[x2][y2 - 1] = aux;
					}
					if (x2 + 1 < arr.length) { // go down
						if (arr[x2 + 1][y2] == 0)
							arr[x2 + 1][y2] = aux;
					}
					if (y2 + 1 < arr[0].length) { // go right
						if (arr[x2][y2 + 1] == 0)
							arr[x2][y2 + 1] = aux;
					}
					if (x2 - 1 >= 0 && y2 - 1 >= 0) { // go up left
						if (arr[x2 - 1][y2 - 1] == 0)
							arr[x2 - 1][y2 - 1] = aux;
					}
					if (x2 - 1 >= 0 && y2 + 1 < arr[0].length) { // go right up
						if (arr[x2 - 1][y2 + 1] == 0)
							arr[x2 - 1][y2 + 1] = aux;
					}
					if (x2 + 1 < arr.length && y2 - 1 >= 0) { // go down left
						if (arr[x2 + 1][y2 - 1] == 0)
							arr[x2 + 1][y2 - 1] = aux;
					}
					if (y2 + 1 < arr[0].length && x2 + 1 < arr.length) { // go right down
						if (arr[x2 + 1][y2 + 1] == 0)
							arr[x2 + 1][y2 + 1] = aux;
					}
				}
			}
		}
	}

	static boolean isValid(int mat[][], boolean visited[][], int row, int col) { // simple method that check the validity of a movement to mark in bfs
		return ((row >= 0) && (row < mat.length)) && ((col >= 0) && (col < mat[0].length)) && (mat[row][col] != -1)
				&& (!visited[row][col]);
	}

	private static boolean bfsByVal(int matrix[][], int val, int i, int j) { // Lee's algo
		int row[] = { -1, 0, 0, 1 }; // keep movement left right
		int col[] = { 0, -1, 1, 0 }; // keep movement up down

		int startV = matrix[i][j]; // the value we want to reach
		int[][] m2 = new int[matrix.length][matrix[0].length]; // matrix to store distances
		for (int k1 = 0; k1 < m2.length; k1++) {
			for (int k2 = 0; k2 < m2[0].length; k2++)
				m2[k1][k2] = -1;
		}
		boolean[][] visited = new boolean[matrix.length][matrix[0].length];
		Queue<node> q = new LinkedList<node>();
		visited[i][j] = true;
		q.add(new node(i, j, 0));

		int minimum_distance = Integer.MAX_VALUE;
		while (!q.isEmpty()) { // once the q is empty we have exhausted all options to search
			node node = q.poll();
			i = node.x;
			j = node.y;
			int dist = node.distance;
			m2[i][j] = dist;
			if (matrix[i][j] == val && dist != 0) { // if the value has been reached we stop
				minimum_distance = dist;
				break;
			}

			for (int k = 0; k < 4; k++) {
				if (isValid(matrix, visited, i + row[k], j + col[k])) {
					visited[i + row[k]][j + col[k]] = true;

					node n = new node(i + row[k], j + col[k], dist + 1);
					q.add(n);
				}
			}
		}

		if (minimum_distance == Integer.MAX_VALUE) {
			return false; // the case where no path has been identified
		} else {

			int[][] directions = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } }; // dirs to go in x y

			int x2 = i, y2 = j;

			while (m2[x2][y2] != 0) {
				matrix[x2][y2] = startV; // we mark our path
				int current = 0;
				boolean continue1 = true;
				for (int i1 = 1; i1 <= 4 && continue1; i1++) {
					int xdir = directions[current][0];
					int ydir = directions[current][1];
					if (x2 + xdir >= 0 && x2 + xdir < matrix.length && y2 + ydir >= 0 && y2 + ydir < matrix[0].length) {
						if (m2[x2 + xdir][y2 + ydir] - m2[x2][y2] == -1 && m2[x2 + xdir][y2 + ydir] != -1) {
							x2 = x2 + xdir;
							y2 = y2 + ydir;
							continue1 = false;
						}
					}
					current++;
				}
				if (current > 3)
					current = 0;
			}

			return true; // a solution has been found 

		}
	}

	public static int[][] solveMapping(int[][] mainArray, List<ValuesXY> listO, int xZ, int yZ) {
		
		if (xZ != -1) { // xZ yZ are to be marked
			int aux = -1; 
			int x2 = xZ;
			int y2 = yZ;
			System.out.println(x2 + " " + y2);
			if (x2 - 1 >= 0) { // go up
				mainArray[x2 - 1][y2] = aux;
			}
			if (y2 - 1 >= 0) { // go left
				mainArray[x2][y2 - 1] = aux;
			}
			if (x2 + 1 < mainArray.length) { // go down
				mainArray[x2 + 1][y2] = aux;
			}
			if (y2 + 1 < mainArray[0].length) { // go right
				mainArray[x2][y2 + 1] = aux;
			}
			if (x2 - 1 >= 0 && y2 - 1 >= 0) { // go up left
				mainArray[x2 - 1][y2 - 1] = aux;
			}
			if (x2 - 1 >= 0 && y2 + 1 < mainArray[0].length) { // go right up
				mainArray[x2 - 1][y2 + 1] = aux;
			}
			if (x2 + 1 < mainArray.length && y2 - 1 >= 0) { // go down left
				mainArray[x2 + 1][y2 - 1] = aux;
			}
			if (y2 + 1 < mainArray[0].length && x2 + 1 < mainArray.length) { // go right down
				mainArray[x2 + 1][y2 + 1] = aux;
			}

		}

		List<ValuesXY> listO2 = new ArrayList<ValuesXY>(); // a copy that has all the points / pins
		listO2.addAll(listO);

		boolean isFine = true; // is fine is to see if a pin can be connected
		boolean isDoable = true; // is to break us out if a solution has been reached
		boolean nu = true; // we search until we come back at the same priority

		do {

			nu = true;
			isDoable = true;

			int[][] copiedArray = new int[mainArray.length][mainArray[0].length];
			for (int k = 0; k < mainArray.length; k++) {
				for (int k2 = 0; k2 < mainArray[0].length; k2++) {
					copiedArray[k][k2] = mainArray[k][k2];
				}
			} // we store the original array

			List<ValuesXY> listAux = new ArrayList<ValuesXY>();
			for (int i = 0; i < listO.size(); i++) {
				listAux.add(new ValuesXY(listO.get(i)));
			} // we store a mock / test priority 
			// we do an order that tells us which pins have a more crucial connecting need
			// say we start with 1 2 3 4 (each a pin val)
			// but the needed order to route is 3 4 1 2 
			// the algorithm checks what pin value gets blocked off and prioritizes it

			for (int i = 0; i < listAux.size(); i++) { // we go through an order

				int xS = listAux.get(i).l.get(0).x;
				int yS = listAux.get(i).l.get(0).y;

				markWallsS(listAux.get(i).value, copiedArray); // we mark(with "walls" around it) all the values aside from the pin we are at
				isFine = bfsByVal(copiedArray, listAux.get(i).value, xS, yS);

				unMark(copiedArray); // we unmark everything 

				if (isFine == false) { // if a pin is blocked we change the order and break
					isDoable = false; // we mark that we don't have a solution
					ValuesXY aux = new ValuesXY(listO.get(i));
					for (int k = i; k >= 1; k--) {
						listO.set(k, listO.get(k - 1));
					}

					listO.set(0, aux);

					break;
				}

			}

			if (isDoable == true) { // if a solution has been found we break
				mainArray = copiedArray;
				break;
			}

			for (int i = 0; i < listO.size(); i++) {
				if (listO.get(i).value != listO2.get(i).value) {
					nu = false;
					break;
				}
			}

		} while (!nu); // we continue until we return at the order we began = NO SOLUTION EXISTS IN THE CURRENT CONFIGURATION

		return mainArray;
	}

	public static int[][] checkMapping(int[][] mainArray) { // a method for Point 3 - we check intersection points in a given .csv

		for (int i = 0; i < mainArray.length; i++) {
			for (int j = 0; j < mainArray[0].length; j++) {
				if (mainArray[i][j] > 0) {
					int x2 = i, y2 = j;
					if (y2 - 1 >= 0) { // go left
						if (mainArray[x2][y2 - 1] != 0 && mainArray[i][j] != mainArray[x2][y2 - 1]
								&& mainArray[x2][y2 - 1] != -3) {
							mainArray[x2][y2 - 1] = -3;
						}
					}
					if (x2 + 1 < mainArray.length) { // go down
						if (mainArray[x2 + 1][y2] != 0 && mainArray[i][j] != mainArray[x2 + 1][y2]
								&& mainArray[x2 + 1][y2] != -3) {
							mainArray[x2 + 1][y2] = -3;
						}
					}
					if (y2 + 1 < mainArray[0].length) { // go right
						if (mainArray[x2][y2 + 1] != 0 && mainArray[i][j] != mainArray[x2][y2 + 1]
								&& mainArray[x2][y2 + 1] != -3) {
							mainArray[x2][y2 + 1] = -3;
						}
					}
					if (y2 - 1 >= 0) { // go right
						if (mainArray[x2][y2 - 1] != 0 && mainArray[i][j] != mainArray[x2][y2 - 1]
								&& mainArray[x2][y2 - 1] != -3) {
							mainArray[x2][y2 - 1] = -3;
						}
					}
					/*
					 * if (x2 - 1 >= 0 && y2 - 1 >= 0) { // go up left if(mainArray[x2 - 1][y2 - 1]
					 * != 0 && mainArray[i][j] != mainArray[x2 - 1][y2 - 1] && mainArray[x2 - 1][y2
					 * - 1] != -3) { mainArray[x2 - 1][y2 - 1] = -3; } } if (x2 - 1 >= 0 && y2 + 1 <
					 * mainArray[0].length) { // go right up if(mainArray[x2 - 1][y2 + 1] != 0 &&
					 * mainArray[i][j] != mainArray[x2 - 1][y2 + 1] && mainArray[x2 - 1][y2 + 1] !=
					 * -3) { mainArray[i][j] = -3; } } if (x2 + 1 < mainArray.length && y2 - 1 >= 0)
					 * { // go down left if(mainArray[x2 + 1][y2 - 1] != 0 && mainArray[i][j] !=
					 * mainArray[x2 + 1][y2 - 1] && mainArray[x2 + 1][y2 - 1] != -3) {
					 * mainArray[i][j] = -3; } } if (y2 + 1 < mainArray[0].length && x2 + 1 <
					 * mainArray.length) { // go right down if(mainArray[x2 + 1][y2 + 1] != 0 &&
					 * mainArray[i][j] != mainArray[x2 + 1][y2 + 1] && mainArray[x2 + 1][y2 + 1] !=
					 * -3) { mainArray[i][j] = -3; } }
					 */
				}
			}
		}

		return mainArray;

	}

	public static void main(String[] args) { // driver main

		List<ValuesXY> listO = new ArrayList<ValuesXY>();
		List<ValuesXY> listO2 = new ArrayList<ValuesXY>();
		List<ValuesXY> listO3 = new ArrayList<ValuesXY>();
		final String COMMA_DELIMITER = ",";
		List<List<Integer>> records = new ArrayList<>(); // input values in 2d array
		List<List<Integer>> records2 = new ArrayList<>(); // input values in 2d array
		List<List<Integer>> records3 = new ArrayList<>(); // input values in 2d array
		int xZ = -1, yZ = -1;
		try {

			BufferedReader br = new BufferedReader(new FileReader("Step_One.csv")); // for Point 1
			String line;
			int lin = 0;

			while ((line = br.readLine()) != null) {
				String[] values = line.split(COMMA_DELIMITER);
				List<Integer> aux = new ArrayList<Integer>();
				for (int i = 0; i < values.length; i++) {
					if (values[i].equals("Z")) {
						xZ = lin;
						yZ = i;
						aux.add(-2);
					} else {
						int element = Integer.parseInt(values[i]);
						aux.add(element);
						if (element != 0) {
							ValuesXY temp2 = new ValuesXY(element);
							temp2.l.add(new Coords(lin, i));
							listO.add(temp2);
						}
					}
				}
				lin++;
				records.add(aux);
			}
			br.close();

			br = new BufferedReader(new FileReader("Step_Two_Simplified.csv")); // Point 2 but in a version that my algo can handle :)
			// the solution is not perfect and needs improving, but works well (most of the time)

			line = "";
			lin = 0;

			while ((line = br.readLine()) != null) {
				String[] values = line.split(COMMA_DELIMITER);
				List<Integer> aux = new ArrayList<Integer>();
				for (int i = 0; i < values.length; i++) {
					if (values[i].equals("Z")) {
						xZ = lin;
						yZ = i;
						aux.add(-2);
					} else {
						int element = Integer.parseInt(values[i]);
						aux.add(element);
						if (element != 0) {
							ValuesXY temp2 = new ValuesXY(element);
							temp2.l.add(new Coords(lin, i));
							listO2.add(temp2);
						}
					}
				}
				lin++;
				records2.add(aux);
			}
			br.close();

			br = new BufferedReader(new FileReader("Step_Three.csv")); // the given mapping to evaluate

			line = "";
			lin = 0;

			while ((line = br.readLine()) != null) {
				String[] values = line.split(COMMA_DELIMITER);
				List<Integer> aux = new ArrayList<Integer>();
				for (int i = 0; i < values.length; i++) {
					if (values[i].equals("Z")) {
						xZ = lin;
						yZ = i;
						aux.add(-2);
					} else {
						int element = Integer.parseInt(values[i]);
						aux.add(element);
						if (element != 0) {
							ValuesXY temp2 = new ValuesXY(element);
							temp2.l.add(new Coords(lin, i));
							listO3.add(temp2);
						}
					}
				}
				lin++;
				records3.add(aux);
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		int[][] mainArray1 = new int[records.size()][records.get(0).size()];
		int[][] mainArray2 = new int[records2.size()][records2.get(0).size()];
		int[][] mainArray3 = new int[records3.size()][records3.get(0).size()];

		for (int i = 0; i < records.size(); i++) {
			for (int j = 0; j < records.get(0).size(); j++) {
				mainArray1[i][j] = records.get(i).get(j);
			}
		}
		for (int i = 0; i < records2.size(); i++) {
			for (int j = 0; j < records2.get(0).size(); j++) {
				mainArray2[i][j] = records2.get(i).get(j);
			}
		}
		for (int i = 0; i < records3.size(); i++) {
			for (int j = 0; j < records3.get(0).size(); j++) {
				mainArray3[i][j] = records3.get(i).get(j);
			}
		}
		// we store the data in a 2d array for the methods developed

		mainArray1 = solveMapping(mainArray1, listO, -1, -1);
		mainArray2 = solveMapping(mainArray2, listO2, xZ, yZ);
		mainArray3 = checkMapping(mainArray3);
		// we apply the changes needed

		new MainFrame(mainArray1, mainArray2, mainArray3); // we start the UI for a pleasant experience :)

	}

}
