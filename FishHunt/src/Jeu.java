import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;

public class Jeu {

    ArrayList<Bulles> bulles = new ArrayList<>();
    ArrayList<Poissons> poissons = new ArrayList<>();
    ArrayList<PoissonsSpeciaux> specialFish = new ArrayList<>();
    Cible cible;
    ArrayList<Balles> balles = new ArrayList<>();
    Score scoreAfficher = new Score();
    Vie nombreVie = new Vie();
    protected boolean fini;
    protected int niveau = 1;
    protected double tempsDebut = 0; //Ecoulement debut partie et niveau
    protected double tempsFin = 0; //Ecoulement lors du gameover

    public Jeu(){ //Constructeur

        for(int i = 0; i < 3; i++){
            bulles.add(new Bulles(Math.random()*FishHunt.WIDTH));
            for(int j = 0; j < 5; j++){
                bulles.add(new Bulles((bulles.get(i).x-20)+(Math.random()*40)));
            }
        }
        cible = new Cible();
        nombreVie.nombreVie = 3;
        tempsDebut = 0;
        tempsFin = 0;
        fini = false;
        scoreAfficher.score = 0;

    }

    /**
     * Dessine toutes nos entités sur le GraphicsContext
     * du jeu, contient aussi conditions affichage niveau
     * et GameOver

     *
     * @param context
     */
    public void draw(GraphicsContext context){
        context.setFill(Color.DARKBLUE);
        context.fillRect(0, 0, FishHunt.WIDTH, FishHunt.HEIGHT);

        for(int i = 0; i < bulles.size(); i++){ //Dessin des bulles
            bulles.get(i).draw(context);
        }

        for(int j = 0; j < poissons.size(); j++){
            poissons.get(j).draw(context);
        }

        for(int i = 0; i < specialFish.size(); i++){
            specialFish.get(i).draw(context);
        }

        for(int i = 0; i < balles.size(); i++){
            balles.get(i).draw(context);
        }

        scoreAfficher.draw(context);
        nombreVie.draw(context);
        if((poissons.size() == 0 && specialFish.size() == 0)){
            nextLevel(context, tempsDebut);
        }

        if(nombreVie.nombreVie == 0 || fini){
            afficherGameOver(context, tempsFin);
        }

    }

    //Compteurs utilisés dans update(dt)
    double tempsEcouleBulles = 0;
    double tempsEcoulePoissons = 0;
    double tempsEcouleSpecialFish = 0;
    double compteurNiveau = 0;

    /**
     * Update de toutes les entitées de notre jeu.
     * Détecte aussi les conditions de fin de partie
     * de début de niveau ou de perte de vie
     *
     * @param dt
     */
    public void update(double dt){

        for(int i = 0; i < bulles.size(); i++){ //Update des bulles
            bulles.get(i).update(dt);
        }
        tempsEcouleBulles += dt;
        if(tempsEcouleBulles > 3){ //Création de nouvelles bulles chaque 3 secondes
            for(int j = 0; j < bulles.size(); j++){
                bulles.remove(j);
            }
            for(int i = 0; i < 3; i++){
                bulles.add(new Bulles(Math.random()*FishHunt.WIDTH));
                for(int k = 0; k < 5; k++){
                    bulles.add(new Bulles((bulles.get(i).x-20)+(Math.random()*40)));
                }
            }
            tempsEcouleBulles = 0;
        }

        tempsEcoulePoissons += dt;
        if(tempsEcoulePoissons > 3){
            poissons.add(new Poissons(this.niveau));
            tempsEcoulePoissons = 0;
        }

        for(int i = 0; i < poissons.size(); i++){
            poissons.get(i).update(dt);
        }

        tempsEcouleSpecialFish += dt;
        if(tempsEcouleSpecialFish > 5 && niveau >= 2){
            specialFish.add(choixSpecialFish());
            tempsEcouleSpecialFish = 0;
        }
        for(int i = 0; i < specialFish.size(); i++){
            specialFish.get(i).update(dt);
        }


        for(int i = 0; i < balles.size(); i++){
            balles.get(i).update(dt);
        }
        for(int i = 0; i < balles.size(); i++){
            for(int j = 0; j < poissons.size(); j++){
                if(balles.get(i).intersects(poissons.get(j))){
                    poissons.remove(j);
                    scoreAfficher.ajoutPoint();
                    compteurNiveau += 1;
                }
            }
        }
        for(int i = 0; i < balles.size(); i++){
            if(balles.get(i).rayon <= 0){
                balles.remove(i);
            }
        }

        for(int i = 0; i < balles.size(); i++){
            for(int j = 0; j < specialFish.size(); j++){
                if(balles.get(i).intersects(specialFish.get(j))){
                    specialFish.remove(j);
                    scoreAfficher.ajoutPoint();
                    compteurNiveau += 1;
                }
            }
        }

        scoreAfficher.update(dt);
        nombreVie.update(dt);
        nombreVie.retirerViePoisson(poissons);
        nombreVie.retirerVieSpecialFish(specialFish);

        tempsDebut += dt;
        if(compteurNiveau/5 == 1){
            niveau += 1;
            compteurNiveau = 0;
            for(int i = 0; i < poissons.size(); i++){
                poissons.remove(i);

            }
            for(int j = 0; j < specialFish.size(); j++){
                specialFish.remove(j);

            }
            tempsEcouleSpecialFish = 0;
            tempsEcoulePoissons = 0;

        }
        finPartie();
    }

