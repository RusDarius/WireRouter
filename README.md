# WireRouter
An application to wire pins according to certain rules

Remarks about the problem:

First and foremost, the connection traces must not touch or intersect. By this, we mean that
there must always be at least one blank space between any given point in a connection trace
and its nearest neighboring trace (orthogonally and diagonally). All the pins in the challenge
input files are spaced so that there is at least one cell with a value of zero between any pin and
its nearest neighbor, so your traces should follow the same rule.

The traced routes must be contiguous.

Also, the space in which to route these traces is limited. The size of the matrix in the input files is
the maximum size for your playground. This is not an issue for step one, as the routing is simple.
However, step two is a different story.

The solution:

First, the idea would be to generate a path between pins only knowing their positions in the 2D array. 
The optimal solution for this would be BFS (Breadth First Search) in Lee’s version. 
A wave pattern that marks distances and when reaching the target gives a path via a queue.

In the method used I check the validity of a path (walls / obstacles) and 
mark it in a result array and return true or false based on the existence of a solution.
	
The method is a bit different because it searches based on reaching a value (pins 1 and 1 or 2 and 2 and so on) and 
so I only send the start and the desired target value.

The main menu has Solve Example 1, 2. (Step_One.csv, Step_Two_Simplified.csv). Check Step_3 (Step_Three.csv) 
and the results are simple to perceive.

In Custom Netlist we can make our own 2D array and mark it with pins (1-99). 
The way to use it is to click a cell, it then gets marked as green because we have picked it, and then type the pin value (1 - 99). 
We use “Z” to mark obstacles.
In custom to mark an obstacle use “z” not “Z” (easier). We can use the sliders to pick the size of the netlist / map.
After the input is given we close and click Solve Custom Netlist

