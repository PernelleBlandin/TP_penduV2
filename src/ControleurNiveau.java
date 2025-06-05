import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;

/**
 * Controleur des radio boutons gérant le niveau
 */
public class ControleurNiveau implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;


    /**
     * @param modelePendu modèle du jeu
     */
    public ControleurNiveau(MotMystere modelePendu) {
        this.modelePendu = modelePendu;
    }

    /**
     * gère le changement de niveau
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // A implémenter
        RadioButton radiobouton = (RadioButton) actionEvent.getTarget();
        String nomDuRadiobouton = radiobouton.getText();
        
        if(nomDuRadiobouton.equals("Facile")) {
            this.modelePendu.setNiveau(0);
        }
        else if(nomDuRadiobouton.equals("Médium")) {
            this.modelePendu.setNiveau(1);
        }
        else if(nomDuRadiobouton.equals("Difficile")) {
            this.modelePendu.setNiveau(2);
        }
        else if(nomDuRadiobouton.equals("expert")) {
            this.modelePendu.setNiveau(3);
        }

        System.out.println(nomDuRadiobouton);
    } 
}
