package code;

/**
 * An instance of a TreeNode will house a subrange of the original
 * set of matrices, and split index. It will also have handles on
 * its left and right children, as well as its parent. It seriously
 * just holds data, nothing else.
 * @author alanr
 *
 */
public class TreeNode {
	
	//yktv
	Matrix[] subrange;
	Matrix result;
	Integer split;
	TreeNode parent;
	TreeNode left;
	TreeNode right;
	
	public TreeNode(Matrix[] subrange, Integer split) {
		this.subrange = subrange;
		this.split = split;
		result = null;
		parent = null;
		left = null;
		right = null;
	}
	
	public TreeNode(Matrix[] subrange, int split, TreeNode parent, TreeNode left, TreeNode right) {
		this.subrange = subrange;
		this.split = split;
		result = null;
		this.parent = parent;
		this.left = left;
		this.right = right;
	}

	public Matrix[] getSubrange() {
		return subrange;
	}

	public void setSubrange(Matrix[] subrange) {
		this.subrange = subrange;
	}

	public Integer getSplit() {
		return split;
	}

	public void setSplit(Integer split) {
		this.split = split;
	}
	
	public TreeNode getParent() {
		return parent;
	}
	
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public TreeNode getLeft() {
		return left;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}

	public TreeNode getRight() {
		return right;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}
	
	public Matrix getResult() {
		return result;
	}
	
	public void setResult(Matrix result) {
		this.result = result;
	}
	
	
}
