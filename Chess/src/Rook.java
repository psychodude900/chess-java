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
}
