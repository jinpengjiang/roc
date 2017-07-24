// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   BuildXML.java

package autocode.build;

import java.io.*;
import java.sql.*;
import java.util.*;
import org.open.util.PublicFunction;

import autocode.utility.ApplicationConfig;

// Referenced classes of package autocode.build:
//            tag, BuildApp

public class BuildXML
{

    private Map DBTypes;
    private Map SQLTypes;
    private Map tagMap;
    private Map ColumnType;

    public Map getColumnType()
    {
        return ColumnType;
    }

    public void setColumnType()
    {
        List Olist = new ArrayList();
        Olist.add("COLUMN_NAME");
        Olist.add("DATA_TYPE");
        Olist.add("COMMENTS");
        Olist.add("DATA_LENGTH");
        Olist.add("DATA_PRECISION");
        Olist.add("DATA_SCALE");
        Olist.add("NULLABLE");
        ColumnType.put("oracle", Olist);
        List DBlist = new ArrayList();
        DBlist.add("COLUMN_NAME");
        DBlist.add("TYPE_NAME");
        DBlist.add("REMARKS");
        ColumnType.put("db2", DBlist);
    }

    public Map getSQLTypes()
    {
        return SQLTypes;
    }

    public void setSQLTypes()
    {
        List Olist = new ArrayList();
        Olist.add("SELECT a.COLUMN_NAME,a.DATA_TYPE,b.COMMENTS,a.DATA_LENGTH,a.DATA_PRECISION,a.DATA_SCALE,a.NULLABLE FROM user_tab_columns a,USER_COL_COMMENTS b ");
        Olist.add("and a.TABLE_NAME=b.TABLE_NAME and a.COLUMN_NAME=b.COLUMN_NAME order by a.COLUMN_ID asc");
        SQLTypes.put("oracle", Olist);
        List DBlist = new ArrayList();
        DBlist.add("SELECT * FROM SYSIBM.SQLCOLUMNS a ");
        DBlist.add("order by a.table_name,a.ordinal_position");
        SQLTypes.put("db2", DBlist);
    }

    public Map getTagMap()
    {
        return tagMap;
    }

    public void setTagMap()
    {
        tagMap = tag.map;
    }

    public Map getDBTypes()
    {
        return DBTypes;
    }

    public void setDBTypes()
    {
        DBTypes.put("oracle", "oracle.jdbc.driver.OracleDriver");
        DBTypes.put("db2", "com.ibm.db2.jcc.DB2Driver");
    }

    public BuildXML()
    {
        DBTypes = new HashMap();
        SQLTypes = new HashMap();
        tagMap = new HashMap();
        ColumnType = new HashMap();
        setDBTypes();
        setSQLTypes();
        setTagMap();
        setColumnType();
    }

    public static void exe()
        throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException
    {
        BuildXML buildXml = new BuildXML();
        List tableList = buildXml.connect();
        buildXml.makeXML(tableList);
    }

