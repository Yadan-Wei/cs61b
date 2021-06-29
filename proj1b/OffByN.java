public class OffByN implements CharacterComparator {
    private final int diffN;

    public OffByN(int N) {
        diffN = N;
    }
    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        return diff == diffN || diff == -diffN;
    };
}
