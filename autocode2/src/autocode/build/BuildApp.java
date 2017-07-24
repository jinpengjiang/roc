package autocode.build;

/**���������ļ���ģ��·��������Ӧ�ļ�<br>*/
public class BuildApp {
	/** PackageBean�����<br> */
	private static PackageBean packageBean;
	/** �����ļ�·��<br> */
	private static String dir;
	/**
	 * ����һ���޲εĹ��캯��
	 */
	public BuildApp() {
	}
	
	/**
	 * �õ������ļ�·��
	 */
	public static String getDir() {
		return dir;
	}

	/**
	 * ���������ļ�·�������������ļ�
	 * @param config �����ļ�·��
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
	 * ���������ļ�·�������������ļ�
	 * @param config �����ļ�·��
	 * @param type ��������
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
	 * ���������ļ�·����ģ���ļ�·�������ɴ����ļ�
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
	 * ���������ļ�·����ģ���ļ�·�����������ͣ����ɴ����ļ�
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
		//BuildApp.exe("E:/AutoCode from bll20061226/config.xml", "E:/AutoCode from bll20061226/ģ��/Add.jsp.txt");
		int count=0;
		ReadXML rx = new ReadXML();
		packageBean = rx.readXMLFile("E:/AutoCode from bll20061226/config.xml");
		new tag(packageBean);
	}*/

	/**
	 * ���������ļ�·��
	 */
	public void build(String dir) throws Exception {
		this.dir = dir;
	}
}
