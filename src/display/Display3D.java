package display;

import java.util.Vector;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;


public class Display3D implements GLEventListener {
	private GLU glu = new GLU();
	private Camera3D camera;
	private Vector<Cube> cubes;
	private int focusedCube;


	Display3D(Camera3D camera) {
		this.camera = camera;
		this.cubes = new Vector<Cube>();
		focusedCube = -1;
	}

	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();

		if (focusedCube > -1)
			camera.setFocusedPoint(getCube(focusedCube).position);

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		for (int i = 0; i < cubes.size(); ++i)
			cubes.get(i).draw(drawable, camera);
		gl.glFlush();
	}

	public void dispose(GLAutoDrawable drawable) {
	}

	public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		if (height <= 0)
			height = 1;

		final float ratio = (float) width / (float) height;
		final GL2 gl = drawable.getGL().getGL2();

		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		glu.gluPerspective(45.0f, ratio, 0.1, 10000.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public int getCubeCount() {
		return cubes.size();
	}

	public void addCube(Cube cube) {
		cubes.add(cube);
	}

	public Cube getCube(int cubeId) {
		if (cubeId >= 0 && cubeId < getCubeCount())
			return cubes.get(cubeId);
		return null;
	}

	public void removeCube(int cubeId) {
		if (cubeId >= 0 && cubeId < getCubeCount())
			cubes.remove(cubeId);
	}

	public void setFocusedCube(int cubeId) {
		if (cubeId >= 0 && cubeId < getCubeCount())
			focusedCube = cubeId;
		else
			focusedCube = -1;
	}	
}