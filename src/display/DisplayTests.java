package display;

import maths.Vect3D;
import physics.Body;


public class DisplayTests {

	static private Body getSpaceShip() {
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
		
		window.getDisplay().addCube(new Cube(new Vect3D(),
				new Vect3D(0.5,0.5,0.5),
				new Vect3D()));
		
		
		int j = 0;
		while (true) {
			j++;
			
			spaceship.updateState(1. / 60);

			window.getCamera().setFocusedPoint(spaceship.getPosition());

			for (int i = 0; i < spaceship.getChildren().size(); ++i) {
				window.getDisplay().getCube(i).setPosition(spaceship.getChildren().get(i).getAbsolutePosition());
				window.getDisplay().getCube(i).setAngles(spaceship.getChildren().get(i).getAbsoluteRotPosition());
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
