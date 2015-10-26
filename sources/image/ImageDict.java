/*
Question 4 :

Image Dict sera plus rapide que Image Tab, lorsque l'image sera grande ou comptabilisera un nombre de cases blanches élevè car elles seront ignoré. 
Les deux méthodes se valent, il faut donc choisir la plus efficace en fonction du nombre de cases blanches et de la taille de l'image.
 */

package image;
import dictionnaire.correction.*;

public class ImageDict implements ImageGrise {
    
    private Dictionnaire<Point,NiveauGris> dict;
    private int largeur;
    private int hauteur;

    /** Constructeur qui initaliase l'image a blanc **/
    public ImageDict(int x, int y) {
	largeur = x;
	hauteur = y;
	dict = new TabDict();
    }
    
    /** Retourne la largeur de l'image */
    public int largeur() {
	return largeur;
    }
    
    /** Retourne la hauteur de l'image */
    public int hauteur() {
	return hauteur;
    }

    /** Retourne le niveau de gris du point de coordonnées (x,y) */
    public NiveauGris pointEn(int x, int y) {
	if (dict.contientClef(new Point(x,y)))
	    return dict.valeurPour(new Point(x,y));
	else
	    return NiveauGris.BLANC;
    }
    
    /** Fixe le niveau de gris du point de coordonnées (x,y) à la valeur spécifiée */
    public void definirPoint(int x, int y, NiveauGris gris) {
        if (gris!=NiveauGris.BLANC)
	    dict.ajouter(new Point(x,y),gris);
    }
    
    /** Met en noir le point de coordonnées (x,y) */
    public void allumer(int x, int y) {
	definirPoint(x,y,NiveauGris.NOIR);
    }
    
    /** Met en blanc le point de coordonnées (x,y) */
    public void eteindre(int x, int y) {
	dict.enleverPour(new Point(x,y));
    }
    
    /** Donne une valeur aléatoire (noir ou blanc) à chaque point de l'image */
    public void randomize() {
	for (int i=0;i<this.largeur();i++) {
	    for (int j=0;j<this.hauteur();j++) {
		definirPoint(i,j,NiveauGris.randomizeNB());
	    }
	}   
    }
    
    /** Compte le nombre de points de l'image dont le niveau de gris est égal au niveau spécifié */
    public int compterPoints(NiveauGris gris) {
	int cpt=0;
      	for (int i=0;i<this.largeur();i++) {
	    for (int j=0;j<this.hauteur();j++) {
		if (this.pointEn(i,j).couleur()==gris.couleur())
		    cpt++;
	    }
	}
	return cpt;
    }
    
    /** Retourne une image qui est le négatif de l'image courante */
    public ImageGrise inverser() {
        ImageGrise tmp = this.dupliquer();
	for (int i=0;i<this.largeur();i++) {
	    for (int j=0;j<this.hauteur();j++) {
	        tmp.definirPoint(i,j,(this.pointEn(i,j).inverser()));
	    }
	}
	return tmp;
    }
    
    /** Retourne une image dont tous les points (sauf blancs) sont un niveau
     * plus clair que dans l'image courante */
    public ImageGrise eclaircir() {
        ImageGrise tmp = this.dupliquer();
	for (int i=0;i<this.largeur();i++) {
	    for (int j=0;j<this.hauteur();j++) {
	        if(!this.pointEn(i,j).estBlanc())
		    tmp.definirPoint(i,j,(this.pointEn(i,j).eclaircir()));
	    }
	}
	return tmp;
    }
    
    /** Retourne une image dont tous les points (sauf noirs) sont un niveau
     * plus foncé que dans l'image courante */
    public ImageGrise assombrir() {
	ImageGrise tmp = this.dupliquer();
	for (int i=0;i<this.largeur();i++) {
	    for (int j=0;j<this.hauteur();j++) {
	        if(!this.pointEn(i,j).estNoir())
		    tmp.definirPoint(i,j,(this.pointEn(i,j).assombrir()));
	    }
	}
	return tmp;
    }
    
    /** Retourne une <B>copie</B> de l'image courante */
    public ImageGrise dupliquer() {
	ImageGrise tmp = new ImageDict(this.largeur(),this.hauteur());
	for (int i=0;i<this.largeur();i++) {
	    for (int j=0;j<this.hauteur();j++) {
		tmp.definirPoint(i,j,this.pointEn(i,j));
	    }
	}
	return tmp;
    }
    
