public class GameLogic {
    
    private char[] board;

    public GameLogic() {
        reset();
    }

    public void reset() {

        board = new char[9];
        for (int i = 0; i < 9; i++) {

            board[i] = ' ';

        }

    }

    public boolean makeMove(int index, char player) {

        if (board[index] == ' ') {
            board[index] = player;
            return true;

        }

        return false; 

    }

    public char getCell(int index) {
        return board[index];
    }

    public char[] getBoard() {
        return board;
    }

    
    public char checkWinner() {

        int[][] lines = {

            {0,1,2}, {3,4,5}, {6,7,8}, 
            {0,3,6}, {1,4,7}, {2,5,8}, 
            {0,4,8}, {2,4,6}           

        };

        for (int[] line : lines) {

            if (board[line[0]] != ' '
                && board[line[0]] == board[line[1]]

                && board[line[1]] == board[line[2]]) {

                return board[line[0]];

            }
        }

        return ' ';

    }

    
    public int[] getWinningLine() {

        int[][] lines = {

            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}

        };

        for (int[] line : lines) {
            if (board[line[0]] != ' '
                && board[line[0]] == board[line[1]]
                && board[line[1]] == board[line[2]]) {
                return line;

            }
        }

        return null;

    }

    public boolean isBoardFull() {
        for (char c : board) {
            if (c == ' ') return false;
        }
        return true;

    }
    
}