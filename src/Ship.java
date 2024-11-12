public class Ship {
    private int size;
    private int hits;
    private boolean isHorizontal;
    private Position startPosition;

    public Ship(int size) {
        this.size = size;
        this.hits = 0;
    }

    public void hit() { hits++; }
    public boolean isSunk() { return hits >= size; }
    public int getSize() { return size; }
    public void setPosition(Position pos, boolean horizontal) {
        this.startPosition = pos;
        this.isHorizontal = horizontal;
    }
    public boolean isHorizontal() { return isHorizontal; }
    public Position getStartPosition() { return startPosition; }
}