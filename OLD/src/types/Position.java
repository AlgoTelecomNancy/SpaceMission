package types;

//Système de position pour très grand nombre
public class Position {
	
	//L'univers est découpé en zones
	// la position varie entre -10^6 et + 10^6 km
	// La sous zone varie entre (int) -10^9 et + 10^9 de 2*10^6 km
	// La sous zone varie entre (int) -10^6 et + 10^6 de 4*10^15 km
	// Au total l'univers a une taille de 8*10^21 km (=10^8 al)
	private Vect3D position = new Vect3D(0,0,0);
	private Vect3D sousZone = new Vect3D(0,0,0);
	private Vect3D zone = new Vect3D(0,0,0);

	public Position(){
	}
	
	public Position(double x,double y,double z,int szx,int szy,int szz,int zx,int zy,int zz){
		this.position.set(x, y, z);
		this.sousZone.set((double)szx, (double)szy, (double)szz);
		this.zone.set((double)zx, (double)zy, (double)zz);
	}
	
	public Position(Vect3D pos, Vect3D sZone, Vect3D zone){
		this.position = pos;
		this.sousZone = sZone.floor(); //S'assurer que c'est entier
		this.zone = zone.floor();
	}
	
	public Vect3D getZone(){
		update();
		return zone;
	}
	public Vect3D getSousZone(){
		update();
		return sousZone;
	}
	
	public Vect3D getPosition(){
		update();
		return position;
	}
	
	public void setZone(Vect3D z){
		this.zone = z;
		update();
	}
	public void setSousZone(Vect3D sz){
		this.sousZone = sz;
		update();
	}
	
	public void setPosition(Vect3D p){
		this.position = p;
		update();
	}
	
	public void translate(Vect3D p){
		this.position.x += p.x;
		this.position.y += p.y;
		this.position.z += p.z;
		update();
	}
	
	//Replacer les éléments en fonction de leur position avant de les afficher
	private void update(){
		
		//Position et sous zone
		if(position.x>1000000){
			position.x = position.x-2000000;
			sousZone.x += 1;
		}
		if(position.x<-1000000){
			position.x = position.x+2000000;
			sousZone.x += -1;
		}
		if(position.y>1000000){
			position.y = position.y-2000000;
			sousZone.y += 1;
		}
		if(position.y<-1000000){
			position.y = position.y+2000000;
			sousZone.y += -1;
		}
		if(position.z>1000000){
			position.z = position.z-2000000;
			sousZone.z += 1;
		}
		if(position.z<-1000000){
			position.z = position.z+2000000;
			sousZone.z += -1;
		}
		
		//Zone
		if(sousZone.x>1000000000){
			sousZone.x = sousZone.x-2000000;
			zone.x += 1;
		}
		if(sousZone.x<-1000000000){
			sousZone.x = sousZone.x+2000000;
			zone.x += -1;
		}
		if(sousZone.y>1000000000){
			sousZone.y = sousZone.y-2000000;
			zone.y += 1;
		}
		if(sousZone.y<-1000000000){
			sousZone.y = sousZone.y+2000000;
			zone.y += -1;
		}
		if(sousZone.z>1000000000){
			sousZone.z = sousZone.z-2000000;
			zone.z += 1;
		}
		if(sousZone.z<-1000000000){
			sousZone.z = sousZone.z+2000000;
			zone.z += -1;
		}
		
		//Boucle
		if(zone.x>1000000000){
			zone.x = -1000000000;
		}
		if(zone.x<-1000000000){
			zone.x = 1000000000;
		}
		if(zone.y>1000000000){
			zone.y = -1000000000;
		}
		if(zone.y<-1000000000){
			zone.y = 1000000000;
		}
		if(zone.z>1000000000){
			zone.z = -1000000000;
		}
		if(zone.z<-1000000000){
			zone.z = 1000000000;
		}
		
	}
	
	@Override public String toString(){
		String ret = "(";
		
		ret += toLisibleNb(position.x,sousZone.x,zone.x)+",";
		ret += toLisibleNb(position.y,sousZone.y,zone.y)+",";
		ret += toLisibleNb(position.z,sousZone.z,zone.z);

		ret += ")";
		
		return ret;
	}
	
	private String toLisibleNb(double p, double sz, double z){
		
		String ret = "";
		
		double temp_p = p;
		int temp_sz = (int)sz;
		int temp_z = (int)z;
		
		if(temp_z!=0){
			ret += 4*temp_z+"*10^15+";
		}
		if(temp_sz!=0){
			ret += 2*temp_sz+"*10^6+";
		}
		ret += temp_p+"km";

		return ret;
		
	}
	
}
