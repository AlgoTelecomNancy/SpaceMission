package main;

import display.Window;
import maths.Vect3D;
import physics.Body;

public class Tests {

	public static void main(String args[]){
		
		Window window = new Window();
		
		Body test = new Body();

		Body sub1 = new Body();
		Body sub2 = new Body();
		
		test.addChild(sub1);
		test.addChild(sub2);
		
		sub1.setRadius(0.5);
		sub1.setMass(1);

		sub2.setRadius(0.5);
		sub2.setMass(1);

		sub1.setPosition(new Vect3D(0, 0, 0));
		sub2.setPosition(new Vect3D(0, 0, 2));
		
		sub1.setForce(new Vect3D(0,1,0));
		sub2.setForce(new Vect3D(0,1,0));

		test.updateProperties();
		
		
		window.getDisplay().attach(test);

	
	}
	
}
