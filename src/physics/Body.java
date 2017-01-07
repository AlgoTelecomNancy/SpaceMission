package physics;

import java.util.ArrayList;

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

	//Debug
	public String debugString;
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
	
	public ArrayList<Body> getAttachedBodies() {
		return attached;
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
	 * Retourne deux objet qui représente les deux parties éventuellement détachées
	 * Peut retourner une liste d'un seul objet si reste attaché finalement !!!
	 */
	public ArrayList<BodyGroup> detachFrom(Body brother){
		
		ArrayList<BodyGroup> array = new ArrayList<BodyGroup>();
		
		this.removeLink(brother);
		
		boolean not_really_detached = false;
		ArrayList<Body> found = new ArrayList<Body>();
		ArrayList<Body> nextBrothers = brother.attached;
		found.add(brother);
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
			array.add(this.getGroup());
			return array;
		}else{
			//Sinon on créé un nouveau groupe et on y met les nouveaux éléments
			//On met aussi à jour toutes les variables
			
			BodyGroup my_group = this.getGroup();
			my_group.lockProperties();

			//Récupérer le centre commun du groupe uni
			Vect3D old_common_barycenter = my_group.getAbsolutePosition().clone();
			BodyGroup old_group_phantom = new BodyGroup();
			old_group_phantom.setRotPosition(my_group.getAbsoluteRotPosition().clone());
			old_group_phantom.absoluteSpeed = my_group.absoluteSpeed.clone();
			old_group_phantom.absoluteRotSpeed = my_group.absoluteRotSpeed.clone();
			old_group_phantom.absoluteAcceleration = my_group.absoluteAcceleration.clone();
			old_group_phantom.absoluteRotAcceleration = my_group.absoluteRotAcceleration.clone();
			
			//Séparer les deux groupes
			BodyGroup new_group1 = new BodyGroup();
			BodyGroup new_group2 = new BodyGroup();

			new_group1.lockProperties();
			new_group2.lockProperties();

			new_group1.setPosition(this.getGroup().getAbsolutePosition());
			new_group1.setRotPosition(this.getGroup().getAbsoluteRotPosition());
			new_group2.setPosition(this.getGroup().getAbsolutePosition());
			new_group2.setRotPosition(this.getGroup().getAbsoluteRotPosition());
			
			for(Body b: found){
				if(this.group==null){
					this.parent.removeChild(b);
				}else{
					this.group.removeBody(b);
				}
				new_group1.addBody(b);
			}
			for(Body b: this.getParentBody().getDescendants()){
				new_group2.addBody(b);
			}
			
			new_group1.unlockProperties();
			new_group2.unlockProperties();
			

			/*
			 * On se retrouve alors avec deux groupes, un nouveau groupe qui ne possède pas de
			 * données utiles
			 * Et le groupe initial qui possède les anciennent vitesses et accélération des deux groupes réunis
			 * Ce dernier va nous servir pour recalculer les valeurs d'accélération et de vitesses
			 * des deux nouveaux sous groupes.
			*/
			
			//Mettre à jour les calculs

			new_group1.updateProperties();
			new_group2.updateProperties();


			Vect3D diff_1_barycenter = new_group1.getAbsolutePosition().minus(old_common_barycenter).mult(-1);
			Vect3D diff_2_barycenter = new_group2.getAbsolutePosition().minus(old_common_barycenter).mult(-1);

			new_group1.updateAfterDetach(diff_1_barycenter, old_group_phantom);
			new_group2.updateAfterDetach(diff_2_barycenter, old_group_phantom);
			

			array.add(new_group1);
			array.add(new_group2);
			
			//Remove all links with old parent (be freeee !)
			this.getParentBody().getDescendants().clear();

			return array;
			
		}
	}
	
	/**
	 * Détacher un corps de toutes ses attaches
	 * Retourne la liste des nouveaux corps (dont l'objet lui même, mais sans l'objet considéré comme parent)
	 * Retourne obligatoirement l'élément détaché de tout le reste
	 */
	public ArrayList<BodyGroup> detach(){
		
		ArrayList<BodyGroup> array = new ArrayList<BodyGroup>();

		ArrayList<Body> attached_temp = (ArrayList<Body>) this.attached.clone();
		
		for(Body b: attached_temp){
			for(BodyGroup b2: this.detachFrom(b)){
				if(!b2.getDescendants().contains(this)){
					array.add(b2);
				}else{
					this.group = b2;
				}
			}
		}
		
		return array;
		
	}
}
