package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.clientController;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Menu {

    @javafx.fxml.FXML
    private BorderPane bp;

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void searchOnAction(ActionEvent actionEvent) {
        loadPage("buscar.fxml");
    }

    @javafx.fxml.FXML
    public void ConnectionOnAction(ActionEvent actionEvent) {
        loadPage("conexion.fxml");

    }

    @javafx.fxml.FXML
    public void insertOnAction(ActionEvent actionEvent) {
        loadPage("Insertar.fxml");
    }
}
