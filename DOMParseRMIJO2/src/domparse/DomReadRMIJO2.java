package domparse;

import java.io.File;
import java.io.IOException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class DomReadRMIJO2 {

	public static void main(String[] args) {
		try {
			File xmlFile = new File("XMLRMIJO2.xml"); //beolvasandó file.
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //Az XML dokumentumból DOM objektumot csinálunk.
			
			DocumentBuilder dBuilder = factory.newDocumentBuilder(); //Document lekéréséhez XML file.
			Document doc =	dBuilder.parse(xmlFile); //Dokumentum lekérése.
			doc.getDocumentElement().normalize();
			System.out.println("Magic Verseny információk lekérése.");
			Read(doc);
		}catch(ParserConfigurationException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(SAXException e) {
			e.printStackTrace();
		}

	}

	public static void Read(Document doc) {
		NodeList nList =doc.getElementsByTagName("verseny");	//verseny tagú elemek lekérése egy listába.
		for(int i = 0;i < nList.getLength();i++) {	//listaiterátor
			Node nNode = nList.item(i);	
			Element element = (Element) nNode;	// A lista aktuális elemét,lekérése után, Elementé konvertáljuk.
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				String formatum = element.getElementsByTagName("formatum").item(0).getTextContent();
				String helyszin = element.getElementsByTagName("helyszin").item(0).getTextContent();
				String korszam = element.getElementsByTagName("korok_szama").item(0).getTextContent();
				String versenyID = element.getAttribute("versenyID");		
				System.out.println("Verseny információk: \tFormátum:\t"+ formatum+
						"\n\tHelyszín:\t"+helyszin+
						"\n\tKörök Száma:\t"+korszam);
				ReadJatekByID(doc,versenyID);
			}
		}
	}
	
	public static void ReadJatekByID(Document doc, String versenyID) {
		NodeList nList =doc.getElementsByTagName("jatek");	
		for(int i = 0;i < nList.getLength();i++) {	
			Node nNode = nList.item(i);	
			Element element = (Element) nNode;	
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				String idotartam = element.getElementsByTagName("idotartam").item(0).getTextContent();
				String jatekosID = element.getAttribute("jatekosID");
				System.out.println("\n" + (i+1)+". versenyzõ \n\tAz aktuális kör hátralévõ ideje:\t"+idotartam);
				
				ReadJatekosByID(doc,jatekosID);
			}
		}
	}

	public static void ReadJatekosByID(Document doc, String jatekosID) {
		
		NodeList nList = doc.getElementsByTagName("jatekos");
		for(int i = 0;i<nList.getLength();i++) {
			Node nNode = nList.item(i);
			Element element = (Element) nNode;
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				if(element.getAttribute("id").equals(jatekosID)) {
					String nev = element.getElementsByTagName("Nev").item(0).getTextContent();
					String szul_datum = element.getElementsByTagName("Szul_datum").item(0).getTextContent();
					String szul_orszag = element.getElementsByTagName("Sul_orszag").item(0).getTextContent();
					String telszam = element.getElementsByTagName("Telefonszam").item(0).getTextContent();
					System.out.println("Játekos neve: "+ nev +
							"\nSzemelyes adatok:\t \n\tSzületési Dátum:\t" + szul_datum + 
							"\n\tSzületési ország:\t"+ szul_orszag + "\n\tTelefonszám:\t" + telszam);
					String pakliID = element.getAttribute("pakliID");
					ReadPakliByID(doc,pakliID);
				}
			}
		}
		
	}

	public static void ReadPakliByID(Document doc, String pakliID) {
		
		NodeList nList =doc.getElementsByTagName("pakli");
		for(int i = 0;i<nList.getLength();i++) {
			Node nNode = nList.item(i);
			Element element = (Element) nNode;
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				if(element.getAttribute("id").equals(pakliID)) {
					String nev = element.getElementsByTagName("Nev").item(0).getTextContent();
					String banlist = element.getElementsByTagName("Ban_lista").item(0).getTextContent();
					String lapszam = element.getElementsByTagName("Lapszam").item(0).getTextContent();
					System.out.println("Pakli adatok: \t Pakli név: \t"+nev+
							"\n\tBan Lista: \t"+banlist+
							"\n\tLapszám: \t"+lapszam);
					ReadLapByPakliID(doc,pakliID);
				}
			}
		}
		
	}

	public static void ReadLapByPakliID(Document doc, String pakliID) {
		System.out.println("A pakli fontosabb lapjai:");
		NodeList nList =doc.getElementsByTagName("lap");
		for(int i = 0;i<nList.getLength();i++) {
			Node nNode = nList.item(i);
			Element element = (Element) nNode;
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				if(element.getAttribute("pakliID").equals(pakliID)) {
					String nev = element.getElementsByTagName("Nev").item(0).getTextContent();
					String szin = element.getElementsByTagName("Szin").item(0).getTextContent();
					String db = element.getElementsByTagName("Darabszam").item(0).getTextContent();
					String tipus = element.getElementsByTagName("Tipus").item(0).getTextContent();
					System.out.println("\tLap név: \t"+nev+
							"\n\tSzín: \t"+szin+
							"\n\tdb: \t"+db+
							"\n\tTipus: \t"+tipus);
				}
			}
		}
	}
	public static void ReadLapByID(Document doc, String lapID) {
		NodeList nList =doc.getElementsByTagName("lap");
		for(int i = 0;i<nList.getLength();i++) {
			Node nNode = nList.item(i);
			Element element = (Element) nNode;
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				if(element.getAttribute("id").equals(lapID)) {
					String nev = element.getElementsByTagName("Nev").item(0).getTextContent();
					String szin = element.getElementsByTagName("Szin").item(0).getTextContent();
					String db = element.getElementsByTagName("Darabszam").item(0).getTextContent();
					String tipus = element.getElementsByTagName("Tipus").item(0).getTextContent();
					System.out.println("\tLap név: \t"+nev+
							"\n\tSzín: \t"+szin+
							"\n\tdb: \t"+db+
							"\n\tTipus: \t"+tipus);
				}
			}
		}
	}	
}
