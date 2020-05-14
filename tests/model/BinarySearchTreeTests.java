package model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import com.model.BinarySearchTree;

/**
 * @author Sebastián García Acosta
 *
 */
public class BinarySearchTreeTests {

	private BinarySearchTree<Integer> bst;

//	Setups
	public void setupEmptyTree() {
		this.bst = new BinarySearchTree<Integer>();
	}
	
//	Setup 1 tree will look like this::
//	
//					50
//			40				  60
//		35		45		55			65
//	  30  36	  46  51  56  	  64  66
//			37					63		70
	public void setupDeletion() {
		setupEmptyTree();
		Integer[] data = new Integer[] { 50, 40, 60, 35, 45, 30, 36, 37, 46, 60, 55, 65, 51, 56, 64, 66, 63, 70 };
		bst.addAllIteratively(data);
	}

// Iterative versions
	@Test
	public void addRootIterativelyTest() {
		setupEmptyTree();
		int data = 3;
		bst.add(data);
		assertTrue("Root is not added correctly", bst.getRootData() == data);
	}

	@Test
	public void addIterativelyTest() {
		setupEmptyTree();
		Integer[] data = new Integer[] { 1, 4, 53, 3, 2 };
		Integer[] expectedData = new Integer[] { 1, 2, 3, 4, 53 };
		bst.addAllIteratively(data);
		ArrayList<Integer> actualDataAdded = (ArrayList<Integer>) bst.getNodes();
		assertTrue("Size is not equal", actualDataAdded.size() == expectedData.length);
		for(int i = 0; i < expectedData.length; i++) 
			assertTrue("This should add " + expectedData[i] + " instead of " + actualDataAdded.get(i), expectedData[i] == actualDataAdded.get(i));
	}

	@Test
	public void addNonDuplicateIterativelyTest() {
		setupEmptyTree();
		Integer[] data = new Integer[] { 12, 1, 3, 3, 3, 3, 3, 3, 3, 3 };
		Integer[] expectedAddedNodes = new Integer[] { 1, 3, 12 }; // Ordered because getNodes returns a stack with
																	// nodes in-order.
		bst.addAllIteratively(data);
		int expetectedSize = 3;
		ArrayList<Integer> nodesAdded = bst.getNodes();
		assertTrue(
				"The number of elements added in BST should be 3, since that is the number of unique elements passed",
				bst.getNodes().size() == expetectedSize);
		for (int i = 0; i < expectedAddedNodes.length; i++) {
			assertTrue(nodesAdded.get(i) == expectedAddedNodes[i]);
		}
	}
	
//	The following methods will test the delete method, the data that will contain the Binary Search Tree will consider the four cases of
//	deletion (deleting a leaf node, deleting a node with one child, and deleting a node with 2 children)

	@Test
	public void deleteRootIterativelyTest() {
		setupEmptyTree();
		setupDeletion();
		bst.deleteIteratively(50); // roots
		assertTrue(bst.searchNodeIteratively(50) == null);
		int expectedRoot = 46;
		int actualRoot = bst.getRootData();
		assertTrue("The root should be "  + expectedRoot + " not " + actualRoot, actualRoot == expectedRoot); // Check that the root is the in order successor, which is the maximum of
												// the left subtree-
	}

	@Test
	public void deleteLeafNodeIterativelyTest() {
		setupDeletion();
		Integer[] nodesToBeDeleted = new Integer[] { 30, 63, 70 };
		for (Integer e : nodesToBeDeleted) {
			bst.deleteIteratively(e);
			if (bst.searchNodeIteratively(e) != null)
				fail(e + " should not be found");
		}
	}

	@Test
	public void deleteNodeWithOneChildTIterativelyest() {
		setupDeletion();
		Integer[] nodesToBeDeleted = new Integer[] { 36, 45, 66 };
//		Check that have been deleted
		for (Integer e : nodesToBeDeleted) {
			bst.deleteIteratively(e);
			if (bst.searchNodeIteratively(e) != null)
				fail(e + " should not be found");
		}
	}

