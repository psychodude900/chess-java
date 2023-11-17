import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Square {
    private final int col;
    private final int row;
    private final String name;
    private Piece piece;
    private JButton button;
    private static List<Square> allSquares = new ArrayList<>();

    public Square(int x, int y){
        this.col = x;
        this.row = y;
        this.name = parseSquareName(col, row);
        allSquares.add(this);
    }

    public static String parseSquareName(int x, int y){
        char letter = (char)('a' + x);
        char number = (char)('1' + y);
        return "" + letter + number;
    }

    public static Square getSquare(String name){
        for(Square square : allSquares){
            if(name.equals(square.getName())){
                return square;
            }
        }
        return null;
    }

    public static List<Square> getAllSquares() {
        return allSquares;
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
    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }
}
