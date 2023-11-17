import java.util.ArrayList;
import java.util.List;

public class King extends Piece{
    public King(int color, Square pos, Board board) {
        super(color, 0, pos, board);
        this.letterName = "K";
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

        Square shortCastleSquare = shortCastle();
        Square longCastleSquare = longCastle();

        if(shortCastleSquare != null){
            possibleSquares.add(shortCastleSquare);
        }
        if(longCastleSquare != null){
            possibleSquares.add(longCastleSquare);
        }

        return possibleSquares;

    }

    private Square shortCastle(){
        boolean hasKingMoved = pieceMoves != 0;
        if(!hasKingMoved){
            //checking short side castle rook
            Piece shortRook = board.getSquareAt(currSquare.getRow(), 7).getPiece(); //short rook at col 7

            if(shortRook != null){
                if(shortRook.getLetterName().equals("R") && shortRook.pieceMoves == 0){
                    if(canCastle(this, shortRook)){
                        return board.getSquareAt(currSquare.getRow(), currSquare.getCol() + 2);
                    }
                }
            }
        }

        return null;
    }

    private Square longCastle(){
        boolean hasKingMoved = pieceMoves != 0;

        if(!hasKingMoved) {
            //checking long side castle rook
            Piece longRook = board.getSquareAt(currSquare.getRow(), 0).getPiece(); //long rook at col 0

            if (longRook != null) {
                if (longRook.getLetterName().equals("R") && longRook.pieceMoves == 0) {
                    if (canCastle(this, longRook)) {
                        return board.getSquareAt(currSquare.getRow(), currSquare.getCol() - 2);
                    }
                }
            }
        }
        return null;
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

    //accounts for castling
    @Override
    public boolean move(Square to){
        if(to != null) {
            if (to == longCastle()) {
                //change king square
                currSquare.setPiece(null);
                currSquare = to;
                to.setPiece(this);

                //change long rook position
                Piece rook = board.getSquareAt(currSquare.getRow(), 0).getPiece();
                Square newRookSquare = board.getSquareAt(currSquare.getRow(), currSquare.getCol() + 1);
                rook.currSquare.setPiece(null);
                rook.currSquare = newRookSquare;
                newRookSquare.setPiece(rook);

                pieceMoves++;
                totalMoves++;
                return true;

            } else if (to == shortCastle()) {
                //change king square
                currSquare.setPiece(null);
                currSquare = to;
                to.setPiece(this);

                //change long rook position
                Piece rook = board.getSquareAt(currSquare.getRow(), 7).getPiece();
                Square newRookSquare = board.getSquareAt(currSquare.getRow(), currSquare.getCol() -1);
                rook.currSquare.setPiece(null);
                rook.currSquare = newRookSquare;
                newRookSquare.setPiece(rook);

                pieceMoves++;
                totalMoves++;
                return true;
            }
            if (possibleSquares().contains(to)) {
                if (to.getPiece() == null) {
                    currSquare.setPiece(null);
                    currSquare = to;
                    to.setPiece(this);
                    pieceMoves++;
                    totalMoves++;
                } else {
                    take(to);
                }
                return true;
            }
        }
        return false;
    }

    protected boolean inCheck(){
        //get king of this color
        Piece thisKing = getThisKing(color);

        //check if king is in check by any opposing piece in direct vision

        //check if pawn checks king

        //black pawn checking white king
        if(color == 0){
            int up = 1, left = -1, right = 1;
            //check left take
            if(indexInBound(currSquare.getRow() + up) && indexInBound(currSquare.getCol() + left)){
                Square leftPawnSquare = board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + left);
                Piece leftPawnPiece = leftPawnSquare.getPiece();
                if(leftPawnPiece instanceof Pawn && leftPawnPiece.getColor() != color){
                    return true;
                }
            }
            //check right take
            if(indexInBound(currSquare.getRow() + up) && indexInBound(currSquare.getCol() + right)){
                Square rightPawnSquare = board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + right);
                Piece rightPawnPiece = rightPawnSquare.getPiece();
                if(rightPawnPiece instanceof Pawn && rightPawnPiece.getColor() != color){
                    return true;
                }
            }
        } else { // white pawn checks
            int down = -1, left = -1, right = 1;
            //check left take
            if(indexInBound(currSquare.getRow() + down) && indexInBound(currSquare.getCol() + left)){
                Square leftPawnSquare = board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + left);
                Piece leftPawnPiece = leftPawnSquare.getPiece();
                if(leftPawnPiece instanceof Pawn && leftPawnPiece.getColor() != color){
                    return true;
                }
            }
            //check right take
            if(indexInBound(currSquare.getRow() + down) && indexInBound(currSquare.getCol() + right)){
                Square rightPawnSquare = board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + right);
                Piece rightPawnPiece = rightPawnSquare.getPiece();
                if(rightPawnPiece instanceof Pawn && rightPawnPiece.getColor() != color){
                    return true;
                }
            }
        }

        //check if bishop checks king
        int up = 1, down = -1, left = -1, right = 1;
        // Check top left diagonal
        while(indexInBound(currSquare.getRow() + up) && indexInBound(currSquare.getCol() + left)){
            Square leftDiagBishopSquare = board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + left);
            Piece leftDiagBishopPiece = leftDiagBishopSquare.getPiece();
            if(leftDiagBishopPiece != null){
                if(leftDiagBishopPiece instanceof Bishop || leftDiagBishopPiece instanceof Queen){
                    if(leftDiagBishopPiece.getColor() != color){
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            up++;
            left--;
        }

        // reset counter diagonals
        up = 1; left = -1;

        // Check top right diagonal
        while(indexInBound(currSquare.getRow() + up) && indexInBound(currSquare.getCol() + right)){
            Square rightDiagBishopSquare = board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + right);
            Piece rightDiagBishopPiece = rightDiagBishopSquare.getPiece();
            if(rightDiagBishopPiece != null){
                if(rightDiagBishopPiece instanceof Bishop || rightDiagBishopPiece instanceof Queen){
                    if(rightDiagBishopPiece.getColor() != color){
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            up++;
            right++;
        }

        //reset diagonal counters
        up = 1; right = 1;

        // Check bottom right diagonal
        while(indexInBound(currSquare.getRow() + down) && indexInBound(currSquare.getCol() + right)){
            Square rightDiagBishopSquare = board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + right);
            Piece rightDiagBishopPiece = rightDiagBishopSquare.getPiece();
            if(rightDiagBishopPiece != null){
                if(rightDiagBishopPiece instanceof Bishop || rightDiagBishopPiece instanceof Queen){
                    if(rightDiagBishopPiece.getColor() != color){
                        return true;
                    } else {
                        break;
                    }
                } else{
                    break;
                }
            }
            down--;
            right++;
        }

        //reset used diagonal counter
        down = -1; right = 1;

        // Check bottom left diagonal
        while(indexInBound(currSquare.getRow() + down) && indexInBound(currSquare.getCol() + left)){
            Square leftDiagBishopSquare = board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + left);
            Piece leftDiagBishopPiece = leftDiagBishopSquare.getPiece();
            if(leftDiagBishopPiece != null){
                if(leftDiagBishopPiece instanceof Bishop || leftDiagBishopPiece instanceof Queen){
                    if(leftDiagBishopPiece.getColor() != color){
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            down--;
            left--;
        }

        //reset used diagonal counter
        down = -1; left = -1;


        //check rook and queen checks

        //declare horizontal and vertical directions
        int vertical = 1, horizontal = 1;

        //moving up
        while(indexInBound(currSquare.getRow() + vertical)){
            Square upRookSquare = board.getSquareAt(currSquare.getRow() + vertical, currSquare.getCol());
            Piece upRookPiece = upRookSquare.getPiece();
            if(upRookPiece != null){
                if(upRookPiece instanceof Rook || upRookPiece instanceof Queen){
                    if(upRookPiece.getColor() != color){
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            vertical++;
        }

        //reset used direction counter
        vertical = 1;

        //moving down
        while(indexInBound(currSquare.getRow() - vertical)){
            Square downRookSquare = board.getSquareAt(currSquare.getRow() - vertical, currSquare.getCol());
            Piece downRookPiece = downRookSquare.getPiece();
            if(downRookPiece != null){
                if(downRookPiece instanceof Rook || downRookPiece instanceof Queen){
                    if(downRookPiece.getColor() != color){
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            vertical++;
        }

        //reset direction counter
        vertical = 1;

        //moving right
        while(indexInBound(currSquare.getCol() + horizontal)){
            Square rightRookSquare = board.getSquareAt(currSquare.getRow(), currSquare.getCol() + horizontal);
            Piece rightRookPiece = rightRookSquare.getPiece();
            if(rightRookPiece != null){
                if(rightRookPiece instanceof Rook || rightRookPiece instanceof Queen){
                    if(rightRookPiece.getColor() != color){
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            horizontal++;
        }

        //reset used direction counter
        horizontal = 1;

        //moving left
        while(indexInBound(currSquare.getCol() - horizontal)){
            Square rightRookSquare = board.getSquareAt(currSquare.getRow(), currSquare.getCol() - horizontal);
            Piece rightRookPiece = rightRookSquare.getPiece();
            if(rightRookPiece != null){
                if(rightRookPiece instanceof Rook || rightRookPiece instanceof Queen){
                    if(rightRookPiece.getColor() != color){
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            horizontal++;
        }
        //reset used direction counter
        horizontal = 1;

        // check if knight checks
        //Up 2, right 1 move ('L' shape reflected on x-axis)
        up = 2;
        right = 1;

        if(indexInBound(currSquare.getRow() + up) && indexInBound(currSquare.getCol() + right)){
            Square knightSquare = board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + right);
            Piece knightPiece = knightSquare.getPiece();
            if(knightSquare.getPiece() == null){
                if(knightPiece instanceof Knight){
                    if(knightPiece.getColor() != color){
                        return true;
                    }
                }
            }
        }

        //Up 1, right 2 move ('L' shape rotated 90 anticlockwise)
        up = 1;
        right = 2;

        if(indexInBound(currSquare.getRow() + up) && indexInBound(currSquare.getCol() + right)){
            Square knightSquare = board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + right);
            Piece knightPiece = knightSquare.getPiece();
            if(knightSquare.getPiece() == null){
                if(knightPiece instanceof Knight){
                    if(knightPiece.getColor() != color){
                        return true;
                    }
                }
            }
        }

        //Up 1, right 2 move ('L' shape rotated 90 anticlockwise)
        up = 2;
        left = -1;

        if(indexInBound(currSquare.getRow() + up) && indexInBound(currSquare.getCol() + left)){
            Square knightSquare = board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + left);
            Piece knightPiece = knightSquare.getPiece();
            if(knightSquare.getPiece() == null){
                if(knightPiece instanceof Knight){
                    if(knightPiece.getColor() != color){
                        return true;
                    }
                }
            }
        }

        //Up 1, right 2 move ('L' shape rotated 90 anticlockwise)
        up = 1;
        left = -2;

        if(indexInBound(currSquare.getRow() + up) && indexInBound(currSquare.getCol() + left)){
            Square knightSquare = board.getSquareAt(currSquare.getRow() + up, currSquare.getCol() + left);
            Piece knightPiece = knightSquare.getPiece();
            if(knightSquare.getPiece() == null){
                if(knightPiece instanceof Knight){
                    if(knightPiece.getColor() != color){
                        return true;
                    }
                }
            }
        }

        //Up 1, right 2 move ('L' shape rotated 90 anticlockwise)
        down = -2;
        left = -1;

        if(indexInBound(currSquare.getRow() + down) && indexInBound(currSquare.getCol() + left)){
            Square knightSquare = board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + left);
            Piece knightPiece = knightSquare.getPiece();
            if(knightSquare.getPiece() == null){
                if(knightPiece instanceof Knight){
                    if(knightPiece.getColor() != color){
                        return true;
                    }
                }
            }
        }

        //Up 1, right 2 move ('L' shape rotated 90 anticlockwise)
        down = -1;
        left = -2;

        if(indexInBound(currSquare.getRow() + down) && indexInBound(currSquare.getCol() + left)){
            Square knightSquare = board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + left);
            Piece knightPiece = knightSquare.getPiece();
            if(knightSquare.getPiece() == null){
                if(knightPiece instanceof Knight){
                    if(knightPiece.getColor() != color){
                        return true;
                    }
                }
            }
        }

        //Up 1, right 2 move ('L' shape rotated 90 anticlockwise)
        down = -2;
        right = 1;

        if(indexInBound(currSquare.getRow() + down) && indexInBound(currSquare.getCol() + right)){
            Square knightSquare = board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + right);
            Piece knightPiece = knightSquare.getPiece();
            if(knightSquare.getPiece() == null){
                if(knightPiece instanceof Knight){
                    if(knightPiece.getColor() != color){
                        return true;
                    }
                }
            }
        }

        //Up 1, right 2 move ('L' shape rotated 90 anticlockwise)
        down = -1;
        right = 2;

        if(indexInBound(currSquare.getRow() + down) && indexInBound(currSquare.getCol() + right)){
            Square knightSquare = board.getSquareAt(currSquare.getRow() + down, currSquare.getCol() + right);
            Piece knightPiece = knightSquare.getPiece();
            if(knightSquare.getPiece() == null){
                if(knightPiece instanceof Knight){
                    if(knightPiece.getColor() != color){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean checkmate(){
        List<Square> possibleMoves = new ArrayList<>();
        for(Piece piece : playingPieces){
            if(piece.getColor() == color){
                possibleMoves.addAll(piece.possibleSquares());
            }
        }
        return possibleMoves.size() == 0;
    }

}