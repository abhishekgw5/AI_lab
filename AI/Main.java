package AI;
import java.util.*;

class Node {
    int[][] state;
    Node parent;
    String action;
    int depth;

    public Node(int[][] state, Node parent, String action, int depth) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.depth = depth;
    }
}

public class Main {

    public static void main(String[] args) {
        int[][] initial = {
            {1, 2, 3},
            {8, 0, 4},
            {7, 6, 5}
        };

        int[][] goal = {
            {1, 2, 3},
            {8, 0, 4},
            {7, 6, 5}
        };

        solveEightPuzzle(initial, goal);
    }

    public static void solveEightPuzzle(int[][] initial, int[][] goal) {
        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        Node rootNode = new Node(initial, null, "", 0);
        queue.add(rootNode);
        visited.add(Arrays.deepToString(initial));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            
            if (Arrays.deepEquals(current.state, goal)) {
                printSolution(current);
                return;
            }

            int[] emptyPosition = findEmptyTile(current.state);
            int row = emptyPosition[0];
            int col = emptyPosition[1];

            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            String[] actionNames = {"Up", "Down", "Left", "Right"};

            for (int i = 0; i < directions.length; i++) {
                int newRow = row + directions[i][0];
                int newCol = col + directions[i][1];

                if (isValidPosition(newRow, newCol)) {
                    int[][] newState = swapTiles(current.state, row, col, newRow, newCol);
                    if (!visited.contains(Arrays.deepToString(newState))) {
                        Node newNode = new Node(newState, current, actionNames[i], current.depth + 1);
                        queue.add(newNode);
                        visited.add(Arrays.deepToString(newState));
                    }
                }
            }
        }

        System.out.println("No solution found.");
    }

    public static int[] findEmptyTile(int[][] state) {
        int[] position = new int[2];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == 0) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return null;
    }

    public static boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3;
    }

    public static int[][] swapTiles(int[][] state, int row1, int col1, int row2, int col2) {
        int[][] newState = new int[3][3];
        for (int i = 0; i < state.length; i++) {
            newState[i] = Arrays.copyOf(state[i], state[i].length);
        }

        int temp = newState[row1][col1];
        newState[row1][col1] = newState[row2][col2];
        newState[row2][col2] = temp;

        return newState;
    }

    public static void printSolution(Node node) {
        Stack<String> actions = new Stack<>();
        while (node != null) {
            if (!node.action.isEmpty()) {
                actions.push(node.action);
            }
            node = node.parent;
        }

        while (!actions.isEmpty()) {
            System.out.print(actions.pop() + " ");
        }
        System.out.println();
    }
}
