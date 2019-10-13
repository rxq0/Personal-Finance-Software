package Dashboard.Util;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import Dashboard.Model.Currency;

public class XmlReader {

    private static XmlReader instance = null;
    private XmlReader() {
    }

    public static XmlReader getInstance() {
        if (instance == null) {
            instance = new XmlReader();
        }
        return instance;
    }

    public ArrayList<Currency> getCurrencies() {
        ArrayList<Currency> items = new ArrayList<Currency>();

        try {
            // Build a new document
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            // Specify the URL
            URL tcmbUrl = new URL("http://www.tcmb.gov.tr/kurlar/today.xml");

            // Parse the web page contents and save it into a document
            Document doc = builder.parse(tcmbUrl.openStream());

            // Create nodes for all the elements on the page named as "Currency"
            NodeList nodes = doc.getElementsByTagName("Currency");
            // Traverse the nodes and extract values
            for (int i = 0; i < nodes.getLength(); i++) {
                // Create a currency object for each node
                Currency currency = new Currency();
                Element element = (Element) nodes.item(i);

                currency.setCurrencyCode(element.getAttribute("CurrencyCode"));
                currency.setCurrencyName(getElementValue(element,"CurrencyName"));
                currency.setBanknoteBuying(extractFloat(getElementValue(element,"BanknoteBuying")));
                currency.setBanknoteSelling(extractFloat(getElementValue(element,"BanknoteSelling")));
                currency.setBanknoteBuyingForex(extractFloat(getElementValue(element,"ForexBuying")));
                currency.setBanknoteSellingForex(extractFloat(getElementValue(element,"ForexSelling")));
                currency.setCrossrateUSD(extractFloat(getElementValue(element,"CrossRateUSD")));
                currency.setCrossrateOther(extractFloat(getElementValue(element,"CrossRateOther")));//EUR - GBP - KWD
                System.out.println("name:"+currency.getCurrencyCode()+" crossrate:"+currency.getCrossrateUSD() + "other:" + currency.getCrossrateOther());
                items.add(currency);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    // Utility methods

    private String extractCharacterDataFromElement(Element e) {
        try {
            Node child = e.getFirstChild();
            if (child instanceof CharacterData) {
                CharacterData cd = (CharacterData) child;
                return cd.getData();
            }
        } catch (Exception ex) {
        }
        return "";
    }

    protected float extractFloat(String value) {
        if (value != null && !value.equals("")) {
            return Float.parseFloat(value);
        }
        return 0;
    }

    protected String getElementValue(Element parent, String label) {
        return extractCharacterDataFromElement((Element) parent
                .getElementsByTagName(label).item(0));
    }
}
