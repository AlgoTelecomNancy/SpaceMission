package display;

import com.jogamp.opengl.GLAutoDrawable;

import maths.Vect3D;

public interface DrawableObject {

	public void draw(GLAutoDrawable drawable, Camera3D camera);
	
	public Vect3D getPosition();
}
