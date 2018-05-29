package XML;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	private static String fileName;
	private static final String xmlFilePathDesktop = System.getProperty("user.home") + "\\Desktop\\";
	private static String filePath;
	

	public XMLWriter (String problem_type_selected, ArrayList<String> algoritmsChecked, String n_variables, String filePath) {

		String fileSuffix = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		fileName = problem_type_selected + fileSuffix + ".xml"; 
		this.filePath = xmlFilePathDesktop + fileName;
		

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			//add elements to Document

			doc.appendChild(getProblem(doc, problem_type_selected, algoritmsChecked, n_variables, filePath));

			//for output to file, console
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			//for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			//write to console or file
			StreamResult console = new StreamResult(System.out);
			StreamResult file = new StreamResult(new File(this.filePath));

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

	public static String getFileName() {
		return fileName;
	}

	public static String getFilePath() {
		return filePath;
	}

}