import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle ;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Génère la vue d'un clavier et associe le contrôleur aux touches
 * le choix ici est d'en faire un héritié d'un TilePane
 */
public class Clavier extends TilePane{
    /**
     * il est conseillé de stocker les touches dans un ArrayList
     */
    private List<Button> clavier;

    /**
     * constructeur du clavier
     * @param lesLettres une chaine de caractères qui contient les lettres à mettre sur les touches
     * @param actionTouches le contrôleur des touches
     * @param tailleLigne nombre de touches par ligne
     */
    public Clavier(String lesLettres, EventHandler<ActionEvent> actionTouches, int tailleLigne) {

        this.clavier = new ArrayList<>();
        this.setVgap(5);
        this.setHgap(5);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);
        this.setPrefColumns(tailleLigne);

        for(int i=0; i<lesLettres.length(); i++) {
            Button boutonLettre = new Button(Character.toString(lesLettres.charAt(i)));
            boutonLettre.setOnAction(actionTouches);
            this.clavier.add(boutonLettre);
            boutonLettre.setShape(new Circle(3));
            this.getChildren().add(boutonLettre);      
            
        }
    }

        public List<Button> getClavier() {
        return this.clavier;
    }

    /**
     * permet de désactiver certaines touches du clavier (et active les autres)
     * @param touchesDesactivees une chaine de caractères contenant la liste des touches désactivées
     */
   public void desactiveTouches(Set<String> touchesDesactivees){
        for(String toucheADeact : touchesDesactivees) {
            for(Button boutonLettre : this.clavier) {
                if(touchesDesactivees.contains(boutonLettre.getText())) {
                    boutonLettre.setDisable(true);
                }
                else {
                    boutonLettre.setDisable(false);
                }
            }
        }
        
    }
}
