package display;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

import maths.Vect3D;
import maths.VectRotation;


public class Cube {
	final public Vect3D position;
	final public Vect3D size;
	final public Vect3D angles;
	private GLU glu = new GLU();


	public Cube(Vect3D position, Vect3D size, Vect3D angles) {
		this.position = position.clone();
		this.size = size.clone();
		this.angles = angles.clone();
	}

	public Cube clone() {
		return new Cube(position, size, angles);
	}

	public void setPosition(Vect3D position) {
		this.position.x = position.x;
		this.position.y = position.y;
		this.position.z = position.z;
	}

	public void setAngles(Vect3D angles) {
		this.angles.x = angles.x;
		this.angles.y = angles.y;
		this.angles.z = angles.z;
	}

	public void setSize(Vect3D size) {
		this.size.x = size.x;
		this.size.y = size.y;
		this.size.z = size.z;
	}
	
	//TODO Contain errors !
	private Vect3D getRotatedVector(Vect3D vector, Vect3D angles) {
		Vect3D newVector = vector;

		Vect3D oldVector = vector;
		newVector.y = (float) (oldVector.y * Math.cos(angles.x)
				- oldVector.z * Math.sin(angles.x));
		newVector.z = (float) (oldVector.y * Math.sin(angles.x)
				+ oldVector.z * Math.cos(angles.x));

		oldVector = newVector;
		newVector.z = (float) (oldVector.z * Math.cos(angles.y)
				- oldVector.x * Math.sin(angles.y));
		newVector.x = (float) (oldVector.z * Math.sin(angles.y)
				+ oldVector.x * Math.cos(angles.y));

		oldVector = newVector;
		newVector.x = (float) (oldVector.x * Math.cos(angles.z)
				- oldVector.y * Math.sin(angles.z));
		newVector.y = (float) (oldVector.x * Math.sin(angles.z)
				+ oldVector.y * Math.cos(angles.z));

		return newVector;
	}

	public void draw(GLAutoDrawable drawable, Camera3D camera) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glLoadIdentity();
		glu.gluLookAt(camera.position.x, camera.position.y, camera.position.z, camera.focusedPoint.x,
				camera.focusedPoint.y, camera.focusedPoint.z, 0, 0, 1);

		gl.glTranslatef((float) position.x, (float) position.y, (float) position.z);
		
		gl.glRotated(angles.z * (180/Math.PI), 0, 0, 1);
		gl.glRotated(angles.y * (180/Math.PI), 0, 1, 0);
		gl.glRotated(angles.x * (180/Math.PI), 1, 0, 0);

		gl.glScaled(size.x / 2, size.y / 2, size.z / 2);

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
