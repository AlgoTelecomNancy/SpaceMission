package main;

import maths.Vect3D;
import physics.Body;

public class Tests {

	public static void main(String args[]){
		
		Body test = new Body();

		Body sub1 = new Body();
		Body sub2 = new Body();
		Body sub3 = new Body();
		Body sub4 = new Body();
		Body sub5 = new Body();
		Body sub6 = new Body();
		Body sub7 = new Body();
		Body sub8 = new Body();
		
		test.addChild(sub1);
		test.addChild(sub2);
		test.addChild(sub3);
		test.addChild(sub4);
		test.addChild(sub5);
		test.addChild(sub6);
		test.addChild(sub7);
		test.addChild(sub8);
		
		sub1.setRadius(0.3);
		sub1.setMass(1);

		sub2.setRadius(0.1);
		sub2.setMass(1);
		
		sub3.setRadius(0.1);
		sub3.setMass(1);

		sub4.setRadius(0.1);
		sub4.setMass(1);
		
		sub5.setRadius(0.1);
		sub5.setMass(1);

		sub6.setRadius(0.1);
		sub6.setMass(1);
		
		sub7.setRadius(0.1);
		sub7.setMass(1);

		sub8.setRadius(0.3);
		sub8.setMass(1);

		sub1.setPosition(new Vect3D(0, 0, 0));
		sub2.setPosition(new Vect3D(0, 0, 1));
		sub3.setPosition(new Vect3D(0, 1, 0));
		sub4.setPosition(new Vect3D(0, 1, 1));
		sub5.setPosition(new Vect3D(1, 0, 0));
		sub6.setPosition(new Vect3D(1, 0, 1));
		sub7.setPosition(new Vect3D(1, 1, 0));
		sub8.setPosition(new Vect3D(1, 1, 1));
		
		System.out.println("Position : "+test.getAbsolutePosition());
		System.out.println("Position s1 : "+sub1.getAbsolutePosition());
		System.out.println("Position s2 : "+sub2.getAbsolutePosition());
		System.out.println("Position s3 : "+sub3.getAbsolutePosition());
		System.out.println("Position s4 : "+sub4.getAbsolutePosition());
		System.out.println("Position s5 : "+sub5.getAbsolutePosition());
		System.out.println("Position s6 : "+sub6.getAbsolutePosition());
		System.out.println("Position s7 : "+sub7.getAbsolutePosition());
		System.out.println("Position s8 : "+sub8.getAbsolutePosition());
		
		test.setRotPosition(new Vect3D(Math.PI/2,0,0));

		
		System.out.println("================ i=");

		System.out.println("Position : "+test.getAbsolutePosition());
		System.out.println("Position s1 : "+sub1.getAbsolutePosition());
		System.out.println("Position s2 : "+sub2.getAbsolutePosition());
		System.out.println("Position s3 : "+sub3.getAbsolutePosition());
		System.out.println("Position s4 : "+sub4.getAbsolutePosition());
		System.out.println("Position s5 : "+sub5.getAbsolutePosition());
		System.out.println("Position s6 : "+sub6.getAbsolutePosition());
		System.out.println("Position s7 : "+sub7.getAbsolutePosition());
		System.out.println("Position s8 : "+sub8.getAbsolutePosition());
		
	
	
	}
	
}