    /** Retourne une image en additionnant point par point les niveaux de gris de l'image 
     * courante et de l'image en paramètre (les deux images doivent être de même taille) */
    public ImageGrise ajouter(ImageGrise img) {
	ImageGrise tmp = new ImageDict(this.largeur(),this.hauteur());
	if (this.largeur()==img.largeur() && this.hauteur()==img.hauteur()) {
	    for (int i=0;i<this.largeur();i++) {
		for (int j=0;j<this.hauteur();j++) {
		    tmp.definirPoint(i,j,this.pointEn(i,j).ajouter(img.pointEn(i,j)));
		}
	    }
	}
	return tmp;
    }
    
    /** Retourne une image en retranchant point par point les niveaux de gris de l'image 
     * courante et de l'image en paramètre (les deux images doivent être de même taille) */
    public ImageGrise soustraire(ImageGrise img) {
	ImageGrise tmp = new ImageDict(this.largeur(),this.hauteur());
	if (this.largeur()==img.largeur() && this.hauteur()==img.hauteur()) {
	    for (int i=0;i<this.largeur();i++) {
		for (int j=0;j<this.hauteur();j++) {
		    tmp.definirPoint(i,j,this.pointEn(i,j).soustraire(img.pointEn(i,j)));
		}
	    }
	}
	return tmp;
    }
    
    /** Retourne une image en faisant un OU Exclusif (XOR) point par
     * point les niveaux de gris de l'image courante et de l'image en
     * paramètre (les deux images doivent être de même taille) */
    public ImageGrise XOR(ImageGrise img) {
	ImageGrise tmp = new ImageDict(this.largeur(),this.hauteur());
	if (this.largeur()==img.largeur() && this.hauteur()==img.hauteur()) {
	    for (int i=0;i<this.largeur();i++) {
		for (int j=0;j<this.hauteur();j++) {
		    tmp.definirPoint(i,j,this.pointEn(i,j).XOR(img.pointEn(i,j)));
		}
	    }
	}
	return tmp;
    }
    
    /** Retourne une image qui représente "l'intersection" de l'image courante et de l'image 
     * en paramètre : seuls les points qui ont le même niveau de gris dans les deux images sont
     * conservés (les deux images doivent être de même taille) */
    public ImageGrise intersection(ImageGrise img) {
	ImageGrise tmp = new ImageDict(this.largeur(),this.hauteur());
	if (this.largeur()==img.largeur() && this.hauteur()==img.hauteur()) {
	    for (int i=0;i<this.largeur();i++) {
		for (int j=0;j<this.hauteur();j++) {
		    if (this.pointEn(i,j).couleur()==img.pointEn(i,j).couleur())
			tmp.definirPoint(i,j,this.pointEn(i,j));
		}
	    }
	}
	return tmp;
    }

    /** Retourne le niveau de gris moyen de l'image. Pour le calculer, il faut faire la 
     * moyenne des niveaux de chaque point de l'image (ce qui revient à compter combien il y
     * a de points de chaque niveau de gris possible) */
    public NiveauGris niveauMoyen() {
	int cpt=0;
	 for (int i=0;i<this.largeur();i++) {
		for (int j=0;j<this.hauteur();j++) {
		    cpt+=this.pointEn(i,j).ordinal();
		}
	    }
	 return NiveauGris.deNiveau(cpt);
    }
    
    /** Retourne une image obtenue en augmentant le contraste de l'image courante. Pour 
     * augmenter le contraste, il faut rendre les points sombres plus sombres qu'ils ne sont, 
     * et les points clairs plus clairs. Un bon moyen de procéder consiste à calculer le 
     * niveau de gris moyen de l'image, et assombrir (respectivement eclaircir) les points 
     * plus sombres (resp. plus clairs) que ce niveau moyen */
    public ImageGrise augmenterContraste() {
	ImageGrise tmp = new ImageDict(this.largeur(),this.hauteur());
	NiveauGris moy = niveauMoyen();
	for (int i=0;i<this.largeur();i++) {
	    for (int j=0;j<this.hauteur();j++) {
		if (this.pointEn(i,j).ordinal()>moy.ordinal())
		    tmp.definirPoint(i,j,this.pointEn(i,j).assombrir());
		else
		    tmp.definirPoint(i,j,this.pointEn(i,j).eclaircir());
	    }
	}
	return tmp;
    }
}
