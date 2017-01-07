package physics;

import java.util.ArrayList;
import java.util.HashMap;

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
public class Body extends BodySuperClass {

	//Group element (sort of SuperParent)
	private BodyGroup group;
	
	//Tree representation
	private Body parent;
	private ArrayList<Body> children = new ArrayList<Body>();
	private ArrayList<Body> attached = new ArrayList<Body>();
	
	//Position is local and global if no parent
	private Vect3D position = new Vect3D(); //Position relative to the parent body	
	private Vect3D rotPosition = new Vect3D(); //RotPosition relative to the parent body


	public Body() {	}
	
	public void setGroup(BodyGroup group){
		this.group = group;
	}

	public void addChild(Body child) {
		child.parent = this;
		this.children.add(child);
		this.updateProperties();
	}
	public void removeChild(Body child) {
		this.children.remove(child);
		this.updateProperties();
	}

	public void attachTo(Body body){
		body.attached.add(this);
		this.attached.add(body);
	}
	
	public void removeLink(Body body){
		body.attached.remove(this);
		this.attached.remove(body);
	}

	public void setPosition(Vect3D newPosition) {
		this.position = newPosition;
		this.updateProperties();
	}
	
	public void setRotPosition(Vect3D newRotPosition){
		this.rotPosition = newRotPosition;
		this.updateProperties();
	}

	public Vect3D getPosition() {
		return this.position;
	}

	public Vect3D getRotPosition() {
		return this.rotPosition;
	}
	
	public Vect3D getAbsolutePosition() {
		BodyInterface globalContext = getParentBody();
		return (VectRotation.rotate(this.position,globalContext.getAbsoluteRotPosition())).add(globalContext.getAbsolutePosition());
	}

	public Vect3D getAbsoluteRotPosition() {
		BodyInterface globalContext = getParentBody();
		return globalContext.getAbsoluteRotPosition().add(this.rotPosition);
	}
	
	public BodyGroup getGroup(){
		if(this.parent==null){
			return group;
		}
		return this.parent.group;
	}

	/**
	 * get children
	 * 
	 */
	public ArrayList<Body> getDescendants() {
		return this.children;
	}
	
	/**
	 * Find if the "parent" is actualy the group or an other parent body
	 * @return
	 */
	private BodySuperClass getParentBody(){
		BodySuperClass globalContext = this.parent;
		if(this.parent==null){
			globalContext = this.group;
		}
		return globalContext;
	}

	

	/**
	 * update position and rotation after a delta time ONLY FOR THE PARENT
	 * 
	 * @param deltaTime
	 */
	public void updateState(double deltaTime) {

		BodySuperClass parent = getParentBody();

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
		
		for(Body child: this.children){
			child.updateState(deltaTime);
		}

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
		
		return res;
	}

	
	/**
	 * Détacher le corps
	 * Retourne un objet qui représente la partie éventuellement détachée
	 * Peut retourner null si reste attaché finalement !!!
	 */
	public BodyGroup detachFrom(Body brother){
		
		this.removeLink(brother);
		
		boolean not_really_detached = false;
		ArrayList<Body> found = new ArrayList<Body>();
		ArrayList<Body> nextBrothers = brother.attached;
		ArrayList<Body> temp_nextBrothers = new ArrayList<Body>();
		boolean end = false;
		boolean hasNextBrothers = false;
		while(!end){
			hasNextBrothers = false;
			temp_nextBrothers = new ArrayList<Body>();
			for(Body b : nextBrothers){
				if(!found.contains(b)){
					found.add(b);
					hasNextBrothers = true;
					temp_nextBrothers.addAll(b.attached);
				}
			}
			nextBrothers = temp_nextBrothers;
			if(!hasNextBrothers){
				end = true;
			}
		}
		
		if(found.contains(this)){ //Ce détachement n'a pas créé d'autre groupe
			return null;
		}else{
			//Sinon on créé un nouveau groupe et on y met les nouveaux éléments
			//On met aussi à jour toutes les variables
			
			BodyGroup my_group = this.getGroup();

			//Récupérer le centre commun du groupe uni
			Vect3D old_common_barycenter = my_group.getAbsolutePosition().clone();
			BodyGroup old_group_phantom = new BodyGroup();
			old_group_phantom.setRotPosition(my_group.getAbsoluteRotPosition().clone());
			old_group_phantom.absoluteSpeed = my_group.absoluteSpeed.clone();
			old_group_phantom.absoluteRotSpeed = my_group.absoluteRotSpeed.clone();
			old_group_phantom.absoluteAcceleration = my_group.absoluteAcceleration.clone();
			old_group_phantom.absoluteRotAcceleration = my_group.absoluteRotAcceleration.clone();
			
			//Séparer les deux groupes
			BodyGroup new_group = new BodyGroup();
			
			new_group.lockProperties();
			this.getParentBody().lockProperties();

			new_group.setPosition(this.getGroup().getAbsolutePosition());
			new_group.setRotPosition(this.getGroup().getAbsoluteRotPosition());
			
			for(Body b: found){
				if(this.group==null){
					this.parent.removeChild(b);
				}else{
					this.group.removeBody(b);
				}
				new_group.addBody(b);
			}
			
			new_group.unlockProperties();
			this.getParentBody().unlockProperties();

			

			/*
			 * On se retrouve alors avec deux groupes, un nouveau groupe qui ne possède pas de
			 * données utiles
			 * Et le groupe initial qui possède les anciennent vitesses et accélération des deux groupes réunis
			 * Ce dernier va nous servir pour recalculer les valeurs d'accélération et de vitesses
			 * des deux nouveaux sous groupes.
			*/
			
			//Mettre à jour les calculs

			new_group.updateProperties();
			my_group.updateProperties();

			System.out.println("old=>"+old_common_barycenter);
			System.out.println("1=>"+my_group.getPosition());
			System.out.println("2=>"+new_group.getPosition());

			Vect3D diff_my_barycenter = my_group.getAbsolutePosition().minus(old_common_barycenter).mult(-1);
			Vect3D diff_newgroup_barycenter = new_group.getAbsolutePosition().minus(old_common_barycenter).mult(-1);

			new_group.updateAfterDetach(diff_newgroup_barycenter, old_group_phantom);
			my_group.updateAfterDetach(diff_my_barycenter, old_group_phantom);
			

			return new_group;
			
		}
	}
	
	/**
	 * Détacher un corps de toutes ses attaches
	 * Retourne la liste des nouveaux corps (dont l'objet lui même, mais sans l'objet considéré comme parent)
	 * Retourne obligatoirement l'élément détaché de tout le reste
	 */
	public ArrayList<BodyGroup> detach(){
		
		//TODO utiliser detachFrom pour détacher de tout le monde
		
		return null;
		
		/*this.position = this.getAbsolutePosition();
		this.rotPosition = this.getAbsoluteRotPosition();
		
		Vect3D old_barycenter = getParentBody().getAbsolutePosition();
		
		getParentBody().removeChild(this);
		
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
		return this;*/
	}
}
