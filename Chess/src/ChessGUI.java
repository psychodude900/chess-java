import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChessGUI extends JFrame {
    private final Board chessBoard;
    private JButton selectedButton = null;

    JPanel chessPanel;
    private List<Square> lastPossibleSquares = null;
    private Piece lastClickedPiece = null;
    private Square lastClickedSquare = null;

    private int currentPlayer = 0; // 0 for White, 1 for Black

    public ChessGUI() {
        chessBoard = new Board();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chessPanel = new JPanel(new GridLayout(8, 8));
        add(chessPanel, BorderLayout.CENTER);

        // Create buttons for each square on the chessboard
        for (int x = chessBoard.getBoard().size() - 1; x >= 0; x--) {
            for (int y = 0; y < chessBoard.getBoard().size(); y++) {
                Square square = chessBoard.getBoard().get(x).get(y);
                JButton button = createSquareButton(square);
                chessPanel.add(button);
            }
        }

        // Add a label to display the current turn or other information
        JLabel turnLabel = new JLabel("White's Turn", SwingConstants.CENTER);
        add(turnLabel, BorderLayout.SOUTH);

        setSize(600, 600);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private JButton createSquareButton(Square square) {
        JButton button = new JButton();
        square.setButton(button); // Add this line
        updateButtonIcon(button, square);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Piece piece = square.getPiece();

                // Check if the clicked square has a piece and it belongs to the current player
                if (piece != null && piece.getColor() == currentPlayer) {
                    lastClickedSquare = square;
                    System.out.println("Clicked: " + square.getName());

                    if (selectedButton != null) {
                        selectedButton.setBackground(null); // Reset the background color
                    }

                    if (lastPossibleSquares != null) {
                        for (Square lastPossibleSquare : lastPossibleSquares) {
                            JButton lastButton = lastPossibleSquare.getButton();
                            if (lastButton != null) {
                                lastButton.setBackground(null);
                            }
                        }
                    }

                    // Highlight possible squares in red
                    highlightPossibleSquares(square);

                    selectedButton = button;
                } else {
                    if (lastPossibleSquares != null) {
                        for (Square lastPossibleSquare : lastPossibleSquares) {
                            JButton lastButton = lastPossibleSquare.getButton();
                            if (lastButton != null) {
                                lastButton.setBackground(null);
                            }
                        }
                        if(lastPossibleSquares.contains(square)){
                            lastClickedPiece.move(square);
                            switchTurn();

                        }
                    } else{
                        System.out.println("Invalid move: It's not your turn or no piece on the square.");
                    }
                }
            }
        });

        return button;
    }

    private void updateButtonIcon(JButton button, Square square) {
//        if (lastClickedSquare != null && lastClickedSquare != square) {
//            lastClickedSquare.getButton().setIcon(null);
//        }

        Piece piece = square.getPiece();
        if (piece != null) {
            // Set the button icon based on the piece type and color
            String iconPath = "assets/images/" + getFullColorName(piece.getColor()) + "_" + getFullPieceName(piece.getLetterName()) + ".png";
            button.setIcon(new ImageIcon(iconPath));
        } else {
            button.setIcon(null);
        }
    }



    private void highlightPossibleSquares(Square square) {
        Piece piece = square.getPiece();
        if (piece != null) {
            // Get the list of possible squares for the selected piece
            List<Square> possibleSquares = piece.possibleSquares();

            // Highlight possible squares in red
            for (Square possibleSquare : possibleSquares) {
                possibleSquare.getButton().setBackground(Color.RED);
            }

            // Save the last set of possible squares
            lastPossibleSquares = possibleSquares;
            lastClickedPiece = piece;
        }
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer + 1) % 2; // Switch turn between 0 and 1
        JLabel turnLabel = (JLabel) getContentPane().getComponent(1); // Get the turn label
        turnLabel.setText((currentPlayer == 0) ? "White's Turn" : "Black's Turn");

        chessPanel = new JPanel(new GridLayout(8, 8));
        add(chessPanel, BorderLayout.CENTER);

        // Create buttons for each square on the chessboard
        for (int x = chessBoard.getBoard().size() - 1; x >= 0; x--) {
            for (int y = 0; y < chessBoard.getBoard().size(); y++) {
                Square square = chessBoard.getBoard().get(x).get(y);
                JButton button = square.getButton();
                chessPanel.add(button);
            }
        }
    }

    public static String getFullPieceName(String letterName) {
        switch (letterName) {
            case "N" -> {
                return "Knight";
            }
            case "K" -> {
                return "King";
            }
            case "Q" -> {
                return "Queen";
            }
            case "B" -> {
                return "Bishop";
            }
            case "R" -> {
                return "Rook";
            }
            case "P" -> {
                return "Pawn";
            }
        }
        return null;
    }

    public static String getFullColorName(int color) {
        return color == 0 ? "White" : "Black";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGUINew::new);
    }
}
