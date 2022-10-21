/**
 * This class stores information about tiles placed on the game-board
 * and implements support methods needed by the algorithm that plays the game.
 */
public class Board {
    private int board_size; // represents the board size
    private int empty_positions; // represents how many tiles must remain empty
    private int max_levels; // the quality of the computer player
    private char[][] theBoard; // list of chars represent the board


    /**
     * Initialize the instance variables and construct the board (empty tiles, initially).
     *
     * @param board_size Board size.
     * @param empty_positions Number of tiles that must remain empty.
     * @param max_levels Quality of computer player.
     */
    public Board(int board_size, int empty_positions, int max_levels) {

        this.board_size = board_size;
        this.empty_positions = empty_positions;
        this.max_levels = max_levels;

        theBoard = new char[board_size][board_size];
        for (int i = 0; i < board_size; i++)
            for (int j = 0; j < board_size; j++)
                theBoard[i][j] = 'e';
    }

    /**
     * @return An empty Dictionary with fixed prime number size of 9973.
     */
    public Dictionary makeDictionary() {
        return new Dictionary(9973);
    }

    /**
     * Represent the content of theboard[][] array to a string,
     * then check if there a data item in the dict referenced by dict with key string.
     *
     * @param dict Dictionary to check if there is a data item in.
     * @return The score associated with the data item, otherwise -1.
     */
    public int repeatedLayout(Dictionary dict) {
        String s = "";
        for (int i = 0; i < board_size; i++)
            for (int j = 0; j < board_size; j++)
                // represent the content of 2D array from top to bottom
                s += theBoard[j][i];

        // return the score associated with data item or -1
        return dict.getScore(s);
    }

    /**
     * Stores theboard[][] in a string, then creates a layout
     * with the string and score and stores the layout in the dict.
     *
     * @param dict Dictionary to store the layout in.
     * @param score Score of the layout.
     */
    public void storeLayout(Dictionary dict, int score) {
        String s = "";
        for (int i = 0; i < board_size; i++)
            for (int j = 0; j < board_size; j++)
                s += theBoard[j][i];

        // Try to avoid the exception if the layout is already in the dict.
        try {
            dict.put(new Layout(s, score));
        } catch (DictionaryException e) {
            e.printStackTrace();
        }
    }

    /**
     * Store the symbol in the board.
     *
     * @param i Number of row.
     * @param j Number of column.
     * @param symbol Symbol to store.
     */
    public void saveTile(int i, int j, char symbol) {
        theBoard[i][j] = symbol;
    }

    /**
     * @param row Number of row.
     * @param col Number of column.
     * @return True if a tile with given coordinates is empty, false otherwise.
     */
    public boolean positionIsEmpty(int row, int col) {
        return theBoard[row][col] == 'e';
    }

    /**
     * @param row Number of row.
     * @param col Number of column.
     * @return True if a tile with given coordinates is a computer tile, false otherwise.
     */
    public boolean isComputerTile(int row, int col) {
        return theBoard[row][col] == 'c';
    }

    /**
     * @param row Number of row.
     * @param col Number of column.
     * @return True if a tile with given coordinates is a human tile, false otherwise.
     */
    public boolean isHumanTile(int row, int col) {
        return theBoard[row][col] == 'h';
    }

    /**
     * @param symbol Symbol to check if it is the winner.
     * @return True if there is n adjacent tiles of type symbol in the same row, column or diagonal,
     * false otherwise.
     */
    public boolean winner(char symbol) {
        // check rows
        for (int row = 0; row < board_size; row++) {
            int sameTile = 0;
            for (int col = 0; col < board_size; col++)
                if (theBoard[row][col] == symbol)
                    sameTile++;

            if (sameTile == board_size)
                return true;
        }

        // check columns
        for (int row = 0; row < board_size; row++) {
            int sameTile = 0;
            for (int col = 0; col < board_size; col++)
                if (theBoard[col][row] == symbol)
                    sameTile++;

            if (sameTile == board_size)
                return true;
        }

        // check top left to bottom right diagonal
        int sameTile = 0;
        for (int row = 0; row < board_size; row++)
            if (theBoard[row][row] == symbol)
                sameTile++;

        if (sameTile == board_size)
            return true;

        // check top left to bottom left diagonal
        sameTile = 0;
        int row = 0;
        for (int col = board_size - 1; col >= 0; col--)
            if (theBoard[row++][col] == symbol)
                sameTile++;

        if (sameTile == board_size)
            return true;

        return false;

    }

    /**
     * Game is draw if one of the following rules is met:
     * <ol><li>empty_positions = 0 (no empty positions are left on the board).</li>
     * <li>empty_positions is equal to the empty positions on the game-board,
     * and none of the empty positions left on the game-board has a tile of
     * the type specified by <b>symbol</b> adjacent to it.</li></ol>
     *
     * @param symbol Symbol specify the player that will perform next move.
     * @param empty_positions Number of positions that muse remain empty.
     * @return True if the game is draw according to the rules, false otherwise.
     */
    public boolean isDraw(char symbol, int empty_positions) {
        // There is no empty positions left on the board
        if (empty_positions == 0)
            return true;

        int count = 0;
        boolean adjacent = false;
        for (int i = 0; i < board_size; i++)
            for (int j = 0; j < board_size; j++)
                // an empty tile
                if (theBoard[i][j] == 'e') {
                    count++;

                    // check if the empty position has a tile of type "symbol" from top
                    if (i - 1 >= 0 && theBoard[i - 1][j] == symbol)
                        adjacent = true;

                    // check if the empty position has a tile of type "symbol" from right
                    else if (j + 1 < board_size && theBoard[i][j + 1] == symbol)
                        adjacent = true;

                    // check if the empty position has a tile of type "symbol" from bottom
                    else if (i + 1 < board_size && theBoard[i + 1][j] == symbol)
                        adjacent = true;

                    // check if the empty position has a tile of type "symbol" from left
                    else if (j - 1 >= 0 && theBoard[i][j - 1] == symbol)
                        adjacent = true;

                    // check if the empty position has a tile of type "symbol" from left diagonal
                    else if (i - 1 >= 0 && j - 1 >= 0 && theBoard[i - 1][j - 1] == symbol)
                        adjacent = true;

                    // check if the empty position has a tile of type "symbol" from right diagonal
                    else if (i + 1 < board_size && j + 1 < board_size && theBoard[i + 1][j + 1] == symbol)
                        adjacent = true;
                }

        if (count == empty_positions)
            return ! adjacent;

        return false;
    }

    public int evaluate(char symbol, int empty_positions) {
        if (winner('c'))
            return 3;
        else if (winner('h'))
            return 0;
        else if (isDraw(symbol, empty_positions))
            return 2;

		return 1;
    }
}