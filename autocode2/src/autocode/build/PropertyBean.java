package autocode.build;

/** ����<br> */
public class PropertyBean {
  public static void main(String[] args) {
  }
  public String getPropertyColumn() {
    return propertyColumn;
  }
  public String getPropertyJavadoc() {
    return propertyJavadoc;
  }
  public String getPropertyTitle() {
    return propertyTitle;
  }
  public String getPropertyName() {
    return propertyName;
  }
  public String getPropertyType() {
    return propertyType;
  }
  public void setPropertyType(String propertyType) {
    this.propertyType = propertyType;
  }
  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }
  public void setPropertyJavadoc(String propertyJavadoc) {
    this.propertyJavadoc = propertyJavadoc;
  }
  public void setPropertyColumn(String propertyColumn) {
    this.propertyColumn = propertyColumn;
  }
  public void setPropertyTitle(String propertyTitle) {
    this.propertyTitle = propertyTitle;
  }
  /** ��������<br> */
private String propertyName;
/** �����ı��ֶ���<br> */
private String propertyColumn;
/** ��������<br> */
private String propertyType;
/** ����ע��<br> */
private String propertyJavadoc;
/** ��������<br> */
private String propertyTitle;


public String getPropertyLength() {
	return propertyLength;
}
public void setPropertyLength(String propertyLength) {
	this.propertyLength = propertyLength;
}
public String getPropertyPrecision() {
	return propertyPrecision;
}
public void setPropertyPrecision(String propertyPrecision) {
	this.propertyPrecision = propertyPrecision;
}
public String getPropertyScale() {
	return propertyScale;
}
public void setPropertyScale(String propertyScale) {
	this.propertyScale = propertyScale;
}
public String getPropertyNullable() {
	return propertyNullable;
}
public void setPropertyNullable(String propertyNullable) {
	this.propertyNullable = propertyNullable;
}
private String propertyLength;
private String propertyPrecision;

private String propertyScale;

private String propertyNullable;


}
