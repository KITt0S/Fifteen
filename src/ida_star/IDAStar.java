package ida_star;

import a_star.AStarNode;
import game_objects.BoardState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IDAStar {

    BoardState boardState;


    public IDAStar(BoardState boardState) {
        this.boardState = boardState;
    }

    public List<IDAStarNode> findPath() {

        IDAStarNode initialNode = new IDAStarNode( boardState );
        double max = initialNode.getH();
        while ( true ) {

            Object result = search( initialNode, 0, max, 0 );
            if ( result instanceof IDAStarNode ) {

                List<IDAStarNode> path = getPath( ( IDAStarNode ) result );
                Collections.reverse( path );
                return path;
            }
            if( result instanceof Double ) {

                if( ( double )result == Double.MAX_VALUE ) {

                    return null;
                }
            }
            max = ( double )result;
        }
    }

    private Object search( IDAStarNode node, int g, double max, int sysoutLvl ) {

        double h = node.getH();
        double f = g + h;
        //System.out.println( f );
        if( f > max) {

            return f;
        }
        if( h == 0 ) {

            return node;
        }
        double min = Double.MAX_VALUE;
        List<IDAStarNode> adjNodes = node.getAdjacentNodes();
        for ( IDAStarNode adjNode :
             adjNodes ) {


            Object result = search( adjNode, g + 1, max, sysoutLvl + 1 );

            if( result instanceof IDAStarNode ) {

                return result;
            }

            if( result instanceof Double ) {

                double newMin = ( double ) result;
                if( newMin < min ) {

                    min = newMin;
                }
            }
        }
        return min;
    }

    private List<IDAStarNode> getPath(IDAStarNode node) {

        List<IDAStarNode> path = new ArrayList<>();
        path.add( node );
        IDAStarNode parentNode = node.getParentNode();
        if ( parentNode != null ) {

            path.addAll( getPath( parentNode ) );
        }
        return path;
    }
}
