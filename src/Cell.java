public class Cell {
    public enum CellState { BLANK, HIT, MISS }

    private CellState state;
    private Ship ship;

    public Cell() {
        state = CellState.BLANK;
        ship = null;
    }

    public CellState getState() { return state; }
    public void setState(CellState state) { this.state = state; }
    public Ship getShip() { return ship; }
    public void setShip(Ship ship) { this.ship = ship; }
    public boolean hasShip() { return ship != null; }
}
