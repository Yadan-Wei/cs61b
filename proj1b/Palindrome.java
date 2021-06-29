public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> res = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            res.addLast(character);
        }
        return res;
    }

    private boolean PalindromeHelper(Deque d) {
        if (d.size() == 0 || d.size() == 1) {
            return true;
        }
        if (d.removeFirst() == d.removeLast()) {
            return PalindromeHelper(d);
        }
        return false;
    }

    public boolean isPalindrome(String word) {
        Deque d = wordToDeque(word);
        return PalindromeHelper(d);
    }

    private boolean byOneHelper(Deque d, CharacterComparator cc) {
        if (d.size() == 0 || d.size() == 1) {
            return true;
        }
        if (cc.equalChars((char) d.removeFirst(), (char) d.removeLast())) {
            return byOneHelper(d, cc);
        }
        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque d = wordToDeque(word);
        return byOneHelper(d, cc);
    }
}
