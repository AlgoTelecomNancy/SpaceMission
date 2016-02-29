package display;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import types.Vect3D;

public class Cube
{
	final public Vect3D position;
	final public Vect3D size;
	final public Vect3D angles;
	
	public Cube(Vect3D position, Vect3D size, Vect3D angles)
	{
		this.position = position.clone();
		this.size = size.clone();
		this.angles = angles.clone();
	}
	
	public Cube clone()
	{
		return new Cube(position, size, angles);
	}
	
	public void setPosition(Vect3D position)
	{
		this.position.x = position.x;
		this.position.y = position.y;
		this.position.z = position.z;
	}
	
	public void setAngles(Vect3D angles)
	{
		this.angles.x = angles.x;
		this.angles.y = angles.y;
		this.angles.z = angles.z;
	}
	
	public void setSize(Vect3D size)
	{
		this.size.x = size.x;
		this.size.y = size.y;
		this.size.z = size.z;
	}
	
	public void draw(GLAutoDrawable drawable, Camera3D camera)
	{
		GL2 gl = drawable.getGL().getGL2();		
		
		gl.glLoadIdentity();
		gl.glRotatef(-(float)camera.angles.x, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(-(float)camera.angles.y, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(-(float)camera.angles.z, 0.0f, 0.0f, 1.0f);
		gl.glTranslatef((float)position.x, (float)position.y, (float)position.z);
		gl.glTranslatef(-(float)camera.position.x, -(float)camera.position.y, -(float)camera.position.z);
		gl.glRotatef((float)angles.x, 1.0f, 0.0f, 0.0f);
		gl.glRotatef((float)angles.y, 0.0f, 1.0f, 0.0f);
		gl.glRotatef((float)angles.z, 0.0f, 0.0f, 1.0f);
		gl.glScalef((float)size.x/2, (float)size.y/2, (float)size.z/2);
		
		
		
		gl.glBegin(GL2.GL_QUADS);

		gl.glColor3f(1f, 0f, 0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);

		gl.glColor3f(0f, 1f, 0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);

		gl.glColor3f(0f, 0f, 1f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);

		gl.glColor3f(1f, 1f, 0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);

		gl.glColor3f(1f, 0f, 1f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);

		gl.glColor3f(0f, 1f, 1f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		
		gl.glEnd();
	}
}
