package ida_star;

import a_star.AStarNode;
import game_objects.BoardState;

import java.util.ArrayList;
import java.util.List;

public class IDAStarNode implements Comparable {

    private IDAStarNode parentNode;
    private BoardState boardState;
    private int g, h, f;

    public IDAStarNode( BoardState boardState ) {

        this.boardState = boardState;
        h = calculateHeuristics();
    }

    public IDAStarNode(BoardState boardState, IDAStarNode parentNode ) {

        this( boardState );
        this.parentNode = parentNode;
    }

    private int calculateHeuristics() {

        return boardState.calculateManhattanDistance() + boardState.calculateLastMoveH();
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

    public void setH() {

        h = calculateHeuristics();
    }

    public void setF() {

        f = g + h;
    }

    public BoardState getBoardState() {
        return boardState;
    }

    public IDAStarNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(IDAStarNode parentNode) {
        this.parentNode = parentNode;
    }

    List<IDAStarNode> getAdjacentNodes() {

        List<IDAStarNode> adjNodes = new ArrayList<>();
        List<BoardState> adjBoardStates = boardState.getAdjacentBorderStates();
        for ( BoardState i :
                adjBoardStates ) {

            IDAStarNode adjNode = new IDAStarNode( i, this );
            adjNode.calculateHeuristics();
            adjNodes.add( adjNode );
        }
        return adjNodes;
    }

    @Override
    public int compareTo(Object o) {

        IDAStarNode node1 = ( IDAStarNode ) o;
        return Integer.compare( this.f, node1.f );
    }

    @Override
    public boolean equals( Object obj ) {

        if( getClass() != obj.getClass() ) {

            return false;
        }
        IDAStarNode other = ( IDAStarNode ) obj;
        return this.boardState.equals( other.boardState )  ;
    }
}
