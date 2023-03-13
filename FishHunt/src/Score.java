import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Score {

    //Attributs
    Text scoreAfficher = new Text();
    protected int posX, posY;
    protected int score;

    public Score(){ //Constructeur

        this.posX = FishHunt.WIDTH/2;
        this.posY = 30;
    }


    /**
     * Ajoute un point.
     * Sera appelé dans Jeu lorsqu'il y a collision
     * entre balle et poissons
     */
    public void ajoutPoint(){

        this.score += 1;
    }


    /**
     * Permet de dessiner (afficher) le score
     * dans le haut de l'interface
     *
     * @param context
     */
    public void draw(GraphicsContext context){

        context.setFill(Color.WHITE);
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);
        context.setFont(Font.font(25));
        context.fillText(scoreAfficher.getText(), posX, posY);
    }


    /**
     * Update de score à chaque appel
     *
     * @param dt
     */
    public void update(double dt){
        scoreAfficher.setText("" +score);
    }

}
