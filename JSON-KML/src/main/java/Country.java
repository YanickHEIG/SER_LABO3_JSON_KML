import java.util.ArrayList;

public class Country {
    private String name;
    private String iso;
    private String polygonType;
    private ArrayList<Polygon> polygons;

    /**
     * Constructor
     * @param name the name given to the country
     * @param iso the ISO_A3 notation of the country
     * @param polygonType the type of polygon representing the country
     * @param polygons the polygons representing the country
     */
    public Country(String name, String iso, String polygonType, ArrayList<Polygon> polygons){
        if (polygons != null){
            this.polygons = new ArrayList<>();
        }
        this.name = name;
        this.iso = iso;
        this.polygonType = polygonType;
        this.polygons = polygons;
    }


    public String getName() {
        return name;
    }

    public String getIso() {
        return iso;
    }

    public String getPolygonType() {
        return polygonType;
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

}
