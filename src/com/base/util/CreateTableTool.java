package com.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wfsc.common.bo.user.LogCharger;

public class CreateTableTool {
	/**   
     * 根据表名称 和 实体对象 创建一张表 ,已排除id和使用 final 和 static修饰的 字段,其中id已做特殊处理，所以排除。
     * 对于String类型默认全部存储为varchar(255),所以需要存储为text类型的还是需要自己手动改一下
     * @param tableName   表名
     * @param obj 实体对象 
     * @param noCol 不需要生成的列   
     */    
    @SuppressWarnings("unchecked")  
    public static String createTable(String tableName,Object obj, List<String> noCol){  
    	List<String> foreignKey = new ArrayList<String>();
        StringBuffer sb = new StringBuffer("DROP TABLE IF EXISTS `"+tableName+"`;");    
        sb.append("\n");
        sb.append("CREATE TABLE `" + tableName + "` (");
        sb.append("\n");
        sb.append("`id` bigint(20) NOT NULL AUTO_INCREMENT,");  
        sb.append("\n");
        Class c = obj.getClass();  
        Field field[] = c.getDeclaredFields();  
        for (Field f : field) {  
            if("id".equals(f.getName()) || noCol.contains(f.getName()) || Modifier.isStatic(f.getModifiers())|| Modifier.isFinal(f.getModifiers())){  
               continue;
            }else{
            	 String type = f.getType().toString();  
                 if(type.equals("class java.lang.String")){// String  
                      sb.append("`" + f.getName() + "` varchar(255) DEFAULT NULL,");    
                      sb.append("\n");
                 }else if(type.equals("int") || type.equals("class java.lang.Integer")){// int  
                     sb.append("`" + f.getName() + "` int(10) DEFAULT NULL,");     
                     sb.append("\n");
                 }else if(type.equals("double") || type.equals("class java.lang.Double")){// double  
                     sb.append("`" + f.getName() + "` DECIMAL(10,2) DEFAULT NULL,"); 
                     sb.append("\n");
                 }else if(type.equals("float") || type.equals("class java.lang.Float")){// float  
                     sb.append("`" + f.getName() + "` DECIMAL(10,2) DEFAULT NULL,"); 
                     sb.append("\n");
                 }else if(type.equals("long") || type.equals("class java.lang.Long")){// long  
                     sb.append("`" + f.getName() + "` bigint(20) DEFAULT NULL,");     
                     sb.append("\n");
                 }else if(type.equals("boolean") || type.equals("class java.lang.Boolean")){// boolean  
                     sb.append("`" + f.getName() + "` bit,");     
                     sb.append("\n");
                 }else if(type.equals("class java.util.Date")){// Date  
                     sb.append("`" + f.getName() + "` datetime DEFAULT NULL,");     
                     sb.append("\n");
                 }else{	//否则就当外键处理
                	 sb.append("`" + f.getName() + "Id` bigint(20) DEFAULT NULL,");     //外键==字段名+Id
                     sb.append("\n");
                     foreignKey.add(f.getName());
                 }        
            }  
        }  
        sb.append(" PRIMARY KEY (`id`)");
        if(foreignKey.size()>0){
        	sb.append(",");
        	sb.append("\n");
        }
        for(int i=0;i<foreignKey.size();i++){
        	String fkName = "FK"+new Date().getTime();
        	 sb.append(" CONSTRAINT "+fkName+" FOREIGN KEY("+foreignKey.get(i)+"Id) REFERENCES wf_"+foreignKey.get(i)+"(id)");
        	 if(i!=foreignKey.size()-1){
         		sb.append(",");
         		sb.append("\n");
         	}
        	 
        }
        sb.append("\n");
        sb.append(")ENGINE=InnoDB DEFAULT CHARSET=utf8;");     
        
        return sb.toString();     
    }   
    
    /**
     * 在控制台打印建表语句
     * @param args
     */
    public static void main(String[] args){
    	//不需要存储的字段，默认已排除使用 final 和 static修饰的 字段
    	List<String> noCol = new ArrayList<String>();
    	//需要为该实体对象生成建表语句
    	LogCharger obj = new LogCharger();
    	
    	//表名默认为wf_类名的小写
    	String className = obj.getClass().getName().toLowerCase();
    	String tableName = "wf_"+className.substring(className.lastIndexOf(".")+1);
    	String str = createTable(tableName,obj,noCol);
    	System.out.println(str);
    	
    }

}
