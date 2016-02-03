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
	
	public void set(double x,double y,double z){
		this.x = x;
		this.y = y;
		this.z = z;
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

	@Override public String toString(){
		return "("+this.x+", "+this.y+", "+this.z+")";
	}

}
