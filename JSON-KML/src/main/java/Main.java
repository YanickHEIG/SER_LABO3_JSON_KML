import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        GeoJsonParser geoJsonParser = new GeoJsonParser("../countries.geojson");
        KMLWriter kmlWriter = new KMLWriter("../countries.kml", geoJsonParser.getCountries());
    }

}
