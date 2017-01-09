package maths;

public class VectRotation {

	public static Vect3D rotate(Vect3D matrix, Vect3D angles){
		return rotMatrixModule_Space(angles).multiply(matrix);
	}
	
	/**
	 * Convert a quaternion axis to matrix of rotation (VERIFIED - OK)
	 * @param quaternion
	 * @return
	 */
	public static Matrix quaternionToMatrix(Vect3D quaternion){
		
		double theta = quaternion.size();
				
		double uUn = quaternion.x;
		double vUn = quaternion.y;
		double wUn = quaternion.z;
		
		//Normalisation
        double u = uUn/theta;
        double v = vUn/theta;
        double w = wUn/theta;

        double u2 = u*u;
        double v2 = v*v;
        double w2 = w*w;
        double cosT = Math.cos(theta);
        double oneMinusCosT = 1-cosT;
        double sinT = Math.sin(theta);
        
        Matrix res = new Matrix();
                
        if(Math.abs(theta)<=1E-9){
            res.setCoef(1, 1, 1);
            res.setCoef(2, 2, 1);
            res.setCoef(3, 3, 1);
        	return res;
        }

        // Build the matrix entries element by element.
        res.setCoef(1, 1, u2 + (v2 + w2) * cosT);
        res.setCoef(1, 2, u*v*oneMinusCosT - w*sinT);
        res.setCoef(1, 3, u*w*oneMinusCosT + v*sinT);

        res.setCoef(2, 1, u*v * oneMinusCosT + w*sinT);
        res.setCoef(2, 2, v2 + (u2 + w2) * cosT);
        res.setCoef(2, 3, v*w * oneMinusCosT - u*sinT);

        res.setCoef(3, 1, u*w * oneMinusCosT - v*sinT);
        res.setCoef(3, 2, v*w * oneMinusCosT + u*sinT);
        res.setCoef(3, 3, w2 + (u2 + v2) * cosT);

		
		return res;
	}
	
	/**
	 * Convert an intrinsec axis to matrix of rotation (VERIFIED - OK)
	 * @param quaternion
	 * @return
	 */
	public static Matrix intrinsecToMatrix(Vect3D intrinsec){
		return rotMatrixModule_Space(intrinsec);
	}
	
	/**
	 * Convert a rotation matrix to quaternion (VERIFIED - OK) 
	 * @param quaternion
	 * @return
	 */
	public static Vect3D matrixToQuaternion(Matrix matrix){
		
		double qr = Math.acos(0.5 * (matrix.getCoef(1, 1)+matrix.getCoef(2, 2)+matrix.getCoef(3, 3)-1));
		
		double qi = (matrix.getCoef(3, 2)-matrix.getCoef(2, 3))/(4*qr);
		double qj = (matrix.getCoef(1, 3)-matrix.getCoef(3, 1))/(4*qr);
		double qk = (matrix.getCoef(2, 1)-matrix.getCoef(1, 2))/(4*qr);

		double l = Math.sqrt(qi*qi + qj*qj + qk*qk);

		if(l<=1E-9 || qr<=1E-9){
			return new Vect3D();
		}
		
		double x = qi * qr / l;
		double y = qj * qr / l;
		double z = qk * qr / l;
		
		return new Vect3D(x,y,z);
	}
	
	/**
	 * Convert a rotation matrix to intrinsec angles
	 * @param quaternion
	 * @return
	 */
	public static Vect3D matrixToIntrinsec(Matrix matrix){
        
        double z = Math.atan2(
        		matrix.getCoef(2,1),
        		matrix.getCoef(1,1)
        		);
        
        double y = -Math.asin(
        		matrix.getCoef(3,1)
        		);
        
        double x = Math.atan2(
        		matrix.getCoef(3,2),
        		matrix.getCoef(3,3)
        		);
		
        if(y>=Math.PI/2-(1E-9)){
        	z = 0;
        	x = Math.atan2(
            		matrix.getCoef(1,2),
            		matrix.getCoef(1,3)
            		);
        }
        
        if(y<=-Math.PI/2+(1E-9)){
        	z = 0;
        	x = Math.atan2(
            		-matrix.getCoef(1,2),
            		-matrix.getCoef(1,3)
            		);
        }
        
        return new Vect3D(x,y,z);
        
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
