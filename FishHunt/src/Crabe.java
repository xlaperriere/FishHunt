import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Crabe extends PoissonsSpeciaux {

    //Attributs
    Image imageCrabe = new Image("/images/crabe.png");
    protected double choixCote = Math.random();


    public Crabe(double niveau){ //Constructeur

        if(choixCote > 0.5){
            this.x = -50;
            this.vx = 1.3*(100* (Math.cbrt(niveau)) + 200);
        } else {
            this.x = FishHunt.WIDTH ;
            this.vx = -(1.3*(100* (Math.cbrt(niveau)) + 200));
        }

        this.y = 96+ Math.random()*188;
    }


    /**
     * Permet de dessiner un crabe
     *
     * @param context
     */
    public void draw(GraphicsContext context){
        context.drawImage(this.imageCrabe, this.x, this.y , dimension, dimension);
    }

    //Compteurs utilisé dans update();
    double temps = 0;
    int changementDirection = 0;

    /**
     * Update la position et orientation du deplacement
     * du crabe en fonction des conditions énoncés
     *
     * @param dt
     */
    public void update(double dt){

        this.x += this.vx*dt;
        this.y = y;

        temps += dt;
        if(temps > 0.5 && changementDirection%2 == 0){
            this.vx *= -1;
            changementDirection +=1;
            temps = 0;
        }
        if(temps > 0.25 && changementDirection%2 == 1){
            this.vx *= -1;
            changementDirection +=1;
            temps = 0;
        }
    }


}
