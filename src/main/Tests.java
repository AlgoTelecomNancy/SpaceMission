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
		
		//sub1.setForce(new Vect3D(0,1,0));
				
		System.out.println("Position : "+test.getAbsolutePosition());
		System.out.println("Position s1 : "+sub1.getAbsolutePosition());
		System.out.println("Position s2 : "+sub2.getAbsolutePosition());
		System.out.println("Rotation : "+test.getAbsoluteRotPosition());
		System.out.println("Rotation s1 : "+sub1.getAbsoluteRotPosition());
		System.out.println("Rotation s2 : "+sub2.getAbsoluteRotPosition());

		System.out.println("Force : "+test.getForce());
		System.out.println("Force s1 : "+sub1.getForce());
		System.out.println("Force s2 : "+sub2.getForce());
		
		System.out.println("Moment : "+test.getMoment());
		System.out.println("Moment s1 : "+sub1.getMoment());
		System.out.println("Moment s2 : "+sub2.getMoment());
		
		test.setRotPosition(new Vect3D(Math.PI/2,0,0));

		
		System.out.println("================ i=");

		System.out.println("Position : "+test.getAbsolutePosition());
		System.out.println("Position s1 : "+sub1.getAbsolutePosition());
		System.out.println("Position s2 : "+sub2.getAbsolutePosition());
		System.out.println("Rotation : "+test.getAbsoluteRotPosition());
		System.out.println("Rotation s1 : "+sub1.getAbsoluteRotPosition());
		System.out.println("Rotation s2 : "+sub2.getAbsoluteRotPosition());

		System.out.println("Force : "+test.getForce());
		System.out.println("Force s1 : "+sub1.getForce());
		System.out.println("Force s2 : "+sub2.getForce());
		
		System.out.println("Moment : "+test.getMoment());
		System.out.println("Moment s1 : "+sub1.getMoment());
		System.out.println("Moment s2 : "+sub2.getMoment());
	
	
	}
	
}
