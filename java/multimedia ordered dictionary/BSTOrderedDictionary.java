import java.util.ArrayList;

public class BSTOrderedDictionary implements BSTOrderedDictionaryADT {

    BSTNode root;
    int numInternalNodes;

    public BSTOrderedDictionary() {
        root = null;
        numInternalNodes = 0;
    }

    @Override
    public BSTNode getRoot() {
        return root;
    }

    @Override
    public int getNumInternalNodes() {
        return numInternalNodes;
    }

    @Override
    public ArrayList<MultimediaItem> get(BSTNode r, String k) {
        k = k.toLowerCase();

        if (r == null)
            return null;

        if (k.equals(r.getData().getName()))
            return r.getData().getMedia();

        else {
            if (k.compareTo(r.getData().getName()) < 0)
                return get(r.getLeftChild(), k);
            else
                return get(r.getRightChild(), k);
        }
    }

    @Override
    public void put(BSTNode r, String key, String content, int type) {
        key = key.toLowerCase();
        NodeData data = new NodeData(key);
        MultimediaItem media = new MultimediaItem(content, type);// create the data
        data.add(media);

        // the root node is empty so create a new node and make it the root
        if (root == null) {
            // create the root data
            root = new BSTNode(); // create the root node
            root.setData(data);
            numInternalNodes++;
        } else {

            BSTNode p = null, curr = r;

            while (curr != null) {
                p = curr; // update p to be the parent of the curr when the next iteration happens

                // go left
                if (key.compareTo(curr.getData().getName()) < 0)
                    curr = curr.getLeftChild();

                    // go right
                else if (key.compareTo(curr.getData().getName()) > 0)
                    curr = curr.getRightChild();

                else { // node with key is found, append tha data to the list in that node
                    curr.getData().getMedia().add(new MultimediaItem(content, type));
                    break;
                }
            }

            if (curr == null)
                // add node to the left
                if (key.compareTo(p.getData().getName()) < 0) {
                    BSTNode left = new BSTNode();
                    left.setData(data);
                    p.setLeftChild(left);
                    left.setParent(p);
                    numInternalNodes++;
                } else { // add node to the right
                    BSTNode right = new BSTNode();
                    right.setData(data);
                    p.setRightChild(right);
                    right.setParent(p);
                    numInternalNodes++;
                }
        }
    }

    @Override
    public void remove(BSTNode r, String k) throws DictionaryException {
        BSTNode p = null, curr = r;
        k = k.toLowerCase();

        while (curr != null && !curr.getData().getName().equals(k)) {
            p = curr;  // update p to be the parent of the curr node

            // go left sub-tree
            if (curr.getData().getName().compareTo(k) > 0)
                curr = curr.getLeftChild();

            else // go right sub-tree
                curr = curr.getRightChild();
        }

        // no node with the given key is not found
        if (curr == null) {
            throw new DictionaryException("No node with the given key \"" + k + "\"");
        }

        // the node with the given key is found
        // The node is a leaf node
        if (curr.isLeaf()) {
            // the node to be deleted is not the root,
            // set its parent's left/right child to null
            if (curr != r) {
                if (p.getLeftChild() == curr)
                    p.setLeftChild(null);
                else
                    p.setRightChild(null);
            } else {  // the tree has only the root node, set it to null
                if (curr == root) {
//                    root.setData(null);
                    root = null;
                }
                else if (curr.getParent().getLeftChild() == curr)
                    curr.getParent().setLeftChild(null);
                else if (curr.getParent().getRightChild() == curr)
                    curr.getParent().setRightChild(null);
                curr.setParent(null);
                numInternalNodes--;
            }
        }

        // The node has two children
        else {
            if (curr.getLeftChild() != null && curr.getRightChild() != null) {

                // find the successor of the node
                BSTNode successor = curr.getRightChild();
                while (successor.getLeftChild() != null)
                    successor = successor.getLeftChild();

                NodeData tmp = successor.getData();  // store successor data
                curr.setData(tmp);  // replace the curr node data with the successor data
                remove(successor, tmp.getName());  // delete the successor node

            }

            // The node has ONLY one child
            else {
                BSTNode child = curr.getLeftChild() != null ? curr.getLeftChild() : curr.getRightChild();
                if (curr != r) {
                    child.setParent(p);
                    if (p.getLeftChild() == curr) {
                        p.setLeftChild(child);
                    } else {
                        p.setRightChild(child);
                    }
                } else {  // the node is the root
                    root.setData(child.getData());

                    if (child.getRightChild() != null) {
                        root.setRightChild(child.getRightChild());
                        child.getRightChild().setParent(root);
                    } else if (child.getLeftChild() != null) {
                        root.setLeftChild(child.getLeftChild());
                        child.getLeftChild().setParent(root);
                    }
                }

            }
            numInternalNodes--;
        }
    }