    public List connect()
        throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
    {
        List tl = new ArrayList();
        String url = (String)tagMap.get("getURL()");
        String username = (String)tagMap.get("getUserName()");
        String password = (String)tagMap.get("getPassWord()");
        String isall=(String)tagMap.get("getIsAll()");
        String types[] = url.split(":");
        Map db = getDBTypes();
        Map sql = getSQLTypes();
        Map col = getColumnType();
        String driver = (String)db.get(types[1]);
        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection(url, username, password);
        if(conn == null)
        {
            System.out.println("getConn:连接池获取不到connection。");
            return null;
        }
        Statement stmt = conn.createStatement(1004, 1008);
        Map map;
        for(Iterator iter = ((List)tagMap.get("getTableName()")).iterator(); iter.hasNext(); tl.add(map))
        {
            List column = new ArrayList();
            List type = new ArrayList();
            List comment = new ArrayList();
            List length = new ArrayList();
            List precision = new ArrayList();
            List scale = new ArrayList();
            List nullable=new ArrayList();
            String tableName = (String)iter.next();
            System.out.println("sql=:" + ((List)sql.get(types[1])).get(0) + "where a.TABLE_NAME='" + tableName + "' " + ((List)sql.get(types[1])).get(1));
            //ResultSet rs;
            ResultSet rs= stmt.executeQuery(((List)sql.get(types[1])).get(0) + "where a.TABLE_NAME='" + tableName + "' " + ((List)sql.get(types[1])).get(1));
            /*for(rs = stmt.executeQuery(((List)sql.get(types[1])).get(0) + "where a.TABLE_NAME='" + tableName + "' " + ((List)sql.get(types[1])).get(1)); !rs.isLast(); comment.add(rs.getObject((String)((List)col.get(types[1])).get(2))))
            {
                rs.next();
                column.add(rs.getObject((String)((List)col.get(types[1])).get(0)));
                type.add(rs.getObject((String)((List)col.get(types[1])).get(1)));
            }*/
            while(!(rs.isLast())){
                rs.next();
                comment.add(PublicFunction.doNull(rs.getObject((String)((List)col.get(types[1])).get(2))).toString().trim());
                column.add(rs.getObject((String)((List)col.get(types[1])).get(0)));
                type.add(rs.getObject((String)((List)col.get(types[1])).get(1)));
                length.add(rs.getObject((String)((List)col.get(types[1])).get(3)));
                precision.add(rs.getObject((String)((List)col.get(types[1])).get(4)));
                scale.add(rs.getObject((String)((List)col.get(types[1])).get(5)));
                nullable.add(rs.getObject((String)((List)col.get(types[1])).get(6)));
            }
            rs.close();
            map = new HashMap();
            map.put("column", column);
            map.put("type", type);
            map.put("comment", comment);
            map.put("length", length);
            map.put("precision", precision);
            map.put("scale", scale);
            map.put("nullable", nullable);
        }

        conn.close();
        return tl;
    }

    public void makeXML(List tableList)
        throws IOException
    {
        String path = "";
        path = BuildApp.getDir() + "/" + tagMap.get("getModule()") + ".xml";
        System.out.println("path=:"+path);
        String dir2 = path.substring(0, path.lastIndexOf("/"));
        File build = new File(dir2);
        if(!build.exists())
            build.mkdirs();
        String newFile = path.substring(path.lastIndexOf("/") + 1, path.length());
        File build2 = new File(dir2, newFile);
        if(!build2.exists())
            try
            {
                build2.createNewFile();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        //File file = new File(path);
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(path),ApplicationConfig.encoding);

        fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        fw.write("\r\n\t");
        fw.write("<root>");
        fw.write("\r\n\t");
        fw.write("<module name=\"" + tagMap.get("getModule()") + "\" package=\"" + tagMap.get("getPackage()") + "\" datasource=\"" + tagMap.get("getURL()") + "\" resource=\"RES_SYS_DEMO\"  username=\"" + tagMap.get("getUserName()") + "\" password=\"" + tagMap.get("getPassWord()") + "\">");
        fw.write("\r\n\t");
        Iterator iter = tableList.iterator();
        for(int i = 0; iter.hasNext(); i++)
        {
            fw.write("\r\n\t");
            fw.write("<class name=\"" + ((List)tagMap.get("getClass()")).get(i) + "\" instance=\"" + ((List)tagMap.get("getClassInstance()")).get(i) + "\" javadoc=\"" + ((List)tagMap.get("getClassDoc()")).get(i) + "\" title=\"" + ((List)tagMap.get("getClassTitle()")).get(i) + "\" table=\"" + ((List)tagMap.get("getTableName()")).get(i) + "\">");
            fw.write("\r\n\t");
            fw.write("\t");
            Map map = (Map)iter.next();
            List clist = (List)map.get("column");
            Iterator citer = clist.iterator();
            for(int k = 0; citer.hasNext(); k++)
            {
                String col = (String)citer.next();
                String tname = "";
                String name[] = col.split("_");
                tname = name[0].toLowerCase();
                if(name.length > 1)
                {
                    for(int j = 1; j < name.length; j++)
                        tname = tname + name[j].substring(0, 1).toUpperCase() + name[j].substring(1, name[j].length()).toLowerCase();

                }
                //if(PublicFunction.doNull(((List)map.get("comment")).get(k)).equals("ID"))
                if(k==0)
                {
                    fw.write("<id name=\"" + tname + "\" column=\"" + col +"\""  + typeXml((String)((List)map.get("type")).get(k),PublicFunction.doNull(((List)map.get("length")).get(k)),PublicFunction.doNull(((List)map.get("precision")).get(k)),PublicFunction.doNull(((List)map.get("scale")).get(k)),PublicFunction.doNull(((List)map.get("nullable")).get(k))) + " javadoc=\"" + PublicFunction.doNull(((List)map.get("comment")).get(k)) + "\" title=\"" + PublicFunction.doNull(((List)map.get("comment")).get(k)) + "\" >");
                    fw.write("\r\n\t");
                    fw.write("\t");
                    fw.write("\t");
                    fw.write("<generator class=\"GUID\"/>");
                    fw.write("\r\n\t");
                    fw.write("\t");
                    fw.write("</id>");
                    fw.write("\r\n\t");
                } else
                {
                    fw.write("\r\n\t");
                    fw.write("\t");
                    fw.write("<property name=\"" + tname + "\" column=\"" + col +"\"" + typeXml((String)((List)map.get("type")).get(k),PublicFunction.doNull(((List)map.get("length")).get(k)),PublicFunction.doNull(((List)map.get("precision")).get(k)),PublicFunction.doNull(((List)map.get("scale")).get(k)),PublicFunction.doNull(((List)map.get("nullable")).get(k)))  + " title=\"" + PublicFunction.doNull(((List)map.get("comment")).get(k)) + "\" />");
                    fw.write("\r\n\t");
                }
            }

            fw.write("\r\n\t");
            fw.write("</class>");
        }

        fw.write("\r\n\t");
        fw.write("</module>");
        fw.write("\r\n\t");
        fw.write("</root>");
        fw.write("\r\n\t");
        fw.flush();
        fw.close();
        System.out.println("对数据表进行makeXML编辑成功！！");
    }

