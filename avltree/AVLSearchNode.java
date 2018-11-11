public class AVLSearchNode<E> {

	//important (visible) values
	private int key;
	private E value;
	
	//relational (invisible) values
	private AVLSearchNode<E> parent;
	private AVLSearchNode<E> leftChild;
	private AVLSearchNode<E> rightChild;
	
	public AVLSearchNode(AVLSearchNode<E> parent) {
		this.parent = parent;
	}
	
	public AVLSearchNode(AVLSearchNode<E> parent, int key, E value) {
		this.parent = parent;
		this.key = key;
		this.value = value;
		leftChild = new AVLSearchNode<E>(this);
		rightChild = new AVLSearchNode<E>(this);
	}
	
	public void initNode(int key, E value) {
		this.key = key;
		this.value = value;
		leftChild = new AVLSearchNode<E>(this);
		rightChild = new AVLSearchNode<E>(this);
	}
	
	public void fosterNode(AVLSearchNode<E> node) {
		this.key = node.key;
		this.value = node.value;
		if (node.parent.leftChild.equals(node)) {
			node.parent.leftChild = new AVLSearchNode<E>(node.parent);
		} else {
			node.parent.rightChild = new AVLSearchNode<E>(node.parent);
		}
	}
	
	public void changeFamily(AVLSearchNode<E> oldParent, AVLSearchNode<E> oldBranch) {
		if (this.leftChild.equals(oldBranch)) {
			
			this.parent = oldParent.parent;
			this.leftChild = oldParent;
			oldParent.rightChild = oldBranch;
			oldParent.parent = this;
			oldBranch.parent = oldParent;
			
		} else {
			
			this.parent = oldParent.parent;
			this.rightChild = oldParent;
			oldParent.leftChild = oldBranch;
			oldParent.parent = this;
			oldBranch.parent = oldParent;
			
		}
		
	}
	
	public boolean isShell() {
		if (value == null) return true;
		return false;
	}
	
	public void changeValue(E value) {
		this.value = value;
	}
	
	public E getValue() {
		return value;
	}
	
	public int getKey() {
		return key;
	}
	
	public AVLSearchNode<E> getParent() {
		return parent;
	}
	
	public AVLSearchNode<E> getLeftChild() {
		return leftChild;
	}
	
	public AVLSearchNode<E> getRightChild() {
		return rightChild;
	}
	
}