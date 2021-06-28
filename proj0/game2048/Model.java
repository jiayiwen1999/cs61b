package game2048;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author Jiayi
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;
        checkGameOver();
        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        board.startViewingFrom(Side.opposite(side));
        int size = board.size();
        for(int i =0; i<size;i++){
            int[] column_value = get_values(board,i);
            this.score +=points(column_value);
            int [] step_list = amount_to_move(column_value);
            tilt_column(board,i,step_list);
            for(int num : step_list){
                changed = changed || num !=0;
            }
        }

        if (changed) {
            setChanged();
        }
        board.startViewingFrom(Side.NORTH);
        return changed;
    }

    /** a helper function that move up the ith column
     *
     */
    public static void tilt_column(Board board, int i,int[] step_list){
        int size = board.size();
        for(int j =0;j<size;j++){
            if(step_list[j]==0){
                continue;
            }
            board.move(i,j-step_list[j],board.tile(i,j));
        }
    }

    //get the value of the ith column of a board
    private static int[] get_values(Board board, int i){
        int size = board.size();
        int[] column = new int[size];
        for(int j=0;j<size;j++){
            if(board.tile(i,j) !=null){
                column[j] = board.tile(i,j).value();
            }
            else{
                column[j]=0;
            }
        }
        return column;
    }
    private static int points(int [] array){
        int point =0 ;
        int start =0;
        int end =1;
        while(end <array.length){
            if(array[start]==0){
                start++;
                end =start+1;
            }
            else if (array[end] ==0){
                end++;
            }
            else if(array[start]==array[end]){
                point+=2*array[start];
                start = end+1;
                end =start +1;
            }
            else{
                start = end;
                end =start +1;
            }
        }
        return point;
    }
    private static boolean exist_block_to_merge(int index, int[] array){
        for(int j = index+1;j<array.length;j++){
            if (array[j] != 0){
                return array[index]==array[j];
            }
        }
        return false;

    }
    private static int next_not_zero_block (int[] array,int index){
        for (int j = index+1;j<array.length;j++){
            if(array[j]!=0){
                return j;
            }

        }
        return 0;
    }
    private static int[] amount_to_move(int[] column){
       int[] result = new int[column.length];
       int count =0;                        // how many tiles are empty
       for(int i=0;i<column.length;i++){
           if(column[i]==0){
               result[i]=0;
               count ++;
           }
           else if(i != column.length-1 && exist_block_to_merge(i,column)){
               int index = next_not_zero_block(column,i);
               result[i] = count;
               count+= index-i;
               result[index] = count;
               i=index;
           }
           else{
               result[i]= count;
           }
       }
       return result;
    }
    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        int size_of_board = b.size();
        for(int i=0; i<size_of_board;i++){
            for(int j=0;j<size_of_board;j++){
                if (b.tile(i,j) == null){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        int size_of_board = b.size();
        for(int i=0; i<size_of_board;i++){
            for(int j=0;j<size_of_board;j++){
                //we want to check whether the tile is empty or not before calling the value function
                if (b.tile(i,j)!=null &&b.tile(i,j).value() == MAX_PIECE){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        int size_to_check = b.size()-1;
        //check empty tile
        if (emptySpaceExists(b)) return true;
        //check all tiles except for the last row and last column
        for(int i =0;i<size_to_check;i++){
            for(int j =0 ;j<size_to_check;j++){

                int curr = b.tile(i,j).value();
                int right = b.tile(i+1,j).value();
                int down =b.tile(i,j+1).value();
                if(curr ==0||curr == right||curr ==down){
                    return true;
                }
            }
        }
        //last row
        for(int i =0;i<size_to_check;i++){
            int curr = b.tile(i,size_to_check).value();
            int right = b.tile(i+1,size_to_check).value();
            if(curr == right) return true;
        }
        //last column
        for(int i =0;i<size_to_check;i++){
            int curr = b.tile(size_to_check,i).value();
            int down = b.tile(size_to_check,i+1).value();
            if(curr == down) return true;
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
