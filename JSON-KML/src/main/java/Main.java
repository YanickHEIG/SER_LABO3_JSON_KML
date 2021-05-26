public class Main {

    public static void main(String[] args) {
        GeoJsonParser geoJsonParser = new GeoJsonParser();
        geoJsonParser.parse("../countries.geojson");

        KMLWriter kmlWriter = new KMLWriter();
        kmlWriter.write("../countries.kml", geoJsonParser.getCountries());
    }

}
