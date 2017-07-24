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
 * title�� AutoCode<br>
 * dec:�����Զ�����<br>
 * 
 * @author wanglijun
 * @version 1.0
 */
public class BuildTxt {
	/** ����ֵ<br> */
	private String inFile;

	/** ���ֵ<br> */

	private String outFile;

	/** ģ���ļ�·��<br> */

	private String inFileName;

	/** �����ļ�·��<br> */

	private String outFileName;

	/** �����<br> */

	private PrintWriter out;

	/** �����ȡ��<br> */

	private RandomAccessFile in;

	/** ���class.propertyֵ����ʱ�б�<br> */

	private List list2 = new ArrayList();

	/** �ļ�����<br> */

	private String level;

	/** �����ļ�·������<br> */

	private List dirs = new ArrayList();

	/** class�ı�ǩ<br> */

	private int type = 0;

	/** �ܵ�ѭ����<br> */

	private int allfor = 0;

	/** temp�ĵ�2ά�±�<br> */

	private int k = 0;

	/** fortemp�ĵ�2ά�±�<br> */

	private int h = 0;

	/** �õ�z��������ʱ�ĵ�2ά�±�<br> */

	private int f = 0;

	/** ѭ����ÿ������ѭ���Ŀ�ʼλ��<br> */

	private int y[] = new int[200];

	/** ��CLASS��PROPERTY�ĸ���<br> */

	private int property = 0;

	/** ���ǰ�����Ϣ������<br> */

	private String fortemp[][] = new String[200][Integer.MAX_VALUE / 30000];

	/** ����temp��õ�����Ҫ����Ϣ<br> */

	private String println[];

	/** ѭ����ʼλ��<br> */

	private long start;

	/** �ж��Ƿ����¸�ѭ����ʼλ��<br> */

	private int starttype = 0;

	/** �����ѭ������λ��<br> */

	private String end = null;

	/** ��ѭ�����������תλ��<br> */

	private int seek[] = new int[200];

	/** ѭ���������Ʋ���<br> */

	private int p = 0;

	/** ѭ���������Ʋ���<br> */

	private int g = 0;

	/** ��ʱ���ѭ�����ڲ���Ϣ<br> */

	private Map temp = new HashMap();

	/** path������±�<br> */

	private int t = 0;

	/** ����z��������ʱ�������2ά�±�<br> */

	private int q = 0;

	/** ѭ����ÿ������ѭ���Ľ���λ��<br> */

	private int z[][] = new int[200][200];

	/** ģ��·�������е�ģ���ļ�<br> */

	private String path[] = new String[Integer.MAX_VALUE / 30000];

	/** tag��ǩ���key<br> */

	private List tagKey = new ArrayList();

	/** tag��ǩ���value<br> */

	private Map tagMap = new HashMap();

	/**
	 * ��ģ���ļ����ݣ�д�������ļ�
	 * 
	 * @param file
	 *            ģ���ļ�
	 * @return boolean ֵ
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
	 * ����ģ���ļ����ݣ�д�������ļ�
	 * 
	 * @param File
	 *            ģ���ļ�����
	 */
	public void WriteFile(String File) throws IOException {
		if (check(File)) {
			ClassTagChange(File);
		}
	}

	/**
	 * ����ģ���ļ����ݣ�����ע�ͺͲ���
	 * 
	 * @param inFile
	 *            ģ���ļ�����
	 * @return boolean ֵ
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
	 * ����ģ���ļ�Ŀ¼�õ�ģ���ļ��������еĵõ��䲿�ݲ�����Level(�ļ�����),File(�ļ����·��)
	 * 
	 * @param file
	 *            ģ���ļ�Ŀ¼
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
	 * ���ݲ������õ��ò�����ֵ��Level(�ļ�����),File(�ļ����·��)
	 * 
	 * @param model
	 *            ģ���ļ���
	 * @param arrtibute
	 *            ģ���ļ����� (#Level/#File)
	 * @return String ���ز���ֵ
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
	 * ����ģ���ļ��Ĳ���������������ļ��к��ļ���Class/Module�ࣩ 
	 * Class���ͣ�����class��ĸ������ֱ����������Ӧ���ļ���
	 * Module���ͣ�����module��ģ�飩�ĸ������ֱ����������Ӧ���ļ���
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
	 * ���ַ������зָͬʱ�õ�ѭ����ʼ���������ж�����
	 * 
	 * @param inFile
	 *            �����ַ���
	 * @return Map ����i,b,str[](��ʼ��������ѭ������)
	 */
	public Map StringSplit(String inFile) {
		Map biList = new HashMap();
		String str[] = split(inFile);
		int i = str.length;
		int b = 0;
		if (str[i - 1].equals("")) {
			b = i - 1;// �Լ����ɵ�βֱ��Ҫȥ��
		} else {
			b = i;
		}
		biList.put("0", String.valueOf(i));
		biList.put("1", String.valueOf(b));
		biList.put("2", str);
		return biList;
	}

