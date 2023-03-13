import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Balles extends Entity {

    //Attributs
    protected int rayon = 100;
    protected int vitesseRayon = 300;

    public Balles(double x, double y){ //Constructeur
        this.rayon = rayon;
        this.vitesseRayon = vitesseRayon;

        this.x = x;
        this.y = y;
    }


    /**
     * Dessine la balle
     *
     * @param context
     */
    public void draw(GraphicsContext context){
        context.setFill(Color.BLACK);
        context.fillOval(this.x - rayon/2, this.y- rayon/2, this.rayon, this.rayon);
    }


    /**
     * Update rayon de la balle.
     * Pas sa position puisque sa position de fixé
     * lors du clic
     *
     * @param dt
     */
    public void update(double dt){
        this.rayon -= vitesseRayon*dt;
    }


    /**
     * Permet de détecter s'il y a collision entre
     * une balle et une autre entité (poisson ou poisson special)
     *
     * @param other
     * @return
     */
    public boolean intersects(Entity other){ //Détecter s'il y a intersection
        return (this.x > other.x && this.x < other.x +other.dimension
                && this.y > other.y && this.y < other.y + other.dimension
                && this.rayon < 4);
    }

}
