package core;
import Univers.Espace;
import display.Cube;
import display.Window;
import spaceship.Module;
import types.Vect3D;

public class Visualisation3D {
	Espace espace;
	Window window;

	public Visualisation3D(Espace espace) {
		this.espace = espace;
		window = new Window();

		window.getDisplay().addCube(new Cube(new Vect3D(), new Vect3D(), new Vect3D()));
		window.getCamera().setPosition(new Vect3D(0.05, 0.00, 0.00));

		// Modules
		for (Module m : espace.getPlayer(0).modules) {
			window.getDisplay().addCube(new Cube(new Vect3D(), new Vect3D(), new Vect3D()));
		}

		window.getCamera().speed = 0.001;
		window.getDisplay().setFocusedCube(0);

		for (int x = -1; x < 2; x += 2)
			for (int y = -1; y < 2; y += 2)
				for (int z = -1; z < 2; z += 2)
					window.getDisplay().addCube(new Cube(new Vect3D(x * 0.03, y * 0.03, z * 0.03),
						new Vect3D(0.001, 0.001, 0.001), new Vect3D(45, 45, 45)));
	}

	public void update() {
		for(int i=0;i<espace.joueurs.length;i++){
			if(espace.joueurs[i]!=null){
				
				window.getDisplay().getCube(0).setPosition(espace.joueurs[i].position.getPosition());
				window.getDisplay().getCube(0).setSize(new Vect3D(0.0001,0.0001,0.0001));
				
				int j = 1;
				for(Module m: espace.joueurs[0].modules){
					
					if(window.getDisplay().getCube(j) != null && m != null){			
						
						window.getDisplay().getCube(j).setPosition(m.AbsolutePosition);
						window.getDisplay().getCube(j).setSize(new Vect3D(m.rayon,m.rayon,m.rayon));
						window.getDisplay().getCube(j).setAngles(new Vect3D(Math.toDegrees(espace.joueurs[0].orientation.x + m.orientation.x),
								Math.toDegrees(espace.joueurs[0].orientation.y + m.orientation.y),
								Math.toDegrees(espace.joueurs[0].orientation.z + m.orientation.z)));
						j++;
					}
				}
			
			}
		}
	}
}
