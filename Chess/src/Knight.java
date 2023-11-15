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
}