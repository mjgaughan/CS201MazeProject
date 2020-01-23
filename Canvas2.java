//Canvas2.java
//Duncan McCabe and Jake Gaughan
// Originally from BoxGame.java

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;


public class Canvas2 extends Canvas implements MouseListener {

	public long start;
	public double time = 0;


	public void start() {
		//begins the gameplay of the maze, sets the timer
		start = System.currentTimeMillis();
		System.out.println("Start: "+ start);
		
	}

	public void moveCurr(int x, int y) {

		//gets where the user is
		int k = currentplace[0] + x;
        int z = currentplace[1] + y;
        //System.out.println(k);
        if (k == n-1 && z == n-1) {
        	quit();
        }
        //use of sep. array graph is to disallow any out of bounds exceptions
        if (box[k][z - 1] == true ||
        	box[k-1][z] == true ||
        	box[k+1][z] == true ||
        	box[k][z + 1] == true) {
        	//if it is a playable square enter it
        	if (gridCells[k-1][z-1]) {
        		box[k][z] = true;
        		currentplace[0] += x;
        		currentplace[1] += y;
        	}
        	//end the gameplay if the user reaches the last box
        	if (k == n && z == n) {
            	quit();
            }
        }
        //System.out.println(currentplace[0] + "," + currentplace[1]);
        repaint();
	}

	public void newMaze() {
		//make an array that stores the vertices of the maze
		//(each cell represented by a point)
		//
		// gridCells = createPath(gridCells);
		currentplace[0] = 1;
		currentplace[1] = 1;
		// clears out all gameplay values from last game
		for (int i = 0; i < n + 1; i++) {
        	for (int j = 0; j < n + 1; j++) {
        		box[i][j]= false;
        	}
		}
		// new game
		gridCells = gridHelper();
		repaint();
		start();
	}

	// the main driver of the game, creates random mazes using depth first search
	public boolean[][] createPath(boolean[][] grid) {

		//create stack of all points visited
		//Stack<int[]> visitedPoints = new Stack<int[]>();
		Vector<int[]> visitedPoints = new Vector<int[]>();
		//create starting cell at the origin
		int[] currentCell = new int[2];
		currentCell[0] = 0;
		currentCell[1] = 0;
		//set origin to true
		grid[0][0] = true;
		//push the origin on the stack
		int[] begin = {0,0};
		visitedPoints.add(begin);

		// while the stack isnt empty
		while(!visitedPoints.isEmpty()) {

			//randomly decide to turn even if there are possible neighbors
			// makes the game harder
			//NOTE: all "neighbors" are two away (so there are walls)
			int decideToTurn = ThreadLocalRandom.current().nextInt(0, 8);
			if(decideToTurn == 0) {
				if (visitedPoints.size() > 5) {
					System.out.println(visitedPoints.size());
					int randomCell = ThreadLocalRandom.current().nextInt(0, visitedPoints.size());
					currentCell = visitedPoints.get(randomCell);}
			}
			//create vector of all possible neighbors
			Vector<Integer> neighbourList = hasNeighbors(grid, currentCell);

			// if the current cell has unvisited neighbors
			if(!neighbourList.isEmpty()) {
				Vector<Integer> neighbours = hasNeighbors(grid, currentCell);
				// randomly select which one to jump to
				int low = 0;
				int high = neighbours.size()-1;
				int index = ThreadLocalRandom.current().nextInt(low, high + 1);

				int neighbour = neighbourList.get(index);
				// one is the N neighbor
				if(neighbour == 1) {
					//make the neighbor and the one in between true
					grid[currentCell[0]][currentCell[1]-2] = true;
					grid[currentCell[0]][currentCell[1]-1] = true;
					int[] p = {currentCell[0], currentCell[1]-2};
					// add onto the stack
					visitedPoints.add(p);
					//trouble shooting
					System.out.println("push: " + currentCell[0] + " " + currentCell[1]);
					// move the current cell up to the N neighbor
					currentCell[1] = currentCell[1]-2;


				}
				// same for the E neighbor
				else if (neighbour == 2) {
					grid[currentCell[0]+2][currentCell[1]] = true;
					grid[currentCell[0]+1][currentCell[1]] = true;
					System.out.println("push: " + currentCell[0] + " " + currentCell[1]);
					int[] p = {currentCell[0]+2, currentCell[1]};
					visitedPoints.add(p);
					currentCell[0] = currentCell[0]+2;


				}
				// same for the S neighbor
				else if (neighbour == 3) {
					grid[currentCell[0]][currentCell[1]+2] = true;
					grid[currentCell[0]][currentCell[1]+1] = true;
					System.out.println("push: " + currentCell[0] + " " + currentCell[1]);
					int[] p = {currentCell[0], currentCell[1]+2};
					visitedPoints.add(p);
					currentCell[1] = currentCell[1]+2;


				}
				// same for the W neighbor
				else {
					grid[currentCell[0]-2][currentCell[1]] = true;
					grid[currentCell[0]-1][currentCell[1]] = true;
					System.out.println("push: " + currentCell[0] + " " + currentCell[1]);
					int[] p = {currentCell[0]-2, currentCell[1]};
					visitedPoints.add(p);
					currentCell[0] = currentCell[0]-2;
				}

			}
			// if no unvisited neighbors
			else {
				// pop off the stack and move the current to there
				System.out.println("pop occuring");
				currentCell = visitedPoints.lastElement();
				visitedPoints.remove(visitedPoints.size()-1);
				System.out.println(currentCell[0] + " " + currentCell[1]);

			}
		}
		//printMaze(grid);
		return grid;
	}


