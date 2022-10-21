import java.util.ArrayList;

public class Graph implements GraphADT {

    /**
     * The number of the nodes in the graph.
     */
    private int n;
    /**
     * Adjacency matrix to represent the graph.
     */
    private Edge[][] edges;
    /**
     * An array to hold the nodes in the graph.
     */
    private Node[] nodes;

    /**
     * Construct the nodes array for the graph.
     *
     * @param n The number of the nodes in the graph.
     */
    public Graph(int n) {
        this.n = n;

        edges = new Edge[n][n]; // initialize the edges matrix
        nodes = new Node[n]; // initialize the nodes array

        for (int i = 0; i < n; i++)
            nodes[i] = new Node(i);
    }

    /**
     * Insert an edge between the two specified nodes.
     *
     * @param nodeu First node of the edge.
     * @param nodev Second node of the edge.
     * @param type Type of the edge
     * @throws GraphException Thrown if an edge is already existed, or one of the nodes is not in the graph.
     */
    @Override
    public void insertEdge(Node nodeu, Node nodev, int type) throws GraphException {
        // Check if graph contains both nodes
        if (nodeu.getName() >= n || nodev.getName() >= n || nodeu.getName() < 0 || nodev.getName() < 0) {
            throw new GraphException("U node and/or V node are not nodes of the graph.");
        }

        // Check if edge exists already
        if (edges[nodeu.getName()][nodev.getName()] != null) {
            throw new GraphException("Edge is already exists!");
        }

        Edge insertedEdge = new Edge(nodeu, nodev, type); // create new edge to be inserted

        // insert the edge in the adjacency matrix
        edges[nodeu.getName()][nodev.getName()] = insertedEdge;
        edges[nodev.getName()][nodeu.getName()] = insertedEdge;
    }

    /**
     * Return the node with the given name.
     *
     * @param u Name of node to get.
     * @return The node with the given name.
     * @throws GraphException Thrown if the node not in the graph.
     */
    @Override
    public Node getNode(int u) throws GraphException {
        // the node not in the graph
        if (u >= n)
            throw new GraphException("Node '%d' does not exists.".formatted(u));

        return nodes[u];
    }

    /**
     * Get an ArrayList of the incident edges of a node. (null if there's none)
     *
     * @param u The node to get its incident edges.
     * @return ArrayList of the incident edges of u node, null if there's none.
     * @throws GraphException Thrown if the node u not in the graph
     */
    @Override
    public ArrayList<Edge> incidentEdges(Node u) throws GraphException {
        // the node u not in the graph
        if (u.getName() < 0 || u.getName() >= n)
            throw new GraphException("The node %d does not exist.".formatted(u.getName()));

        ArrayList<Edge> incidentEdges = new ArrayList<>();
        for (int i = 0; i < n; i++) {

            // An edge exist in the graph (not equal to null)
            if (edges[u.getName()][i] != null)
                incidentEdges.add(edges[u.getName()][i]);
        }

        // return incidentEdges if it's not empty, null otherwise
        return incidentEdges.isEmpty() ? null : incidentEdges;
    }

    /**
     * Get the edge that connects the two given nodes.
     *
     * @param u First node of the edge.
     * @param v Second node of the edge.
     * @return An edge between u and v, null if there's not an edge.
     * @throws GraphException Thrown if the nodes are not in the graph or no edge between u and v.
     */
    @Override
    public Edge getEdge(Node u, Node v) throws GraphException {
        // The graph does not contain the nodes u, v
        if (u.getName() >= n || v.getName() >= n || u.getName() < 0 || v.getName() < 0)
            throw new GraphException("U node and/or V node are not nodes of the graph. (BOTH nodes should be existed)");

        // There is no edge between u and v
        if (edges[u.getName()][v.getName()] == null)
            throw new GraphException("There is no edge between node %d and node %d.".formatted(u.getName(), v.getName()));

        // return the edge between u and v
        return edges[u.getName()][v.getName()];
    }

    /**
     * Check if the nodes u and v are adjacent. (They are adjacent if there is an edge connects them)
     *
     * @param u First node of the edge.
     * @param v Second node of the edge.
     * @return True if there is an edge between u and v, false otherwise.
     * @throws GraphException Thrown if nodes u and v are not nodes of the graph.
     */
    @Override
    public boolean areAdjacent(Node u, Node v) throws GraphException {
        // The graph does not contain the nodes u, v
        if (u.getName() >= n || v.getName() >= n || u.getName() < 0 || v.getName() < 0)
            throw new GraphException("U node and/or V node are not nodes of the graph. (BOTH nodes should be existed)");

        return edges[u.getName()][v.getName()] != null;
    }
}
