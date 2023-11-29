package AI.AI_prac;

public class soduku {
    static boolean solveSudoku(int [][] board){
        
        int [] unassigned = findUnassignedLocation(board);
        int row = unassigned[0];
        int col = unassigned[1];

        if(row == -1 && col == -1) {
            printS(board);
            return true;
        }

        for(int num = 1; num <= 9; num++){
            if(isSafe(board, row, col, num)){
                board[row][col] = num;

                if(solveSudoku(board)) return true;

                board[row][col] = 0;
            }
        }
        return false;
    }

    static int[] findUnassignedLocation(int [][] board){
        int [] res = new int[]{-1, -1};
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board.length; j++){
                if(board[i][j] == 0){
                    res[0] = i;
                    res[1] = j;
                }
            }
        }
        return res;
    }

    static boolean isSafe(int [][] board, int row, int col, int num){
        return !usedInRow(board, row, num) && !usedInCol(board, col, num) && !usedInBox(board, row - row%3, col - col%3, num);
    }

    static boolean usedInRow(int [][] board, int row, int num){
        for(int i = 0; i<board.length; i++){
            if(board[row][i] == num) return true;
        }
        return false;
    }

    static boolean usedInCol(int [][] board, int col, int num){
        for(int i = 0; i<board.length; i++){
            if(board[i][col] == num) return true;
        }
        return false;
    }

    static boolean usedInBox(int [][] board, int boxRStart, int boxCStart, int num){
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                if(board[i+boxRStart][j+boxCStart] == num) return true;
            }
        }
        return false;
    }

    static void printS(int [][] board){
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board.length; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int [][] sudoku = new int[9][9];
        for(int i = 0; i<sudoku.length; i++){
            for(int j = 0; j<sudoku.length; j++){
                sudoku[i][j] = 0;
            }
        }
        if(solveSudoku(sudoku)){
            System.out.println("Solved");
        }
        else{
            System.out.println("Not solved");
        }
    }
}
