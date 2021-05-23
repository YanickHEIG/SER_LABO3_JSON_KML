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
    private ArrayList<Country> countries = new ArrayList<Country>();

    public GeoJsonParser(String filePath){
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


        if (polygonType.equals("Polygon")){
            // Parse the polygon coordinates
            //parsePolygon(coordinatesArray);
            countries.add(new Country(countryName, countryISO, polygonType, parsePolygon(coordinatesArray), null));
        }

        if (polygonType.equals("MultiPolygon")){
            // Initialize array for Country creation
            String[] multipolygonCoordinatesArrayAsString = new String[coordinatesArray.size()];
            // Parse the multipolygon coordinates sets
            for (int i = 0; i < coordinatesArray.size(); ++i){
                JSONArray coordinates = (JSONArray) coordinatesArray.get(i);
                //parsePolygon(coordinates);
                multipolygonCoordinatesArrayAsString[i] = parsePolygon(coordinates);

            }
            countries.add(new Country(countryName, countryISO, polygonType, null, multipolygonCoordinatesArrayAsString));
        }
    }

    public static String parsePolygon(JSONArray array){
        // Get coordinates from polygon array
        JSONArray coordinates = (JSONArray) array.get(0);
        String coordinateAsString = "";

        // Count and display amount of coordinates
        System.out.println("\t- "+coordinates.size()+" coordinates");

        // Iterate through individual coordinates and create return string
        for (Object coord : coordinates){
            coordinateAsString = coordinateAsString + coord.toString().replace("[", "").replace("]", " ");
        }

        return coordinateAsString;
    }



     /*public static void main(String[] args) {

        //JSON parser object pour lire le fichier
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("../countries.geojson")) {


            // lecture du fichier
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
    }*/

    public ArrayList<Country> getCountries() {
        return countries;
    }
}
