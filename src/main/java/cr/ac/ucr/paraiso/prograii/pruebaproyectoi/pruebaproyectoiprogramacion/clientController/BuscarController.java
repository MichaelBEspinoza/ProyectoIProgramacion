package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.clientController;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.HelloApplication;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.ClienteXMLData;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain.DesignPattern;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain.PatronNoEncontradoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.jdom2.JDOMException;

import java.io.IOException;

public class BuscarController {

    @javafx.fxml.FXML
    private TextField idArea;
    @javafx.fxml.FXML
    private TextArea textArea;
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
    public void ConnectionOnAction(ActionEvent actionEvent) {
        loadPage("conexion.fxml");
    }

    @javafx.fxml.FXML
    public void findOnAction(ActionEvent actionEvent) {
        try {

            DesignPattern designPattern = new DesignPattern();
            ClienteXMLData client = new ClienteXMLData("C:\\Users\\micha\\IdeaProjects\\Progra II\\Intellij\\PruebaProyectoIProgramacion\\src\\main\\java\\cr\\ac\\ucr\\paraiso\\prograii\\pruebaproyectoi\\pruebaproyectoiprogramacion\\Proyecto.xml");
            DesignPattern patron =  client.buscarPorID(idArea.getText());
            textArea.setText(patron.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        } catch (PatronNoEncontradoException e) {
            throw new RuntimeException(e);
        }

    }

    @javafx.fxml.FXML
    public void goBackOnAction(ActionEvent actionEvent) {
        loadPage("menu.fxml");
    }
}
