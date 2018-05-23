package XML;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLWriter {

    public XMLWriter (String problem_type_selected, ArrayList<String> algoritmsChecked, String n_variables, String filePath) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            //add elements to Document

            //append first child element to root element
//            String [] algoritmsChecked = {"Cheese", "Pepperoni", "Black Olives"};
//            doc.appendChild(getProblem(doc, "Integer", algoritmsChecked, "2", "jar ..."));
            doc.appendChild(getProblem(doc, problem_type_selected, algoritmsChecked, n_variables, filePath));

            //for output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //write to console or file
            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File(System.getProperty("user.home") +"/Desktop/problem.xml"));

            //write data
            transformer.transform(source, console);
            transformer.transform(source, file);
            System.out.println("DONE");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Node getProblem(Document doc, String problemType, ArrayList<String> algoritmsChecked, String n_variables, String jar_path) {
        Element problem = doc.createElement("Problem");
        
        algoritmsChecked = (ArrayList<String>) algoritmsChecked.stream().distinct().collect(Collectors.toList());

        //create problemType element
        problem.appendChild(getProblemElements(doc, problem, "problemType", problemType));

        //create algoritm element
        for (String algoritm : algoritmsChecked) {
        	problem.appendChild(getProblemElements(doc, problem, "algoritm", algoritm));
		}

        //create n_variables element
        problem.appendChild(getProblemElements(doc, problem, "n_variables",  n_variables));

        //create jar_path element
        problem.appendChild(getProblemElements(doc, problem, "jar_path", jar_path));

        return problem;
    }


    //utility method to create text node
    private static Node getProblemElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

}