    @Override
    public void remove(BSTNode r, String k, int type) throws DictionaryException {
        k = k.toLowerCase();
        BSTNode curr = r;
        while (curr != null) {
            if (k.compareTo(curr.getData().getName()) < 0)
                curr = curr.getLeftChild();
            else if (k.compareTo(curr.getData().getName()) > 0)
                curr = curr.getRightChild();
            else
                break;
        }

        if (curr == null)
            throw new DictionaryException("No key was found in the ordered dictionary.");


        ArrayList<MultimediaItem> list = curr.getData().getMedia();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == type)
                list.remove(i);
        }

        if (list.isEmpty())
            remove(curr, k);

    }

    @Override
    public NodeData successor(BSTNode r, String k) {
        k = k.toLowerCase();
        BSTNode curr = r;
        while (curr != null) {
            if (k.compareTo(curr.getData().getName()) < 0)
                curr = curr.getLeftChild();
            else if (k.compareTo(curr.getData().getName()) > 0)
                curr = curr.getRightChild();
            else
                break;
        }

        if (curr == null)
            return null;

        r = curr;
        // successor will be the smallest node in the right sub-tree
        if (r.getRightChild() != null)
            return smallest(r.getRightChild());

        // start searching for the successor from the ancestors
        BSTNode parent = r.getParent();
        while (parent != null && r == parent.getRightChild()) {
            r = parent;
            parent = parent.getParent();
        }

        if (parent == null)
            return null;

        return parent.getData();
    }

    @Override
    public NodeData predecessor(BSTNode r, String k) {
        k = k.toLowerCase();
        BSTNode curr = r;
        while (curr != null) {
            if (k.compareTo(curr.getData().getName()) < 0)
                curr = curr.getLeftChild();
            else if (k.compareTo(curr.getData().getName()) > 0)
                curr = curr.getRightChild();
            else
                break;
        }

        if (curr == null)
            return null;

        r = curr;
        // successor will be the greatest node in the left sub-tree
        if (r.getLeftChild() != null)
            return smallest(r.getLeftChild());

        // start searching for the successor from the ancestors
        BSTNode parent = r.getParent();
        while (parent != null && r == parent.getLeftChild()) {
            r = parent;
            parent = parent.getParent();
        }

        if (parent == null)
            return null;

        return parent.getData();
    }

    @Override
    public NodeData smallest(BSTNode r) {
        while (r.getLeftChild() != null)
            r = r.getLeftChild();
        return r.getData();
    }

    @Override
    public NodeData largest(BSTNode r) {
        while (r.getRightChild() != null)
            r = r.getRightChild();
        return r.getData();
    }

    public void printTree(BSTNode r) {
        if (r == null)
            return;

        printTree(r.getLeftChild());
        System.out.print(r.getData().getName() + " ");
//        String str = r.getData().getName() + "(";
//        for (int i = 0; i < r.getData().getMedia().size(); i++) {
//            MultimediaItem media = r.getData().getMedia().get(i);
//            str += media.getType();
//            if (i < r.getData().getMedia().size() - 1)
//                str += ", ";
//        }
//        str += ") ";
//        System.out.print(str);
        printTree(r.getRightChild());
    }
}
