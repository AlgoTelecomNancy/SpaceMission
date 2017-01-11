package physics;

import java.util.ArrayList;

import maths.Vect3D;

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
	public Vect3D absoluteRotSpeed = new Vect3D(); //Axis of acceleration ! (not euleur angles)
	public Vect3D absoluteRotAcceleration = new Vect3D(); //Axis of acceleration ! (not euleur angles)
	
	public Vect3D getSpeed(){
		return absoluteSpeed;
	}
	
	public Vect3D getRotSpeed(){
		return absoluteRotSpeed;
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
	
	protected BodySuperClass getParentBody(){ return null; }
	
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
	
}
