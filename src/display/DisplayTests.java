package display;

import maths.Vect3D;
import physics.Body;


public class DisplayTests {

	static private Body getSpaceShip() {
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
		
		sub1.setRadius(0.1);
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

		sub8.setRadius(0.1);
		sub8.setMass(1);

		sub1.setPosition(new Vect3D(0, 0, 1));
		sub2.setPosition(new Vect3D(0, 0, 2));
		sub3.setPosition(new Vect3D(1, 0, 0));
		sub4.setPosition(new Vect3D(2, 0, 0));
		sub5.setPosition(new Vect3D(0, 1, 0));
		sub6.setPosition(new Vect3D(0, 2, 0));
		sub7.setPosition(new Vect3D(0, 0, 0));
		sub8.setPosition(new Vect3D(1, 0, 0));
		
		test.updateProperties();
		

		return test;
	}

	public static void main(String[] args) {
		Window window = new Window();

		Body spaceship = getSpaceShip();

		for (Body child : spaceship.getChildren()) {
			Cube cube = new Cube(child.getPosition(),
					new Vect3D(child.getRadius() * 2, child.getRadius() * 2, child.getRadius() * 2),
					child.getRotPosition());
			window.getDisplay().addCube(cube);
		}
		
	
		int j = 0;
		while (true) {
			j++;
			
			//spaceship.updateState(1. / 60);
			spaceship.setRotPosition(new Vect3D(Math.PI/4,j*0.01,j*0.01));

			window.getCamera().setFocusedPoint(spaceship.getPosition());

			for (int i = 0; i < spaceship.getChildren().size(); ++i) {
				window.getDisplay().getCube(i).setPosition(spaceship.getChildren().get(i).getAbsolutePosition());
				//window.getDisplay().getCube(i).setAngles(spaceship.getChildren().get(i).getAbsoluteRotPosition().mult(180/(Math.PI)));
			}

			
			try {
				Thread.sleep(1000 / 60);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			spaceship.getChildren().get(0).setForce(new Vect3D());
			spaceship.getChildren().get(1).setForce(new Vect3D());

		}
	}

}
