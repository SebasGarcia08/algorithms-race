package com.model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T>{
	/**
	 * The wild-card Node<T extends Comparable<? super T>> 
	 *  allows T to be a type that is a sub-type of some type that implements Comparable 
	 * @param <T> 
	 */
	@SuppressWarnings("hiding")
	protected class BSTNode<T extends Comparable<? super T>> {
		private BSTNode<T> parent;
		private BSTNode<T> left;
		private BSTNode<T> right;
		private T data;

		public BSTNode(T data) {
			this.data = data;
			this.left = null;
			this.right = null;
			this.parent = null;
		}

		public boolean isLeafNode() {
			return (right == null && left == null);
		}

		public boolean hasTwoChildren() {
			return (right != null && left != null);
		}

		public boolean hasOnlyOneChild() {
			return hasOnlyLeftChild() || hasOnlyRightChild();
		}

		public boolean hasOnlyRightChild() {
			return (right != null && left == null);
		}

		public boolean hasOnlyLeftChild() {
			return (right == null && left != null);
		}

		public boolean isRightNode() {
			return parent != null && parent.right == this;
		}

		public boolean isLeftNode() {
			return parent != null && parent.left == this;
		}

		public BSTNode<T> getMaximumNode() {
			BSTNode<T> curr = this;
			while (curr.right != null) {
				curr = curr.right;
			}
			return curr;
		}

		public BSTNode<T> getMinimumNode() {
			BSTNode<T> curr = this;
			while (curr.left != null) {
				curr = curr.left;
			}
			return curr;
		}

		/**
		 * Deletes itself. <b> pre: </b> This is not the root.
		 * 
		 * @return Node<T> {@link BSTNode} this, the deleted node.
		 */
		public BSTNode<T> delete() {
			if (isLeafNode()) {
				if (this.isLeftNode())
					parent.left = null;
				else
					parent.right = null;
			} else if (hasOnlyOneChild()) {
				BSTNode<T> child = (hasOnlyLeftChild()) ? this.left : this.right;
				if (this.isLeftNode())
					this.parent.left = child;
				else
					this.parent.right = child;
				child.parent = this.parent;
			} else {
				BSTNode<T> inOrderSuccesor = this.left.getMaximumNode().delete();
				this.data = inOrderSuccesor.data;
			}
			return this;
		}

		public boolean isLessThan(BSTNode<T> another) {
			return this.data.compareTo(another.data) < 0;
		}

		public boolean isGreaterThan(BSTNode<T> another) {
			return this.data.compareTo(another.data) > 0;
		}

		public boolean dataIsEqualTo(BSTNode<T> another) {
			return this.data.compareTo(another.data) == 0;
		}

		@Override
		public String toString() {
			return this.data.toString();
		}
	}

	private BSTNode<T> root;
	private int numberOfElements;

	public BinarySearchTree() {
		this.root = null;
		this.numberOfElements = 0;
	}
	
	/**
	 * Delete an element from this tree.
	 * 
	 * @param target
	 *            The object to be deleted
	 */
	public void deleteRecursively(T value) {
		deleteRecursively(root, value);
		numberOfElements--;
	}
	
	private BSTNode<T> deleteRecursively(BSTNode<T> currentRoot, T value) {
		if(currentRoot != null) {
			if( value.compareTo(currentRoot.data) > 0) { // If value to delete is greater than current root 
				return deleteRecursively(currentRoot.right, value);
			} else if ( value.compareTo(currentRoot.data) < 0){
				return deleteRecursively(currentRoot.left, value);
			} else {
				if(currentRoot.hasTwoChildren()) {
					BSTNode<T> inOrderSuccesor = getMaximumRecursively(currentRoot.left);
					currentRoot.data = deleteRecursively(currentRoot, inOrderSuccesor.data).data;
				}else if(currentRoot.hasOnlyOneChild()) {
					BSTNode<T> child = (currentRoot.hasOnlyLeftChild()) ? currentRoot.left : currentRoot.right;
					if(currentRoot.isLeftNode()) 
						currentRoot.parent.left = child; 
					else
						currentRoot.parent.right = child; 
				} else {
					if(currentRoot.isLeftNode())
						currentRoot.parent.left = null;
					else
						currentRoot.parent.right = null;
				}
				return currentRoot;
			}			
		}
		return currentRoot;
	}

	public void add(T data) {
		addIteratively(data);
	}

	public void addAllIteratively(Collection<T> c) {
		for (T element : c)
			addIteratively(element);
	}

	public void addAllIteratively(T[] c) {
		for (T element : c)
			addIteratively(element);
	}

	public void addAllRecursively(Collection<T> c) {
		for (T element : c)
			addRecursively(element);
	}

	public void addAllRecursively(T[] c) {
		for (T element : c)
			addRecursively(element);
	}
		
	public void print2DUtil(BSTNode<T> root, int space)  {
		int COUNT = 5;
	    // Base case  
	    if (root == null)  
	        return;  
	  
	    // Increase distance between levels  
	    space += COUNT;  
	  
	    // Process right child first  
	    print2DUtil(root.right, space);  
	  
	    // Print current node after space  
	    // count  
	    for (int i = COUNT; i < space; i++)  
	        System.out.print(" ");  
	    System.out.print(root.data + "\n");  
	  
	    // Process left child  
	    print2DUtil(root.left, space);  
	}  
	  
	// Wrapper over print2DUtil()  
	public void print2D()  {  
	    // Pass initial space count as 0  
	    print2DUtil(root, 0);  
	}  

	public void addRecursively(T data) {
		BSTNode<T> newNode = new BSTNode<T>(data);
		if (root == null)
			root = newNode;
		else
			addRecursively(newNode, root);
		numberOfElements++;
	}

	/**
	 * @param newNode
	 * 
	 * @param currentNode
	 */
	private void addRecursively(BSTNode<T> newNode, BSTNode<T> currentNode) {
		if (newNode.isLessThan(currentNode)) {
			if (currentNode.left == null) {
				currentNode.left = newNode;
				newNode.parent = currentNode;
			} else {
				addRecursively(newNode, currentNode.left);
			}
		} else if (newNode.isGreaterThan(currentNode)) {
			if (currentNode.right == null) {
				currentNode.right = newNode;
				newNode.parent = currentNode;
			} else {
				addRecursively(newNode, currentNode.right);
			}
		} else {
			; // Nothing if found some duplicate node
		}
	}

	public BSTNode<T> searchIteratively(T data) {
		BSTNode<T> nodeFound = root;
		boolean found = false;
		if (root != null) {
			while (!found && nodeFound != null) {
				if (data.compareTo(nodeFound.data) < 0)
					nodeFound = nodeFound.left;
				else if (data.compareTo(nodeFound.data) > 0)
					nodeFound = nodeFound.right;
				else
					found = true;
			}
		}
		return nodeFound;
	}

	public BSTNode<T> searchRecursively(T data) {
		if (root == null)
			return null;
		else
			return searchRecursively(root, data);
	}

	private BSTNode<T> searchRecursively(BSTNode<T> current, T data) {
		if (current == null) {
			return null;
		} else if (data.compareTo(current.data) == 0) {
			return current;
		} else if (data.compareTo(current.data) < 0) {
			return searchRecursively(current.left, data);
		} else {
			return searchRecursively(current.right, data);
		}
	}

	/**
	 * 
	 * @param data
	 */
	public void addIteratively(T data) {
		BSTNode<T> newNode = new BSTNode<T>(data);
		if (root == null) {
			root = newNode;
		} else {
			BSTNode<T> currentNode = root;
			boolean added = false;
			boolean duplicate = false;
			while (!added && !duplicate) {
				if (newNode.isLessThan(currentNode)) {
					if (currentNode.left == null) {
						newNode.parent = currentNode;
						currentNode.left = newNode;
					} else {
						currentNode = currentNode.left;
					}
				} else if (newNode.isGreaterThan(currentNode)) {
					if (currentNode.right == null) {
						newNode.parent = currentNode;
						currentNode.right = newNode;
					} else {
						currentNode = currentNode.right;
					}
				} else {
					duplicate = true;
				}
			}
		}
		numberOfElements++;
	}

	public BSTNode<T> getMaximumIteratively(BSTNode<T> localRoot) {
		BSTNode<T> currentNode = localRoot;
		while (currentNode.right != null)
			currentNode = currentNode.right;
		return currentNode;
	}
	
	public BSTNode<T> getMaximumRecursively(BSTNode<T> localRoot) {
		BSTNode<T> maximum = localRoot;
		if (maximum.right != null)
			return getMaximumRecursively(maximum.right);
		else
			return maximum;
	}

	public BSTNode<T> getMinimumIteratively(BSTNode<T> localRoot) {
		BSTNode<T> currentNode = localRoot;
		while (currentNode.left != null)
			currentNode = currentNode.left;
		return currentNode;
	}

	public BSTNode<T> getMinimumRecursively(BSTNode<T> localRoot) {
		BSTNode<T> minimum = localRoot;
		if (minimum.right != null)
			return getMinimumRecursively(minimum.right);
		else
			return minimum;
	}

	public void deleteIteratively(T data) {
		if (root != null) {
			if (data.compareTo(root.data) == 0) {
				if (root.isLeafNode())
					root = null;
				else if (root.hasOnlyOneChild())
					root = (root.hasOnlyLeftChild()) ? root.left : root.right;
				else
					root.data = root.left.getMaximumNode().delete().data;
			} else {
				BSTNode<T> nodeFound = searchIteratively(data);
				if (nodeFound != null)
					nodeFound.delete();
			}
		}
		numberOfElements--;
	}

	public T getRootData() {
		return this.root.data;
	}
	
	public int NumberOfElements() {
		return numberOfElements;
	}
	
	public ArrayList<T> getNodes(){
		ArrayList<T> nodes = new ArrayList<T>();
		for(T nodeData : this) 
			nodes.add(nodeData);	
		return nodes;
	}
	
	@Override
	public Iterator<T> iterator(){
		return new InorderIterator();
	}
	
	private class InorderIterator implements Iterator<T> {
		/** The nodes that are still to be visited. */
		private Stack<BSTNode<T>> stack;

		/** Construct. */
		private InorderIterator() {
			stack = new Stack<BSTNode<T>>();
			pushPathToMin(root);
		}

		/**
		 * Push all the nodes in the path from a given node to the leftmost node
		 * in the subtree.
		 */
		private void pushPathToMin (BSTNode<T> localRoot) {
			BSTNode<T> current = localRoot;
			while(current != null) {
				stack.push(current);
				current = current.left;
			}
		}
		
		/** Is there another element in this iterator? */
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		/**
		 * The next element in this iterator; Advance the iterator.
		 */
		public T next() {
			BSTNode<T> node = stack.peek();
			stack.pop();
			pushPathToMin(node.right);
			return node.data;
		}

		/** (Don't) remove an element from this iterator. */
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
