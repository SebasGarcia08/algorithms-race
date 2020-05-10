package model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.model.DoublyLinkedList;

public class DoublyLinkedListTests {

	private DoublyLinkedList<Integer> dll;

	public void emptyCase() {
		this.dll = new DoublyLinkedList<Integer>();
	}

	public void nonEmptyCase() {
		emptyCase();
		Integer[] data = new Integer[] { 4, 666, 1879, 1912, 1643 };
		for (Integer number : data)
			dll.addIteratively(number);
	}

	@Test
	public void addIterativelyEmptyCaseTest() {
		emptyCase();
		dll.addIteratively(666);
		assertTrue("Not adding the first element right", dll.getFirstData() == 666);
	}

	@Test
	public void addRecursivelyEmptyCaseTest() {
		emptyCase();
		dll.addRecursively(666);
		assertTrue("Not adding the first element right", dll.getFirstData() == 666);
	}

	@Test
	public void addIterativelyNonEmptyCaseTest() {
		nonEmptyCase();
		dll.addIteratively(1);
		Integer[] expectedData = new Integer[] { 4, 666, 1879, 1912, 1643, 1 };
		ArrayList<Integer> actualData = dll.getAllData();
		for (int i = 0; i < expectedData.length; i++)
			assertTrue(expectedData[i] + " should be added, but " + actualData.get(i) + " was added instead",
					expectedData[i].equals(actualData.get(i)));
	}

	@Test
	public void addRecursivelyNonEmptyCaseTest() {
		nonEmptyCase();
		dll.addRecursively(1);
		Integer[] expectedData = new Integer[] { 4, 666, 1879, 1912, 1643, 1 };
		ArrayList<Integer> actualData = dll.getAllData();
		for (int i = 0; i < expectedData.length; i++)
			assertTrue(expectedData[i] + " should be added, but " + actualData.get(i) + " was added instead",
					expectedData[i].equals(actualData.get(i)));
	}

	@Test
	public void searchIterativelyEmptyCaseTest() {
		emptyCase();
		assertTrue("Expected null but got " + dll.searchIteratively(666), dll.searchIteratively(666) == null);
	}

	@Test
	public void searchRecursivelyEmptyCaseTest() {
		emptyCase();
		assertTrue("Expected null but got " + dll.searchRecursively(666), dll.searchRecursively(666) == null);
	}

	@Test
	public void searchIterativelyNonEmptyCaseTest() {
		nonEmptyCase();
		assertTrue("Should return 1879, but got" + dll.searchIteratively(1879), dll.searchIteratively(1879) == 1879);
		assertTrue("Should return null, but got" + dll.searchIteratively(364), dll.searchIteratively(364) == null);
	}

	@Test
	public void searchRecursivelyNonEmptyCaseTest() {
		nonEmptyCase();
		assertTrue("Should return 1879, but got" + dll.searchRecursively(1879), dll.searchRecursively(1879) == 1879);
		assertTrue("Should return null, but got" + dll.searchRecursively(364), dll.searchRecursively(364) == null);
	}

	@Test
	public void deleteRecursivelyEmptyCaseTest() {
		emptyCase();
		dll.deleteRecursively(4);
		assertTrue("List should be empty", dll.getFirst() == null);
	}

	@Test
	public void deleteIterativelyEmptyCaseTest() {
		emptyCase();
		dll.deleteIteratively(4);
		assertTrue("List should be empty", dll.getFirst() == null);
	}

	@Test
	public void deleteRecursivelyNonEmptyCaseTest() {
		nonEmptyCase();
		dll.deleteRecursively(666);
		Integer actual = dll.searchRecursively(666);
		assertTrue("666 was not deleted, searching it expected null, got " + actual, actual == null);
	}

	@Test
	public void deleteIterativelyNonEmptyCaseTest() {
		nonEmptyCase();
		dll.deleteIteratively(666);
		Integer actual = dll.searchIteratively(666);
		assertTrue("666 was not deleted, searching it expected null, got " + actual, actual == null);
	}
}