package maths;

/**
 * This vector will be used for positionning elements in a super sized universe
 * TODO
 * @author romaricportable
 *
 */
public class SuperVect3D extends Vect3D {

	public double CUBESIZE = 1000000000; //1 000 000 km
	
	public long superX = 0;
	public long superY = 0;
	public long superZ = 0;

	public SuperVect3D add(Vect3D deltaPosition){
		this.x += deltaPosition.x;
		this.y += deltaPosition.y;
		this.z += deltaPosition.z;
		
		this.updateSuperPos();
		return this;
	}
	
	/**
	 * Only if the two elements are in the same area
	 * @param superPosition
	 * @return
	 */
	public Vect3D minus(SuperVect3D superPosition){
		
		Vect3D res = new Vect3D();
		res.x = this.x - superPosition.x + (this.superX - superPosition.superX)*CUBESIZE;
		res.y = this.y - superPosition.y + (this.superY - superPosition.superY)*CUBESIZE;
		res.z = this.z - superPosition.z + (this.superZ - superPosition.superZ)*CUBESIZE;

		return res;
		
	}

	/**
	 * Return distance rounded to 1 000 000 km
	 * @param superPosition
	 * @return
	 */
	public SuperVect3D superMinus(SuperVect3D superPosition){
		
		SuperVect3D res = new SuperVect3D();
		res.superX = this.superX - superPosition.superX;
		res.superY = this.superY - superPosition.superY;
		res.superZ = this.superZ - superPosition.superZ;

		return res;
		
	}
	
	/**
	 * Return true if the distance between the two vessels is less of MAX_DOUBLE VALUE
	 * @param otherElement
	 * @return
	 */
	public boolean isInMyArea(SuperVect3D otherElement){
		SuperVect3D sv = this.superMinus(otherElement);
		if(Math.abs(sv.superX)<=1 && Math.abs(sv.superY)<=1 && Math.abs(sv.superZ)<=1){
			return true;
		}
		return false;
	}
	
	private void updateSuperPos(){
		if(this.x>CUBESIZE/2){
			this.x += -CUBESIZE;
			this.superX += 1;
		}
		if(this.x<-CUBESIZE/2){
			this.x += +CUBESIZE;
			this.superX += -1;
		}
		
		if(this.y>CUBESIZE/2){
			this.y += -CUBESIZE;
			this.superY += 1;
		}
		if(this.y<-CUBESIZE/2){
			this.y += CUBESIZE;
			this.superY += -1;
		}
		
		if(this.z>CUBESIZE/2){
			this.z += -CUBESIZE;
			this.superZ += 1;
		}
		if(this.z<-CUBESIZE/2){
			this.z += CUBESIZE;
			this.superZ += -1;
		}
	}
	
}