	//checks whether or not each of the four neighbors is visited
		//or is a wall: returns an array with [1,2,3,4] if all neighbors are open
		// returns 0s in indexes of visited neighbors
    public Vector<Integer> hasNeighbors(boolean[][] grid, int[] currentcell) {
    	int[] returnable= new int[4];
    	for (int i = 0; i < 4; i++) {
    		returnable[i] = 0;
    	}
    	int x = currentcell[0];
    	int y = currentcell[1];

    	//check for border cases
    	//if top row
    	//............................................................
    	if(y<2) {
    		if(x<2) {
    			if(!grid[x+2][y]) {
    				returnable[1]= 2;
    			}

    		}
    		// top right corner
    		else if (x>n-2) {
    			if(!grid[x-2][y]) {
    				returnable[3] = 4;
    			}
    		}
    		// middle top
    		else {
    			if(!grid[x-2][y]) {
    				returnable[3] = 4;
    			}
    			if(!grid[x+2][y]) {
    				returnable[1]= 2;
    			}

    		}

			if(!grid[x][y+2]) {
				returnable[2] = 3;
			}
    	}
		//......................................................................

		//if bottom row
		else if (y>n-2) {
			if(x<2) {
    			if(!grid[x+2][y])
    				returnable[1]= 2;
			}
			else if(x>n-2) {
    			if(!grid[x-2][y])
    				returnable[3] = 4;
			}
			else {
    			if(!grid[x-2][y])
    				returnable[3] = 4;
    			if(!grid[x+2][y])
    				returnable[1]= 2;
			}
			if(!grid[x][y-2])
				returnable[0] = 1;
		}

		//if left side
		else if(x<2) {
			if(!grid[x][y-2])
				returnable[0] = 1;
			if(!grid[x+2][y])
				returnable[1]= 2;
			if(!grid[x][y+2])
				returnable[2] = 3;
		}

    	//if right side
		else if (x>n-2) {
			if(!grid[x][y-2])
				returnable[0] = 1;
			if(!grid[x][y+2])
				returnable[2] = 3;
			if(!grid[x-2][y])
				returnable[3]= 4;
		}
    	//.........................................................................

    	//if middle - no out of bounds exceptions
		else {
			if(!grid[x][y-2])
				returnable[0] = 1;
			if(!grid[x+2][y])
				returnable[1]= 2;
			if(!grid[x][y+2])
				returnable[2] = 3;
			if(!grid[x-2][y])
				returnable[3]= 4;
		}

    	//for (int i = 0; i < 4; i++) {
    	//	System.out.print(returnable[i] + ", ");
    	//}

    	//creating the vector to return in a clean fashion
    	System.out.println();
    	Vector<Integer> neighbors = new Vector<Integer>();
    	for (int i = 0; i < 4; i++) {
    		if (returnable[i] != 0) {
    			neighbors.add(i + 1);

    		}
    	}
    	return neighbors;

    }


