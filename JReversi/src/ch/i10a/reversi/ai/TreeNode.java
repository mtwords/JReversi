package ch.i10a.reversi.ai;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.i10a.reversi.gui.Field;

/**
 * A generic TreeNode implementation.
 * Especially used to build up the game tree used by the artificial intelligence
 * for evaluating the next move.
 */
public class TreeNode<T> {

	// fields, board, ... ???
	T data;
	Field field;
	List<Field> possibleMoves;
	Set<TreeNode<T>> children = new HashSet<TreeNode<T>>();

	/**
	 * Standard Constructor, inherited from super class
	 * 
	 * 
	 */
	public TreeNode(T data) {
		setData(data);
	}
	
	/**
	 * 
	 * Constructor of the specific TreeNode
	 * 
	 * @param The data Field is mostly used for boards, possibleMoves represents all possible moves in the next playstep
	 */
	public TreeNode(T data, List<Field> possibleMoves) {
		setData(data);
		setPossibleMoves(possibleMoves);
	}

	/**
	 * 
	 * Standard getter for the data (mostly of the type board)
	 * 
	 * @return data of type T
	 */
	public T getData() {
		return data;
	}
	
	/**
	 * 
	 * Standard setter for the data (mostly of the type board)
	 * 
	 * @param data of type <T> to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 
	 * Standard getter for a set of TreeNode<T>
	 * 
	 * @return Set of TreeNode<T>
	 */
	public Set<TreeNode<T>> getChildren() {
		return children;
	}
	
	/**
	 * 
	 * Standard setter for the children (mostly a TreeNode of the type board)
	 * 
	 * @param Set of children
	 */
	public void setChildren(Set<TreeNode<T>> children) {
		this.children = children;
	}
	
	/**
	 * 
	 * adds a child to the children
	 * 
	 * @param the child Node<T> to add
	 */
	public void addChild(TreeNode<T> child) {
		children.add(child);
	}
	
	/**
	 * 
	 * removes a child from the children
	 * 
	 * @param the child Node<T> to remove
	 */
	public void removeChild(TreeNode<T> child) {
		children.remove(child);
	}

	/**
	 * 
	 * gives all possible moves saved in this specific TreeNode
	 * 
	 * @return List of possible Moves, datatype Field
	 */
	public List<Field> getPossibleMoves() {
		return possibleMoves;
	}
	/**
	 * 
	 * Sets all the possible moves for this specific TreeNode
	 * 
	 * @param possibleMoves is a List of Field
	 */
	public void setPossibleMoves(List<Field> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}

	public Field getField() {
		return field;
	}
	
	/**
	 * 
	 * Sets the field that is to play in this TreeNode
	 * 
	 * @param playable Field field
	 */
	public void setField(Field field) {
		this.field = field;
	}

	/**
	 * 
	 * if it is a Node is a leaf, it has no children, so when this node is a leaf, it returns true
	 * 
	 * @return true if the node is a tree, else it returns false
	 */
	public boolean isLeaf() {
		return children.size() == 0;
	}
}
