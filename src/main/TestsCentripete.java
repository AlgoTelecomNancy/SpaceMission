package main;

import java.util.ArrayList;

import display.DrawableSpaceship;
import display.Window;
import maths.Vect3D;
import physics.Body;


public class TestsCentripete {

	static private Body getSpaceShip() {
		Body test = new Body();
		
		test.lockProperties();

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
		
		sub1.setForce(new Vect3D(0,10,0));
		sub2.setForce(new Vect3D(0,-10,0));

		test.unlockProperties();
		test.updateProperties();
		
		int i=0;
		for(i=0;i<10;i++){
			test.updateState(1./60);
		}
		
		test.getChildren().get(0).setForce(new Vect3D());
		test.getChildren().get(1).setForce(new Vect3D());

		return test;
	}

	public static void main(String[] args) {
		Window window = new Window();

		Body spaceship = getSpaceShip();
		spaceship.setRotPosition(new Vect3D(0,0,0));
		
		ArrayList<DrawableSpaceship> spaceships = new ArrayList<DrawableSpaceship>();
		ArrayList<Body> spaceshipsBody = new ArrayList<Body>();

		spaceships.add(new DrawableSpaceship(spaceship, window));
		spaceshipsBody.add(spaceship);
		
		window.getCamera().setPosition(new Vect3D(-10,0,0));;

		int j =0;
		while (true) {
			j++;
			
			for(Body sp: spaceshipsBody){
				sp.updateState(1. / 60);
			}
			for(DrawableSpaceship sp: spaceships){
				sp.updateSpaceship();
			}
			
			//window.getCamera().setFocusedPoint(spaceship.getPosition());

			
			try {
				Thread.sleep(1000 / 60);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					

			
			if(j==120){
				Body part = spaceship.getChildren().get(0).detach();
				spaceships.add(new DrawableSpaceship(part, window));
				spaceshipsBody.add(part);
			}
			
		}
	}

}
