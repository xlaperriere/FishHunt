import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Bulles extends Entity{

    //Attributs
    protected double vitesseBulle;
    //Opacité VOLONTAIREMENT mise à 0.1 plutot que 0.4
    //pour rendre plus plaisant à voir (à mon gout)
    Color couleurBulle = Color.rgb(0 ,0, 255, 0.1);
    protected double rayon;
    ArrayList<Bulles> bulles = new ArrayList();


    public Bulles(double x){ //Constructeur

        this.x = FishHunt.WIDTH*Math.random();
        this.y = FishHunt.HEIGHT+10;
        this.vitesseBulle = -(350 + (Math.random() * 100));
        this.rayon = 10 +(Math.random()*30);
    }

    /**
     * Dessine les bulles aléatoirement
     *
     * @param context
     */
    public void draw(GraphicsContext context){
        context.setFill(couleurBulle);
        for(int i = 0; i < 5; i ++){
            context.fillOval(this.x, this.y, this.rayon,this.rayon);
        }
    }

    /**
     * Update la position des bulles en y
     *
     * @param dt
     */
    public void update(double dt){

        this.y += dt*this.vitesseBulle;
    }




}
