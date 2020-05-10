package com.model;

import java.util.Random;

public class Main {
	public static void main(String[] args) {
		try {
			BinarySearchTree<Long> ll = new BinarySearchTree<Long>();
			Long[] dataNodes = new Long[] { 50L, 40L, 60L, 35L, 45L, 30L, 36L, 37L, 46L, 60L, 55L, 65L, 51L, 56L, 64L, 66L, 63L, 70L };
			for(long data : dataNodes)
				ll.addIteratively(data);
			Random r = new Random();
			ll.deleteRecursively(50L);
			ll.add(r.nextLong());
			for(long node : ll)
				System.out.println(node);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
