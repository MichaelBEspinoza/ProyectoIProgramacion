package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain.DesignPattern;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain.PatronNoEncontradoException;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.utility.Ordenamiento;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClienteXMLData {

    /* Declaración de atributos. */
    private final String rutaDocumento;
    private final Element raiz;
    private final Document documento;

    Ordenamiento ordenamiento = new Ordenamiento();


    public ClienteXMLData(String rutaDocumento) throws IOException, JDOMException{
        File file = new File(rutaDocumento);
        if (!file.exists()) {
            // Si el archivo no existe, se encarga de crear el DOM y el documento XML.
            this.rutaDocumento = rutaDocumento;
            this.raiz = new Element("patrones"); // Asigna el nombre al tag 'raiz'.
            this.documento = new Document(raiz); // Agrega la raiz al documento XML.
            guardar(); // Guarda el XML respectivo.
        } else {
            // Se encarga de parsear el documento XML a un DOM.
            SAXBuilder saxBuilder= new SAXBuilder();
            saxBuilder.setIgnoringElementContentWhitespace(true); // Se define para ignorar los elementos con espacios en blanco.
            this.documento = saxBuilder.build(new File(rutaDocumento)); // Obtiene el documento XML utilizando el SAXBuilder.
            this.raiz = documento.getRootElement(); // Obtiene el elemento raiz del documento y lo asigna al atributo raiz.
            this.rutaDocumento = rutaDocumento;
        }// End of 'if-else'.
    }// End of constructor.

    public void guardar() throws IOException, JDOMException {
        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.output(this.documento, new PrintWriter(this.rutaDocumento)); // Escribe en el documento de la ruta.
        xmlOutputter.output(this.documento,System.out); // Imprime el XML en consola.
    }// End of method [guardar].

    public String insertar(DesignPattern DP) throws IOException, JDOMException {
        if (existe(DP))
            // Termina si el patrón ya se encuentra en el archivo.
            return "El patrón con el ID " + DP.getId() + " ya existe.";

        Element ePatron = new Element("patron");
        ePatron.setAttribute("idDelPatron", DP.getId()); // Añade el identificador.

        Element eNombre = new Element("nombre");
        eNombre.setText(DP.getName()); // Añade el nombre.
        Element eDescripcion = new Element("descripcion");
        eDescripcion.setText(DP.getDescription()); // Añade la descripción.
        Element eTipo = new Element("tipo");
        eTipo.setText(DP.getType()); // Añade el tipo.
        Element eEjemploCodigo = new Element("ejemploDeCodigo");
        eEjemploCodigo.setText(DP.getCodeExamples()); // Añade el ejemplo de código.
        Element eFechaAgregado = new Element("fechaAgregado");
        eFechaAgregado.setText(DP.getFechaAgregado().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))); // Añade la fecha de creación.
        Element eContext = new Element("contexto");
        eContext.setText(DP.getContext());
        Element eProblem = new Element("problema");
        eProblem.setText(DP.getProblem());
        Element eSolution = new Element("solucion");
        eSolution.setText(DP.getSolution());

        ePatron.addContent(eNombre);
        ePatron.addContent(eDescripcion);
        ePatron.addContent(eTipo);
        ePatron.addContent(eEjemploCodigo);
        ePatron.addContent(eFechaAgregado);
        ePatron.addContent(eContext);
        ePatron.addContent(eProblem);
        ePatron.addContent(eSolution);

        this.raiz.addContent(ePatron);
        ordenamiento.ordenarPatronesPorNombre();
        guardar();
        return DP.toString();
    }// End of method [insertar].

    public void eliminar(DesignPattern DP) throws IOException, JDOMException, PatronNoEncontradoException {
        String codigoDelPatron = DP.getId();
        List<Element> patrones = this.raiz.getChildren("patron");
        for (Element patron : patrones)
            if (patron.getAttributeValue("idDelPatron").equals(codigoDelPatron)) {
                patron.detach(); // Elimina el elemento del árbol XML.
                guardar(); // Guarda los cambios en el archivo XML.
                return; // Termina la búsqueda una vez que se ha encontrado y eliminado el elemento.
            }// End of 'if'.
        throw new PatronNoEncontradoException();
    }// End of method [eliminar].

    public void actualizar(String codigoDelPatron, DesignPattern nuevoPatron) throws IOException, JDOMException, PatronNoEncontradoException {
        List<Element> patrones = this.raiz.getChildren("patron");
        for (Element patron : patrones)
            if (patron.getAttributeValue("idDelPatron").equals(codigoDelPatron)) {
                // Actualiza el contenido del elemento XML con los datos del nuevo patrón.
                patron.getChild("nombre").setText(nuevoPatron.getName());
                patron.getChild("descripcion").setText(nuevoPatron.getDescription());
                patron.getChild("tipo").setText(nuevoPatron.getType());
                patron.getChild("ejemploDeCodigo").setText(nuevoPatron.getCodeExamples());
                patron.getChild("fechaAgregado").setText(nuevoPatron.getFechaAgregado().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                patron.getChild("contexto").setText(nuevoPatron.getContext());
                patron.getChild("problema").setText(nuevoPatron.getProblem());
                patron.getChild("solucion").setText(nuevoPatron.getSolution());
                guardar();
                return; // Termina cuando se actualiza.
            }// End of 'if'.
        throw new PatronNoEncontradoException();
    }// End of method [actualizar].

    public boolean existe(DesignPattern DP) {
        List<Element> patrones = this.raiz.getChildren("patron");

        if (!(patrones.isEmpty()))
            for (Element patron : patrones) {
                String comparar = String.valueOf(patron.getAttributeValue("idDelPatron"));
                if (comparar.equals(DP.getId())) return true;
            }// End of 'for'.
        return false;
    }// End of method [existe].

    public void limpiar() throws IOException, JDOMException {
        // Elimina todos los hijos del elemento raíz.
        this.raiz.removeContent();
        guardar();
    }// End of method [limpiar].

    public List<Element> lista() {
        List<Element> listaCompleta = new ArrayList<>(), elementos = this.raiz.getChildren();

        for (Element elemento : elementos) {
            // Copia del elemento para preservar el original.
            Element copiaElemento = new Element(elemento.getName());

            // Copia los atributos del elemento original a la copia.
            for (Attribute atributo : elemento.getAttributes())
                copiaElemento.setAttribute(atributo.getName(), atributo.getValue());

            // Copia el contenido del elemento original a la copia.
            for (Content contenido : elemento.getContent())
                copiaElemento.addContent(contenido.clone());

            // Agregar la copia del elemento a la lista completa.
            listaCompleta.add(copiaElemento);
        }
        return listaCompleta;
    }

    public String mostrarContenidos() {
        StringBuilder contenidos = new StringBuilder();
        for (Element elemento : this.raiz.getChildren()) {
            contenidos.append("\n<patron");

            // Obtener y agregar los atributos
            Attribute idAttribute = elemento.getAttribute("idDelPatron");
            if (idAttribute != null) contenidos.append(" idDelPatron=\"").append(idAttribute.getValue()).append("\"");

            contenidos.append(">\n"); // Cierra etiqueta de apertura.

            String nombre = elemento.getChildText("nombre");
            if (nombre != null && !nombre.isEmpty()) contenidos.append("  <nombre>").append(nombre).append("</nombre>\n");

            String descripcion = elemento.getChildText("descripcion");
            if (descripcion != null && !descripcion.isEmpty()) contenidos.append("  <descripcion>").append(descripcion).append("</descripcion>\n");

            String tipo = elemento.getChildText("tipo");
            if (tipo != null && !tipo.isEmpty()) contenidos.append("  <tipo>").append(tipo).append("</tipo>\n");

            String ejemploCodigo = elemento.getChildText("ejemploDeCodigo");
            if (ejemploCodigo != null && !ejemploCodigo.isEmpty()) contenidos.append("  <ejemploDeCodigo>").append(ejemploCodigo).append("</ejemploDeCodigo>\n");

            String fechaAgregado = elemento.getChildText("fechaAgregado");
            if (fechaAgregado != null && !fechaAgregado.isEmpty()) contenidos.append("  <fechaAgregado>").append(fechaAgregado).append("</fechaAgregado>\n");

            contenidos.append("</patron>\n"); // Cierra etiqueta final.
        }// End of 'for' loop.
        return contenidos.toString();
    }// End of method [mostrarContenidos].

    public DesignPattern buscarPorID(String id) throws IOException, JDOMException, PatronNoEncontradoException {
        List<Element> patrones = this.raiz.getChildren("patron");
        for (Element patron : patrones) {
            String idPatron = patron.getAttributeValue("idDelPatron");
            if (idPatron.equals(id)) {
                String nombre = patron.getChildText("nombre");
                String descripcion = patron.getChildText("descripcion");
                String tipo = patron.getChildText("tipo");
                String codigo = patron.getChildText("ejemploDeCodigo");
                LocalDate fechaAgregado = LocalDate.parse(patron.getChildText("fechaAgregado"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String contexto = patron.getChildText("contexto");
                String problema = patron.getChildText("problema");
                String solution = patron.getChildText("solution");
                return new DesignPattern(idPatron, nombre, descripcion, tipo, codigo,contexto,problema,solution,fechaAgregado);
            }// End of 'if'.
        }// End of 'for' loop.
        throw new PatronNoEncontradoException();
    }// End of method [buscarPorID].

    public String getRutaDocumento() {
        return rutaDocumento;
    }

    public Element getRaiz() {
        return raiz;
    }

    public Document getDocumento() {
        return documento;
    }
}// End of class [ClienteXMLData].

