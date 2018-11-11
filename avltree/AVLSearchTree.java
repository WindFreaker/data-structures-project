public class AVLSearchTree<E> {

	private AVLSearchNode<E> root;
	
	public AVLSearchTree() {
		root = new AVLSearchNode<E>(null);
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public int size() {
		return size(root);
	}
	
	private int size(AVLSearchNode<E> node) { //public use not needed but possible
		if (node.isShell()) return 0;
		return 1 + size(node.getLeftChild()) + size(node.getRightChild());
	}
	
	public int height() {
		return height(root);
	}
	
	private int height(AVLSearchNode<E> node) { //public use not needed but possible
		if (node.isShell()) return 0;
		int leftH = height(node.getLeftChild());
		int rightH = height(node.getRightChild());
		return 1 + (leftH > rightH ? leftH : rightH);
	}
	
	public AVLSearchNode<E> min() {
		return min(root);
	}
	
	private AVLSearchNode<E> min(AVLSearchNode<E> node) { //public use not needed but possible
		if (node.isShell()) return node.getParent();
		return min(node.getLeftChild());
	}
	
	public AVLSearchNode<E> max() {
		return max(root);
	}
	
	private AVLSearchNode<E> max(AVLSearchNode<E> node) { //public use not needed but possible
		if (node.isShell()) return node.getParent();
		return max(node.getRightChild());
	}
	
	public AVLSearchNode<E> search(int key) {
		return search(root, key);
	}
	
	private AVLSearchNode<E> search(AVLSearchNode<E> node, int key) { //public use not needed but possible
		if (node.isShell()) return null;
		if (node.getKey() == key) return node;
		return node.getKey() > key ? search(node.getLeftChild(), key) : search(node.getRightChild(), key);
	}
	
	public E insert(int key, E value) {
		E output = insert(root, key, value);
		return output;
	}
	
	private E insert(AVLSearchNode<E> testNode, int key, E value) {
		
		if (testNode.isShell()) {
			
			testNode.initNode(key, value);
			fixHeight(testNode);
			return null;
			
		} else if (testNode.getKey() > key) {
			return insert(testNode.getLeftChild(), key, value);
		} else if (testNode.getKey() == key) {
			
			E output = testNode.getValue();
			testNode.changeValue(value);
			return output;
			
		} else {
			return insert(testNode.getRightChild(), key, value);
		}
		
	}
	
	public E delete(int key) {
		
		AVLSearchNode<E> node = search(key);
		if (node == null) return null; //when you try to delete a node that doesn't exist
		
		E output = node.getValue(); //method outputs the value of the deleted node
		
		AVLSearchNode<E> replacement;
		if (size(node.getRightChild()) > size(node.getLeftChild())) {
			replacement = min(node.getRightChild());
		} else {
			replacement = max(node.getLeftChild());
		}
		
		node.fosterNode(replacement);
		fixHeight(node);
		return output;
		
	}
	
	public AVLSearchTree<E> preOrder() {
		System.out.print("PreOrder: ");
		preOrder(root);
		System.out.println();
		return this;
	}
	
	public AVLSearchTree<E> inOrder() {
		System.out.print("InOrder: ");
		inOrder(root);
		System.out.println();
		return this;
	}
	
	public AVLSearchTree<E> postOrder() {
		System.out.print("PostOrder: ");
		postOrder(root);
		System.out.println();
		return this;
	}
	
	private void preOrder(AVLSearchNode<E> node) {
		if (node.isShell()) return;
		
		System.out.print(node.getKey() + " ");
		inOrder(node.getLeftChild());
		inOrder(node.getRightChild());
		
	}
	
	private void inOrder(AVLSearchNode<E> node) {
		if (node.isShell()) return;
		
		inOrder(node.getLeftChild());
		System.out.print(node.getKey() + " ");
		inOrder(node.getRightChild());
		
	}
	
	private void postOrder(AVLSearchNode<E> node) {
		if (node.isShell()) return;
		
		inOrder(node.getLeftChild());
		inOrder(node.getRightChild());
		System.out.print(node.getKey() + " ");
		
	}
	
	private void fixHeight(AVLSearchNode<E> node) {
		
		if (node == null) return; //stop once you pass the root
		
		int difference = height(node.getLeftChild()) - height(node.getRightChild());
		
		if (difference > 1) {
			node = node.getLeftChild();
			if (node.getParent().equals(root)) root = node;
			node.changeFamily(node.getParent(), node.getRightChild());
		} else if (difference < -1) {
			node = node.getRightChild();
			if (node.getParent().equals(root)) root = node;
			node.changeFamily(node.getParent(), node.getLeftChild());
		}
		
		fixHeight(node.getParent());
		
	}
	
}