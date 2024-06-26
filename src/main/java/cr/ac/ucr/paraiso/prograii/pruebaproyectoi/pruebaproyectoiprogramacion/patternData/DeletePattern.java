package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.patternData;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain.DesignPattern;

import java.util.List;

public class DeletePattern {
    public static void deletePattern(String id) throws Exception {
        List<DesignPattern> patterns = LoadPatterns.loadPatterns();
        patterns.removeIf(pattern -> pattern.getId().equals(id));
        SavePatterns.savePatterns(patterns);
    }
}