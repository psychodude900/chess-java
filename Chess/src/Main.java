public class Main {
    public static void main(String[] args) {
        Board board = new Board();

        System.out.println(board.getSquareAt(3, 1));
        System.out.println(board.getBoard().get(3).get(1).getRow());
        System.out.println(board.getBoard().get(3).get(1).getCol());

        board.displayBoard();
    }
}