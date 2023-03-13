import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Cible extends Entity {

    ImageView imageCible = new ImageView(new Image(
            "/images/cible.png",
            50, 50, false, true));

    protected double largeur;
    protected double hauteur;

    public Cible(){ //Constructeur
        this.imageCible = imageCible;
        this.largeur = imageCible.getBoundsInLocal().getWidth();
        this.hauteur = imageCible.getBoundsInLocal().getHeight();

    }


}
