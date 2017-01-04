package types;

/**
 * For now, just handle 3*3 matrix (for rotation)
 * 
 * @author alexandr9519
 * 
 */
public class Matrix {

	private double[][] coef;

	public Matrix() {
		coef = new double[3][3];
	}

	public Matrix(double[][] coef) {
		this.coef = coef;
	}

	public double[][] getCoef() {
		return coef;
	}

	public double getCoef(int x, int y) {
		return coef[x-1][y-1];
	}

	public void setCoef(double[][] coef) {
		this.coef = coef;
	}

	public void setCoef(int x, int y, double coef) {
		this.coef[x-1][y-1] = coef;
	}
	
	public static Matrix multiply(Matrix A, Matrix B) {
		Matrix C = new Matrix();
		for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                for (int k = 0; k < 3; k++)
                    C.coef[i][j] += A.coef[i][k] * B.coef[k][j];
		return C;
	}
	
	public Vect3D multiply(Vect3D v) {
		double[] result = new double[3];
		for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
            	result[i] += this.coef[i][j] * v.getCoef(j);
		return new Vect3D(result[0], result[1], result[2]);
	}

}
