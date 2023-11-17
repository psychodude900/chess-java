import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private Square enPassantSquare = null;
    public Pawn(int color, Square pos, Board board){
        super(color, 1, pos, board);
        this.letterName = "P";
    }

    public List<Square> possibleSquares() {
        List<Square> possibleSquares = new ArrayList<>();
        if(color == 0){
            //First pawn move
            if(currSquare.getRow() == 1){
                for(int squares = 1; squares <= 2; squares++){
                    if(indexInBound(currSquare.getRow() + squares)){
                        Square forwardFirst = board.getSquareAt(currSquare.getRow() + squares, currSquare.getCol());
                        if(forwardFirst.getPiece() == null){
                            possibleSquares.add(forwardFirst);
                        } else{
                            break;
                        }
                    }
                }
            } else { //Not first move
                //forward move
                if(indexInBound(currSquare.getRow() + 1)){
                    Square forwardNotFirst = board.getSquareAt(currSquare.getRow() + 1, currSquare.getCol());
                    if(forwardNotFirst.getPiece() == null){
                        possibleSquares.add(forwardNotFirst);
                    }
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
                    if(indexInBound(currSquare.getRow() - squares)){
                        Square forwardFirst = board.getSquareAt(currSquare.getRow() - squares, currSquare.getCol());
                        if(forwardFirst.getPiece() == null){
                            possibleSquares.add(forwardFirst);
                        } else{
                            break;
                        }
                    }
                }
            } else { //Not first move
                //forward move
                if(indexInBound(currSquare.getRow() - 1)){
                    Square forwardNotFirst = board.getSquareAt(currSquare.getRow() - 1, currSquare.getCol());
                    if(forwardNotFirst.getPiece() == null){
                        possibleSquares.add(forwardNotFirst);
                    }
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

        Square enPassant = enPassant();
        if(enPassant != null){
            enPassantSquare = enPassant;
            possibleSquares.add(enPassant);
        }

        filterValidMoves(possibleSquares); // remove invalid moves from possibleSquares (discovered checks etc.)

        return possibleSquares;
    }

    public Square enPassant(){
        int left = -1;
        int right = 1;
        int up = 1;
        int down = -1;

        if(indexInBound(currSquare.getCol() + left)){
            Square leftPassant = board.getSquareAt(currSquare.getRow(), currSquare.getCol() + left);
            Piece leftPiece = leftPassant.getPiece();

            if(leftPiece instanceof Pawn && leftPiece.getColor() != color){
                if(leftPiece.getPieceMoves() == 1 && leftPiece == lastPieceMoved){
                    if(color == 0){
                        return board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + left);
                    } else {
                        return board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + left);
                    }
                }
            }
        }

        if(indexInBound(currSquare.getCol() + right)){
            Square leftPassant = board.getSquareAt(currSquare.getRow(), currSquare.getCol() + right);
            Piece leftPiece = leftPassant.getPiece();

            if(leftPiece instanceof Pawn && leftPiece.getColor() != color){
                if(leftPiece.getPieceMoves() == 1 && leftPiece == lastPieceMoved){
                    if(color == 0){
                        return board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + right);
                    } else {
                        return board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + right);
                    }
                }
            }
        }

        return null;

    }

    public boolean move(Square to) {
        List<Square> possibleSquares = possibleSquares();
        System.out.println(currSquare);
        enumerate(possibleSquares);
        if(possibleSquares.contains(to)){
            if(to.getPiece() == null && to != enPassantSquare){
                currSquare.setPiece(null);
                currSquare = to;
                to.setPiece(this);
            } else {
                take(to);
            }
            pieceMoves++;
            totalMoves++;
            lastPieceMoved = this;
            return true;
        }
        return false;

    }

    public void take(Square to){
        if(to == enPassantSquare){
            Square takenPawnSquare = board.getSquareAt(currSquare.getRow(), to.getCol());
            Piece takenPawn = takenPawnSquare.getPiece();

            takenPieces.add(takenPawn);
            playingPieces.remove(takenPawn);

            takenPawn.currSquare.setPiece(null);
            takenPawn.currSquare = null;

        } else {
            Piece takenPiece = to.getPiece();
            takenPieces.add(takenPiece);
            playingPieces.remove(takenPiece);

            //place current piece in new square
        }
        currSquare.setPiece(null);
        currSquare = to;
        to.setPiece(this);
    }

}
