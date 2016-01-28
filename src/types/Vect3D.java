package types;

//Système de position x y z
public class Vect3D {
	public float x;
	public float y;
	public float z;

	public Vect3D(int x,int y,int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vect3D(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public float[] get(){
		float[] R = {x, y, z};
		return R;
	}
	

}
