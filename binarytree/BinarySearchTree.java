

public class BinarySearchTree {
	  Node root; 

    class Node { 
        int key; 
        Node left, right; 
  
        public Node(int item) { 
            key = item; 
            left = right = null; 
        } 
    } 
  
   
    BinarySearchTree() {  
        root = null;  
    } 
  
    // Method to insert key into the tree
    void insert(int key) { 
       root = insertRec(root, key); 
    } 
      
    Node insertRec(Node root, int key) { 
  
        
        if (root == null) { 
            root = new Node(key); 
            return root; 
        } 
  
       
        if (key < root.key) 
            root.left = insertRec(root.left, key); 
        else if (key > root.key) 
            root.right = insertRec(root.right, key); 
  
        
        return root; 
    } 
  
    // method to print out the tree 
    void inorder()  { 
       inorderRec(root); 
    } 
  
    
    void inorderRec(Node root) { 
        if (root != null) { 
            inorderRec(root.left); 
            System.out.println(root.key); 
            inorderRec(root.right); 
        } 
    }
    
    //search method
    
    void search(int key) { 
        root = searchRec(root, key); 
     }
    
    Node searchRec(Node root,int key) {
    	 if (root == null) { 
             
             System.out.println("the key does not exist in the tree"); 
         } 
    	 else if (key < root.key) 
              root.left = searchRec(root.left, key); 
    	 else  if (key > root.key) 
              root.right = searchRec(root.right, key);
          else if (key == root.key ) {
        	  System.out.println("the key is in the tree");
        	  return root;
        	  
          }
         
		return root;
    }
    
    void delete(int key) { 
        root = deleteRec(root, key); 
     }
    
    Node deleteRec(Node root, int key) 
    { 
        //empty tree:
        if (root == null)  return root; 
  
        
        if (key < root.key) 
            root.left = deleteRec(root.left, key); 
        else if (key > root.key) 
            root.right = deleteRec(root.right, key); 
        else
        { 
           
            if (root.left == null) 
                return root.right; 
            else if (root.right == null) 
                return root.left; 
  
          //two children for the key to be deleted: 
            root.key = minValue(root.right); 
  
           
            root.right = deleteRec(root.right, root.key); 
        } 
  
        return root; 
    } 
  
    int minValue(Node root) 
    { 
        int minv = root.key; 
        while (root.left != null) 
        { 
            minv = root.left.key; 
            root = root.left; 
        } 
        return minv; 
    } 
    

	public static void main(String[] args) {
		 BinarySearchTree tree = new BinarySearchTree(); 
		 
		 	tree.insert(90); 
	        tree.insert(40); 
	        tree.insert(70); 
	        tree.insert(60); 
	        tree.insert(10); 
	        tree.insert(30); 
	        tree.insert(80); 
	  
	        // print inorder traversal of the Search Tree 
	        tree.inorder(); 
	        
	        tree.search(90);
	        tree.search(20);
	        
	        tree.delete(30);
	        tree.inorder();
		// TODO Auto-generated method stub

	}


}





