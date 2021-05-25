public class Polygon {
    private String outerBoundary;
    private String[] innerBoundaries;

    /**
     * Constructor
     * @param outer the string containing all coordinates of the outer boundaries
     * @param inner the string array containing all coordinates of the inner boundaries
     */
    public Polygon(String outer, String[] inner){
        this.outerBoundary = outer;
        this.innerBoundaries = inner;
    }


    public String getOuterBoundary() {
        return outerBoundary;
    }

    public String[] getInnerBoundaries() {
        return innerBoundaries;
    }

}
