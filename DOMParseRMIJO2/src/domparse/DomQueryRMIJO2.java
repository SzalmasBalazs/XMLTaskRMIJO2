package domparse;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomQueryRMIJO2 {

	public static void main(String[] args)throws ParserConfigurationException, IOException, SAXException, TransformerException {
		File xmlFile = new File("XMLRMIJO2.xml"); //beolvasandó file.
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //Az XML dokumentumból DOM objektumot csinálunk.
		DocumentBuilder dBuilder = factory.newDocumentBuilder();//Document lekéréséhez XML file.
		Document doc = dBuilder.parse(xmlFile);//Dokumentum lekérése.
		doc.getDocumentElement().normalize();

		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
		System.out.println("------------------------------");
		LoadJatekosQuery(doc);

	}

	private static void LoadJatekosQuery(Document doc) throws TransformerException {
		NodeList nodeList = doc.getElementsByTagName("jatekos"); //Lekérjük a játékos elemeket.
		String jatekos;
		Element element = null;
		Node nNode = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			nNode = nodeList.item(i);
			element = (Element) nNode;
			String nev = element.getElementsByTagName("Nev").item(0).getTextContent();
			System.out.println((i + 1) + ") " + nev);

		}
		//Játékos kiválasztása.
		System.out.println("Melyik játékos pakliját kívánja látni?");
		Scanner sc = new Scanner(System.in);
		jatekos = sc.nextLine();
		for (int i = 0; i < nodeList.getLength(); i++) {
			nNode = nodeList.item(i);
			element = (Element) nNode;
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				if(jatekos.equals("Karsten Borresen")) {
					LoadPakliQuery(doc,"1");
					break;
				}
				if(jatekos.equals("Aleksander Vlakovits")) {
					LoadPakliQuery(doc,"2");
					break;
				}
				if(jatekos.equals("Szalmás Balázs")) {
					LoadPakliQuery(doc,"3");
					break;
				}
				if(jatekos.equals("Markus Highley")) {
					LoadPakliQuery(doc,"4");
					break;
				}
				if(jatekos.equals("Mark Rosewater")) {
					LoadPakliQuery(doc,"5");
					break;
				}
				if(jatekos.equals("Beringar Werner")) {
					LoadPakliQuery(doc,"6");
					break;
				}
				if(jatekos.equals("Ota Hideki")) {
					LoadPakliQuery(doc,"7");
					break;
				}
				if(jatekos.equals("Farkas Bence")) {
					LoadPakliQuery(doc,"8");
					break;
				}
				
			}
		}
		sc.close();
	}
	//A kiválasztott játékos paklijának az adatainak kiírása.
	private static void LoadPakliQuery(Document doc, String id) throws TransformerException {
		NodeList nodeList = doc.getElementsByTagName("akli");
		int pakli = 0;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nNode = nodeList.item(i);
			Element element = (Element) nNode;
			String pID = element.getAttribute("id");
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				
				if(id.equals(pID)) {
					pakli +=1;
					System.out.println(pakli +". pakli adatai:");
					String paID = element.getAttribute("id");
					DomReadRMIJO2.ReadPakliByID(doc,paID);
				}
			}
		}
	}
}
