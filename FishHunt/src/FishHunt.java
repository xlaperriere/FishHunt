/*

Xavier Laperriere

 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;



public class FishHunt extends Application{

    public static final int HEIGHT = 480, WIDTH = 680;
    Scene menu, jeu, scores;
    Background background = new Background(new BackgroundFill(Color.DARKBLUE,
            CornerRadii.EMPTY, Insets.EMPTY));
    Stage stage;
    boolean justFinished;
    Controleur controleur;



    public static void main(String[] args){

        FishHunt.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{

        this.stage = stage;
        stage.setResizable(false);
        stage.setTitle("FishHunt");

        stage.setScene(creerSceneMenu());
        stage.show();

    }


    /**
     * Créé la scene pour le Menu.
     * On l'appel à chaque fois qu'on ouvre le jeu
     * ou qu'on clique sur ajouter ou Menu (boutons)
     * de la scene des scores
     *
     * @return sceneMenu
     */
    public Scene creerSceneMenu(){
        justFinished = false;

        BorderPane root = new BorderPane();
        StackPane pane = new StackPane();
        root.setCenter(pane);
        Image logo = new Image("/images/logo.png");
        ImageView imageView = new ImageView(logo);
        imageView.setPreserveRatio(true);

        pane.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);


        VBox root1 = new VBox(10);
        root1.setAlignment(Pos.CENTER);
        root.setBottom(root1);

        menu = new Scene(root, WIDTH, HEIGHT);
        root.setBackground(background);


        //Bouton nouvelle partie
        Button newGame = new Button("Nouvelle partie!");
        newGame.setOnAction((event) -> stage.setScene(creerSceneJeu()));
        root1.getChildren().add(newGame);

        //Bouton meilleurs scores
        Button boutonScore = new Button("Meilleurs scores");
        boutonScore.setOnAction((event) -> {
            stage.setScene(creerSceneScores());
        });
        root1.getChildren().add(boutonScore);

        VBox.setMargin(boutonScore, new Insets(0, 0, 20, 0));

        root1.setOnKeyPressed((pressed) ->{
            if(pressed.getCode() == KeyCode.ESCAPE){
                Platform.exit();
            }
        });

        return menu;
    }


    /**
     * Créé la scene de jeu.
     * Appellé lorsque l'on clique sur le bouton
     * Nouvelle Partie!
     *
     * @return sceneJeu
     */
    public Scene creerSceneJeu(){

        Pane root2 = new Pane();
        jeu = new Scene(root2, WIDTH, HEIGHT);
        root2.setBackground(background);

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root2.getChildren().add(canvas);
        GraphicsContext context = canvas.getGraphicsContext2D();
        controleur = new Controleur();
        controleur.draw(context);

        //Notre animation timer
        AnimationTimer timer = new AnimationTimer() {

            long lastTime = 0;
            @Override
            public void handle(long now) {
                if(lastTime == 0){
                    lastTime = now;
                    return;
                }
                double deltaTemps = (now -lastTime) * 1e-9;

                controleur.update(deltaTemps);
                controleur.draw(context);
                if(controleur.finPartie(deltaTemps)){
                    controleur.gameOver = true;
                    justFinished = true;
                    stage.setScene(creerSceneScores());
                    this.stop();
                    controleur.gameOver = false;

                }

                lastTime = now;
            }
        };

        //lorsque l'on bouge notre curseur, positions update
        root2.getChildren().add(controleur.cible.imageCible);
        root2.setOnMouseMoved((event) -> {
            double w =
                    controleur.cible.largeur;
            double h =
                    controleur.cible.hauteur;
            double x = event.getX() - w / 2;
            double y = event.getY() - h / 2;
            controleur.cible.imageCible.setX(x);
            controleur.cible.imageCible.setY(y);

        });

        //Clic pour tirer une balle
        root2.setOnMouseClicked((clic) ->{
            if(clic.getButton() == MouseButton.PRIMARY){
                controleur.tirerBalle(clic.getX(), clic.getY());
            }
        });

        //OnKeyPressed pour le mode débug
        jeu.setOnKeyPressed((pressed) ->{
            if(pressed.getCode() == KeyCode.L){
                controleur.forceEnd();
            }
            if(pressed.getCode() == KeyCode.J){
                controleur.debugAjoutPoint();
            }
            if(pressed.getCode() == KeyCode.H){
                controleur.debugNextLevel();
            }
            if(pressed.getCode() == KeyCode.K){
                controleur.addLife();
            }
            if(pressed.getCode() == KeyCode.ESCAPE){
                Platform.exit();
            }
        });

        timer.start();

        return jeu;
    }


    /**
     * Créé la scene qui affiche les highscores.
     * Appellé à la fin d'une partie ou lorsque
     * l'on clique sur le boutton Meilleurs Scores
     *
     * @return
     */
    public Scene creerSceneScores(){

        VBox root3 = new VBox(10);
        root3.setAlignment(Pos.CENTER);
        scores = new Scene(root3, WIDTH, HEIGHT);


        Text titre = new Text("Meilleurs scores");
        titre.setFont(Font.font(25));

        ListView<String> highScores = new ListView();
        BufferedReader reader;
        ArrayList<String> listScores = new ArrayList<>();
        ArrayList<Integer> sortedScores = new ArrayList<>();

        //Fichier HighScores
        File lesScores = new File("HighScores.txt");

        //Lit dans le fichier text HighScores
        try{
            FileReader scoreFile = new FileReader(lesScores);
            reader = new BufferedReader(scoreFile);
            String ligne;


            while((ligne = reader.readLine()) != null){
                listScores.add(ligne);
            }

            reader.close();

        } catch (FileNotFoundException e){

        } catch (IOException f){ }

        ArrayList<Integer> sorted2 = new ArrayList<>();

        for(int j = 1; j < listScores.size(); j += 2){
            int p = Integer.parseInt(listScores.get(j));
            sortedScores.add(p);
            sorted2.add(p);
        }
        Collections.sort(sorted2, Collections.reverseOrder());

        ArrayList<String> sortedScoresString = new ArrayList<>();
        for(int i = 0; i < sorted2.size(); i ++){
            sortedScoresString.add(sorted2.get(i) +"");
        }

        ArrayList<String> finalSorted = new ArrayList<>();
        for(int i = 0; i < sortedScoresString.size(); i++){
            for(int j = 0; j < listScores.size(); j += 2){
                if(sortedScoresString.get(i).compareTo(listScores.get(j+1)) == 0){
                    finalSorted.add(listScores.get(j) +" - " +sortedScoresString.get(i));
                }
            }
        }


        //On affiche dans le listView!
        for(int i = 0; i < finalSorted.size(); i++){

            if(finalSorted.size() != 0){
                highScores.getItems().add("#" +(i+1) +" - " +finalSorted.get(i));
            }
            if(finalSorted.size() > 10){
                finalSorted.remove(10);
            }
        }


        //Bouton pour revenir au menu
        Button revenir = new Button("Menu");
        revenir.setOnAction((event) -> stage.setScene(creerSceneMenu()));
        root3.getChildren().add(titre);
        root3.getChildren().add(highScores);

        //Si on vient de terminer une partie : demande nom en entre le score
        if(justFinished){
            HBox demandeScore = new HBox();
            demandeScore.setAlignment(Pos.CENTER);
            demandeScore.setSpacing(20);
            Text tonNom = new Text("Votre nom: ");
            TextField nom = new TextField();
            Text points = new Text("a fait " +controleur.getScore() +" points!");
            Button ajouter = new Button("Ajouter");

            //boutton qui ajoute le score et nous retourne ensuite au menu
            ajouter.setOnAction((action) -> {
                try{
                    FileWriter fr = new FileWriter(lesScores, true);
                    BufferedWriter writer = new BufferedWriter(fr);

                    writer.append(nom.getText());
                    writer.newLine();
                    writer.append(controleur.getScore()+"");
                    writer.newLine();
                    writer.close();

                } catch (IOException exception){
                }

                stage.setScene(creerSceneMenu());
            });

            demandeScore.getChildren().addAll(tonNom, nom, points, ajouter);
            root3.getChildren().add(demandeScore);

        }

        root3.getChildren().add(revenir);

        VBox.setMargin(revenir, new Insets(0, 0, 20, 0));
        VBox.setMargin(highScores, new Insets(0, 30, 0, 30));
        VBox.setMargin(titre, new Insets(20, 0, 0, 0));

        //Escape pour quitter
        highScores.setOnKeyPressed((pressed) ->{
            if(pressed.getCode() == KeyCode.ESCAPE){
                Platform.exit();
            }
        });

        return scores;
    }


}
