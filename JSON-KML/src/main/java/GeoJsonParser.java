import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GeoJsonParser {
    private ArrayList<Country> countries;

    public GeoJsonParser(){
        this.countries = new ArrayList<>();
    }

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

        ArrayList<Polygon> polygons = new ArrayList<>();
        if (polygonType.equals("Polygon")){
            //for (int i = 0; i < coordinatesArray.size(); i++){
                polygons.add(parsePolygon((JSONArray) coordinatesArray));
            //}

            countries.add(new Country(countryName, countryISO, polygonType, polygons));
        }

        if (polygonType.equals("MultiPolygon")){
            for (int polygon = 0; polygon < coordinatesArray.size(); polygon++) {
                JSONArray polygonCoordinates = (JSONArray) coordinatesArray.get(polygon);
                //for (int i = 0; i < polygonCoordinates.size(); i++){
                    polygons.add(parsePolygon(polygonCoordinates));
                //}
            }
            countries.add(new Country(countryName, countryISO, polygonType, polygons));
        }
    }

    private static Polygon parsePolygon(JSONArray polygonCoordinatesArray){
        Polygon polygon = null;
        String outerBoundariesAsString = "";
        String innerBoundariesAsString = "";
        String[] innerBoundariesAsStringArray = null;

        JSONArray outerBoundariesArray =  (JSONArray) polygonCoordinatesArray.get(0);
        for (Object coord : outerBoundariesArray) {
            outerBoundariesAsString = outerBoundariesAsString
                            + coord.toString().replace("[", "").replace("]", "") + ",0\n";
        }

        // If coordinates array is bigger than 1 => polygon has innerbounds
        if (polygonCoordinatesArray.size() > 1){
            innerBoundariesAsStringArray = new String[polygonCoordinatesArray.size()];
            for (int i = 1; i < polygonCoordinatesArray.size(); i++){
                JSONArray innerBoundariesArray = (JSONArray) polygonCoordinatesArray.get(i);
                for (Object coord : innerBoundariesArray){
                    innerBoundariesAsString = innerBoundariesAsString
                            + coord.toString().replace("[", "").replace("]", "") + ",0\n";
                }
                innerBoundariesAsStringArray[i] = innerBoundariesAsString;
            }
        }

        return new Polygon(outerBoundariesAsString, innerBoundariesAsStringArray);
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }
}
