package autocode.utility;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.crimson.tree.XmlDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import autocode.exceptionCollection.XMLParseInvalidException;

public class XMLParseUtility implements Serializable {
        /**
         * 返回根节点
         * @param inputFile
         * @return
         */
        public static Element getXMLRootElement(String inputFile){
                Element root=null;
                try{
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                        dbf.setCoalescing(true);
                        DocumentBuilder db = null;
                        db = dbf.newDocumentBuilder();
                        Document doc = null;
                        doc = db.parse(inputFile);
                        root = doc.getDocumentElement();
                }catch(IOException ioe){
                        try{throw new XMLParseInvalidException();
                        }catch(XMLParseInvalidException e){e.printStackTrace();}
                }catch(SAXException sax){
                        try{throw new XMLParseInvalidException();
                        }catch(XMLParseInvalidException e){e.printStackTrace();}
                }catch(ParserConfigurationException pce){
                        try{throw new XMLParseInvalidException();
                        }catch(XMLParseInvalidException e){e.printStackTrace();}
                }
                return root;
        }

        /**
         * 返回已有的doc
         * @param inputFile
         * @return
         */
        public static Document getXMLDocument(String inputFile){
                Element root=null;
                Document doc = null;
                try{
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                        dbf.setCoalescing(true);
                        DocumentBuilder db = null;
                        db = dbf.newDocumentBuilder();
                        doc = db.parse(inputFile);
                }catch(IOException ioe){
                        try{throw new XMLParseInvalidException();
                        }catch(XMLParseInvalidException e){e.printStackTrace();}
                }catch(SAXException sax){
                        try{throw new XMLParseInvalidException();
                        }catch(XMLParseInvalidException e){e.printStackTrace();}
                }catch(ParserConfigurationException pce){
                        try{throw new XMLParseInvalidException();
                        }catch(XMLParseInvalidException e){e.printStackTrace();}
                }
                return doc;
        }

        /**
         * 返回创建的doc
         * @param rootName
         * @return
         */
        public static Document getNewXMLDocument(){
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setCoalescing(true);
                DocumentBuilder db = null;
                try{
                        db = dbf.newDocumentBuilder();
                }catch(ParserConfigurationException pce){
                        try{throw new XMLParseInvalidException();
                        }catch(XMLParseInvalidException e){e.printStackTrace();}
                }
                return db.newDocument();
        }

        /**
         * 返回元素
         * @param superElement 上一级的根元素
         * @param selfElement 本元素的名字
         * @param position 元素集合中的位置
         * @return
         */
        public static Element getElement(Element superElement,String selfElement,int position){
                return (Element)superElement.getElementsByTagName(
                        selfElement).item(position);
        }

        /**
         * 返回单节点值
         * @param superElement 上一级的根元素
         * @param selfElement 本元素的名字
         * @param position 元素集合中的位置
         * @return
         */
        //如果selfElement=null || selfElement.length()==0说明是根节点有值
        public static String getNodeValue(Element superElement,String selfElement,int position){
                if((selfElement==null || selfElement.length()==0) && position!=0)
                        try{throw new XMLParseInvalidException();
                        }catch(XMLParseInvalidException e){e.printStackTrace();}
                NodeList fPath=null;
                Element filePathElement=null;
                Text filePathText=null;
                if(selfElement==null || selfElement.length()==0){
                        filePathText=(Text)superElement.getFirstChild();
                }else{
                        fPath = superElement.getElementsByTagName(selfElement);
                        filePathElement=(Element)fPath.item(position);
                        filePathText=(Text)filePathElement.getFirstChild();
                }
                return filePathText.getNodeValue();
        }

