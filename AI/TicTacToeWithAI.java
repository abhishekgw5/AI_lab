package AI;
//assignment 1 
import java.util.*;

public class TicTacToeWithAI {

    static String[] board;
    static String humanPlayer = "X";
    static String aiPlayer = "O";

    static void gameBoard() {
        System.out.println();
        System.out.println(board[0] + "  " + board[1] + "  " + board[2]);
        System.out.println();
        System.out.println(board[3] + "  " + board[4] + "  " + board[5]);
        System.out.println();
        System.out.println(board[6] + "  " + board[7] + "  " + board[8]);
        System.out.println();
    }

    public static String gameWinner() {
        for (int i = 0; i < 8; i++) {
            String s = null;
            switch (i) {
                case 0:
                    s = board[0] + board[1] + board[2];
                    break;
                case 1:
                    s = board[3] + board[4] + board[5];
                    break;
                case 2:
                    s = board[6] + board[7] + board[8];
                    break;
                case 3:
                    s = board[0] + board[3] + board[6];
                    break;
                case 4:
                    s = board[1] + board[4] + board[7];
                    break;
                case 5:
                    s = board[2] + board[5] + board[8];
                    break;
                case 6:
                    s = board[0] + board[4] + board[8];
                    break;
                case 7:
                    s = board[2] + board[4] + board[6];
                    break;
            }

            if (s.equals("XXX")) {
                return "X";
            } else if (s.equals("OOO")) {
                return "O";
            }
        }

        // for (int a = 0; a < 9; a++) {
        //     if (Arrays.asList(board).contains(String.valueOf(a + 1))) {
        //         break;
        //     } else if (a == 8) {
        //         return "draw";
        //     }
        // }
        for (int a = 0; a < 9; a++) {
            if(board[a].equals(String.valueOf(a+1))){
                return null;
            }else if(a==8){
                return "draw";
            }
        }

        return null;
    }

    // Minimax algorithm 
    //implementation for AI player
    public static int minimax(boolean isMaximizer) {
        String result = gameWinner();

        if (result != null) {
            if (result.equals(humanPlayer)) {
                return -1;
            } else if (result.equals(aiPlayer)) {
                return 1;
            } else {
                return 0;
            }
        }

        if (isMaximizer) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i].equals(String.valueOf(i + 1))) {
                    board[i] = aiPlayer;
                    bestScore = Math.max(bestScore, minimax(false));
                    board[i] = String.valueOf(i + 1);
                }
            }
            return bestScore;
        } else {        
            //for opponents move
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i].equals(String.valueOf(i + 1))) {
                    board[i] = humanPlayer;
                    bestScore = Math.min(bestScore, minimax(true));
                    board[i] = String.valueOf(i + 1);
                }
            }
            return bestScore;
        }
    }

    /// AI makes a move using minimax algorithm
    public static int aiMove() {
        System.out.println("AI's move:");
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int i = 0; i < 9; i++) {
            if (board[i].equals(String.valueOf(i + 1))) {
                board[i] = aiPlayer;
                int score = minimax(false);
                board[i] = String.valueOf(i + 1);

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }

        return bestMove;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        board = new String[9];

        for (int i = 0; i < 9; i++) {
            board[i] = String.valueOf(i + 1);
        }
        

        System.out.println("TIC TAC TOE GAME");
        gameBoard();

        String winner = null;
        System.out.println("X will be for Player1 & O will be for AI");

        while (winner == null) {
            System.out.println("Player 1's turn : Enter position(number) in board where you want to put X");
            int input;
            try {
                input = sc.nextInt();
                if (!(input > 0 && input <= 9)) {
                    System.out.println("Invalid Input. Re-enter input");
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Re-enter input");
                sc.next(); 
                continue;
            }

            if (board[input - 1].equals(String.valueOf(input))) {
                board[input - 1] = humanPlayer;
                gameBoard();
                winner = gameWinner();

                if (winner != null) {//check winner again..
                    break;
                }

                //play for ai
                int aiMove = aiMove();
                board[aiMove] = aiPlayer;
                gameBoard();
                winner = gameWinner();
            } else {
                System.out.println("Entered position is already taken. Re-enter position.");
            }
        }

        if (winner.equals("draw")) {
            System.out.println("Draw!!");
        } else if (winner.equals(humanPlayer)) {
            System.out.println("Congrats! You win the game");
        } else {
            System.out.println("AI wins the game");
        }
    }
}
