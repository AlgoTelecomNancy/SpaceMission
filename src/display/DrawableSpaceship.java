package display;


import java.util.ArrayList;

import maths.Vect3D;
import maths.VectRotation;
import physics.Body;
import physics.BodySuperClass;


public class DrawableSpaceship {

	private BodySuperClass spaceship;
	private ArrayList<Body> addedChildBodies;
	private ArrayList<Cube> addedChildBodiesCubes;
	private ArrayList<Line> addedChildBodiesForceLine;
	private ArrayList<ArrayList<Line>> addedChildBodiesLinks;
	private Window window;


	public DrawableSpaceship(BodySuperClass spaceship, Window window) {

		this.spaceship = spaceship;
		this.addedChildBodies = new ArrayList<Body>();
		this.addedChildBodiesCubes = new ArrayList<Cube>();
		this.addedChildBodiesForceLine = new ArrayList<Line>();
		this.addedChildBodiesLinks = new ArrayList<ArrayList<Line>>();
		this.window = window;
	}

	public BodySuperClass getSpaceship() {
		return spaceship;
	}

	private Line getForceLine(Body body) {
		Vect3D vertex1 = body.getAbsolutePosition();
		Vect3D vertex2 = body.getAbsolutePosition().add(VectRotation.rotate(body.getForce().getNormalized().mult(-1), body.getAbsoluteRotPosition()));

		//System.out.println(body.getForce());

		return new Line(vertex1, vertex2, new Vect3D(1, 1, 1));
	}

	public void updateSpaceship() {

		for (int i = 0; i < spaceship.getAllTerminalBodies().size(); ++i) {
			Body child = spaceship.getAllTerminalBodies().get(i);

			if (addedChildBodies.contains(child)) {
				addedChildBodiesCubes.get(i).setPosition(child.getAbsolutePosition());
				addedChildBodiesCubes.get(i).setAngles(child.getAbsoluteRotPosition());
				addedChildBodiesCubes.get(i).setSize((new Vect3D(1, 1, 1)).mult(child.getRadius() * 2));
				addedChildBodiesForceLine.get(i).setVertex1(getForceLine(child).getVertex1());
				addedChildBodiesForceLine.get(i).setVertex2(getForceLine(child).getVertex2());
				
				for (Line line : addedChildBodiesLinks.get(i)) {
					window.getDisplay().removeDrawableObject(line);
				}
				for (Body attachedBody : child.getAttachedBodies()) {
					addedChildBodiesLinks.get(i).add(new Line(child.getAbsolutePosition(), attachedBody.getAbsolutePosition(), new Vect3D(0, 1, 1)));
					window.getDisplay().addDrawableObject(addedChildBodiesLinks.get(i).get(addedChildBodiesLinks.get(i).size() - 1));
				}
			}
			else {
				Cube cube = new Cube(child.getAbsolutePosition(),
						child.getAbsoluteRotPosition(),
						(new Vect3D(1, 1, 1)).mult(child.getRadius() * 2));
				addedChildBodies.add(child);
				addedChildBodiesCubes.add(cube);
				window.getDisplay().addDrawableObject(cube);

				Line force = getForceLine(child);
				addedChildBodiesForceLine.add(force);
				window.getDisplay().addDrawableObject(force);

				addedChildBodiesLinks.add(new ArrayList<Line>());
				for (Body attachedBody : child.getAttachedBodies()) {
					addedChildBodiesLinks.get(addedChildBodiesLinks.size() - 1).add(new Line(child.getAbsolutePosition(), attachedBody.getAbsolutePosition(), new Vect3D(0, 1, 1)));
					window.getDisplay().addDrawableObject(addedChildBodiesLinks.get(addedChildBodiesLinks.size() - 1).get(addedChildBodiesLinks.get(addedChildBodiesLinks.size() - 1).size() - 1));
				}
			}
		}

		for (int i = addedChildBodies.size() - 1; i >= 0; --i) {
			Body child = addedChildBodies.get(i);

			if (!spaceship.getAllTerminalBodies().contains(child)) {
				window.getDisplay().removeDrawableObject(addedChildBodiesCubes.get(i));
				addedChildBodies.remove(i);
				addedChildBodiesCubes.remove(i);
				window.getDisplay().removeDrawableObject(addedChildBodiesForceLine.get(i));
				addedChildBodiesForceLine.remove(i);
				
				for (Line line : addedChildBodiesLinks.get(i)) {
					window.getDisplay().removeDrawableObject(line);
				}
				addedChildBodiesLinks.remove(i);
			}
		}
	}
}

