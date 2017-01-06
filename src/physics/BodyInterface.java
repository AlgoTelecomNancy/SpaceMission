package physics;

import maths.Vect3D;

public interface BodyInterface {
	
	public Vect3D getAbsolutePosition();

	public Vect3D getAbsoluteRotPosition();
	
	public void updateProperties();
	
}
