package display;
import java.nio.FloatBuffer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

import javafx.beans.binding.FloatBinding;
import types.Matrix;
import types.Vect3D;

public class Cube
{
	final public Vect3D position;
	final public Vect3D size;
	final public Vect3D angles;
	private GLU glu = new GLU();
	
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
	
	private Vect3D getRotatedVector(Vect3D vector, Vect3D angles)
	{
		Vect3D newVector = vector.clone();
		
		Vect3D oldVector = vector.clone();
		newVector.y = (float)(oldVector.y * Math.cos(angles.x * Math.PI / 180) - oldVector.z * Math.sin(angles.x * Math.PI / 180));
		newVector.z = (float)(oldVector.y * Math.sin(angles.x * Math.PI / 180) + oldVector.z * Math.cos(angles.x * Math.PI / 180));
		
		oldVector = newVector.clone();
		newVector.z = (float)(oldVector.z * Math.cos(angles.y * Math.PI / 180) - oldVector.x * Math.sin(angles.y * Math.PI / 180));
		newVector.x = (float)(oldVector.z * Math.sin(angles.y * Math.PI / 180) + oldVector.x * Math.cos(angles.y * Math.PI / 180));
		
		oldVector = newVector.clone();
		newVector.x = (float)(oldVector.x * Math.cos(angles.z * Math.PI / 180) - oldVector.y * Math.sin(angles.z * Math.PI / 180));
		newVector.y = (float)(oldVector.x * Math.sin(angles.z * Math.PI / 180) + oldVector.y * Math.cos(angles.z * Math.PI / 180));
		
		return newVector;
	}
	
	public void draw(GLAutoDrawable drawable, Camera3D camera)
	{
		GL2 gl = drawable.getGL().getGL2();		
		
		/*Vect3D rotations = getRotations(angles);
		
		gl.glLoadIdentity();		
		
		/*gl.glMultMatrixf(FloatBuffer.wrap(new float[]{
				1, 0, 0, 0,
				0, (float)Math.cos(-rotations.x), -(float)Math.sin(-rotations.x), 0,
				0, (float)Math.sin(-rotations.x), (float)Math.cos(-rotations.x), 0,
				0, 0, 0, 1}));*/
		
		/*gl.glMultMatrixf(FloatBuffer.wrap(new float[]{
				1, 0, 0, 0,
				0, (float)Math.cos(-rotations.x), (float)Math.sin(-rotations.x), 0,
				0, -(float)Math.sin(-rotations.x), (float)Math.cos(-rotations.x), 0,
				0, 0, 0, 1}));*/
				
				
				
		/*gl.glMultMatrixf(new float[]{
				(float)Math.cos(-rotations.y), 0, (float)Math.sin(-rotations.y), 0,
				0, 1, 0, 0,
				-(float)Math.sin(-rotations.y), 0, (float)Math.cos(-rotations.y), 0,
				0, 0, 0, 1}, 0);
		gl.glMultMatrixf(new float[]{
				(float)Math.cos(-rotations.z), -(float)Math.sin(-rotations.z), 0, 0,
				(float)Math.sin(-rotations.z), (float)Math.cos(-rotations.z), 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1}, 0);*/
		/*gl.glRotatef(-(float)rotations.x, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(-(float)rotations.y, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(-(float)rotations.z, 0.0f, 0.0f, 1.0f);*/
		
		/*gl.glMultMatrixf(FloatBuffer.wrap(new float[]{
			1, 0, 0, 0,
			0, (float)Math.cos(Math.toRadians(angles.x)), -(float)Math.sin(Math.toRadians(angles.x)), 0,
			0, (float)Math.sin(Math.toRadians(angles.x)), (float)Math.cos(Math.toRadians(angles.x)), 0,
			0, 0, 0, 1}));

		gl.glMultMatrixf(FloatBuffer.wrap(new float[]{
				(float)Math.cos(Math.toRadians(angles.y)), 0, (float)Math.sin(Math.toRadians(angles.y)), 0,
				0, 1, 0, 0,
				-(float)Math.sin(Math.toRadians(angles.y)), 0, (float)Math.cos(Math.toRadians(angles.y)), 0,
				0, 0, 0, 1}));
		
		gl.glMultMatrixf(FloatBuffer.wrap(new float[]{
				(float)Math.cos(Math.toRadians(angles.z)), -(float)Math.sin(Math.toRadians(angles.z)), 0, 0,
				(float)Math.sin(Math.toRadians(angles.z)), (float)Math.cos(Math.toRadians(angles.z)), 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1}));*/
		
		gl.glLoadIdentity();		
		glu.gluLookAt(camera.position.x, camera.position.y, camera.position.z, camera.focusedPoint.x, camera.focusedPoint.y, camera.focusedPoint.z, 0, 0, 1);
		
		gl.glTranslatef((float)position.x, (float)position.y, (float)position.z);
		
		Vect3D axis = getRotatedVector(new Vect3D(1, 0, 0), new Vect3D(0, 0, 0));
		gl.glRotated(angles.x, axis.x, axis.y, axis.z);
		axis = getRotatedVector(new Vect3D(0, 1, 0), new Vect3D(-angles.x, 0, 0));
		gl.glRotated(angles.y, axis.x, axis.y, axis.z);
		axis = getRotatedVector(new Vect3D(0, 0, 1), new Vect3D(-angles.x, -angles.y, 0));
		gl.glRotated(angles.z, axis.x, axis.y, axis.z);
		
		
		/*gl.glRotated(angles.x, 1, 0, 0);
		gl.glRotated(angles.y, 0, 1, 0);
		gl.glRotated(angles.z, 0, 0, 1);*/
		
		gl.glScaled(size.x/2, size.y/2, size.z/2);
		
		
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
