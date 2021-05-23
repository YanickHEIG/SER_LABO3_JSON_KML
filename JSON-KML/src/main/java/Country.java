import org.json.simple.JSONArray;

public class Country {
    private String name;
    private String iso;
    private String polygonType;
    private String polygonCoordinates;
    private String[] multiPolygonCoordinates;

    public Country(String name, String iso, String polygonType, String polygonCoordinates, String[] multiPolygonCoordinates){
        this.name = name;
        this.iso = iso;
        this.polygonType = polygonType;
        this.polygonCoordinates = polygonCoordinates;
        this.multiPolygonCoordinates = multiPolygonCoordinates;
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

    public String getPolygonCoordinates() {
        return polygonCoordinates;
    }

    public String[] getMultiPolygonCoordinates() {
        return multiPolygonCoordinates;
    }
}
