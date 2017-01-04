package physics;

import java.util.ArrayList;

import maths.Matrix;
import maths.Vect3D;
import maths.VectRotation;


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

	private Vect3D rotPosition = new Vect3D(); //RotPosition relative to the parent body	
	private Vect3D rotSpeed = new Vect3D();
	private Vect3D rotAcceleration = new Vect3D();

	private Vect3D force = new Vect3D();
	private Vect3D moment = new Vect3D();


	public Body() {

	}

	public void addChild(Body child) {
		child.parent = this;
		this.children.add(child);
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
		}
	}

	public void setRadius(double newRadius) {
		this.radius = newRadius;
	}

	public void setMass(double newMass) {
		this.mass = newMass;
	}

	public void setPosition(Vect3D newPosition) {
		this.position = newPosition;
	}
	
	public void setRotPosition(Vect3D newRotPosition){
		this.rotPosition = newRotPosition;
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

	public void updateProperties() {
		

		//Update mass
		if(this.children.size()>0){
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
						(child.getPosition()).mult(child.getMass()));
				massSum += child.getMass();
			}
	
			if(massSum!=0){
				gravityCenterDelta = gravityCenterDelta.mult(1/massSum);
			}
			this.position = this.position.add(gravityCenterDelta); //Change position to have gravity center equal to 0
			if(gravityCenterDelta.size()!=0){
				for (Body child : this.children) {
					child.position = child.getPosition().minus(gravityCenterDelta);
				}
			}
			
			//Update global radius
			//TODO improve this to have the minimum sphere that fit the body
			// (actualy this is the minimum sphere that fit AND having a center
			// equal to the barycentre center)
			double newRadius = 0;
			for (Body child : this.children) {
				newRadius = Math.max(
						newRadius,
						(child.getPosition()).size() + child.getRadius());
			}
			this.radius = newRadius;
	
	
			//Update force at barycenter
			this.force = new Vect3D();
	
			this.moment = new Vect3D();		
			for(Body child : this.children){
				
				Vect3D childRelativeRotation = child.getRotPosition();
				Vect3D childRotatedForce = VectRotation.rotate(child.getForce(), childRelativeRotation); //We have to rotate the force because it is relative to the child referential
				
				
				this.force = this.force.add(childRotatedForce); //Add forces
				this.moment = this.moment.add( //Add all moments
						child.moment.add( //Moment on the child
								childRotatedForce.vectProd( //Force on the child ^
										child.position //Distance parent-child
								)));
			}

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

		if (this.parent == null) { //Seulement le parent principal
 
			Vect3D deltaPosition = this.speed.mult(deltaTime);
			Vect3D deltaRotPosition = this.rotSpeed.mult(deltaTime);

			Vect3D deltaSpeed = this.acceleration.mult(deltaTime);
			Vect3D deltaRotSpeed = this.rotAcceleration.mult(deltaTime);

			this.position = this.position.add(deltaPosition);
			this.rotPosition = this.rotPosition.add(deltaRotPosition);

			this.speed = this.speed.add(deltaSpeed);
			this.rotSpeed = this.rotSpeed.add(deltaRotSpeed);


			this.acceleration = this.force.mult(1 / this.mass);
			this.rotAcceleration = this.moment.mult(1 / this.mass);

		}

	}

	public Vect3D getAbsolutePosition() {
		
		if(this.parent==null){
			return this.position;
		}else{
			
			return (VectRotation.rotate(this.position,this.parent.getAbsoluteRotPosition())).add(this.parent.getAbsolutePosition());
			
		}

	}

	public Vect3D getAbsoluteRotPosition() {
		
		if(this.parent==null){
			return this.rotPosition;
		}else{
			return this.parent.getAbsoluteRotPosition().add(this.rotPosition);
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

	public void setMoment(Vect3D moment) {
		this.moment = moment;
	}

	/**
	 * get children
	 * 
	 */
	public ArrayList<Body> getChildren() {
		return this.children;
	}

}
