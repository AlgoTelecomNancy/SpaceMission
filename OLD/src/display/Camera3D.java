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
	final public Vect3D focusedPoint;
	public double speed;
	
	Camera3D(Vect3D position, Vect3D focusedPoint, double speed)
	{
		// position : position of the camera
		// angles : orientation of the camera following the x, y and z axis
		
		this.position = position.clone();
		this.focusedPoint = focusedPoint.clone();
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
	
	public void setFocusedPoint(Vect3D focusedPoint)
	{
		// Set the focused point position of the camera
		// focusedPoint : new focused point position of the camera
		
		this.focusedPoint.x = focusedPoint.x;
		this.focusedPoint.y = focusedPoint.y;
		this.focusedPoint.z = focusedPoint.z;
	}
	
	public void move(Vect3D movement)
	{
		// Move the camera over a given distance
		// movement : distance of the current position
		
		double angleRad = /*Math.toRadians(angles.z)*/0;
		double cosa = Math.cos(angleRad);
		double sina = Math.sin(angleRad);

		position.x += movement.x * cosa - movement.y * sina;
		position.y += movement.x * sina + movement.y * cosa;
		position.z += movement.z;
	}

	public void keyPressed(KeyEvent event)
	{
		int key = event.getKeyCode();
		
		// Movement of the camera following X (Z and S keys), Y (Q and D keys) and Z (A and E keys) axis
		if (key == KeyEvent.VK_Z)
			move(new Vect3D(0, speed, 0));
		if (key == KeyEvent.VK_S)
			move(new Vect3D(0, -speed, 0));
		if (key == KeyEvent.VK_Q)
			move(new Vect3D(-speed, 0, 0));
		if (key == KeyEvent.VK_D)
			move(new Vect3D(speed, 0, 0));
		if (key == KeyEvent.VK_A)
			move(new Vect3D(0, 0, -speed));
		if (key == KeyEvent.VK_E)
			move(new Vect3D(0, 0, speed));
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
