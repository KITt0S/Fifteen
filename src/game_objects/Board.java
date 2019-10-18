package game_objects;

import a_star.AStar;
import a_star.AStarNode;

import java.util.List;

public class Board {

    private int[][] boardMatrix = new int[ 4 ][ 4 ];

    public Board(int[][] boardMatrix) {
        this.boardMatrix = boardMatrix;
    }

    public void solve() {

        System.out.println( isSolvable() );
        if( isSolvable() ) {

            List<AStarNode> path = new AStar( new BoardState( boardMatrix ) ).findPath();
            for ( AStarNode i :
                    path ) {

                System.out.println( i.getH() );
                System.out.println();
                System.out.print( i.getBoardState() );
                System.out.println();
            }
        }
    }

    public boolean isSolvable() {

        int td = getTotalDisorders();
        int zr = getZeroRow();
        return ( td + zr ) % 2 == 0;
    }

    private int getZeroRow() {

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                if( boardMatrix[ i ][ j ] == 0 ) {

                    return i + 1;
                }
            }
        }
        return 0;
    }

    private int getTotalDisorders() {

        int d = 0;
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {


                d += getDisorderForOnePos( j, i );
            }
        }
        return d;
    }

    private int getDisorderForOnePos( int x, int y ) {

        int d = 0;
        int[] boardRow = new int[ 16 ];
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                boardRow[ i * 4 + j ] = boardMatrix[ i ][ j ];
            }
        }
        int pos = y * 4 + x;
        for (int i = pos; i < 16; i++) {

            int v = boardRow[ pos ];
            int v2 = boardRow[ i ];
            if( v != 0 && v2 != 0 && v > v2 ) {

                d++;
            }
        }
        return d;
    }
}
