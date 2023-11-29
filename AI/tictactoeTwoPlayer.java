package AI;

import java.util.*;

public class tictactoeTwoPlayer {

    static String[] board;
    static String turn;

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
                return "Y";
            }
        }

        for (int a = 0; a < 9; a++) {
            if (Arrays.asList(board).contains(String.valueOf(a + 1))) {
                break;
            } else if (a == 8) {
                return "draw";
            }
        }

        int n;
        if (turn == "X") {
            n = 1;
        } else {
            n = 2;
        }
        System.out.println("Player " + n + "'s turn : Enter position(number) in board where you want to put " + turn);
        return null;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        board = new String[9];

        for (int i = 0; i < 9; i++) {
            board[i] = String.valueOf(i + 1);
        }

        String winner = null;
        turn = "X";

        System.out.println("TIC TAC TOE GAME");
        gameBoard();

        System.out.println("X will be for Player-1 & O will be for Player-2");
        System.out.println("Player 1's turn : Enter position(number) in board where you want to put X");

        while (winner == null) {
            int input;
            try{
                input = sc.nextInt();
                if (!(input > 0 && input <= 9)) {
                System.out.println("Invalid Input. Re-enter input");
                continue;
            }
            }catch(InputMismatchException e){
                System.out.println("Invalid Input. Re-enter input");
                continue;
            }
            

            if (board[input - 1].equals(String.valueOf(input))) {
                board[input - 1] = turn;

                if (turn.equals("X")) {
                    turn = "O";
                } else {
                    turn = "X";
                }

                gameBoard();
                winner = gameWinner();
            } else {
                System.out.println("Entered position is already taken. Re-enter position.");
            }
        }

        int n;
        if (winner == "X") {
            n = 1;
        } else {
            n = 2;
        }

        if (winner.equals("draw")) {
            System.out.println("Draw!!");
        } else {
            System.out.println("Congrats! Player " + n + " wins the game");
        }

    }
}
