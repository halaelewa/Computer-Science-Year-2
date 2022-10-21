import java.io.*;
import java.util.ArrayList;

public class Query {
    public static void main(String[] args) throws IOException, MultimediaException, DictionaryException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(args[0]));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("File name argument was not provided. (java Query.java {filename.txt})");
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.out.printf("File \"%s\" not found!", args[0]);
            System.exit(1);
        }

        BSTOrderedDictionary dictionary = new BSTOrderedDictionary();
        BSTNode root = dictionary.getRoot();

        String line;
        while ((line = reader.readLine()) != null) {
            String key = line;
//            System.out.println(key);
            String s = reader.readLine();

            int type;
            if (s.endsWith(".wav") || s.endsWith(".mid"))
                type = 2;
            else if (s.endsWith(".gif") || s.endsWith(".jpg"))
                type = 3;
            else if (s.endsWith(".html"))
                type = 4;
            else
                type = 1;

            dictionary.put(root, key, s, type);
            root = dictionary.getRoot();
        }

        StringReader keyboard = new StringReader();
        line = keyboard.read("Enter next command: ");

        while (!line.equals("end")) {

            // split the command and get the key
            String[] arguments = line.split(" ");

            // GET COMMAND
            if (line.contains("get")) {
                String key = arguments[1];

                // get the data list corresponding to the node with the given key
                ArrayList<MultimediaItem> list = dictionary.get(root, key);

                // there is a node with the given key
                if (list != null) {
                    // go for each media object
                    for (MultimediaItem media : list) {
                        int type = media.getType();
                        if (type == 1) // type is text
                            System.out.println(media.getContent());
                        else if (type == 2) { // type is sound
                            try {
                                new SoundPlayer().play(media.getContent());
                            } catch (MultimediaException e) {
                                System.out.println(e.getMessage());
                            }
                        } else if (type == 3) { // type is picture
                            try {
                                new PictureViewer().show(media.getContent());
                            } catch (MultimediaException e) {
                                System.out.println(e.getMessage());
                            }
                        } else { // type is html file
                            try {
                                new ShowHTML().show(media.getContent());
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                } else { // key not found in dictionary
                    System.out.printf("The word %s is not in the ordered dictionary.\n", key);

                    // put the key in the dictionary
                    dictionary.put(root, key, "", 1);

                    // get successor of the node
                    NodeData data = dictionary.successor(root, key);
                    String following = data != null ? data.getName() : "";

                    // get predecessor of the node
                    data = dictionary.predecessor(root, key);
                    String preceding = data != null ? data.getName() : "";

                    // remove the key from the dictionary
                    dictionary.remove(root, key);

                    System.out.printf("Preceding word: %s\n", preceding);
                    System.out.printf("Following word: %s\n", following);
                }
            }

            // REMOVE COMMAND
            else if (line.contains("remove")) {
                String key = arguments[1];

                // remove the key from the dictionary
                try {
                    dictionary.remove(root, key);
                } catch (DictionaryException e) {
                    System.out.printf("No record in the ordered dictionary has key %s\n", key);
                }
            }

            // DELETE COMMAND
            else if (line.contains("delete")) {
                String key = arguments[1];
                int type = Integer.parseInt(arguments[2]);

                // delete the key from dictionary
                try {
                    dictionary.remove(root, key, type);
                } catch (DictionaryException e) {
                    System.out.printf("No record in the ordered dictionary has key %s\n", key);
                }
            }

            // ADD COMMAND
            else if (line.contains("add")) {
                String key = arguments[1];
                String content = arguments[2];
                int type = Integer.parseInt(arguments[3]);

                dictionary.put(root, key, content, type); // add a node to the dict with the given key
            }

            // FIRST COMMAND
            else if (line.contains("first")) {
                if (root != null) {
                    NodeData smallest = dictionary.smallest(root); // get the smallest node
                    System.out.println(smallest.getName());
                } else
                    System.out.println("The ordered dictionary is empty.");
            }

            // LAST COMMAND
            else if (line.contains("last")) {
                if (root != null) {
                    NodeData largest = dictionary.largest(root); // get largest node
                    System.out.println(largest.getName());
                } else
                    System.out.println("The ordered dictionary is empty.");
            }

            // SIZE COMMAND
            else if (line.contains("size")) {
                System.out.printf("There are %d keys in the ordered dictionary.\n", dictionary.getNumInternalNodes());
            }

            // NEXT COMMAND
            else if (line.contains("next")) {
                String key = arguments[1];
                boolean q = true;
                int d = Integer.parseInt(arguments[2]);

                ArrayList<MultimediaItem> data = dictionary.get(root, key);

                // key not found in the dictionary, add the key to dict
                if (data == null)
                    dictionary.put(root, key, "temp", 1);

                else
                    System.out.print(key + " ");

                NodeData successor = dictionary.successor(root, key);
                while (d-- > 0) {
                    if (successor == null)
                        break;
                    q = false; // mark that there is at least one successor
                    System.out.print(successor.getName() + (d > 0 ? " " : ""));
                    successor = dictionary.successor(root, successor.getName());
                }

                System.out.print(q ? "There are no keys larger than or equal to " + key + ".\n" : "\n");
                if (data == null) // remove the node if it was added
                    dictionary.remove(root, key);
            }

            // PREV COMMAND
            else if (line.contains("prev")) {
                String key = arguments[1];
                boolean q = true;
                int d = Integer.parseInt(arguments[2]);

                ArrayList<MultimediaItem> data = dictionary.get(root, key);

                // key not found in the dictionary, add the key to dict
                if (data == null)
                    dictionary.put(root, key, "temp", 1);

                else
                    System.out.print(key + " ");

                NodeData predecessor = dictionary.predecessor(root, key);
                while (d-- > 0) {
                    if (predecessor == null) break;
                    q = false; // mark  that there is at least one predecessor
                    System.out.print(predecessor.getName() + (d > 0 ? " " : ""));
                    predecessor = dictionary.predecessor(root, predecessor.getName());
                }

                System.out.print(q ? "There are no keys smaller than or equal to " + key + ".\n" : "\n");
                if (data == null) // remove the node if it was added
                    dictionary.remove(root, key);
            }

            else
                System.out.println("Invalid command.");

            line = keyboard.read("Enter next command: ");
        }
    }
}
