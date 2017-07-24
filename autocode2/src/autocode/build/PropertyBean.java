package autocode.build;

/** 参数<br> */
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
  /** 参数名称<br> */
private String propertyName;
/** 参数的表字段名<br> */
private String propertyColumn;
/** 参数类型<br> */
private String propertyType;
/** 参数注释<br> */
private String propertyJavadoc;
/** 参数标题<br> */
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
