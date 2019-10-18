package a_star;

import game_objects.BoardState;

import java.util.ArrayList;
import java.util.List;

public class AStarNode implements Comparable {

    private AStarNode parentNode;
    private BoardState boardState;
    private int g, h, f;


    public AStarNode( BoardState boardState ) {

        this.boardState = boardState;
        h = calculateHeuristics();
    }

    public AStarNode(BoardState boardState, AStarNode parentNode ) {

        this( boardState );
        this.parentNode = parentNode;
    }


    int calculateHeuristics() {

        int h1 = boardState.calculateManhattanDistance();
        //int h2 = boardState.calculateLinearConflict();
        int h3 = boardState.calculateLastMoveH();
        return h1;
    }

    List<AStarNode> getAdjacentNodes() {

        List<AStarNode> adjNodes = new ArrayList<>();
        List<BoardState> adjBoardStates = boardState.getAdjacentBorderStates();
        for ( BoardState i :
             adjBoardStates ) {

            AStarNode adjNode = new AStarNode( i, this );
            //adjNode.calculateHeuristics();
            adjNodes.add( adjNode );
        }
        return adjNodes;
    }


    private void updateFinalCost() {

        f = g + h;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public int getF() {
        return f;
    }

    public void setG(int g) {
        this.g = g;
    }

//    public void setH() {
//
//        h = calculateHeuristics();
//    }

    public void setF() {

        f = g + h;
    }

    public BoardState getBoardState() {
        return boardState;
    }

    public AStarNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(AStarNode parentNode) {
        this.parentNode = parentNode;
    }

    @Override
    public int compareTo(Object o) {

        AStarNode node1 = ( AStarNode ) o;
        return Integer.compare( this.f, node1.f );
    }

    @Override
    public boolean equals( Object obj ) {

        if( getClass() != obj.getClass() ) {

            return false;
        }
        AStarNode other = ( AStarNode ) obj;
        return this.boardState.equals( other.boardState )  ;
    }
}
