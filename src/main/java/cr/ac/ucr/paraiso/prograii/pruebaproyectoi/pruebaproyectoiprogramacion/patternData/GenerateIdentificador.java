package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.patternData;


import java.util.UUID;

public class GenerateIdentificador {
    public String generateIdentificador() {
        return UUID.randomUUID().toString();
    }
}