package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.patternData;


import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain.DesignPattern;

import java.util.List;

public class AddPattern {
    public void addPattern(DesignPattern pattern) throws Exception {
        List<DesignPattern> patterns = LoadPatterns.loadPatterns();
        patterns.add(pattern);
        SavePatterns.savePatterns(patterns);
    }
}