package autocode.build;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * title： AutoCode<br>
 * dec:代码自动生成(Module)<br>
 * 
 * @author wanglijun
 * @version 1.0
 */
public class BuildTxt2 {
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
	/** 存放for.class值的临时列表<br> */
	private List list3 = new ArrayList();
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
	/** 纪录当前循环类型for.class/for.class.property<br> */
	private int x[][] = new int[100][100];
	/** 该CLASS中PROPERTY的个数<br> */
	private int property = 0;
	/** 该Module中CLASS的个数<br> */
	private int classtype = 0;
	/** 输出前存放信息的数组<br> */
	private String fortemp[][] = new String[200][Integer.MAX_VALUE / 500000];
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
	private int e = 0;
	/** 循环结束控制参数<br> */
	private int g = 0;
	/** 临时存放循环体内部信息<br> */
	private Map temp = new HashMap();
	/** 增加z数组内容时的数组第2维下标<br> */
	private int q = 0;
	/** x数组的第2维下标<br> */
	private int r = 0;
	/** 循环中每个并列循环的结束位置<br> */
	private int z[][] = new int[200][200];
	/** tag标签库的key<br> */
    private List tagKey=new ArrayList();
    /** tag标签库的value<br> */
	private Map tagMap=new HashMap();


	/**
	 * 构造一个无参的构造函数
	 */
	public BuildTxt2() {
		tagMap = tag.map;
		tagKey = tag.key;
	}
	
