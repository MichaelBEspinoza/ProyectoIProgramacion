module cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.jdom2;

    opens cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion to javafx.fxml;
    opens cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.clientController to javafx.fxml;
    exports cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion;



}