	@Test
	public void deleteNodeWithTwoChildrenIterativelyTest1() {
		setupDeletion();
		Integer[] nodesToBeDeleted = new Integer[] { 40, 55, 35 };
//		Check that have been deleted
		for (Integer e : nodesToBeDeleted) {
			bst.deleteIteratively(e);
			if (bst.searchNodeIteratively(e) != null)
				fail(e + " should not be found");
		}
	}
	
/*													RECURSIVE TESTS 											*/
	@Test
	public void addRootRecursiveTest() {
		setupEmptyTree();
		int data = 3;
		bst.addRecursively(data);
		assertTrue("Root is not added correctly", bst.getRootData() == data);
	}

	@Test
	public void addRecursiveTest() {
		setupEmptyTree();
		Integer[] data = new Integer[] { 1, 4, 53, 3, 2 };
		Integer[] expectedData = new Integer[] { 1, 2, 3, 4, 53 };
		bst.addAllRecursively(data);
		ArrayList<Integer> actualDataAdded = (ArrayList<Integer>) bst.getNodes();
		assertTrue("Size is not equal", actualDataAdded.size() == expectedData.length);
		for(int i = 0; i < expectedData.length; i++) 
			assertTrue("This should add " + expectedData[i] + " instead of " + actualDataAdded.get(i), expectedData[i] == actualDataAdded.get(i));
	}

	@Test
	public void addNonDuplicateRecursiveTest() {
		setupEmptyTree();
		Integer[] data = new Integer[] { 12, 1, 3, 3, 3, 3, 3, 3, 3, 3 };
		Integer[] expectedAddedNodes = new Integer[] { 1, 3, 12 }; // Ordered because getNodes returns a stack with
		// nodes in-order.
		bst.addAllRecursively(data);
		int expetectedSize = 3;
		ArrayList<Integer> nodesAdded = bst.getNodes();
		assertTrue(
				"The number of elements added in BST should be 3, since that is the number of unique elements passed",
				bst.getNodes().size() == expetectedSize);
		for (int i = 0; i < expectedAddedNodes.length; i++) {
			assertTrue(nodesAdded.get(i) == expectedAddedNodes[i]);
		}
	}
	
//	The following methods will test the delete method, the data that will contain the Binary Search Tree will consider the four cases of
//	deletion (deleting a leaf node, deleting a node with one child, and deleting a node with 2 children)

	@Test
	public void deleteRootRecursiveTest() {
		setupEmptyTree();
		setupDeletion();
		bst.deleteRecursively(50); // roots
		assertTrue(bst.searchNodeIteratively(50) == null);
		int expectedRoot = 46;
		int actualRoot = bst.getRootData();
		assertTrue("The root should be "  + expectedRoot + " not " + actualRoot, actualRoot == expectedRoot); // Check that the root is the in order successor, which is the maximum of
												// the left subtree-
	}

	@Test
	public void deleteLeafNodeRecursiveTest() {
		setupDeletion();
		Integer[] nodesToBeDeleted = new Integer[] { 30, 63, 70 };
		for (Integer e : nodesToBeDeleted) {
			bst.deleteRecursively(e);
			if (bst.searchRecursively(e) != null)
				fail(e + " should not be found");
		}
	}

	@Test
	public void deleteNodeWithOneChildRecursiveTest() {
		setupDeletion();
		Integer[] nodesToBeDeleted = new Integer[] { 36, 45, 66 };
//		Check that have been deleted
		for (Integer e : nodesToBeDeleted) {
			bst.deleteRecursively(e);
			if (bst.searchRecursively(e) != null)
				fail(e + " should not be found");
		}
	}

	@Test
	public void deleteNodeWithTwoChildrenRecursiveTest1() {
		setupDeletion();
		Integer[] nodesToBeDeleted = new Integer[] { 40, 55, 35 };
//		Check that have been deleted
		for (Integer e : nodesToBeDeleted) {
			bst.deleteRecursively(e);
			if (bst.searchRecursively(e) != null)
				fail(e + " should not be found");
		}
	}
}