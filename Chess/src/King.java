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
}
