import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    protected int color; //0 == white 1 == black
    protected int value;
    protected String letterName;
    protected Square currSquare;
    protected Board board;
    protected int pieceMoves = 0;
    protected static int totalMoves = 0;
    protected List<Square> possibleSquares;
    protected static List<Piece> playingPieces = new ArrayList<>();
    protected static List<Piece> takenPieces = new ArrayList<>();

    public Piece(int color, int value, Square pos, Board board){
        this.color = color;
        this.value = value;
        this.board = board;
        this.currSquare = pos;
        possibleSquares = new ArrayList<>();
        addPlayingPiece(this);
    }

    public static void addPlayingPiece(Piece piece){
        if(piece != null){
            if(!playingPieces.contains(piece)){
                playingPieces.add(piece);
            }
        }
    }

    public static Piece getPlayingPiece(String letterName, int color, String startingSquareName){
        for(Piece piece : playingPieces){
            if(letterName.equals(piece.getLetterName()) &&
                    color == piece.getColor() &&
                    piece.currSquare == Square.getSquare(startingSquareName)){
                return piece;
            }
        }
        return null;
    }

    public int getColor(){
        return color;
    }

    public int getValue(){
        return value;
    }

    public String getLetterName(){
        return letterName;
    }

    public abstract List<Square> possibleSquares();

    public boolean move(Square to) {
        possibleSquares = possibleSquares();
        if(possibleSquares.contains(to)){
            if(to.getPiece() == null){
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
        return false;

    }

    public void take(Square to){
        //remove taken piece from playing board
        Piece takenPiece = to.getPiece();
        takenPieces.add(takenPiece);
        playingPieces.remove(takenPiece);

        //place current piece in new square
        currSquare.setPiece(null);
        currSquare = to;
        to.setPiece(this);
    }

    protected boolean isKingInCheck(){
        for(Piece piece : playingPieces){
            if(piece.getColor() != color){
                for(Square possibleSquare : piece.possibleSquares()){
                    if(possibleSquare.getPiece().getLetterName().equals("K")){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected void filterValidMoves(List<Square> possibleSquares){
        List<Square> inValidSquares = new ArrayList<>();
        for(Square possibleSquare : possibleSquares){
            Square originalSquare = currSquare;
            Piece originalPieceOnPossibleSquare = possibleSquare.getPiece();

            currSquare.setPiece(null);
            currSquare = possibleSquare;
            possibleSquare.setPiece(this);

            if(isKingInCheck()){ // Check if after trial move, king is in check
                inValidSquares.add(possibleSquare);
            }

            currSquare = originalSquare;
            possibleSquare.setPiece(originalPieceOnPossibleSquare);
            currSquare.setPiece(this);
        }
        for(Square inValid : inValidSquares){
            possibleSquares.remove(inValid);
        }
    }

    protected static boolean indexInBound(int index){
        return index >= 0 && index <= 7;
    }

    public String toString(){
        return color + letterName;
    }

}
