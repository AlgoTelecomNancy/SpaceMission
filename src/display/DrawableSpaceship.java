package display;

import java.util.ArrayList;

import maths.Vect3D;
import physics.Body;

public class DrawableSpaceship {
	
	private Body spaceship;
	private ArrayList<Body> addedChildBodies;
	private ArrayList<Cube> addedChildBodiesCubes;
	private Window window;
	
	public DrawableSpaceship(Body spaceship, Window window) {
		
		this.spaceship = spaceship;
		this.addedChildBodies = new ArrayList<Body>();
		this.addedChildBodiesCubes = new ArrayList<Cube>();
		this.window = window;
	}
	
	public void updateSpaceship() {

		for (int i = 0; i < spaceship.getAllTerminalBodies().size(); ++i) {
			Body child = spaceship.getAllTerminalBodies().get(i);
			
			if (addedChildBodies.contains(child)){
				addedChildBodiesCubes.get(i).setPosition(child.getAbsolutePosition());
				addedChildBodiesCubes.get(i).setAngles(child.getAbsoluteRotPosition());
				addedChildBodiesCubes.get(i).setSize((new Vect3D(1, 1, 1)).mult(child.getRadius() * 2));
			}
			else {
				Cube cube = new Cube(child.getAbsolutePosition(),
						child.getAbsoluteRotPosition(),
						(new Vect3D(1, 1, 1)).mult(child.getRadius() * 2));
				addedChildBodies.add(child);
				addedChildBodiesCubes.add(cube);
				window.getDisplay().addCube(cube);
			}
		}
	}
}
