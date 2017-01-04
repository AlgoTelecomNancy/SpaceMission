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

		sub1.setRadius(1);
		sub1.setMass(1);

		sub2.setRadius(1);
		sub2.setMass(1);

		sub1.setPosition(new Vect3D(0, 0, 3));
		
		test.getChildren().get(0).setForce(new Vect3D(100,0,0));
		test.getChildren().get(1).setForce(new Vect3D(100,0,0));

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

			spaceship.updateState(1. / 60);
			//spaceship.setRotPosition(new Vect3D(0,j*0.001,j*0.01));

			window.getCamera().setFocusedPoint(spaceship.getPosition());

			for (int i = 0; i < spaceship.getChildren().size(); ++i) {
				window.getDisplay().getCube(i).setPosition(spaceship.getChildren().get(i).getAbsolutePosition());
				window.getDisplay().getCube(i).setAngles(spaceship.getChildren().get(i).getAbsoluteRotPosition().mult(180/(Math.PI)));
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
