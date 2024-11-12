import java.util.ArrayList;
import java.util.Random;

public class GameBoard {
    private static final int BOARD_SIZE = 10;
    private Cell[][] board;
    private ArrayList<Ship> ships;
    private Random random;

    public GameBoard() {
        board = new Cell[BOARD_SIZE][BOARD_SIZE];
        ships = new ArrayList<>();
        random = new Random();

        // Initialize board
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new Cell();
            }
        }

        // Create ships
        ships.add(new Ship(5));
        ships.add(new Ship(4));
        ships.add(new Ship(3));
        ships.add(new Ship(3));
        ships.add(new Ship(2));
    }

    public void placeShips() {
        for (Ship ship : ships) {
            boolean placed = false;
            while (!placed) {
                boolean horizontal = random.nextBoolean();
                int maxRow = horizontal ? BOARD_SIZE : BOARD_SIZE - ship.getSize();
                int maxCol = horizontal ? BOARD_SIZE - ship.getSize() : BOARD_SIZE;

                int row = random.nextInt(maxRow);
                int col = random.nextInt(maxCol);

                if (canPlaceShip(ship, row, col, horizontal)) {
                    placeShip(ship, row, col, horizontal);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceShip(Ship ship, int row, int col, boolean horizontal) {
        int size = ship.getSize();

        if (horizontal) {
            for (int c = col; c < col + size; c++) {
                if (board[row][c].hasShip()) return false;
            }
        } else {
            for (int r = row; r < row + size; r++) {
                if (board[r][col].hasShip()) return false;
            }
        }
        return true;
    }

    private void placeShip(Ship ship, int row, int col, boolean horizontal) {
        ship.setPosition(new Position(row, col), horizontal);
        int size = ship.getSize();

        if (horizontal) {
            for (int c = col; c < col + size; c++) {
                board[row][c].setShip(ship);
            }
        } else {
            for (int r = row; r < row + size; r++) {
                board[r][col].setShip(ship);
            }
        }
    }

    public Cell.CellState shoot(int row, int col) {
        Cell cell = board[row][col];
        if (cell.hasShip()) {
            cell.setState(Cell.CellState.HIT);
            cell.getShip().hit();
            return Cell.CellState.HIT;
        } else {
            cell.setState(Cell.CellState.MISS);
            return Cell.CellState.MISS;
        }
    }

    public boolean isGameWon() {
        return ships.stream().allMatch(Ship::isSunk);
    }

    public Ship getShipAt(int row, int col) {
        return board[row][col].getShip();
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }
}