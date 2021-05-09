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

    private static void parseFeatureObject(JSONObject feature) {
        //System.out.println(feature);

        JSONObject properties = (JSONObject) feature.get("properties");
        JSONObject geometry = (JSONObject) feature.get("geometry");

        // Get country ISO and full name
        String countryName = (String) properties.get("ADMIN");
        String countryISO = (String) properties.get("ISO_A3");

        System.out.println("("+countryISO+") " + countryName );


        // Get polygon type
        String polygonType = (String) geometry.get("type");

        if (polygonType.equals("Polygon")){
            JSONArray coordinatesArray = (JSONArray) geometry.get("coordinates");
            JSONArray coordinates = (JSONArray) coordinatesArray.get(0);
            // Count and display amount of coordinates
            System.out.println("\t- "+coordinates.size()+" coordinates");

            // Display coordinates
            /*
            for (Object coord : coordinates){
                System.out.println(coord);
            }
            */
        }

        if (polygonType.equals("MultiPolygon")){
            JSONArray coordinatesArray1 = (JSONArray) geometry.get("coordinates");
            //System.out.println("----"+coordinatesArray1.size());
            JSONArray coordinatesArray2 = (JSONArray) coordinatesArray1.get(0);
            //System.out.println("----"+coordinatesArray2.size());
            JSONArray coordinates = (JSONArray) coordinatesArray2.get(0);
            //System.out.println("----"+coordinates.size());
            //System.out.println("\t- "+coordinates.size()+" coordinates");
            //System.out.println("\t- "+coordinatesArray1.size()+" coordinates");
        }
    }
}
