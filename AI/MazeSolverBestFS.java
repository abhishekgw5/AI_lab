package AI;
//assign 3 part 1
import java.util.*;

public class MazeSolverBestFS {
    static class Node{
        int x;
        int y;
        Node parent;

        Node(int x,int y,Node parent){
            this.x=x;
            this.y=y;
            this.parent=parent;
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

    public static boolean bestFS(Node start,Node end){
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node->heuristic(node,end)));
        pq.add(start);
        while(!pq.isEmpty()){
            Node current = pq.remove();
            int currRow = current.x;
            int currCol = current.y;

            if (currRow==end.x && currCol==end.y) {
                printPath(current);
                return true;
            }

            if(isValid(currRow, currCol)){
                maze[currRow][currCol] = 2;
                int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
                for(int[] d : directions){
                    int newRow = currRow + d[0];
                    int newCol = currCol + d[1];
                    if(isValid(newRow, newCol)){
                        pq.add(new Node(newRow, newCol, current));
                    }
                }
            }
        }
        return false;
    }

    public static void printPath(Node current){
        List<Node> path = new ArrayList<>();
        while(current!=null){
            path.add(current);
            current=current.parent;
        }
        Collections.reverse(path);
        for(int i=0;i<path.size();i++){
            System.out.println("(" + path.get(i).x + ", " + path.get(i).y + ")");
        }
    }

    public static int heuristic(Node current, Node end){
        return Math.abs(current.x-end.x) - Math.abs(current.y - end.y);
    }

    public static void main(String[] args) {
        Node start = new Node(0, 0, null);
        Node end = new Node(numRows-1,numCols-1,null);

        if (bestFS(start,end)) {
            System.out.println("Congratulations! You found the exit.");
        } else {
            System.out.println("No path to the exit.");
        }
    }
}

