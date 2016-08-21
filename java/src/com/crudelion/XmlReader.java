package com.crudelion;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.HashSet;

/**
 * Created by guliuzhong on 2016/8/21.
 */
public class XmlReader extends Config {
    public XmlReader(String XmlFile) throws ConfigException {
        super();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document document = null;
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(XmlFile);

            //target
            setTarget((String) xpath.evaluate("/sync/target/path", document, XPathConstants.STRING));
            //target
            switch (((String) xpath.evaluate("/sync/target/clean", document, XPathConstants.STRING)).toUpperCase().trim()) {
                case "1":
                case "TRUE":
                    setTargetClean(true);
                    break;
                case "0":
                case "FALSE":
                    setTargetClean(false);
                    break;
                default:
                    setTargetClean(true);
            }
            //source
            setSource((String) xpath.evaluate("/sync/source/path", document, XPathConstants.STRING));
            //sourceExclude
            HashSet<String> sourceExcludetmp = new HashSet<>();
            NodeList evaluate = (NodeList) xpath.evaluate("/sync/source/exclude/value", document, XPathConstants.NODESET);
            for (int i = 0; i < evaluate.getLength(); i++) {
                StringBuffer excludeExpand = new StringBuffer();
                if (evaluate.item(i).getTextContent().startsWith("/")) {
                    excludeExpand.append(getSource() + evaluate.item(i).getTextContent());
                } else {
                    excludeExpand.append(evaluate.item(i).getTextContent());
                }
                sourceExcludetmp.add(excludeExpand.toString());
            }
            setSourceExclude(sourceExcludetmp);

            //targetExclude
            if (!getTargetClean()) {
                HashSet<String> targetExcludetmp = new HashSet<>();
                NodeList targetEvaluate = (NodeList) xpath.evaluate("/sync/target/exclude/value", document, XPathConstants.NODESET);
                for (int i = 0; i < targetEvaluate.getLength(); i++) {
                    StringBuffer excludeExpand = new StringBuffer();
                    if (targetEvaluate.item(i).getTextContent().startsWith("/")) {
                        excludeExpand.append(getTarget() + targetEvaluate.item(i).getTextContent());
                    } else {
                        excludeExpand.append(targetEvaluate.item(i).getTextContent());
                    }
                    targetExcludetmp.add(excludeExpand.toString());
                }
                setTargetExclude(targetExcludetmp);
            }
        } catch (ParserConfigurationException e) {
            throw new ConfigException(e.getMessage(), e.getCause());
        } catch (SAXException e) {
            throw new ConfigException(e.getMessage(), e.getCause());
        } catch (IOException e) {
            throw new ConfigException(e.getMessage(), e.getCause());
        } catch (XPathExpressionException e) {
            throw new ConfigException(e.getMessage(), e.getCause());
        }
    }
}
