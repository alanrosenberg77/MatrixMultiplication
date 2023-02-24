package code;

import java.util.Arrays;

/**
 * A Calculator object is capable of storing an array of matrices, determining
 * the best sequence to multiply them to minimize operations, and multiply them
 * using the determined sequence. Objects of this class will maintain an array
 * of the dimensions of the matrices, as well as a 2d array in which the object
 * will store the number of operations it would take to multiply particular
 * matrices. This is a form of dynamic programming because in order to determine
 * the number of operations needed to multiply compound matrices together, it is
 * necessary to know the number of operations needed to multiply single matrices
 * together, which is stored in the 2d array of operations. This prevents lots
 * of repeat calculations and saves time.
 * 
 * @author alanr
 *
 */
public class Calculator {

	private Matrix[] mtrx;	// Local handle on the matrices to be multiplied
	private Integer[][] m;	// Stores previously determined number of operations to multiply per matrix combination
	private Integer[][] s;	// Stores k values designating the splitting index when determining multiplication sequence
	private int[] d; 		// Stores the dimensions of the matrices

	/*
	 * Constructor takes in array of matrices, and stores them in 1-indexed, local
	 * array. It will also parse the matrices and store into the 0-indexed
	 * dimensions array the dimensions of the matrices.
	 */
	public Calculator(Matrix... matrices) {

		mtrx = matrices;

		m = new Integer[matrices.length][matrices.length];
		d = new int[matrices.length + 1];
		s = new Integer[matrices.length][matrices.length];

		for (int i = 0; i < mtrx.length; i++) {
			d[i] = mtrx[i].getBase();
		}
		d[d.length - 1] = mtrx[mtrx.length - 1].getHeight();
	}

	/**
	 * calcSequence will first calculate the number of operations it takes
	 * to multiply chains of matrices, storing those values in the m matrix.
	 * It will then use that data to determine the optimal sequence in which
	 * the matrices should be multiplied to minimize operations, storing
	 * those values in the s matrix. Finally, the method will generate a 
	 * binary tree whose organization of nodes indicates parenthesis when
	 * multiplying matrices.
	 * 
	 * @return TreeNode root of optimal sequence tree
	 */
	public TreeNode calcSequence() {

		//following seudocode...
		
		/*
		 * Marking m matrix slots where i = j.
		 * This saves some calculations. When multiplying a matrix by itself,
		 * the total operations is always 0.
		 */
		int n = mtrx.length;
		for (int i = 0; i < n; i++) {
			m[i][i] = 0;
		}

		
		int j;
		
		//Expanding chain length...
		for (int l = 2; l <= n; l++) {
			
			//as we consider matrix A(i)...
			for (int i = 0; i <= n - l; i++) {
				
				//determining end matrix based on chain length
				j = i + l - 1;
				//Setting as infinity to make comparison easy
				m[i][j] = Integer.MAX_VALUE;

				//Considering all matrices between A(i) and A(j)
				for (int k = i; k < j; k++) {
					//determining total operations to multiply...
					int q = m[i][k] + m[k + 1][j] + d[i] * d[k + 1] * d[j + 1];

					//and comparing to previous calculation...
					if (q < m[i][j]) {
						m[i][j] = q;
						s[i][j] = k;
					}
				}
			}
		}

		/*
		 * This next part is where we make the tree. I'll first
		 * create the root (full range of matrices, and first split
		 * index, otherwise blank), then I'll call makeTree to generate
		 * the rest of the tree.
		 */
		TreeNode root = new TreeNode(mtrx, s[0][n - 1]);
		makeTree(root, 0, n - 1, s);
		
		//returnin...
		return root;
	}

	/**
	 * makeTree is a private helper method that takes a node, and creates the
	 * appropriate child nodes corresponding to the sequence calculated in 
	 * calcSequence. It then recursively calls itself on the child nodes to
	 * generate the rest of the tree.
	 * @param current node
	 * @param starting index i
	 * @param ending index j
	 * @param optimal sequence matrix s
	 */
	private void makeTree(TreeNode current, int i, int j, Integer[][] s) {

		//Base case
		//Stopping once a node has just one matrix attached
		if (i >= j) {
			return;
		}

		//Creating subranges for left and right children
		Matrix[] left = Arrays.copyOfRange(mtrx, i, current.getSplit() + 1);
		Matrix[] right = Arrays.copyOfRange(mtrx, current.getSplit() + 1, j + 1);

		//Designating left and right children
		current.setLeft(new TreeNode(left, s[i][current.getSplit()]));
		current.setRight(new TreeNode(right, s[current.getSplit() + 1][j]));
		
		//Giving left and right children result matrices, if appropriate
		if(left.length == 1) {
			current.getLeft().setResult(left[0]);
		}
		if(right.length == 1) {
			current.getRight().setResult(right[0]);
		}
		
		//Linking children to the parent
		current.getLeft().setParent(current);
		current.getRight().setParent(current);

		//Recursive calls on children
		makeTree(current.getLeft(), i, current.getSplit(), s);
		makeTree(current.getRight(), current.getSplit() + 1, j, s);
	}

	/**
	 * matrixMultiply is a method that will do the actual multiplication of
	 * matrices and generate the final, resultant matrix of the entire range
	 * of matrices. It does so by taking the optimal sequence tree generated
	 * by the calcSequence method, and traversing it from bottom to top, until
	 * all matrices have been multiplied in the appropriate, optimal sequence.
	 * @param root node
	 * @return final, resultant matrix
	 */
	public Matrix matrixMultiply(TreeNode root) {
		
		//Finding the furthest left child
		TreeNode current = root;
		while (current.getLeft() != null) {
			current = current.getLeft();
		}
		
		TreeNode sibling = null;
		
		//Until we reach the original root node...
		while (!current.equals(root)) {
			
			//We first find the sibling of the current node...
			sibling = current.getParent().getRight();
			
			//and if it has a result attached...
			if (sibling.getResult() != null) {
				//we multiply and store result in the parent
				current.getParent().setResult(multiply(current.getResult(), sibling.getResult()));
			} else {
				
				//otherwise, we give the sibling a result
				matrixMultiply(sibling);
				continue;
			}
			
			//walkin...
			current = current.getParent();
		}

		//returnin...
		return current.getResult();
	}

	/**
	 * This is a private helper method that does the actual multiplication of
	 * matrices. It takes two matrices with like interior dimensions, and does
	 * cross multiplication to generate a resultant matrix.
	 * @param m1 matrix 1
	 * @param m2 matrix 2
	 * @return matrix product
	 */
	public Matrix multiply(Matrix m1, Matrix m2) {
		
		//Allocating space for the resultant matrix
		Integer[][] result = new Integer[m1.getBase()][m2.getHeight()];
		
		//Looping through all rows and columns...
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				
				result[i][j] = 0;
				
				//Multiplying and adding...
				for (int k = 0; k < m1.getHeight(); k++) {
					//Adding and multiplying...
					result[i][j] += m1.get(i, k) * m2.get(k, j);
				}
			}
		}

		//returnin...
		return new Matrix(result);
	}

	/*
	 * Obligatory getters and setters
	 */
	public Matrix[] getMatricies() {
		return mtrx;
	}

	public Integer[][] getOps() {
		return m;
	}

	public int[] getDimensions() {
		return d;
	}
	
	public Integer[][] getS() {
		return s;
	}

	public void setS(Integer[][] s) {
		this.s = s;
	}
}
