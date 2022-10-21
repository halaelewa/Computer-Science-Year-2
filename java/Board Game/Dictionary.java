import java.util.LinkedList;

/**
 * This class implements a dictionary using a hash table with separate chaining.
 */
public class Dictionary implements DictionaryADT {

    private int size; // size of the table
    private LinkedList<Layout>[] hashTable;

    /**
     * Initialize the size of the table and the Dictionary with an empty hash table.
     *
     * @param size Size of the table.
     */
    public Dictionary(int size) {
        this.size = size;
        hashTable = (LinkedList<Layout>[]) new LinkedList[size];

        for (int i = 0; i < size; i++)
            hashTable[i] = new LinkedList<>();
    }

    /**
     * Inserts in the dictionary the Layout object referenced by 'data' in the dictionary. Throws
     * a DictionaryException if the key attribute of 'data' is already in the dictionary.
     *
     * @param data Data to be inserted into the Dictionary
     * @return 1 if there is a collision, 0 if not.
     * @throws DictionaryException Will be thrown if the key does exist in the Dictionary.
     */
    @Override
    public int put(Layout data) throws DictionaryException {
        int collision = 0;
        final int h = hash(data.getBoardLayout()); // create a hash code from boardLayout
        LinkedList<Layout> head = hashTable[h]; // get the node associated with hashcode if exits

        // If the key exits
        if (head.size() != 0) {
            for (int i = 0; i < head.size(); i++)
                if (head.get(i).getBoardLayout().equals(data.getBoardLayout()))
                    throw new DictionaryException("Key is already exists in the Dictionary!");

            collision = 1;
        }

        head.add(data);
        return collision;
    }

    /**
     * Removes from the dictionary the Layout object with the same key attribute as parameter 'boardLayout'.
     * Throws a DictionaryException if there is no Layout object with key attribute equal to 'boardLayout'
     * in the dictionary.
     *
     * @param boardLayout Key to specify the object of the Dictionary to be removed.
     * @throws DictionaryException Will be thrown if such a key not found.
     */
    @Override
    public void remove(String boardLayout) throws DictionaryException {
        LinkedList<Layout> head = hashTable[hash(boardLayout)];
        boolean removed = false;

        for (int i = 0; i < head.size(); i++) {
            if (head.get(i).getBoardLayout().equals(boardLayout)) {
                head.remove(i);
                removed = true;
                break;
            }
        }

        if (!removed)
            throw new DictionaryException("Key is not found in the Dictionary!");
    }

    /**
     * Returns the score in the Layout object with the same kay attribute as 'boardLayout'; returns
     * -1 if no Layout object in the dictionary has this key.
     *
     * @param boardLayout Key to get the score associated with.
     * @return Score associated with the given key, -1 if key not found.
     */
    @Override
    public int getScore(String boardLayout) {
        LinkedList<Layout> head = hashTable[hash(boardLayout)];

        int score = -1;
        for (int i = 0; i < head.size(); i++) {
            if (head.get(i).getBoardLayout().equals(boardLayout)) {
                score = head.get(i).getScore();
                break;
            }
        }

        return score;
    }

    /**
     * @param string A string to generate a hash from.
     * @return An integer represents a hash code from the given string.
     */
    private int hash(String string) {
        int hash = 2;
        for (int i = 0; i < string.length(); i++)
            hash = (11 * hash + string.charAt(i)) % size;

        return hash;
    }
}
