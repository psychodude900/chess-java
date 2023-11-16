import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece{
        public Queen(int color, Square pos, Board board) {
            super(color, 9, pos, board);
            this.letterName = "Q";
        }

    public boolean move(Square to) {
        this.currSquare = null;
        to.setPiece(this);
        return true;
    }

    public List<Square> possibleSquares(){
        List<Square> possibleSquares = new ArrayList<>();

        //Queen is a combination of a rook and bishop


        //Bishop moves
        // Diagonal counters
        int left = -1;
        int right = 1;
        int up = 1;
        int down = -1;

        // Check top left diagonal
        while(indexInBound(currSquare.getRow() + up) && indexInBound(currSquare.getCol() + left)){
            Square to = board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + left);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
                break;
            }
            up++;
            left--;
        }

        //reset used diagonal counters
        left = -1;
        up = 1;

        //Check top right diagonals
        while(indexInBound(currSquare.getRow() + up) && indexInBound(currSquare.getCol() + right)){
            Square to = board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + right);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
                break;
            }
            up++;
            right++;
        }

        //reset used diagonal counters
        up = 1;
        right = 1;

        //Check bottom left diagonal
        while(indexInBound(currSquare.getRow() + down) && indexInBound(currSquare.getCol() + left)){
            Square to = board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + left);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
                break;
            }
            down--;
            left--;
        }

        //reset used diagonal counters
        down = -1;
        left = -1;

        //Check bottom right diagonal
        while(indexInBound(currSquare.getRow() + down) && indexInBound(currSquare.getCol() + right)){
            Square to = board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + right);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
                break;
            }
            down--;
            right++;
        }

        //reset used diagonal counters
        down = -1;
        right = 1;


        //Rook moves
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

        //remove invalid moves
        filterValidMoves(possibleSquares);

        return possibleSquares;
    }
}