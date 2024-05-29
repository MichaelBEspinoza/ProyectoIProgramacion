package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.data;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain.DesignPattern;
import org.jdom2.Element;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class PatternXmlData {
    public String generateIdentificador() {
        return UUID.randomUUID().toString();
    }

    private static final String FILE_PATH = "patterns.xml";

    public static List<DesignPattern> loadPatterns() throws Exception {
        File xmlFile = new File(FILE_PATH);
        if (!xmlFile.exists()) {
            xmlFile.createNewFile();
            Element rootElement = new Element("patterns");
            Document document = new Document(rootElement);
            saveDocument(document);
        }

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(xmlFile);
        Element rootElement = document.getRootElement();
        return rootElement.getChildren("pattern").stream()
                .map(DesignPattern::fromXMLElement)
                .collect(Collectors.toList());
    }

    public static void savePatterns(List<DesignPattern> patterns) throws IOException {
        Element rootElement = new Element("patterns");
        Document document = new Document(rootElement);
        for (DesignPattern pattern : patterns) {
            rootElement.addContent(pattern.toXMLElement());
        }
        saveDocument(document);
    }

    private static void saveDocument(Document document) throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(document, new FileWriter(FILE_PATH));
    }

    public void addPattern(DesignPattern pattern) throws Exception {
        List<DesignPattern> patterns = loadPatterns();
        patterns.add(pattern);
        savePatterns(patterns);
    }

    public DesignPattern findPatternById(String id) throws Exception {
        List<DesignPattern> patterns = loadPatterns();
        return patterns.stream()
                .filter(pattern -> pattern.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static void updatePattern(DesignPattern updatedPattern) throws Exception {
        List<DesignPattern> patterns = loadPatterns();
        for (int i = 0; i < patterns.size(); i++) {
            if (patterns.get(i).getId().equals(updatedPattern.getId())) {
                patterns.set(i, updatedPattern);
                break;
            }
        }
        savePatterns(patterns);
    }

    public static void deletePattern(String id) throws Exception {
        List<DesignPattern> patterns = loadPatterns();
        patterns.removeIf(pattern -> pattern.getId().equals(id));
        savePatterns(patterns);
    }
}