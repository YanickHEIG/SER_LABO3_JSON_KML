
import java.util.ArrayList;

public class Country {
    private String name;
    private String iso;
    private String polygonType;
    private ArrayList<Polygon> polygons;

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

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


    public String toString(){
        return "(" + iso + ") " + name + " " + polygonType + " "  + polygons.toString();
    }

}
