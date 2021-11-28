package domparse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DomModifyRMIJO2 {

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
		File xmlFile = new File("XMLRMIJO2.xml"); // xml fájl bekérése
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // olvasás lehetõvé tétele
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
				System.out.println("XML Módosítása");
				System.out.println("Adja meg mit szeretne módosítani: ");
				System.out.println("1 Verseny módosítása\n2 Játékos módosítása\n3 Pakli módosítása\n4 Lap módosítása");
			Modify(doc);
	}
	//A modify metódusban megkérdezzük a felhasználót, melyik adatot kivánja megváltoztatni.
	private static void Modify(Document doc) throws TransformerException {
		int numberOfVerseny = doc.getElementsByTagName("verseny").getLength();
		int numberOfJatekos = doc.getElementsByTagName("jatekos").getLength();
		int numberOfPakli = doc.getElementsByTagName("pakli").getLength();
		int numberOfLap = doc.getElementsByTagName("lap").getLength();
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Adja meg a sorszámot: ");
		int readInt = scanner.nextInt();
			switch(readInt) {
			case 1:
					ModifyVerseny(doc,numberOfVerseny);
					break;
			case 2:
					ModifyJatekos(doc,numberOfJatekos);
					break;
			case 3:
					ModifyPakli(doc,numberOfPakli);
					break;
			case 4:
					ModifyLap(doc,numberOfLap);
					break;
			}
			scanner.close();
				
	}
	
	private static void ModifyVerseny(Document doc, int numberOfVerseny) throws TransformerException {
		System.out.println("Melyik verseny adatait kívánja módosítani?");
		for (int i = 0; i < numberOfVerseny + 1; i++) {
			System.out.println(i+". verseny");
			DomReadRMIJO2.Read(doc);
		}
		String id =ReadID();
			Scanner sc = new Scanner(System.in);
			System.out.println("Formátum: ");
			String format = sc.nextLine();
			System.out.print("Helyszín: ");
			String helyszin = sc.nextLine();
			System.out.print("Körök száma: ");
			String korszam = sc.nextLine();
		sc.close();
		NodeList nodeList = doc.getElementsByTagName("verseny");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nNode = nodeList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nNode;
				String sid = element.getAttribute("id");
				if (sid.equals(id)) {
					Node node1 = element.getElementsByTagName("formatum").item(0);
					node1.setTextContent(format);
					Node node2 = element.getElementsByTagName("helyszin").item(0);
					node2.setTextContent(helyszin);
					Node node3 = element.getElementsByTagName("korok_szama").item(0);
					node3.setTextContent(korszam);
					System.out.println("Sikeres módosítás");
				}
			}
		}
		ModifyXML(doc); // Létrehozzuk a módosított XML-t.
	}
	
	private static void ModifyJatekos(Document doc, int numberOfJatekos) throws TransformerException {
		System.out.println("Melyik játékos adatait kívánja módosítani?");
		for (int i = 0; i < numberOfJatekos + 1; i++) {
			System.out.println(i+". jatekos");
			DomReadRMIJO2.ReadJatekosByID(doc,String.valueOf(i));
		}
		String id =ReadID();
			Scanner sc = new Scanner(System.in);
			System.out.println("Név: ");
			String nev = sc.nextLine();
			System.out.print("Születési dátum: ");
			String szul_datum = sc.nextLine();
			System.out.print("Születési ország: ");
			String szul_orszag = sc.nextLine();
			System.out.print("Telefonszám: ");
			String telszam = sc.nextLine();
		sc.close();
		NodeList nodeList = doc.getElementsByTagName("jatekos");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nNode = nodeList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nNode;
				String sid = element.getAttribute("id");
				if (sid.equals(id)) {
					Node node1 = element.getElementsByTagName("Nev").item(0);
					node1.setTextContent(nev);
					Node node2 = element.getElementsByTagName("Szul_datum").item(0);
					node2.setTextContent(szul_datum);
					Node node3 = element.getElementsByTagName("Sul_orszag").item(0);
					node3.setTextContent(szul_orszag);
					Node node4 = element.getElementsByTagName("Telefonszam").item(0);
					node4.setTextContent(telszam);
					System.out.println("Sikeres módosítás");
				}
			}
		}
		ModifyXML(doc); 
	}
	private static void ModifyPakli(Document doc, int numberOfPakli) throws TransformerException {
		System.out.println("Melyik Pakli adatait kívánja módosítani?");
		for (int i = 0; i < numberOfPakli + 1; i++) {
			System.out.println(i+". pakli");
			DomReadRMIJO2.ReadPakliByID(doc,String.valueOf(i));
		}
		String id =ReadID();
			Scanner sc = new Scanner(System.in);
			System.out.println("Név: ");
			String nev = sc.nextLine();
			System.out.print("Ban lista: ");
			String banlist = sc.nextLine();
			System.out.print("Lapszam: ");
			String lapszam = sc.nextLine();
		sc.close();
		NodeList nodeList = doc.getElementsByTagName("jatekos");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nNode = nodeList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nNode;
				String sid = element.getAttribute("id");
				if (sid.equals(id)) {
					Node node1 = element.getElementsByTagName("Nev").item(0);
					node1.setTextContent(nev);
					Node node2 = element.getElementsByTagName("Ban_lista").item(0);
					node2.setTextContent(banlist);
					Node node3 = element.getElementsByTagName("Lapszam").item(0);
					node3.setTextContent(lapszam);
					System.out.println("Sikeres módosítás");
				}
			}
		}
		ModifyXML(doc); 
	}
	private static void ModifyLap(Document doc, int numberOfLap) throws TransformerException {
		System.out.println("Melyik lap adatait kívánja módosítani?");
		for (int i = 0; i < numberOfLap + 1; i++) {
			System.out.println(i+". lap");
			DomReadRMIJO2.ReadLapByID(doc,String.valueOf(i));
		}
		String id =ReadID();
			Scanner sc = new Scanner(System.in);
			System.out.println("Név: ");
			String nev = sc.nextLine();
			System.out.print("Szín: ");
			String szin = sc.nextLine();
			System.out.print("Darabszám:");
			String db = sc.nextLine();
			System.out.print("Típus: ");
			String tipus = sc.nextLine();
		sc.close();
		NodeList nodeList = doc.getElementsByTagName("lap");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nNode = nodeList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nNode;
				String sid = element.getAttribute("id");
				if (sid.equals(id)) {
					Node node1 = element.getElementsByTagName("Nev").item(0);
					node1.setTextContent(nev);
					Node node2 = element.getElementsByTagName("Szin").item(0);
					node2.setTextContent(szin);
					Node node3 = element.getElementsByTagName("Darabszam").item(0);
					node3.setTextContent(db);
					Node node4 = element.getElementsByTagName("Tipus").item(0);
					node4.setTextContent(tipus);
					 
					System.out.println("Sikeres módosítás");
				}
			}
		}
		ModifyXML(doc);
	}
	private static String ReadID() {
			Scanner sc = new Scanner(System.in);
			System.out.print("\nid:");
			String id = sc.nextLine();
			return id;
		}
	
	public static void ModifyXML(Document doc) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("XMLRMIJO2.xml"));
		transformer.transform(source, result);
	}
}
