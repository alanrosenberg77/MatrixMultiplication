package code;

/**
 * An instance of the Matrix class simply stores a 2D array of Integers,
 * a base dimension, and a height dimension. It is capable of determining
 * whether it is equal to another matrix. That's about it...
 * Update: it has a toString method
 * @author alanr
 *
 */
public class Matrix {

	Integer[][] matrix;	//The actual matrix
	int b;				//base
	int h;				//height
	
	
	public Matrix(Integer[][] matrix) {
		this.matrix = matrix;
		b = matrix.length;
		try {
			h = matrix[0].length;
		} catch(IndexOutOfBoundsException e) {
			h = 0;
		}
	}
	
	public Matrix(Integer[][] matrix, Matrix left, Matrix right) {
		this.matrix = matrix;
		left = null;
		right = null;
	}
	
	
	public boolean equals(Matrix m) {
		
		for(int r=0 ; r<m.getBase() ; r++) {
			for(int c=0 ; c<m.getHeight() ; c++) {
				try {
					if(this.get(r, c).compareTo(m.get(r, c)) != 0) {
						return false;
					}
				} catch (IndexOutOfBoundsException e) {
					return false;
				} catch (NullPointerException e) {
					if(this.get(r, c) != null) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public String toString() {
		String s = "";
		
		for(int r=0 ; r<b ; r++) {
			for(int c=0 ; c<h ; c++) {
				s += this.get(r, c).toString() + " ";
			}
			s += "\n";
		}
		
		return s;
	}
	
	public Integer get(int row, int col) throws IndexOutOfBoundsException {
		return matrix[row][col];
	}
	
	public void setMatrix(Integer[][] matrix) {
		this.matrix = matrix;
	}
	
	public int getBase() {
		return b;
	}
	
	public int getHeight() {
		return h;
	}
}
