package AI.AI_prac;

import java.util.*;

public class EightPuzzle_BFS {
    static class State{
        int board[][];
        State parent;

        public State(int[][] board, State parent){
            this.board = board;
            this.parent = parent;
        }
    }

    public static final int[][] GOAL_STATE = {
        { 1, 2, 3 },
        { 8, 0, 4 },
        { 7, 6, 5 }
    };

    public static void printBoard(int[][] board){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean bfs(int[][] initial){
        Queue<State> q = new LinkedList<>();
        q.add(new State(initial, null));
        while(!q.isEmpty()){
            State curr = q.remove();
            int[][] currBoard = curr.board;
            if(Arrays.deepEquals(currBoard, GOAL_STATE)){
                while(curr.parent!=null){
                    printBoard(curr.board);
                    curr=curr.parent;
                }
                return true;
            }

            int blankRow = -1, blankCol = -1;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (currBoard[i][j] == 0) {
                        blankRow = i;
                        blankCol = j;
                        break;
                    }
                }
            }
            int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
            for(int[] d : directions){
                int newRow = blankRow + d[0];
                int newCol = blankCol + d[1];
                if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                    int[][] newBoard = new int[3][3];
                    for (int i = 0; i < 3; i++) {
                        newBoard[i] = currBoard[i].clone();
                    }
                    newBoard[blankRow][blankCol] = newBoard[newRow][newCol];
                    newBoard[newRow][newCol] = 0;
                    q.add(new State(newBoard,curr));
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] initialBoard = {
            { 2, 8, 3 },
            { 1, 6, 4 },
            { 7, 0, 5 }
        };
        if(!bfs(initialBoard)){
            System.out.println("No");
        }
    }
}
