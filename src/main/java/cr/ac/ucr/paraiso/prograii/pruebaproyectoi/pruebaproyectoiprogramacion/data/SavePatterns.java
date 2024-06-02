package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.data;


import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain.DesignPattern;
import org.jdom2.Document;
import org.jdom2.Element;

import java.io.IOException;
import java.util.List;

public class SavePatterns {
    private static final String FILE_PATH = "patterns.xml";

    public static void savePatterns(List<DesignPattern> patterns) throws IOException {
        Element rootElement = new Element("patterns");
        Document document = new Document(rootElement);
        for (DesignPattern pattern : patterns) {
            rootElement.addContent(pattern.toXMLElement());
        }
        SaveDocument.saveDocument(document, FILE_PATH);
    }
}