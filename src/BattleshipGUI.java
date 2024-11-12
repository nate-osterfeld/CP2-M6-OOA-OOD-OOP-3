import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BattleshipGUI extends JFrame {
    private static final int BOARD_SIZE = 10;
    private JButton[][] boardButtons;
    private JLabel missCounterLabel;
    private JLabel strikeCounterLabel;
    private JLabel totalMissLabel;
    private JLabel totalHitLabel;
    private GameBoard gameBoard;
    private GameStatus gameStatus;

    public BattleshipGUI() {
        super("Battleship Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameBoard = new GameBoard();
        gameStatus = new GameStatus();

        initializeGUI();
        startNewGame();

        pack();
        setLocationRelativeTo(null);
    }

    private void initializeGUI() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Battleship", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Game board
        JPanel boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        boardButtons = new JButton[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                boardButtons[i][j] = createBoardButton(i, j);
                boardPanel.add(boardButtons[i][j]);
            }
        }
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        // Status panel
        JPanel statusPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        missCounterLabel = new JLabel("Misses: 0");
        strikeCounterLabel = new JLabel("Strikes: 0");
        totalMissLabel = new JLabel("Total Misses: 0");
        totalHitLabel = new JLabel("Total Hits: 0");

        statusPanel.add(missCounterLabel);
        statusPanel.add(strikeCounterLabel);
        statusPanel.add(totalMissLabel);
        statusPanel.add(totalHitLabel);

        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton playAgainButton = new JButton("Play Again");
        JButton quitButton = new JButton("Quit");

        playAgainButton.addActionListener(e -> handlePlayAgain());
        quitButton.addActionListener(e -> handleQuit());

        controlPanel.add(playAgainButton);
        controlPanel.add(quitButton);

        // Right side panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(statusPanel, BorderLayout.NORTH);
        rightPanel.add(controlPanel, BorderLayout.SOUTH);

        mainPanel.add(rightPanel, BorderLayout.EAST);

        add(mainPanel);
    }

    private JButton createBoardButton(int row, int col) {
        JButton button = new JButton("~");
        button.setPreferredSize(new Dimension(40, 40));
        button.setBackground(new Color(173, 216, 230)); // Light blue
        button.addActionListener(e -> handleCellClick(row, col));
        return button;
    }

    private void handleCellClick(int row, int col) {
        JButton button = boardButtons[row][col];
        if (!button.isEnabled()) return;

        Cell.CellState result = gameBoard.shoot(row, col);
        button.setEnabled(false);

        if (result == Cell.CellState.HIT) {
            button.setText("X");
            button.setBackground(Color.RED);
            gameStatus.recordHit();

            Ship ship = gameBoard.getShipAt(row, col);
            if (ship.isSunk()) {
                JOptionPane.showMessageDialog(this, "Ship Sunk!");
                if (gameBoard.isGameWon()) {
                    handleGameWon();
                }
            }
        } else {
            button.setText("M");
            button.setBackground(Color.YELLOW);
            gameStatus.recordMiss();

            if (gameStatus.isGameLost()) {
                handleGameLost();
            }
        }

        updateStatusLabels();
    }

    private void updateStatusLabels() {
        missCounterLabel.setText("Misses: " + gameStatus.getMissCounter());
        strikeCounterLabel.setText("Strikes: " + gameStatus.getStrikeCounter());
        totalMissLabel.setText("Total Misses: " + gameStatus.getTotalMisses());
        totalHitLabel.setText("Total Hits: " + gameStatus.getTotalHits());
    }

    private void handleGameWon() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Congratulations! You've won! Would you like to play again?",
                "Victory!",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            startNewGame();
        } else {
            System.exit(0);
        }
    }

    private void handleGameLost() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Game Over! Too many strikes. Would you like to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            startNewGame();
        } else {
            System.exit(0);
        }
    }

    private void handlePlayAgain() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to start a new game?",
                "New Game",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            startNewGame();
        }
    }

    private void handleQuit() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Quit Game",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void startNewGame() {
        gameBoard = new GameBoard();
        gameBoard.placeShips();
        gameStatus.reset();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                boardButtons[i][j].setText("~");
                boardButtons[i][j].setBackground(new Color(173, 216, 230));
                boardButtons[i][j].setEnabled(true);
            }
        }

        updateStatusLabels();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BattleshipGUI().setVisible(true);
        });
    }
}