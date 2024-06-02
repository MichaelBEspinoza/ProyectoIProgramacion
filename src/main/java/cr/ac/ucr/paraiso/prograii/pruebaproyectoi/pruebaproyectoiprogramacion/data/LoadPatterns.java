package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.data;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain.DesignPattern;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class LoadPatterns {
    private static final String FILE_PATH = "patterns.xml";

    public static List<DesignPattern> loadPatterns() throws Exception {
        File xmlFile = new File(FILE_PATH);
        if (!xmlFile.exists()) {
            xmlFile.createNewFile();
            Element rootElement = new Element("patterns");
            Document document = new Document(rootElement);
            SaveDocument.saveDocument(document, FILE_PATH);
        }

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(xmlFile);
        Element rootElement = document.getRootElement();
        return rootElement.getChildren("pattern").stream()
                .map(DesignPattern::fromXMLElement)
                .collect(Collectors.toList());
    }
}