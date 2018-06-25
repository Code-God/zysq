package util;

/*

 * SourceCodeAnalysis Beta1.2 

 * Created on Sep 29, 2005

 *

 * To change the template for this generated file go to

 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments

 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

/**
 * 
 * @author JACKY
 * 
 * 使用时，配置文件放到项目的根目录下即可，注意：是项目根目录，不是src的根目录。<br>
 * 如果pFileName = "conf/Analysis.properties";则表示在项目根目录下的conf文件夹里。
 * 
 * To change the template for this generated type comment go to
 * 
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 * 
 */
public class SourceCodeAnalysis {
	/** properties file */
	public final static String pFileName = "Analysis.properties";

	public final static String DATE_FORMATE = "yyyyMMdd_HHmmss";

	public final static String DATE_FORMATE1 = "yyyy-MM-dd";

	public final static String DATE_FORMATE2 = "yyyy-MM-dd HH:mm:ss";

	public Float TOTALSIZE = new Float(0);

	public Integer TOTAL = new Integer(0);

	public Integer COMMENT = new Integer(0);

	public Integer REAL = new Integer(0);

	public Integer BLANK = new Integer(0);

	public Float COMMENTP = new Float(0);

	/** total bugs, read from properties file */
	public int totalBugs = 0;

	public String REPORT_FILE_NAME = "";

	public String[] DIR = null;

	public boolean RECURSIVE = false;

	HashMap<String, Number> hm = new HashMap<String, Number>();

	/** report dir */
	public String reportdir = "";

	@SuppressWarnings("unchecked")
	public SourceCodeAnalysis() throws IOException {
		this.reportdir = getProp(pFileName, "report.dir");
		this.totalBugs = Integer.parseInt(getProp(pFileName, "total.bugs").trim().equals("") ? "0" : getProp(pFileName,
				"total.bugs"));
		this.DIR = getProp(pFileName, "path.dir").split(";");
		this.RECURSIVE = new Boolean(getProp(pFileName, "recursive")).booleanValue();
		BuildHeader();
		// collect all the data into array, for overall statistics
		hm.put("totalsize", TOTALSIZE);
		hm.put("total", TOTAL);
		hm.put("comment", COMMENT);
		hm.put("real", REAL);
		hm.put("blank", BLANK);
		hm.put("commentp", COMMENTP);
	}

