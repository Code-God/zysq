package com.wfsc.actions.common;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.ServerBeanFactory;
import com.base.action.DispatchPagerAction;
import com.wfsc.services.account.IUserService;

/**
 * 公共模块ACTION：如交友攻略之类
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("PublicAction")
@Scope("prototype")
public class PublicAction extends DispatchPagerAction {

	private static final long serialVersionUID = 6831508664307084002L;

	private Logger logger = Logger.getLogger(PublicAction.class);

	@Resource(name = "userService")
	private IUserService userService;

	private File myFile;

	private String myFileFileName;

	/**
	 *  进入更多文章列表
	 * @return
	 * @throws IOException 
	 */
	public String subGonglueList() throws IOException{
		request.setAttribute("subforumId", request.getParameter("subforumId"));
		request.setAttribute("expert", request.getParameter("expert"));
		return "ok";
	}

//	/**
//	 * 新浪回调
//	 * 
//	 * @return
//	 * @throws IOException
//	 */
//	public String sina() {
//		try {
//			HttpServletRequest request = this.getRequest();
//			// // 获取授权码
//			String code = request.getParameter("code");
//			if (code == null) {// 去授权
//				// 跳转到授权页
//				this.getResponse().sendRedirect(CupidConstants.authUrl);
//				return null;
//			}
//			// 换取授权码的地址
//			// https://api.weibo.com/oauth2/access_token?client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET&grant_type=authorization_code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI&code=CODE
//			// String tokenUrl = MarkConstants.tokenUrl + "&code=" + code;
//			// 根据授权码和app secret获取访问令牌
//			Map<String, String> params = new HashMap<String, String>();
//			params.put("grant_type", "authorization_code");
//			params.put("client_id", CupidConstants.clientId);
//			// params.put("client_id", sub_appkey);//这里应该是sub_appkey
//			params.put("client_secret", CupidConstants.appSecret);//
//			params.put("redirect_uri", CupidConstants.callbackUrl);
//			params.put("code", code);
//			// //获取token 的值
//			String doPost = WebUtils.doPost(CupidConstants.tokenUrl, params, 3000, 3000);
//			JSONObject fromObject = JSONObject.fromObject(doPost);
//			String access_token = (String) fromObject.get("access_token");
//			System.out.println("访问令牌：： " + access_token);
//			// ===============================
//			// TODO 获取当前用户UID
//			// String getUidUrl = "https://api.weibo.com/2/account/get_uid.json";
//			// Map<String, String> ps = new HashMap<String, String>();
//			// ps.put("access_token", access_token);
//			// String doGet = WebUtils.doGet(getUidUrl, ps);
//			Account ac = new Account();
//			ac.setToken(access_token);
//			weibo4j.org.json.JSONObject uid = ac.getUid();
//			String loginUid = uid.getString("uid");
//			Users um = new Users();
//			um.client.setToken(access_token);
//			User user = um.showUserById(loginUid);
//			request.getSession().setAttribute(CupidConstants.SES_SINA_UID, loginUid);
//			request.getSession().setAttribute(CupidConstants.SES_USERTOKEN, access_token);
//			System.out.println("新浪用户ID==" + loginUid);
//			// 用微博用户ID和昵称自动注册一个账号
//			com.wfsc.common.bo.user.User autoRegister = autoRegister(user);
//			getSession().setAttribute(CupidStrutsConstants.SESSION_USER, autoRegister);
//			getSession().setAttribute(CupidStrutsConstants.SES_TOKEN, access_token);
//			getResponse().sendRedirect("/love/public/go_index.Q");
//			return null;
//		} catch (WeiboException e) {
//			e.printStackTrace();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}


