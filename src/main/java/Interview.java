import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Interview {


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        FileWriter fWriter = null;
        BufferedWriter writer = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File("src/main/resources/files/news.xml"));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("item");
            List<String> links = new ArrayList<>();
            List<String> names = new ArrayList<>();
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String link = element.getElementsByTagName("link").item(0).getTextContent();
                    String title = element.getElementsByTagName("title").item(0).getTextContent();
                    links.add(link);
                    names.add(title);
                }
            }
            try{
                fWriter = new FileWriter("parse.html");
                writer = new BufferedWriter(fWriter);
                for (int i = 0; i<=links.size() & i<= names.size(); i+=1) {
                    String ref = links.get(i);
                    String name = names.get(i);
                    System.out.println(ref);
                    writer.write("<a href = '" + ref + "'>" + name +  "</a><br>");
                    writer.flush();
                }
                writer.newLine();
                writer.close();
            }catch (Exception ex){
            }
    }
}

