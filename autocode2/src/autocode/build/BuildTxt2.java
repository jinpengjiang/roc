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
 * title�� AutoCode<br>
 * dec:�����Զ�����(Module)<br>
 * 
 * @author wanglijun
 * @version 1.0
 */
public class BuildTxt2 {
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
	/** ���for.classֵ����ʱ�б�<br> */
	private List list3 = new ArrayList();
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
	/** ��¼��ǰѭ������for.class/for.class.property<br> */
	private int x[][] = new int[100][100];
	/** ��CLASS��PROPERTY�ĸ���<br> */
	private int property = 0;
	/** ��Module��CLASS�ĸ���<br> */
	private int classtype = 0;
	/** ���ǰ�����Ϣ������<br> */
	private String fortemp[][] = new String[200][Integer.MAX_VALUE / 500000];
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
	private int e = 0;
	/** ѭ���������Ʋ���<br> */
	private int g = 0;
	/** ��ʱ���ѭ�����ڲ���Ϣ<br> */
	private Map temp = new HashMap();
	/** ����z��������ʱ�������2ά�±�<br> */
	private int q = 0;
	/** x����ĵ�2ά�±�<br> */
	private int r = 0;
	/** ѭ����ÿ������ѭ���Ľ���λ��<br> */
	private int z[][] = new int[200][200];
	/** tag��ǩ���key<br> */
    private List tagKey=new ArrayList();
    /** tag��ǩ���value<br> */
	private Map tagMap=new HashMap();


	/**
	 * ����һ���޲εĹ��캯��
	 */
	public BuildTxt2() {
		tagMap = tag.map;
		tagKey = tag.key;
	}
	
	/**
	 * ��ģ���ļ����ݣ�д�������ļ�
	 * @param file ģ���ļ�
	 * @param file2 �����ļ�
	 * @return boolean ֵ
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
	 * ����ģ���ļ����ݣ�д�������ļ�
	 * 
	 * @param File
	 *            ģ���ļ�����
	 */
	public void WriteFile(String File) throws IOException {
		if (check(File)) {
			moduleTagChange(File);
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
	 * �����ַ���(ѭ������ʼ���)�������ȡ������������Ҫ��ѭ����ʾ������
	 * 
	 * @param key1
	 *            �����ַ���(FOR.CLASS.PROPERTY/FOR.CLASS)
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
	 * �����ַ���(ѭ������ʼ���)�������ȡ������������Ҫ��ѭ����ʾ������
	 * @param list �����ַ������ݵ�list
	 * @param str ���滻���ַ���
	 * @param b �ⲿѭ��������־
	 * @param type ѭ������(class/property)
	 * @param inFile δ�滻ǰ�������ַ���
	 * @param end ��������ѭ��������־
	 * @return Map ����b,list,inFile(�ⲿѭ��������־���滻��ɵ����ݣ������滻�õ��ַ���)
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
	 * ����ģ���ļ����ݰ�Class���ͽ����滻 Module���ͣ�����module��ģ�飩�ĸ������ֱ����������Ӧ���ļ���
	 * 
	 * @param inFile
	 *            ģ���ļ�����
	 * @return boolean ֵ
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
	 * ��ģ���ļ��е���ʼλ�ã���ģ���ļ����б������õ���һ��Ŀ��Ŀ�ʼλ��
	 * 
	 * @param begin
	 *            ģ���ļ��е���ʼλ��
	 * @param target
	 *            ��Ҫ���ҵ�Ŀ����
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
					b = i - 1;// �Լ����ɵ�βֱ��Ҫȥ��
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
					b = i - 1;// �Լ����ɵ�βֱ��Ҫȥ��
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
	 * �õ�x����ĵ�2ά�±�
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
	 * �ѱ�����temp�����ݵ���out()������������fortemp��
	 * @param a ѭ������(1/2)
	 */
	public void outtemp(String a){
		String fortype = a;
		int count[] = tag.propertyClassCount();
		int propetrycount = count[type];
		int module = tag.classCount();
		out(fortype,module,propetrycount);
	}
	
	/**
	 * �ѱ�����temp��������������fortemp��
	 * @param fortype ѭ������(1/2)
	 * @param module Class������
	 * @param propetrycount Class.property������
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
	 * ���ǰ��׼��������fortemp�����п���
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
	 * �����ַ������ָ��ַ�����ͬʱ�õ�ѭ����ʼ���������ж�����
	 * 
	 * @param inFile
	 *            �����ַ���
	 * @return Map ����i,b,str[](��ʼ��������ѭ������)
	 */
	public Map StringSplit(String inFile){
		Map biList=new HashMap();
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
	 * �������ַ��������в��
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
				return st;
			}
		}
	}

	/**
	 * �����б�������
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

}
