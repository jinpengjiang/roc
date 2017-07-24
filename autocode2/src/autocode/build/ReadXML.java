package autocode.build;
import java.sql.*;
import org.*;
import autocode.utility.XMLParseUtility;

import org.apache.crimson.tree.XmlDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import java.io.*; //Java基础包，包含各种IO操作
import java.util.*; //Java基础包，包含各种标准数据结构操作
import javax.xml.parsers.*; //XML解析器接口
import org.w3c.dom.*; //XML的DOM实现
import org.apache.crimson.tree.XmlDocument; //写XML文件要用到

import java.text.*;
/** 解析XML配置文件<br> */
public class ReadXML {
  private java.util.List metaBeans=new ArrayList();
  public static void main(String[] args) {
    new ReadXML();
    //new PackageBean("c:\\config.xml");
  }

  /**
	 * 构造一个无参的构造函数
	 */
  ReadXML() {

  }

  
  /**
	 * 解析XML配置文件
	 * @param inFile 配置文件路径
	 * @return PackageBean 返回解析完成的模块
	 */
  public PackageBean readXMLFile(String inFile) throws Exception {
    PackageBean ret = new PackageBean();
    Element root = XMLParseUtility.getXMLRootElement(inFile);
    NodeList list2=XMLParseUtility.getCount(root, "class");
    String moduleName = XMLParseUtility.getNodeAttribute(root, "module", "name",
        0);
    String modulePackage = XMLParseUtility.getNodeAttribute(root, "module",
        "package", 0);
    String resource=XMLParseUtility.getNodeAttribute(root,"module","resource",0);
    String datasource=XMLParseUtility.getNodeAttribute(root,"module","datasource",0);
    ret.setModuleName(moduleName);
    ret.setModulePackage(modulePackage);
    ret.setResource(resource);
    ret.setDatasource(datasource);
    
    /*System.out.println("1"+moduleName);
    System.out.println("2"+modulePackage);
    System.out.println("3"+resource);
    System.out.println("4"+datasource);*/


    for (int i = 0; i<list2.getLength(); i++) {
    	java.util.List propertyBeans=new ArrayList();
      MetaBean metaBean = new MetaBean();
      Element classEL = XMLParseUtility.getElement(root,"class",i);
      NodeList list=XMLParseUtility.getCount(classEL, "property");
      String className = XMLParseUtility.getNodeAttribute(root, "class", "name",
          i);
      String classInstance = XMLParseUtility.getNodeAttribute(root, "class",
          "instance", i);
      String classJavadoc = XMLParseUtility.getNodeAttribute(root, "class",
          "javadoc", i);
      String classTitle = XMLParseUtility.getNodeAttribute(root, "class",
          "title", i);
      String classTable = XMLParseUtility.getNodeAttribute(root, "class",
          "table", i);

      metaBean.setClassName(className);
      metaBean.setClassInstance(classInstance);
      metaBean.setClassJavadoc(classJavadoc);
      metaBean.setClassTitle(classTitle);
      metaBean.setClassTable(classTable);
      
      /*System.out.println("5"+className);
      System.out.println("6"+classInstance);
      System.out.println("7"+classJavadoc);
      System.out.println("8"+classTitle);
      System.out.println("9"+classTable);*/

      String idName=XMLParseUtility.getNodeAttribute(classEL, "id", "name",0);
      String idColumn=XMLParseUtility.getNodeAttribute(classEL, "id", "column",0);
      String idType=XMLParseUtility.getNodeAttribute(classEL, "id", "type",0);
      String idJavadoc=XMLParseUtility.getNodeAttribute(classEL, "id", "javadoc",0);
      String idTitle=XMLParseUtility.getNodeAttribute(classEL, "id", "title",0);
      String idLength=XMLParseUtility.getNodeAttribute(classEL, "id", "length",0);
      String idPrecision=XMLParseUtility.getNodeAttribute(classEL, "id", "precision",0);
      String idScale=XMLParseUtility.getNodeAttribute(classEL, "id", "scale",0);
      String idNullable=XMLParseUtility.getNodeAttribute(classEL, "id", "not-null",0);

      metaBean.setIdName(idName);
      metaBean.setIdColumn(idColumn);
      metaBean.setIdType(idType);
      metaBean.setIdJavadoc(idJavadoc);
      metaBean.setIdTitle(idTitle);
      metaBean.setIdTitle(idLength);
      metaBean.setIdTitle(idPrecision);
      metaBean.setIdTitle(idScale);
      metaBean.setIdTitle(idNullable);
      
      /*System.out.println("10"+idName);
      System.out.println("11"+idColumn);
      System.out.println("12"+idType);
      System.out.println("13"+idJavadoc);
      System.out.println("14"+idTitle);
      System.out.println(metaBean.getClassInstance());
      System.out.println("list.getLength()2=: "+list.getLength());*/
      
      for(int j=0;j<list.getLength();j++)
      {
        PropertyBean propertyBean=new PropertyBean();
        String propertyName=XMLParseUtility.getNodeAttribute(classEL, "property", "name",j);
        String propertyColumn=XMLParseUtility.getNodeAttribute(classEL, "property", "column",j);
        String propertyType=XMLParseUtility.getNodeAttribute(classEL, "property", "type",j);
        String propertyJavadoc=XMLParseUtility.getNodeAttribute(classEL, "property", "javadoc",j);
        String propertyTitle=XMLParseUtility.getNodeAttribute(classEL, "property", "title",j);
        
        String propertyLength=XMLParseUtility.getNodeAttribute(classEL, "property", "length",j);
        String propertyPrecision=XMLParseUtility.getNodeAttribute(classEL, "property", "precision",j);
        String propertyScale=XMLParseUtility.getNodeAttribute(classEL, "property", "scale",j);
        String propertyNullable=XMLParseUtility.getNodeAttribute(classEL, "property", "not-null",j);

        propertyBean.setPropertyName(propertyName);
        propertyBean.setPropertyColumn(propertyColumn);
        propertyBean.setPropertyType(propertyType);
        propertyBean.setPropertyJavadoc(propertyJavadoc);
        propertyBean.setPropertyTitle(propertyTitle);
        propertyBean.setPropertyLength(propertyLength);
        propertyBean.setPropertyPrecision(propertyPrecision);
        propertyBean.setPropertyScale(propertyScale);
        propertyBean.setPropertyNullable(propertyNullable);
        
        /*System.out.println("15"+propertyName);
        System.out.println("16"+propertyColumn);
        System.out.println("17"+propertyType);
        System.out.println("18"+propertyJavadoc);
        System.out.println("19"+propertyTitle);*/

        propertyBeans.add(propertyBean);
      /*  System.out.println(".......");
        System.out.println("j:= "+j);*/
      }
      metaBean.setPropertyBeans(propertyBeans);
      metaBeans.add(metaBean);
    }
    ret.setMetaBeans(metaBeans);
   return ret;
  }

