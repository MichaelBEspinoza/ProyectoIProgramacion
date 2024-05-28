package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.common;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain.Pattern;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.utility.Utility;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.data.PatternXmlData;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;

public class TCPProtocol {

    //Variables que hacen referencia al estado de la acción.
    private static final int ESPERANDO = 0;
    private static final int ESPERANDO_COMANDO = 0;

    //Variables que hacen referencia al rol que cumple un comando de un router o switch
    private static final String INSERTAR_PATRON = "INSERTAR_PATRON"; //Comando para insertar o crear un patron
    private static final String ELIMINAR_PATRON = "ELIMINAR_PATRON"; //Comando para eliminar un patron
    private static final String ACTUALIZAR_PATRON = "ACTUALIZAR_PATRON"; //Comando para actualizar un patron
    private static final String BUSCAR_PATRON = "BUSCAR_PATRON"; //Comando para buscar un patron
    private static final String GUARDAR_PATRON = "GUARDAR PATRON"; //Comando para guardar un patron
    private static final String ORDENAR_ID_PATRON = "ORDENAR_ID_PATRON"; //Comando para ordenar por ID un patron
    private static final String ORDENAR_TIPO_PATRON = "ORDENAR_TIPO_PATRON"; //Comando para ordenar por tipo un patron
    private static final String LOGIN = "LOGIN"; //Comando para obtener conexion con el servidor
    private static final String LOGOUT = "LOGOUT"; //Comanodo para terminar la conexion con el servidor

    private int esperado;
    private String documentRoute;
    private Document document;
    private Element root;
    public TCPProtocol(){ esperado = ESPERANDO;}

    public String procesarEntrada(String entrada){
        String salida = null;
        if (esperado == ESPERANDO){
            salida = "Esperando comando de entrada para ejecutar accion correspondiente...";
            esperado = ESPERANDO_COMANDO;
        }else if (esperado == ESPERANDO_COMANDO){
            salida = comandoEscogido(entrada);
            esperado = ESPERANDO;
        }
        return salida;
    }

    public String comandoEscogido(String entrada){

        String salida = null;

        try{

            SAXBuilder saxBuilder = new SAXBuilder();
            File file = new File(documentRoute);
            this.document = saxBuilder.build(new File(documentRoute));
            this.root = document.getRootElement();
            String comando = root.getChildText("command");

            switch (comando){

                case INSERTAR_PATRON:
                    salida = insertarOnAction(Pattern.fromXMLElement(root.getChild("pattern")));
                case BUSCAR_PATRON:
                    salida = String.valueOf(buscarOnAction(Pattern.fromXMLElement(root.getChild("pattern"))));

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return salida;
    }

    private String insertarOnAction(Pattern pattern) {

        PatternXmlData util = new PatternXmlData();
        try {
            util.addPattern(pattern);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Elemento insertado con exito";
    }

    private StringBuilder buscarOnAction(Pattern element) throws Exception {
        PatternXmlData util = new PatternXmlData();
        StringBuilder builder = new StringBuilder();
        Pattern patron = util.findPatternById(element.getId());
        if(Utility.compare(element.getId(),patron.getId())==0){
            builder.append("El patron fue encontrado\n").append("Name: " +element.getName()).append("Contexto: "+ element.getContext()).append("Problema: "+ element.getProblem()).append("Solucion: " + element.getSolution()).append("Ejemplos: "+element.getExample()).append("Clasificacion: "+ element.getClassification());
            return builder;
        }else{
            builder.append("El patron no fue encontrado");
            return builder;
        }
    }

}
