package maths;

public class Vect3D {

	public double x = 0;
	public double y = 0;
	public double z = 0;


	public Vect3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vect3D() {

	}

	public double getCoef(int i) {
		switch (i) {
			case 0:
				return x;
			case 1:
				return y;
			case 2:
				return z;
		}
		System.out.println("Wrong coef (" + i + ") in Vect3D.getCoef(int i)");
		return 0;
	}

	/**
	 * Return a new vector that is this one minus the parameter
	 * 
	 * @param Vect3D
	 *            v vect2
	 * @return
	 */
	public Vect3D minus(Vect3D v) {
		return this.add(v.mult(-1.0));
	}

	public Vect3D add(Vect3D v) {
		return new Vect3D(this.x + v.x, this.y + v.y, this.z + v.z);
	}

	public Vect3D mult(double m) {
		return new Vect3D(this.x * m, this.y * m, this.z * m);
	}

	public Vect3D vectProd(Vect3D v) {
		return new Vect3D(
				this.y * v.z - this.z * v.y,
				this.z * v.x - this.x * v.z,
				this.x * v.y - this.y * v.x);
	}

	/**
	 * Return length of the vector
	 * 
	 * @return
	 */
	public double size() {
		return Math.sqrt(
				Math.pow(this.x, 2) +
						Math.pow(this.y, 2) +
						Math.pow(this.z, 2));
	}
	
	public Vect3D getNormalized(){
		return this.mult(1 / size());
	}

	public String toString() {
		return "(" + x + "; " + y + "; " + z + ")";
	}
	
	public Vect3D clone(){
		return new Vect3D(x, y, z);
	}
}