    /**
     * Permet de choisir aléatoirement quel
     * sera le poisson special qui spawn
     *
     * @return
     */
    public PoissonsSpeciaux choixSpecialFish(){
        double random = Math.random();
        PoissonsSpeciaux p = null;

        if(random > 0.5){
            p = new Crabe(this.niveau);
        } else {
            p = new Star(this.niveau);
        }
        return p;
    }


    /**
     * Permet de tirer une balle, donc
     * créé une instance de Balle aux
     * coordonnées x,y
     *
     * @param x
     * @param y
     */
    public void tirerBalle(double x, double y){
        if(!this.fini){
            balles.add(new Balles(x, y));
        }
    }

    /**
     * Détecte si la partie est terminée,
     * si oui, pose le boolean fini à true.
     */
    public void finPartie(){ //Detecte fin de partie

        if(nombreVie.nombreVie == 0 || fini){
            this.fini = true;
        }
    }


    /**
     * Permet de décider si on affiche
     * 'Level x' ou non. Utilisé dans draw();
     *
     * @param context
     * @param tempsDebut
     */
    public void nextLevel(GraphicsContext context, double tempsDebut){
        if(compteurNiveau == 5 || compteurNiveau == 0){
            afficherNiveau(context, tempsDebut);
        }
    }


    /**
     * Pour concretement dessiner le niveau.
     * Utilisé dans nextLevel();
     *
     * @param context
     * @param tempsDebut
     */
    public void afficherNiveau(GraphicsContext context, double tempsDebut){

        context.setFill(Color.WHITE);
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);
        context.setFont(Font.font(65));
        if(niveau >= 2){
            context.fillText("Level " +this.niveau,
                    FishHunt.WIDTH/2, FishHunt.HEIGHT/2 -20);
        }
        if(niveau == 1){
            if(tempsDebut < 3){
                context.fillText("Level " +this.niveau,
                            FishHunt.WIDTH/2, FishHunt.HEIGHT/2 -20);
            }
        }
    }


    /**
     * Pour décider si on affiche Game Over
     *
     * @param context
     * @param tempsFin
     */
    public void afficherGameOver(GraphicsContext context, double tempsFin){

        context.setFill(Color.RED);
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);
        context.setFont(Font.font(65));

        if(tempsFin <= 3){
            context.fillText("GAME OVER", FishHunt.WIDTH/2, FishHunt.HEIGHT/2 -20);
        }
    }


    /**
     * DÉBUG : si on appuie sur L
     * fin de partie
     */
    public void forceEnd(){

        this.fini = true;
    }


    /**
     * DÉBUG : appuie sur J
     * score augmente de 1
     */
    public void debugAjoutPoint(){
        this.scoreAfficher.score += 1;
        this.compteurNiveau += 1;
    }


    /**
     * DEBUG : appuie sur H
     * niveau augmente de 1
     */
    public void debugNextlevel(){

        compteurNiveau = 5;
    }


    /**
     * DEBUG : appuie sur K
     * vie augmente de 1
     */
    public void addLife(){
        this.nombreVie.nombreVie += 1;
        if(nombreVie.nombreVie > 3){
            this.nombreVie.nombreVie = 3;
        }
    }




}
