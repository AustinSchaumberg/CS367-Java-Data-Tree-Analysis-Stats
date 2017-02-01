///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Program 4
// Files:            BSTreeSetTester.java
// Semester:         Spring 2016
//
// Author:           Austin Schaumberg
// Email:            schaumberg2@wisc.edu
// CS Login:         schaumberg
// Lecturer's Name:  Deb Deppeler
// Lab Section:      367-002 (lecture)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
//                  CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     (name of your pair programming partner)
// Email:            (email address of your programming partner)
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
//			NOT APPLICABLE
//
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * SetTesterADT implementation using a Binary Search Tree (BST) as the data
 * structure.
 *
 * <p>The BST rebalances if a specified threshold is exceeded (explained below).
 * If rebalanceThreshold is non-positive (&lt;=0) then the BST DOES NOT
 * rebalance. It is a basic BStree. If the rebalanceThreshold is positive
 * then the BST does rebalance. It is a BSTreeB where the last B means the
 * tree is balanced.</p>
 *
 * <p>Rebalancing is triggered if the absolute value of the balancedFfactor in
 * any BSTNode is &gt;= to the rebalanceThreshold in its BSTreeSetTester.
 * Rebalancing requires the BST to be completely rebuilt.</p>
 *
 * @author CS367
 */
public class BSTreeSetTester <K extends Comparable<K>> implements SetTesterADT<K>{

	/** Root of this tree */
	BSTNode<K> root;

	/** Number of items in this tree*/
	int numKeys;

	/**
	 * rebalanceThreshold &lt;= 0 DOES NOT REBALANCE (BSTree).<br>
	 * rebalanceThreshold  &gt;0 rebalances the tree (BSTreeB).
	 */
	int rebalanceThreshold;

	/**
	 * True iff tree is balanced, i.e., if rebalanceThreshold is NOT exceeded
	 * by absolute value of balanceFactor in any of the tree's BSTnodes.Note if
	 * rebalanceThreshold is non-positive, isBalanced must be true.
	 */
	boolean isBalanced;

	/**
	 * Constructs an empty BSTreeSetTester with a given rebalanceThreshold.
	 *
	 * @param rbt the rebalance threshold
	 */
	public BSTreeSetTester(int rbt) 
	{
		//TODO
		rebalanceThreshold = rbt;
		isBalanced = true;
		numKeys = 0;
		root = null;
	}

	/**
	 * Add node to binary search tree. Remember to update the height and
	 * balancedFfactor of each node. Also rebalance as needed based on
	 * rebalanceThreshold.
	 *
	 * @param key the key to add into the BST
	 * @throws IllegalArgumentException if the key is null
	 * @throws DuplicateKeyException if the key is a duplicate
	 */
	public void add(K key) 
	{
		//TODO
		// conditional that checks for duplicate keys in tree.
		if(contains(key))
		{
			throw new DuplicateKeyException();
		}
		// add new node with given key to root, using helper method.
		root = add(root, key);
		// add to numKeys
		numKeys++;
		// check if tree is balanced. If not rebalance.
		if(!this.isBalanced)
		{
			rebalance();
		}
	}
	
