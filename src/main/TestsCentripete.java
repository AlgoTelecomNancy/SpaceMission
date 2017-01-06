package main;

import java.util.ArrayList;

import display.DrawableSpaceship;
import display.Window;
import maths.Vect3D;
import physics.Body;
import physics.BodyGroup;


public class TestsCentripete {

	static private BodyGroup getSpaceShip() {
		BodyGroup test = new BodyGroup();
		
		test.lockProperties();

		Body sub1 = new Body();
		Body sub2 = new Body();
		Body sub3 = new Body();
		
		test.addBody(sub1);
		test.addBody(sub2);
		test.addBody(sub3);

		sub1.attachTo(sub2);
		sub1.attachTo(sub3);
		
		
		sub1.setRadius(0.5);
		sub1.setMass(10);

		sub2.setRadius(0.2);
		sub2.setMass(1);
		
		sub3.setRadius(0.2);
		sub3.setMass(1);

		sub1.setPosition(new Vect3D(0, 0, 0));
		sub2.setPosition(new Vect3D(0, 0, -2));
		sub3.setPosition(new Vect3D(0, 0, 2));

		//sub1.setForce(new Vect3D(0,10,0));
		sub2.setForce(new Vect3D(0,4,0));
		sub3.setForce(new Vect3D(0,3,0));
		
		test.unlockProperties();
		test.updateProperties();
		
		int i=0;
		for(i=0;i<10;i++){
			test.updateState(1./60);
		}
		
		//test.getChildren().get(0).setForce(new Vect3D());
		//test.getChildren().get(1).setForce(new Vect3D());

		return test;
	}

	public static void main(String[] args) {
		Window window = new Window();

		BodyGroup spaceship = getSpaceShip();
		spaceship.setRotPosition(new Vect3D(0,0,0));
		
		ArrayList<DrawableSpaceship> spaceships = new ArrayList<DrawableSpaceship>();
		ArrayList<BodyGroup> spaceshipsBody = new ArrayList<BodyGroup>();

		spaceships.add(new DrawableSpaceship(spaceship, window));
		spaceshipsBody.add(spaceship);
		
		window.getCamera().setPosition(new Vect3D(-20,0,0));;

		int j =0;
		while (true) {
			j++;
			
			for(BodyGroup sp: spaceshipsBody){
				sp.updateState(1. / 160);
				System.out.println(sp.getAbsolutePosition());
			}
			for(DrawableSpaceship sp: spaceships){
				sp.updateSpaceship();
			}
			
			
			try {
				Thread.sleep(1000 / 60);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					

			
			if(j==240){
				spaceship.getDescendants().get(2).setForce(new Vect3D());
				spaceship.getDescendants().get(1).setForce(new Vect3D());
				
				BodyGroup part = spaceship.getDescendants().get(1).detachFrom(
							spaceship.getDescendants().get(0)
						);
				if(part!=null){
					spaceships.add(new DrawableSpaceship(part, window));
					spaceshipsBody.add(part);
				}
			}
			
		}
	}

}
