public abstract class Piece {
    protected int color; //0 == white 1 == black
    protected int value;
    protected String letterName;
    protected Square currSquare;
    protected Board board;

    public Piece(int color, int value, Square pos, Board board){
        this.color = color;
        this.value = value;
        this.board = board;
        this.currSquare = pos;
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

    public abstract boolean move(Square to);

//    public abstract boolean take(Square to);

//    public abstract List<Square> possibleSquares();

    protected static boolean indexInBound(int index){
        return index >= 0 && index <= 7;
    }


}
