import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton Accueil
 */
public class RetourAccueil implements EventHandler<ActionEvent> {
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
     * @param vuePendu vue du jeu
     */
    public RetourAccueil(MotMystere modelePendu, Pendu vuePendu) {
        this.vuePendu = vuePendu;
        this.modelePendu = modelePendu;
    }


    /**
     * L'action consiste à retourner sur la page d'accueil. Il faut vérifier qu'il n'y avait pas une partie en cours
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        if(this.vuePendu.getChrono().getText()!="0") {
            Optional<ButtonType> rep = this.vuePendu.popUpPartieEnCours().showAndWait();

            if(rep.isPresent() && rep.get().equals(ButtonType.YES)) {
                System.out.println("Direction l'accueil");
                this.vuePendu.getChrono().resetTime();;
                this.vuePendu.modeAccueil();
            }

            else {
                System.out.println("On continue");
            }
        }
        this.vuePendu.modeAccueil();
        this.modelePendu.setMotATrouver();
    }
}
