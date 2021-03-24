import java.io.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.*;
import javax.xml.validation.*;
import org.w3c.dom.*;

/**
 DOM handler to read XML information, to create this, and to print it

 @author CSCU9T4, University of Stirling
 @version 11/03/20
 */
public class DOMMenu {

    /**
     * Document builder
     */
    private static DocumentBuilder builder = null;

    /**
     * XML document
     */
    private static Document document = null;

    /**
     * XPath expression path
     */
    private static XPath path = null; // expression needed to be changed to path for path to work (or all 'path' to expression)

    /**
     * XML Schema for validation
     */
    private static Schema schema = null;

    /* General Methods ===================================================*/

    /**
     * Main program to call DOM parser
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // load XML file into "document"
        loadDocument(args[0]);
        // load XSD file into validator
        validateDocument(args[1]);
        // print staff.xml using DOM methods and XPath queries
        printNodes();
    }

    /**
     * Set global document by reading the given file
     * @param filename XML file to read
     */
    private static void loadDocument(String filename) {
        try {
            // create document builder
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builder = builderFactory.newDocumentBuilder();

            // create an XPath expression
            XPathFactory xpathFactory = XPathFactory.newInstance();
            path = xpathFactory.newXPath();

            // parse the document for later searching
            document = builder.parse(new File(filename));
        } catch (Exception exception) {
            System.err.println("Could not load document " + exception);
        }
    }

    /* DOM and XPath Methods ========================================================================= */

    /**
     * Validate the document given a schema file
     * @param filename XSD file to read
     */
    private static Boolean validateDocument(String filename) {
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            schema = factory.newSchema(new File(filename));
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));
            System.out.println("Validated :)"); // message for when file has passed validation
            return true;
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("Could not load schema or validate");
            return false;
        }
    }

    /**
     * Print nodes using DOM methods and XPath queries
     */
    private static void printNodes() {
        //Node menuItem_1 = document.getFirstChild();
        //Node menuItem_2 = menuItem_1.getFirstChild();
        //System.out.println("First child is: " + menuItem_1.getNodeName());
        //System.out.println("  Child is: " + menuItem_2.getNodeName() + "\n");

        System.out.println("Today's small menue is: \n");

        // Printing out starters
        Node starter_1 = document.getElementsByTagName("item").item(0);// getting first item element from xml document
        System.out.println(starter_1.getTextContent());
        Node starter_2 = document.getElementsByTagName("item").item(1);// getting second item element from xml document
        System.out.println(starter_2.getTextContent());
        Node starter_3 = document.getElementsByTagName("item").item(2);// getting third item element from xml document
        System.out.println(starter_3.getTextContent());

        // Printing out mains
        Node main_1 = document.getElementsByTagName("item").item(3);// getting fourth item element from xml document
        System.out.println(main_1.getTextContent());
        Node main_2 = document.getElementsByTagName("item").item(4);// getting fifth item element from xml document
        System.out.println(main_2.getTextContent());
        Node main_3 = document.getElementsByTagName("item").item(5);// getting sixth item element from xml document
        System.out.println(main_3.getTextContent());

        // Printing out desserts
        Node dessert_1 = document.getElementsByTagName("item").item(6);// getting seventh element from xml document
        System.out.println(dessert_1.getTextContent());
        Node dessert_2 = document.getElementsByTagName("item").item(7);// getting eighth element from xml document
        System.out.println(dessert_2.getTextContent());

    }

    /**
     * Get result of XPath query
     * @param query XPath Query
     * @return result of query
     */
    private static String query(String query) {
        String result = "";
        try {
            result = path.evaluate(query, document);
        }
        catch (Exception exception) {
            System.err.println("Could not perform query - " + exception);
        }
        return(result);
    }
}
