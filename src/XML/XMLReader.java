package XML;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLReader {

	private String problemType;
	private ArrayList<String> algoritms = new ArrayList<>();
	private int n_variables;
	private String jar_path;


	public XMLReader (File file) {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean bproblemType = false;
				boolean balgoritm = false;
				boolean bn_variables = false;
				boolean bjar_path = false;

				public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {

					System.out.println("Start Element: " + qName);

					if (qName.equalsIgnoreCase("problemType")) {
						bproblemType = true;
					}

					if (qName.equalsIgnoreCase("algoritm")) {
						balgoritm = true;
					}

					if (qName.equalsIgnoreCase("n_variables")) {
						bn_variables = true;
					}

					if (qName.equalsIgnoreCase("jar_path")) {
						bjar_path = true;
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					System.out.println("End Element: " + qName);

				}

				public void characters(char ch[], int start, int length) throws SAXException {

					if (bproblemType) {
						System.out.println("problemType: " + new String(ch, start, length));
						problemType = new String(ch, start, length);
						bproblemType = false;
					}

					if (balgoritm) {
						System.out.println("algoritm: " + new String(ch, start, length));
						algoritms.add(new String(ch, start, length));
						balgoritm = false;
					}

					if (bn_variables) {
						System.out.println("n_variables: " + new String(ch, start, length));
						n_variables = Integer.parseInt(new String(ch, start, length));
						bn_variables = false;
					}

					if (bjar_path) {
						System.out.println("jar_path: " + new String(ch, start, length));
						jar_path = new String(ch, start, length);
						bjar_path = false;
					}

				}

			};

			saxParser.parse(file.getAbsolutePath(), handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getProblemType() {
		return problemType;
	}

	public ArrayList<String> getAlgoritms() {
		return algoritms;
	}
	
	public int getN_variables() {
		return n_variables;
	}
	
	public String getJar_path() {
		return jar_path;
	}

}