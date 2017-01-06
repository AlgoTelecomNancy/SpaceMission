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
	private boolean propertiesLock = false;

	private double radius = 1;
	private double mass = 1;

	//Position is local and global if no parent
	private Vect3D position = new Vect3D(); //Position relative to the parent body	
	private Vect3D rotPosition = new Vect3D(); //RotPosition relative to the parent body
	
	//Force is local
	private Vect3D force = new Vect3D();
	private Vect3D moment = new Vect3D();
	
	//Acceleration and speed are absolute (space axis)
	public Vect3D absoluteSpeed = new Vect3D();
	public Vect3D absoluteAcceleration = new Vect3D();
	public Vect3D absoluteRotSpeed = new Vect3D();
	public Vect3D absoluteRotAcceleration = new Vect3D();


	public Body() {

	}

	public void addChild(Body child) {
		child.parent = this;
		this.children.add(child);
		this.updateProperties();
	}

	public void destroyChild(Body child) {
		this.children.remove(child);
		this.updateProperties();
	}
	
	/*
	 * Détacher le corps
	 */
	public Body detach(){
		
		this.position = this.getAbsolutePosition();
		this.rotPosition = this.getAbsoluteRotPosition();
		
		Vect3D old_barycenter = this.parent.getAbsolutePosition();
		
		this.parent.destroyChild(this);
		
		Vect3D new_barycenter = this.parent.getAbsolutePosition().minus(old_barycenter).mult(-1);

		
		
		//Recalculer les données d'accélération et vitesse du corps parent

		Vect3D new_absoluteSpeed = this.parent.absoluteSpeed.add(
				this.parent.absoluteRotSpeed.vectProd(VectRotation.rotate(new_barycenter, this.getAbsoluteRotPosition()))
				);
		Vect3D new_absoluteRotSpeed = this.parent.absoluteRotSpeed.clone();

		Vect3D new_absoluteAcceleration = this.parent.absoluteAcceleration.clone();
		if(this.position.size()>0){
			new_absoluteAcceleration = this.parent.absoluteAcceleration.add(new_barycenter.mult(
						Math.pow(this.parent.absoluteRotSpeed.vectProd(new_barycenter).size(),2)
						/new_barycenter.size()
					));
		}
		Vect3D new_absoluteRotAcceleration = this.parent.absoluteRotAcceleration.clone();

		this.parent.absoluteSpeed = new_absoluteSpeed;
		this.parent.absoluteRotSpeed = new_absoluteRotSpeed;
		this.parent.absoluteAcceleration = new_absoluteAcceleration;
		this.parent.absoluteRotAcceleration = new_absoluteRotAcceleration;

		this.parent = null;
		return this;
	}

	public void setRadius(double newRadius) {
		this.radius = newRadius;
		this.updateProperties();
	}

	public void setMass(double newMass) {
		this.mass = newMass;
		this.updateProperties();
	}

	public void setPosition(Vect3D newPosition) {
		this.position = newPosition;
		this.updateProperties();
	}
	
	public void setRotPosition(Vect3D newRotPosition){
		this.rotPosition = newRotPosition;
		this.updateProperties();
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
		
		if(propertiesLock){
			return;
		}

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
			this.position = this.position.add(VectRotation.rotate(gravityCenterDelta,this.rotPosition)); //Change position to have gravity center equal to 0
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
 
			Vect3D deltaPosition = this.absoluteSpeed.mult(deltaTime);
			Vect3D deltaRotPosition = this.absoluteRotSpeed.mult(deltaTime);

			Vect3D deltaSpeed = this.absoluteAcceleration.mult(deltaTime);
			Vect3D deltaRotSpeed = this.absoluteRotAcceleration.mult(deltaTime);

			this.position = this.position.add(deltaPosition);
			this.rotPosition = this.rotPosition.add(deltaRotPosition);

			this.absoluteSpeed = this.absoluteSpeed.add(deltaSpeed);
			this.absoluteRotSpeed = this.absoluteRotSpeed.add(deltaRotSpeed);

			this.absoluteAcceleration = VectRotation.rotate(this.force.mult(1 / this.mass), this.getAbsoluteRotPosition());
			this.absoluteRotAcceleration = this.moment.mult(-1 / this.mass);

		}else{
			//Children set current speed and acceleration
			
			//Absolute speed if parent exists =
			// parentSpeed + ParentChildVector ^ parentRotSpeed
			this.absoluteSpeed = parent.absoluteSpeed.add(
					parent.absoluteRotSpeed.vectProd(VectRotation.rotate(this.position, this.getAbsoluteRotPosition()))
					);
			this.absoluteRotSpeed = parent.absoluteRotSpeed.clone();

			if(this.position.size()>0){
			this.absoluteAcceleration = parent.absoluteAcceleration.add(this.position.mult(
						Math.pow(parent.absoluteRotSpeed.vectProd(this.position).size(),2)
						/this.position.size()
					));
			}
			this.absoluteRotAcceleration = parent.absoluteRotAcceleration.clone();
			
		}
		
		for(Body child: this.children){
			child.updateState(deltaTime);
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
		this.updateProperties();
	}

	public Vect3D getMoment() {
		return moment;
	}

	public void setMoment(Vect3D moment) {
		this.moment = moment;
		this.updateProperties();
	}

	/**
	 * get children
	 * 
	 */
	public ArrayList<Body> getChildren() {
		return this.children;
	}

	public ArrayList<Body> getAllTerminalBodies() {
		
		ArrayList<Body> res = new ArrayList<Body>();
		
		if(this.children.size()==0){
			res.add(this);
			return res;
		}
		
		for(Body child: this.children){
			for(Body el: child.getAllTerminalBodies()){
				res.add(el);
			}
		}
		
		return this.children;
	}

	/**
	 * Disable auto properties set
	 */
	public void lockProperties() {
		this.propertiesLock = true;
	}
	public void unlockProperties() {
		this.propertiesLock = false;
	}

}
