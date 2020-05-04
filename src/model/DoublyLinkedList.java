package model;


public class DoublyLinkedList<T> {
	@SuppressWarnings("hiding")
	static class Node<T>{
		Node<T> pointsForwardTo;
		Node<T> pointsBackwardsTo;
		T data;
		public Node(T d){
			pointsBackwardsTo = null;
			pointsForwardTo = null;
			this.data = d;
		}
		public String toString() {
			return data.toString();
		}
	}
	
	public Node<T> first;
	public Node<T> last;
	public int length = 0;
	
	public void prepend(T d) {
		Node<T> newNode = new Node<T>(d);
		if(first == null) {
			newNode.pointsForwardTo = last;
			last = newNode;
			last.pointsBackwardsTo = newNode;
		} else {
			newNode.pointsForwardTo = first;
			first.pointsBackwardsTo = newNode;
		}
		first = newNode;
		length++;
	}
	
	public void prependByRef(Node<T> newNode) {
		if(first == null) {
			newNode.pointsForwardTo = last;
			last = newNode;
			last.pointsBackwardsTo = newNode;
		} else {
			newNode.pointsForwardTo = first;
			first.pointsBackwardsTo = newNode;
		}
		first = newNode;
		length++;
	}
		
	public void append(T d) {
		Node<T> newNode = new Node<T>(d);
		if(first == null) {
			newNode.pointsBackwardsTo = first;
			first = newNode;
			first.pointsForwardTo = last;
		}else {
			newNode.pointsBackwardsTo = last;
			last.pointsForwardTo = newNode;
		}
		last = newNode;
		length++;
	}
	
	public void appendByRef(Node<T> newNode) {
		if(first == null) {
			newNode.pointsBackwardsTo = first;
			first = newNode;
			first.pointsForwardTo = last;
		}else {
			newNode.pointsBackwardsTo = last;
			last.pointsForwardTo = newNode;
		}
		last = newNode;
		length++;
	}
	
	public void addAfter(T data, Node<T> node) {
		Node<T> next = node.pointsForwardTo;
		if(next == null) append( data );
		else {
			Node<T> newNode = new Node<T>(data);
			newNode.pointsBackwardsTo = node;
			newNode.pointsForwardTo = next;
			next.pointsBackwardsTo = newNode;
			node.pointsForwardTo = newNode;
			length++;
		}
	}
	
	public Node<T> deleteByRef(Node<T> node){
		// Base case 
        if (first == null || node == null) { 
            return null; 
        } 
  
        // If node to be deleted is head node 
        if (first == node) { 
            first = node.pointsForwardTo; 
        } 
  
        // Change next only if node to be deleted 
        // is NOT the last node 
        if (node.pointsForwardTo != null) { 
            node.pointsForwardTo.pointsBackwardsTo = node.pointsBackwardsTo; 
        } 
  
        // Change prev only if node to be deleted 
        // is NOT the first node 
        if (node.pointsBackwardsTo != null) { 
            node.pointsBackwardsTo.pointsForwardTo = node.pointsForwardTo; 
        }
        return node;
	}
	
	public boolean isEmpty() {
		return length == 0;
	}
	
	public void addBefore(T data, Node<T> another) {
		Node<T> prev = another.pointsBackwardsTo;
		if(prev == null) {
			prepend(data);
		}else {
			Node<T> newNode = new Node<T>(data);
			newNode.pointsBackwardsTo = prev;
			prev.pointsForwardTo = newNode;
			newNode.pointsForwardTo = another;
			another.pointsBackwardsTo = newNode;
		}
	}
	
	public String toString() {
		String res = "";
		Node<T> curr = first;
		do {
			res += "<-"+curr.data +"->";
			curr = curr.pointsForwardTo;
		}while(curr != null);
		return res;
	}
}