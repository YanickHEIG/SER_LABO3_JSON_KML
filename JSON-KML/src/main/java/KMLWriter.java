import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class KMLWriter {

    public KMLWriter(){

    }

    public void write(String outfilePath, ArrayList<Country> countries){
        // Initialize document and root element, including namespace
        Document doc = new Document();
        Element kml = new Element("kml", "http://www.opengis.net/kml/2.2");

        Element document = new Element("Document");
        // Add document name
        Element docuName = new Element("name");
        docuName.addContent("countries.kml");
        document.addContent(docuName);

        Element open = new Element("open");
        open.addContent("0");
        document.addContent(open);

        // Document tag added to root
        kml.addContent(document);

        for (int i = 0; i < countries.size(); i++){
            // Create Placemark tag and add to root
            Element placemark = new Element("Placemark");
            document.addContent(placemark);
            // Create name tag and add it to placemark
            Element name = new Element("name");
            name.addContent(countries.get(i).getName());
            placemark.addContent(name);

            // Write tags for polygon
            if (countries.get(i).getPolygonType().equals("Polygon")){
                writeTagsForPolygon(placemark, countries.get(i).getPolygons().get(0));
            }

            // Write tags for multipolygon
            if (countries.get(i).getPolygonType().equals("MultiPolygon")){
                // Create multigeometry tag and add it to placemark
                Element multigeometry = new Element("MultiGeometry");
                placemark.addContent(multigeometry);
                // Parse all polygons in the multipolygon
                for (int j = 0; j < countries.get(i).getPolygons().size(); j++){

                    writeTagsForPolygon(multigeometry, countries.get(i).getPolygons().get(j));
                }
            }
        }

        // Add root to document
        doc.addContent(kml);
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


    private void writeTagsForPolygon(Element parent, Polygon p){
        // Create polygon element and add it to placemark
        Element polygon = new Element("Polygon");
        parent.addContent(polygon);
        // Create outerBoundaryIs tag and add it to polygon
        Element outerBoundaryIs = new Element("outerBoundaryIs");
        polygon.addContent(outerBoundaryIs);
        // Create linearRing and add it to outerBoundaryIs
        Element linearRing = new Element("LinearRing");
        outerBoundaryIs.addContent(linearRing);
        // Create coordinates tag, set its value and add it to linearRing
        Element coordinates = new Element("coordinates");
        coordinates.addContent(p.getOuterBoundary());
        linearRing.addContent(coordinates);

        for (int k = 1 ; p.getInnerBoundaries()!= null && k < p.getInnerBoundaries().length; ++k){
            Element innerBoundaryIs = new Element("innerBoundaryIs");
            polygon.addContent(innerBoundaryIs);
            // Create linearRing and add it to outerBoundaryIs
            linearRing = new Element("LinearRing");
            innerBoundaryIs.addContent(linearRing);
            // Create coordinates tag, set its value and add it to linearRing
            coordinates = new Element("coordinates");
            coordinates.addContent(p.getInnerBoundaries()[k]);
            linearRing.addContent(coordinates);
        }
    }



}
