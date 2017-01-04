package display;

import maths.Vect3D;


public class DisplayTests {

	public static void main(String[] args) {
		Window window = new Window();
		window.getDisplay().addCube(new Cube(new Vect3D(0, 0, 0), new Vect3D(10, 10, 10), new Vect3D(0, 0, 0)));
	}

}
