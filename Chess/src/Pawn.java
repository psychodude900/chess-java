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
            if(currSquare.getxPos() == 1){
                for(int squares = 1; squares <= 2; squares++){
                    Square forwardFirst = board.getBoard().get(currSquare.getyPos() + squares).get(currSquare.getxPos());
                    if(forwardFirst.getPiece() == null){
                        possibleSquares.add(forwardFirst);
                    } else{
                        break;
                    }
                }
            } else { //Not first move
                //forward move
                Square forwardNotFirst = board.getBoard().get(currSquare.getyPos() + 1).get(currSquare.getxPos());
                if(forwardNotFirst.getPiece() == null){
                    possibleSquares.add(forwardNotFirst);
                }
                //possible take
                // make sure index in bounds
                int left = currSquare.getxPos() - 1;
                int right = currSquare.getxPos() + 1;
                int forwardWhite = currSquare.getyPos() + 1;
                if(indexInBound(forwardWhite)){
                    if(indexInBound(left)){
                        Square leftTake = board.getBoard().get(forwardWhite).get(left);
                        if(leftTake.getPiece().getColor() != color){
                            possibleSquares.add(leftTake);
                        }
                    }
                    if(indexInBound(right)){
                        Square rightTake = board.getBoard().get(forwardWhite).get(right);
                        if(rightTake.getPiece().getColor() != color){
                            possibleSquares.add(rightTake);
                        }
                    }
                }
            }

        } else {
            if(currSquare.getxPos() == 6){
                for(int squares = 1; squares <= 2; squares++){
                    Square forwardFirst = board.getBoard().get(currSquare.getyPos() - squares).get(currSquare.getxPos());
                    if(forwardFirst.getPiece() == null){
                        possibleSquares.add(forwardFirst);
                    } else{
                        break;
                    }
                }
            } else { //Not first move
                //forward move
                Square forwardNotFirst = board.getBoard().get(currSquare.getyPos() - 1).get(currSquare.getxPos());
                if(forwardNotFirst.getPiece() == null){
                    possibleSquares.add(forwardNotFirst);
                }
                //possible take
                // make sure index in bounds
                int left = currSquare.getxPos() - 1;
                int right = currSquare.getxPos() + 1;
                int forwardBlack = currSquare.getyPos() - 1;
                if(indexInBound(forwardBlack)){
                    if(indexInBound(left)){
                        Square leftTake = board.getBoard().get(forwardBlack).get(left);
                        if(leftTake.getPiece().getColor() != color){
                            possibleSquares.add(leftTake);
                        }
                    }
                    if(indexInBound(right)){
                        Square rightTake = board.getBoard().get(forwardBlack).get(right);
                        if(rightTake.getPiece().getColor() != color){
                            possibleSquares.add(rightTake);
                        }
                    }
                }
            }

        }
        return possibleSquares;
    }

    public boolean move(Square to) {
        this.currSquare.setPiece(null);
        this.currSquare = to;
        to.setPiece(this);
        return true;
    }
}
