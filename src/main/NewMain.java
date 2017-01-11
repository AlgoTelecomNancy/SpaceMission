package main;

import java.util.ArrayList;

import display.DrawableSpaceship;
import display.Window;
import maths.Vect3D;
import physics.Body;
import physics.BodyGroup;

public class NewMain extends Window {
	
	private static double mod_deltaTime = 1. / 160;
	private BodyGroup spaceship;
	private ArrayList<DrawableSpaceship> spaceships;
	private ArrayList<BodyGroup> spaceshipsBody;
	private int frameId;
	
	
	public static void main(String[] args) {
		new NewMain();
	}
	
	public NewMain() {
		super();
		
		spaceship = getSpaceShip();
		spaceship.setRotPosition(new Vect3D(1,2,0));
		spaceships = new ArrayList<DrawableSpaceship>();
		spaceshipsBody = new ArrayList<BodyGroup>();
		spaceships.add(new DrawableSpaceship(spaceship, this));
		spaceshipsBody.add(spaceship);
		frameId = 0;
		
		getCamera().setPosition(new Vect3D(20,20,20));
	}
	
	@Override
	public void updateDisplay() {
		frameId++;
		
		for(BodyGroup sp: spaceshipsBody){
			sp.updateState(mod_deltaTime);
		}
		for(DrawableSpaceship sp: spaceships){
			sp.updateSpaceship();
		}				

		if(frameId==10240){
			
			ArrayList<BodyGroup> parts = spaceship.getDescendants().get(2).detachFrom(
						spaceship.getDescendants().get(0)
					);

			spaceships.remove(spaceship);
			spaceshipsBody.remove(spaceship);
			for(BodyGroup part: parts){
				spaceships.add(new DrawableSpaceship(part, this));
				spaceshipsBody.add(part);
				
				if(part.getDescendants().size()>=2){
					spaceship = part;
				}
			}
			
		}
		
		if(frameId==130){
			//mod_deltaTime = mod_deltaTime/10;
		}
		
		if(frameId==10340){
							
			ArrayList<BodyGroup> parts = spaceship.getDescendants().get(0).detach();
			
			spaceships.remove(spaceship);
			spaceshipsBody.remove(spaceship);
			for(BodyGroup part: parts){
				System.out.println(part.getForce());
				System.out.println(part.absoluteSpeed);
				for (Body test : part.getDescendants()){
					System.out.println(test.debugString);
				}
				spaceships.add(new DrawableSpaceship(part, this));
				spaceshipsBody.add(part);
				
				if(part.getDescendants().size()==2){
					spaceship = part;
				}
			}
			
		}
		
	}
	
	static private BodyGroup getSpaceShip() {
		BodyGroup test = new BodyGroup();
		
		test.lockProperties();

		Body sub1 = new Body();
		sub1.debugString = "hi, I'm number 1 bigone";
		Body sub2 = new Body();
		sub2.debugString = "hi, I'm number 2";
		Body sub3 = new Body();
		sub3.debugString = "hi, I'm number 3";
		Body sub4 = new Body();
		sub4.debugString = "hi, I'm number 4";
		Body sub5 = new Body();
		sub5.debugString = "hi, I'm number 5";
		Body sub6 = new Body();
		sub6.debugString = "hi, I'm number 6";
		
		Body sub51 = new Body();
		sub51.debugString = "hi, I'm number 51";

		test.addBody(sub1);
		test.addBody(sub2);
		test.addBody(sub3);
		test.addBody(sub5);
		test.addBody(sub6);

		sub5.addChild(sub51);

		sub1.attachTo(sub2);
		sub1.attachTo(sub3);
		sub1.attachTo(sub5);
		sub1.attachTo(sub6);
		sub51.attachTo(sub5);
		
		
		sub1.setRadius(0.5);
		sub1.setMass(3);

		sub2.setRadius(0.2);
		sub2.setMass(1);
		
		sub3.setRadius(0.2);
		sub3.setMass(1);
		
		sub5.setRadius(0.2);
		sub5.setMass(1);
		
		sub6.setRadius(0.2);
		sub6.setMass(1);
		
		sub51.setRadius(0.1);
		sub51.setMass(0.5);

		sub1.setPosition(new Vect3D(0, 0, 0));
		sub2.setPosition(new Vect3D(0, 0, -2));
		sub3.setPosition(new Vect3D(0, 0, 2));
		sub5.setPosition(new Vect3D(0, -2, 0));
		sub6.setPosition(new Vect3D(0, 2, 0));
		sub51.setPosition(new Vect3D(0, -1, 0));

		test.unlockProperties();
		test.updateProperties();
		
		test.getAllTerminalBodies();

		sub1.setForce(new Vect3D(0,0,0));
		sub2.setForce(new Vect3D(0,-10,0));
		sub3.setForce(new Vect3D(0,10,0));
		//sub5.setForce(new Vect3D(10,0,0));
		//sub6.setForce(new Vect3D(10,0,0));
		
		//test.getChildren().get(0).setForce(new Vect3D());
		//test.getChildren().get(1).setForce(new Vect3D());

		return test;
	}
}
