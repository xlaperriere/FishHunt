import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Vie {

    //Attributs
    Image imagePoissonVie = new Image("/images/fish/00.png");
    protected int grosseur = 35;
    protected int posX, posY;
    protected int nombreVie = 3;
    protected int espacement = 50;


    public Vie(){ //Constructeur
        this.posX = FishHunt.WIDTH/2 - (grosseur/2) -espacement;
        this.posY = 65;
        this.nombreVie = nombreVie;
    }


    /**
     * Permet de dessiner les 3 poissons qui
     * sont le nombres de vies restantes
     *
     * @param context
     */
    public void draw(GraphicsContext context){
        if(nombreVie == 3){
            context.drawImage(imagePoissonVie, this.posX, this.posY, grosseur, grosseur);
            context.drawImage(imagePoissonVie, this.posX + espacement, this.posY, grosseur, grosseur);
            context.drawImage(imagePoissonVie, this.posX + espacement*2, this.posY, grosseur, grosseur);
        }
        if(nombreVie == 2){
            context.drawImage(imagePoissonVie, this.posX, this.posY, grosseur, grosseur);
            context.drawImage(imagePoissonVie, this.posX + espacement, this.posY, grosseur, grosseur);
        }
        if(nombreVie == 1){
            context.drawImage(imagePoissonVie, this.posX, this.posY, grosseur, grosseur);
        }

    }


    /**
     * Update le nombre de vie
     * @param dt
     */
    public void update(double dt){

        nombreVie = this.nombreVie;
    }


    /**
     * Retire une vie si un poisson sort de l'écran
     *
     * @param other
     */
    public void retirerViePoisson(ArrayList<Poissons> other){
        for(int i = 0; i < other.size(); i++){
            if(other.get(i).x < -110 || other.get(i).x > FishHunt.WIDTH +50){
                other.remove(i);
                this.nombreVie -= 1;
            }
        }
    }


    /**
     * Retire une vie si un poisson special sort de l'ecran
     * NOTE : on aurait sans doute pu fusionner les deux
     * fonctions ensembles
     *
     * @param other
     */
    public void retirerVieSpecialFish(ArrayList<PoissonsSpeciaux> other){
        for(int i = 0; i < other.size(); i++){
            if(other.get(i).x < -150 || other.get(i).x > FishHunt.WIDTH +100){
                other.remove(i);
                this.nombreVie -= 1;
            }
            if(this.nombreVie <= 0){
                this.nombreVie = 0;
            }
        }
    }

}
