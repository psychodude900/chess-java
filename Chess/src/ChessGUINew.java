import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChessGUINew extends JFrame {
    private final Board chessBoard;
    private JButton selectedButton = null;
    private List<Square> lastPossibleSquares = null;
    private Piece lastClickedPiece = null;
    private Square lastClickedSquare = null;

    private int currentPlayer = 0; // 0 for White, 1 for Black
    JPanel chessPanel;
    private JButton resignButton, drawButton;
    private JLabel turnLabel;

    public ChessGUINew() {
        chessBoard = new Board();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Chess Game");
        setSize(800, 800);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Chessboard
        chessPanel = new JPanel(new GridLayout(8, 8));
        add(chessPanel, BorderLayout.CENTER);

        // Create buttons for each square on the chessboard
        for (int x = chessBoard.getBoard().size() - 1; x >= 0; x--) {
            for (int y = 0; y < chessBoard.getBoard().size(); y++) {
                Square square = chessBoard.getBoard().get(x).get(y);
                JButton button = createSquareButton(square);
                if((x % 2 == y % 2)){
                    button.setBackground(Color.darkGray);
                } else {
                    button.setBackground(Color.lightGray);
                }
                chessPanel.add(button);
            }
        }

        // Add buttons and labels alongside the chessboard
        resignButton = new JButton("Resign");
        drawButton = new JButton("Draw");
        turnLabel = new JLabel("White to play");

        resignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle resign button click
                String resignedPlayer = currentPlayer == 0 ? "White" : "Black";
                JOptionPane.showMessageDialog(ChessGUINew.this,  resignedPlayer + " resigned!");
            }
        });

        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle draw button click
                JOptionPane.showMessageDialog(ChessGUINew.this, "Game is a draw!");
            }
        });

        JPanel controlPanel = new JPanel(new GridLayout(3, 1));
        controlPanel.add(resignButton);
        controlPanel.add(drawButton);
        controlPanel.add(turnLabel);

        add(controlPanel, BorderLayout.EAST);
        updateBoardIcons();
        setVisible(true);
    }

    private JButton createSquareButton(Square square){
        JButton button = new JButton();
        square.setButton(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Piece piece = square.getPiece();

                // Check if the clicked square has a piece and it belongs to the current player
                if (piece != null && piece.getColor() == currentPlayer) {

                    System.out.println("Clicked: " + square.getName());

//                    if (selectedButton != null) {
//                        selectedButton.setBackground(null); // Reset the background color
//                    }

                    if (lastPossibleSquares != null) {
                        for (Square lastPossibleSquare : lastPossibleSquares) {
                            JButton lastButton = lastPossibleSquare.getButton();
                            if (lastButton != null) {
                                boolean dark = lastPossibleSquare.getRow() % 2 == lastPossibleSquare.getCol() % 2;
                                lastButton.setBackground(dark ? Color.darkGray : Color.lightGray);
                            }
                        }
                    }

                    // Highlight possible squares in red
                    lastClickedSquare = square;
                    highlightPossibleSquares(square);

                    selectedButton = button;
                } else {
                    if (lastPossibleSquares != null) {
                        for (Square lastPossibleSquare : lastPossibleSquares) {
                            JButton lastButton = lastPossibleSquare.getButton();
                            if (lastButton != null) {
                                boolean dark = lastPossibleSquare.getRow() % 2 == lastPossibleSquare.getCol() % 2;
                                lastButton.setBackground(dark ? Color.darkGray : Color.lightGray);
                            }
                        }
                        if(lastPossibleSquares.contains(square)){
                            lastClickedPiece.move(square);
                            switchTurn();
                            updateBoardIcons();
                            updateGameStatus();
                        }
                    } else{
                        System.out.println("Invalid move: It's not your turn or no piece on the square.");
                    }
                }
            }
        });

        return button;
    }

    private void highlightPossibleSquares(Square square) {
        Piece piece = square.getPiece();
        if (piece != null) {
            // Get the list of possible squares for the selected piece
            List<Square> possibleSquares = piece.possibleSquares();

            // Highlight possible squares in red
            for (Square possibleSquare : possibleSquares) {
                possibleSquare.getButton().setBackground(Color.blue);
            }

            // Save the last set of possible squares
            lastPossibleSquares = possibleSquares;
            lastClickedPiece = piece;
        }
    }

    private void updateBoardIcons(){
        for(Square square : Square.getAllSquares()){
            boolean dark = square.getRow() % 2 == square.getCol() % 2;
            square.getButton().setBackground(dark ? Color.darkGray : Color.lightGray);
            Piece piece = square.getPiece();
            if (piece != null) {
                // Set the button icon based on the piece type and color
                String iconPath = "assets/images/" + Piece.getFullColorName(piece.getColor()) + "_" + Piece.getFullPieceName(piece.getLetterName()) + ".png";
                square.getButton().setIcon(new ImageIcon(iconPath));
            } else {
                square.getButton().setIcon(null);
            }
        }
    }

    private void updateGameStatus(){
        King currentKing = Piece.getThisKing(currentPlayer);

        if(currentKing.checkmate()){
            currentKing.currSquare.getButton().setBackground(Color.red);
        } else if(currentKing.inCheck()){
            currentKing.currSquare.getButton().setBackground(Color.orange);
        }

    }

    private void switchTurn() {
        currentPlayer = (currentPlayer + 1) % 2; // Switch turn between 0 and 1

        // Remove the existing chessPanel
        remove(chessPanel);

        // Create a new chessPanel for the updated turn
        chessPanel = new JPanel(new GridLayout(8, 8));
        add(chessPanel, BorderLayout.CENTER);

        turnLabel.setText((currentPlayer == 0) ? "White to play" : "Black to play");

        // Create buttons for each square on the chessboard
        if (currentPlayer == 0) {
            for (int x = chessBoard.getBoard().size() - 1; x >= 0; x--) {
                for (int y = 0; y < chessBoard.getBoard().size(); y++) {
                    Square square = chessBoard.getBoard().get(x).get(y);
                    JButton button = square.getButton();
                    chessPanel.add(button);
                }
            }
        } else {
            for (int x = 0; x < chessBoard.getBoard().size(); x++) {
                for (int y = chessBoard.getBoard().size() - 1; y >= 0; y--) {
                    Square square = chessBoard.getBoard().get(x).get(y);
                    JButton button = square.getButton();
                    chessPanel.add(button);
                }
            }
        }

        revalidate(); // Ensure the changes are applied
        repaint();    // Repaint the frame
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChessGUINew();
            }
        });
    }
}
