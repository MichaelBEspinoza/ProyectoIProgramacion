package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.data;


import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;

import java.io.FileWriter;
import java.io.IOException;

public class SaveDocument {
    public static void saveDocument(Document document, String filePath) throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(document, new FileWriter(filePath));
    }
}