	public void quit() {
		//function to end gameplay
		long stop = System.currentTimeMillis();
		time = (-(start - stop) / 1000.0);
		System.out.println("quit " + time);
		//JOptionPane.showMessageDialog(null, "Final Time: " + timer);

	}

	// instance variables representing the game go here
	// size of board
    int n = 31;
    // gameplay boolean array
    boolean[][] box = new boolean[n + 2][n + 2];
    // size of the blocks
    int size = 20;
    int border = 20;
    //maze bnoolean array
    boolean[][] gridCells = new boolean[2*n-1][2*n-1];
    // store where the user is currently on the board
    int[] currentplace = {1, 1};

    //helps to initialize new games
    private boolean[][] gridHelper() {
    	boolean[][] grids = new boolean[2*n-1][2*n-1];
    	for(int i = 0; i < n + 1; i++) {
			for(int j = 0; j<n + 1; j++){
					grids[j][i] = false;}
		}
    	grids = createPath(grids);
    	return grids;
    }


    // draw the boxes
    public void paint(Graphics g) {
    	//boolean[][] gridCells = new boolean[2*n-1][2*n-1];
        for (int i = 0; i < n; i++) {
        	for (int j = 0; j < n; j++) {
        		
        		//if a treaded square
        		if (box[i+1][j+1])
                    g.setColor(Color.green);
        		// if a wall
        		else if (!gridCells[i][j])
        			g.setColor(Color.black);
        		//if a playable but untreaded square
                else
                	g.setColor(Color.white);
        		
        		// beginning and ends are green
        		if(i == 0 && j == 0 || i == n - 1 && j == n - 1) {
        			box[i+1][j+1] = true;
        			g.setColor(Color.green);
        		}
        		 
                int x = i * size + border;
                int y = j * size + border;
                g.fillRect(x, y, size, size);
                g.setColor(Color.black);
                g.drawRect(x, y, size, size);
        	}
        	
        	//draw the red cursor to show the current place
        	g.setColor(Color.red);
        	int x = (currentplace[0]-1) * size + border;
            int y = (currentplace[1]-1) * size + border;
            g.fillRect(x, y, size, size);
            g.setColor(Color.black);
            g.drawRect(x, y, size, size);
        	
        }
    }

    // handle mouse events
    // secondary method of gameplay
    public void mousePressed(MouseEvent event) {
        Point p = event.getPoint();

        // check if clicked in box area
        int x = p.x - border;
        int y = p.y - border;
        // if in bounds
        if (x >= 0 && x < n*size &&
            y >= 0 && y < n*size) {
        	// get the location
            int k = x / size + 1;
            int z = y / size + 1;
            //System.out.println(k);
            //start the game
            if (k == 1 && z == 1) {
            	start();
            }
            //check if a square around it was already clicked (no jumping)
            if (box[k][z - 1] == true ||
            	box[k-1][z] == true ||
            	box[k+1][z] == true ||
            	box[k][z + 1] == true) {
            	// if a part of the maze
            	if (gridCells[k-1][z-1]) {
            		box[k][z] = true;
            		currentplace[0] = k;
            		currentplace[1] = z;
            	}
            }
        }
        repaint();
    }

    // methods called from the event handler of the main applet


    // need these also because we implement a MouseListener
    public void mouseReleased(MouseEvent event) { }
    public void mouseClicked(MouseEvent event) { }
    public void mouseEntered(MouseEvent event) { }
    public void mouseExited(MouseEvent event) { }



// troubleshooting helper function, printed out the maze
    public void printMaze(boolean[][] m) {
    	for(int i =0; i<n+1; i++) {
    		for (int j = 0; j<n+1; j++) {
    			if(m[j][i])
    				System.out.print("  " + m[j][i] + "," );
    			else
    				System.out.print(" " + m[j][i] + "," );
    		}
    		System.out.println();
    		System.out.println();
    	}
    }

}
