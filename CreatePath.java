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


	while(!visitedPoints.isEmpty()) {

		//randomly decide to turn even if there are possible neighbors
		int decideToTurn = ThreadLocalRandom.current().nextInt(0, 4);
		if(decideToTurn == 0) {
			if (visitedPoints.size() > 5) {
				System.out.println(visitedPoints.size());
				int randomCell = ThreadLocalRandom.current().nextInt(0, visitedPoints.size());
				currentCell = visitedPoints.get(randomCell);}
		}
		//create vector of all possible neighbors
		Vector<Integer> neighbourList = hasNeighbors(grid, currentCell);


		if(!neighbourList.isEmpty()) {
			Vector<Integer> neighbours = hasNeighbors(grid, currentCell);

			int low = 0;
			int high = neighbours.size()-1;
			int index = ThreadLocalRandom.current().nextInt(low, high + 1);

			int neighbour = neighbourList.get(index);

			if(neighbour == 1) {
				grid[currentCell[0]][currentCell[1]-2] = true;
				grid[currentCell[0]][currentCell[1]-1] = true;
				int[] p = {currentCell[0], currentCell[1]-2};
				visitedPoints.add(p);
				System.out.println("push: " + currentCell[0] + " " + currentCell[1]);
				currentCell[1] = currentCell[1]-2;


			}
			else if (neighbour == 2) {
				grid[currentCell[0]+2][currentCell[1]] = true;
				grid[currentCell[0]+1][currentCell[1]] = true;
				System.out.println("push: " + currentCell[0] + " " + currentCell[1]);
				int[] p = {currentCell[0]+2, currentCell[1]};
				visitedPoints.add(p);
				currentCell[0] = currentCell[0]+2;


			}
			else if (neighbour == 3) {
				grid[currentCell[0]][currentCell[1]+2] = true;
				grid[currentCell[0]][currentCell[1]+1] = true;
				System.out.println("push: " + currentCell[0] + " " + currentCell[1]);
				int[] p = {currentCell[0], currentCell[1]+2};
				visitedPoints.add(p);
				currentCell[1] = currentCell[1]+2;


			}
			else {
				grid[currentCell[0]-2][currentCell[1]] = true;
				grid[currentCell[0]-1][currentCell[1]] = true;
				System.out.println("push: " + currentCell[0] + " " + currentCell[1]);
				int[] p = {currentCell[0]-2, currentCell[1]};
				visitedPoints.add(p);
				currentCell[0] = currentCell[0]-2;


			}

		}
		else {
			System.out.println("pop occuring");
			currentCell = visitedPoints.lastElement();
			visitedPoints.remove(visitedPoints.size()-1);
			System.out.println(currentCell[0] + " " + currentCell[1]);

		}
	}
	//printMaze(grid);
	return grid;
}
