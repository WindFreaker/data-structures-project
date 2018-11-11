public class TestClass {

	public static void main(String[] args) {
		
		AVLSearchTree<String> testTree = new AVLSearchTree<String>();
		
		System.out.println(testTree.isEmpty());
		
		testTree.insert(6, "This is the root node");
		
		System.out.println(testTree.isEmpty());
		
		testTree.insert(2, "This is the first node on the left of the root.");
		System.out.println();
		testTree.preOrder().inOrder().postOrder();
		System.out.println();
		testTree.insert(1, "This is the minimum node!");
		testTree.preOrder().inOrder().postOrder();
		System.out.println();
		testTree.insert(4, "This is just another node.");
		testTree.preOrder().inOrder().postOrder();
		System.out.println();
		testTree.insert(9, "This is both the first node on the right and the maximum node.");
		testTree.preOrder().inOrder().postOrder();
		System.out.println();
		testTree.insert(8, "Is this a node?");
		testTree.preOrder().inOrder().postOrder();
		System.out.println();
		
		System.out.println(testTree.min().getValue());
		System.out.println(testTree.max().getValue());
		
		System.out.println(testTree.search(4).getValue());
		System.out.println(testTree.search(2).getValue());
		
		System.out.println(testTree.size());
		System.out.println(testTree.height());
		
		System.out.println(testTree.delete(9));
		
		System.out.println(testTree.size());
		System.out.println(testTree.max().getValue());
		
	}
	
}