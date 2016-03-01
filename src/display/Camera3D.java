package display;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import types.Vect3D;

public class Camera3D implements KeyListener
{
	final public Vect3D position;
	final public Vect3D angles;
	final public Vect3D oldMousePosition;
	public double speed;
	
	Camera3D(Vect3D position, Vect3D angles, double speed)
	{
		// position : position of the camera
		// angles : orientation of the camera following the x, y and z axis
		
		this.position = position.clone();
		this.angles = angles.clone();
		this.oldMousePosition = new Vect3D(0, 0, 0);
		this.speed = speed;
	}
	
	public void setPosition(Vect3D position)
	{
		// Set the position of the camera
		// position : new position of the camera
		
		this.position.x = position.x;
		this.position.y = position.y;
		this.position.z = position.z;
	}
	
	public void setAngles(Vect3D angles)
	{
		// Set the orientation of the camera
		// angles : new orientation of the camera
		
		this.angles.x = angles.x;
		this.angles.y = angles.y;
		this.angles.z = angles.z;
	}
	
	public void move(Vect3D movement)
	{
		// Move the camera over a given distance
		// movement : distance of the current position
		
		double angleRad = Math.toRadians(angles.z);
		double cosa = Math.cos(angleRad);
		double sina = Math.sin(angleRad);

		position.x += movement.x * cosa - movement.y * sina;
		position.y += movement.x * sina + movement.y * cosa;
		position.z += movement.z;
	}
	
	public void focus(Vect3D pointPosition)
	{
		// Set the orientation of the camera to focus a point of the space
		// pointPosition : focused point position
		
		Vect3D vectorDifference = new Vect3D(pointPosition.x - position.x,
				pointPosition.y - position.y, pointPosition.z - position.z);
		
		angles.x = (float)Math.toDegrees(getAnglesDifference(new Vect3D(-vectorDifference.y, Math.abs(vectorDifference.z), 0),
			new Vect3D(0, -1, 0)));
		angles.y = (float)Math.toDegrees(getAnglesDifference(new Vect3D(-vectorDifference.z, -vectorDifference.x, 0),
			new Vect3D(-1, 0, 0)));
	}

	public void keyPressed(KeyEvent event)
	{
		int key = event.getKeyCode();
		
		// Movement of the camera following X (Z and S keys) and Y (Q and D keys) axis
		if (key == KeyEvent.VK_Z)
			move(new Vect3D(0, speed, 0));
		if (key == KeyEvent.VK_S)
			move(new Vect3D(0, -speed, 0));
		if (key == KeyEvent.VK_Q)
			move(new Vect3D(-speed, 0, 0));
		if (key == KeyEvent.VK_D)
			move(new Vect3D(speed, 0, 0));
		
		// Set of the orientation of the camera following X (up and down keys) and Y (left and right keys) axis
		if (key == KeyEvent.VK_UP)
			angles.x += 1;
		if (key == KeyEvent.VK_DOWN)
			angles.x -= 1;
		if (key == KeyEvent.VK_LEFT)
			angles.z += 1;
		if (key == KeyEvent.VK_RIGHT)
			angles.z -= 1;
	}

	public void keyReleased(KeyEvent event) 
	{
	}

	public void keyTyped(KeyEvent event)
	{		
	}
	
	private float getAnglesDifference(Vect3D vector1, Vect3D vector2)
	{
		// Return the angle beetween vector1 and vector2
		
		double dot = vector1.x * vector2.x + vector1.y * vector2.y;
		double det = vector1.x * vector2.y - vector1.y * vector2.x;
		return (float)(-Math.atan2(det, dot) + Math.PI);
	}
}