	/**
	 * 
	 * 
	 * 
	 */
	private void BuildHeader() throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE);
		String date = sdf.format(new Date());
		this.REPORT_FILE_NAME = reportdir + "\\" + date + "_report.txt";
		File f = new File(reportdir);
		if (!f.exists()) {
			f.mkdirs();
		}
		FileWriter fw = new FileWriter(REPORT_FILE_NAME);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("Report Date:" + new SimpleDateFormat(DATE_FORMATE2).format(new Date()));
		bw.newLine();
		bw.write("----------------------------------------------------------------------------------------------------------------------");
		bw.newLine();
		bw.write("File Name                        | Time Stamp   |   Byte    |    Type |   Total |   Comment | Real | Blank |  Comment%");
		bw.newLine();
		bw.write("----------------------------------------------------------------------------------------------------------------------");
		bw.flush();
		bw.close();
	}

	/**
	 * 
	 * analysis the files in certain folder
	 * 
	 * @param path
	 * 
	 * @param recursive
	 * 
	 */
	public void Analysis(String[] path, boolean recursive) throws IOException {
		for (int i = 0; i < path.length; i++) {
			System.out.println("in path " + (i + 1) + path[i]);
			// if(!f.canRead()){
			// System.err.println("Folder cannot read!!!");
			// }
			if (recursive) {
				AnalysisRec(path[i]);
			} else {
				AnalysisNormal(path[i]);
			}
		}
		buildFoot();
	}

	/**
	 * 
	 * 
	 * 
	 */
	private void buildFoot() throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE);
		String date = sdf.format(new Date());
		// FileWriter fw=new FileWriter(reportdir + "\\"+date+"_report.txt");
		RandomAccessFile rf = new RandomAccessFile(REPORT_FILE_NAME, "rw");
		System.out.println("build fooooot==" + rf.length());
		rf.seek(rf.length());
		rf.writeBytes("\r\n");
		rf
				.writeBytes("----------------------------------------------------------------------------------------------------------------------");
		rf.writeBytes("\r\nJava/jsp src SUM");
		this.WriteSpace(rf, 51 - "Java/jsp src SUM".length());
		float size = ((Float) hm.get("totalsize")).floatValue() / 1000;
		if (size < 1000) {
			rf.writeBytes(new Float(size).toString() + "K");
		} else {
			float _size = size / 1000;
			rf.writeBytes(new Float(_size).toString() + "M");
		}
		this.WriteSpace(rf, 24 - hm.get("totalsize").toString().length());
		rf.writeBytes(hm.get("total").toString());
		this.WriteSpace(rf, 9 - hm.get("total").toString().length());
		rf.writeBytes(hm.get("comment").toString());
		this.WriteSpace(rf, 10 - hm.get("comment").toString().length());
		rf.writeBytes(hm.get("real").toString());
		this.WriteSpace(rf, 9 - hm.get("real").toString().length());
		rf.writeBytes(hm.get("blank").toString());
		this.WriteSpace(rf, 10 - hm.get("blank").toString().length());
		rf.writeBytes("--");
		rf.writeBytes("\r\n\r\n\r\n\r\n");
		// Total bugs: 50
		// Every 1000 line: 912/1000 = bug(s).
		int totalline = ((Integer) hm.get("real")).intValue();
		if (totalline != 0) {
			BigDecimal re = new BigDecimal(totalBugs).multiply(new BigDecimal(1000)).divide(new BigDecimal(totalline), 2, 1);
			if (totalBugs != 0) {
				rf.writeBytes("Total bugs: " + totalBugs);
				rf.writeBytes("\r\nEvery 1000 lines: " + re.toString() + " bugs");
			}
		}
		rf.close();
	}

	/**
	 * 
	 * @param path
	 * 
	 */
	private void AnalysisNormal(String path) throws IOException {
		File f = new File(path);
		if (!f.exists()) {
			throw new FileNotFoundException("File Not Found!Pls check!");
		}
		File subF[] = f.listFiles();
		for (int i = 0; i < subF.length; i++) {
			if (!subF[i].isDirectory()) {
				System.out.println("Analysising file: " + subF[i].getName());
				// if(subF[i].getName().equalsIgnoreCase("sourcecodeanalysis.java")){
				AnalysisFile(path + "\\" + subF[i].getName());
				// }
			}
		}
	}

	/**
	 * 
	 * @param path
	 * 
	 */
	private void AnalysisRec(String path) throws FileNotFoundException {
		File f = new File(path);
		if (!f.exists()) {
			throw new FileNotFoundException("File Not Found!Pls check!");
		}
		File subF[] = f.listFiles();
		for (int i = 0; i < subF.length; i++) {
			if (!subF[i].isDirectory()) {
				System.out.println("Analysising file: " + subF[i].getName());
				// if(subF[i].getName().equalsIgnoreCase("sourcecodeanalysis.java")){
				try {
					AnalysisFile(path + "\\" + subF[i].getName());
				} catch (IOException e) {
					e.printStackTrace();
				}
				// }
			} else {
				System.out.println("====In directory:" + subF[i].getName());
				if(subF[i].getName().equals(".svn")){
					continue;
				}else{
					AnalysisRec(path + "\\" + subF[i].getName());
				}
			}
		}
	}

	/**
	 * 
	 * analysis a file<br>
	 * 
	 * get the statistics and write into a file(txt)
	 * 
	 * @param file
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void AnalysisFile(String fullpath) throws IOException {
		String fileName = "";
		String fileType = "";
		String timeStamp = "";
		float fileSize = 0;
		int total = 0; // total line num
		int comment = 0;// comment line num
		int real = 0;// real line num
		int blank = 0;// blank line num
		float comPer = 0.0f;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE1);
		File f = new File(fullpath);
		String fname = f.getName();
		fileSize = f.length();
		fileName = fname;
		if (fname.substring(fname.indexOf('.') + 1).equalsIgnoreCase("java")) {
			fileType = "java";
		} else if (fname.substring(fname.indexOf('.') + 1).equalsIgnoreCase("jsp")) {
			fileType = "jsp";
		} else {
			fileType = "???";
		}
		// get time stamp
		timeStamp = sdf.format(new Date(f.lastModified()));
		FileReader fr = new FileReader(fullpath);
		BufferedReader br = new BufferedReader(fr);
		int n1 = 0; // html
		int n2 = 0; // c
		int n3 = 0; // scriptlet
		int n4 = 0; // vb script
		String line = br.readLine();
		if ((line != null && line.indexOf("//") != -1 && line.substring(0, line.indexOf("//")).trim().equals(""))
				|| (line != null && line.indexOf("'") != -1 && line.substring(0, line.indexOf("'")).trim().equals(""))
				|| (line != null && line.indexOf("/*") != -1 && line.indexOf("*/") != -1)
				|| (line != null && line.indexOf("<!--") != -1 && line.indexOf("-->") != -1)
				|| (line != null && line.indexOf("<%--") != -1 && line.indexOf("--%>") != -1)) {
			System.out.println("++ " + line);
			comment++;
		}
		if (line != null && line.indexOf("<!--") != -1 && line.indexOf("-->") == -1) {
			n1++;
		}
		if (line != null && line.indexOf("/*") != -1 && line.indexOf("*/") == -1 && !line.startsWith("//")) {
			n2++;
		}
		if (line != null && line.indexOf("<%--") != -1 && line.indexOf("--%>") == -1) {
			n3++;
		}
		// System.out.println(total + ":" +line);
		while (line != null) {
			line = br.readLine();
			// System.out.println("***************** "+line);
			if ((n1 == 0 && n2 == 0 && n3 == 0) && (line == null || "".equals(line.trim()))) {
				blank++;
				// System.out.println("***************** "+line);
			}
			// test
			if ((line != null && line.indexOf("//") != -1 && line.substring(0, line.indexOf("//")).trim().equals(""))
					|| (line != null && line.indexOf("'") != -1 && line.substring(0, line.indexOf("'")).trim().equals(""))) {
				// System.out.println("++ "+line);
				comment++;
			}
			// =================handler C style comment,html multiple(Single)
			// line style comment,VB Script style comment and scriptlet style
			// comment
			if (line != null && line.indexOf("<!--") != -1 && line.indexOf("-->") != -1) { // single
				// line
				// comment
				// in
				// html
				comment++;
			}
			if (n1 == 0) {
				if (line != null && line.indexOf("<!--") != -1 && line.indexOf("-->") == -1) {
					n1++;
				}
			} else {
				if (line != null && line.indexOf("-->") != -1) {
					comment += (n1 + 1);
					n1 = 0;
				} else {
					n1++;
				}
			}
			// single line comment in C
			if (line != null && line.indexOf("/*") != -1 && line.indexOf("*/") != -1) {
				comment++;
			}
			if (n2 == 0) {
				if (line != null && line.indexOf("/*") != -1 && line.indexOf("*/") == -1 && !line.startsWith("//")) {
					n2++;
				}
			} else {
				if (line != null && line.indexOf("*/") != -1 && !line.startsWith("//")) {
					comment += (n2 + 1);
					n2 = 0;
				} else if (line != null && !line.startsWith("//")) {
					n2++;
				}
			}
			// single line comment in scriptlet
			if (line != null && line.indexOf("<%--") != -1 && line.indexOf("--%>") != -1) {
				comment++;
			}
			if (n3 == 0) {
				if (line != null && line.indexOf("<%--") != -1 && line.indexOf("--%>") == -1) {
					n3++;
				}
			} else {
				if (line != null && line.indexOf("--%>") != -1) {
					comment += (n3 + 1);
					n3 = 0;
				} else {
					n3++;
				}
			}
			// ===========================
			// ////test2
			// System.out.println(total + ":" +line);
			total++;
		}
		// File Name
		// Time Stamp
		// Byte
		// Type
		// Total
		// Comment
		// Real
		// Blank
		// Comment%");
		if (!fileType.equals("???")) {
			System.out.println("File Name :" + fileName);
			System.out.println("Time stamp :" + timeStamp);
			System.out.println("fileSize :" + fileSize);
			System.out.println("File type :" + fileType);
			System.out.println("total:" + total);
			System.out.println("comment:" + comment);
			System.out.println("real :" + (total - blank - comment));
			System.out.println("blank:" + blank);
			float commentp = new BigDecimal(comment).multiply(new BigDecimal(100).divide(new BigDecimal(total), 2, 2))
					.floatValue();
			System.out.println("comment%:" + commentp + "%");
			SimpleDateFormat sdf_ = new SimpleDateFormat(DATE_FORMATE);
			String date = sdf_.format(new Date());
			// write data into file by increment
			RandomAccessFile rf = new RandomAccessFile(REPORT_FILE_NAME, "rw");
			hm.put("totalsize", new Float(((Float) hm.get("totalsize")).floatValue() + fileSize));
			hm.put("total", new Integer(((Integer) hm.get("total")).intValue() + total));
			// hm.get("comment");
			hm.put("comment", new Integer(((Integer) hm.get("comment")).intValue() + comment));
			// hm.get("real");
			hm.put("real", new Integer(((Integer) hm.get("real")).intValue() + (total - blank - comment)));
			// hm.get("blank");
			hm.put("blank", new Integer(((Integer) hm.get("blank")).intValue() + blank));
			// hm.get("commentp");
			float newCommentp = ((Float) hm.get("commentp")).floatValue() + commentp;
			// int newtotal = ((Integer)hm.get("total")).intValue();
			hm.put("commentp", new Float(newCommentp));
			rf.seek(rf.length());// move the pointer to file end
			rf.writeBytes("\r\n" + fileName);
			WriteSpace(rf, 35 - fileName.length());
			rf.writeBytes(timeStamp);
			WriteSpace(rf, 16 - timeStamp.length());
			rf.writeBytes(String.valueOf(fileSize / 1000) + "K");
			WriteSpace(rf, 15 - (String.valueOf(fileSize / 1000) + "K").length());
			rf.writeBytes(fileType);
			WriteSpace(rf, 9 - fileType.length());
			rf.writeBytes(String.valueOf(total));
			WriteSpace(rf, 9 - String.valueOf(total).length());
			rf.writeBytes(String.valueOf(comment));
			WriteSpace(rf, 10 - String.valueOf(comment).length());
			rf.writeBytes(String.valueOf(total - blank - comment));
			WriteSpace(rf, 9 - String.valueOf(total - blank - comment).length());
			rf.writeBytes(String.valueOf(blank));
			WriteSpace(rf, 9 - String.valueOf(blank).length());
			if (total != 0) {
				rf.writeBytes(new BigDecimal(comment).multiply(new BigDecimal(100).divide(new BigDecimal(total), 2, 2))
						+ "%");
			}
			rf.close();
		}
	}

	/**
	 * 
	 * @param n
	 * 
	 */
	private void WriteSpace(RandomAccessFile rf, int n) throws IOException {
		for (int i = 0; i < n; i++) {
			rf.writeBytes(" ");
		}
	}

	/**
	 * 
	 * get the value of certain properties
	 * 
	 * @param fileName
	 * 
	 * @param propName
	 * 
	 * @return String - properties value
	 * 
	 */
	public String getProp(String fileName, String propName) {
		Properties props = new Properties();
		fileName = pFileName;
		FileInputStream in;
		try {
			in = new FileInputStream(fileName);
			props.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String retVal = props.getProperty(propName);
		return retVal;
	}

	public static void main(String[] args) throws IOException {
		SourceCodeAnalysis sca = new SourceCodeAnalysis();
		// sca.Analysis("C:\\SpainSignature\\OscarWeb\\SignatureJavaSource\\tt\\",false);
		sca.Analysis(sca.DIR, sca.RECURSIVE);
	}
}
