import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Star extends PoissonsSpeciaux {

    //Attributs
    Image imageStar = new Image("/images/star.png");
    protected double choixCote = Math.random();

    public Star(double niveau){ //Constructeur

        if(choixCote > 0.5){
            this.x = -100;
            this.vx = 100* (Math.cbrt(niveau)) + 200;
        } else {
            this.x = FishHunt.WIDTH +100;
            this.vx = -(100* (Math.cbrt(niveau)) + 200);
        }
        this.y = 96+ Math.random()*188;
        this.vy = 200;
    }


    /**
     * Permet de dessiner une étoile de mer
     *
     * @param context
     */
    public void draw(GraphicsContext context){
        context.drawImage(this.imageStar, this.x, this.y , dimension, dimension);
    }

    double temps = 0;

    /**
     * Update position et orientation de la
     * vitesse en y de l'étoile de mer
     *
     * @param dt
     */
    public void update(double dt){

        this.x += this.vx*dt;
        this.y += this.vy*dt;

        temps += dt;
        if(temps > 0.5){
            this.vy *= -1;
            temps = 0;
        }
    }



}
