package com.exttool;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportTool {

	private Connection conn;

	private Statement st;

	public static void main(String[] args) {
		ImportTool tool = new ImportTool();
//		String sql = "select * from carlib";
//		List<Map<String, String>> query = tool.query(sql);
//		System.out.print(query.size());
		try {
			InputStream input = new FileInputStream("E:\\ResInt\\04.项目\\2016\\车险平台\\carlib.xlsx");
			Workbook  wb = new XSSFWorkbook(input);
			Sheet sheet = wb.getSheetAt(0);
			// Iterate over each row in the sheet
			Iterator<Row> rows = sheet.rowIterator();
			List<String> sqls = new ArrayList<String>();
			int i = 0;
			while (rows.hasNext()) {
//				if(i > 20) break;
				XSSFRow row = (XSSFRow) rows.next();
				if(row.getRowNum() == 0){
					continue;
				}
				System.out.println("Row #" + row.getRowNum() + "|" + row.getCell(5).toString() + "|" + row.getCell(6).toString() + "|");
//				`id` bigint(20) NOT NULL AUTO_INCREMENT,
//				  `alphabeta` varchar(5) NOT NULL,
//				  `brandName` varchar(50) NOT NULL,
//				  `facName` varchar(50) NOT NULL,
//				  `typeName` varchar(30) DEFAULT NULL,
//				  `carYear` varchar(30) DEFAULT NULL,
//				  `carTypeName` varchar(30) DEFAULT NULL,
//				  `brandCountry` varchar(30) DEFAULT NULL,
//				  `carLevel` varchar(30) DEFAULT NULL,
//				  `cc` int(5) DEFAULT '0',
//				  `gear` varchar(30) DEFAULT NULL,
				
				String string = "insert into carlib(alphabeta, brandName, facName, typeName,  carYear,  carTypeName,brandCountry, carLevel,cc, gear)values('"+ row.getCell(1).toString() +"','"+  row.getCell(2).toString()  +"','"+  row.getCell(3).toString()  +"','"+  row.getCell(4).toString() +"','"+  row.getCell(5).toString() +"','"+  row.getCell(6).toString() +"','"+ row.getCell(7).toString() +"','"+ row.getCell(9).toString() +"','"+ row.getCell(10).toString() +"','"+ row.getCell(11).toString() +"')";
				System.out.println(string);
				sqls.add(string);
				i ++;
			}
			tool.executeBatch(sqls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	/* 插入数据记录，并输出插入的数据记录数 */
	public int execute(String sql) {
		System.out.println("sql==" + sql); // 输出插入操作的处理结果
		conn = getConnection(); // 首先要获取连接，即连接到数据库
		try {
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象
			int count = st.executeUpdate(sql); // 执行插入操作的sql语句，并返回插入数据的个数
			conn.close(); // 关闭数据库连接
			return count;
		} catch (SQLException e) {
			System.out.println("插入数据失败" + e.getMessage());
		}
		return 0;
	}
	
	
	public void executeBatch(List<String> sqls) throws SQLException {
		conn = getConnection(); // 首先要获取连接，即连接到数据库
		try {
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象
			for (String sql : sqls) {
				st.addBatch(sql);
			}
			st.executeBatch();
		} catch (SQLException e) {
			System.out.println("插入数据失败" + e.getMessage());
		}finally{
			st.close();
			conn.close(); // 关闭数据库连接
		}
	}

	/**
	 * 获取查询的结果集，list返回，每条记录封装到一个map里
	 */
	public List<Map<String, String>> query(String sql) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		System.out.println("query sql==" + sql); // 输出插入操作的处理结果
		conn = getConnection(); // 同样先要获取连接，即连接到数据库
		try {
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
			ResultSet rs = st.executeQuery(sql); // 执行sql查询语句，返回查询数据的结果集
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) { // 判断是否还有下一个数据
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 1; i <= columnCount; i++) {
					map.put(metaData.getColumnName(i), rs.getString(i));
				}
				list.add(map);
			}
			System.out.println("查询结果数量：" + list.size());
			conn.close(); // 关闭数据库连接
		} catch (SQLException e) {
			System.out.println("查询数据失败");
		}
		return list;
	}

	/* 获取数据库连接的函数 */
	public Connection getConnection() {
		Connection con = null; // 创建用于连接数据库的Connection对象
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动
			con = DriverManager.getConnection("jdbc:mysql://localhost:12581/wfsc", "root", "windows#2013");// 创建数据连接
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return con; // 返回所建立的数据库连接
	}
}
