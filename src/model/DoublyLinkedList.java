package model;

import java.util.ArrayList;
import java.util.Iterator;

import model.DoublyLinkedList.Node;

public class DoublyLinkedList<T> implements Iterable<Node<T>> {

	protected static class Node<T> {
		private T data;
		private Node<T> next;
		private Node<T> prev;

		public Node(T data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}

		/**
		 * @return the data
		 */
		public T getData() {
			return data;
		}

		/**
		 * @return the next
		 */
		public Node<T> getNext() {
			return next;
		}

		/**
		 * @return the prev
		 */
		public Node<T> getPrev() {
			return prev;
		}

		/**
		 * @param data the data to set
		 */
		public void setData(T data) {
			this.data = data;
		}

		/**
		 * @param next the next to set
		 */
		public void setNext(Node<T> next) {
			this.next = next;
		}

		/**
		 * @param prev the prev to set
		 */
		public void setPrev(Node<T> prev) {
			this.prev = prev;
		}
	}

	private Node<T> first;
	private int size;

	public DoublyLinkedList() {
		this.first = null;
		this.size = 0;
	}

	public void addIteratively(T data) {
		Node<T> newNode = new Node<T>(data);
		if (first == null) {
			first = newNode;
		} else {
			Node<T> lastNode = first;
			while (lastNode.next != null)
				lastNode = lastNode.next;
			newNode.prev = lastNode;
			lastNode.next = newNode;
		}
		size++;
	}

	public void addRecursively(T data) {
		Node<T> newNode = new Node<T>(data);
		if (first == null)
			first = newNode;
		else
			addRecursively(first, newNode);
		size++;
	}

	private void addRecursively(Node<T> currentNode, Node<T> newNode) {
		if (currentNode.next != null) {
			addRecursively(currentNode.next, newNode);
		} else {
			newNode.prev = currentNode;
			currentNode.next = newNode;
		}
	}

	public void deleteIteratively(T data) {
		for (Node<T> currentNode : this)
			if (currentNode.data.equals(data))
				currentNode.prev.next = currentNode.next;
		size--;
	}

	public void deleteRecursively(T data) {
		if (first != null)
			deleteRecursively(first, data);
		size--;
	}

	private void deleteRecursively(Node<T> currentNode, T data) {
		if(currentNode != null) {
			if (currentNode.data.equals(data)) {
				currentNode.prev.next = currentNode.next;
			} else
				deleteRecursively(currentNode.next, data);			
		}
	}

	public T searchIteratively(T data) {
		for (Node<T> currentNode : this)
			if (currentNode.data.equals(data))
				return currentNode.data;
		return null;
	}

	public T searchRecursively(T data) {
		T dataFound = null;
		if (first != null)
			dataFound = searchRecursively(first, data);
		return dataFound;
	}

	private T searchRecursively(Node<T> currentNode, T data) {
		if(currentNode != null) {
			if (currentNode.data.equals(data))
				return currentNode.data;
			else
				return searchRecursively(currentNode.next, data);			
		}else {
			return null;
		}
	}
	
	/**
	 * @return the first
	 */
	public T getFirstData() {
		return first.data;
	}

	public Node<T> getFirst() {
		return this.first;
	}
	
	public ArrayList<T> getAllData() {
		ArrayList<T> data = new ArrayList<T>();
		for(Node<T> node : this)
			data.add(node.data);
		return data;
 	}

	@Override
	public Iterator<Node<T>> iterator() {
		return new DoublyLinkedListIterator();
	}

	private class DoublyLinkedListIterator implements Iterator<Node<T>> {

		private Node<T> currentNode;

		public DoublyLinkedListIterator() {
			this.currentNode = first;
		}

		@Override
		public boolean hasNext() {
			return currentNode != null;
		}

		@Override
		public Node<T> next() {
			Node<T> next = currentNode;
			currentNode = currentNode.next;
			return next;
		}
	}
}