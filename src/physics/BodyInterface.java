package physics;

import maths.Vect3D;

public interface BodyInterface {
	
	public Vect3D getAbsolutePosition();

	public Vect3D getAbsoluteRotPosition();
	
	public void updateProperties();
	
	public void setPosition(Vect3D position);
	
	public void setRotPosition(Vect3D rotPosition);

	public Vect3D getPosition();

	public Vect3D getRotPosition();
}
