import game_objects.Board;
import game_objects.BoardState;

/**
 * This program allows to determine a solution of the popular game "Fifteen"
 */

public class MainClass {

    static BoardState board = new BoardState( new int[][]{

            { 1, 2, 3, 0 },
            { 5, 6, 7, 8 },
            { 9, 10, 11, 12 },
            { 13, 14, 15, 4 }
    });


    public static void main(String[] args) {

        board.solve2();
    }
}
