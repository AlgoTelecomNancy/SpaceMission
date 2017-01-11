package display;

import java.util.Vector;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;


public class Display3D implements GLEventListener {
	private GLU glu = new GLU();
	private Camera3D camera;
	private Window window;
	private Vector<DrawableObject> drawableObjects;
	private int focusedObject;


	Display3D(Camera3D camera, Window window) {
		this.camera = camera;
		this.window = window;
		this.drawableObjects = new Vector<>();
		focusedObject = -1;
	}

	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		window.updateDisplay();

		if (focusedObject > -1)
			camera.setFocusedPoint(getDrawableObject(focusedObject).getPosition());

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		for (int i = 0; i < drawableObjects.size(); ++i)
			drawableObjects.get(i).draw(drawable, camera);
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
		
		window.initDisplay();
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

	public int getObjectCount() {
		return drawableObjects.size();
	}

	public void addDrawableObject(DrawableObject cube) {
		drawableObjects.add(cube);
	}

	public DrawableObject getDrawableObject(int cubeId) {
		if (cubeId >= 0 && cubeId < getObjectCount())
			return drawableObjects.get(cubeId);
		return null;
	}

	public void removeDrawableObject(DrawableObject object) {
		drawableObjects.remove(object);
	}

	public void setFocusedObject(int cubeId) {
		if (cubeId >= 0 && cubeId < getObjectCount())
			focusedObject = cubeId;
		else
			focusedObject = -1;
	}	
}