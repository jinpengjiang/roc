package autocode.build;

/**根据配置文件和模版路径生成相应文件<br>*/
public class BuildApp {
	/** PackageBean类对象<br> */
	private static PackageBean packageBean;
	/** 生成文件路径<br> */
	private static String dir;
	/**
	 * 构造一个无参的构造函数
	 */
	public BuildApp() {
	}
	
	/**
	 * 得到生成文件路径
	 */
	public static String getDir() {
		return dir;
	}

	/**
	 * 输入配置文件路径，解析配置文件
	 * @param config 配置文件路径
	 */
	public BuildApp(String config) throws Exception {
		//build(System.getProperty("user.dir"));
		build("d:/test/AutoCode");
		ReadXML rx = new ReadXML();
		System.out.println("readXML start");
		packageBean = rx.readXMLFile(config);
		System.out.println("readXMLFile(config) success");
		new tag(packageBean);
		System.out.println("dir  "+dir);
	}

	/**
	 * 输入配置文件路径，解析配置文件
	 * @param config 配置文件路径
	 * @param type 生成类型
	 */
	public BuildApp(String config, String type)
        throws Exception
    {
        //build(System.getProperty("user.dir"));
		build("d:/test/AutoCode");
        ReadXML rx = new ReadXML();
        System.out.println("readXML start2");
        packageBean = rx.readXMLFile2(config);
        System.out.println("readXMLFile(config) success");
        new tag(packageBean, type);
        System.out.println("dir  " + dir);
    }
	
	/**
	 * 输入配置文件路经和模版文件路径，生成代码文件
	 */
	public static void exe(String config,String model){
		try {
			BuildApp buildApp1 = new BuildApp(config);
			BuildTxt.exe(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
       
	}
	
	/**
	 * 输入配置文件路经、模版文件路径和生成类型，生成代码文件
	 */
	public static void exe(String config, String model, String type)
    {
        try
        {
            if(type == null)
            {
                exe(config, model);
            } else
            {
                BuildApp buildApp1 = new BuildApp(config, type);
                BuildXML.exe();
                if(type.equals("0"))
                {
                    System.out.println("BuildApp.getDir()=:" + getDir());
					buildApp1 = new BuildApp(getDir() + "/" + tag.map.get("getModule()") + ".xml");
                    //buildApp1 = new BuildApp(getDir() + "/src/" + tag.map.get("getModule()") + ".xml");
                    BuildTxt.exe(model);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

	

/*	public static void main(String[] args) throws Exception {
		//BuildApp.exe("E:/AutoCode from bll20061226/config.xml", "E:/AutoCode from bll20061226/模型/Add.jsp.txt");
		int count=0;
		ReadXML rx = new ReadXML();
		packageBean = rx.readXMLFile("E:/AutoCode from bll20061226/config.xml");
		new tag(packageBean);
	}*/

	/**
	 * 产生生成文件路径
	 */
	public void build(String dir) throws Exception {
		this.dir = dir;
	}
}
