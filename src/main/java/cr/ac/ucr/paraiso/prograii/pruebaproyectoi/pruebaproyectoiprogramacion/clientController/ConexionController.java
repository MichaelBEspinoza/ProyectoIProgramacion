package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.clientController;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.PatternServer;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;

public class ConexionController {

    @javafx.fxml.FXML
    private Text label;

    @javafx.fxml.FXML
    public void disconnectOnAction(ActionEvent actionEvent) {

    }

    @javafx.fxml.FXML
    public void connectOnAction(ActionEvent actionEvent) {
        PatternServer servidor = new PatternServer();
    }

    @javafx.fxml.FXML
    public void exitOnAction(ActionEvent actionEvent) {
        System.exit(1);
    }
}
