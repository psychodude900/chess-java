import java.util.*;
public class Main {
    public static void movePiece(String letterName, int color, String startSquareName, String toSquareName){
        Piece piece = Piece.getPlayingPiece(letterName, color, startSquareName);
        if(piece != null){
            Square to = Square.getSquare(toSquareName);
            if(to != null){
                piece.move(to);
            } else {
                System.out.println("Invalid destination square");
            }
        } else {
            System.out.println("Invalid piece");
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Board board = new Board();

        int turn = 0; //white starts
        board.displayWhiteBoard();

        System.out.print("Enter move in format (letterName startingSquareNumber toSquareNumber): ");
        movePiece(in.next(), turn, in.next(), in.next());
        turn++;

        while(in.hasNextLine()){
            if (turn == 0) {
                board.displayWhiteBoard();
            } else {
                board.displayBlackBoard();
            }
            System.out.print("Enter move in format (letterName startingSquareNumber toSquareNumber): ");

            String letterName = in.next();
            if(letterName.equals("resign")){
                break;
            }
            String start = in.next();
            String to = in.next();
            movePiece(letterName, turn, start, to);
            if(turn == 0){
                turn++;
            } else {
                turn--;
            }
        }

    }
}