        /**
         * 设置单节点值
         * @param superElement 上一级的根元素
         * @param selfElement 本元素的名字
         * @param nodeValue 要设置值
         * @param position 元素集合中的位置
         */
        //如果selfElement=null || selfElement.length()==0说明是根节点有值
        public static void setNodeValue(Element superElement,
                String selfElement,String nodeValue,int position){
                if((selfElement==null || selfElement.length()==0) && position!=0)
                        try{throw new XMLParseInvalidException();
                        }catch(XMLParseInvalidException e){e.printStackTrace();}
                NodeList fPath=null;
                Element filePathElement=null;
                if(selfElement==null || selfElement.length()==0){
                        superElement.setNodeValue(nodeValue);
                }else{
                        fPath = superElement.getElementsByTagName(selfElement);
                        filePathElement=(Element)fPath.item(position);
                        filePathElement.setNodeValue(nodeValue);
                }
        }

        /**
         * 返回属性值
         * @param superElement 上一级的根元素
         * @param selfElement 本元素的名字
         * @param attributeName 本元素的属性名字
         * @param position 元素集合中的位置
         * @return
         */
        //如果selfElement=null，说明是根节点有属性
        public static String getNodeAttribute(Element superElement,String selfElement,
                String attributeName,int position){
                if((selfElement==null || selfElement.length()==0) && position!=0)
                        try{throw new XMLParseInvalidException();
                        }catch(XMLParseInvalidException e){e.printStackTrace();}
                NodeList nodeList=null;
                Element providerUrl=null;
                if(selfElement==null || selfElement.length()==0){
                        providerUrl=superElement;
                }else{
                        nodeList = superElement.getElementsByTagName(selfElement);
                        providerUrl = (Element)nodeList.item(position);
                }
                return providerUrl.getAttribute(attributeName);
        }

        /**
         * 设置属性值
         * @param superElement 上一级的根元素
         * @param selfElement 本元素的名字
         * @param attributeName 本元素的属性名字
         * @param attributeValue 要设置值
         * @param position 元素集合中的位置
         */
        //如果selfElement=null，说明是根节点有属性
        public static void setNodeAttribute(Element superElement,
                String selfElement,String attributeName,String attributeValue,int position){
                if((selfElement==null || selfElement.length()==0) && position!=0)
                        try{throw new XMLParseInvalidException();
                        }catch (XMLParseInvalidException e){e.printStackTrace();}
                NodeList nodeList=null;
                Element providerUrl=null;
                if(selfElement==null || selfElement.length()==0){
                        providerUrl=superElement;
                }else{
                        nodeList = superElement.getElementsByTagName(selfElement);
                        providerUrl = (Element)nodeList.item(position);
                }
                providerUrl.setAttribute(attributeName,attributeValue);
        }

        /**
         * 写xml(servlet写有问题！会报XmlDocument错误)
         * @param doc
         * @param file
         */
        public static void writeXMLUseCrimson(Document doc,File file,String filePath){
                FileOutputStream outStream=null;
                try {
                        if(file!=null)	outStream = new FileOutputStream(file);
                        else outStream = new FileOutputStream(filePath);
                        OutputStreamWriter outWriter = new OutputStreamWriter(outStream);
                        try {
                                ((XmlDocument)doc).write(outWriter, ApplicationConfig.encoding);
                                outWriter.close();
                                outStream.close();
                        } catch (IOException e1) {e1.printStackTrace();}
                } catch (FileNotFoundException e) {e.printStackTrace();}
        }

        /**
         * 写xml
         * @param doc
         * @param file
         */
        public static void writeXMLUseDom(Document doc,File file,String filePath){
                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer;
                try {
                        transformer = tFactory.newTransformer();
                        transformer.setOutputProperty(OutputKeys.ENCODING,ApplicationConfig.encoding);
                        DOMSource source = new DOMSource(doc);
                        StreamResult result=null;
                        if(file!=null) result = new StreamResult(file);
                        else result = new StreamResult(new java.io.File(filePath));
                        try {
                                transformer.transform(source, result);
                        } catch (TransformerException e1) {e1.printStackTrace();}
                } catch (TransformerConfigurationException e) {e.printStackTrace();}
        }
        
       public static NodeList getCount(Element superElement,String selfElement){
    	   NodeList list=superElement.getElementsByTagName(selfElement);
    	   return list;
       }
      
}
