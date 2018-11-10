package redblacktree;

import java.util.LinkedList;
import java.util.Queue;

class RBT<E> {

    private Node<E> root;

    // left rotates the given node
    private void leftRotate(Node<E> x) {
        // new parent will be node's right child
        Node<E> nParent = x.getRightChild();

        // update root if current node is root
        if (x == root)
            root = nParent;

        x.moveDown(nParent);

        // connect x with new parent's left element
        x.setRightChild(nParent.getLeftChild());
        // connect new parent's left element with node
        // if it is not null
        if (nParent.getLeftChild() != null)
            nParent.getLeftChild().setParent(x);

        // connect new parent with x
        nParent.setLeftChild(x);
    }

    private void rightRotate(Node<E> x) {
        // new parent will be node's left child
        Node<E> nParent = x.getLeftChild();

        // update root if current node is root
        if (x == root)
            root = nParent;

        x.moveDown(nParent);

        // connect x with new parent's right element
        x.setLeftChild(nParent.getRightChild());
        // connect new parent's right element with node
        // if it is not null
        if (nParent.getRightChild() != null)
            nParent.getRightChild().setParent(x);

        // connect new parent with x
        nParent.setRightChild(x);
    }

    private void swapColors(Node<E> x1, Node<E> x2) {
        ColorRB temp;
        temp = x1.getColor();
        x1.setColor(x2.getColor());
        x2.setColor(temp);
    }

    private void swapValues(Node<E> u, Node<E> v) {
        E temp;
        temp = u.getValue();
        u.setValue(v.getValue());
        v.setValue(temp);
    }

    // fix red red at given node
    private void fixRedRed(Node<E> x) {
        // if x is root color it black and return
        if (x == root) {
            x.setColor(ColorRB.BLACK);
            return;
        }

        // initialize parent, grandparent, uncle
        Node<E> parent = x.getParent(), grandparent = parent.getParent(), uncle = x.getUncle();

        if (parent.getColor() != ColorRB.BLACK) {
            if (uncle != null && uncle.getColor() == ColorRB.RED) {
                // uncle red, perform recoloring and recurse
                parent.setColor(ColorRB.BLACK);
                uncle.setColor(ColorRB.BLACK);
                grandparent.setColor(ColorRB.RED);
                fixRedRed(grandparent);
            } else {
                // Else perform LR, LL, RL, RR
                if (parent.isOnLeft()) {
                    if (x.isOnLeft()) {
                        // for left right
                        swapColors(parent, grandparent);
                    } else {
                        leftRotate(parent);
                        swapColors(x, grandparent);
                    }
                    // for left left and left right
                    rightRotate(grandparent);
                } else {
                    if (x.isOnLeft()) {
                        // for right left
                        rightRotate(parent);
                        swapColors(x, grandparent);
                    } else {
                        swapColors(parent, grandparent);
                    }

                    // for right right and right left
                    leftRotate(grandparent);
                }
            }
        }
    }

    // find node that do not have a left child
// in the subtree of the given node
    private Node<E> successor(Node<E> x) {
        Node<E> temp = x;

        while (temp.getLeftChild() != null)
            temp = temp.getLeftChild();

        return temp;
    }

    // find node that replaces a deleted node in BST
    private Node<E> BSTreplace(Node<E> x) {
        // when node have 2 children
        if (x.getLeftChild() != null && x.getRightChild() != null) return successor(x.getRightChild());

        // when leaf
        if (x.getLeftChild() == null && x.getRightChild() == null) return null;

        // when single child
        if (x.getLeftChild() != null) {
            return x.getLeftChild();
        }else {
            return x.getRightChild();
        }
    }

    // deletes the given node
    private void deleteNode(Node<E> v) {
        Node<E> u = BSTreplace(v);

        // True when u and v are both black
        boolean uvBlack = ((u == null || u.getColor() == ColorRB.BLACK) && (v.getColor() == ColorRB.BLACK));
        Node<E> parent = v.getParent();

        if (u == null) {
            // u is NULL therefore v is leaf
            if (v == root) {
                // v is root, making root null
                root = null;
            } else {
                if (uvBlack) {
                    // u and v both black
                    // v is leaf, fix double black at v
                    fixDoubleBlack(v);
                } else {
                    // u or v is red
                    if (v.getSibling() != null)
                        // sibling is not null, make it red"
                        v.getSibling().setColor(ColorRB.RED);
                }

                // delete v from the tree
                if (v.isOnLeft()) {
                    parent.setLeftChild(null);
                } else {
                    parent.setRightChild(null);
                }
            }
            return;
        }

        if (v.getLeftChild() == null || v.getRightChild() == null) {
            // v has 1 child
            if (v == root) {
                // v is root, assign the value of u to v, and delete u
                v.setValue(u.getValue());
                v.setLeftChild(null);
                v.setRightChild(null);
            } else {
                // Detach v from tree and move u up
                if (v.isOnLeft()) {
                    parent.setLeftChild(u);
                } else {
                    parent.setRightChild(u);
                }

                u.setParent(parent);
                if (uvBlack) {
                    // u and v both black, fix double black at u
                    fixDoubleBlack(u);
                } else {
                    // u or v red, color u black
                    u.setColor(ColorRB.BLACK);
                }
            }
            return;
        }

        // v has 2 children, swap values with successor and recurse
        swapValues(u, v);
        deleteNode(u);
    }

