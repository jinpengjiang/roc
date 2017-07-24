package autocode.build;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**标签<br>*/
public class tag {
	
	private static PackageBean packageBean;
	/**模块类<br>*/
	public static Map map=new HashMap();
	/**标签库的key<br>*/
	public static List key=new ArrayList();
	/**标签库的map<br>*/
	
	
	/**
	 * 构造一个无参的构造函数
	 */
	public tag() {
	}
	
	/**
	 * 得到标签库的key值
	 * @return List  返回标签库的key值
	 */
	public static List getKey() {
		return key;
	}

	/**
	 * 构造构造函数，得到模块类、初始化key,map
	 */
	public tag(PackageBean packageBean) {
		this.packageBean=packageBean;
		System.out.println("tag's packageBean set ok");
		getMapAndKey();
	}
	
	public tag(PackageBean packageBean, String type)
    {
		this.packageBean = packageBean;
        System.out.println("tag's packageBean set ok");
        getMapAndKey2();
    }
	
	/**
	 * 得到JSP路径
	 * @return String 返回JSP路径
	 */
	public String getJspPah() {
		String jspPath = BuildApp.getDir() + "/web/" + getModule();
		return jspPath;
	}

	public String getURL(){
        String URL = packageBean.getDatasource();
        return URL;
    }

    public String getUserName(){
        String username = packageBean.getUsername();
        return username;
    }

    public String getPassWord(){
        String password = packageBean.getPassword();
        return password;
    }
    
    public String getIsAll(){
    	String isall= packageBean.getIsall();
    	return isall;
    }

	/**
	 * 得到JAVA路径
	 * @return String 返回JAVA路径
	 */
	public String getJavaPah() {
		String path=getPackage();
		String path1[]=path.split("\\.");
		String path2="";
		String path3="";
		for(int i=0;i<path1.length;i++){
			path2+=path1[i]+"/";
		}
		
		if(path2.length()!=0){
		path3=path2.substring(0, path2.length()-1);
		}
		String javaPath = BuildApp.getDir() + "/src/" + path3;
		return javaPath;
	}

	/**
	 * 得到模块名称
	 * @return String 返回模块名称
	 */
	public String getModule() {
		String moduleName = packageBean.getModuleName();
		return moduleName;
	}

	/**
	 * 得到权限代码
	 * @return String 返回权限代码
	 */
	public String getResource() {
		String resource = packageBean.getResource();
		return resource;
	}

