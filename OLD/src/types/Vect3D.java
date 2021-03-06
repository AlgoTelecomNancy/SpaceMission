package types;

//Système de position x y z
public class Vect3D {
	public double x;
	public double y;
	public double z;

	public Vect3D(double x,double y,double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vect3D(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Vect3D clone(){
		return new Vect3D(x, y, z);
	}
	
	public void set(double x,double y,double z){
		this.x = x;
		this.y = y;
		this.z = z;
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
		System.out.println("Wrong coef ("+i+") in Vect3D.getCoef(int i)");
		return 0;
	}
	
	//Mettre la valeur entière
	public Vect3D floor(){
		this.x = Math.floor(this.x);
		this.y = Math.floor(this.y);
		this.z = Math.floor(this.z);
		return this;
	}
	
	//Multiplier le vecteur
	public Vect3D multiply(double k){
		return new Vect3D(k*this.x,k*this.y,k*this.z);
	}
	
	public Vect3D multiply(Vect3D v) {
		
		return new Vect3D(this.y*v.z-this.z*v.y,
				this.z*v.x-this.x*v.z,
				this.x*v.y-this.y*v.x);
	}
	
	public double size(){
		return Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2));
	}
	
	public double[] get(){
		double[] R = {x, y, z};
		return R;
	}
	
	public int[] getInt(){
		int[] R = {(int)x, (int)y, (int)z};
		return R;
	}
	
	public void translate(Vect3D p){
		this.x += p.x;
		this.y += p.y;
		this.z += p.z;
	}
	
	public void modulo(int m){
		this.x = this.x%m;
		this.y = this.y%m;
		this.z = this.z%m;
	}

	@Override public String toString(){
		return "("+this.x+", "+this.y+", "+this.z+")";
	}

}
