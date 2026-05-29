import java.util.ArrayList;
import java.util.Random;


public class AIPlayer {

    private char aiMark;     
    private char humanMark;  

    private Random random = new Random();

    public AIPlayer(char aiMark, char humanMark) {

        this.aiMark = aiMark;
        this.humanMark = humanMark;

    }

    
    public int getMove(GameLogic game, String difficulty) {

        switch (difficulty) {
            case "Easy":   return getRandomMove(game);

            case "Medium": return getMediumMove(game);

            case "Hard":   return getBestMove(game); 
            default:       return getRandomMove(game);

        }

    }

    
    private int getRandomMove(GameLogic game) {

        ArrayList<Integer> empty = getEmptyCells(game);

        return empty.get(random.nextInt(empty.size()));

    }

    
    private int getMediumMove(GameLogic game) {

        if (random.nextBoolean()) {

            return getBestMove(game);

        }
        return getRandomMove(game);

    }

    
    private int getBestMove(GameLogic game) {

        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;
        char[] board = game.getBoard();

        for (int i = 0; i < 9; i++) {

            if (board[i] == ' ') {
                board[i] = aiMark;
                int score = minimax(game, 0, false);
                board[i] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;

                }
            }

        }
        return bestMove;

    }

    
    private int minimax(GameLogic game, int depth, boolean isMaximizing) {

        char winner = game.checkWinner();
        if (winner == aiMark)    return 10 - depth;

        if (winner == humanMark) return depth - 10;

        if (game.isBoardFull())  return 0;

        char[] board = game.getBoard();

        if (isMaximizing) {

            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {

                if (board[i] == ' ') {

                    board[i] = aiMark;
                    best = Math.max(best, minimax(game, depth + 1, false));
                    board[i] = ' ';
                }

            }

            return best;
        } 
        
        else {

            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = humanMark;
                    best = Math.min(best, minimax(game, depth + 1, true));
                    board[i] = ' ';

                }

            }

            return best;
        }
    }

    private ArrayList<Integer> getEmptyCells(GameLogic game) {
        ArrayList<Integer> empty = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (game.getCell(i) == ' ') empty.add(i);
        }
        return empty;

    }
    
}