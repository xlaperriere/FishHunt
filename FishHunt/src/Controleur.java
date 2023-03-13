import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class Controleur {

    Jeu jeu;
    Cible cible;
    protected boolean ended = false;
    protected double ecoulement = 0;
    protected boolean gameOver = false; //Pour passer à la scene scores et demander le nom


    public Controleur(){ //Constructeur

        jeu = new Jeu();
        cible = jeu.cible;
    }

    void draw(GraphicsContext context){

        jeu.draw(context);
    }


    void update(double dt){

        jeu.update(dt);
    }


    void tirerBalle(double x, double y){

        jeu.tirerBalle(x, y);
    }


    boolean finPartie(double dt){

        if(jeu.fini){
            this.ecoulement += dt;
            if( this.ecoulement > 3){
                this.ended = true;

            }
        } else {
            this.ended = false;
        }
        return ended;
    }


    void forceEnd(){
        jeu.forceEnd();

    }

    void debugAjoutPoint(){

        jeu.debugAjoutPoint();
    }

    void debugNextLevel(){

        jeu.debugNextlevel();

    }

    void addLife(){

        jeu.addLife();
    }

    int getScore(){

        return jeu.scoreAfficher.score;
    }

}
