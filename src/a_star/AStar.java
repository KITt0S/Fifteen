package a_star;


import game_objects.BoardState;

import java.util.*;

public class AStar {

    BoardState initBorderState;

    //private  PriorityQueue<AStarNode> priorityQueue = new PriorityQueue<>( Comparator.comparingInt( AStarNode::getF ) );
    private List<AStarNode> priorityQueue = new LinkedList<>();
    private List<AStarNode> closedList = new ArrayList<>();

    public AStar( BoardState initBorderState ) {

        this.initBorderState = initBorderState;
    }

    public List<AStarNode> findPath() {

        AStarNode initNode = new AStarNode( initBorderState );
        initNode.setG( 0 );
        //initNode.setH();
        initNode.setF();
        priorityQueue.add( initNode );
        //priorityQueue.sort( Comparator.comparingInt( AStarNode::getF ) );

        while( !priorityQueue.isEmpty() ) {

            priorityQueue.sort( Comparator.comparingInt( AStarNode::getF ) );
            AStarNode x = priorityQueue.get( 0 );
            if( x.getH() == 0 ) {

                List<AStarNode> path = getPath( x );
                Collections.reverse( path );
                return path;
            }
            priorityQueue.remove( 0 );
            closedList.add( x );
            addAdjacentNodes( x );
        }
        return null;
    }

    // получение пути в виде списка узлов
    private List<AStarNode> getPath(AStarNode node) {

        List<AStarNode> path = new ArrayList<>();
        path.add( node );
        AStarNode parentNode = node.getParentNode();
        if ( parentNode != null ) {

            path.addAll( getPath( parentNode ) );
        }
        return path;
    }

    private void printPath( AStarNode node ) {

        List<AStarNode> path = getPath( node );
        Collections.reverse( path );
        System.out.println("====================================================================================");
        for ( AStarNode i :
                path ) {

            System.out.println( i.getF() );
            System.out.print( i.getBoardState() );
            System.out.println();
        }
    }

    private void addAdjacentNodes( AStarNode node ) {

        if( node != null ) {

            List<AStarNode> adjNodes = node.getAdjacentNodes();
            for ( AStarNode i :
                 adjNodes ) {

                int cost = 1;
                checkNode( node, i, cost );
            }
        }
    }

//    private void checkNode( AStarNode parentNode, AStarNode node, int dCost) {
//
//        int g = parentNode.getG() + dCost;
//        if( !closedList.contains( node ) ) {
//
//            boolean isGBetter = false;
//            if( !priorityQueue.contains( node ) ) {
//
//                priorityQueue.add( node );
//                priorityQueue.sort( Comparator.comparingInt( AStarNode::getF ) );
//                isGBetter = true;
//            } else {
//
//                for ( AStarNode i : priorityQueue ) {
//
//                    if ( i.equals( node ) ) {
//
//                        if ( g < i.getG() ) {
//
//                            isGBetter = true;
//                        } else isGBetter = false;
//                        break;
//                    }
//                }
//            }
//
//            if( isGBetter ) {
//
//                for ( AStarNode i : priorityQueue ) {
//
//                    if ( i.equals( node ) ) {
//
//                        //priorityQueue.remove( i );
//                        i.setParentNode( parentNode );
//                        i.setG( g );
//                        //i.setH();
//                        i.setF();
//                        priorityQueue.add( i );
//                        priorityQueue.sort( Comparator.comparingInt( AStarNode::getF ) );
//                        break;
//                    }
//                }
//            }
//        }
//    }

    private void checkNode( AStarNode parentNode, AStarNode node, int dCost) {

        int g = parentNode.getG() + dCost;
        if( closedList.contains( node ) && g >= node.getG() ) {

            return;
        }
        if( !closedList.contains( node ) || g < node.getG() ) {

            node.setParentNode( parentNode );
            node.setG( g );
            node.setF();
            priorityQueue.add( node );
            priorityQueue.sort( Comparator.comparingInt( AStarNode::getF ) );
            if( priorityQueue.contains( node ) ) {

                priorityQueue.add( node );
                priorityQueue.sort( Comparator.comparingInt( AStarNode::getF ));
            }
        }
    }
}
