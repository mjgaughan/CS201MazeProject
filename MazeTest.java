import java.util.*;

public class MazeTest{

    public static void MazeToast(){

        int n = 11;
        boolean[][] grid = new boolean[n][n];
        Stack<E> stackable = new Stack<E>();
        Integer[] firstcurrent = new Integer[3];


        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                grid[i][j] = false;
            }
        }

        grid[0][0] = true;
        firstcurrent = [1,0,0];
        stackable.push(firstcurrent);

        //current = grid[0][0];
        System.out.println(stackable);

        /*
        while (!stackable.isEmpty()){

        }
        */
    }

    public static void main(String[] args) {
        MazeToast();
    }
}
