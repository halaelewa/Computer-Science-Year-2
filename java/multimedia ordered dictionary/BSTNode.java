public class BSTNode {

    BSTNode parent;
    BSTNode leftChild;
    BSTNode rightChild;
    NodeData data;

    public BSTNode() {
        parent = leftChild = rightChild = null;
        data = null;
    }

    public BSTNode(BSTNode newParent, BSTNode newLeftChild, BSTNode newRightChild, NodeData newData) {
        parent = newParent;
        leftChild = newLeftChild;
        rightChild = newRightChild;
        data = newData;
    }

    public BSTNode getParent() {
        return parent;
    }

    public BSTNode getLeftChild() {
        return leftChild;
    }

    public BSTNode getRightChild() {
        return rightChild;
    }

    public NodeData getData() {
        return data;
    }

    public void setParent(BSTNode newParent) {
        this.parent = newParent;
    }

    public void setLeftChild(BSTNode newLeftChild) {
        this.leftChild = newLeftChild;
    }

    public void setRightChild(BSTNode newRightChild) {
        this.rightChild = newRightChild;
    }

    public void setData(NodeData newData) {
        this.data = newData;
    }

    public boolean isLeaf() {
        return (leftChild == null && rightChild == null);
    }
}
