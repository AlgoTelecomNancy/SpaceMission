package physics;

import java.util.ArrayList;

import maths.Matrix;
import maths.Vect3D;
import maths.VectRotation;


/**
 * 
 * BodyGroup représente l'élément global, c'est un groupe d'éléments attachés entre eux
 * Toutes ses caractéristiques sont globales à l'espace
 * 
 * @author romaricportable
 *
 */
public class BodyGroup extends BodySuperClass {

	private ArrayList<Body> elements = new ArrayList<Body>();

	//Position is global if no parent
	private Vect3D position = new Vect3D(); //Position relative to the parent body	
	private Vect3D rotPosition = new Vect3D(); //RotPosition relative to the parent body



	public BodyGroup() {	}
	
	/*
	 * Add and remove Bodies
	 */
	public void addBody(Body child) {
		child.setGroup(this);
		this.elements.add(child);
		
		this.updateProperties();
	}
	public void removeBody(Body child) {
		this.elements.remove(child);
		
		this.updateProperties();
	}
	public Body newBody() {
		Body child = new Body();
		
		child.setGroup(this);
		this.elements.add(child);
		
		this.updateProperties();
		
		return child;
	}
	

	public Vect3D getPosition() {
		return this.position;
	}

	public Vect3D getRotPosition() {
		return this.rotPosition;
	}
	
	public void setPosition(Vect3D newPosition) {
		this.position = newPosition;
		this.updateProperties();
	}
	
	public void setRotPosition(Vect3D newRotPosition){
		this.rotPosition = newRotPosition;
		this.updateProperties();
	}
	
	public Vect3D getAbsolutePosition() {
		return this.position.clone();
	}

	public Vect3D getAbsoluteRotPosition() {
		return this.rotPosition.clone();
	}

	/**
	 * get children
	 * 
	 */
	public ArrayList<Body> getDescendants() {
		return this.elements;
	}
	
	public ArrayList<Body> getAllTerminalBodies() {
		
		ArrayList<Body> res = new ArrayList<Body>();
		
		for(Body child: this.elements){
			for(Body el: child.getAllTerminalBodies()){
				res.add(el);
			}
		}
		
		return res;
	}

	/**
	 * update position and rotation after a delta time ONLY FOR THE PARENT
	 * 
	 * @param deltaTime
	 */
	public void updateState(double deltaTime) {

 
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

		
		for(Body child: this.elements){
			child.updateState(deltaTime);
		}

	}
	
	/**
	 * Cette fonction permet de calculer les données de vitesses et accelerations en un nouveau point
	 * (défini par diff_barycenter, relativement au barycentre de oldGroup)
	 * Pour celà, la fonction utilise les vitesses et accélération du corps complet oldGroup
	 * (old car cette fonction est utilisé dans le cas d'un détachement)
	 * @param diff_barycenter
	 * @param oldGroup
	 */
	public void updateAfterDetach(Vect3D diff_barycenter, BodyGroup oldGroup){
		
		Vect3D new_absoluteSpeed = oldGroup.absoluteSpeed.add(
				oldGroup.absoluteRotSpeed.vectProd(diff_barycenter.mult(-1))
				);
		Vect3D new_absoluteRotSpeed = oldGroup.absoluteRotSpeed.clone();

		Vect3D new_absoluteAcceleration = oldGroup.absoluteAcceleration.clone();
		if(this.position.size()>0){
			new_absoluteAcceleration = oldGroup.absoluteAcceleration.add(diff_barycenter.mult(
						Math.pow(oldGroup.absoluteRotSpeed.vectProd(diff_barycenter).size(),2)
						/diff_barycenter.size()
					));
		}
		Vect3D new_absoluteRotAcceleration = oldGroup.absoluteRotAcceleration.clone();

		this.absoluteSpeed = new_absoluteSpeed;
		this.absoluteRotSpeed = new_absoluteRotSpeed;
		this.absoluteAcceleration = new_absoluteAcceleration;
		this.absoluteRotAcceleration = new_absoluteRotAcceleration;
		
	}

}
