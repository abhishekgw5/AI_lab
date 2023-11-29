package AI;
//assign2 part1
import java.util.*;

public class MazeSolverBFS {
    static class Node {
        int x, y;
        Node parent;

        public Node(int x, int y, Node parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }

    static int[][] maze = {
        // {0, 0, 1, 1, 1},
        // {1, 0, 1, 0, 1},
        // {1, 0, 0, 1, 1},
        // {1, 1, 0, 0, 0},
        // {1, 1, 1, 1, 0}
        {0,0,0,1,1},
        {0,1,1,0,1},
        {0,0,0,1,1},
        {1,0,0,0,1},
        {1,1,0,0,0}
    };

    static int numRows = maze.length;
    static int numCols = maze[0].length;

    static boolean isValid(int row, int col) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols && maze[row][col] == 0;
    }

    static boolean bfs(Node start,Node end) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int row = current.x;
            int col = current.y;

            if (current.x==end.x && current.y==end.y) {
                printPath(current);
                return true;
            }

            if (isValid(row, col)) {
                maze[row][col] = 2; 
                int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
                for (int[] dir : directions) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];
                    if (isValid(newRow, newCol)) {
                        queue.add(new Node(newRow, newCol,current));
                    }
                }
            }
        }
        return false; 
    }

    public static void printPath(Node current){
        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);

        for(int i=0;i<path.size();i++){
            System.out.println("(" + path.get(i).x + ", " + path.get(i).y + ")");
        }
    }  

    public static void main(String[] args) {
        int endRow = numRows - 1;
        int endCol = numCols - 1;

        Node start = new Node(0,0,null);
        Node end = new Node(endRow,endCol,null);


        if (bfs(start,end)) {
            System.out.println("Congratulations! You found the exit.");
        } else {
            System.out.println("No path to the exit.");
        }
    }
}
