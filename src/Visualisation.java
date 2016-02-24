import java.awt.*;
import javax.swing.*;

import com.sun.org.apache.bcel.internal.generic.IADD;

import Univers.Espace;
import spaceship.Module;
import spaceship.Spaceship;


/**
 * Showing spaceship and general game stats (2D)
 * 
 * Circle = module
 * Green border = doors opens, Red border = doors locked
 * gray blink = alarm
 * Color -> Red = hot -> Blue = cold
 * @author romaricportable
 *
 */
public class Visualisation extends JPanel {

	public JFrame fenetre;
	private int gameSpeedSlower = 0;
	
	public Visualisation(){
		
		
		
		fenetre = new JFrame();
		 fenetre.setTitle("Visalisation");
		 fenetre.setSize(800,600);
		 fenetre.setLocationRelativeTo(null);
		 fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 this.setBackground(Color.GRAY);
		 this.setLayout(null);
		 
		 fenetre.setContentPane(this);
		 
		 fenetre.show();
		
	}
	
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g); // take care of anything else
		Graphics2D g2d = (Graphics2D) g;
		
		Spaceship spaceship = Game.Univers.joueurs[0];

		gameSpeedSlower += (1/base.Cons.deltaTime-gameSpeedSlower)/100;
		g2d.drawString("Game speed "+gameSpeedSlower +" f/s", 10, this.fenetre.getHeight()-30);
		
		g2d.drawString("Global time "+(int)(base.Cons.universalHorloge), 10, 10);
		g2d.drawString("Spaceship time "+(int)(base.Cons.horloge), 10, 25);

		g2d.drawString("Spaceship speed "+(int)((float)(spaceship.vitesse.size())/1000) + " m/s", 10, 45);
		g2d.drawString("Spaceship time relativity "+(float)((int)(spaceship.tempsRelatif*1000))/1000, 10, 60);

		
		for(Module mod: spaceship.modules){
			if(mod!=null){
				
				if(mod.ferme){
					g2d.setColor(Color.RED);
				}else{
					g2d.setColor(Color.GREEN);
				}
				g2d.fillOval(this.fenetre.getWidth()/2+(int)(mod.position.x*10000)-(int)(mod.rayon*10000/2)-2,
						this.fenetre.getHeight()/2+(int)(mod.position.y*10000)-(int)(mod.rayon*10000/2)-2,
						(int)(mod.rayon*10000)+4,
						(int)(mod.rayon*10000)+4);
				
				
			}
		}
		
		for(Module mod: spaceship.modules){
			if(mod!=null){
								
				g2d.setColor(new Color((int)Math.min(Math.max(0, (mod.temperature-285)*4),255), 0, (int)Math.min(Math.max(0, (-mod.temperature+285)*4),255)));
				
				if(mod.alarme && (int)(base.Cons.horloge*4)%2==0){
					g2d.setColor(Color.GRAY);
				}
				
				g2d.fillOval(this.fenetre.getWidth()/2+(int)(mod.position.x*10000)-(int)(mod.rayon*10000/2),
						this.fenetre.getHeight()/2+(int)(mod.position.y*10000)-(int)(mod.rayon*10000/2),
						(int)(mod.rayon*10000),
						(int)(mod.rayon*10000));	
				
				
				drawVText("T="+(int)(mod.temperature-273)+"°C", this.fenetre.getWidth()/2+(int)(mod.position.x*10000), this.fenetre.getHeight()/2+(int)(mod.position.y*10000), g2d);
				drawVText("H="+(int)(mod.nbHumains)+"/"+(int)(mod.capaciteHumaine), this.fenetre.getWidth()/2+(int)(mod.position.x*10000), this.fenetre.getHeight()/2+(int)(mod.position.y*10000) + 10, g2d);

				
			}
		}
		
	}
	
	//Draw a visible text
	private void drawVText(String txt, int x, int y, Graphics g2d){
		g2d.setFont(new Font("TimesRoman", Font.BOLD, 10));
		g2d.setColor(Color.BLACK);
		g2d.drawString(txt,x-1,y+1);
		g2d.setColor(Color.WHITE);
		g2d.drawString(txt,x,y);
		
	}
	
	
	public void clear(){
		this.removeAll();
		this.revalidate();
		this.repaint();
		fenetre.show();
	}
	
}
