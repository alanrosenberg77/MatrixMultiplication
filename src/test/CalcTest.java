package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import code.Calculator;
import code.Matrix;
import code.TreeNode;

public class CalcTest {

	Matrix A1;
	Matrix A2;
	Matrix A3;
	Matrix A4;
	Matrix M;
	Matrix S;
	Matrix result;
	Calculator c;
	
	@Before
	public void setUp() throws Exception {
		
		Integer[][] a1 = {{1,2}, {3,4}, {5,6}, {7,8}};
		Integer[][] a2 = {{1,2,3,4}, {5,6,7,8}};
		Integer[][] a3 = {{1},{2},{3},{4}};
		Integer[][] a4 = {{1,2,3,4,5,6}};
		Integer[][] m = {{0,32,16,40},{null,0,8,20},{null,null,0,24},{null,null,null,0}};
		Integer[][] s = {{null,0,0,2},{null,null,1,2},{null,null,null,2},{null,null,null,null}};
		Integer[][] r = {{170,340,510,680,850,1020},{370,740,1110,1480,1850,2220},
				{570,1140,1710,2280,2850,3420},{770,1540,2310,3080,3850,4620}};
		A1 = new Matrix(a1);
		A2 = new Matrix(a2);
		A3 = new Matrix(a3);
		A4 = new Matrix(a4);
		M = new Matrix(m);
		S = new Matrix(s);
		result = new Matrix(r);
		
		c = new Calculator(A1, A2, A3, A4);
	}
	
	@Test
	public void creationTest() {
		
		Matrix[] mtrx = c.getMatricies();
		int[] dims = c.getDimensions();
		
		assertTrue(A1.equals(mtrx[0]));
		assertTrue(A2.equals(mtrx[1]));
		assertTrue(A3.equals(mtrx[2]));
		assertTrue(A4.equals(mtrx[3]));
		
		assertEquals(A1.getBase(), dims[0]);
		assertEquals(A1.getHeight(), dims[1]);
		assertEquals(A2.getBase(), dims[1]);
		assertEquals(A2.getHeight(), dims[2]);
		assertEquals(A3.getBase(), dims[2]);
		assertEquals(A3.getHeight(), dims[3]);
		assertEquals(A4.getBase(), dims[3]);
		assertEquals(A4.getHeight(), dims[4]);
	}

	/*
	 * calcSequence() Tests
	 */
	@Test
	public void calcSequenceTest() {
		c.calcSequence();
		assertTrue(S.equals(new Matrix(c.getS())));
		assertTrue(M.equals(new Matrix(c.getOps())));
	}
	
	/*
	 * matrixMultiply() Tests
	 */
	
	// TODO matrixMultiply() Tests
	@Test
	public void matrixMultiplyTest() {
		TreeNode tree = c.calcSequence();
		assertTrue(c.matrixMultiply(tree).equals((result)));
	}

}
