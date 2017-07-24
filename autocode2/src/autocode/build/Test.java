package autocode.build;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class Test {

	public static void main(String args[]){
		System.out.println("start");
		ReadXML read=new ReadXML();
		try {
			PackageBean packageBean=read.readXMLFile("E:/AutoCode from bll20061226/config.xml");
			System.out.println("A"+packageBean.getDatasource());
			System.out.println("B"+packageBean.getModuleName());
			System.out.println("C"+packageBean.getModulePackage());
			System.out.println("D"+packageBean.getResource());
			List list=packageBean.getMetaBeans();
			Iterator iter=list.iterator();
			while(iter.hasNext()){
				MetaBean metaBean=(MetaBean) iter.next();
				System.out.println("D"+metaBean.getClassInstance());
				System.out.println("E"+metaBean.getClassJavadoc());
				System.out.println("F"+metaBean.getClassName());
				System.out.println("G"+metaBean.getClassTable());
				System.out.println("H"+metaBean.getClassTitle());
				System.out.println("I"+metaBean.getIdColumn());
				System.out.println("J"+metaBean.getIdJavadoc());
				System.out.println("K"+metaBean.getIdName());
				System.out.println("L"+metaBean.getIdTitle());
				System.out.println("M"+metaBean.getIdType());
				List list2=metaBean.getPropertyBeans();
				Iterator iter2=list2.iterator();
				while(iter2.hasNext()){
					PropertyBean propertyBean=(PropertyBean) iter2.next();
					System.out.println("N"+propertyBean.getPropertyColumn());
					System.out.println("O"+propertyBean.getPropertyJavadoc());
					System.out.println("P"+propertyBean.getPropertyName());
					System.out.println("Q"+propertyBean.getPropertyTitle());
					System.out.println("R"+propertyBean.getPropertyType());
				}
			}
			
			ReadXML rx = new ReadXML();
			packageBean = rx.readXMLFile("E:/AutoCode from bll20061226/config.xml");
			System.out.println("readXMLFile(config) success");
			new tag(packageBean);
			tag t =new tag();
			Map map=t.getClassPropertyTitle();
			System.out.println(map.size());
			List list1=(List) map.get(Integer.toString(0));
			List list2=(List) map.get(Integer.toString(1));
			System.out.println(list1.get(0));
			System.out.println(list1.get(1));
			System.out.println(list2.get(0));
			System.out.println(list2.get(1));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
