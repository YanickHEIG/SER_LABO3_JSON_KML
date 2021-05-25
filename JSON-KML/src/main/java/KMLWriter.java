import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class KMLWriter {

    /**
     * Default constructor
     */
    public KMLWriter(){}

    /**
     * write(String outfilePath, ArrayList<Country> countries) method used to write a kml file
     * @param outfilePath the path of the file we want to write, relative to project folder
     * @param countries the array of countries for which we want to write the kml file
     */
    public void write(String outfilePath, ArrayList<Country> countries){
        // Initialize document and root element, including namespace
        Namespace ns = Namespace.getNamespace("http://www.opengis.net/kml/2.2");
        Document doc = new Document();
        Element kml = new Element("kml", ns);

        Element document = new Element("Document", ns);
        // Add document name
        Element docuName = new Element("name", ns);
        docuName.addContent("countries.kml");
        document.addContent(docuName);

        // Document tag added to root
        kml.addContent(document);

        for (int i = 0; i < countries.size(); i++){
            // Create Placemark tag and add to root
            Element placemark = new Element("Placemark", ns);
            document.addContent(placemark);
            // Create name tag and add it to placemark
            Element name = new Element("name", ns);
            name.addContent(countries.get(i).getName());
            placemark.addContent(name);

            // Write tags for polygon
            if (countries.get(i).getPolygonType().equals("Polygon")){
                writeTagsForPolygon(placemark, countries.get(i).getPolygons().get(0), ns);
            }

            // Write tags for multipolygon
            if (countries.get(i).getPolygonType().equals("MultiPolygon")){
                // Create multigeometry tag and add it to placemark
                Element multigeometry = new Element("MultiGeometry", ns);
                placemark.addContent(multigeometry);
                // Parse all polygons in the multipolygon
                for (int j = 0; j < countries.get(i).getPolygons().size(); j++){
                    writeTagsForPolygon(multigeometry, countries.get(i).getPolygons().get(j), ns);
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


    /**
     * writeTagsForPolygon(Element parent, Polygon p) method used to create kml tags for a polygon
     * @param parent the parent tag to which the created tags must be appended
     * @param p the polygon for which we want to creat the tags
     * @param ns the namespace under which we want to create the tags
     */
    private void writeTagsForPolygon(Element parent, Polygon p, Namespace ns){
        // Create polygon element and add it to placemark
        Element polygon = new Element("Polygon", ns);
        parent.addContent(polygon);
        // Create outerBoundaryIs tag and add it to polygon
        Element outerBoundaryIs = new Element("outerBoundaryIs", ns);
        polygon.addContent(outerBoundaryIs);
        // Create linearRing and add it to outerBoundaryIs
        Element linearRing = new Element("LinearRing", ns);
        outerBoundaryIs.addContent(linearRing);
        // Create coordinates tag, set its value and add it to linearRing
        Element coordinates = new Element("coordinates", ns);
        coordinates.addContent(p.getOuterBoundary());
        linearRing.addContent(coordinates);

        for (int k = 1 ; p.getInnerBoundaries()!= null && k < p.getInnerBoundaries().length; ++k){
            Element innerBoundaryIs = new Element("innerBoundaryIs", ns);
            polygon.addContent(innerBoundaryIs);
            // Create linearRing and add it to outerBoundaryIs
            linearRing = new Element("LinearRing", ns);
            innerBoundaryIs.addContent(linearRing);
            // Create coordinates tag, set its value and add it to linearRing
            coordinates = new Element("coordinates", ns);
            coordinates.addContent(p.getInnerBoundaries()[k]);
            linearRing.addContent(coordinates);
        }
    }



}
