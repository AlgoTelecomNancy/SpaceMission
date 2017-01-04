package main;

import maths.Vect3D;
import physics.Body;

public class Tests {

	public static void main(String args[]){
		
		Body test = new Body();

		Body sub1 = new Body();
		Body sub2 = new Body();
		
		test.addChild(sub1);
		test.addChild(sub2);
		
		sub1.setRadius(1);
		sub1.setMass(1);
		
		sub2.setRadius(1);
		sub2.setMass(1);

		sub1.setPosition(new Vect3D(0,0,2));
		
		sub1.setForce(new Vect3D(0,1,0));
				
		double deltaTime = 0.1;
		test.updateState(deltaTime);
		
		
	}
	
}