	/**
	 * "Helper" method for add to actually perform the add within the 
	 * BST.
	 * 
	 * Notes about Method:
	 * The helper method returns a BSTnode. It does this to handle
	 * the case when the node passed to it is null. In general, the helper
	 * method is passed a pointer to a  possibly empty tree. Its 
	 * responsibility is to add the indicated key to that tree and 
	 * return a pointer to the root of the modified tree. 
	 * If the original tree is empty, the result is a one-node tree. 
	 * Otherwise, the result is a pointer to the same node that was passed 
	 * as an argument.
	 * 
	 * @param n, the node to be added to the current BST
	 * @param key, the key to add into the BST
	 * @return the BSTNode to act as or be added to root
	 */
	private BSTNode<K> add(BSTNode<K> n, K key)
	{
		// base case
		if (n==null)
		{
			return new BSTNode<K>(key);
		}
		// if the key value exists, throw exception
		if (n.getKey().equals(key)) 
		{
			throw new DuplicateKeyException();
		}
		// portion of recursive method which add which adds left child nodes
		if (key.compareTo( n.getKey()) < 0)
		{
			BSTNode<K> leftChild = n.getLeftChild();;
			// add key to the left subtree
			n.setLeftChild( add(leftChild, key) );
		}
		// portion of recursive method which add which adds right child nodes 
		else
		{
			BSTNode<K> rightChild = n.getRightChild();
			//add key to the right subtree
			n.setRightChild( add(rightChild, key) );
		}
		// conditional statement setting a variable left height to 0 if null,
		// otherwise sets height to the height of the left child.
		int leftHeight = n.getLeftChild() 
				== null ? 0 : n.getLeftChild().getHeight();
		// conditional statement setting a variable left height to 0 if null,
		// otherwise sets height to the height of the left child.
		int rightHeight = n.getRightChild() 
				== null ? 0: n.getRightChild().getHeight();
		// sets height of the node to the max height between left and
		// right child. Add one to account for the node itself.
		n.setHeight(Math.max(leftHeight, rightHeight)+1);
		// sets balance factor of the node to the difference in height 
		// between left and right child. Add one to account for the node itself
		n.setBalanceFactor(leftHeight - rightHeight);
		// adjust isBalanced boolean to refelect current state of the tree.
		// if rebalance method has run, be sure to set back to true.
		if(Math.abs(n.getBalanceFactor()) 
				> this.rebalanceThreshold && this.rebalanceThreshold > 0)
		{
			isBalanced = false;
		}
		// return node to non-helper add method.
		return n;
	}

	/**
	 * Rebalances the tree by:
	 * 1. Copying all keys in the BST in sorted order into an array.
	 *    Hint: Use your BSTIterator.
	 * 2. Rebuilding the tree from the sorted array of keys.
	 */
	public void rebalance() 
	{
		//TODO
		K[] keys = (K[]) new Comparable[numKeys];
		int count = 0;
		Iterator<K> iter = iterator();
		while(iter.hasNext())
		{
			K transferInfo = iter.next();
			keys[count] = transferInfo;
			count++;
		}
		root = sortedArrayToBST(keys, 0, keys.length-1);
		isBalanced = true;
	}

	/**
	 * Recursively rebuilds a binary search tree from a sorted array.
	 * Reconstruct the tree in a way similar to how binary search would explore
	 * elements in the sorted array. The middle value in the sorted array will
	 * become the root, the middles of the two remaining halves become the left
	 * and right children of the root, and so on. This can be done recursively.
	 * Remember to update the height and balanceFactor of each node.
	 *
	 * @param keys the sorted array of keys
	 * @param start the first index of the part of the array used
	 * @param stop the last index of the part of the array used
	 * @return root of the new balanced binary search tree
	 */
	private BSTNode<K> sortedArrayToBST(K[] keys, int start, int stop) 
	{
		//TODO
		//Base case of recursive code:
		if(start > stop)
		{
			return null;	
		}
		// retrieve the mid element of array and make it root of BST
		int midElmnt = (start + stop) /2;
		if(keys[midElmnt]!=null)
		{
			BSTNode<K> node = new BSTNode<K> (keys[midElmnt]);
			// Recursive call to create left subtree and make it left child 
			node.setLeftChild(sortedArrayToBST(keys, start, midElmnt - 1));
			// Recursive call to create right subtree and make it right child 
			node.setRightChild(sortedArrayToBST(keys, midElmnt + 1, stop));
			// use conditional statements to check for null values in 
			// tree's re-construction. These variables will be used to
			// set the balance factor and height of the node being re-created
			// from the array.
			int leftHeight = node.getLeftChild() 
					== null ? 0 : node.getLeftChild().getHeight();
			int rightHeight = node.getRightChild() 
					== null ? 0: node.getRightChild().getHeight();
			node.setHeight(Math.max(leftHeight, rightHeight)+1);
			node.setBalanceFactor(leftHeight - rightHeight);
			// return node
			return node;
		}
		else
			return null;
	}

	/**
	 * Returns true iff the key is in the binary search tree.
	 *
	 * @param key the key to search
	 * @return true if the key is in the binary search tree
	 * @throws IllegalArgumentException if key is null
	 */
	public boolean contains(K key) 
	{
		//TODO
		if(key==null)
		{
			throw new IllegalArgumentException();
		}
		return contains(root, key);
	}

