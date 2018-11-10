package redblacktree;

class Node<E> {

    private E value;
    private ColorRB color;
    private Node<E> parent, leftChild, rightChild;

    Node(E value) {
        this(value, null, null, null);
    }

    private Node(E value, Node<E> parent, Node<E> left, Node<E> right) {

        this.value = value;

        this.parent = parent;
        leftChild = left;
        rightChild = right;

        color = ColorRB.RED;

    }

    E getValue() {
        return value;
    }

    void setValue(E newValue) {
        value = newValue;
    }

    Node<E> getParent() {
        return parent;
    }

    void setParent(Node<E> newParent) {
        parent = newParent;
    }

    Node<E> getRightChild() {
        return rightChild;
    }

    void setRightChild(Node<E> newRightChild) {
        rightChild = newRightChild;
    }

    Node<E> getLeftChild() {
        return leftChild;
    }

    void setLeftChild(Node<E> newLeftChild) {
        leftChild = newLeftChild;
    }

    ColorRB getColor() {
        return color;
    }

    void setColor(ColorRB newColor) {
        color = newColor;
    }

    Node<E> getUncle() {

        if (parent == null || parent.parent == null) return null;

        if (parent.isOnLeft()) {
            return parent.parent.rightChild;
        } else {
            return parent.parent.leftChild;
        }

    }

    boolean isOnLeft() {
        return (this == parent.leftChild);
    }

    Node<E> getSibling() {

        if (parent == null) return null;

        if (isOnLeft()) {
            return parent.rightChild;
        } else {
            return parent.leftChild;
        }

    }

    void moveDown(Node<E> newParent) {

        if (parent != null) {
            if (isOnLeft()) {
                parent.leftChild = newParent;
            } else {
                parent.rightChild = newParent;
            }
        }

        newParent.parent = parent;
        parent = newParent;

    }

    boolean hasRedChild() {
        return ( (leftChild != null && leftChild.color == ColorRB.RED) || (rightChild != null && rightChild.color == ColorRB.RED) );
    }

}