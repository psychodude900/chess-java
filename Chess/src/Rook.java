import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{
    public Rook(int color, Square pos, Board board){
        super(color, 5, pos, board);
        this.letterName = "R";
    }

    public boolean move(Square to) {
        this.currSquare = null;
        to.setPiece(this);
        return true;
    }

    public List<Square> possibleSquares(){
        List<Square> possibleSquares = new ArrayList<>();

        //declare horizontal and vertical directions
        int vertical = 1, horizontal = 1;

        //moving up
        while(indexInBound(currSquare.getRow() + vertical)){
            Square to = board.getSquareAt(currSquare.getRow() + vertical, currSquare.getCol());
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
                break;
            }
            vertical++;
        }

        //reset used direction counter
        vertical = 1;

        //moving down
        while(indexInBound(currSquare.getRow() - vertical)){
            Square to = board.getSquareAt(currSquare.getRow() - vertical, currSquare.getCol());
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
                break;
            }
            vertical++;
        }

        //reset used direction counter
        vertical = 1;

        //moving right
        while(indexInBound(currSquare.getCol() + horizontal)){
            Square to = board.getSquareAt(currSquare.getRow(), currSquare.getCol() + horizontal);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
                break;
            }
            horizontal++;
        }

        //reset used direction counter
        horizontal = 1;

        //moving left
        while(indexInBound(currSquare.getCol() - horizontal)){
            Square to = board.getSquareAt(currSquare.getRow(), currSquare.getCol() - horizontal);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
                break;
            }
            horizontal++;
        }

        //reset used direction counter
        horizontal = 1;

        //remove invalid squares
        filterValidMoves(possibleSquares);

        return possibleSquares;
    }
}
