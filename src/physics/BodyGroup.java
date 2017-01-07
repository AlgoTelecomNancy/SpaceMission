package physics;

import java.util.ArrayList;

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
	
	public boolean contains(Body tofind){
		System.out.println(tofind.debugString);

		for(Body el: this.getDescendants()){
			System.out.println(el.debugString);
			if(el.equals(tofind)){
				System.out.println("FOUND !");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Cette fonction permet de calculer les données de vitesses et accelerations en un nouveau point
	 * (défini par diff_barycenter, relativement au barycentre de oldGroup)
	 * Pour celà, la fonction utilise les vitesses et accélération du corps complet oldGroup
	 * (old car cette fonction est utilisé dans le cas d'un détachement)
	 * @param diff_barycenter
	 * @param oldGroup
	 */
	public void updateAfterDetach(Vect3D diff_barycenter2, BodyGroup oldGroup){
		
		Vect3D diff_barycenter = diff_barycenter2;//VectRotation.rotate(diff_barycenter2, oldGroup.getAbsoluteRotPosition().mult(-1));
		
		Vect3D new_absoluteSpeed = oldGroup.absoluteSpeed.add(
				diff_barycenter.vectProd(oldGroup.absoluteRotSpeed)
				);
		
/*		Vect3D hypotetical_pos1 = (VectRotation.rotate(diff_barycenter,oldGroup.getAbsoluteRotPosition())).add(oldGroup.getAbsolutePosition());
		
		System.out.println("pos ="+this.getAbsolutePosition());
		System.out.println("speed? ="+oldGroup.getRotSpeed());
		
		Vect3D hypotetical_pos2 = (
				VectRotation.rotate(
						diff_barycenter,
						oldGroup.getAbsoluteRotPosition()//.add(oldGroup.getRotSpeed().mult(1./60))
						)
				)
				.add(oldGroup.getAbsolutePosition().add(oldGroup.getSpeed()));
		

		System.out.println("pos?2 ="+hypotetical_pos2);
*/
		/*
		Vect3D hypotetical_pos2 = (
				VectRotation.rotate(
						diff_barycenter,oldGroup.getAbsoluteRotPosition().add(oldGroup.getRotSpeed())
				)
				).add(oldGroup.getAbsolutePosition().add(oldGroup.getSpeed()));
		*/
		
		//new_absoluteSpeed = hypotetical_pos2.minus(hypotetical_pos1);
		
		Vect3D new_absoluteRotSpeed = oldGroup.absoluteRotSpeed.clone();

		this.absoluteSpeed = new_absoluteSpeed;
		this.absoluteRotSpeed = new_absoluteRotSpeed;
		
	}

}
