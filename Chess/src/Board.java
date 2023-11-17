import java.util.ArrayList;
import java.util.List;

public class Board {
    List<List<Square>> board = new ArrayList<>();

    public Board(){
        //initialize empty board
        for(int y = 0; y < 8; y++){
            List<Square> squares = new ArrayList<>();
            for(int x = 0; x < 8; x++){
                squares.add(new Square(x, y));
            }
            board.add(squares);
        }

        populateStartingBoard();
    }

    private void populateStartingBoard() {
        //populate staring board
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                Square current = board.get(y).get(x);
                switch(y){
                    case 0:
                        switch(x){
                            case 0,7:
                                current.setPiece(new Rook(0, current, this));
                                break;
                            case 1,6:
                                current.setPiece(new Knight(0, current, this));
                                break;
                            case 2,5:
                                current.setPiece(new Bishop(0, current, this));
                                break;
                            case 3:
                                current.setPiece(new Queen(0, current, this));
                                break;
                            case 4:
                                current.setPiece(new King(0, current, this));
                                break;
                        }
                        break;
                    case 1:
                        current.setPiece(new Pawn(0, current, this));
                        break;
                    case 6:
                        current.setPiece(new Pawn(1, current, this));
                        break;
                    case 7:
                        switch(x){
                            case 0,7:
                                current.setPiece(new Rook(1, current, this));
                                break;
                            case 1,6:
                                current.setPiece(new Knight(1, current, this));
                                break;
                            case 2,5:
                                current.setPiece(new Bishop(1, current, this));
                                break;
                            case 3:
                                current.setPiece(new Queen(1, current, this));
                                break;
                            case 4:
                                current.setPiece(new King(1, current, this));
                                break;
                        }
                        break;
                }
            }
        }
    }

    public void resetBoard() {
        // Clear all pieces from the board
        for (List<Square> row : board) {
            for (Square square : row) {
                square.setPiece(null);
            }
        }

        // Initialize the board with the starting position
        populateStartingBoard();
    }

    public void displayWhiteBoard(){
        for(int x = board.size() - 1; x >=0; x--){
            for(int y = 0; y < board.size(); y++){
                Square square = board.get(x).get(y);
                Piece piece = square.getPiece();
                System.out.print((piece == null ? square : piece) + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayBlackBoard(){
        for(int x = 0; x < board.size(); x++){
            for(int y = board.size() - 1; y >= 0; y--){
                Square square = board.get(x).get(y);
                Piece piece = square.getPiece();
                System.out.print((piece == null ? square : piece) + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public Square getSquareAt(int row, int col){
        return board.get(row).get(col);
    }

    public List<List<Square>> getBoard() {
        return board;
    }
}
