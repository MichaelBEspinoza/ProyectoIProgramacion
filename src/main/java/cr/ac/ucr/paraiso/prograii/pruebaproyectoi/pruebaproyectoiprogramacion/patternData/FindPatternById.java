package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.patternData;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain.DesignPattern;

import java.util.List;

public class FindPatternById {
    public DesignPattern findPatternById(String id) throws Exception {
        List<DesignPattern> patterns = LoadPatterns.loadPatterns();
        return patterns.stream()
                .filter(pattern -> pattern.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}