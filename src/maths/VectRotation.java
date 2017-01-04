package maths;

public class VectRotation {

	public static Vect3D rotate(Vect3D matrix, Vect3D angles){
		return rotMatrixModule_Space(angles).multiply(matrix);
	}
	
	/**
	 * Compute the rotation matrix between the spaceship and the space
	 * 
	 * @return The rotation matrix between the spaceship and the space
	 */
	public static Matrix rotMatrixModule_Space(Vect3D orientation) {
		Matrix rotx = new Matrix();
		rotx.setCoef(1, 1, 1);
		rotx.setCoef(2, 2, Math.cos(orientation.x));
		rotx.setCoef(3, 3, Math.cos(orientation.x));
		rotx.setCoef(2, 3, -Math.sin(orientation.x));
		rotx.setCoef(3, 2, Math.sin(orientation.x));

		Matrix roty = new Matrix();
		roty.setCoef(2, 2, 1);
		roty.setCoef(1, 1, Math.cos(orientation.y));
		roty.setCoef(3, 3, Math.cos(orientation.y));
		roty.setCoef(1, 3, Math.sin(orientation.y));
		roty.setCoef(3, 1, -Math.sin(orientation.y));

		Matrix rotz = new Matrix();
		rotz.setCoef(3, 3, 1);
		rotz.setCoef(1, 1, Math.cos(orientation.z));
		rotz.setCoef(2, 2, Math.cos(orientation.z));
		rotz.setCoef(1, 2, -Math.sin(orientation.z));
		rotz.setCoef(2, 1, Math.sin(orientation.z));

		return Matrix.multiply(Matrix.multiply(rotz, roty), rotx);
	}
	
}
