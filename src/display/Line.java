package display;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

import maths.Vect3D;

public class Line implements DrawableObject {

	private Vect3D vertex1;
	private Vect3D vertex2;
	private GLU glu = new GLU();
	
	public Line(Vect3D vertex1, Vect3D vertex2){
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
	}
	
	public Vect3D getVertex1() {
		return vertex1;
	}

	public void setVertex1(Vect3D vertex1) {
		this.vertex1 = vertex1;
	}

	public Vect3D getVertex2() {
		return vertex2;
	}

	public void setVertex2(Vect3D vertex2) {
		this.vertex2 = vertex2;
	}
	
	public Vect3D getPosition() {
		return vertex1.add(vertex2).mult(0.5);
	}
	
	public void draw(GLAutoDrawable drawable, Camera3D camera) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glLoadIdentity();
		glu.gluLookAt(camera.position.x, camera.position.y, camera.position.z, camera.focusedPoint.x,
				camera.focusedPoint.y, camera.focusedPoint.z, 0, 0, 1);
		
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f(1f, 1f, 1f);
		gl.glVertex3d(vertex1.x, vertex1.y, vertex1.z);
		gl.glVertex3d(vertex2.x, vertex2.y, vertex2.z);
		gl.glEnd();
	}
}
