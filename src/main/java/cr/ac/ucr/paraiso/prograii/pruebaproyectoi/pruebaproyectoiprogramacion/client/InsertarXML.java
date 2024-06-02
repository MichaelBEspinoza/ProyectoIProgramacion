package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain.DesignPattern;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.utility.Ordenamiento;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class InsertarXML {
    private final Ordenamiento ordenamiento = new Ordenamiento();
    private final GuardarXML guardarXML = new GuardarXML();

    public String insertar(DesignPattern DP, Document documento, Element raiz, String rutaDocumento) throws IOException, JDOMException {
        if (new ExisteXML().existe(DP, raiz))
            return "El patrón con el ID " + DP.getId() + " ya existe.";

        Element ePatron = new Element("patron");
        ePatron.setAttribute("idDelPatron", DP.getId());

        ePatron.addContent(new Element("nombre").setText(DP.getName()));
        ePatron.addContent(new Element("descripcion").setText(DP.getDescription()));
        ePatron.addContent(new Element("tipo").setText(DP.getType()));
        ePatron.addContent(new Element("ejemploDeCodigo").setText(DP.getCodeExamples()));
        ePatron.addContent(new Element("fechaAgregado").setText(DP.getFechaAgregado().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        ePatron.addContent(new Element("contexto").setText(DP.getContext()));
        ePatron.addContent(new Element("problema").setText(DP.getProblem()));
        ePatron.addContent(new Element("solucion").setText(DP.getSolution()));

        raiz.addContent(ePatron);
        ordenamiento.ordenarPatronesPorNombre();
        guardarXML.guardar(rutaDocumento, documento);
        return DP.toString();
    }
}
