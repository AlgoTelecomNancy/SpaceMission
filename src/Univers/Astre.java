package Univers;

public class Astre {
	
	//------------------------------------------------Initialisation---------------------------------------
	//declaration des variables de l'astre
	//coordonnées
	public double x;
	public double y;
	public double z;
	//rayon de l'astre
	public double r;
	//élément principal qui compose la planète
	public String element;
	//température à la surface de la planète
	public double temperature;
	//élément principal qui compose l'air
	public String compositionAir;
	
	//des constantes utiles pour les calculs
	private static final double G=6.6740831*Math.pow(10, 11);

	//les constructeurs
	public Astre (double x, double y, double z, double r, Element element, double temperature, String compositionAir){
		this.x=0;
		this.y=0;
		this.z=0;
		this.r=0;
		this.element="carbon";
		this.temperature=264;
		this.compositionAir="oxygene";
	}
	
	//-----------------------------------------Les Coordonnées-------------------------------------------
	//appeler les coordonnées
	public double getX () {return this.x;}
	public double getY () {return this.y;}
	public double getZ () {return this.z;}
	
	//modifier les coordonnées
	public void setX (double X) {this.x=X;}
	public void setY (double Y) {this.y=Y;}
	public void setZ (double Z) {this.z=Z;}
	
	//translate les coordonnées
	public void translate (double X, double Y, double Z) {
		this.x=X;
		this.y=Y;
		this.z=Z;
	}
	
	//appeler le rayon
	public double getR (){return this.r;}
	
	//modifier le rayon
	public void setR (double R) {this.r=R;}
	
	
	//---------------------------------------------Caractéristique de l'Astre---------------------------------
	//vérifier si un object(x,y,z) n'est pas entrée en colosion avec l'astre
	public boolean isImpact (double X, double Y, double Z) {
		return (this.r)<=(Math.sqrt((Z-this.z)*(Z-this.z)+Math.sqrt((X-this.x)*(X-this.x)+(Y-this.y)*(Y-this.y))));
	}
	
	//Le volume de l'astre
	public double volume () {return (4/3)*Math.PI*this.r*this.r*this.r;}
	
	//La masse de l'Astre
	public double masse () {return this.volume()*this.element.masseVolumique();}
	
	//Le Poids de l'astre
	public double Poids () {return this.masse()*G/(this.r*this.r);}
	
	//--------------------------------------------Caractéristique de l'Atmosphère---------------------------
	//public double rayonAir () {
		
	//}
	
	//public double po () {
		
	//}
	
	//public Boolean respirable (double s) {
		
	//}
	
	//----------------------------------------------La Température------------------------------------------
	//Appeler la température
	public double getT (){return this.temperature;}
	
	//Modifier la température
	public void setT(double T){this.temperature=T;}

	//----------------------------------------------Trajectoire et Orbite -----------------------------------
	//Première vitesse cosmologique, c'est la vitesse minimale pour sateliter un objet
	public double vitesseSatelisation () {
		return Math.sqrt(G*this.masse()/this.r);
	}
	
	//Deuxième vitesse cosmologique, c'est la vitesse minimale pour sortir de l'attraction d'un astre
	public double vitesseLiberation () {
		return Math.sqrt(2)*this.vitesseSatelisation();
	}
	
	//Période de révolution pour une orbite de demi grand axe rOrbite
	public double periodeOrbite (double rOrbite){
		return Math.sqrt((4*Math.PI*Math.PI*rOrbite*rOrbite*rOrbite)/(G*this.masse()));
	}
	
}
