/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jUtil {

	public static Element getXmlElement(String FilePath) throws Exception {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new File(FilePath));
		Element root = document.getRootElement();
		return root;
	}

	public static Element getTeamElement(String FilePath, String TeamName,
			String ElementName, String ElementText) throws Exception {
		Element root = getXmlElement(FilePath);
		Element actionEl = null;
		for (Iterator i = root.elementIterator(TeamName); i.hasNext();) {
			actionEl = (Element) i.next();
			if (actionEl.elementText(ElementName).equals(ElementText)) {
				break;
			}
		}
		return actionEl;
	}

	public static String getElementText(String FilePath, String TeamName,
			String ElementName, String ElementText, String retElementName)
			throws Exception {
		String ActionClass = null;
		Element root = getXmlElement(FilePath);
		Element actionEl;
		for (Iterator i = root.elementIterator(TeamName); i.hasNext();) {
			actionEl = (Element) i.next();
			if (actionEl.elementText(ElementName).equals(ElementText)) {
				ActionClass = actionEl.elementText(retElementName);
				break;
			}
		}
		return ActionClass;
	}

	public static String getElementText(String FilePath, String ElementName)
			throws Exception {
		String ActionClass = null;
		SAXReader reader = new SAXReader();
		Document docement = reader.read(new File(FilePath));
		List list = null;
		list = docement.selectNodes(ElementName);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element el = (Element) it.next();
			ActionClass = el.getText();
		}
		return ActionClass;
	}
}
