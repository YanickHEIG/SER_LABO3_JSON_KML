public class Polygon {
    private String outerBoundary;
    private String[] innerBoundaries;

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

    public String toString(){
        //String out =  "Outer: " + outerBoundary + "\n";
        String out = "";
        if (innerBoundaries != null)
            out = out + " inner boundaries : " + innerBoundaries.length;

        return out;
    }



}
