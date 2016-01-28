package types;

//Système de position pour très grand nombre
public class Position {
	public float x; //Position dans la zone
	public float y;
	public float z;
	
	public float Mx; //Position de la zone de niveau 1
	public float My;
	public float Mz;

	public float MMx; //Position de la zone de niveau 2
	public float MMy;
	public float MMz;
	
	public Position(int x,int y,int z, int Mx,int My,int Mz, int MMx,int MMy,int MMz){
		this.x = x;
		this.y = y;
		this.z = z;
		this.Mx = Mx;
		this.My = My;
		this.Mz = Mz;
		this.MMx = MMx;
		this.MMy = MMy;
		this.MMz = MMz;
	}
	
	public Position(int x,int y,int z, int Mx,int My,int Mz){
		this.empty();
		this.x = x;
		this.y = y;
		this.z = z;
		this.Mx = Mx;
		this.My = My;
		this.Mz = Mz;
	}

	public Position(int x,int y,int z){
		this.empty();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Position(){
		this.empty();
	}
	
	private void empty(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.Mx = 0;
		this.My = 0;
		this.Mz = 0;
		this.MMx = 0;
		this.MMy = 0;
		this.MMz = 0;
	}
	
	public float[] getPosition(){
		float[] R = {x, y, z, Mx, My, Mz, MMx, MMy, MMz};
		return R;
	}
	

}
