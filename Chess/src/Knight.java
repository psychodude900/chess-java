import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    public Knight(int color, Square pos, Board board) {
        super(color, 3, pos, board);
        this.letterName = "N";
    }

    public boolean move(Square to) {
        this.currSquare = null;
        to.setPiece(this);
        return true;
    }

    public List<Square> possibleSquares(){
        List<Square> possibleSquares = new ArrayList<>();

        // declare square moves
        int up, down, left, right;

        //Up 2, right 1 move ('L' shape reflected on x-axis)
        up = 2;
        right = 1;

        if(indexInBound(currSquare.getRow() + up) && indexInBound(currSquare.getCol() + right)){
            Square to = board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + right);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //Up 1, right 2 move ('L' shape rotated 90 anticlockwise)
        up = 1;
        right = 2;

        if(indexInBound(currSquare.getRow() + up) && indexInBound(currSquare.getCol() + right)){
            Square to = board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + right);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //Up 2, left 1 move ('L' shape reflected on x-axis, then y-axis)
        up = 2;
        left = -1;

        if(indexInBound(currSquare.getRow() + up) && indexInBound(currSquare.getCol() + left)){
            Square to = board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + left);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //Up 1, left 2 move ('L' shape reflected on y-axis, then rotated 90 clockwise)
        up = 1;
        left = -2;

        if(indexInBound(currSquare.getRow() + up) && indexInBound(currSquare.getCol() + left)){
            Square to = board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + left);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //down 2, right 1 move ('L' shape)
        down = -2;
        right = 1;

        if(indexInBound(currSquare.getRow() + down) && indexInBound(currSquare.getCol() + right)){
            Square to = board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + right);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //down 1, right 2 move ('L' shape reflected on y-axis, then rotated 90 clockwise)
        down = -1;
        right = 2;

        if(indexInBound(currSquare.getRow() + down) && indexInBound(currSquare.getCol() + right)){
            Square to = board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + right);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //down 2, left 1 move ('L' shape reflected on y-axis)
        down = -2;
        left = -1;

        if(indexInBound(currSquare.getRow() + down) && indexInBound(currSquare.getCol() + left)){
            Square to = board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + left);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //down 1, left 2 move ('L' shape reflected on y-axis, then rotated 90 clockwise)
        down = -1;
        left = -2;

        if(indexInBound(currSquare.getRow() + down) && indexInBound(currSquare.getCol() + left)){
            Square to = board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + left);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //remove invalid moves
        filterValidMoves(possibleSquares);

        return possibleSquares;

    }
}