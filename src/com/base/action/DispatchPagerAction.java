package com.base.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import model.bo.auth.Org;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.log4j.Logger;

import service.intf.AdminService;

import com.base.ServerBeanFactory;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.wfsc.actions.common.CupidBaseAction;
import com.wfsc.util.SysUtil;

public abstract class DispatchPagerAction extends CupidBaseAction {
	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	private int pageNo;

	private int pageSize = 15;

	private int start;

	private int totalSize;

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize(HttpServletRequest request) {
		String size = request.getParameter("pageSize");
		if(size != null && !"".equals(size)){
			return Integer.valueOf(size);
		}
		return this.pageSize;
	}

	public void setPageSize(int pageSize, HttpServletRequest request) {
		//如果用户设置过每页显示条数，则以用户设置的为主
		if(request.getSession().getAttribute("pageSize") != null){
			this.pageSize = Integer.valueOf(request.getSession().getAttribute("pageSize").toString());
		}else{
			this.pageSize = pageSize;
		}
		request.setAttribute("pageSize", Integer.valueOf(pageSize));
	}

	public void prepare() throws Exception {
		this.generatePagerParam(request, totalSize);
	}

	protected void generatePagerParam(HttpServletRequest request, int totalSize) {
		setTotalSize(totalSize);
		String pageNoStr = (request.getParameter("pageNo") == null) ? "1" : request.getParameter("pageNo");
		System.out.println("in pagerAction--------------------------------------------传入的当前页码：" + pageNoStr);
		if (this.pageSize == 0) {
			this.pageSize = 10;
		} else if (request.getAttribute("pageSize") != null && NumberUtils.isDigits(request.getAttribute("pageSize").toString())) {
			// 有数据
			this.pageSize = (Integer) request.getAttribute("pageSize");
//			request.setAttribute("pageSize", this.pageSize);
			request.getSession().setAttribute("pageSize", this.pageSize);
		}else if(StringUtils.isNotEmpty(request.getParameter("pageSize")) ){//用户指定的条数，放到session里
			// 有数据
			this.pageSize = Integer.valueOf(request.getParameter("pageSize"));
			request.getSession().setAttribute("pageSize", this.pageSize);
		}
		setPageSize(this.pageSize, request);
		int totalPage = totalSize / this.pageSize + ((totalSize % this.pageSize == 0) ? 0 : 1);
		if (Integer.parseInt(pageNoStr) > totalPage)
			setPageNo(totalPage);
		else {
			setPageNo(Integer.parseInt(pageNoStr));
		}
		int start = (Integer.valueOf(getPageNo()).intValue() - 1) * Integer.valueOf(this.pageSize).intValue();
		setStart(start);
		request.setAttribute("start", Integer.valueOf(start));
		request.setAttribute("pageNo", Integer.valueOf(Integer.parseInt(pageNoStr)));
		request.setAttribute("totalSize", Integer.valueOf(totalSize));
	}
	
	/**
	 * 获得war工程绝对路径 
	 * @return
	 */
	public String getAbsoluteRootPath(){
		String systemPath = request.getSession().getServletContext().getRealPath("/");
		return systemPath;
	}

	public int getStart(HttpServletRequest request) {
		int startindex = StringUtils.isNotBlank(request.getParameter("start")) ? Integer.parseInt(request.getParameter("start")) : 0;
		start = startindex;
		return ((this.start < 0) ? 0 : this.start);
	}

	public void setStart(int start) {
		this.start = start;
	}

//	public abstract int getTotalSize(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
	
	/**
	 * 获得用户上传路径 
	 * @param request
	 * @param dir - 指定的目录，如果不存在，会再服务器上新建一个
	 * @return
	 */
	protected String getUserPicPath(HttpServletRequest request, String dir) {
		String rootPath = request.getSession().getServletContext().getRealPath("/upload/" + dir);
		File f = new File(rootPath);
		if (!f.exists()) {
			f.mkdirs();
		}
		return rootPath;
	}
	/**
	 * 获得商品图片上传路径 
	 * @param request
	 * @param dir - 指定的目录，如果不存在，会再服务器上新建一个
	 * @return
	 */
	protected String getProductPicPath(HttpServletRequest request, String dir) {
		String rootPath = request.getSession().getServletContext().getRealPath("/private/UploadImages/" + dir);
		File f = new File(rootPath);
		if (!f.exists()) {
			f.mkdirs();
		}
		return rootPath;
	}

	/**
	 * 是否需要登录 
	 * @param request
	 * @param state
	 * @return true - 需要登录才允许进入    false-不需要登录
	 */
	protected boolean checkAuthMenu(HttpServletRequest request, String state){
		String authStr = Version.getInstance().getNewProperty("AUTH_MENU");
		String[] split = StringUtils.split(authStr, "|");
		boolean needAuth = false;
		for (String string : split) {
			if(string.equals(state)){
				logger.info("IN BaseAction  state=" + state + "需要登录才能进入.......");
				needAuth = true;
				break;
			}
		}
		return needAuth;
	}
	/**
	 * 用户（普通用户，分销客）当前访问的分销商的对象
	 * @return
	 */
	public Org getCurrentFenXiao(){
		Object obj = request.getSession().getAttribute("WXFENXIAO");
		if(obj != null){
			return (Org) obj;
		}else{
			if("y".equals(Version.getInstance().getNewProperty("wxtest"))){
				//测试用
				AdminService adminService = (AdminService) ServerBeanFactory.getBean("adminService");
				Org org = adminService.getOrgById(Long.valueOf(Version.getInstance().getNewProperty("testOrgId")));
				return org;
			}else{
				return null;
			}
		}
	}

	/**
	 * 对菜单入口处的分销编码进行解码
	 * @param reverseCode
	 * @return
	 * @throws Base64Exception 
	 */
	public String decodeFxCode(String reverseCode) throws Base64Exception {
		byte[] decode = Base64Utility.decode(StringUtils.reverse(reverseCode).substring(0, reverseCode.length()-10).replaceAll(",", "="));
		//转码后存放到session
		return new String(decode);
	}
	
	/**
	 * base64编码（有=号线替换成逗号）+随机10位字母; 然后倒序
	 * 生成加密的分销商代码， 即：Org.code
	 * @return
	 */
	public String encodeFxCode(String code){
//		从参数上传递过来的分销商编码是经过特定算法的： base64编码+随机10位字母; 然后倒序
		String encode = Base64Utility.encode(code.getBytes());
		//对分销商编号进行base64编码，然后附加10位随机编码； 最后再倒序返回
		String string = encode.replaceAll("=", ",") + SysUtil.getRandomCode(10);
		return StringUtils.reverse(string);
	}
	
	public static void main(String[] args){
//		String encode = Base64Utility.encode("001".getBytes());
		String encode = Base64Utility.encode("000001".getBytes());
		//对分销商编号进行base64编码，然后附加10位随机编码； 最后再倒序返回
		String string = encode.replaceAll("=", ",") + SysUtil.getRandomCode(10);
		System.out.println(StringUtils.reverse(string));
	}
	
}