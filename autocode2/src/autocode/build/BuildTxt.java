package autocode.build;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import autocode.utility.ApplicationConfig;

/**
 * title： AutoCode<br>
 * dec:代码自动生成<br>
 * 
 * @author wanglijun
 * @version 1.0
 */
public class BuildTxt {
	/** 输入值<br> */
	private String inFile;

	/** 输出值<br> */

	private String outFile;

	/** 模板文件路径<br> */

	private String inFileName;

	/** 生成文件路径<br> */

	private String outFileName;

	/** 输出流<br> */

	private PrintWriter out;

	/** 随机读取流<br> */

	private RandomAccessFile in;

	/** 存放class.property值的临时列表<br> */

	private List list2 = new ArrayList();

	/** 文件级别<br> */

	private String level;

	/** 生成文件路径集合<br> */

	private List dirs = new ArrayList();

	/** class的标签<br> */

	private int type = 0;

	/** 总的循环数<br> */

	private int allfor = 0;

	/** temp的第2维下标<br> */

	private int k = 0;

	/** fortemp的第2维下标<br> */

	private int h = 0;

	/** 得到z数组内容时的第2维下标<br> */

	private int f = 0;

	/** 循环中每个并列循环的开始位置<br> */

	private int y[] = new int[200];

	/** 该CLASS中PROPERTY的个数<br> */

	private int property = 0;

	/** 输出前存放信息的数组<br> */

	private String fortemp[][] = new String[200][Integer.MAX_VALUE / 30000];

	/** 过滤temp后得到所需要的信息<br> */

	private String println[];

	/** 循环开始位置<br> */

	private long start;

	/** 判断是否是下个循环开始位置<br> */

	private int starttype = 0;

	/** 最外层循环结束位置<br> */

	private String end = null;

	/** 内循环结束后的跳转位置<br> */

	private int seek[] = new int[200];

	/** 循环结束控制参数<br> */

	private int p = 0;

	/** 循环结束控制参数<br> */

	private int g = 0;

	/** 临时存放循环体内部信息<br> */

	private Map temp = new HashMap();

	/** path数组的下标<br> */

	private int t = 0;

	/** 增加z数组内容时的数组第2维下标<br> */

	private int q = 0;

	/** 循环中每个并列循环的结束位置<br> */

	private int z[][] = new int[200][200];

	/** 模板路径下所有的模板文件<br> */

	private String path[] = new String[Integer.MAX_VALUE / 30000];

	/** tag标签库的key<br> */

	private List tagKey = new ArrayList();

	/** tag标签库的value<br> */

	private Map tagMap = new HashMap();

