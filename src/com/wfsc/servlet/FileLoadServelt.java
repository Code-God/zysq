package com.wfsc.servlet;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.base.ServerBeanFactory;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.services.productcat.IProductCatService;

public class FileLoadServelt extends HttpServlet {

	private static final long serialVersionUID = -4509793750650977333L;

	/**
	 * Constructor of the object.
	 */
	public FileLoadServelt() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orgCode = request.getSession().getAttribute("orgCode").toString();
		String catId = request.getParameter("catId");
		// 上传之后的文件保存在这个文件夹下
		String serverFilePath = this.getServletContext().getRealPath("") + java.io.File.separator + "private" + java.io.File.separator + "category" + java.io.File.separator + orgCode;
		File ff = new File(serverFilePath);
		if (!ff.exists()) {
			ff.mkdirs();
		}
		String filename = "";
		String type = "";
		ServletInputStream in = request.getInputStream();
		byte[] buf = new byte[4048];
		int len = in.readLine(buf, 0, buf.length);
		String f = new String(buf, 0, len - 1);
		while ((len = in.readLine(buf, 0, buf.length)) != -1) {
			filename = new String(buf, 0, len);
			int j = filename.lastIndexOf("\"");
			int p = filename.lastIndexOf(".");
			if(p == -1){
				return;
			}
			type = filename.substring(p, j); // 文件类型
			filename = catId + type; // 文件名称:就用分类ID来命名，不会重复
			DataOutputStream fileStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(serverFilePath + File.separator + filename)));
			len = in.readLine(buf, 0, buf.length);
			len = in.readLine(buf, 0, buf.length);
			while ((len = in.readLine(buf, 0, buf.length)) != -1) {
				String tempf = new String(buf, 0, len - 1);
				if (tempf.equals(f) || tempf.equals(f + "--")) {
					break;
				} else {
					fileStream.write(buf, 0, len); // 写入
				}
			}
			fileStream.close();
		}
		PrintWriter out = response.getWriter();
		String result = filename;
		out.print(result);
		out.close();
		in.close();
		// 更新到数据库
		if (catId != null) {
			IProductCatService service = (IProductCatService) ServerBeanFactory.getBean("productCatService");
			ProductCat cat = service.getProductCatById(Long.valueOf(catId));
			cat.setPicUrl(orgCode + "/" +filename);
			service.saveOrUpdateProductCat(cat);
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
}
