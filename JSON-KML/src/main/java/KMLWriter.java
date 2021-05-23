import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class KMLWriter {

    public KMLWriter(String outfilePath, ArrayList<Country> countries){
        // Initialize document and root element, including namespace
        Document doc = new Document();
        Element root = new Element("kml", "http://www.opengis.net/kml/2.2");

        Element document = new Element("Document");
        // Document tag added to root
        root.addContent(document);

        for (int i = 0; i < countries.size(); i++){
            // Create Placemark tag and add to root
            Element placemark = new Element("Placemark");
            document.addContent(placemark);
            // Create ExtendedData tag and add to placemark
            Element extendedData = new Element("ExtendedData");
            placemark.addContent(extendedData);
            // Create data tag used for name, set attribute and add to extendedData
            Element data_name = new Element("Data");
            data_name.setAttribute("name", "ADMIN");
            extendedData.addContent(data_name);
            // Create country name element, set its value and add to data
            Element value_name = new Element("value");
            value_name.addContent(countries.get(i).getName());
            data_name.setContent(value_name);

            // Do the same for ISO data tag
            Element data_iso = new Element("Data");
            data_iso.setAttribute("name", "ISO_A3");
            extendedData.addContent(data_iso);
            Element value_iso = new Element("value");
            value_iso.addContent(countries.get(i).getIso());
            data_iso.setContent(value_iso);

            // Write tags for polygon
            if (countries.get(i).getPolygonType().equals("Polygon")){
                constructPolygonElement(placemark, countries.get(i).getPolygonCoordinates());
            }

            // Write tags for multipolygon
            if (countries.get(i).getPolygonType().equals("MultiPolygon")){
                // Create multigeometry tag and add it to placemark
                Element multigeometry = new Element("MultiGeometry");
                placemark.addContent(multigeometry);
                // Parse all polygons in the multipolygon
                for (int j = 0; j < countries.get(i).getMultiPolygonCoordinates().length; j++){
                    constructPolygonElement(multigeometry, countries.get(i).getMultiPolygonCoordinates()[j]);
                }
            }


        }

        // Add root to document
        doc.addContent(root);
        // Set formatting settings
        Format format = Format.getPrettyFormat();
        format.setIndent("\t");
        // Create the XML outputter
        XMLOutputter outp = new XMLOutputter(format);

        try {
            outp.output(doc, new FileOutputStream(outfilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void constructPolygonElement(Element parent, String coords){
        // Create polygon element and add it to placemark
        Element polygon = new Element("Polygon");
        parent.addContent(polygon);
        // Create outerBoundaryIs tag and add it to polygon
        Element outerBoundaryIs = new Element("OuterBoundaryIs");
        polygon.addContent(outerBoundaryIs);
        // Create linearRing and add it to outerBoundaryIs
        Element linearRing = new Element("LinearRing");
        outerBoundaryIs.addContent(linearRing);
        // Create coordinates tag, set its value and add it to linearRing
        Element coordinates = new Element("coordinates");
        coordinates.addContent(coords);
        linearRing.addContent(coordinates);

    }

}
