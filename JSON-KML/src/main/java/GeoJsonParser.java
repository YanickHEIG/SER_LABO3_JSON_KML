import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GeoJsonParser {

    public static void main(String[] args) {

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
    }

    public static void parseFeatureObject(JSONObject feature) {
        //System.out.println(feature);

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
            parsePolygon(coordinatesArray);
        }

        if (polygonType.equals("MultiPolygon")){
            // Parse the multipolygon coordinates sets
            for (int i = 0; i < coordinatesArray.size(); ++i){
                JSONArray coordinates = (JSONArray) coordinatesArray.get(i);
                //System.out.println("\t- "+coordinates.size()+" coordinates");
                parsePolygon(coordinates);
            }
        }
    }

    public static void parsePolygon(JSONArray array){
        // Get coordinates from polygon array
        JSONArray coordinates = (JSONArray) array.get(0);

        // Count and display amount of coordinates
        System.out.println("\t- "+coordinates.size()+" coordinates");

        // Display individual coordinates
        /*for (Object coord : coordinates){
            //System.out.println(coord);
        }*/
    }
}
