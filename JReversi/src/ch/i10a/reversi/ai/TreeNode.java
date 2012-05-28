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

	public TreeNode(T data) {
		setData(data);
	}
	public TreeNode(T data, List<Field> possibleMoves) {
		setData(data);
		setPossibleMoves(possibleMoves);
	}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	public Set<TreeNode<T>> getChildren() {
		return children;
	}
	public void setChildren(Set<TreeNode<T>> children) {
		this.children = children;
	}
	public void addChild(TreeNode<T> child) {
		children.add(child);
	}
	public void removeChild(TreeNode<T> child) {
		children.remove(child);
	}

	public List<Field> getPossibleMoves() {
		return possibleMoves;
	}
	public void setPossibleMoves(List<Field> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}

	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}

	public boolean isLeaf() {
		return children.size() == 0;
	}
}
