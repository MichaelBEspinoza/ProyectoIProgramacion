package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.domain;

import org.jdom2.Element;

public class Pattern {
    private String id;
    private String name;
    private String context;
    private String problem;
    private String solution;
    private String image;
    private String example;
    private String classification;

    // Constructores, getters y setters


    public Pattern(String id, String name, String context, String problem, String solution, String image, String example, String classification) {
        this.id = id;
        this.name = name;
        this.context = context;
        this.problem = problem;
        this.solution = solution;
        this.image = image;
        this.example = example;
        this.classification = classification;
    }

    public Element toXMLElement() {
        Element patternElement = new Element("pattern");
        patternElement.setAttribute("id", id);
        patternElement.addContent(new Element("name").setText(name));
        patternElement.addContent(new Element("context").setText(context));
        patternElement.addContent(new Element("problem").setText(problem));
        patternElement.addContent(new Element("solution").setText(solution));
        patternElement.addContent(new Element("image").setText(image));
        patternElement.addContent(new Element("example").setText(example));
        patternElement.addContent(new Element("classification").setText(classification));
        return patternElement;
    }

    public static Pattern fromXMLElement(Element element) {
        String id = element.getAttributeValue("id");
        String name = element.getChildText("name");
        String context = element.getChildText("context");
        String problem = element.getChildText("problem");
        String solution = element.getChildText("solution");
        String image = element.getChildText("image");
        String example = element.getChildText("example");
        String classification = element.getChildText("classification");
        return new Pattern(id, name, context, problem, solution, image, example, classification);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
