import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton rejouer ou Lancer une partie
 */
public class ControleurLancerPartie implements EventHandler<ActionEvent> {
    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     **/
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param p vue du jeu
     */
    public ControleurLancerPartie(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * L'action consiste à recommencer une partie. Il faut vérifier qu'il n'y a pas une partie en cours
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        if (!(this.modelePendu.gagne() || this.modelePendu.perdu() || this.modelePendu.getNbErreursMax()==this.modelePendu.getNbErreursRestants())){
            Optional<ButtonType> reponse = this.vuePendu.popUpPartieEnCours().showAndWait(); // on lance la fenêtre popup et on attends la réponse
        // si la réponse est oui
            if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)){ 
                //this.vuePendu.getChrono().resetTime();
                this.vuePendu.lancePartie();
                System.out.println("On recommence une partie");
            }
            else{ 
                System.out.println("On reprend la partie.");
            }
        
        
       } 
        else{ 
            this.vuePendu.lancePartie();
        }
    }
}
