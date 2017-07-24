package autocode.build;
import java.sql.*;
import org.*;
import autocode.utility.XMLParseUtility;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import java.io.*; //Java����������������IO����
import java.util.*; //Java���������������ֱ�׼���ݽṹ����
import javax.xml.parsers.*; //XML�������ӿ�
import org.w3c.dom.*; //XML��DOMʵ��
import org.apache.crimson.tree.XmlDocument; //дXML�ļ�Ҫ�õ�

import java.text.*;

/** ģ��<br>*/
public class PackageBean {
	/** ģ���е�CLASS�༯��<br> */
  private java.util.List metaBeans;
  /** Ȩ�޴���<br> */
  private String resource;
  private String datasource;
	private String username;
   private String password;
   private String isall;
  /** ģ����<br> */
  private String moduleName;
  
  /** ����<br> */
  private String modulePackage; 

  /**
	 * ����һ���޲εĹ��캯��
	 */
  public  PackageBean(){

  }

  /**
	 * ���칹�캯������ʼ��ģ������������Class�༯��
	 * @param configFile �����ļ�
	 */
  public PackageBean(String configFile){

    this.moduleName= "";
    this.modulePackage="";

    //for  classes in the moudle......

    MetaBean metaBean = new MetaBean();
    //for each property in the class
    //metaBean.setClassName()
    //......
    //end for propeties in this class.

    metaBeans.add(metaBean);

  //end  from calsses....

  }

public String getIsall() {
	return isall;
}

public void setIsall(String isall) {
	this.isall = isall;
}

public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

  public java.util.List getMetaBeans() {
    return metaBeans;
  }
  public void setMetaBeans(java.util.List metaBeans) {
    this.metaBeans = metaBeans;
  }
  public String getResource() {
    return resource;
  }
  public void setResource(String resource) {
    this.resource = resource;
  }
  public String getModuleName() {
    return moduleName;
  }
  public String getModulePackage() {
    return modulePackage;
  }
  public void setModulePackage(String modulePackage) {
    this.modulePackage = modulePackage;
  }
  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }
  public String getDatasource() {
    return datasource;
  }
  public void setDatasource(String datasource) {
    this.datasource = datasource;
  }
}
