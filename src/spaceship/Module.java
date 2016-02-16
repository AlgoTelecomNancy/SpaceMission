package spaceship;

import java.lang.Math.*;
import spaceship.modules.*;
import types.*;

public class Module {

	public int id;

	// Taille et position du module sur le vaisseau
	public Vect3D position = new Vect3D(0, 0, 0);
	public Vect3D orientation = new Vect3D(0, 0, 0);
	public Vect3D positionRelative = new Vect3D(0, 0, 0);
	public float rayon = 1;
	public float poids = 1;

	public String type;

	public float nbHumains = 0; // nombre d'humains
	public float capaciteHumaine = 0; // nombre max d'humains
	public boolean ferme = false; // Sas ouverts ou fermés

	public float transfertTime = 0; // Temps de transfert en cas de danger
	public int dommage = 0; // 0 dommage : bon état
	public int temperature = 290; // Température interieure en Kelvin
	public int temperatureMax = 320;
	public int pression = 100; // Pourcentage de la pression normale
	public float pressionTime = 0; // Temps depuis lequel la pression est
									// mortelle
	public boolean incendie = false; // Incendie (si temp>320 et pression>30)
	public float incendieTime = 0; // temps depuis lequel l'incendie est déclaré
	public boolean alarme = false; // Alarme
	public float coeffMortalite = 0; // coeff de mortalite
	public float coeffSurvie = 0; // coeff de survie
	public float coeffDanger = 0; // coeff de danger
	public int[] ArrayModulesContact = new int[8]; // Max 8 contacts physique
													// avec sas
	public int[] ArrayModulesBranche = new int[64]; // Max 64 contacts
													// branchement cable

	public Spaceship myParent;

	// Fonction de base pour actualiser le module
	public void update() {

		// myParent.modules[ArrayModulesTouche[0]]==null;

		// Incendie
		if (temperature > temperatureMax) {
			incendie = true;
		}
		if (pression < 30) {
			incendie = false;
		}

		// Alarme
		alarme = incendie; // Si incendie, alarme déclenchée
		alarme = pression < 50 && nbHumains > 0; // Si pression basse ou trop
													// élevée et humain alarme
		alarme = pression < 50 && nbHumains > 0;

		/*
		 * //Incendie augmente température if(incendie && Math.random()>0.98 &&
		 * temperature<390){ temperature += 1; } //Pas d'incendie stabilise la
		 * température à 290K if(!incendie && Math.random()>0.78 &&
		 * temperature!=290){ temperature +=
		 * -(290-temperature)/(290-temperature); }
		 */

		// Tuer des humains
		coeffDanger = 0;
		coeffMortalite = 0;
		coeffSurvie = 1;
		// Température (ça marche pas mal...)
		if (Math.random() > 0.4) {
			coeffDanger += Math.abs(290 - temperature) - 15 / 10;
			if (coeffDanger < 0)
				coeffDanger = 0;
			coeffMortalite = (float) Math.min(Math.max(0,
					(0.0045 * Math.pow((temperature - 290), 2) - 3) / 10), 1);
			coeffSurvie = (float) (coeffSurvie * (1 - Math.pow(coeffMortalite,
					2)));
			coeffMortalite = 0;
		}

		// Incendie
		if (incendie) {
			coeffDanger = 1;
			incendieTime += (float) base.Cons.deltaTime; // augmente le temps de
															// l'incendie
			if (incendieTime > 10) {
				coeffMortalite = Math.min(incendieTime / 2000, 1); // incendie
																	// de plus
																	// en plus
																	// mortel
																	// avec le
																	// temps
				coeffSurvie = (float) (coeffSurvie * (1 - Math.pow(
						coeffMortalite, 2)));
				coeffMortalite = 0;
			}
		} else if (incendieTime != 0) {
			incendieTime = 0;
		}

		// Pression basse
		if (pression < 50) {
			pressionTime += (float) base.Cons.deltaTime;
			if (pressionTime > 45) { // après 45 secondes en sous oxygène, tout
										// le monde est mort d'intoxication
				nbHumains = (float) (nbHumains * 0.45);
			}
			;
		} else if (pressionTime != 0) {
			pressionTime = 0;
		}

		if (alarme)
			coeffDanger += 0.5;
		else
			coeffDanger -= 0.25;
		if (coeffDanger < 0)
			coeffDanger = 0;
		if (coeffDanger > 1)
			coeffDanger = 1;
		nbHumains = (float) Math.max(0, nbHumains
				* (1 - (1 - coeffSurvie) * base.Cons.deltaTime / 0.05));
		// Gérer les flux

		// transfert d'humains
		if (nbHumains > 0 && !ferme && coeffDanger != 0) {
			transfertTime += coeffDanger * (float) base.Cons.deltaTime;
			while (transfertTime >= 0.5) {
				transfertTime -= Math.random() * 0.5;
				for (int i = 0; i <= 7; i++) {
					System.out.println(i);

					if (ArrayModulesContact[i] > -1) {
						if (nbHumains > 0
								&& myParent.modules[ArrayModulesContact[i]].capaciteHumaine > myParent.modules[ArrayModulesContact[i]].nbHumains
								&& myParent.modules[ArrayModulesContact[i]].ferme == false) {
							nbHumains -= 1;
							myParent.modules[ArrayModulesContact[i]].nbHumains += 1;
						}
					}
				}
			}
		}

	}

	public void updatePositionRel(Vect3D centreGrav) {
		this.positionRelative.translate(centreGrav.multiply(-1));
	}

	/**
	 * Calcul le moment dynamique appliqué par le module sur le vaisseau en
	 * fonction de la force appliquée sur le module
	 * 
	 * @param force
	 *            La force appliqué sur le module dans son propre repère
	 * @return Le moment dynamique appliqué sur le centre de gravité du vaisseau
	 */
	public Vect3D getMoment(Vect3D force) {
		Matrix rotx = new Matrix();
		rotx.setCoef(1, 1, 1);
		rotx.setCoef(2, 2, Math.cos(orientation.x));
		rotx.setCoef(3, 3, Math.cos(orientation.x));
		rotx.setCoef(2, 3, -Math.sin(orientation.x));
		rotx.setCoef(3, 2, Math.sin(orientation.x));

		Matrix roty = new Matrix();
		roty.setCoef(2, 2, 1);
		roty.setCoef(1, 1, Math.cos(orientation.y));
		roty.setCoef(3, 3, Math.cos(orientation.y));
		roty.setCoef(1, 3, Math.sin(orientation.y));
		roty.setCoef(3, 1, -Math.sin(orientation.y));

		Matrix rotz = new Matrix();
		rotz.setCoef(3, 3, 1);
		rotz.setCoef(1, 1, Math.cos(orientation.z));
		rotz.setCoef(2, 2, Math.cos(orientation.z));
		rotz.setCoef(1, 2, -Math.sin(orientation.z));
		rotz.setCoef(2, 1, Math.sin(orientation.z));

		Matrix passMatrix = Matrix.multiply(Matrix.multiply(rotz, roty), rotx);

		// La force appliquée sue le modules dans le repère du vaisseau
		Vect3D newForce = passMatrix.multiply(force);

		return positionRelative.multiply(newForce);

	}

}