	/**
	 * 读模板文件内容，写入生成文件
	 * @param file 模板文件
	 * @param file2 生成文件
	 * @return boolean 值
	 */
	public boolean ReadFile(String file,String file2) {
		try {
			System.out.println("ReadFile(String file) start");
			outFileName=file2;
			inFileName=file;
			in = new RandomAccessFile(new File(file), "r");
			File ab = new File(outFileName);
			out = new PrintWriter(new FileOutputStream(ab, false));
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
			System.out.println("one file" + outFileName + " make success");
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
			moduleTagChange(File);
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
	 * 输入字符串(循环的起始标记)，处理读取过程中遇到的要求循环显示的问题
	 * 
	 * @param key1
	 *            输入字符串(FOR.CLASS.PROPERTY/FOR.CLASS)
	 */
	public void doFor(String key1){
		String forkey = key1;
		Map forvalue = new HashMap();
		forvalue = mkpair(start, forkey);
		if((allfor-1)!=0){
		seek[allfor - 2] = forvalue.size();
		}
		mapToString(forvalue);
	}
	
	
	/**
	 * 输入字符串(循环的起始标记)，处理读取过程中遇到的要求循环显示的问题
	 * @param list 含有字符串内容的list
	 * @param str 代替换的字符串
	 * @param b 外部循环结束标志
	 * @param type 循环类型(class/property)
	 * @param inFile 未替换前的完整字符串
	 * @param end 方法体内循环结束标志
	 * @return Map 返回b,list,inFile(外部循环结束标志、替换完成的内容，部份替换好的字符串)
	 */
	public Map forTarget(List list,String str,int b,String type,String inFile,int end){
		int count=0;
		List list4=new ArrayList();
		Map map=new HashMap();
		if(type.equals("class")){
		e = 1;
		count=classtype;
		}else{
		p=1;
		count=property;
		}
		if (count < end) {
			String change = (String) list
					.get(count);
			String changed = replace(inFile,
					str, change);
			inFile = changed;
		} else {
			List list5 = new ArrayList();
			if(type.equals("class")){
				for (int u = 0; u < list3.size(); u++) {
					list5.add(list3.get(u));
				}
				list3.clear();
				}else{
					for (int u = 0; u < list2.size(); u++) {
						list5.add(list2.get(u));
					}
					list2.clear();
				}
			list4 = list5;
			g = 1;
			if(type.equals("class")){
				e = 0;
				classtype=0;
				}else{
					p=0;
				property=0;
				}
			b = 0;
		}
		map.put("0", String.valueOf(b));
		map.put("1", list4);
		map.put("2", inFile);
		return map;
	}
	
	/**
	 * 根据模板文件内容按Class类型进行替换 Module类型：（按module（模块）的个数，分别产生多个相对应的文件）
	 * 
	 * @param inFile
	 *            模板文件内容
	 * @return boolean 值
	 */
	public boolean moduleTagChange(String inFile) throws IOException {
		List list4 = new ArrayList();
		String log = inFile;
		Map map = new HashMap();
		outFile = inFile;
		map=StringSplit(inFile);
		int i=Integer.parseInt((String) map.get("0"));
		int b=Integer.parseInt((String) map.get("1"));
		String str[]=(String[]) map.get("2");
		if (allfor==0) {
			if (i >= 3) {
				int j = 1;
				while (2 * j <= b) {
					Iterator iter = tagKey.iterator();
					while (iter.hasNext()) {
						String key1 = (String) iter.next();
						if (str[2 * j - 1].equals(key1)) {
							if (equals(tagMap.get(key1))) {
								List list = (List) tagMap.get(key1);
								String change = (String) list.get(type);
								String changed = replace(outFile,
										str[2 * j - 1], change);
								outFile = changed;
								break;
							} else if (equalsFor(tagMap.get(key1))) {
								String strfor = (String) tagMap.get(key1);
								if (strfor.equals("1")) {
									allfor++;
									getRType();
									x[allfor - 1][r] = 1;
									start = in.getFilePointer();
									doFor(key1);
									outtemp("1");
									x[allfor - 1][r] = 0;
									allfor--;
									return true;
								} else if ((strfor.equals("10"))
										|| (strfor.equals("20"))) {
									String changed = replace(outFile,
											str[2 * j - 1], "");
									outFile = changed;
									break;
								} else if (strfor.equals("2")) {
									allfor++;
									int propertyType=type;
									long propertystart2=0;
									long propertystart=start;
									while(type<tag.classCount()){
									start = in.getFilePointer();
									doFor(key1);
									outtemp("2");									
									type++;									
									propertystart2=start;
									start=propertystart;
									}
									allfor--;									
									start=propertystart2;
									type=propertyType;
									return true;
								} else {
									String changed = replace(outFile,
											str[2 * j - 1], strfor);
									outFile = changed;
									break;
								}
							}
						}
					}
					j++;
				}
				out.println(outFile);
			} else {
				out.println(outFile);
				return true;
			}
		} else {
			if (i >= 3) {
				int j = 1;
				while (2 * j <= b) {
					Iterator iter = tagKey.iterator();
					while (iter.hasNext()) {
						String key1 = (String) iter.next();
						if (str[2 * j - 1].equals(key1)) {
							if (equals(tagMap.get(key1))) {
								List list = (List) tagMap.get(key1);
								if (x[allfor - 1][r] == 2) {
									String change = (String) list.get(type);
									String changed = replace(inFile,
											str[2 * j - 1], change);
									inFile = changed;
								} else if (x[allfor - 1][r] == 1) {
									Map map2=new HashMap();
									map2=forTarget(list,str[2 * j - 1],b,"class",inFile,tag.classCount());
									b=Integer.parseInt(((String) map2.get("0")));
									list4=(List) map2.get("1");
									inFile=(String) map2.get("2");
									break;
								}
							} else if (equalsFor(tagMap.get(key1))) {
								String strfor = (String) tagMap.get(key1);
								if (strfor.equals("1")) {
									getType();
									temp.put("[" + (allfor - 1) + "][" + k
											+ "]", tagMap);
									allfor++;
									getRType();
									x[allfor - 1][r] = 1;
									doFor(key1);
									outtemp("1");
									x[allfor - 1][r] = 0;
									allfor--;
								} else if (strfor.equals("2")) {
									int propertyType=type;
									type=0;
									long propertystart2=0;
									long propertystart=start;
									getType();
									temp.put("[" + (allfor - 1) + "][" + k
											+ "]", tagMap);
									allfor++;
									while(type<tag.classCount()){	
									doFor(key1);
									outtemp("2");
									type++;
									propertystart2=start;
									start=propertystart;
									}
									z[allfor - 2][q-1]=z[allfor - 2][q];
									z[allfor - 2][q]=0;
									allfor--;
									start=propertystart2;
									type=propertyType;
								} else if ((strfor.equals("10"))
										|| (strfor.equals("20"))) {
									String changed = replace(inFile,
											str[2 * j - 1], "");
									inFile = changed;
									break;
								} else {
									String changed = replace(inFile,
											str[2 * j - 1], strfor);
									inFile = changed;
									break;
								}
								return true;
							} else {
								Map strmap = (Map) tagMap.get(key1);
								int[] count = tag.propertyClassCount();
								List list = (List) strmap.get(Integer
										.toString(type));
								Map map2=new HashMap();
								map2=forTarget(list,str[2 * j - 1],b,"property",inFile,count[type]);
								b=Integer.parseInt(((String) map2.get("0")));
								list4=(List) map2.get("1");
								inFile=(String) map2.get("2");
								break;
							}
						}
					}
					j++;
				}
				if (p != 0) {
					list2.add(inFile);
					property++;
					moduleTagChange(log);

				} else if (g != 0) {
					temp(list4);
					g = 0;
					return true;
				} else if (e != 0) {
					list3.add(inFile);
					classtype++;
					moduleTagChange(log);
				} else {
					temp(inFile);
				}
			} else {
				temp(inFile);
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
	public void findStart(long begin) {
		try {
			RandomAccessFile in2 = new RandomAccessFile(new File(inFileName),
					"r");
			in2.seek(begin);
			while ((inFile = in2.readLine()) != null) {
				byte[] byteStr = inFile.getBytes("iso-8859-1");
				String sr = new String(byteStr);
				String str[] = split(sr);
				int i = str.length;
				int b = 0;
				if (str[i - 1].equals("")) {
					b = i - 1;// 自己生成的尾直，要去掉
				} else {
					b = i;
				}
				if (i >= 3) {
					int k = 1;
					while (2 * k <= b) {
						if (str[2 * k - 1].equals("FOR.CLASS.PROPERTY")||str[2 * k - 1].equals("FOR.CLASS")) {
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
		findStart(begin);
		try {
			RandomAccessFile in2 = new RandomAccessFile(new File(inFileName),
					"r");
			in2.seek(begin);
			while ((inFile = in2.readLine()) != null) {
				byte[] byteStr = inFile.getBytes("iso-8859-1");
				String sr = new String(byteStr);
				String str[] = split(sr);
				int i = str.length;
				int b = 0;
				if (str[i - 1].equals("")) {
					b = i - 1;// 自己生成的尾直，要去掉
				} else {
					b = i;
				}
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
				moduleTagChange(inFile);
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
	 * 得到x数组的第2维下标
	 */
	public void getRType() {
		if (x[allfor - 1][0] == 0) {
			r = 0;
		}
		for (r = 0; r < 200; r++) {
			if (x[allfor - 1][r] != 0) {
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
	 * 把保存在temp中内容调用out()方法输出或存入fortemp中
	 * @param a 循环类型(1/2)
	 */
	public void outtemp(String a){
		String fortype = a;
		int count[] = tag.propertyClassCount();
		int propetrycount = count[type];
		int module = tag.classCount();
		out(fortype,module,propetrycount);
	}
	
	/**
	 * 把保存在temp中内容输出或存入fortemp中
	 * @param fortype 循环类型(1/2)
	 * @param module Class的数量
	 * @param propetrycount Class.property的数量
	 */
	public void out(String fortype,int module,int propertycount){
		int count=0;
		if(fortype.equals("1")){
			count=module;
		}else{
			count=propertycount;
		}
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
		}				int i = 0;
		int j = 0;
		int l = 0;
		int c = tempSize((allfor - 1));
		for (i = 0; i < count; i++) {
			for (j = 0; j < c; j++) {
				if (equals(temp
						.get("[" + (allfor - 1) + "][" + j + "]"))) {
					List list = (List) temp.get("[" + (allfor - 1)
							+ "][" + j + "]");
					if ((allfor - 1) != 0) {
					fortemp[allfor - 2][h] = (String) list.get(i);
					h++;
					}else{
						out.println(list.get(i));
					}
				} else if (equalsFor(temp.get("[" + (allfor - 1) + "]["
						+ j + "]"))) {
					if ((allfor - 1) != 0) {
					fortemp[allfor - 2][h] = (String) temp.get("["
							+ (allfor - 1) + "][" + j + "]");
					h++;
					}else{
						out.println(temp.get("[" + (allfor - 1) + "][" + j
								+ "]"));
					}
				} else if (equalsMap(temp.get("[" + (allfor - 1) + "]["
						+ j + "]"))) {
					if (printready()) {
						for (l = y[allfor - 1]; l < println.length; l++) {
							if ((allfor - 1) != 0) {
							fortemp[allfor - 2][h] = println[l];
							h++;
							}else{
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
	 * 输出前的准备，过滤fortemp中所有空项
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
	 * 输入字符串，分割字符串，同时得到循环开始、结束的判断条件
	 * 
	 * @param inFile
	 *            输入字符串
	 * @return Map 返回i,b,str[](起始，结束，循环内容)
	 */
	public Map StringSplit(String inFile){
		Map biList=new HashMap();
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
	 * 把输入字符串按进行拆分
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
				return st;
			}
		}
	}

	/**
	 * 把所有变量清零
	 */
	public void backAll() {
		for (int i = 0; i < 200; i++) {
			for (int j = 0; j < Integer.MAX_VALUE / 500000; j++) {
				fortemp[i][j] = null;

			}
		}
		for (int i = 0; i < 200; i++) {
			for (int j = 0; j < 200; j++) {
				z[i][j] = 0;
				
			}
		}
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				x[i][j] = 0;
				
			}
		}

		for (int i = 0; i < 200; i++) {

			y[i] = 0;
			seek[i] = 0;

		}
		temp.clear();
		list2.clear();
		list3.clear();
		k = 0;
		h = 0;
		f = 0;
		q = 0;
		r=0;
		starttype = 0;
		property = 0;
		classtype=0;
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

}
