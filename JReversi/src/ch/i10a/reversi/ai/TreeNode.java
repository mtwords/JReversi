package ch.i10a.reversi.ai;

import java.util.HashSet;
import java.util.Set;

/**
 * A generic TreeNode implementation.
 * Especially used to build up the game tree used by the artificial intelligence
 * for evaluating the next move.
 */
public class TreeNode<T> {

	// fields, board, ... ???
	T data;
	Set<TreeNode<T>> children = new HashSet<TreeNode<T>>();
	// Weitere benötigte Felder:
	// - Gewichtung
	// - ...

	public TreeNode(T data) {
		setData(data);
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

	public boolean isLeaf() {
		return children.size() == 0;
	}
}
