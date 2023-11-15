public class Square {
    private final int xPos;
    private final int yPos;
    private final String name;
    private Piece piece;

    public Square(int x, int y){
        this.xPos = x;
        this.yPos = y;
        this.name = parseSquareName(xPos, yPos);
    }

    public static String parseSquareName(int x, int y){
        char letter = (char)('a' + x);
        char number = (char)('1' + y);
        return "" + letter + number;
    }

    public int getxPos() {
        return xPos;
    }
    public int getyPos() {
        return yPos;
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



}
