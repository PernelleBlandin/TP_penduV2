import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.geometry.Pos;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Alert;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData ;
import javafx.scene.control.ButtonType ;
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

    private Text titre;

    private BorderPane root;

    private Stage primaryStage;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");

        this.niveaux = new ArrayList<>();
        this.dessin = new ImageView();
        this.motCrypte = new Text();
        this.pg = new ProgressBar();
        this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ/", new ControleurLettres(this.modelePendu, this), 7);
        this.leNiveau = new Text();
        this.chrono = new Chronometre();
        this.panelCentral = fenetreAccueil();


        this.boutonHome = new Button();
        this.boutonInfo = new Button();
        this.boutonParametres = new Button();
        Image imgInfo = new Image("../img/info.png");
        ImageView viewInfo = new ImageView(imgInfo);
        this.boutonInfo.setGraphic(viewInfo);

        Image imgParam = new Image("../img/parametres.png");
        ImageView viewParam = new ImageView(imgParam);
        this.boutonParametres.setGraphic(viewParam);

        Image imgHome = new Image("../img/home.png");
        ImageView viewHome = new ImageView(imgHome);
        this.boutonHome.setGraphic(viewHome);

        
        this.titre = new Text("Jeu du pendu");
        
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    // private Scene laScene(){
    //     BorderPane fenetre = new BorderPane();
    //     fenetre.setTop(this.banniere());
    //     fenetre.setCenter(this.panelCentral);
    //     return new Scene(fenetre, 800, 1000);
    // }

    /**
     * @return le panel contenant le titre du jeu
     */
    private BorderPane banniere(){
        // A implementer          
        BorderPane banniere = new BorderPane();
        //titre.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        banniere.setLeft(this.titre);


        HBox lesBoutons = new HBox();
        lesBoutons.getChildren().addAll(this.boutonHome, this.boutonParametres, this.boutonInfo);
        banniere.setRight(lesBoutons);

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
     // * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     // *         de progression et le clavier
     // */
    // private Pane fenetreJeu(){
        // A implementer
        // Pane res = new Pane();
        // return res;
    // }

    // /**
     // * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     // */
    private BorderPane fenetreAccueil(){
        // A implementer    
        BorderPane res = new BorderPane();
        res.setTop(banniere());
        //this.bJouer = new Button("Lancer une partie");
        res.setCenter(bJouer);

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
        this.panelCentral = fenetreAccueil();
        this.root.setCenter(this.panelCentral);
    }
    
    public void modeJeu(){
        // A implementer
    }
    
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        // A implementer
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        // A implementer
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
    //     alert.setTitle("Attention");
        return alert;
     }
        
    public Alert popUpReglesDuJeu(){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
    //     alert.setTitle("Alerte de type information");
    //     alert.setHeaderText("Règles du jeu");
    //     alert.SetContentText("1-choisir un niveau\n2-choisir une lettre\n3-Si le mot ne contient pas la lettre, un trait est ajouté au dessin\n4-Quand le dessin est fini, vous avez perdu !");
         return alert;
     }
    
     public Alert popUpMessageGagne(){
    //     // A implementer
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
    //     alert.setTitle("Alerte de type information");
    //     alert.setHeaderText("Victoire");
    //     alert.SetContentText("Bravo, vous avez gagné la partie !!!");   
         return alert;
     }
    
     public Alert popUpMessagePerdu(){
    //     // A implementer    
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
    //     alert.setTitle("Alerte de type information");
    //     alert.setHeaderText("Perdu...");
    //     alert.SetContentText("Le jeu vous a vaincu."); 
         return alert;
     }


    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        this.root = new BorderPane();
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        this.primaryStage = stage;
        this.root.setTop(banniere());
        this.panelCentral = fenetreAccueil();
        this.root.setCenter(this.panelCentral);

        Scene scene = new Scene(this.root, 800, 1000);
        stage.setScene(scene);
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
