package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.example;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.utility.Ordenamiento;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.ClienteXMLData;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain.PatronNoEncontradoException;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain.DesignPattern;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class ClienteXMLDataTest {

    ClienteXMLData client = new ClienteXMLData("pruebas.xml");
    ClienteXMLDataTest() throws IOException, JDOMException {}

    Ordenamiento ordenes = new Ordenamiento();

    @Test
    public void insertar_y_eliminar_funcionan() throws Exception {

        // Arrange.
        DesignPattern nuevoPatron = new DesignPattern();
        String id = nuevoPatron.generatePatternCode();
        nuevoPatron = new DesignPattern(id, "Nombre", "Descripción", "Tipo","codeExamples", "Context", "Problem", "Solution", LocalDate.of(2024,5,17));

        // Act.
        client.insertar(nuevoPatron);

        // Assert.
        assert(client.existe(nuevoPatron));

        // Act.
        client.eliminar(nuevoPatron);

        // Assert.
        assert(!client.existe(nuevoPatron));
        client.limpiar();
    }// End of method [insertar_y_eliminar_funcionan].

    @Test
    public void insertar_ordenado() throws IOException, JDOMException {
        client.limpiar();
        client.insertar(new DesignPattern("ID1", "Patron A", "Descripción A", "Tipo A", "Examples" ,"Código A", "Context", "Problem", LocalDate.now().minusDays(1)));
        client.insertar(new DesignPattern("ID2", "Patron B", "Descripción B", "Tipo B", "Examples" ,"Código B", "Context", "Problem", LocalDate.now().minusDays(2)));

        client.insertar(new DesignPattern("ID3", "Patron C", "Descripción C", "Tipo C", "Examples" ,"Código C", "Context", "Problem", LocalDate.now().minusDays(3)));


        List<Element> patrones = client.lista();

        // Assert: Verificar que los patrones estén ordenados correctamente por nombre.
        assertEquals("Patron A", patrones.get(0).getChildText("nombre"));
        assertEquals("Patron B", patrones.get(1).getChildText("nombre"));
        assertEquals("Patron C", patrones.get(2).getChildText("nombre"));

        client.limpiar();
    }


    @Test
    void actualizar_funciona() throws IOException, PatronNoEncontradoException, JDOMException {
        // Arrange: crear un patrón inicial en el XML.
        DesignPattern patronInicial = new DesignPattern("ABC123", "PatronInicial", "DescripcionInicial", "TipoInicial", "EjemploCodigoInicial", "Codigo", "Context", "Problem", LocalDate.now().minusDays(1));
        System.out.println(client.insertar(patronInicial));

        // Crear un patrón nuevo con los nuevos valores para actualizar.
        DesignPattern nuevoPatron = new DesignPattern("ABC123", "PatronInicial", "DescripcionInicial", "TipoInicial", "EjemploCodigoInicial", "Codigo", "Context", "Problem", LocalDate.now().minusDays(2));
        // Act: actualizar el patrón existente en el XML.
        client.actualizar("ABC123", nuevoPatron);
        List<Element> patrones = client.lista();

        // Verificar el contenido de la lista de patrones.
        for (Element patron : patrones) {
            System.out.println("ID: " + patron.getAttributeValue("idDelPatron"));
            System.out.println("Nombre: " + patron.getChildText("nombre"));
            System.out.println("Descripcion: " + patron.getChildText("descripcion"));
            System.out.println("Tipo: " + patron.getChildText("tipo"));
            System.out.println("EjemploCodigo: " + patron.getChildText("ejemploDeCodigo"));
            System.out.println("FechaAgregado: " + patron.getChildText("fechaAgregado"));
        }// End of 'for' loop.

        // Assert: verificar que los cambios se realizaron correctamente.
        assertEquals(1, patrones.size()); // Debe haber solo un patrón en el archivo XML
        assertEquals("NuevoPatron", patrones.getFirst().getChildText("nombre"));
        assertEquals("DescripcionNuevoPatron", patrones.getFirst().getChildText("descripcion"));
        assertEquals("TipoNuevoPatron", patrones.getFirst().getChildText("tipo"));
        assertEquals("EjemploCodigoNuevoPatron", patrones.getFirst().getChildText("ejemploDeCodigo"));
        assertNotNull(patrones.getFirst().getChildText("fechaAgregado"));
        assertEquals(nuevoPatron.getFechaAgregado().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), patrones.getFirst().getChildText("fechaAgregado"));

        client.limpiar();
    }// End of method [actualizar_funciona].

    @Test
    public void ordenar_por_criterios_funciona() throws IOException, JDOMException {
        // Arrange: Agregar algunos patrones desordenados.
        System.out.println(client.insertar(new DesignPattern("ID2", "Patron B", "Descripción B", "Tipo B", "Example", "Código B", "Context", "Problem", LocalDate.now().minusDays(2))));
        System.out.println(client.insertar(new DesignPattern("ID1", "Patron A", "Descripción A", "Tipo A", "Example", "Código A", "Context", "Problem", LocalDate.now().minusDays(1))));
        System.out.println(client.insertar(new DesignPattern("ID3", "Patron C", "Descripción C", "Tipo C", "Example", "Código C", "Context", "Problem", LocalDate.now().minusDays(3))));
        ordenes.ordenarPatronesPorNombre();

        // Assert: Verificar que los patrones estén ordenados correctamente por nombre.
        List<Element> patrones = client.lista();
        assertEquals("Patron A", patrones.get(0).getChildText("nombre"));
        assertEquals("Patron B", patrones.get(1).getChildText("nombre"));
        assertEquals("Patron C", patrones.get(2).getChildText("nombre"));
        System.out.println(client.mostrarContenidos());

        ordenes.ordenarPatronesPorFecha();
        System.out.println("Por fechas\n" + client.mostrarContenidos());

        assertEquals("20/05/2024", patrones.get(0).getChildText("fechaAgregado"));
        assertEquals("19/05/2024", patrones.get(1).getChildText("fechaAgregado"));
        assertEquals("18/05/2024", patrones.get(2).getChildText("fechaAgregado"));
        System.out.println(client.mostrarContenidos());


        ordenes.ordenarPatronesPorTipo();
        assertEquals("Tipo A", patrones.get(0).getChildText("tipo"));
        assertEquals("Tipo B", patrones.get(1).getChildText("tipo"));
        assertEquals("Tipo C", patrones.get(2).getChildText("tipo"));
        System.out.println(client.mostrarContenidos());

        ordenes.ordenarPatronesPorID();
        assertEquals("ID1", patrones.get(0).getAttributeValue("idDelPatron"));
        assertEquals("ID2", patrones.get(1).getAttributeValue("idDelPatron"));
        assertEquals("ID3", patrones.get(2).getAttributeValue("idDelPatron"));
        System.out.println(client.mostrarContenidos());


        client.limpiar();
    }// End of method [ordenar_por_criterios_funciona].

    @Test
    void mostrar_contenidos_funciona() throws IOException, JDOMException, ParserConfigurationException, SAXException {
        DesignPattern patron1 = new DesignPattern("ID1", "Patron A", "Descripción A", "Tipo A", "Examples" ,"Código A", "Context", "Problem", LocalDate.now().minusDays(1));
        DesignPattern patron2 = new DesignPattern("ID2", "Patron B", "Descripcion B", "Tipo B", "Examples", "Codigo B", "Context", "Problem", LocalDate.now().minusDays(2));
        System.out.println(client.insertar(patron1));
        System.out.println(client.insertar(patron2));
        System.out.println(client.mostrarContenidos());
        client.limpiar();
    }// End of method [mostrar_contenidos_funciona].

    @Test
    public void buscar_por_nombre_funciona() throws IOException, JDOMException, PatronNoEncontradoException {
        // Arrange: Crea e inserta algunos patrones en el archivo XML.
        DesignPattern dp1 = new DesignPattern("ID1", "Patron A", "Descripción A", "Tipo A", "Examples" ,"Código A", "Context", "Problem", LocalDate.now().minusDays(1));
        DesignPattern dp2 = new DesignPattern("ID1", "Patron A", "Descripción A", "Tipo A", "Examples" ,"Código A", "Context", "Problem", LocalDate.now().minusDays(2));
        DesignPattern dp3 = new DesignPattern("ID1", "Patron A", "Descripción A", "Tipo A", "Examples" ,"Código A", "Context", "Problem", LocalDate.now().minusDays(3));
        client.insertar(dp1);
        client.insertar(dp2);
        client.insertar(dp3);

        // Act: Busca por el nombre.
        DesignPattern resultado = client.buscarPorID("ID2");

        // Assert: Verifica que se encontró el patrón correcto.
        assertNotNull(resultado);
        assertEquals("ID2", resultado.getId());
        assertEquals("Patron B", resultado.getName());
        assertEquals("Descripción B", resultado.getDescription());
        assertEquals("Tipo2", resultado.getType());
        assertEquals("Código B", resultado.getCodeExamples());
        assertEquals(LocalDate.now().minusDays(2), resultado.getFechaAgregado());

        client.limpiar();
    }// End of method [buscar_por_tipo_funciona].



}