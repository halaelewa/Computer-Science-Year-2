public class Node {

    /**
     * Represents the name of the node (between 0 and n-1)
     */
    private int name;
    /**
     * To determine whether the node is marked or not
     */
    private boolean marked;

    /**
     * Create an unmarked node with the given name
     */
    public Node(int nodeName) {
        name = nodeName;
        marked = false;
    }

    /**
     * Mark this node with true or false.
     *
     * @param marked A Boolean value true or false to mark this node.
     */
    public void setMark(boolean marked) {
        this.marked = marked;
    }

    /**
     * Get the name of the node.
     *
     * @return The Name of the node.
     */
    public int getName() {
        return name;
    }

    /**
     * Return true if the node is marked, false otherwise.
     *
     * @return Boolean value whether the node is marked or not.
     */
    public boolean getMark() {
        return marked;
    }

    /**
     * Compare this node with the other node using their names.
     *
     * @param otherNode Other node to compare with this node.
     * @return True if the two nodes are equals, false otherwise.
     */
    public boolean equals(Node otherNode) {
        return name == otherNode.getName();
    }
}