	/**
	 * Helper method for the contains method above.
	 * 
	 * @param n, the node to be returned to the non helper contains 
	 * method.
	 * @param key, the key to search for.
	 * @return true if the key is within the BST.
	 */
	private boolean contains(BSTNode<K> n, K key)
	{
		// base case:
		if(n==null)
		{
			return false;
		}
		if(n.getKey().equals(key))
		{
			return true;
		}
		if(key.compareTo(n.getKey()) < 0)
		{
			// if key is less than this node's key; look in left subtree
			return contains(n.getLeftChild(), key);
		}
		else
		{
			//if this key is greater than this node's key; look in right
			// subtree
			return contains(n.getRightChild(), key);
		}
	}

	/**
	 * Returns the sorted list of keys in the tree that are in the specified
	 * range (inclusive of minValue, exclusive of maxValue). This can be done
	 * recursively.
	 *
	 * @param minValue the minimum value of the desired range (inclusive)
	 * @param maxValue the maximum value of the desired range (exclusive)
	 * @return the sorted list of keys in the specified range
	 * @throws IllegalArgumentException if either minValue or maxValue is
	 * null, or minValue is larger than maxValue
	 */
	public List<K> subSet(K minValue, K maxValue) 
	{
		//TODO
		List<K> subSetList = new ArrayList<K>();
		int minVal = (int) minValue;
		int maxVal = (int) maxValue;
		if (minVal > maxVal||minValue==null||maxValue==null)
			throw new IllegalArgumentException("minValue > maxValue");
		if (root != null)
			subSetList = rangeSearchBST(root, minVal, maxVal);
		return subSetList;
	}
	/**
	 * Helper method for the subSet method.  Returns the sorted list of keys 
	 * in the tree that are in the specified range (inclusive of minValue, 
	 * exclusive of maxValue). This is done recursively.
	 *
	 * 
	 */
	private List<K> rangeSearchBST(BSTNode<K> root, int lower, int upper) 
	{
		List<K> subSetList = new ArrayList<K>();
		BSTNode<K> leftChild = root.getLeftChild();
		BSTNode<K> rightChild = root.getRightChild();
		K rootKey = root.getKey();
		int intRootKey = (int)rootKey;
		if (leftChild != null && intRootKey > lower) 
		{
			root = leftChild;
			rangeSearchBST(root, lower, upper);
		} 
		if (intRootKey >= lower && intRootKey <= upper) 
		{
			subSetList.add(rootKey);
		} 
		if (rightChild != null && intRootKey < upper) 
		{
			root = rightChild;
			rangeSearchBST(root, lower, upper);
		}
		return subSetList;
	}

	/**
	 * Return an iterator for the binary search tree.
	 * @return the iterator
	 */
	public Iterator<K> iterator() 
	{
		//TODO
		return new BSTIterator<K>(root);
	}

	/**
	 * Clears the tree, i.e., removes all the keys in the tree.
	 */
	public void clear() 
	{
		//TODO
		root = null;
		numKeys = 0;
	}

	/**
	 * Returns the number of keys in the tree.
	 *
	 * @return the number of keys
	 */
	public int size() 
	{
		//TODO
		return numKeys;
	}

	/**
	 * Displays the top maxNumLevels of the tree. DO NOT CHANGE IT!
	 *
	 * @param maxDisplayLevels from the top of the BST that will be displayed
	 */
	public void displayTree(int maxDisplayLevels) 
	{
		if (rebalanceThreshold > 0) {
			System.out.println("---------------------------" +
					"BSTreeBSet Display-------------------------------");
		} else {
			System.out.println("---------------------------" +
					"BSTreeSet Display--------------------------------");   
		}
		displayTreeHelper(root, 0, maxDisplayLevels);
	}

	private void displayTreeHelper(BSTNode<K> n, int curDepth,
			int maxDisplayLevels) {
		if(maxDisplayLevels <= curDepth) return;
		if (n == null)
			return;
		for (int i = 0; i < curDepth; i++) {
			System.out.print("|--");
		}
		System.out.println(n.getKey() + "[" + n.getHeight() + "]{" +
				n.getBalanceFactor() + "}");
		displayTreeHelper(n.getLeftChild(), curDepth + 1, maxDisplayLevels);
		displayTreeHelper(n.getRightChild(), curDepth + 1, maxDisplayLevels);
	}
}
