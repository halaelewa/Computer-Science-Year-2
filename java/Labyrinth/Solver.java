import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solver {

    /**
     * The Graph for the labyrinth.
     */
    private Graph graph;
    /**
     * The Entrance node of the labyrinth.
     */
    private Node entrance;
    /**
     * The Exit node of the labyrinth.
     */
    private Node exit;
    /**
     * A Stack to hold the nodes that represent the path from entrance to exit.
     * (this instance variable only used to help with finding the path)
     */
    private Stack<Node> path;

    // A Stack to hold the previous edges that will be marked to help with finding the path.
    private Stack<Edge> previous;
    /**
     * Represents the number of blast bombs the person has.
     */
    private int blastBombs;
    /**
     * Represents the number of melt bombs the person has.
     */
    private int meltBombs;

    /**
     * This constructor will read the input file and build the graph from it.
     *
     * @param inputFile Input file to build the graph from
     */
    public Solver(String inputFile) throws LabyrinthException, IOException, GraphException {

        BufferedReader reader;
        try {  // read the input file
            reader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {  // input file not found
            System.out.printf("%s file not found.\n", inputFile);
            throw new LabyrinthException("\"%s file not found.".formatted(inputFile));
        }

        path = new Stack<>(); // initialize the path stack
        previous = new Stack<>(); // initialize the previous stack

        reader.readLine(); // skip the first line

        int width = Integer.parseInt(reader.readLine()); // get width from second line
        int length = Integer.parseInt(reader.readLine()); // get length from third line

        graph = new Graph(width * length); // initialize the graph with n = width * length

        blastBombs = Integer.parseInt(reader.readLine()); // get the blast bombs number from fourth line
        meltBombs = Integer.parseInt(reader.readLine()); // get the metal bombs number from fifth line

        // store the labyrinth representation
        char[][] labyrinth = new char[length + (length - 1)][width + (width - 1)];

        // store the nodes of the graph
        Node[][] nodes = new Node[length + (length - 1)][width + (width - 1)];

        // read the rest of the lines and store the labyrinth
        int row = 0; // used to know what row number we've reached
        String line;
        while ((line = reader.readLine()) != null) {
            for (int i = 0; i < line.length(); i++)
                labyrinth[row][i] = line.charAt(i);

            row++;
        }

        // store the nodes (rooms)
        row = 0;
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                if (labyrinth[i][j] == 'e' || labyrinth[i][j] == 'o' || labyrinth[i][j] == 'x')
                    nodes[i][j] = graph.getNode(row++);
            }
        }

        // build the graph
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                char c = labyrinth[i][j];

                if (c == 'e') // get the entrance
                    entrance = nodes[i][j];

                else if (c == 'x') // get the exit
                    exit = nodes[i][j];

                // a vertical edge, get the node above and below and connects them together with it
                if (c == 'B' || c == 'R' || c == 'M' || c == '|') {
                    int type = c == '|' ? 1 : c == 'B' ? 2 : c == 'R' ? 3 : 4;
                    graph.insertEdge(nodes[i + 1][j], nodes[i - 1][j], type);
                }

                // a horizontal edge, get the node before/left and after/right and connects them together with it
                else if (c == 'b' || c == 'r' || c == 'm' || c == '-') {
                    int type = c == '-' ? 1 : c == 'b' ? 2 : c == 'r' ? 3 : 4;
                    graph.insertEdge(nodes[i][j - 1], nodes[i][j + 1], type);
                }
            }
        }
    }

    /**
     * Get the graph of the labyrinth.
     *
     * @return Graph if it does exist.
     * @throws LabyrinthException Thrown if the graph does not exist.
     */
    public Graph getGraph() throws LabyrinthException {
        // graph does not exist
        if (graph == null)
            throw new LabyrinthException("Graph does not exist.");

        return graph;
    }

    /**
     * Solve the labyrinth.
     *
     * @return Iterator if there is a path from entrance node to exit node, null otherwise
     * @throws GraphException Thrown if the nodes does not exist in the graph.
     */
    public Iterator solve() throws GraphException {
        // start finding the path from the exit, that would be better to find the best route
        solveHelper(exit);

        // return iterator if the there is a path from entrance to exit, null otherwise
        if (path.isEmpty())
            return null;

        // reverse the path stack, so that we get the path from entrance to exit to draw it correctly
        Stack<Node> tmp = new Stack<>();
        while (!path.isEmpty())
            tmp.push(path.pop());

        return tmp.iterator();
    }

    private boolean solveHelper(Node u) throws GraphException {
        path.push(u); // push the node into the stack

        // BaseCase: if this the entrance, return true
        if (u == entrance)
            return true;

        // mark the node
        u.setMark(true);

        // get the incident edges of the node
        ArrayList<Edge> incEdges = graph.incidentEdges(u);

        // loop through all the edges
        for (Edge edge : incEdges) {

            // if the edge connects to an unmarked node
            if (!edge.firstEndpoint().getMark()) {
                // Check the types

                if (edge.getType() == 1) { // the edge is a corridor
                    previous.push(edge);

                    // try the first end point node of the current edge
                    if (solveHelper(edge.firstEndpoint()))
                        return true;
                }

                // the edge is a brick wall and there is enough blast bombs to explode it
                else if (edge.getType() == 2 && blastBombs >= 1) {
                    previous.push(edge);
                    blastBombs--;
                    if (solveHelper(edge.firstEndpoint()))
                        return true;
                }

                // the edge is a rock wall and there is enough blast bombs to explode it
                else if (edge.getType() == 3 && blastBombs >= 2) {
                    previous.push(edge);
                    blastBombs -= 2;
                    if (solveHelper(edge.firstEndpoint()))
                        return true;
                }

                // the edge is metal wall and there is enough melt bombs to explode it
                else if (edge.getType() == 4 && meltBombs >= 1) {
                    previous.push(edge);
                    meltBombs--;
                    if (solveHelper(edge.firstEndpoint()))
                        return true;
                }
            }

            // if the edge connects to an unmarked node
            else if (!edge.secondEndpoint().getMark()) {
                // Check the types

                if (edge.getType() == 1) {
                    previous.push(edge);
                    if (solveHelper(edge.secondEndpoint()))
                        return true;
                } else if (edge.getType() == 2 && blastBombs >= 1) {
                    previous.push(edge);
                    blastBombs--;
                    if (solveHelper(edge.secondEndpoint()))
                        return true;
                } else if (edge.getType() == 3 && blastBombs >= 2) {
                    previous.push(edge);
                    blastBombs -= 2;
                    if (solveHelper(edge.secondEndpoint()))
                        return true;
                } else if (edge.getType() == 4 && meltBombs >= 1) {
                    previous.push(edge);
                    meltBombs--;
                    if (solveHelper(edge.secondEndpoint()))
                        return true;
                }
            }
        }

        // The Person couldn't reach the exit, retrace the steps
        // and re increase all the bombs and pop edges from previous
        if (!previous.isEmpty()) {
            Edge temp = previous.pop();

            if (temp.getType() == 2)
                blastBombs++;
            else if (temp.getType() == 3)
                blastBombs += 2;
            else if (temp.getType() == 4)
                meltBombs++;
        }

        u.setMark(false); // unmark the node
        path.pop(); // pop it
        return false;
    }
}
