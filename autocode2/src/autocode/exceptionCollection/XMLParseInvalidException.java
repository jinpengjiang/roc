package autocode.exceptionCollection;
import java.io.Serializable;

public class XMLParseInvalidException extends Exception implements Serializable{
  public XMLParseInvalidException() {
    super();
    System.err.println("XML Parse Error!");
  }
}
