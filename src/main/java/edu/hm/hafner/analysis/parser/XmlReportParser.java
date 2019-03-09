package edu.hm.hafner.analysis.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.hm.hafner.analysis.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * A parser for generic XML report files.
 *
 * @author Derrick Gibelyou
 */
public class XmlReportParser extends IssueParser {
    private static final long serialVersionUID = 6507147028628714706L;

    public XmlReportParser() {
        super();
    }

    @Override
    public Report parse(final ReaderFactory readerFactory) throws ParsingException {
        Report report = new Report();
        Document doc = readerFactory.readDocument();
        doc.getDocumentElement().normalize();

        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList nodeList;
        try {
            String expression = "/issues/issue";
            nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return null;
        }

        for (int ii = 0; ii < nodeList.getLength(); ii++) {
            Node issue = nodeList.item(ii);
            NodeList properties = issue.getChildNodes();
            IssueBuilder builder = new IssueBuilder();
            for (int jj = 0; jj < properties.getLength(); jj++) {
                Node prop = properties.item(jj);
                addProperty(builder, prop);
            }
            report.add(builder.build());
//            System.out.println("Success: " + builder.build().toString());
        }
        return report;
    }

    private void addProperty(IssueBuilder builder, Node prop) {
        String name = prop.getNodeName();
        String value = prop.getTextContent();
        switch (name)
        {
            case "severity":
                builder.setSeverity(new Severity(value.toUpperCase()));
                break;
            case "lineStart":
                builder.setLineStart(value);
                break;
            case "lineEnd":
                builder.setLineEnd(value);
                break;
            case "columnStart":
                builder.setColumnStart(value);
                break;
            case "columnEnd":
                builder.setColumnEnd(value);
                break;
            case "fileName":
                builder.setFileName(value);
                break;
            case "directory":
                builder.setDirectory(value);
                break;
            case "category":
                builder.setCategory(value);
                break;
            case "type":
                builder.setType(value);
                break;
            case "message":
                builder.setMessage(value);
                break;
            case "description":
                builder.setDescription(value);
                break;
            case "packageName":
                builder.setPackageName(value);
                break;
            case "moduleName":
                builder.setModuleName(value);
                break;
            default:
                Logger.getLogger(XmlReportParser.class.getName())
                        .log(Level.WARNING, "Invalid Issue XML element: " + name);
                break;
        }
    }

}

