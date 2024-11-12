public class GameStatus {
    private int missCounter;
    private int strikeCounter;
    private int totalMisses;
    private int totalHits;

    public GameStatus() {
        reset();
    }

    public void reset() {
        missCounter = 0;
        strikeCounter = 0;
        totalMisses = 0;
        totalHits = 0;
    }

    public void recordHit() {
        totalHits++;
        missCounter = 0;
    }

    public void recordMiss() {
        totalMisses++;
        missCounter++;
        if (missCounter >= 5) {
            strikeCounter++;
            missCounter = 0;
        }
    }

    public boolean isGameLost() {
        return strikeCounter >= 3;
    }

    public int getMissCounter() { return missCounter; }
    public int getStrikeCounter() { return strikeCounter; }
    public int getTotalMisses() { return totalMisses; }
    public int getTotalHits() { return totalHits; }
}