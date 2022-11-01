package code.service;

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
import java.util.ArrayList;
import java.util.List;

public class NewsReader {

    private List<String> links = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public NewsReader() {
    }

    public List<String> getLinks() {
        return links;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void readPosts(String filepath) throws ParserConfigurationException, SAXException, IOException {
        FileWriter fWriter = null;
        BufferedWriter writer = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(new File(filepath));
        doc.getDocumentElement().normalize();
        NodeList list = doc.getElementsByTagName("item");
        for (int temp = 0; temp < list.getLength(); temp++) {
            Node node = list.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String link = element.getElementsByTagName("link").item(0).getTextContent();
                String title = element.getElementsByTagName("title").item(0).getTextContent();
                links.add(link);
                titles.add(title);
            }
        }
    }

    public void writePosts(List<String> links, List<String> titles, String filename) {
        FileWriter fWriter = null;
        BufferedWriter writer = null;
        try{
            fWriter = new FileWriter(filename);
            writer = new BufferedWriter(fWriter);
            for (int i = 0; i<=links.size() & i<= titles.size(); i+=1) {
                String ref = links.get(i);
                String name = titles.get(i);
                System.out.println(ref);
                writer.write("<a href = '" + ref + "'>" + name +  "</a><br>");
                writer.flush();
            }
            writer.newLine();
            writer.close();
        }catch (Exception ex){
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        NewsReader newsReader = new NewsReader();
        String d = "src/main/resources/files/news.xml";
        String b = "src/main/resources/files/ukr.xml";
        String c = "src/main/resources/files/zvezda.xml";
        String v = "parse.html";
        newsReader.readPosts(d);
        newsReader.readPosts(b);
        newsReader.readPosts(c);
        newsReader.writePosts(newsReader.getLinks(), newsReader.getTitles(), v);
        /*System.out.println(newsReader.getLinks());*/
    }
}