//	public String importData() {
//		MigrationTool tool = new MigrationTool();
//		Connection conn = null;
//		Statement stmt = null;
//		String sqlstr = "select * from yyy";
//		int i = 1;
//		try {
//			conn = tool.getConnection();
//			stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery(sqlstr);
//			DecimalFormat df = new DecimalFormat("00");
//			Random random = new Random();
//			while (rs.next()) {
//				String sex = rs.getString("sex");
//				String id = rs.getString("id");
//				String name = rs.getString("name");
//				System.out.println(i + "----" + sex + "|" + id);
//				com.wfsc.common.bo.user.User user = new com.wfsc.common.bo.user.User();
//				user.setName(name);
//				if ("1".equals(sex)) {
//					user.setSex("0");
//				} else {
//					user.setSex("1");
//				}
//				user.setRealName(name);
//				user.setNickName(name);
//				user.setProvince("31");// 上海
//				// 随机地区分布
//				int nextInt = random.nextInt(28);
//				user.setCity("31" + df.format(nextInt == 0 ? 1 : nextInt));
//				int nextInt2 = random.nextInt(19);
//				user.setArea(nextInt2 == 0 ? "1" : nextInt2 + "");
//				// 0=中专 1=高中 2=大专 3=本科 4=硕士 5=博士 6=博士后 7=留学生 8=其它
//				String dip = rs.getString("education");
//				// diploma.put(0, "--请选择--");
//				// diploma.put(1, "初中");
//				// diploma.put(2, "高中/中专/职校");
//				// diploma.put(3, "大专");
//				// diploma.put(4, "本科");
//				// diploma.put(5, "硕士");
//				// diploma.put(6, "博士");
//				// diploma.put(7, "博士后");
//				if ("0".equals(dip) || "1".equals(dip)) {
//					user.setDiploma(2);
//				} else if ("2".equals(dip)) {
//					user.setDiploma(3);
//				} else if ("3".equals(dip)) {
//					user.setDiploma(4);
//				} else if ("4".equals(dip)) {
//					user.setDiploma(5);
//				} else if ("5".equals(dip)) {
//					user.setDiploma(6);
//				} else if ("6".equals(dip)) {
//					user.setDiploma(7);
//				} else {
//					user.setDiploma(0);
//				}
//				// 0=未婚 1=离异 2=丧偶
//				String marr = rs.getString("marry");
//				// 婚姻状况 0 - 未婚 2 - 离异(未育) 3 - 离异(有子女) 4 - 丧偶
//				if ("0".equals(marr)) {
//					user.setMarital(0);
//				} else if ("1".equals(marr)) {
//					user.setMarital(2);
//				} else if ("2".equals(marr)) {
//					user.setMarital(4);
//				}
//				user.setStatus(1);// 启用
//				String birth = rs.getString("birthday");
//				user.setBirthday(birth.substring(0, 10));
//				String hight = rs.getString("high");
//				if (StringUtils.isNotEmpty(hight) && StringUtils.isNotBlank(hight)) {
//					user.setHeight(Integer.valueOf(hight));
//				} else {
//					user.setHeight(0);
//				}
//				user.setDataType("IMPORT");// 自动导入的百合内网数据
//				user.setMasked("N");
//				user.setPass("N");//
//				user.setSrvLevel(2);
//				user.setPassword(id);
//				user.setAuthPass(true);
//				String d = rs.getString("date");
//				user.setRegistTime(DateUtil.getLongDate(d));
//				
//				//设置收入
//				String s = rs.getString("income");
//				int income = Integer.valueOf(s);
//				if(income < 2000){
//					user.setSalary(1);
//				}else if(income>=2000 && income < 3000){
//					user.setSalary(2);
//				}else if(income>=3000 && income < 4000){
//					user.setSalary(3);
//				}else if(income>=4000 && income < 5000){
//					user.setSalary(4);
//				}else if(income>=5000 && income < 6000){
//					user.setSalary(5);
//				}else if(income>=6000 && income < 7000){
//					user.setSalary(6);
//				}else if(income>=7000 && income < 8000){
//					user.setSalary(7);
//				}else if(income>=8000 && income < 9000){
//					user.setSalary(8);
//				}else if(income>=9000 && income < 10000){
//					user.setSalary(9);
//				}else if(income>=10000 && income < 15000){
//					user.setSalary(10);
//				}else if(income>=15000 && income < 20000){
//					user.setSalary(11);
//				}else if(income>=20000 && income < 25000){
//					user.setSalary(12);
//				}else if(income>=25000 && income < 30000){
//					user.setSalary(13);
//				}else if(income>=30000 && income < 50000){
//					user.setSalary(14);
//				}else if(income>=50000){
//					user.setSalary(15);
//				}
//				
//				userService.fastRegister(user);
//				i++;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.getRequest().setAttribute("info", "导入失败！" + e.getLocalizedMessage());
//			return "import";
//		} finally {
//			try {
//				stmt.close();
//				conn.close();
//			} catch (Exception e) {
//				System.err.println(e);
//			}
//		}
//		this.getRequest().setAttribute("info", "导入成功！已成功导入" + i + "条消息。");
//		return "import";
//	}
//
//	public String importPic() {
//		MigrationTool tool = new MigrationTool();
//		Connection conn = null;
//		Statement stmt = null;
//		String sqlstr = "select * from yyy";
//		String picPath = "D:\\所有有照片的会员";
//		String srcPath = "";
//		try {
//			conn = tool.getConnection();
//			stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery(sqlstr);
//			File srcDir = null;
//			while (rs.next()) {
//				String sex = rs.getString("sex");
//				String id = rs.getString("id");
//				String name = rs.getString("name");
//				String year = rs.getString("birthday").split("-")[0];
//				com.wfsc.common.bo.user.User userByName = userService.getUserByName(name);
//				// 根据birth，sex，id来确定源图片文件
//				if ("1".equals(sex)) {// 女
//					srcPath = picPath + "\\WoMan\\" + year + "年\\" + id;
//				} else if ("0".equals(sex)) {// 男
//					srcPath = picPath + "\\Man\\" + year + "年\\" + id;
//				}
//				srcDir = new File(srcPath);
//				// 这里只处理男生
//				if (srcDir.exists() && userByName != null && "0".equals(sex)) {
//					if (userByName.getPhotoName() == null || StringUtils.isEmpty(userByName.getPhotoName())) {
//						System.out.println(sex + "|" + srcPath);
//						copyPic(srcPath, srcDir, userByName);
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "import";
//	}
//
//	/**
//	 * 导入BH注册会员，通过解析html文件
//	 * 
//	 * @return
//	 */
//	public String importBhPublicData() {
//		int i = 1;
//		long t1 = System.currentTimeMillis();
//		try {
//			File f = new File("c:/output/");
//			File[] listFiles = f.listFiles();
//			System.out.println(listFiles.length);
//			for (File file : listFiles) {
//				System.out.println(i + " | 正在解析：" + file.getName() + ".....");
//				if(file.getName().equals("TTc4ib5fmaa19165")){
//					System.out.println(i + " | 正在解析：" + file.getName() + ".....");
//				}
//				if (file.getName().endsWith(".html")) {
//					com.wfsc.common.bo.user.User fillUser = MigrationTool1.fillUser(file);
//					if (fillUser != null) {
//						if (userService.getUserByName(fillUser.getName()) != null) {// 已存在
//							logger.info("用户登录名重复：" + fillUser.getName());
//							continue;
//						}else{
//							// 插入到数据库
//							userService.fastRegister(fillUser);
//							logger.info(i + "成功导入一条数据：" + fillUser.getName());
//							i++;
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.info("导入结束........耗时：" + (System.currentTimeMillis() - t1) + "ms");
//		this.getRequest().setAttribute("info", "导入成功！已成功导入" + i + "条用户数据。");
//		return "import";
//	}

	
	  

	private String getPicRootPath(HttpServletRequest request) {
		String rootPath = request.getSession().getServletContext().getRealPath("/swfupload/files/");
		File f = new File(rootPath);
		if (!f.exists()) {
			f.mkdirs();
		}
		return rootPath;
	}

	public static void main(String args[]) {
		// 得到本地的缺省格式
		DecimalFormat df1 = new DecimalFormat("####.000");
		System.out.println(df1.format(1234.56));
		// 得到德国的格式
		Locale.setDefault(Locale.GERMAN);
		DecimalFormat df2 = new DecimalFormat("0000");
		System.out.println(df2.format(123.56d));
		System.out.println(new Random().nextInt(28));
	}

	/**
	 * 取消授权
	 * 
	 * @return
	 */
	public String sinac() {
		return "ok";
	}

	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
