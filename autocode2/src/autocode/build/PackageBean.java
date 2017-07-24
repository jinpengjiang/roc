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

import java.io.*; //Java基础包，包含各种IO操作
import java.util.*; //Java基础包，包含各种标准数据结构操作
import javax.xml.parsers.*; //XML解析器接口
import org.w3c.dom.*; //XML的DOM实现
import org.apache.crimson.tree.XmlDocument; //写XML文件要用到

import java.text.*;

/** 模块<br>*/
public class PackageBean {
	/** 模块中的CLASS类集合<br> */
  private java.util.List metaBeans;
  /** 权限代码<br> */
  private String resource;
  private String datasource;
	private String username;
   private String password;
   private String isall;
  /** 模块名<br> */
  private String moduleName;
  
  /** 包名<br> */
  private String modulePackage; 

  /**
	 * 构造一个无参的构造函数
	 */
  public  PackageBean(){

  }

  /**
	 * 构造构造函数，初始化模块名、包名、Class类集合
	 * @param configFile 配置文件
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
