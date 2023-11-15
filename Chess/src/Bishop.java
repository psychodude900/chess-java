public class Bishop extends Piece{
    public Bishop(int color, Square pos, Board board){
        super(color, 3, pos, board);
        this.letterName = "B";
    }

    public boolean move(Square to) {
        this.currSquare = null;
        to.setPiece(this);
        return true;
    }
}
