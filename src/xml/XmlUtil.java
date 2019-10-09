package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Description xml工具类
 * @Author pc
 * @Date 2019/10/03 11:32
 */
public class XmlUtil {

    /**
      * @Description map转xml
      * @Author guojianfeng
      * @Date 2019/10/03 11:40
      * @param parameters key和value都为string类型的map参数对象
      * @Return
      */

    public static String mapToXml(Map<String, Object> parameters){
        StringBuffer sb = new StringBuffer();
        List<String> keys = new ArrayList<String>(parameters.keySet());
        Collections.sort(keys);
        sb.append("<xml>");
        for (String key : keys){
            Object value = parameters.get(key);
            if (null != value && !"".equals(value) && !"appkey".equals(key)){
                sb.append("<"+key+">"+value+"</"+key+">\n");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }


    /**
      * @Description xml转map
      * @Author guojianfeng
      * @Date 2019/10/03 11:44
      * @param xmlString 需要转换的xml字符串你
      * @Return
      */

    public static Map xmlToMap(String xmlString) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //字符串转换为字节流
        ByteArrayInputStream tInputStringStream = null;
        if (xmlString != null && !xmlString.trim().equals("")) {
            tInputStringStream = new ByteArrayInputStream(xmlString.getBytes());
        }

        InputStream inputStream = tInputStringStream;
        Document document = builder.parse(inputStream);

        //获取到document的全部节点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;

        //开始组装map数据
        Map<String, Object> map = new HashMap<String, Object>();
        int i = 0;
        while (i<allNodes.getLength()){
            node = allNodes.item(i);
            if (node instanceof Element) {
                map.put(node.getNodeName(), node.getTextContent());
            }
            i++;
        }
        return map;
    }
}
