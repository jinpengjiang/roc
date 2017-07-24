package autocode.build;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class readFileTest {

	public static void main(String[] args){
		readFileTest obj =new readFileTest();
		obj.outPutJava(obj.getFunctions("d://Coreapi.php"));
	}
			
	
	public  List getFunctions(String fileName){
		File file = new File(fileName);
        BufferedReader reader = null;
        List functions = new ArrayList();
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis,"UTF-8"); //指定以UTF-8编码读入
            reader = new BufferedReader(isr);
            String tempString = null;
            StringBuffer sb = new StringBuffer();
            Func func = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
            	
            	String temp = tempString.trim();
                if(temp.startsWith("/*")){
                	if(!temp.startsWith("/**"))
                		tempString=tempString.replace("/*", "/**");
                	sb.append(tempString).append("\n");
                }
                else if(sb.length()>0 && !temp.startsWith("public function")){
                	sb.append(tempString).append("\n");
                }
                if(temp.startsWith("public function")){
                	if(func != null) functions.add(func);
                	func = new Func();
                	func.remark = sb.toString();
                	temp = temp.replace("public function", "");
                	temp = temp.trim();
                	temp = temp.substring(0, temp.indexOf('('));
                	func.functionName = temp.trim();
                	sb.setLength(0);
                }
                if(temp.startsWith("if (empty")){
                	temp = temp.substring(temp.indexOf('$')+1, temp.indexOf(')'));
                	func.validiFields.add(temp);
                }
                if(temp.contains("=>")){
                	temp = temp.replace("'", "");
                	temp = temp.replace(",", "");	
                	temp = temp.replace(" ", "");
                	temp = temp.replace("$", "");
                	String[] fields = temp.split("=>");
                	func.fieldMaps.put(fields[1], fields[0]);
                }
            }
            return functions;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
	}
	
	
	public void outPutJava(List functions){
		FileWriter writer =null;
		if(functions == null){
			System.out.print("READ FUNCTION IS NULL");
			return ;
		}
		try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer = new FileWriter("d://CloudApi1.java", false);
			
			for(int i=0;i<functions.size();i++){
				StringBuffer sb = new StringBuffer();
				Func func = (Func)functions.get(i);
				if(func.remark != null && func.remark.length()>0){
					sb.append(func.remark);
				}
				sb.append("public String ").append(func.functionName).append("(Map<String,String> params) throws Exception {\n");
				if(func.validiFields!= null && func.validiFields.size()>0){
					for(int j=0;j<func.validiFields.size();j++){
						String fieldName = (String)func.fieldMaps.get(func.validiFields.get(j));
						sb.append("if(!params.containsKey(\"").append(fieldName).append("\")||ValidateUtil.isBlank(params.get(\"").append(fieldName).append("\"))){\n");
						sb.append("throw new BaseAppException(ExceptionConst.PARAM_IS_NULL,\"%s参数不能为空\",\"[").append(func.functionName).append(".").append(fieldName).append("]\");");
						sb.append("}\n");
					}
				}
				sb.append("return request(\"").append(func.functionName).append("\" ,params);\n");
				sb.append("}\n\n");
				writer.write(sb.toString());
			}
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                	writer.close();
                } catch (IOException e1) {
                }
            }
        }
		
	}
	
	public class Func{
		public String functionName;
		public Map  fieldMaps=new HashMap();
		public List  validiFields = new ArrayList();
		public String remark;
	}
}
