package physics;

import java.util.ArrayList;

import maths.Matrix;
import maths.Vect3D;


/**
 * 
 * Body représente une sphère et gère la collision et les déplacements du corps représenté de manière récursive. Le
 * corps le plus général calcule une colision possible avec un autre corps général. Chaque corps calcule sa taille en
 * fonction des corps sous-jacents
 * 
 * @author romaricportable
 *
 */
public class Body {


	private Body parent;
	private ArrayList<Body> children = new ArrayList<Body>();

	private double radius = 0;
	private double mass = 0;

	private Vect3D position = new Vect3D(); //Position relative to the parent body	
	private Vect3D speed = new Vect3D();
	private Vect3D acceleration = new Vect3D();

	private Vect3D rotPosition = new Vect3D(); //Position relative to the parent body	
	private Vect3D rotSpeed = new Vect3D();
	private Vect3D rotAcceleration = new Vect3D();

	private Vect3D force = new Vect3D();
	private Vect3D moment = new Vect3D();


	public Body() {

	}

	public void addChild(Body child) {
		child.parent = this;
		this.children.add(child);

		updateProperties(); //Changer la taille car on a un nouvel enfant
	}

	public void destroyChild(Body child) {
		this.children.remove(child);
	}

	/*
	 * Destruction du corps
	 */
	public void die() {
		if (this.parent != null) {
			this.parent.destroyChild(this);
			this.parent.updateProperties(); //Changer la taille du parent car on n'existe plus
		}
	}

	public void setRadius(double newRadius) {
		this.radius = newRadius;
		if (this.parent != null) {
			this.parent.updateProperties(); //Changer la taille du parent car on n'a plus la même taille
		}
	}

	public void setMass(double newMass) {
		this.mass = newMass;
		if (this.parent != null) {
			this.parent.updateProperties(); //Changer la taille du parent car on n'a plus la même taille
		}
	}

	public void setPosition(Vect3D newPosition) {
		this.position = newPosition;
	}

	public double getRadius() {
		return this.radius;
	}

	public double getMass() {
		return this.mass;
	}

	public Vect3D getPosition() {
		return this.position;
	}

	public Vect3D getRotPosition() {
		return this.rotPosition;
	}

	private void updateProperties() {

		//Update mass
		double newMass = 0;
		for (Body child : this.children) {
			newMass += child.getMass();
		}
		this.mass = newMass;

		//Update position
		//The current position will be changed to the gravity center
		Vect3D gravityCenterDelta = new Vect3D();
		double massSum = 0;
		for (Body child : this.children) {
			gravityCenterDelta = gravityCenterDelta.add(
					child.getPosition().mult(child.getMass()));
			massSum += child.getMass();
		}
		gravityCenterDelta = gravityCenterDelta.mult(1 / massSum);
		this.position = this.position.minus(gravityCenterDelta); //Change position to have gravity center equal to 0


		//Update global radius
		//TODO improve this to have the minimum sphere that fit the body
		// (actualy this is the minimum sphere that fit AND having a center
		// equal to the barycentre center)
		double newRadius = 0;
		for (Body child : this.children) {
			newRadius = Math.max(
					newRadius,
					(child.getPosition().minus(this.getPosition())).size() + child.getRadius());
		}
		this.radius = newRadius;


		//Update force at barycenter
		this.force = new Vect3D();
		this.moment = new Vect3D();
		for (Body child : this.children) {
			this.force = this.force.add(child.getForce()); //Add forces
			this.moment = this.moment.add( //Add all moments
					child.moment.add( //Moment on the child
							child.getForce().vectProd( //Force on the child ^
									this.position.minus(child.position) //Distance parent-child
							)));
		}


		if (this.parent != null) {
			this.parent.updateProperties(); //Changer la taille du parent car on n'a plus la même taille
		}

	}

	/**
	 * update position and rotation after a delta time ONLY FOR THE PARENT
	 * 
	 * @param deltaTime
	 */
	public void updateState(double deltaTime) {

		if (this.parent == null) {

			Vect3D deltaPosition = this.speed.mult(deltaTime);
			Vect3D deltaRotPosition = this.rotSpeed.mult(deltaTime);

			Vect3D deltaSpeed = this.acceleration.mult(deltaTime);
			Vect3D deltaRotSpeed = this.rotAcceleration.mult(deltaTime);

			Vect3D deltaAcceleration = this.force.mult(1 / this.mass);
			Vect3D deltaRotAcceleration = this.force.mult(1 / this.mass);

			this.position = this.position.add(deltaPosition);
			this.rotPosition = this.rotPosition.add(deltaRotPosition);

			this.speed = this.speed.add(deltaSpeed);
			this.rotSpeed = this.rotSpeed.add(deltaRotSpeed);

			this.acceleration = this.acceleration.add(deltaAcceleration);
			this.rotAcceleration = this.rotAcceleration.add(deltaRotAcceleration);

			for (Body child : this.children) {
				child.updatePosition(this.position, this.rotPosition, deltaRotPosition);
			}

		}

	}

	public void updatePosition(Vect3D parentPosition, Vect3D parentRotPosition, Vect3D parentDeltaRotPosition) {

		Matrix rotateMatrix = rotMatrixModule_Space(parentRotPosition);

		this.rotPosition = this.rotPosition.add(parentDeltaRotPosition);

		this.position = rotateMatrix.multiply(parentPosition.minus(this.position));
		this.position.add(parentPosition);

		for (Body child : this.children) {
			child.updatePosition(parentPosition, parentRotPosition, parentDeltaRotPosition);
		}

	}

	public Vect3D getForce() {
		return force;
	}

	public void setForce(Vect3D force) {
		this.force = force;
	}

	public Vect3D getMoment() {
		return moment;
	}

	public void getMoment(Vect3D moment) {
		this.moment = moment;
	}

	/**
	 * get children
	 * 
	 */
	public ArrayList<Body> getChildren() {
		return this.children;
	}

	/**
	 * Compute the rotation matrix between the spaceship and the space
	 * 
	 * @return The rotation matrix between the spaceship and the space
	 */
	public Matrix rotMatrixModule_Space(Vect3D orientation) {
		Matrix rotx = new Matrix();
		rotx.setCoef(1, 1, 1);
		rotx.setCoef(2, 2, Math.cos(orientation.x));
		rotx.setCoef(3, 3, Math.cos(orientation.x));
		rotx.setCoef(2, 3, -Math.sin(orientation.x));
		rotx.setCoef(3, 2, Math.sin(orientation.x));

		Matrix roty = new Matrix();
		roty.setCoef(2, 2, 1);
		roty.setCoef(1, 1, Math.cos(orientation.y));
		roty.setCoef(3, 3, Math.cos(orientation.y));
		roty.setCoef(1, 3, Math.sin(orientation.y));
		roty.setCoef(3, 1, -Math.sin(orientation.y));

		Matrix rotz = new Matrix();
		rotz.setCoef(3, 3, 1);
		rotz.setCoef(1, 1, Math.cos(orientation.z));
		rotz.setCoef(2, 2, Math.cos(orientation.z));
		rotz.setCoef(1, 2, -Math.sin(orientation.z));
		rotz.setCoef(2, 1, Math.sin(orientation.z));

		return Matrix.multiply(Matrix.multiply(rotz, roty), rotx);
	}

}
