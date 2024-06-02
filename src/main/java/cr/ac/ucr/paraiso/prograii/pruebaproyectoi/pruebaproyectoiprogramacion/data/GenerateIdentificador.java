package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.data;


import java.util.UUID;

public class GenerateIdentificador {
    public String generateIdentificador() {
        return UUID.randomUUID().toString();
    }
}