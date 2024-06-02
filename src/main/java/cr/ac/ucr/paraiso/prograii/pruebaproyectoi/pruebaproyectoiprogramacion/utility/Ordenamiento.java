package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.utility;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.ClienteXMLData;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.GuardarXML;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Ordenamiento {
    ClienteXMLData client;
    {
        try {
            client = new ClienteXMLData("Proyecto.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        }
    }

    GuardarXML guardar = new GuardarXML();
    public void ordenarPatronesPorNombre() throws IOException, JDOMException {
        List<Element> patrones = client.getRaiz().getChildren("patron");

        // Ordena los patrones por nombre usando un comparador (ahora lambda).
        patrones.sort((p1, p2) -> {
            String nombre1 = p1.getChildText("nombre");
            String nombre2 = p2.getChildText("nombre");
            return nombre1.compareToIgnoreCase(nombre2);
        });// End of lambda [sort].
        client.guardar();
    }// End of method [ordenarPatronesPorNombre].

    public void ordenarPatronesPorID() throws IOException, JDOMException {
        List<Element> patrones = client.getRaiz().getChildren("patron");

        // Ordena los patrones por ID usando un comparador (ahora lambda).
        patrones.sort((p1, p2) -> {
            String id1 = p1.getAttributeValue("idDelPatron");
            String id2 = p2.getAttributeValue("idDelPatron");
            return id1.compareTo(id2);
        });// End of lambda [sort].
        client.guardar();
    }// End of method [ordenarPatronesPorID].

    public void ordenarPatronesPorTipo() throws IOException, JDOMException {
        List<Element> patrones = client.getRaiz().getChildren("patron");

        // Ordena los patrones por tipo usando un comparador (ahora lambda).
        patrones.sort((p1, p2) -> {
            String tipo1 = p1.getChildText("tipo");
            String tipo2 = p2.getChildText("tipo");
            return tipo1.compareToIgnoreCase(tipo2);
        });// End of lambda [sort].
        client.guardar();
    }// End of method [ordenarPatronesPorTipo].

    public void ordenarPatronesPorFecha() throws IOException, JDOMException {
        List<Element> patrones = client.getRaiz().getChildren("patron");

        // Ordena los patrones por fecha de agregado usando un comparador (ahora lambda).
        patrones.sort((p1, p2) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha1 = LocalDate.parse(p1.getChildText("fechaAgregado"), formatter);
            LocalDate fecha2 = LocalDate.parse(p2.getChildText("fechaAgregado"), formatter);
            return fecha1.compareTo(fecha2);
        });// End of lambda [sort].
        client.guardar();
    }// End of method [ordenarPatronesPorFecha].

}
