import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(int color, Square pos, Board board){
        super(color, 1, pos, board);
        this.letterName = "";
    }

    public List<Square> possibleSquares() {
        List<Square> possibleSquares = new ArrayList<>();
        if(color == 0){
            //First pawn move
            if(currSquare.getRow() == 1){
                for(int squares = 1; squares <= 2; squares++){
                    Square forwardFirst = board.getSquareAt(currSquare.getRow() + squares, currSquare.getCol());
                    if(forwardFirst.getPiece() == null){
                        possibleSquares.add(forwardFirst);
                    } else{
                        break;
                    }
                }
            } else { //Not first move
                //forward move
                Square forwardNotFirst = board.getSquareAt(currSquare.getRow() + 1, currSquare.getCol());
                if(forwardNotFirst.getPiece() == null){
                    possibleSquares.add(forwardNotFirst);
                }
            }
            //possible take
            // make sure index in bounds and there's an opposing piece to take
            int left = currSquare.getCol() - 1;
            int right = currSquare.getCol() + 1;
            int forwardWhite = currSquare.getRow() + 1;
            if(indexInBound(forwardWhite)){
                if(indexInBound(left)){
                    Square leftTake = board.getSquareAt(forwardWhite, left);
                    if(leftTake.getPiece() != null){
                        if(leftTake.getPiece().getColor() != color){
                            possibleSquares.add(leftTake);
                        }
                    }
                }
                if(indexInBound(right)){
                    Square rightTake = board.getSquareAt(forwardWhite, right);
                    if(rightTake.getPiece() != null){
                        if(rightTake.getPiece().getColor() != color){
                            possibleSquares.add(rightTake);
                        }
                    }
                }
            }

        } else { // Black pawn
            if(currSquare.getRow() == 6){ // First move
                for(int squares = 1; squares <= 2; squares++){
                    Square forwardFirst = board.getSquareAt(currSquare.getRow() - squares, currSquare.getCol());
                    if(forwardFirst.getPiece() == null){
                        possibleSquares.add(forwardFirst);
                    } else{
                        break;
                    }
                }
            } else { //Not first move
                //forward move
                Square forwardNotFirst = board.getSquareAt(currSquare.getRow() - 1, currSquare.getCol());
                if(forwardNotFirst.getPiece() == null){
                    possibleSquares.add(forwardNotFirst);
                }
            }

            //possible take
            // make sure index in bounds
            int left = currSquare.getCol() - 1;
            int right = currSquare.getCol() + 1;
            int forwardBlack = currSquare.getRow() - 1;
            if(indexInBound(forwardBlack)){
                if(indexInBound(left)){
                    Square leftTake = board.getSquareAt(forwardBlack, left);
                    if(leftTake.getPiece() != null){
                        if(leftTake.getPiece().getColor() != color){
                            possibleSquares.add(leftTake);
                        }
                    }
                }
                if(indexInBound(right)){
                    Square rightTake = board.getSquareAt(forwardBlack, right);
                    if(rightTake.getPiece() != null){
                        if(rightTake.getPiece().getColor() != color){
                            possibleSquares.add(rightTake);
                        }
                    }
                }
            }

        }
        filterValidMoves(possibleSquares); // remove invalid moves from possibleSquares (discovered checks etc.)

        return possibleSquares;
    }
}