	/**
	 * �����ַ���(ѭ������ʼ���)�������ȡ������������Ҫ��ѭ����ʾ������
	 * 
	 * @param key1
	 *            �����ַ���(FOR.CLASS.PROPERTY/FOR.CLASS)
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
	 * ����ѭ�������������ַ������顢List�����б�ǩ�滻
	 * 
	 * @param b
	 *            ѭ�������ж�����
	 * @param str
	 *            �ַ�������ֺ������
	 * @param maplist
	 *            ���propertyֵ
	 * @return List ����propertyֵ
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
	 * ����ģ���ļ���File���������ݣ������滻���õ��ļ���ȡ·��
	 * 
	 * @param inFile
	 *            ģ���ļ���File����������
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
	 * ����ģ���ļ����ݰ�Class���ͽ����滻 Class���ͣ�����class��ĸ������ֱ����������Ӧ���ļ���
	 * 
	 * @param inFile
	 *            ģ���ļ�����
	 * @return boolean ֵ
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
	 * ��ģ���ļ��е���ʼλ�ã���ģ���ļ����б������õ���һ��Ŀ��Ŀ�ʼλ��
	 * 
	 * @param begin
	 *            ģ���ļ��е���ʼλ��
	 * @param target
	 *            ��Ҫ���ҵ�Ŀ����
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
	 * ��ģ���ļ��е���ʼλ�ã���ģ���ļ����б������õ���ѭ����ֹ֮�����������
	 * 
	 * @param begin
	 *            ģ���ļ��е���ʼλ��
	 * @param forkey
	 *            ��Ҫ���ҵ�Ŀ����
	 * @return Map ��ѭ����ֹ֮�����������
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
	 * ����map,����map�е�����value����������滻
	 * 
	 * @param Map
	 *            ���뺬��ѭ�����ݵ�map
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
	 * �õ�temp�ĵ�2ά�±�
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
	 * ������Ķ��󣬱�����temp��
	 * 
	 * @param in
	 *            Ҫ����Ķ���
	 */
	public void temp(Object in) {
		getType();
		temp.put("[" + (allfor - 1) + "][" + k + "]", in);
	}

	/**
	 * �ѱ�����temp��������������fortemp��
	 * @param propetrycount class.property������
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
	 * �ѱ�����temp�����ݵ���out()������������fortemp��
	 */
	public void outtemp() {
		int count[] = tag.propertyClassCount();
		int propetrycount = count[type];
		out(propetrycount);
	}

	/**
	 * ����fortemp�����п���
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
	 * ����temp��1ά��ֵ�����ظ�ά�����е�������������
	 * 
	 * @param a
	 *            temp��1ά��ֵ
	 * @return int ���ظ�ά�����е�������������
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
	 * ����һ���޲εĹ��캯��
	 */
	public BuildTxt() {
		tagMap = tag.map;
		tagKey = tag.key;
	}

	/**
	 * ����ģ���ļ�Ŀ¼�����ļ�
	 * 
	 * @param model
	 *            ģ���ļ�Ŀ¼
	 */
	public static void exe(String model) {
		BuildTxt build = new BuildTxt();
		build.getAttribute(model);
		System.out.println("build success");
	}

	/*
	 * public static void main(String args[]) { BuildTxt build = new BuildTxt();
	 * build.getAttribute("E:/AutoCode from bll20061226/ģ��/Add.jsp.txt");
	 * build.mkdir(); // build.ReadFile(build.inFileName); }
	 */

	/**
	 * �������ַ�����Ҫ������滻
	 * 
	 * @param input
	 *            ������ַ���
	 * @param olds
	 *            Ҫ�滻���ַ���
	 * @param news
	 *            �滻����ַ���
	 * @return String �����滻���֮����ַ���
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
	 * �������ַ������в��
	 * 
	 * @param in
	 *            ������ַ���
	 * @return String[] ���ز�ֺ���ַ�������
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
	 * �����б�������
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
	 * �ж���������Ƿ�������
	 * 
	 * @param other
	 *            ����Ҫ�жϵĶ���
	 * @return boolean ֵ
	 */
	public boolean equalsFor(Object other) {
		if (other instanceof String) {
			return true;
		}
		return false;
	}

	/**
	 * �ж���������Ƿ���List��
	 * 
	 * @param other
	 *            ����Ҫ�жϵĶ���
	 * @return boolean ֵ
	 */
	public boolean equals(Object other) {
		if (other instanceof List) {
			return true;
		}
		return false;
	}

	/**
	 * �ж���������Ƿ���Map����
	 * 
	 * @param other
	 *            ����Ҫ�жϵĶ���
	 * @return boolean ֵ
	 */
	public boolean equalsMap(Object other) {
		if (other instanceof Map) {
			return true;
		}
		return false;
	}

	/**
	 * ���������ļ�·�����Զ��õ���·���µ������ļ�
	 * 
	 * @param address
	 *            ����·��
	 */
	public void autoFindFiles(String address) {
		File file = new File(address);
		listFile(file);
		t = 0;
	}

	/**
	 * ���������ļ�����·�����Զ��õ���·���µ������ļ�
	 * 
	 * @param file
	 *            ����·��
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
