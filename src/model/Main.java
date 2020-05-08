package model;

import model.DoublyLinkedList.Node;

public class Main {
	public static void main(String[] args) {
		DoublyLinkedList<Integer> ll = new DoublyLinkedList<Integer>();
		Integer[] dataNodes = new Integer[] { 50, 40, 60, 35, 45, 30, 36, 37, 46, 60, 55, 65, 51, 56, 64, 66, 63, 70 };
		for(Integer data : dataNodes)
			ll.addIteratively(data);
		for(Node<Integer> node : ll)
			System.out.println(node.getData());
	}
}
