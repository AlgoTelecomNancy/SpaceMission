package physics;

import java.util.ArrayList;

import maths.Vect3D;
import maths.VectRotation;

public abstract class BodySuperClass implements BodyInterface {

	protected boolean propertiesLock = false;

	protected double radius = 1;
	protected double mass = 1;
	
	//Force is local
	protected Vect3D force = new Vect3D();
	protected Vect3D moment = new Vect3D();
	
	//Acceleration and speed are absolute (space axis)
	public Vect3D absoluteSpeed = new Vect3D();
	public Vect3D absoluteAcceleration = new Vect3D();
	public Vect3D absoluteRotSpeed = new Vect3D();
	public Vect3D absoluteRotAcceleration = new Vect3D();
	
	public Vect3D getSpeed(){
		return absoluteSpeed;
	}
	
	
	public void setRadius(double newRadius) {
		this.radius = newRadius;
		this.updateProperties();
	}

	public void setMass(double newMass) {
		this.mass = newMass;
		this.updateProperties();
	}

	public double getRadius() {
		return this.radius;
	}

	public double getMass() {
		return this.mass;
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
	
	public Vect3D getPosition(){ return new Vect3D();}
	public void setPosition(Vect3D newPosition){}
	public Vect3D getRotPosition(){ return new Vect3D();}
	public void setRotPosition(Vect3D newPosition){}
	
	private BodySuperClass getParentBody(){ return null; }
	
	/**
	 * Disable auto properties set
	 */
	public void lockProperties() {
		this.propertiesLock = true;
		for(Body child: this.getDescendants()){
			child.lockProperties();
		}
	}
	public void unlockProperties() {
		this.propertiesLock = false;
		for(Body child: this.getDescendants()){
			child.unlockProperties();
		}
	}
	
	public ArrayList<Body> getDescendants(){
		return new ArrayList<Body>();
	}
	
	public ArrayList<Body> getAllTerminalBodies(){
		return null;
	}
	
	public void updateProperties() {
		
		if(propertiesLock){
			return;
		}
		
		this.lockProperties();

		ArrayList<Body> descendants = this.getDescendants();
		
		if(descendants.size()>0){
			
			//Update mass
			double newMass = 0;
			for (Body child : descendants) {
				newMass += child.getMass();
			}
			this.mass = newMass;
	
			//Update position
			//The current position will be changed to the gravity center
			Vect3D gravityCenterDelta = new Vect3D();
			double massSum = 0;
			for (Body child : descendants) {
				gravityCenterDelta = gravityCenterDelta.add(
						(child.getPosition()).mult(child.getMass()));
				massSum += child.getMass();
			}
	
			if(massSum!=0){
				gravityCenterDelta = gravityCenterDelta.mult(1/massSum);
			}			
			this.setPosition(this.getPosition().add(VectRotation.rotate(gravityCenterDelta,this.getRotPosition()))); //Change position to have gravity center equal to 0
			if(gravityCenterDelta.size()!=0){
				for (Body child : descendants) {
					child.setPosition(child.getPosition().minus(gravityCenterDelta));
				}
			}
			
			//Update global radius
			//TODO improve this to have the minimum sphere that fit the body
			// (actualy this is the minimum sphere that fit AND having a center
			// equal to the barycentre center)
			double newRadius = 0;
			for (Body child : descendants) {
				newRadius = Math.max(
						newRadius,
						(child.getPosition()).size() + child.getRadius());
			}
			this.radius = newRadius;
	
	
			//Update force at barycenter
			this.force = new Vect3D();
	
			this.moment = new Vect3D();		
			for(Body child : descendants){
				
				Vect3D childRelativeRotation = child.getRotPosition();
				Vect3D childRotatedForce = VectRotation.rotate(child.getForce(), childRelativeRotation); //We have to rotate the force because it is relative to the child referential
				
				
				this.force = this.force.add(childRotatedForce); //Add forces
				this.moment = this.moment.add( //Add all moments
						child.moment.add( //Moment on the child
								childRotatedForce.vectProd( //Force on the child ^
										child.getPosition() //Distance parent-child
								)));
			}
			
		}
		
		this.unlockProperties();
		
		if(this.getParentBody()!=null){
			this.getParentBody().updateProperties();
		}

		
	}
	
}