	/**
	 * 读模板文件内容，写入生成文件
	 * 
	 * @param file
	 *            模板文件
	 * @return boolean 值
	 */
	public boolean ReadFile(String file) {
		try {
			System.out.println("ReadFile(String file) start");
			in = new RandomAccessFile(new File(file), "r");
			File ab = new File(outFileName);
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ab, false),ApplicationConfig.encoding)));
			int m = 0;
			while ((inFile = in.readLine()) != null) {
				byte[] byteStr = inFile.getBytes("iso-8859-1");
				String sr = new String(byteStr);
				WriteFile(sr);
				if (end != null) {
					in.seek(Long.parseLong(end));
					end = null;
					backAll();
				}
				m++;
			}
			out.flush();
			out.close();
			in.close();
			System.out.println("one file " + outFileName + " make success");
			backAll();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 根据模板文件内容，写入生成文件
	 * 
	 * @param File
	 *            模板文件内容
	 */
	public void WriteFile(String File) throws IOException {
		if (check(File)) {
			ClassTagChange(File);
		}
	}

	/**
	 * 根据模板文件内容，过滤注释和参数
	 * 
	 * @param inFile
	 *            模板文件内容
	 * @return boolean 值
	 */
	public boolean check(String inFile) {
		if (inFile.startsWith("//") || inFile.startsWith("#FILE")
				|| inFile.startsWith("#LEVEL")) {
			return false;
		} else if (inFile.length() == 0 || inFile.equals("")) {
			out.println(inFile);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 根据模板文件目录得到模板文件，并从中的得到其部份参数：Level(文件级别),File(文件存放路径)
	 * 
	 * @param file
	 *            模板文件目录
	 */
	public void getAttribute(String file) {
		String model;
		autoFindFiles(file);
		for (int i = 0; i < path.length; i++) {
			if (path[i] == null) {
				if (i == 0) {
					System.out.println("error model path");
				} else {
					break;
				}
			} else {
				model = path[i];
				System.out.println("model:" + model);
				inFileName = model;
				String inputLevel = getAttributeValue(model, "#LEVEL");
				level = inputLevel;
				System.out.println("level:" + level);
				String inputFilePath = getAttributeValue(model, "#FILE");
				tagFileChange(inputFilePath);
				mkdir();
			}
		}
	}

	/**
	 * 根据参数，得到该参数的值：Level(文件级别),File(文件存放路径)
	 * 
	 * @param model
	 *            模版文件名
	 * @param arrtibute
	 *            模板文件参数 (#Level/#File)
	 * @return String 返回参数值
	 */
	public String getAttributeValue(String model, String arrtibute) {
		String input = "";
		try {
			int i = arrtibute.length() + 1;
			BufferedReader in = new BufferedReader(new FileReader(model));
			while ((inFile = in.readLine()) != null) {
				if (inFile.startsWith(arrtibute)) {
					input = inFile.substring(i);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

	/**
	 * 根据模板文件的参数，分类别生成文件夹和文件（Class/Module类） 
	 * Class类型：（按class类的个数，分别产生多个相对应的文件）
	 * Module类型：（按module（模块）的个数，分别产生多个相对应的文件）
	 */
	public void mkdir() {
		List files = dirs;
		Iterator iter = files.iterator();
		while (iter.hasNext()) {
			outFileName = (String) iter.next();
			String dir = outFileName;
			String dir2 = dir.substring(0, dir.lastIndexOf("/"));
			File build = new File(dir2);
			if (!build.exists()) {
				build.mkdirs();
			}
			String newFile = dir.substring((dir.lastIndexOf("/") + 1), dir
					.length());
			File build2 = new File(dir2, newFile);
			if (!build2.exists()) {
				try {
					build2.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (level.equals("CLASS")) {
				ReadFile(inFileName);
				System.out
						.println("*********************************************************************");
				type++;
			} else {
				BuildTxt2 module = new BuildTxt2();
				module.ReadFile(inFileName, outFileName);
				System.out
						.println("*********************************************************************");
				break;
			}
		}
		type = 0;
		dirs.clear();
	}

	/**
	 * 对字符串进行分割，同时得到循环开始、结束的判断条件
	 * 
	 * @param inFile
	 *            输入字符串
	 * @return Map 返回i,b,str[](起始，结束，循环内容)
	 */
	public Map StringSplit(String inFile) {
		Map biList = new HashMap();
		String str[] = split(inFile);
		int i = str.length;
		int b = 0;
		if (str[i - 1].equals("")) {
			b = i - 1;// 自己生成的尾直，要去掉
		} else {
			b = i;
		}
		biList.put("0", String.valueOf(i));
		biList.put("1", String.valueOf(b));
		biList.put("2", str);
		return biList;
	}

	/**
	 * 输入字符串(循环的起始标记)，处理读取过程中遇到的要求循环显示的问题
	 * 
	 * @param key1
	 *            输入字符串(FOR.CLASS.PROPERTY/FOR.CLASS)
	 */
	public void doFor(String key1) {
		try {
			if (allfor != 0) {
				getType();
				temp.put("[" + (allfor - 1) + "][" + k + "]", tagMap);
				k++;
			} else {
				start = in.getFilePointer();
			}
			allfor++;
			String forkey = key1;
			Map forvalue = new HashMap();
			forvalue = mkpair(start, forkey);
			if (allfor - 1 != 0) {
				seek[allfor - 2] = forvalue.size();
			}
			mapToString(forvalue);
			outtemp();
			allfor--;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据循环结束条件、字符串数组、List，进行标签替换
	 * 
	 * @param b
	 *            循环结束判断条件
	 * @param str
	 *            字符串被拆分后的数组
	 * @param maplist
	 *            存放property值
	 * @return List 返回property值
	 */
	public List tagChange(int b, String[] str, List maplist) {
		int j = 1;
		while (2 * j <= b) {
			Iterator iter = tagKey.iterator();
			while (iter.hasNext()) {
				String key1 = (String) iter.next();
				if (str[2 * j - 1].equals(key1)) {
					if (equals(tagMap.get(key1))) {
						List list = (List) tagMap.get(key1);
						String change = (String) list.get(type);
						String changed = replace(outFile, str[2 * j - 1],
								change);
						outFile = changed;
						break;
					} else if (equalsFor(tagMap.get(key1))) {
						String strfor = (String) tagMap.get(key1);
						if (strfor.equals("1")) {
							doFor(key1);
							return null;
						} else if ((strfor.equals("10"))
								|| (strfor.equals("20"))) {
							String changed = replace(outFile, str[2 * j - 1],
									"");
							outFile = changed;
							break;
						} else if (strfor.equals("2")) {
							doFor(key1);
							return null;
						} else {
							String changed = replace(outFile, str[2 * j - 1],
									strfor);
							outFile = changed;
							break;
						}
					} else {
						Map strmap = (Map) tagMap.get(key1);
						int[] count = tag.propertyClassCount();
						p = 1;
						if (property < count[type]) {
							List list = (List) strmap.get(Integer
									.toString(type));
							String change = (String) list.get(property);
							String changed = replace(outFile, str[2 * j - 1],
									change);
							outFile = changed;
							break;
						} else {
							List list3 = new ArrayList();
							for (int u = 0; u < list2.size(); u++) {
								list3.add(list2.get(u));
							}
							list2.clear();
							maplist = list3;
							p = 0;
							g = 1;
							property = 0;
							b = 0;
							break;
						}
					}
				}
			}
			j++;
		}
		return maplist;
	}

	/**
	 * 根据模板文件中File参数的内容，进行替换，得到文件存取路径
	 * 
	 * @param inFile
	 *            模板文件中File参数的内容
	 */
	public void tagFileChange(String inFile) {
		List list4 = new ArrayList();
		Map map = new HashMap();
		map = StringSplit(inFile);
		int i = Integer.parseInt((String) map.get("0"));
		int b = Integer.parseInt((String) map.get("1"));
		String str[] = (String[]) map.get("2");
		if (i >= 3) {
			int a = 0;
			while (a < tag.classCount()) {
				outFile = inFile;
				type = a;
				tagChange(b, str, list4);
				dirs.add(outFile);
				a++;
			}
		} else {
			dirs.add(outFile);
		}
		type = 0;
	}

	/**
	 * 根据模板文件内容按Class类型进行替换 Class类型：（按class类的个数，分别产生多个相对应的文件）
	 * 
	 * @param inFile
	 *            模板文件内容
	 * @return boolean 值
	 */
	public boolean ClassTagChange(String inFile) throws IOException {
		List list4 = new ArrayList();
		String log = inFile;
		Map map = new HashMap();
		map = StringSplit(inFile);
		int i = Integer.parseInt((String) map.get("0"));
		int b = Integer.parseInt((String) map.get("1"));
		String str[] = (String[]) map.get("2");
		outFile = inFile;
		if (allfor == 0) {
			if (i >= 3) {
				List a=tagChange(b, str, list4);
				if(a!=null){
				out.println(outFile);
				}
			} else {
				out.println(outFile);
				return true;
			}
		} else {
			if (i >= 3) {
				list4 = tagChange(b, str, list4);
				if (p != 0) {
					list2.add(outFile);
					property++;
					ClassTagChange(log);
				} else if (g != 0) {
					temp(list4);
					g = 0;
					return true;
				} else {
					temp(outFile);
				}
			} else {
				temp(outFile);
				return true;
			}
		}
		return false;
	}

	/**
	 * 从模板文件中的起始位置，对模板文件进行遍厉，得到下一个目标的开始位置
	 * 
	 * @param begin
	 *            模板文件中的起始位置
	 * @param target
	 *            所要查找的目标名
	 */
	public void findStart(long begin, String target) {
		try {
			RandomAccessFile in2 = new RandomAccessFile(new File(inFileName),
					"r");
			in2.seek(begin);
			while ((inFile = in2.readLine()) != null) {
				byte[] byteStr = inFile.getBytes("iso-8859-1");
				String sr = new String(byteStr);
				Map map = new HashMap();
				map = StringSplit(sr);
				int i = Integer.parseInt((String) map.get("0"));
				int b = Integer.parseInt((String) map.get("1"));
				String str[] = (String[]) map.get("2");
				if (i >= 3) {
					int k = 1;
					while (2 * k <= b) {
						if (str[2 * k - 1].equals(target)) {
							if (starttype == 0) {
								start = in2.getFilePointer();
								starttype++;
							}
							break;
						}
						k++;
					}
				}
				if (starttype != 0) {
					break;
				}
			}
			in2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从模板文件中的起始位置，对模板文件进行遍厉，得到该循环起止之间的所有内容
	 * 
	 * @param begin
	 *            模板文件中的起始位置
	 * @param forkey
	 *            所要查找的目标名
	 * @return Map 该循环起止之间的所有内容
	 */
	public Map mkpair(long begin, String forkey) {
		int p = 0;
		Map map = new HashMap();
		String pointer = null;
		String tempPointer = null;
		String target = null;
		String target2 = null;
		if (forkey.equals("FOR.CLASS.PROPERTY")) {
			target = "ENDFOR.CLASS.PROPERTY";
			target2 = "FOR.CLASS.PROPERTY";
		} else {
			if (forkey.equals("FOR.CLASS")) {
				target = "ENDFOR.CLASS";
				target2 = "FOR.CLASS";
			}
		}
		findStart(begin, target2);
		try {
			RandomAccessFile in2 = new RandomAccessFile(new File(inFileName),
					"r");
			in2.seek(begin);
			while ((inFile = in2.readLine()) != null) {
				byte[] byteStr = inFile.getBytes("iso-8859-1");
				String sr = new String(byteStr);
				Map map2 = new HashMap();
				map2 = StringSplit(sr);
				int i = Integer.parseInt((String) map2.get("0"));
				int b = Integer.parseInt((String) map2.get("1"));
				String str[] = (String[]) map2.get("2");
				if (i >= 3) {
					int k = 1;
					while (2 * k <= b) {
						if (str[2 * k - 1].equals(target2)) {
							p++;
							break;
						} else {
							if (str[2 * k - 1].equals(target)) {
								if (p == 0) {
									pointer = String.valueOf(in2
											.getFilePointer());
								} else {
									p--;
								}
								break;
							}
						}
						tempPointer = String.valueOf(in2.getFilePointer());
						k++;
					}
				} else {
					tempPointer = String.valueOf(in2.getFilePointer());
				}
				if (pointer != null) {
					if (allfor == 1) {
						end = tempPointer;
					}
					break;
				}
			}
			starttype = 0;
			if (pointer != null) {
				in2.seek(begin);
				int j = 0;
				while ((inFile = in2.readLine()) != null) {
					if (!(String.valueOf(in2.getFilePointer()).equals(pointer))) {
						byte[] byteStr = inFile.getBytes("iso-8859-1");
						String sr = new String(byteStr);
						map.put(Integer.toString(j), sr);
						j++;
					} else {
						in2.seek(in2.length());
					}
				}
			}
			in2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 根据map,遍历map中的所有value，对其进行替换
	 * 
	 * @param Map
	 *            输入含有循环内容的map
	 */
	public void mapToString(Map map) {
		Map map2 = map;
		for (int i = 0; i < map.size(); i++) {
			inFile = (String) map2.get(Integer.toString(i));
			try {
				ClassTagChange(inFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (seek[allfor - 1] != 0) {
				i = i + seek[allfor - 1];
				seek[allfor - 1] = 0;
			}
		}
	}

	/**
	 * 得到temp的第2维下标
	 */
	public void getType() {
		if (temp.get("[" + (allfor - 1) + "][0]") == null) {
			k = 0;
		}
		for (k = 0; k < Integer.MAX_VALUE / 30000; k++) {
			if (temp.get("[" + (allfor - 1) + "][" + k + "]") != null) {
			} else {
				break;
			}
		}
	}

	/**
	 * 把输入的对象，保存在temp中
	 * 
	 * @param in
	 *            要输出的对象
	 */
	public void temp(Object in) {
		getType();
		temp.put("[" + (allfor - 1) + "][" + k + "]", in);
	}

	/**
	 * 把保存在temp中内容输出或存入fortemp中
	 * @param propetrycount class.property的数量
	 */
	public void out(int propetrycount) {
		if ((allfor - 1) != 0) {
			if (fortemp[allfor - 2][0] == null) {
				h = 0;
			} else {
				for (h = 0; h < Integer.MAX_VALUE / 30000; h++) {
					if (fortemp[allfor - 2][h] != null) {
					} else {
						break;
					}
				}
			}
		}
		int i = 0;
		int j = 0;
		int l = 0;
		int c = tempSize((allfor - 1));
		for (i = 0; i < propetrycount; i++) {
			for (j = 0; j < c; j++) {
				if (equals(temp.get("[" + (allfor - 1) + "][" + j + "]"))) {
					List list = (List) temp.get("[" + (allfor - 1) + "][" + j
							+ "]");
					if ((allfor - 1) != 0) {
						fortemp[allfor - 2][h] = (String) list.get(i);
						h++;
					} else {
						out.println(list.get(i));
					}
				} else if (equalsFor(temp.get("[" + (allfor - 1) + "][" + j
						+ "]"))) {
					if ((allfor - 1) != 0) {
						fortemp[allfor - 2][h] = (String) temp.get("["
								+ (allfor - 1) + "][" + j + "]");
						h++;
					} else {
						out.println(temp.get("[" + (allfor - 1) + "][" + j
								+ "]"));
					}
				} else if (equalsMap(temp.get("[" + (allfor - 1) + "][" + j
						+ "]"))) {
					if (printready()) {
						for (l = y[allfor - 1]; l < println.length; l++) {
							if ((allfor - 1) != 0) {
								fortemp[allfor - 2][h] = println[l];
								h++;
							} else {
								out.println(println[l]);
							}
						}
					}
					f++;
					y[allfor - 1] = l;
				}
			}
			f = 0;
			y[allfor - 1] = 0;
		}
		if ((allfor - 1) != 0) {
			if (z[allfor - 2][0] == 0) {
				q = 0;
			} else {
				for (q = 0; q < 200; q++) {
					if (z[allfor - 2][q] != 0) {
					} else {
						break;
					}
				}
			}
			z[allfor - 2][q] = h;
		}
		for (int k = 0; k < c; k++) {
			temp.remove("[" + (allfor - 1) + "][" + k + "]");
		}

	}

	/**
	 * 把保存在temp中内容调用out()方法输出或存入fortemp中
	 */
	public void outtemp() {
		int count[] = tag.propertyClassCount();
		int propetrycount = count[type];
		out(propetrycount);
	}

	/**
	 * 过滤fortemp中所有空项
	 */
	public boolean printready() {
		int i = 0;
		int k = 0;
		i = y[allfor - 1];
		k = z[allfor - 1][f];
		int count = k;
		while (i < count) {
			if (fortemp[allfor - 1][i] == null) {
				break;
			} else {
				i++;
			}
		}
		println = new String[i];
		i = y[allfor - 1];
		while (i < count) {
			if (fortemp[allfor - 1][i] == null) {
				return false;
			} else {
				println[i] = fortemp[allfor - 1][i];
				i++;
			}
		}

		return true;
	}

	/**
	 * 输入temp第1维的值，返回该维所含有的所有数据总数
	 * 
	 * @param a
	 *            temp第1维的值
	 * @return int 返回该维所含有的所有数据总数
	 */
	public int tempSize(int a) {
		int i = 0;
		for (i = 0; i < Integer.MAX_VALUE / 30000; i++) {
			if (temp.get("[" + a + "]" + "[" + i + "]") != null) {
			} else {
				break;
			}
		}
		return i;
	}

	/**
	 * 构造一个无参的构造函数
	 */
	public BuildTxt() {
		tagMap = tag.map;
		tagKey = tag.key;
	}

	/**
	 * 根据模板文件目录产生文件
	 * 
	 * @param model
	 *            模板文件目录
	 */
	public static void exe(String model) {
		BuildTxt build = new BuildTxt();
		build.getAttribute(model);
		System.out.println("build success");
	}

	/*
	 * public static void main(String args[]) { BuildTxt build = new BuildTxt();
	 * build.getAttribute("E:/AutoCode from bll20061226/模型/Add.jsp.txt");
	 * build.mkdir(); // build.ReadFile(build.inFileName); }
	 */

	/**
	 * 把输入字符串按要求进行替换
	 * 
	 * @param input
	 *            输入的字符串
	 * @param olds
	 *            要替换的字符串
	 * @param news
	 *            替换后的字符串
	 * @return String 返回替换完成之后的字符串
	 */
	public String replace(String input, String olds, String news) {
		String afterRE = "";
		String str[] = split(input);
		int i = str.length;
		if (i < 3) {
			return input;
		} else {
			afterRE += str[0];
			for (int j = 1; j < i; j++) {
				if ((j == i - 1) && (str[j].equals(""))) {
					str[j] = "$";
					afterRE += str[j];
				} else {
					if (str[j].equals(olds)) {
						str[j] = news;
						afterRE += str[j] + str[j + 1];
						j = j + 1;
					} else if (str[j].equals("")) {
						str[j] = "";
						afterRE += str[j];
					} else {
						str[j] = "$" + str[j];
						afterRE += str[j];
					}
				}
			}
		}
		return afterRE;
	}

	/**
	 * 把输入字符串进行拆分
	 * 
	 * @param in
	 *            输入的字符串
	 * @return String[] 返回拆分后的字符串数组
	 */
	public String[] split(String in) {
		if (in.equals("")) {
			String st[] = new String[1];
			st[0] = "";
			return st;
		} else {
			if ((in.substring(in.length() - 1, in.length())).equals("$")) {
				in += "1";
				String st[] = in.split("\\$");
				st[st.length - 1] = "";
				return st;
			} else {
				String st[] = in.split("\\$");
				for (int i = 0; i < st.length; i++) {
				}
				return st;
			}
		}
	}

	/**
	 * 把所有变量清零
	 */
	public void backAll() {
		for (int i = 0; i < 200; i++) {
			for (int j = 0; j < Integer.MAX_VALUE / 30000; j++) {
				fortemp[i][j] = null;
			}
		}
		for (int i = 0; i < 200; i++) {
			for (int j = 0; j < 200; j++) {
				z[i][j] = 0;
			}
		}
		for (int i = 0; i < 200; i++) {
			y[i] = 0;
			seek[i] = 0;
		}
		temp.clear();
		list2.clear();
		k = 0;
		h = 0;
		t = 0;
		f = 0;
		q = 0;
		starttype = 0;
		property = 0;
	}

	/**
	 * 判断输入对象是否是整型
	 * 
	 * @param other
	 *            输入要判断的对象
	 * @return boolean 值
	 */
	public boolean equalsFor(Object other) {
		if (other instanceof String) {
			return true;
		}
		return false;
	}

	/**
	 * 判断输入对象是否是List型
	 * 
	 * @param other
	 *            输入要判断的对象
	 * @return boolean 值
	 */
	public boolean equals(Object other) {
		if (other instanceof List) {
			return true;
		}
		return false;
	}

	/**
	 * 判断输入对象是否是Map类型
	 * 
	 * @param other
	 *            输入要判断的对象
	 * @return boolean 值
	 */
	public boolean equalsMap(Object other) {
		if (other instanceof Map) {
			return true;
		}
		return false;
	}

	/**
	 * 根据输入文件路径，自动得到该路径下的所有文件
	 * 
	 * @param address
	 *            输入路径
	 */
	public void autoFindFiles(String address) {
		File file = new File(address);
		listFile(file);
		t = 0;
	}

	/**
	 * 根据输入文件类型路径，自动得到该路径下的所有文件
	 * 
	 * @param file
	 *            输入路径
	 */
	public void listFile(File file) {
		if (file.isFile()) {
			path[t] = file.getAbsolutePath();
			t++;
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				listFile(files[i]);
			}
		}

	}

}
