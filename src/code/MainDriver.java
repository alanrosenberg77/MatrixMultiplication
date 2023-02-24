package code;

public class MainDriver {

	public static void main(String[] args) {

		Integer[][] a1 = {{1,2}, {3,4}, {5,6}, {7,8}};
		Integer[][] a2 = {{1,2,3,4}, {5,6,7,8}};
		Integer[][] a3 = {{1},{2},{3},{4}};
		Integer[][] a4 = {{1,2,3,4,5,6}};
		
		Matrix A1 = new Matrix(a1);
		Matrix A2 = new Matrix(a2);
		Matrix A3 = new Matrix(a3);
		Matrix A4 = new Matrix(a4);
		
		Calculator c = new Calculator(A1, A2, A3, A4);
		Matrix result = c.matrixMultiply(c.calcSequence());
		
		System.out.print(result.toString() + "\nRosey out");
	}

}
