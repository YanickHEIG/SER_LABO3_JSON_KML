import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * GeoJsonParser: This class is used to parse a geojson file. It contains methods used for parsing features and polygons
 */
public class GeoJsonParser {
    private ArrayList<Country> countries;

    /**
     * Default constructor
     */
    public GeoJsonParser(){
        this.countries = new ArrayList<>();
    }

    /**
     * parse(String filePath) method used to parse a geojson file given its file path
     * @param filePath the path of the file we want to parse, relative to project folder.
     */
    public void parse(String filePath){
        //JSON parser object in order to be able to read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {

            // Read file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONArray features = (JSONArray) obj.get("features");
            // For each feature, call function
            features.forEach(feat->parseFeatureObject((JSONObject)feat));

        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * parseFeatureObject(JSON Object feature) method used to parse a feature geojson feature object
     * @param feature the geojson feature object to parse
     */
    private void parseFeatureObject(JSONObject feature) {

        JSONObject properties = (JSONObject) feature.get("properties");
        JSONObject geometry = (JSONObject) feature.get("geometry");

        // Get country ISO and full name
        String countryName = (String) properties.get("ADMIN");
        String countryISO = (String) properties.get("ISO_A3");

        System.out.println("("+countryISO+") " + countryName );

        // Get polygon type
        String polygonType = (String) geometry.get("type");
        // Get coordinates
        JSONArray coordinatesArray = (JSONArray) geometry.get("coordinates");

        // Initialize arraylist to store polygons
        ArrayList<Polygon> polygons = new ArrayList<>();
        // In case of a polygon, create the polygon and the country it belongs to
        if (polygonType.equals("Polygon")){
            polygons.add(parsePolygon((JSONArray) coordinatesArray));
            countries.add(new Country(countryName, countryISO, polygonType, polygons));
        }

        // In case of a multipolygon, iterate over all polygons and create them all
        if (polygonType.equals("MultiPolygon")){
            for (int polygon = 0; polygon < coordinatesArray.size(); polygon++) {
                JSONArray polygonCoordinates = (JSONArray) coordinatesArray.get(polygon);
                polygons.add(parsePolygon(polygonCoordinates));
            }
            // Then create country
            countries.add(new Country(countryName, countryISO, polygonType, polygons));
        }
    }

    /**
     * parsePolygon: This method is used to parse an array of coordinates and create the associated polygon object
     * @param polygonCoordinatesArray the coordinates based on which the polygon should be created
     * @return Polygon, the created polygon
     */
    private Polygon parsePolygon(JSONArray polygonCoordinatesArray){
        // Declare variables used for polygon construction
        String outerBoundariesAsString = "";
        String innerBoundariesAsString = "";
        String[] innerBoundariesAsStringArray = null;

        // Create the outerboundaries as a single string
        JSONArray outerBoundariesArray =  (JSONArray) polygonCoordinatesArray.get(0);
        System.out.println("\t - " + outerBoundariesArray.size() + " coordinates");
        for (Object coord : outerBoundariesArray) {
            outerBoundariesAsString = outerBoundariesAsString
                    + coord.toString().replace("[", "").replace("]", "")
                    + ",0\n";
        }

        // If coordinates array is bigger than 1 => polygon has innerbounds
        if (polygonCoordinatesArray.size() > 1){
            // Create an array to store all inner boundaries
            innerBoundariesAsStringArray = new String[polygonCoordinatesArray.size()];
            // Iterate over coordinates array, knowing that [0] is always an outer boundary
            for (int i = 1; i < polygonCoordinatesArray.size(); i++){
                JSONArray innerBoundariesArray = (JSONArray) polygonCoordinatesArray.get(i);
                System.out.println("\t - " + innerBoundariesArray.size() + " coordinates");
                // Create the inner boundaries as a single string
                for (Object coord : innerBoundariesArray){
                    innerBoundariesAsString = innerBoundariesAsString
                            + coord.toString().replace("[", "").replace("]", "")
                            + ",0\n";
                }
                // Add it to inner boundaries array
                innerBoundariesAsStringArray[i] = innerBoundariesAsString;
            }
        }
        // Create the new polygon
        return new Polygon(outerBoundariesAsString, innerBoundariesAsStringArray);
    }

    /**
     * getCountries() method used for returning the list which contains all parsed countries
     * @return a list of all countries parsed
     */
    public ArrayList<Country> getCountries() {
        return countries;
    }
}
