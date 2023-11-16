import java.util.ArrayList;
import java.util.List;

public class King extends Piece{
        public King(int color, Square pos, Board board) {
            super(color, 0, pos, board);
            this.letterName = "K";
        }

    public boolean move(Square to) {
        this.currSquare = null;
        to.setPiece(this);
        return true;
    }

    public List<Square> possibleSquares(){
        List<Square> possibleSquares = new ArrayList<>();

        //King can only move a displacement of 1 square
        int displacement = 1;

        //moving vertically or horizontally

        //moving up
        if(indexInBound(currSquare.getRow() + displacement)){
            Square to = board.getSquareAt(currSquare.getRow() + displacement, currSquare.getCol());
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //moving down
        if(indexInBound(currSquare.getRow() - displacement)){
            Square to = board.getSquareAt(currSquare.getRow() - displacement, currSquare.getCol());
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //moving right
        if(indexInBound(currSquare.getCol() + displacement)){
            Square to = board.getSquareAt(currSquare.getRow(), currSquare.getCol() + displacement);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //moving left
        if(indexInBound(currSquare.getCol() - displacement)){
            Square to = board.getSquareAt(currSquare.getRow(), currSquare.getCol() - displacement);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //moving top right
        if(indexInBound(currSquare.getRow() + displacement) && indexInBound(currSquare.getCol() + displacement)){
            Square to = board.getSquareAt(currSquare.getRow() + displacement, currSquare.getCol() + displacement);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //moving top left
        if(indexInBound(currSquare.getRow() + displacement) && indexInBound(currSquare.getCol() - displacement)){
            Square to = board.getSquareAt(currSquare.getRow() + displacement, currSquare.getCol() - displacement);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //moving bottom right
        if(indexInBound(currSquare.getRow() - displacement) && indexInBound(currSquare.getCol() + displacement)){
            Square to = board.getSquareAt(currSquare.getRow() - displacement, currSquare.getCol() + displacement);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //moving bottom left
        if(indexInBound(currSquare.getRow() - displacement) && indexInBound(currSquare.getCol() - displacement)){
            Square to = board.getSquareAt(currSquare.getRow() - displacement, currSquare.getCol() - displacement);
            if(to.getPiece() == null){
                possibleSquares.add(to);
            } else {
                if(to.getPiece().getColor() != color){
                    possibleSquares.add(to);
                }
            }
        }

        //filter natural king moves
        filterValidMoves(possibleSquares);

        possibleSquares.addAll(possibleCastle());

        return possibleSquares;

    }

    private List<Square> possibleCastle(){
        List<Square> possibleCastle = new ArrayList<>();

        boolean hasKingMoved = pieceMoves != 0;
        if(!hasKingMoved){
            //check which side rook can castle
            if(currSquare.getRow() == 0){
                //checking short side castle rook
                Piece shortRook = board.getSquareAt(currSquare.getRow(), 7).getPiece(); //short rook at col 7

                if(shortRook != null){
                    if(shortRook.getLetterName().equals("R") && shortRook.pieceMoves == 0){
                        if(canCastle(this, shortRook)){
                            Square castleSquare = board.getSquareAt(currSquare.getRow(), currSquare.getCol() + 2);
                            possibleCastle.add(castleSquare);
                        }
                    }
                }
            } else { //if king hasn't moved and is not on row 0, it must be on row 7
                //checking long side castle rook
                Piece longRook = board.getSquareAt(currSquare.getRow(), 0).getPiece(); //short rook at col 0

                if(longRook != null){
                    if(longRook.getLetterName().equals("R") && longRook.pieceMoves == 0){
                        if(canCastle(this, longRook)){
                            Square castleSquare = board.getSquareAt(currSquare.getRow(), currSquare.getCol() - 2);
                            possibleCastle.add(castleSquare);
                        }
                    }
                }

            }
        }

        return possibleCastle;
    }

    /*
    * This method assumes the king and rook have not moved and are in original position
    *
    * Checks if there are no pieces in between
    *
    * Checks if a check is in the way of castling
    *
    * */
    private boolean canCastle(Piece king, Piece rook){
        List<Square> squaresBetweenCastle = new ArrayList<>();

        // determine if king-rook is short or long
        int row = king.currSquare.getRow();
        int kingCol = king.currSquare.getCol();
        int rookCol = rook.currSquare.getCol();

        if(kingCol < rookCol){ //short castle, rook col 7, king col 4
            for(int col = kingCol + 1; col < rookCol; col++){
                Square squareBetweenCastle = board.getSquareAt(row, col);
                if(squareBetweenCastle.getPiece() != null){
                    return false;
                } else {
                    squaresBetweenCastle.add(squareBetweenCastle);
                }
            }

            //compare size of possible castling squares before and after filtering invalid moves
            int initialSize = squaresBetweenCastle.size();

            //remove invalid squared between castle
            filterValidMoves(squaresBetweenCastle);

            int finalSize = squaresBetweenCastle.size();

            return initialSize == finalSize; //if they're equal, no invalid moves, can Castle

        } else { //long castle, rook col 0, king col 4
            for(int col = kingCol - 1; col > rookCol; col--){
                Square squareBetweenCastle = board.getSquareAt(row, col);
                if(squareBetweenCastle.getPiece() != null){
                    return false;
                } else {
                    squaresBetweenCastle.add(squareBetweenCastle);
                }
            }

            //compare size of possible castling squares before and after filtering invalid moves
            int initialSize = squaresBetweenCastle.size();

            //remove invalid squared between castle
            filterValidMoves(squaresBetweenCastle);

            int finalSize = squaresBetweenCastle.size();

            return initialSize == finalSize; //if they're equal, no invalid moves, can Castle
        }
    }
}