///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Program 4
// Files:            BSTIterator.java
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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * The Iterator for Binary Search Tree (BST) that is built using Java's Stack
 * class. This iterator steps through the items BST using an INORDER traversal.
 *
 * @author CS367
 */
public class BSTIterator<K> implements Iterator<K> 
{

	/** Stack to track where the iterator is in the BST*/
	Stack<BSTNode<K>> stack;

	/**
	 * Constructs the iterator so that it is initially at the smallest
	 * value in the set. Hint: Go to farthest left node and push each node
	 * onto the stack while stepping down the BST to get there.
	 *
	 * @param n the root node of the BST
	 */
	public BSTIterator(BSTNode<K> n)
	{
		//TODO
		stack = new Stack<BSTNode<K>>();
		while(n != null)
		{
			stack.push(n);
			n = n.getLeftChild();
		}
	}

	/**
	 * Returns true iff the iterator has more items.
	 *
	 * @return true iff the iterator has more items
	 */
	public boolean hasNext() 
	{
		//TODO
		return !stack.isEmpty();
	}

	/**
	 * Returns the next item in the iteration.
	 *
	 * @return the next item in the iteration
	 * @throws NoSuchElementException if the iterator has no more items
	 */
	public K next() 
	{
		//TODO
		BSTNode<K> node;
		node = stack.pop();
		K nodeVal = node.getKey();
		if(node.getRightChild() != null)
		{
			node = node.getRightChild();
			while(node != null)
			{
				stack.push(node);
				node = node.getLeftChild();
			}
		}
		return nodeVal;
	}

	/**
	 * Not Supported
	 */
	public void remove() 
	{
		throw new UnsupportedOperationException();
	}
}
