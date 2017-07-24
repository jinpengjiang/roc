package autocode.build;

/** 类<br> */
public class MetaBean {
	/** 类名<br> */
  private String className; 
  /** 对象实例<br> */
  private String classInstance; 
  /** 类注释<br> */
  private String classJavadoc;
  /** 类标题<br> */
  private String classTitle;
  /** 类相关的表<br> */
  private String classTable;
  
  /** 类相关的表的主键名<br> */
  private String idName;
  /** 类相关的表的主键表字段名<br> */
  private String idColumn;
  /** 类相关的表的主键类型<br> */
  private String idType;
  /** 类相关的表的主键注释<br> */
  private String idJavadoc;
  /** 类相关的表的主键标题<br> */
  private String idTitle;
  /** 类相关的表的其它对象类表<br> */
  
  private String idLength;


private String idPrecision;

  private String idScale;

  private String idNullable;
  private java.util.List propertyBeans;

  /**
   * 产生类名
   * @param s 类名
   */
  public void setClassName(String s) {
    className= s;
    }
  
  /**
   *  得到类名
   */
  public String getClassName() {
    return className;
    }
  
  /** 产生对象实例
   *@param s 对象实例
   */
  public void setClassInstance(String s) {
    classInstance= s;
  }
  
  /** 
   * 得到对象实例
   */
  public String getClassInstance() {
    return classInstance;
  }

  /** 产生类注释
   *@param s 类注释
   */
  public void setClassJavadoc(String s) {
    classJavadoc= s;
  }
  
  /** 
   * 得到类注释
   */
  public String getClassJavadoc() {
    return classJavadoc;
  }

  /** 产生类标题
   *@param s 类标题
   */
  public void setClassTitle(String s) {
    classTitle= s;
  }
  
  /** 
   * 得到类标题
   */
  public String getClassTitle() {
    return classTitle;
  }

  /** 产生类相关的表
   *@param s 类相关的表
   */
  public void setClassTable(String s) {
    classTable= s;
  }
  
  /** 
   * 得到类相关的表
   */
  public String getClassTable() {
    return classTable;
  }

  /** 产生类相关的表的主键名
   *@param s 类相关的表的主键名
   */
  public void setIdName(String s) {
    idName= s;
  }
  
  /** 
   * 得到类相关的表的主键名
   */
  public String getIdName() {
    return idName;
  }

  /** 产生类相关的表的主键表字段名
   *@param s 类相关的表的主键表字段名
   */
  public void setIdColumn(String s) {
    idColumn= s;
  }
  
  /** 
   * 得到类相关的表的主键表字段名
   */
  public String getIdColumn() {
    return idColumn;
  }

  
  /** 产生类相关的表的主键类型
   *@param s 类相关的表的主键类型
   */
  public void setIdType(String s) {
    idType= s;
  }
  
  /** 
   * 得到类相关的表的主键类型
   */
  public String getIdType() {
      return idType;
  }

  
  /** 产生类相关的表的主键注释
   *@param s 类相关的表的主键注释
   */
  public void setIdJavadoc(String s) {
    idJavadoc= s;
  }
  
  /** 
   * 得到类相关的表的主键注释
   */
  public String getIdJavadoc() {
    return idJavadoc;
  }

  /** 产生类相关的表的主键标题
   *@param s 类相关的表的主键标题
   */
  public void setIdTitle(String s) {
    idTitle= s;
  }
  
  /** 
   * 得到类相关的表的主键标题
   */
  public String getIdTitle() {
    return idTitle;
  }
  
  /** 
   * 得到类相关的表的其它对象类表
   */
  public java.util.List getPropertyBeans() {
    return propertyBeans;
  }
  
  /** 产生类相关的表的其它对象类表
   *@param s 类相关的表的其它对象类表
   */
  public void setPropertyBeans(java.util.List propertyBeans) {
    this.propertyBeans = propertyBeans;
  }



  public String getIdLength() {
	return idLength;
}

public void setIdLength(String idLength) {
	this.idLength = idLength;
}

public String getIdPrecision() {
	return idPrecision;
}

public void setIdPrecision(String idPrecision) {
	this.idPrecision = idPrecision;
}

public String getIdScale() {
	return idScale;
}

public void setIdScale(String idScale) {
	this.idScale = idScale;
}

public String getIdNullable() {
	return idNullable;
}

public void setIdNullable(String idNullable) {
	this.idNullable = idNullable;
}









}