	/**
	 * 得到对象实例
	 * @return List 返回所有对象实例
	 */
	public List getClass2() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List className = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			String name = metaBean.getClassName();
			className.add(name);
		}
		return className;
	}

	
	/**
	 * 得到对象实例
	 * @return List 返回所有对象实例
	 */
	public List getClassInstance() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List classInstance = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			String instance = metaBean.getClassInstance();
			classInstance.add(instance);
		}
		return classInstance;
	}

	
	/**
	 * 得到包名
	 * @return String 返回包名
	 */
	public String getPackage() {
		String modulePackage = packageBean.getModulePackage();
		return modulePackage;
	}

	
	/**
	 * 得到包的目录名
	 * @return String 返回包的目录名
	 */
	public String getPackageDir() {
		String modulePackage = packageBean.getModulePackage();
		return modulePackage.replace('.', '/');
	}
	/**
	 * 得到配置文件中包名
	 * @return String 返回包名
	 */
	public String configPackage(){
		String config=getPackage();
		String newconfig=config.replaceAll("\\.", "/");
		return newconfig;
	}
	
	/**
	 * 得到类标题
	 * @return List 返回所有类标题
	 */
	public List getClassTitle() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List classTitle = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			String title = metaBean.getClassTitle();
			classTitle.add(title);
		}
		return classTitle;
	}
	
	/**
	 * 得到类注释
	 * @return List 返回所有类注释
	 */
	public List getClassDoc() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List classDoc = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			String doc = metaBean.getClassJavadoc();
			classDoc.add(doc);
		}
		return classDoc;
	}

	/**
	 * 得到类相关的表的主键名
	 * @return List 返回所有类相关的表的主键名
	 */
	public List getClassId() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List classId = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			String id = metaBean.getIdName();
			classId.add(id);
		}
		return classId;
	}

	/**
	 * 得到首字母大写的类相关的表的主键名
	 * @return List 返回所有首字母大写的类相关的表的主键名
	 */
	public List getClassIdIC() {
		List list = getClassId();
		Iterator iter = list.listIterator();
		List classIdIC = new ArrayList();
		while (iter.hasNext()) {
			String classid = (String) iter.next();
			String classID = stringToUpper(classid);
			classIdIC.add(classID);
		}
		return classIdIC;
	}

	/**
	 * 得到类相关的表的主键类型
	 * @return List 返回所有类相关的表的主键类型
	 */
	public List getClassIdType() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List classIdType = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			String idtype = metaBean.getIdType();
			classIdType.add(idtype);
		}
		return classIdType;
	}
	
	/**
	 * 得到类相关的表的主键描述
	 * @return List 返回所有类相关的表的主键描述
	 */
	public List getClassIdTitle() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List classIdTitle = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			String idtitle = metaBean.getIdTitle();
			classIdTitle.add(idtitle);
		}
		return classIdTitle;
	}

	/**
	 * 得到类相关的表的主键表字段名
	 * @return List 返回所有类相关的表的主键表字段名
	 */
	public List getClassIdColum() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List classIdColum = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			String idcolum = metaBean.getIdColumn().toUpperCase();
			//String idColum = stringToUpper(idcolum);
			classIdColum.add(idcolum);
		}
		return classIdColum;
	}
	
	/**
	 * 得到类相关的表的主键注释
	 * @return List 返回所有类相关的表的主键表字段名
	 */
	public List getClassIdDoc() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List classIdDoc = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			String idDoc = metaBean.getIdJavadoc();
			classIdDoc.add(idDoc);
		}
		return classIdDoc;
	}
	
	/**
	 * 得到参数标题
	 * @return Map 返回所有参数标题
	 */
	public Map getClassPropertyTitle() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		int i=0;
		Map classPropertyTitle=new HashMap();
		while (iter.hasNext()) {
			List propertytitle=new ArrayList();
			
				MetaBean metaBean = (MetaBean) iter.next();
				List list2 = metaBean.getPropertyBeans();
				Iterator iter2 = list2.iterator();
				while (iter2.hasNext()) {
						PropertyBean property = (PropertyBean) iter2.next();
						propertytitle.add(property.getPropertyTitle());
				}
				classPropertyTitle.put(Integer.toString(i), propertytitle);
				i++;
			
		}
		return classPropertyTitle;
	}
	
	/**
	 * 得到参数名称
	 * @return Map 返回所有参数名称
	 */
	public Map getClassPropertyName() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		int i=0;
		Map classPropertyName=new HashMap();
		while (iter.hasNext()) {
			List propertyName =new ArrayList();
				MetaBean metaBean = (MetaBean) iter.next();
				List list2 = metaBean.getPropertyBeans();
				Iterator iter2 = list2.iterator();
				while (iter2.hasNext()) {
						PropertyBean property = (PropertyBean) iter2.next();
						propertyName.add(property.getPropertyName());					
				}
				classPropertyName.put(Integer.toString(i),propertyName);
			i++;
		}
		return classPropertyName;
	}
	
	/**
	 * 得到参数类型
	 * @return Map 返回所有参数类型
	 */
	public Map getClassPropertyType() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		int i=0;
		Map classPropertyType=new HashMap();
		while (iter.hasNext()) {
			List propertyType=new ArrayList();
				MetaBean metaBean = (MetaBean) iter.next();
				List list2 = metaBean.getPropertyBeans();
				Iterator iter2 = list2.iterator();
				while (iter2.hasNext()) {
						PropertyBean property = (PropertyBean) iter2.next();
						propertyType.add(property.getPropertyType());
				}
				classPropertyType.put(Integer.toString(i), propertyType);
			i++;
		}
		return classPropertyType;
	}
	
	/**
	 * 得到参数的表字段名
	 * @return Map 返回所有参数的表字段名
	 */
	public Map getClassPropertyColum() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		int i=0;
		Map classPropertyColum=new HashMap();
		while (iter.hasNext()) {
			List propertycolum=new ArrayList();
				MetaBean metaBean = (MetaBean) iter.next();
				List list2 = metaBean.getPropertyBeans();
				Iterator iter2 = list2.iterator();
				while (iter2.hasNext()) {
						PropertyBean property = (PropertyBean) iter2.next();
						propertycolum.add(property.getPropertyColumn());
				}
				classPropertyColum.put(Integer.toString(i), propertycolum);
			i++;
		}
		return classPropertyColum;
	}
	
	/**
	 * 得到参数的表注释
	 * @return Map 返回所有参数的表注释
	 */
	public Map getClassPropertyDoc() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		int i=0;
		Map classPropertyDoc=new HashMap();
		while (iter.hasNext()) {
			List propertydoc=new ArrayList();
			
				MetaBean metaBean = (MetaBean) iter.next();
				List list2 = metaBean.getPropertyBeans();
				Iterator iter2 = list2.iterator();
				while (iter2.hasNext()) {
						PropertyBean property = (PropertyBean) iter2.next();
						propertydoc.add(property.getPropertyJavadoc());
				}
				classPropertyDoc.put(Integer.toString(i), propertydoc);
			i++;
		}
		return classPropertyDoc;
	}
	
	/**
	 * 得到首字母大写的参数名称
	 * @return Map 返回所有首字母大写的参数名称
	 */
	public Map getClassPropertyNameIC() {
		Map name=getClassPropertyName();
		Map classPropertyNameIC=new HashMap();
		int c=classCount();
	for(int i=0;i<c;i++){
		 List propertyNameICs=new ArrayList();
		List propertynames=(List) name.get(Integer.toString(i));
		Iterator iter=propertynames.iterator();
		while(iter.hasNext()){
			String propertyname=(String) iter.next();
          String propertyNameIC=stringToUpper(propertyname);
          propertyNameICs.add(propertyNameIC);
			}
		classPropertyNameIC.put(Integer.toString(i), propertyNameICs);
	}
		return classPropertyNameIC;
	}
	
	/**
	 * 得到类相关的表
	 * @return List 返回所有类相关的表
	 */
	public List getTableName(){
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List classTable = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			String table = metaBean.getClassTable();
			classTable.add(table);
		}
		return classTable;
	}

	/**
	 * 得到class的数量
	 * @return int 返回class的数量
	 */
	public static int classCount(){
		int count=0;
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			iter.next();
		count++;
		}
		return count;
	}
	
	public List propertyCounts() {
		int count = 0;
		List list = packageBean.getMetaBeans();
		List propertyCount=new ArrayList();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			List list2 = metaBean.getPropertyBeans();
			Iterator iter2 = list2.iterator();
			while (iter2.hasNext()) {
				iter2.next();
				count++;
			}
			propertyCount.add(String.valueOf(count));
			count=0;
		}
		return propertyCount;
	}
	
	public List propertyCounts2() {
		int count = 0;
		List list = packageBean.getMetaBeans();
		List propertyCount=new ArrayList();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			List list2 = metaBean.getPropertyBeans();
			Iterator iter2 = list2.iterator();
			while (iter2.hasNext()) {
				iter2.next();
				count++;
			}
			propertyCount.add(String.valueOf(count+1));
			count=0;
		}
		return propertyCount;
	}
	
	/*public int propertyALLCount() {
		int count = 0;
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			List list2 = metaBean.getPropertyBeans();
			Iterator iter2 = list2.iterator();
			while (iter2.hasNext()) {
				iter2.next();
				count++;
			}
		}
		return count;
	}*/
	
	/**
	 * 得到class的参数数组数量
	 * @return int[] 返回class的参数数组
	 */
	public static int[] propertyClassCount(){
		int i=0;
		int counts[]=new int[classCount()];
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			List list2 = metaBean.getPropertyBeans();
			counts[i]=list2.size();
			i++;
			
		}
		return counts;
	}

	/**
	 * 使字符串首字母大写
	 * @param str 输入字符串
	 * @return String 返回字符串首字母大写的字符串
	 */
	public String stringToUpper(String str) {
		//str=str.toLowerCase();
		String upper = str.substring(0, 1).toUpperCase()
				+ str.substring(1, str.length());
		return upper;
	}
	
	/*public List ForProperty(){
		List strs=new ArrayList();
		for(int i=0;i<classCount();i++){
			int count[]=propertyClassCount();
		//String str="for(int i=0;i<"+count[i]+";i++){" ;
		strs.add(str);
		}
	return strs;
	}*/
	
	/**
	 * 初始化key和map
	 */
	public void getMapAndKey(){
		map.put("getClass()", getClass2());
		map.put("getClassInstance()",getClassInstance());
		map.put("getClassTitle()",getClassTitle());
		map.put("getClassDoc()",getClassDoc());
		map.put("getClassId()", getClassId());
		map.put("getClassIdIC()",getClassIdIC());
		map.put("getClassIdType()", getClassIdType());
		map.put("getClassIdColum()", getClassIdColum());
		map.put("getClassIdTitle()", getClassIdTitle());
		
		map.put("getClassIdLength()", getClassIdLength());
		map.put("getClassIdPrecision()", getClassIdPrecision());
		map.put("getClassIdScale()", getClassIdScale());
		map.put("getClassIdNullable()", getClassIdNullable());
		
		map.put("getClassIdDoc()", getClassIdDoc());
		map.put("getTableName()",getTableName());
		map.put("FOR.CLASS","1");
		map.put("ENDFOR.CLASS","10" );
		map.put("FOR.CLASS.PROPERTY","2");
		map.put("ENDFOR.CLASS.PROPERTY","20");
		map.put("getClassPropertyTitle()",getClassPropertyTitle() );
		map.put("getClassPropertyName()", getClassPropertyName());
		map.put("getClassPropertyType()", getClassPropertyType());
		map.put("getClassPropertyColum()", getClassPropertyColum());
		map.put("getClassPropertyDoc()",getClassPropertyDoc() );
		map.put("getClassPropertyNameIC()", getClassPropertyNameIC());
		
		map.put("getClassPropertyLength()", getClassPropertyLength());
		map.put("getClassPropertyPrecision()", getClassPropertyPrecision());
		map.put("getClassPropertyScale()", getClassPropertyScale());
		map.put("getClassPropertyNullable()", getClassPropertyNullable());
		
		map.put("getJspPah()", getJspPah());
		map.put("getJavaPah()", getJavaPah());
		map.put("getModule()", getModule());
		map.put("getResource()", getResource());
		map.put("getPackage()", getPackage());
		map.put("getPackageDir()", getPackageDir());
		map.put("getRootPah()", BuildApp.getDir());
		map.put("configPackage()", configPackage());
		map.put("propertyCount()", propertyCounts());
		map.put("propertyCount()+1",propertyCounts2());
		
		map.put("propertyLengthXml()",propertyLengthXml());
		key.add("propertyLengthXml()");
		
		map.put("idLengthXml()",idLengthXml());
		key.add("idLengthXml()");
		
		
		key.add("propertyCount()+1");
		key.add("propertyCount()");
		key.add("getClassIdDoc()");
		key.add("configPackage()");
		key.add("getRootPah()");
		key.add("getJspPah()");
		key.add("getJavaPah()");
		key.add("getModule()");
		key.add("getResource()");
		key.add("getPackage()");
		key.add("getPackageDir()");
		key.add("getClass()");
		key.add("getClassInstance()");
		key.add("getClassTitle()");
		key.add("getClassDoc()");
		key.add("getClassId()");
		key.add("getClassIdIC()");
		key.add("getClassIdType()");
		key.add("getClassIdColum()");
		key.add("getClassIdTitle()");
		key.add("getTableName()");
		key.add("FOR.CLASS.PROPERTY");
		key.add("ENDFOR.CLASS.PROPERTY");
		key.add("FOR.CLASS");
		key.add("ENDFOR.CLASS");
		key.add("getClassPropertyTitle()");
		key.add("getClassPropertyName()");
		key.add("getClassPropertyType()");
		key.add("getClassPropertyColum()");
		key.add("getClassPropertyDoc()");
		key.add("getClassPropertyNameIC()");
		
		key.add("getClassIdLength()");
		key.add("getClassIdPrecision()");
		key.add("getClassIdScale()");
		key.add("getClassIdNullable()");
		
		key.add("getClassPropertyLength()");
		key.add("getClassPropertyPrecision()");
		key.add("getClassPropertyScale()");
		key.add("getClassPropertyNullable()");
		
	}

	private Object idLengthXml() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List classIdScale = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			
			String idScale = metaBean.getIdScale();
			String idPrecision = metaBean.getIdPrecision();
			String idLength = metaBean.getIdLength();
			String idNullable = metaBean.getIdNullable();
			
			String lengthXml="";
			
			if(null!=idLength&&idLength.length()>0)
				lengthXml+="length=\""+idLength+"\" ";
			
			if(null!=idPrecision&&idPrecision.length()>0)
				lengthXml+="precision=\""+idPrecision+"\" ";
			
			if(null!=idScale&&idScale.length()>0)
				lengthXml+="scale=\""+idScale+"\" ";
			
			if(null!=idNullable&&idNullable.equals("N"))
				lengthXml+="not-null=\"true\" ";
			
			classIdScale.add(lengthXml);
		}
		return classIdScale;
	}

	private Object propertyLengthXml() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		int i=0;
		Map classPropertyType=new HashMap();
		while (iter.hasNext()) {
			List propertyType=new ArrayList();
				MetaBean metaBean = (MetaBean) iter.next();
				List list2 = metaBean.getPropertyBeans();
				Iterator iter2 = list2.iterator();
				while (iter2.hasNext()) {
						PropertyBean property = (PropertyBean) iter2.next();
						
						String idScale = property.getPropertyScale();
						String idPrecision = property.getPropertyPrecision();
						String idLength = property.getPropertyLength();
						String idNullable = property.getPropertyNullable();
						
						String lengthXml="";
						
						if(null!=idLength&&idLength.length()>0)
							lengthXml+="length=\""+idLength+"\" ";
						
						if(null!=idPrecision&&idPrecision.length()>0)
							lengthXml+="precision=\""+idPrecision+"\" ";
						
						if(null!=idScale&&idScale.length()>0)
							lengthXml+="scale=\""+idScale+"\" ";
						
						if(null!=idNullable&&idNullable.equals("N"))
							lengthXml+="not-null=\"true\" ";
						propertyType.add(lengthXml);
				}
				classPropertyType.put(Integer.toString(i), propertyType);
			i++;
		}
		return classPropertyType;
	}

	private Object getClassPropertyNullable() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		int i=0;
		Map classPropertyType=new HashMap();
		while (iter.hasNext()) {
			List propertyType=new ArrayList();
				MetaBean metaBean = (MetaBean) iter.next();
				List list2 = metaBean.getPropertyBeans();
				Iterator iter2 = list2.iterator();
				while (iter2.hasNext()) {
						PropertyBean property = (PropertyBean) iter2.next();
						propertyType.add(property.getPropertyNullable());
				}
				classPropertyType.put(Integer.toString(i), propertyType);
			i++;
		}
		return classPropertyType;
	}

	private Object getClassPropertyScale() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		int i=0;
		Map classPropertyType=new HashMap();
		while (iter.hasNext()) {
			List propertyType=new ArrayList();
				MetaBean metaBean = (MetaBean) iter.next();
				List list2 = metaBean.getPropertyBeans();
				Iterator iter2 = list2.iterator();
				while (iter2.hasNext()) {
						PropertyBean property = (PropertyBean) iter2.next();
						propertyType.add(property.getPropertyScale());
				}
				classPropertyType.put(Integer.toString(i), propertyType);
			i++;
		}
		return classPropertyType;
	}

	private Object getClassPropertyLength() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		int i=0;
		Map classPropertyType=new HashMap();
		while (iter.hasNext()) {
			List propertyType=new ArrayList();
				MetaBean metaBean = (MetaBean) iter.next();
				List list2 = metaBean.getPropertyBeans();
				Iterator iter2 = list2.iterator();
				while (iter2.hasNext()) {
						PropertyBean property = (PropertyBean) iter2.next();
						propertyType.add(property.getPropertyLength());
				}
				classPropertyType.put(Integer.toString(i), propertyType);
			i++;
		}
		return classPropertyType;
	}

	private Object getClassPropertyPrecision() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		int i=0;
		Map classPropertyType=new HashMap();
		while (iter.hasNext()) {
			List propertyType=new ArrayList();
				MetaBean metaBean = (MetaBean) iter.next();
				List list2 = metaBean.getPropertyBeans();
				Iterator iter2 = list2.iterator();
				while (iter2.hasNext()) {
						PropertyBean property = (PropertyBean) iter2.next();
						propertyType.add(property.getPropertyPrecision());
				}
				classPropertyType.put(Integer.toString(i), propertyType);
			i++;
		}
		return classPropertyType;
	}

	private Object getClassIdScale() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List classIdScale = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			String idScale = metaBean.getIdScale();
			classIdScale.add(idScale);
		}
		return classIdScale;
	}

	private Object getClassIdNullable() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List classIdScale = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			String idScale = metaBean.getIdNullable();
			classIdScale.add(idScale);
		}
		return classIdScale;
	}

	private Object getClassIdLength() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List classIdScale = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			String idScale = metaBean.getIdLength();
			classIdScale.add(idScale);
		}
		return classIdScale;
	}

	private Object getClassIdPrecision() {
		List list = packageBean.getMetaBeans();
		Iterator iter = list.iterator();
		List classIdScale = new ArrayList();
		while (iter.hasNext()) {
			MetaBean metaBean = (MetaBean) iter.next();
			String idScale = metaBean.getIdPrecision();
			classIdScale.add(idScale);
		}
		return classIdScale;
	}

	public void getMapAndKey2(){
		if(!("1".equals(getIsAll()))){
        map.put("getClass()", getClass2());
        map.put("getClassInstance()", getClassInstance());
        map.put("getClassTitle()", getClassTitle());
        map.put("getClassDoc()", getClassDoc());
        map.put("getTableName()", getTableName());
        map.put("getURL()", getURL());
        map.put("getUserName()", getUserName());
        map.put("getPassWord()", getPassWord());
        map.put("getJspPah()", getJspPah());
        map.put("getJavaPah()", getJavaPah());
        map.put("getModule()", getModule());
        map.put("getResource()", getResource());
        map.put("getPackage()", getPackage());
        map.put("getRootPah()", BuildApp.getDir());
        map.put("configPackage()", configPackage());
		}else{
			getMapAndKey3();
		}
    }
	
	public void getMapAndKey3(){
		map.put("getIsAll()", getIsAll());
        map.put("getURL()", getURL());
        map.put("getUserName()", getUserName());
        map.put("getPassWord()", getPassWord());
        map.put("getJspPah()", getJspPah());
        map.put("getJavaPah()", getJavaPah());
        map.put("getModule()", getModule());
        map.put("getResource()", getResource());
        map.put("getPackage()", getPackage());
        map.put("getRootPah()", BuildApp.getDir());
        map.put("configPackage()", configPackage());
    }
	
}