   /**
	 * 解析XML配置文件
	 * @param inFile 配置文件路径
	 * @return PackageBean 返回解析完成的模块
	 */
  public PackageBean readXMLFile2(String inFile)
        throws Exception
    {
        PackageBean ret = new PackageBean();
        org.w3c.dom.Element root = XMLParseUtility.getXMLRootElement(inFile);
        NodeList list2 = XMLParseUtility.getCount(root, "class");
        String moduleName = XMLParseUtility.getNodeAttribute(root, "module", "name", 0);
        String modulePackage = XMLParseUtility.getNodeAttribute(root, "module", "package", 0);
        String resource = XMLParseUtility.getNodeAttribute(root, "module", "resource", 0);
        String datasource = XMLParseUtility.getNodeAttribute(root, "module", "datasource", 0);
        String username = XMLParseUtility.getNodeAttribute(root, "module", "username", 0);
        String password = XMLParseUtility.getNodeAttribute(root, "module", "password", 0);
        String isall = XMLParseUtility.getNodeAttribute(root, "module", "isall", 0);
        ret.setModuleName(moduleName);
        ret.setModulePackage(modulePackage);
        ret.setResource(resource);
        ret.setDatasource(datasource);
        ret.setUsername(username);
        ret.setPassword(password);
        ret.setIsall(isall);
		/*System.out.println("1"+moduleName);
		System.out.println("2"+modulePackage);
		System.out.println("3"+resource);
		System.out.println("4"+datasource);*/

        for(int i = 0; i < list2.getLength(); i++)
        {
            List propertyBeans = new ArrayList();
            MetaBean metaBean = new MetaBean();
            String className = XMLParseUtility.getNodeAttribute(root, "class", "name", i);
            String classInstance = XMLParseUtility.getNodeAttribute(root, "class", "instance", i);
            String classJavadoc = XMLParseUtility.getNodeAttribute(root, "class", "javadoc", i);
            String classTitle = XMLParseUtility.getNodeAttribute(root, "class", "title", i);
            String classTable = XMLParseUtility.getNodeAttribute(root, "class", "table", i);
            metaBean.setClassName(className);
            metaBean.setClassInstance(classInstance);
            metaBean.setClassJavadoc(classJavadoc);
            metaBean.setClassTitle(classTitle);
            metaBean.setClassTable(classTable);

			/*System.out.println("5"+className);
		  System.out.println("6"+classInstance);
		  System.out.println("7"+classJavadoc);
		  System.out.println("8"+classTitle);
		  System.out.println("9"+classTable);*/
            metaBeans.add(metaBean);
        }

        ret.setMetaBeans(metaBeans);
        return ret;
    }

