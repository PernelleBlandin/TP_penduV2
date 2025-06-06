import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Platform;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;

import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonHome;
    /**
     * le bouton pour avoir des informations comme les règles
     */
    private Button boutonInfo;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button bJouer;

    private Button boutonNouvMot;

    private Text titre;

    private ToggleGroup lesRadiosBtn;
    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");

        this.niveaux = new ArrayList<>();
        this.dessin = new ImageView("../img/pendu0.png");
        this.motCrypte = new Text();
        this.pg = new ProgressBar();
        this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ-", new ControleurLettres(this.modelePendu, this), 8);
        this.leNiveau = new Text();
        this.chrono = new Chronometre();
        this.panelCentral = new BorderPane();
        

        this.boutonHome = new Button();
        this.boutonInfo = new Button();
        this.boutonParametres = new Button();
        this.boutonNouvMot = new Button("Nouveau mot");
        
        Image imgInfo = new Image("../img/info.png");
        ImageView viewInfo = new ImageView(imgInfo);
        this.boutonInfo.setGraphic(viewInfo);
        viewInfo.setFitHeight(25);
        viewInfo.setFitWidth(25);


        Image imgParam = new Image("../img/parametres.png");
        ImageView viewParam = new ImageView(imgParam);
        this.boutonParametres.setGraphic(viewParam);
        viewParam.setFitHeight(25);
        viewParam.setFitWidth(25);

        Image imgHome = new Image("../img/home.png");
        ImageView viewHome = new ImageView(imgHome);
        this.boutonHome.setGraphic(viewHome);
        viewHome.setFitHeight(25);
        viewHome.setFitWidth(25);
        
        this.titre = new Text("Jeu du pendu");
        this.titre.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        this.modeAccueil();
        
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.banniere());
        fenetre.setCenter(this.panelCentral);
        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private BorderPane banniere(){
        // A implementer          
        BorderPane banniere = new BorderPane();
        banniere.setLeft(this.titre);

        HBox lesBoutons = new HBox();
        lesBoutons.getChildren().addAll(this.boutonHome, this.boutonParametres, this.boutonInfo);
        banniere.setRight(lesBoutons);

        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, null, null);
        Background background = new Background(backgroundFill);
        banniere.setBackground(background);
        banniere.setPadding(new Insets(0, 10, 30, 10));

        return banniere;
    }

    // /**
     // * @return le panel du chronomètre
     // */
    // private TitledPane leChrono(){
        // A implementer
        // TitledPane res = new TitledPane();
        // return res;
    // }

    
    // /**
     // * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     // */
    private BorderPane fenetreAccueil(){
        // A implementer    
        BorderPane res = new BorderPane();
        
        VBox pageCentre = new VBox(15);
        this.bJouer = new Button("Lancer une partie");
        pageCentre.getChildren().add(bJouer);

        RadioButton facile = new RadioButton("Facile");
        RadioButton medium = new RadioButton("Médium");
        RadioButton difficile = new RadioButton("Difficile");
        RadioButton expert = new RadioButton("Expert");
        this.lesRadiosBtn = new ToggleGroup();

        facile.setToggleGroup(lesRadiosBtn);
        medium.setToggleGroup(lesRadiosBtn);
        difficile.setToggleGroup(lesRadiosBtn);
        expert.setToggleGroup(lesRadiosBtn);

        VBox vBoxBouton = new VBox(10);
        vBoxBouton.getChildren().addAll(facile, medium, difficile, expert);

        TitledPane titledDifficulte = new TitledPane();
        titledDifficulte.setText("Niveau de difficultés");
        titledDifficulte.setCollapsible(false);
        titledDifficulte.setContent(vBoxBouton);
        pageCentre.setPadding(new Insets(30,50,0,50));
        pageCentre.getChildren().add(titledDifficulte);

        boutonHome.setDisable(true);
        boutonParametres.setDisable(false);
        
        res.setCenter(pageCentre);

        ControleurInfos infos = new ControleurInfos(this);
        this.boutonInfo.setOnAction(infos);

        ControleurLancerPartie nouvellePartie = new ControleurLancerPartie(modelePendu, this);
        this.bJouer.setOnAction(nouvellePartie);

        ControleurNiveau choixNiv = new ControleurNiveau(modelePendu);
        facile.setOnAction(choixNiv);
        medium.setOnAction(choixNiv);
        difficile.setOnAction(choixNiv);
        expert.setOnAction(choixNiv);
   
        return res;
    }


    // /**
     // * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     // *         de progression et le clavier
     // */
    private BorderPane fenetreJeu(){
        // A implementer
        BorderPane res = new BorderPane();
        boutonHome.setDisable(false);
        boutonParametres.setDisable(true);

        RetourAccueil versAccueil = new RetourAccueil(modelePendu, this);
        boutonHome.setOnAction(versAccueil);

        VBox jeuPrincp = new VBox();
        jeuPrincp.setPadding(new Insets(30,50,0,50));
        
        this.motCrypte = new Text(modelePendu.getMotCrypte());
        jeuPrincp.getChildren().addAll(this.motCrypte, this.dessin, this.pg, this.clavier);
        jeuPrincp.setSpacing(10);
        jeuPrincp.setAlignment(Pos.CENTER);

        //A enlever apres les essais
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTGREEN, null, null);
        Background background = new Background(backgroundFill);
        jeuPrincp.setBackground(background);


        VBox aside = new VBox(20);
        aside.setPadding(new Insets(30,50,0,50));
        jeuPrincp.setSpacing(10);

        if(this.modelePendu.getNiveau() == 0) {
            this.leNiveau.setText("Niveau Facile");
        }
        else if(this.modelePendu.getNiveau() == 1) {
            this.leNiveau.setText("Niveau Médium");
        }
        else if(this.modelePendu.getNiveau() == 2) {
            this.leNiveau.setText("Niveau Difficile");
        }
        else if(this.modelePendu.getNiveau() == 1) {
            this.leNiveau.setText("Niveau Expert");
        }

        ControleurLancerPartie nouveauMot = new ControleurLancerPartie(modelePendu, this);
        boutonNouvMot.setOnAction(nouveauMot);

        aside.getChildren().addAll(this.leNiveau, this.boutonNouvMot);

        res.setCenter(jeuPrincp);
        res.setRight(aside);
        return res;
    }


    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil(){
        this.panelCentral.setCenter(fenetreAccueil());
        // this.panelCentral = fenetreAccueil();
        // this.root.setCenter(this.panelCentral);
    }
    
    public void modeJeu(){
        this.panelCentral.setCenter(fenetreJeu());
        //this.panelCentral = fenetreJeu();
        //this.root.setCenter(this.panelCentral);
    }
    
    public void modeParametres(){
        
    }

    /** lance une partie */
    public void lancePartie(){
        this.modeJeu();
        this.majAffichage();
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        // A implementer
        this.pg.setProgress(1.0 - ((double) modelePendu.getNbErreursRestants()/modelePendu.getNbErreursMax()));

        if(!(this.motCrypte == null)){
        this.motCrypte.setText(this.modelePendu.getMotCrypte());
        }

        if(this.lesImages != null) {
            this.dessin.setImage(lesImages.get(10 - modelePendu.getNbErreursRestants()));
        }

    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        // A implémenter
        return null; // A enlever
    }

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n On l'arrête tout de même ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        alert.showAndWait();
        return alert;
     }
        
    public Alert popUpReglesDuJeu(){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("Alerte de type information");
         alert.setHeaderText("Règles du jeu");
         alert.setContentText("1-choisir un niveau\n2-choisir une lettre\n3-Si le mot ne contient pas la lettre, un trait est ajouté au dessin\n4-Quand le dessin est fini, vous avez perdu !");
         alert.showAndWait();
         return alert;
     }
    
     public Alert popUpMessageGagne(){
    //     // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alerte de type information");
        alert.setHeaderText("Victoire");
        alert.setContentText("Bravo, vous avez gagné la partie !!!");  
        alert.showAndWait();
        return alert;
     }
    
     public Alert popUpMessagePerdu(){
    //     // A implementer    
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("Alerte de type information");
         alert.setHeaderText("Perdu...");
         alert.setContentText("Le jeu vous a vaincu."); 
         alert.showAndWait();
         return alert;
     }


    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
