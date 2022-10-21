public class Edge {

    /**
     * First node of the edge.
     */
    private Node firstEndPoint;
    /**
     * Second node of the edge.
     */
    private Node secondEndPoint;
    /**
     * type of edge:
     * <ol>
     *     <li>corridor.</li>
     *     <li>brick wall.</li>
     *     <li>rock wall.</li>
     *     <li>metal wall.</li>
     * </ol>
     **/
    private int type;
    /**
     * Creates an edge of the given type and node (u, v).
     *
     * @param u        first end point.
     * @param v        second end point.
     * @param edgeType edge type
     */
    public Edge(Node u, Node v, int edgeType) {
        firstEndPoint = u;
        secondEndPoint = v;
        type = edgeType;
    }

    /**
     * Get the first node of the edge.
     *
     * @return first end point of the edge.
     */
    public Node firstEndpoint() {
        return firstEndPoint;
    }

    /**
     * Get the second node of the edge.
     *
     * @return second end point.
     */
    public Node secondEndpoint() {
        return secondEndPoint;
    }

    /**
     * Get the type of the edge.
     *
     * @return type of the edge.
     */
    public int getType() {
        return type;
    }

    /**
     * Set the given type as a type for the edge.
     *
     * @param type type to be set.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Return true if this Edge has the same connects nodes as the OtherEdge.
     *
     * @param otherEdge another edge to compare it with this edge.
     * @return True if the edges have the same connects nodes, false otherwise.
     */
    public boolean equals(Edge otherEdge) {
        return (firstEndPoint.getName() == otherEdge.secondEndpoint().getName()
                && secondEndPoint.getName() == otherEdge.firstEndpoint().getName())
                || (firstEndPoint.getName() == otherEdge.firstEndpoint().getName()
                && secondEndPoint.getName() == otherEdge.secondEndpoint().getName());
    }
}
