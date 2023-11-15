public class Queen extends Piece{
        public Queen(int color, Square pos, Board board) {
            super(color, 9, pos, board);
            this.letterName = "Q";
        }

    public boolean move(Square to) {
        this.currSquare = null;
        to.setPiece(this);
        return true;
    }
}