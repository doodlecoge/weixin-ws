package wang.huaichao.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/5/6.
 */
public class XmlUtils {
    public static Node queryNode(String sXml, String sXpath)
            throws ParserConfigurationException, IOException,
            SAXException, XPathExpressionException {
        final Document doc = _buildDocument(sXml);
        final XPathExpression expr = _buildXpathExpression(sXpath);
        return (Node) expr.evaluate(doc, XPathConstants.NODE);
    }

    public static String queryString(String sXml, String sXpath)
            throws ParserConfigurationException, IOException,
            SAXException, XPathExpressionException {
        final Document doc = _buildDocument(sXml);
        final XPathExpression expr = _buildXpathExpression(sXpath);
        return expr.evaluate(doc, XPathConstants.STRING).toString();
    }

    private static Document _buildDocument(String sXml)
            throws ParserConfigurationException, IOException,
            SAXException {
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(_stringToStream(sXml));
    }

    private static InputStream _stringToStream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    private static XPathExpression _buildXpathExpression(String sXpath)
            throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        return xPath.compile(sXpath);
    }
}
