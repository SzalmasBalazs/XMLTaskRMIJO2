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
			File xmlFile = new File("XMLRMIJO2.xml"); //beolvasand� file.
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //Az XML dokumentumb�l DOM objektumot csin�lunk.
			
			DocumentBuilder dBuilder = factory.newDocumentBuilder(); //Document lek�r�s�hez XML file.
			Document doc =	dBuilder.parse(xmlFile); //Dokumentum lek�r�se.
			doc.getDocumentElement().normalize();
			System.out.println("Magic Verseny inform�ci�k lek�r�se.");
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
		NodeList nList =doc.getElementsByTagName("verseny");	//verseny tag� elemek lek�r�se egy list�ba.
		for(int i = 0;i < nList.getLength();i++) {	//listaiter�tor
			Node nNode = nList.item(i);	
			Element element = (Element) nNode;	// A lista aktu�lis elem�t,lek�r�se ut�n, Element� konvert�ljuk.
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				String formatum = element.getElementsByTagName("formatum").item(0).getTextContent();
				String helyszin = element.getElementsByTagName("helyszin").item(0).getTextContent();
				String korszam = element.getElementsByTagName("korok_szama").item(0).getTextContent();
				String versenyID = element.getAttribute("versenyID");		
				System.out.println("Verseny inform�ci�k: \tForm�tum:\t"+ formatum+
						"\n\tHelysz�n:\t"+helyszin+
						"\n\tK�r�k Sz�ma:\t"+korszam);
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
				System.out.println("\n" + (i+1)+". versenyz� \n\tAz aktu�lis k�r h�tral�v� ideje:\t"+idotartam);
				
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
					System.out.println("J�tekos neve: "+ nev +
							"\nSzemelyes adatok:\t \n\tSz�let�si D�tum:\t" + szul_datum + 
							"\n\tSz�let�si orsz�g:\t"+ szul_orszag + "\n\tTelefonsz�m:\t" + telszam);
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
					System.out.println("Pakli adatok: \t Pakli n�v: \t"+nev+
							"\n\tBan Lista: \t"+banlist+
							"\n\tLapsz�m: \t"+lapszam);
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
					System.out.println("\tLap n�v: \t"+nev+
							"\n\tSz�n: \t"+szin+
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
					System.out.println("\tLap n�v: \t"+nev+
							"\n\tSz�n: \t"+szin+
							"\n\tdb: \t"+db+
							"\n\tTipus: \t"+tipus);
				}
			}
		}
	}	
}
