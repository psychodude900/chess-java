public class Square {
    private final int col;
    private final int row;
    private final String name;
    private Piece piece;

    public Square(int x, int y){
        this.col = x;
        this.row = y;
        this.name = parseSquareName(col, row);
    }

    public static String parseSquareName(int x, int y){
        char letter = (char)('a' + x);
        char number = (char)('1' + y);
        return "" + letter + number;
    }

    public int getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }
    public String getName() {
        return name;
    }

    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }


    public String toString(){
        return name;
    }
}
