package display;

import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import maths.Vect3D;


public class Window {
	final private GLProfile profile;
	final private GLCapabilities capabilities;
	final private GLCanvas glcanvas;
	final private JFrame frame;
	final FPSAnimator animator;

	Display3D display;
	Camera3D camera;


	public Window(int fps) {
		camera = new Camera3D(new Vect3D(0, 0, 0), new Vect3D(0, 0, 0), 1.f);
		display = new Display3D(camera, this);

		// Initialization of OpenGL
		profile = GLProfile.get(GLProfile.GL2);
		capabilities = new GLCapabilities(profile);

		// Initialization of the canvas
		capabilities.setDepthBits(16);
		glcanvas = new GLCanvas(capabilities);
		glcanvas.addGLEventListener(display);
		glcanvas.setSize(800, 600);

		// Initialization of the frame
		frame = new JFrame(" Basic Frame");

		// Add of the canvas to the frame
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);
		frame.addKeyListener((KeyListener) camera);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Initialization of the 3D animator
		animator = new FPSAnimator(glcanvas, fps, true);
		animator.start();
	}
	
	public Window() {
		camera = new Camera3D(new Vect3D(0, 0, 0), new Vect3D(0, 0, 0), 1.f);
		display = new Display3D(camera, this);

		// Initialization of OpenGL
		profile = GLProfile.get(GLProfile.GL2);
		capabilities = new GLCapabilities(profile);

		// Initialization of the canvas
		capabilities.setDepthBits(16);
		glcanvas = new GLCanvas(capabilities);
		glcanvas.addGLEventListener(display);
		glcanvas.setSize(800, 600);

		// Initialization of the frame
		frame = new JFrame(" Basic Frame");

		// Add of the canvas to the frame
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);
		frame.addKeyListener((KeyListener) camera);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Initialization of the 3D animator
		animator = new FPSAnimator(glcanvas, 60, true);
		animator.start();
	}

	public Display3D getDisplay() {
		return display;
	}

	public Camera3D getCamera() {
		return camera;
	}

	public boolean isOpen() {
		return frame.isActive();
	}
	
	public void initDisplay() {
		
	}
	
	public void updateDisplay() {
		
	}
}