    public PackageBean readXMLFile3(String inFile)
        throws Exception
    {
        PackageBean ret = new PackageBean();
        org.w3c.dom.Element root = XMLParseUtility.getXMLRootElement(inFile);
        NodeList list2 = XMLParseUtility.getCount(root, "class");
        String moduleName = XMLParseUtility.getNodeAttribute(root, "module", "name", 0);
        String modulePackage = XMLParseUtility.getNodeAttribute(root, "module", "package", 0);
        String resource = XMLParseUtility.getNodeAttribute(root, "module", "resource", 0);
        String datasource = XMLParseUtility.getNodeAttribute(root, "module", "datasource", 0);
        String username = XMLParseUtility.getNodeAttribute(root, "module", "username", 0);
        String password = XMLParseUtility.getNodeAttribute(root, "module", "password", 0);
        ret.setModuleName(moduleName);
        ret.setModulePackage(modulePackage);
        ret.setResource(resource);
        ret.setDatasource(datasource);
        ret.setUsername(username);
        ret.setPassword(password);
        for(int i = 0; i < list2.getLength(); i++)
        {
            List propertyBeans = new ArrayList();
            MetaBean metaBean = new MetaBean();
            String className = XMLParseUtility.getNodeAttribute(root, "class", "name", i);
            String classInstance = XMLParseUtility.getNodeAttribute(root, "class", "instance", i);
            String classJavadoc = XMLParseUtility.getNodeAttribute(root, "class", "javadoc", i);
            String classTitle = XMLParseUtility.getNodeAttribute(root, "class", "title", i);
            String classTable = XMLParseUtility.getNodeAttribute(root, "class", "table", i);
            metaBean.setClassName(className);
            metaBean.setClassInstance(classInstance);
            metaBean.setClassJavadoc(classJavadoc);
            metaBean.setClassTitle(classTitle);
            metaBean.setClassTable(classTable);
            metaBeans.add(metaBean);
        }

        ret.setMetaBeans(metaBeans);
        return ret;
    }
}
