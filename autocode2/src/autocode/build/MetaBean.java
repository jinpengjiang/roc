package autocode.build;

/** ��<br> */
public class MetaBean {
	/** ����<br> */
  private String className; 
  /** ����ʵ��<br> */
  private String classInstance; 
  /** ��ע��<br> */
  private String classJavadoc;
  /** �����<br> */
  private String classTitle;
  /** ����صı�<br> */
  private String classTable;
  
  /** ����صı��������<br> */
  private String idName;
  /** ����صı���������ֶ���<br> */
  private String idColumn;
  /** ����صı����������<br> */
  private String idType;
  /** ����صı������ע��<br> */
  private String idJavadoc;
  /** ����صı����������<br> */
  private String idTitle;
  /** ����صı�������������<br> */
  
  private String idLength;


private String idPrecision;

  private String idScale;

  private String idNullable;
  private java.util.List propertyBeans;

  /**
   * ��������
   * @param s ����
   */
  public void setClassName(String s) {
    className= s;
    }
  
  /**
   *  �õ�����
   */
  public String getClassName() {
    return className;
    }
  
  /** ��������ʵ��
   *@param s ����ʵ��
   */
  public void setClassInstance(String s) {
    classInstance= s;
  }
  
  /** 
   * �õ�����ʵ��
   */
  public String getClassInstance() {
    return classInstance;
  }

  /** ������ע��
   *@param s ��ע��
   */
  public void setClassJavadoc(String s) {
    classJavadoc= s;
  }
  
  /** 
   * �õ���ע��
   */
  public String getClassJavadoc() {
    return classJavadoc;
  }

  /** ���������
   *@param s �����
   */
  public void setClassTitle(String s) {
    classTitle= s;
  }
  
  /** 
   * �õ������
   */
  public String getClassTitle() {
    return classTitle;
  }

  /** ��������صı�
   *@param s ����صı�
   */
  public void setClassTable(String s) {
    classTable= s;
  }
  
  /** 
   * �õ�����صı�
   */
  public String getClassTable() {
    return classTable;
  }

  /** ��������صı��������
   *@param s ����صı��������
   */
  public void setIdName(String s) {
    idName= s;
  }
  
  /** 
   * �õ�����صı��������
   */
  public String getIdName() {
    return idName;
  }

  /** ��������صı���������ֶ���
   *@param s ����صı���������ֶ���
   */
  public void setIdColumn(String s) {
    idColumn= s;
  }
  
  /** 
   * �õ�����صı���������ֶ���
   */
  public String getIdColumn() {
    return idColumn;
  }

  
  /** ��������صı����������
   *@param s ����صı����������
   */
  public void setIdType(String s) {
    idType= s;
  }
  
  /** 
   * �õ�����صı����������
   */
  public String getIdType() {
      return idType;
  }

  
  /** ��������صı������ע��
   *@param s ����صı������ע��
   */
  public void setIdJavadoc(String s) {
    idJavadoc= s;
  }
  
  /** 
   * �õ�����صı������ע��
   */
  public String getIdJavadoc() {
    return idJavadoc;
  }

  /** ��������صı����������
   *@param s ����صı����������
   */
  public void setIdTitle(String s) {
    idTitle= s;
  }
  
  /** 
   * �õ�����صı����������
   */
  public String getIdTitle() {
    return idTitle;
  }
  
  /** 
   * �õ�����صı�������������
   */
  public java.util.List getPropertyBeans() {
    return propertyBeans;
  }
  
  /** ��������صı�������������
   *@param s ����صı�������������
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
