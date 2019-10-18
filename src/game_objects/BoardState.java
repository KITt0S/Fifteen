package game_objects;

import a_star.AStar;
import a_star.AStarNode;
import ida_star.IDAStar;
import ida_star.IDAStarNode;

import java.util.ArrayList;
import java.util.List;

public class BoardState {

    private int[][] state;
    private int zeroX, zeroY;

    public BoardState(int[][] state) {

        this.state = state;
        determZeroPos();
    }

    public void solve() {

        System.out.println( isSolvable() );
        if( isSolvable() ) {

            List<AStarNode> path = new AStar( this ).findPath();
            for ( AStarNode i :
                    path ) {

                System.out.println( i.getH() );
                System.out.println();
                System.out.print( i.getBoardState() );
                System.out.println();
            }
        }
    }
    public void solve2() {

        System.out.println( isSolvable() );
        if( isSolvable() ) {

            List<IDAStarNode> path = new IDAStar( this ).findPath();
            for ( IDAStarNode i :
                    path ) {

                System.out.println( i.getH() );
                System.out.println();
                System.out.print( i.getBoardState() );
                System.out.println();
            }
        }
    }

    private void determZeroPos() {

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {


                if( state[ i ][ j ] == 0 ) {

                    zeroX = j;
                    zeroY = i;
                }
            }
        }
    }

    public int getPos( int x, int y ) {

        return state[ y ][ x ];
    }

    public List<BoardState> getAdjacentBorderStates() {

        List<BoardState> adjacentBorderStates = new ArrayList<>();
        BoardState state0 = getNewBoardState( zeroX, zeroY, zeroX, zeroY - 1 );
        BoardState state1 = getNewBoardState( zeroX, zeroY, zeroX, zeroY + 1 );
        BoardState state2 = getNewBoardState( zeroX, zeroY, zeroX - 1, zeroY );
        BoardState state3 = getNewBoardState( zeroX, zeroY, zeroX + 1, zeroY );
        if( state0 != null ) {

            adjacentBorderStates.add( state0 );
        }
        if( state1 != null ) {

            adjacentBorderStates.add( state1 );
        }
        if( state2 != null ) {

            adjacentBorderStates.add( state2 );
        }
        if( state3 != null ) {

            adjacentBorderStates.add( state3 );
        }
        return adjacentBorderStates;
    }

    private BoardState getNewBoardState(int xOld, int yOld, int xNew, int yNew ) {

        int[][] newState = new int[ 4 ][ 4 ];
        for (int i = 0; i < 4; i++) {

            newState[ i ] = state[ i ].clone();
        }
//        if( xOld >= 0 && xOld < 4 && yOld >= 0 && yOld < 4 ) {
//
//            if( xNew >= 0 && xNew < 4 && yNew >= 0 && yNew < 4 ) {
//
//                int tmp = newState[ yNew ][ xNew ];
//                newState[ yNew ][ xNew ] = newState[ yOld ][ xOld ];
//                newState[ yOld] [ xOld ] = tmp;
//                return new BoardState( newState );
//            }
//        }
        if( xNew >= 0 && xNew < 4 && yNew >= 0 && yNew < 4 ) {

            int tmp = newState[ yNew ][ xNew ];
            newState[ yNew ][ xNew ] = newState[ yOld ][ xOld ];
            newState[ yOld] [ xOld ] = tmp;
            return new BoardState( newState );
        } else return null;
    }

    public int calculateManhattanDistance() {

        int h = 0;
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                int v = state[ i ][ j ];
                if( v != 0 ) {

                    int realX = ( v - 1 ) % 4;
                    int realY = ( v - 1 ) / 4;
                    h += Math.abs( realX - j ) + Math.abs( realY - i );
                }
            }
        }
        return h;
    }

    int calculateLinearConflict() {

        int h = 0;
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 3; ) {

                for (int k = j; k < 4; k++) {

                    int v1 = state[ i ][ j ];
                    int v2 = state[ i ][ k ];
                    if( v1 != 0 && v2 != 0 && v1 > v2 ) {

                        h += 2;
                    }
                }
                j++;
            }
        }

        for (int j = 0; j < 4; j++) {

            for (int i = 0; i < 3; ) {

                for (int k = i; k < 4; k++) {

                    int v1 = state[ i ][ j ];
                    int v2 = state[ k ][ j ];
                    if( v1 != 0 && v2 != 0 && v1 > v2 ) {

                        h += 2;
                    }
                }
                i++;
            }
        }
        return h;
    }

    public int calculateLastMoveH() {

        int h = 0;
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                if( ( i != 2 && j != 3 ) || ( i != 3 && j != 2 ) || ( i != 3 && j != 3 )  ) {

                    if( state[ i ][ j ] != i * 4 + j ) {

                        return 0;
                    }
                } else {

                    if( !( ( state[ 3 ][ 2 ] == 0 && state[ 3 ][ 3 ] == 15 ) || ( state[ 2 ][ 3 ] == 0 && state[ 3 ][ 3 ] == 12 ) ) ) {

                        return 2;
                    }
                }

            }
        }
        return h;
    }

    public boolean isSolvable() {

        int td = getTotalDisorders();
        int zr = getZeroRow();
        return ( td + zr ) % 2 == 0;
    }

    private int getZeroRow() {

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                if( state[ i ][ j ] == 0 ) {

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

                boardRow[ i * 4 + j ] = state[ i ][ j ];
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

    @Override
    public boolean equals( Object obj ) {

        if( getClass() != obj.getClass() ) {

            return false;
        }
        BoardState other = ( BoardState ) obj;
        return this.state == other.state && this.zeroX == other.zeroX && this.zeroY == other.zeroY;
    }

    @Override
    public String toString() {

        StringBuilder strState = new StringBuilder();
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                strState.append( state[ i ][ j ] ).append( " " );
            }
            strState.append( "\n" );
        }
        return strState.toString();
    }
}
