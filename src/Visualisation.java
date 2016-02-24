import java.awt.*;
import javax.swing.*;

import com.sun.org.apache.bcel.internal.generic.IADD;

import Univers.Espace;
import spaceship.Module;

public class Visualisation extends JPanel {

	public JFrame fenetre;
	
	public Visualisation(){
		
		
		
		fenetre = new JFrame();
		 fenetre.setTitle("Visalisation");
		 fenetre.setSize(800,600);
		 fenetre.setLocationRelativeTo(null);
		 fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 this.setBackground(Color.WHITE);
		 this.setLayout(null);
		 
		 fenetre.setContentPane(this);
		 
		 fenetre.show();
		
	}
	
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g); // take care of anything else
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.RED);
		for(Module mod: Game.Univers.joueurs[0].modules){
			if(mod!=null){
				g2d.fillOval(this.fenetre.getWidth()/2+(int)(mod.position.x*10000)-(int)(mod.rayon*10000/2),
						this.fenetre.getHeight()/2+(int)(mod.position.y*10000)-(int)(mod.rayon*10000/2),
						(int)(mod.rayon*10000),
						(int)(mod.rayon*10000));
			}
		}
		
	}
	
	
	public void clear(){
		this.removeAll();
		this.revalidate();
		this.repaint();
		fenetre.show();
	}
	
}
