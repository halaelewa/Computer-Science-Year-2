public class Layout {
    /**
     * This variable represents the board layout as following:
     * 'h' represents the human's tile.
     * 'c' represents the computer's tile.
     * 'e' represents a green empty space.
     */
    String boardLayout;

    /**
     * Represents the score associated with the board layout.
     */
    int score;

    /**
     * @param boardLayout The board layout.
     * @param score The score associated with the board.\
     */
    public Layout(String boardLayout, int score) {
        this.boardLayout = boardLayout;
        this.score = score;
    }

    /**
     * @return boardLayout.
     */
    public String getBoardLayout() {
        return boardLayout;
    }

    /**
     * @return Score associated with board layout.
     */
    public int getScore() {
        return score;
    }
}