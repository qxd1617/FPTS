import java.io.InputStream;
import java.net.URLConnection;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.net.URL;


/**
 * Created by Owen on 4/4/2016.
 */
public class WebServices implements Runnable {
    //URL for the web services. Two parts as the symbol is added in the query process.
    static String urlpart1 = "http://query.yahooapis.com/v1/public/yql?q=select%20LastTradePriceOnly%20from%20yahoo.finance.quotes" +
            "%20where%20symbol%20in%20(%22";
    static String urlpart2 = "%22)&env=store://datatables.org/alltableswithkeys";
    //update method to parse the data into the equities



    //Update takes in a list of stocks to be updated.
    public static float Update(String ticker)throws Exception {

        //We loop through this process for each stock

        // Create a URL and open a connection
        URL YahooURL = new URL(urlpart1 + ticker + urlpart2);
        URLConnection connection = YahooURL.openConnection();

        Document doc = parseXML(connection.getInputStream());
        NodeList descNodes = doc.getElementsByTagName("LastTradePriceOnly");

        for (int i = 0; i < descNodes.getLength(); i++) {
            return Float.parseFloat(descNodes.item(i).getTextContent());
        }
        return 0;
    }
    private static Document parseXML(InputStream stream)
            throws Exception
    {
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try
        {
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

            doc = objDocumentBuilder.parse(stream);
        }
        catch(Exception ex)
        {
            throw ex;
        }

        return doc;
    }
    //29
    @Override
    public void run() {

    }
}
