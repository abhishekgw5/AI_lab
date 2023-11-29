package AI;
//assign1 part 1
import java.util.*;

public class TicTacToeNonAI {

    static String[] board;
    static String humanPlayer = "X";
    static String computerPlayer = "O";
    static Random random = new Random();

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
            String s = "";
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

        //checking draw 
        for (int a = 0; a < 9; a++) {
            if (board[a].equals(String.valueOf(a + 1))) {
                break;
            } else if (a == 8) {
                return "draw";
            }
        }

        return null;
    }

    public static int computerMove() {
        System.out.println("Computer's move:");
        List<Integer> availableMoves = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (board[i].equals(String.valueOf(i + 1))) {
                availableMoves.add(i);
            }
        }
        if (!availableMoves.isEmpty()) {
            return availableMoves.get(random.nextInt(availableMoves.size()));
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        board = new String[9];

        for (int i = 0; i < 9; i++) {
            board[i] = String.valueOf(i + 1);
        }

        String winner = null;

        System.out.println("TIC TAC TOE GAME");
        gameBoard();

        System.out.println("X will be for Player-1 & O will be for Computer");
        System.out.println();

        while (winner == null) {
            System.out.println("Your turn : Enter position(number) in board where you want to put X");
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

                if (winner != null) {
                    break;
                }

                int computerMove = computerMove();
                if (computerMove != -1) {
                    board[computerMove] = computerPlayer;
                    gameBoard();
                    winner = gameWinner();
                } else {
                    System.out.println("It's a draw!");
                    break;
                }
            } else {
                System.out.println("Entered position is already taken. Re-enter position.");
            }
        }

        if (winner.equals("draw")) {
            System.out.println("Draw!!");
        } else if (winner.equals(humanPlayer)) {
            System.out.println("You win the game");
        } else {
            System.out.println("Computer wins the game");
        }
    }
}
