import com.sun.prism.Graphics;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public class Poissons extends Entity{


    protected int ay = 100;
    protected double choixCote = Math.random();

    Image[] image = new Image[]{ //Images possibles pour un poisson
            new Image("/images/fish/00.png"),
            new Image("/images/fish/01.png"),
            new Image("/images/fish/02.png"),
            new Image("/images/fish/03.png"),
            new Image("/images/fish/04.png"),
            new Image("/images/fish/05.png"),
            new Image("/images/fish/06.png"),
            new Image("/images/fish/07.png"),
    };

    Image imageChoisi;

    Color choixCouleur =  Color.rgb((int)(Math.random()*255),
                        (int)(Math.random()*255), (int)(Math.random()*255));

    public Poissons(double niveau){ //Constructeur

        if(choixCote > 0.5){
            this.x = -100;
            this.vx = 100* (Math.cbrt(niveau)) + 200;
            imageChoisi = colorize(choixImage(), choixCouleur);
        } else {
            this.x = FishHunt.WIDTH;
            this.vx = -(100* (Math.cbrt(niveau)) + 200);
            imageChoisi = colorize(flop(imageChoisi = choixImage()), choixCouleur);
        }

        this.vy = -(100*Math.random() + 100);

        this.y = 96+ Math.random()*188;
    }


    /**
     * Dessine le poisson
     *
     * @param context
     */
    public void draw(GraphicsContext context){
        context.drawImage(this.imageChoisi, this.x, this.y , dimension, dimension);
    }


    /**
     * Update position et vitesse du poisson
     *
     * @param dt
     */
    public void update(double dt){

        this.x += this.vx*dt;
        this.vy += this.ay*dt;
        this.y += this.vy*dt;

    }


    /**
     * Permet de choisisr aléatoirement une image
     * parmi les 8 au choix
     *
     * @return image
     */
    public Image choixImage(){

        double choix = Math.random()*8;

        if(choix <= 1){
            imageChoisi = image[0];
        }
        if(choix <= 2 && choix > 1){
            imageChoisi = image[1];
        }
        if(choix <= 3 && choix > 2){
            imageChoisi = image[2];
        }
        if(choix <= 4 && choix > 3){
            imageChoisi = image[3];
        }
        if(choix <= 5 && choix > 4){
            imageChoisi = image[4];
        }
        if(choix <= 6 && choix > 5){
            imageChoisi = image[5];
        }
        if(choix <= 7 && choix > 6){
            imageChoisi = image[6];
        }
        if(choix <= 8 && choix > 7){
            imageChoisi = image[7];
        }

        return imageChoisi;
    }



}