    public String typeCheck(String type,String precision,String scale)
    {
        if(type == null)
            return "";
        if(PublicFunction.doNull(type).toUpperCase().equals("DATE") || PublicFunction.doNull(type).toUpperCase().equals("TIMESTAMP") || PublicFunction.doNull(type).toUpperCase().equals("TIME"))
            return "java.util.Date";
        if(PublicFunction.doNull(type).toUpperCase().equals("INTEGER"))
            return "java.lang.Integer";
        if( PublicFunction.doNull(type).toUpperCase().equals("NUMBER")){
        	
        	int int_precision=0;
        	int int_scale	=0;
        	if(null!=precision&&precision.length()>0)
        		int_precision=Integer.parseInt(PublicFunction.doNull(precision));
        	if(null!=scale&&scale.length()>0)
        		int_scale=Integer.parseInt(PublicFunction.doNull(scale));
        	if(int_scale>0)
        		return "java.lang.Double";
        	else if(int_precision>9)
        		return "java.lang.Long";
        	else
        		return "java.lang.Integer";
        }

        if(PublicFunction.doNull(type).toUpperCase().equals("LONG") || PublicFunction.doNull(type).toUpperCase().equals("BIGINT"))
            return "java.lang.Long";
        if(PublicFunction.doNull(type).toUpperCase().equals("DECIMAL"))
            return "java.math.BigDecimal";
        else
            return "java.lang.String";
    }
    
    public String typeXml(String type,String length,String precision,String scale,String nullable)
    {
    	String returnXml="";
        type=typeCheck(type,precision,scale);
        if(null==type||type.length()==0)
        	return "";
        
        if(type.equals("java.lang.Double")||type.equals("java.lang.Long")||type.equals("java.lang.Integer"))
        	returnXml= " type=\"" + type + "\" precision=\""+ precision + "\" scale=\""+scale +"\"";
        else
        	returnXml= " type=\"" + type + "\" length=\""+ length + "\"";
        if(null!=nullable&&nullable.equals("N"))
        	returnXml+=" not-null=\"true\"";
        return returnXml;
    }
}