    private void fixDoubleBlack(Node<E> x) {
        if (x == root) return;
            // Reached root

        Node<E> sibling = x.getSibling(), parent = x.getParent();
        if (sibling == null) {
            // No sibling, double black pushed up
            fixDoubleBlack(parent);
        } else {
            if (sibling.getColor() == ColorRB.RED) {
                // Sibling red
                parent.setColor(ColorRB.RED);
                sibling.setColor(ColorRB.BLACK);
                if (sibling.isOnLeft()) {
                    // left case
                    rightRotate(parent);
                } else {
                    // right case
                    leftRotate(parent);
                }
                fixDoubleBlack(x);
            } else {
                // Sibling black
                if (sibling.hasRedChild()) {
                    // at least 1 red children
                    if (sibling.getLeftChild() != null && sibling.getLeftChild().getColor() == ColorRB.RED) {
                        if (sibling.isOnLeft()) {
                            // left left
                            sibling.getLeftChild().setColor(sibling.getColor());
                            sibling.setColor(parent.getColor());
                            rightRotate(parent);
                        } else {
                            // right left
                            sibling.getLeftChild().setColor(parent.getColor());
                            rightRotate(sibling);
                            leftRotate(parent);
                        }
                    } else {
                        if (sibling.isOnLeft()) {
                            // left right
                            sibling.getRightChild().setColor(parent.getColor());
                            leftRotate(sibling);
                            rightRotate(parent);
                        } else {
                            // right right
                            sibling.getRightChild().setColor(sibling.getColor());
                            sibling.setColor(parent.getColor());
                            leftRotate(parent);
                        }
                    }
                    parent.setColor(ColorRB.BLACK);
                } else {
                    // 2 black children
                    sibling.setColor(ColorRB.RED);
                    if (parent.getColor() == ColorRB.BLACK)
                        fixDoubleBlack(parent);
                    else
                        parent.setColor(ColorRB.BLACK);
                }
            }
        }
    }

    // prints level order for given node
    private void levelOrder(Node<E> x) {
        if (x == null) return;
            // return if node is null

        Queue<Node<E>> q = new LinkedList<>();
        Node<E> curr;

        // push x
        q.add(x);

        while (!q.isEmpty()) {
            // while q is not empty
            // dequeue
            curr = q.remove();

            // print node value
            System.out.print(curr.getValue() + " ");

            // push children to queue
            if (curr.getLeftChild() != null)
                q.add(curr.getLeftChild());
            if (curr.getRightChild() != null)
                q.add(curr.getRightChild());
        }
    }

    // prints inorder recursively
    private void inorder(Node<E> x) {
        if (x == null)
            return;
        inorder(x.getLeftChild());
        System.out.println(x.getValue() + " ");
        inorder(x.getRightChild());
    }


    // constructor
    // initialize root
    RBT() {
        root = null;
    }

    // searches for given value
// if found returns the node (used for delete)
// else returns the last node while traversing (used in insert)
    private Node<E> search(E n) {
        Node<E> temp = root;
        while (temp != null) {
            if (Integer.parseInt(n.toString()) < Integer.parseInt(temp.getValue().toString())) {
                if (temp.getLeftChild() == null)
                    break;
                else
                    temp = temp.getLeftChild();
            } else if (n == temp.getValue()) {
                break;
            } else {
                if (temp.getRightChild() == null)
                    break;
                else
                    temp = temp.getRightChild();
            }
        }

        return temp;
    }

    // inserts the given value to tree
    void insert(E n) {
        Node<E> newNode = new Node<>(n);
        if (root == null) {
            // when root is null
            // simply insert value at root
            newNode.setColor(ColorRB.BLACK);
            root = newNode;
        } else {
            Node<E> temp = search(n);

            if (temp.getValue() == n) {
                // return if value already exists
                return;
            }

            // if value is not found, search returns the node
            // where the value is to be inserted

            // connect new node to correct node
            newNode.setParent(temp);

            if (Integer.parseInt(n.toString()) < Integer.parseInt(temp.getValue().toString())) {
                temp.setLeftChild(newNode);
            } else {
                temp.setRightChild(newNode);
            }

            // fix red red violation if exists
            fixRedRed(newNode);
        }
    }

    // utility function that deletes the node with given value
    void deleteByVal(E n) {
        if (root == null)
            // Tree is empty
            return;

        Node<E> v = search(n);

        if (v.getValue() != n) {
            System.out.println("No node found to delete with value: " + n);
            return;
        }

        deleteNode(v);
    }

    // prints inorder of the tree
    void printInOrder() {
        System.out.println("Inorder: ");
        if (root == null)
            System.out.println("Tree is empty");
        else
            inorder(root);
        System.out.println();
    }

    // prints level order of the tree
    void printLevelOrder() {
        System.out.println("Level order: ");
        if (root == null)
            System.out.println("Tree is empty");
        else
            levelOrder(root);
        System.out.println();
    }

}
