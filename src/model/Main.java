package model;
public class Main {
	public static void main(String[] args) {
		BinarySearchTree<Integer> ll = new BinarySearchTree<Integer>();
		Integer[] dataNodes = new Integer[] { 50, 40, 60, 35, 45, 30, 36, 37, 46, 60, 55, 65, 51, 56, 64, 66, 63, 70 };
		ll.addAllRecursively(dataNodes);
		ll.print2D();
	}
}
