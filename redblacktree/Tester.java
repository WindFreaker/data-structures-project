package redblacktree;

public class Tester {

    public static void main(String[] args) {

        RBT<Integer> tree = new RBT<>();

        tree.insert(7);
        tree.insert(3);
        tree.insert(18);
        tree.insert(10);
        tree.insert(22);
        tree.insert(8);
        tree.insert(11);
        tree.insert(26);
        tree.insert(2);
        tree.insert(6);
        tree.insert(13);

        tree.printInOrder();
        tree.printLevelOrder();

        System.out.println();
        System.out.println("Deleting 18, 11, 3, 10, 22");

        tree.deleteByVal(18);
        tree.deleteByVal(11);
        tree.deleteByVal(3);
        tree.deleteByVal(10);
        tree.deleteByVal(22);

        tree.printInOrder();
        tree.printLevelOrder();

    }

}
