package actions.drug;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import model.bo.drug.DrugDiseaseDict;
import model.bo.drug.DrugDiseaseItem;
import model.bo.drug.DrugDrugs;
import model.bo.drug.DrugQualification;
import model.bo.drug.DrugScoreLog;
import model.bo.drug.DrugUDRelation;
import model.bo.food.ConfigParam;
import model.vo.WxUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.drug.IDrugMedicineService;
import service.drug.IDrugProjectService;
import service.drug.IDrugQualificationService;
import service.drug.IDrugScoreLogService;
import service.intf.AdminService;
import util.TwoDimensionCode;
import util.UploadUtil;
import actions.integ.weixin.WeiXinUtil;

import com.base.ServerBeanFactory;
import com.base.action.DispatchPagerAction;
import com.base.exception.CupidRuntimeException;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.base.util.Page;
import com.constants.CupidStrutsConstants;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.account.IUserService;
import com.wfsc.util.DateUtil;
import com.wfsc.util.HttpUtils;

/**
 * 我要报名|我要推荐action
 * 
 * @author miaodongming
 * 
 */
@Controller("DrugUserAction")
@Scope("prototype")
public class DrugUserAction extends DispatchPagerAction {

	private static final long serialVersionUID = -3503415849917552421L;

	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	private static final String SMS_URL_KEY = "sms_url";
	private static final String SMS_ACCOUNTL_KEY = "sms_account";
	private static final String SMS_PASSWORD_KEY = "sms_password";

	@Autowired
	private IUserService userService;

	@Autowired
	private IDrugProjectService drugProjectService;
	@Autowired
	private IDrugQualificationService drugQualificationService;
	@Autowired
	private IDrugMedicineService drugMedicineService;
	@Autowired
	private IDrugScoreLogService drugScoreLogService;

	/**
	 * 疾病（项目）编号
	 * 
	 */
	private Long diseaseId;

	/**
	 * 用户报名方式 0： 我要报名 1：我要推荐
	 */
	private String singupType;

	private User user;

	private String[] images;

	private File[] myFile;

	private String[] myFileContentType;

	private String[] myFileFileName;
	private File userPicfile;

	/**
	 * 进入我要报名页面
	 * 
	 * @return
	 */
	public String singup() {
		if (this.diseaseId == null) {
			return null;
		}
		// 根据疾病（项目）编号获取项目信息
		DrugDiseaseItem drugDiseaseItem = drugProjectService
				.getDrugDiseaseById(this.diseaseId);
		int userCount = drugProjectService
				.getDrugDiseaseCountById(this.diseaseId);// 报名人数
		
		request.setAttribute("projectId", drugDiseaseItem.getProjectId());
		// 根据项目编号获取药物
		// List<DrugDrugs> medicineList =
		// drugMedicineService.getDrugMedicineById(this.diseaseId);
		// if (medicineList != null && medicineList.size() > 0) {
		// StringBuffer sb = new StringBuffer();
		// for (DrugDrugs m : medicineList) {
		// sb.append(StringUtils.isBlank(sb.toString()) ? "" : ",");
		// sb.append(StringUtils.isBlank(m.getMedicineName()) ? "" :
		// m.getMedicineName());
		// }
		// request.setAttribute("medicineName", sb.toString());
		// }

		// 查询我的基本信息
		String openid = (String) request.getSession().getAttribute(
				CupidStrutsConstants.WXOPENID);
		 //String openid="oEAHRvm9HPkKHTbqJi_nZUxPEg1E";
		if (openid != null && !openid.equals("")) {
			User user = userService.getUserByOpenid(openid);
			if(user==null){
				user = new User();
				user.setOpenId(openid);
				user.setRegDate(new Date());
				user.setNickName("-");
				userService.saveOrUpdateEntity(user);
			}
			request.setAttribute("userinfo", user);
			if(user.getBirthDate()==null){
			  request.setAttribute("birthDate", "");
			}
			else{
				request.setAttribute("birthDate", new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthDate()));
			}
		}
		request.setAttribute("singupType", singupType);
		request.setAttribute("userCount", userCount);
		request.setAttribute("drugDiseaseItem", drugDiseaseItem);
		
		return "patientInfo";
	}

	/**
	 * 我要推荐页面
	 * 
	 * @return
	 */
	public String patientInfo() {
		String tempId = (String) request.getSession().getAttribute("diseaseId");
		if(tempId.equals("")||tempId==null){
			return null;
		}
		this.diseaseId = Long.parseLong(tempId);
		System.out.println("diseaseid--------"+this.diseaseId);
		if (this.diseaseId == null) {
			return null;
		}
		this.singupType = "1";

		// 根据疾病（项目）编号获取项目信息
		DrugDiseaseItem drugDiseaseItem = drugProjectService
				.getDrugDiseaseById(this.diseaseId);
		int userCount = drugProjectService
				.getDrugDiseaseCountById(this.diseaseId);// 报名人数
		// 根据项目编号获取药物
		// List<DrugDrugs> medicineList =
		// drugMedicineService.getDrugMedicineById(this.diseaseId);
		// if (medicineList != null && medicineList.size() > 0) {
		// StringBuffer sb = new StringBuffer();
		// for (DrugDrugs m : medicineList) {
		// sb.append(StringUtils.isBlank(sb.toString()) ? "" : ",");
		// sb.append(StringUtils.isBlank(m.getMedicineName()) ? "" :
		// m.getMedicineName());
		// }
		// request.setAttribute("medicineName", sb.toString());
		// }
		String openid = (String) request.getSession().getAttribute(
				CupidStrutsConstants.WXOPENID);
		if (openid == null || openid.equals("")) {
			openid = "ohXRbxJRAvMg3avhgMgfatRrQRdU";
			//openid="oEAHRvm9HPkKHTbqJi_nZUxPEg1E";
		}
		
		int isSubscrib = 0;
		
	
		User userByOpenId = userService.getUserByOpenid(openid);
		if(userByOpenId!=null){
			isSubscrib = userByOpenId.getSubstate();
		}
		isSubscrib = isSubscrib > 0 ? 1 : 0;
		System.out.println("projectid-----------"+drugDiseaseItem.getProjectId());
		request.setAttribute("projectId", drugDiseaseItem.getProjectId());

		request.setAttribute("diseaseId", this.diseaseId);
		request.setAttribute("singupType", this.singupType);
		request.setAttribute("userCount", userCount);
		request.setAttribute("drugDiseaseItem", drugDiseaseItem);
		request.setAttribute("birthDate", "");
		request.setAttribute("isSubscrib", isSubscrib);
		

		return "patientInfo";
	}

	/**
	 * 获得数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws IOException
	 */
	public String getCustomData() throws IOException {

		String projectId = request.getParameter("projectId");
		if (projectId != null&&!projectId.isEmpty()) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// 建立一个NameValuePair数组，用于存储欲传送的参数
			params.add(new BasicNameValuePair("projectid", projectId));
			params.add(new BasicNameValuePair("phase", "1"));

			String url = Version.getInstance().getNewProperty("datacenter_url")+"projectcondition?";
			url = url+"projectid="+projectId+"&phase=1&sortkey=ordernum&SortDire=asc";
			String doGet = HttpUtils.doDataCenterGet(url, params);

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.write(doGet);
		}
		return null;
	}
	
	/**
	 * 获得項目基礎数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws IOException
	 */
	public String getProjectData() throws IOException {

		String name = request.getParameter("name");

		String url = Version.getInstance().getNewProperty("datacenter_url")+"Base?type=8";
		
		if(name!=null&&!name.isEmpty()){
		   url=url+"&name="+name;
		}
		
		String doGet = HttpUtils.doDataCenterGet(url, null);
		
		doGet = "{query: \"name\",suggestions:"+
		doGet.replace("\"Id\"", "\"data\"").replace("\"Name\"", "\"value\"").concat("}");

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.write(doGet);
		
		return null;
	}
	
	/**
	 * 提交自定义表单数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws IOException
	 */
	public void postCustomData() throws IOException {

		int isSync =0;
		String syncStr = Version.getInstance().getNewProperty("datacenter_syncData");
		if(syncStr!=null){
			isSync = Integer.parseInt(syncStr);
		}
		if(isSync==0){
			System.out.println("------不提交自定义表单数据到数据中心------");
			return;
		}
		
		String projectId = request.getParameter("projectId");
		String cellphone = request.getParameter("user.mobilePhone");
		if (projectId != null&&!projectId.isEmpty()&&cellphone!=null&&!cellphone.isEmpty()) {
			
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 建立一个NameValuePair数组，用于存储欲传送的参数
		
		params.add(new BasicNameValuePair("phase", "1"));
		params.add(new BasicNameValuePair("cellphone", cellphone));
		
		Enumeration<String> enu = request.getParameterNames();
		
		while(enu.hasMoreElements()){
			String paraName = enu.nextElement();
			params.add(new BasicNameValuePair(paraName,request.getParameter(paraName)));
		}

		String url = Version.getInstance().getNewProperty("datacenter_url")+"customModel";

		String doGet = HttpUtils.doDataCenterPost(url, params,null);
		
		String classStr = JSONArray.fromObject(params).toString();
		System.out.println(classStr);
		System.out.println("-----提交自定义表单数据到数据中心----"+doGet);
		}
	}
	
	
	/**
	 * 同步患者数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws IOException
	 */
	public void SyncPatientDataPost(User loginUser,Integer mapProjectId) throws IOException {
        System.out.println(loginUser);
		int isSync =0;
		String syncStr = Version.getInstance().getNewProperty("datacenter_syncData");
		if(syncStr!=null){
			isSync = Integer.parseInt(syncStr);
		}
		if(isSync==0){
			System.out.println("------不同步患者数据到数据中心------");
			return;
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 建立一个NameValuePair数组，用于存储欲传送的参数
		params.add(new BasicNameValuePair("projectId",mapProjectId.toString()));//如果mapProjectId>0,则添加报名和患者阶段信息
		
		params.add(new BasicNameValuePair("mobile",loginUser.getMobilePhone()));
		params.add(new BasicNameValuePair("channelId", "1"));//5表示来自找药神器
		params.add(new BasicNameValuePair("fullname", loginUser.getRealName()));
		params.add(new BasicNameValuePair("sex", loginUser.getSex()));
		params.add(new BasicNameValuePair("cityname", loginUser.getCity()));
		params.add(new BasicNameValuePair("provincename", loginUser.getProvince()));
		params.add(new BasicNameValuePair("address", loginUser.getAddress()));
		params.add(new BasicNameValuePair("cityid", "781"));
		params.add(new BasicNameValuePair("provinceid", "218"));
		params.add(new BasicNameValuePair("districtId","1"));
		if(loginUser.getBirthDate()!=null){
			params.add(new BasicNameValuePair("Birthday", (new SimpleDateFormat("yyyy-MM-dd")).format(loginUser.getBirthDate())));//出生日期
		}
		params.add(new BasicNameValuePair("reportDate", (new SimpleDateFormat("yyyy-MM-dd")).format(new Date())));//报名时间
		
		logger.info("=====================同步用户数据参数：："+params);

		String url = Version.getInstance().getNewProperty("datacenter_url")+"patient/sync";

		String doGet = HttpUtils.doDataCenterPost(url, params,null);
		
//		String classStr = JSONObject.fromObject(loginUser).toString();
//		System.out.println(classStr);
		System.out.println("-----同步用户数据----"+doGet);
	}


	/**
	 * 我要推荐，中间页面，介绍药物的好处
	 * 
	 * @return
	 */
	public String medicineInfo() {
		if (this.diseaseId == null) {
			
			return null;
		}

		String openid = (String) request.getSession().getAttribute(
				CupidStrutsConstants.WXOPENID);
		if (openid == null || openid.equals("")) {
			openid = "ohXRbxJRAvMg3avhgMgfatRrQRdU";
			//openid="oEAHRvm9HPkKHTbqJi_nZUxPEg1E";
		}
		
//		if(!openid.equals("oEAHRvm9HPkKHTbqJi_nZUxPEg1E")){
//		// 根据疾病（项目）编号获取项目信息
//		DrugDiseaseItem drugDiseaseItem = drugProjectService
//				.getDrugDiseaseById(this.diseaseId);
//		// 根据项目编号获取药物
//		List<DrugDrugs> medicineList = drugMedicineService
//				.getDrugMedicineById(this.diseaseId);
//		if (medicineList != null && medicineList.size() > 0) {
//			StringBuffer sb = new StringBuffer();
//			for (DrugDrugs m : medicineList) {
//				sb.append(StringUtils.isBlank(sb.toString()) ? "" : ",");
//				sb.append(StringUtils.isBlank(m.getMedicineName()) ? "" : m
//						.getMedicineName());
//			}
//			request.setAttribute("medicineName", sb.toString());
//		}
//
//		request.setAttribute("diseaseId", this.diseaseId);
//		request.setAttribute("singupType", this.singupType);
//		request.setAttribute("drugDiseaseItem", drugDiseaseItem);
//	
//		return "medicineInfo";
//		}
		
		try {
			
			String userid = "";
			
			User user = userService.getUserByOpenid(openid);
			if (user != null) {
				userid = user.getId().toString();
			}
			if (userid != null && !userid.equals("")) {
				String saveDir = UploadUtil.getSaveUrl()
						+ UploadUtil.UPLOADSIMAGES + "\\QR_code_temp";
				String picname = userid + "_" + this.diseaseId.toString() + ".png";
				String picSavePath = saveDir + File.separator + picname; // 保存路径
				System.out.println(picSavePath);
				File fileDirect = new File(saveDir);
				if (!fileDirect.exists()) {
					// 该目录不存在，则创建目录
					fileDirect.mkdirs();
				}
				// 二维码不存在，需要生成二维码
				File file = new File(picSavePath);
				if (!file.exists()) {
					TwoDimensionCode code = new TwoDimensionCode();
					AdminService adminService = (AdminService) ServerBeanFactory
							.getBean("adminService");
					String appId = adminService
							.getConfigParam(ConfigParam.APPID);
					String redirect_uri = adminService
							.getConfigParam(ConfigParam.WX_SERVER_CTX);
					String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
							+ appId
							+ "&redirect_uri="
							+ URLEncoder.encode(redirect_uri, "UTF-8")
							+ "?userIdStr"
							+ "="
							+ userid+"|"+this.diseaseId
							+ "&response_type=code&scope=snsapi_userinfo&state=79#wechat_redirect";
					code.encoderQRCode(url, picSavePath, "png", 15);
				}
				
				// 保存二维码路径
//				user.setTwoDimensionCode(picSavePath.replace(
//						UploadUtil.getSaveUrl(), ""));
//				userService.saveOrUpdateEntity(user);
				//System.out.println(UploadUtil.getImgUrl());
				//request.setAttribute("qr_code",
						//UploadUtil.getImgUrl() + user.getTwoDimensionCode());
				//System.out.println(UploadUtil.getSaveUrl());
				//System.out.println(picSavePath.replace(UploadUtil.getSaveUrl(), ""));
				request.setAttribute("qr_code",
						UploadUtil.getImgUrl() + picSavePath.replace(UploadUtil.getSaveUrl(), ""));
			}
			else{
				request.setAttribute("qr_code",null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "qrCode";
		
	}

	/**
	 * 获取短信验证
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getVerificationCode() throws IOException, Exception {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession();

		String postUrl = Version.getInstance().getProperty(SMS_URL_KEY);

		int code = (int) ((Math.random() * 9 + 1) * 100000);

		String account = Version.getInstance().getProperty(SMS_ACCOUNTL_KEY);
		String password = Version.getInstance().getProperty(SMS_PASSWORD_KEY);
		String mobile = request.getParameter("mobile");
		String content = new String("您的校验码是：" + code + "。请不要把校验码泄露给其他人。");

		if (StringUtils.isBlank(postUrl)) {

			throw new Exception("短信接口未配置！");
		}

		if (StringUtils.isBlank(mobile)) {
			throw new Exception("手机号码为空，不能发送验证码！");
		}

		int flag = 0;
		try {
			URL url = new URL(postUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);// 允许连接提交信息
			connection.setRequestMethod("POST");// 网页提交方式“GET”、“POST”
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("Connection", "Keep-Alive");
			StringBuffer sb = new StringBuffer();
			sb.append("account=" + account);
			sb.append("&password=" + password);
			sb.append("&mobile=" + mobile);
			sb.append("&content=" + content);
			OutputStream os = connection.getOutputStream();
			os.write(sb.toString().getBytes());
			os.close();

			String line, result = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));
			while ((line = in.readLine()) != null) {
				result += line + "\n";
			}
			in.close();
			logger.info(result);

			// StringBuffer result = new StringBuffer();
			// result.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
			// result.append("<SubmitResult xmlns=\"http://121.199.16.178/\">");
			// result.append("<code>2</code>");
			// result.append("<msg>提交成功</msg>");
			// result.append("<smsid>403320248</smsid>");
			// result.append("</SubmitResult>");
			Map<String, String> map = this.parseXmlData(result.toString());
			String resultCode = map.get("code");
			if (StringUtils.isNotBlank(resultCode) && "2".equals(resultCode)) {
				logger.info("成功获取到短信验证码");
				// 将验证码存入SESSION
				session.setAttribute(
						CupidStrutsConstants.MOBILE_VERIFICATION_CODE,
						String.valueOf(code));
				flag = 1;
			} else {
				System.out.println("code errow");
				logger.info("获取短信验证码异常:" + map.get("msg"));
			}

			/*
			 * // 将验证码存入SESSION
			 * session.setAttribute(CupidStrutsConstants.MOBILE_VERIFICATION_CODE
			 * , String.valueOf(code)); flag = 1;
			 */

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获取短信验证码异常:" + e.getMessage());
		}

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		jo.put("flag", "" + flag);
		jo.put("code", "" + code);
		out.print(jo.toString());

		return null;
	}

	/**
	 * 解析短信接口返回参数
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> parseXmlData(String xml) {
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			SAXReader sax = new SAXReader();
			byte[] bytes = xml.toString().getBytes();
			InputStream in = new ByteArrayInputStream(bytes);
			InputStreamReader strInStream = new InputStreamReader(in, "GBK");
			// 获得dom4j的文档对象
			Document root = sax.read(strInStream);
			Element rootEle = root.getRootElement();
			// Element rootEle =
			// (Element)root.selectSingleNode("//SubmitResult");
			List<Element> elements = rootEle.elements();
			for (Element ele : elements) {
				// System.out.println(ele.getName() +"==========="+
				// ele.getText());
				dataMap.put(ele.getName(), ele.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
	}

	/**
	 * 验证手机号是否存在
	 * 
	 * @return
	 * @throws IOException
	 */
	public String mobileIsExist() throws IOException {
		int flag = 1;
		try {
			String mobile = request.getParameter("mobile");
			User user = this.userService.getUserByMobile(mobile);
			if (user == null) {
				flag = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = -1;
		}

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		jo.put("flag", "" + flag);
		out.print(jo.toString());

		return null;
	}

	/**
	 * 保存患者信息
	 * 
	 * @return
	 * @throws IOException
	 */
	public String savePatientInfo() throws IOException {
		int flag = 0;
		try {
			boolean isSave = true;
			boolean isexist = true;
			String referrerId;// 扫描二维码进入，推荐人的id
			String openid = (String) request.getSession().getAttribute(
					CupidStrutsConstants.WXOPENID);
			
//			String openid="oEAHRvneDnARQWfvf-lfctl69Y0M";
//			String openid="oEAHRvm9HPkKHTbqJi_nZUxPEg1E"; //测试数据
			
			User loginUser = userService.getUserByOpenid(openid);
			HttpSession session = request.getSession();
			
			String mapProjectId = request.getParameter("mapProjectId");
			//测试数据
//			mapProjectId = "242";

			if (StringUtils.isNotBlank(this.singupType)
					&& "0".equals(this.singupType)) { // ******** 我要报名 *******
				// 验证码校验
				String sessionVerificationCode = (String) session
						.getAttribute(CupidStrutsConstants.MOBILE_VERIFICATION_CODE);
				String verificationCode = request
						.getParameter("verificationCode");
				System.out.println("--------------------sessioncode:"
						+ sessionVerificationCode + ",mobileCode:"
						+ verificationCode);
				if (StringUtils.isBlank(sessionVerificationCode)
						|| StringUtils.isBlank(verificationCode)
						|| !verificationCode.equals(sessionVerificationCode)) {
					// 验证码不正确
					flag = 1;
					isSave = false;
				}
				// -------
				// if(!isexist){
				// 手机号注册唯一性校验
				// User existUser =
				// this.userService.getUserByMobile(user.getMobilePhone());
				// if (existUser != null) {
				// flag = 3;
				// isSave = false;
				// }
				// }
				
				isexist = drugProjectService.isExist(loginUser.getId()
						.toString(), diseaseId.toString());
				//测试数据
//				isexist = false;
				if (isexist) {
					flag = 3;
					isSave = false;
				}

				if (isSave) {
					// Date date = new Date();
					// user.setPassword("MTExMTExMTE=");
					// user.setRegDate(date);
					// user.setRegistrationDate(date);
					// user.setStatus(1);
					// user.setUserType(3);
					// 报名用户的openId
					if (loginUser != null) {
						loginUser.setRealName(user.getRealName());
						loginUser.setMobilePhone(user.getMobilePhone());
						loginUser.setSex(user.getSex());
						loginUser.setBirthDate(user.getBirthDate());
						loginUser.setProvince(user.getProvince());
						loginUser.setCity(user.getCity());
						loginUser.setAddress(user.getAddress());
						loginUser.setMedicalHistoryDescription(user
								.getMedicalHistoryDescription());
					}
					// 报名用户关联的项目
					DrugUDRelation drugUDRelation = new DrugUDRelation();
					drugUDRelation.setDiseaseId(this.diseaseId);
					// 扫描二维码进入，填写我要报名，获取二维码上推荐人userId
					referrerId = (String) session
							.getAttribute(CupidStrutsConstants.REFERRER_USER_ID);
					if (StringUtils.isNotBlank(referrerId)
							&& !referrerId.equals("null")) {
						drugUDRelation.setReferrerId(Long.valueOf(referrerId));
					}

					// 病历附件上传的路径(zysq/attachments/medical_history/)
					String tempFilePath = this.getAbsoluteRootPath()
							+ "attachments" + java.io.File.separator
							+ "medical_history" + java.io.File.separator
							+ "temp" + java.io.File.separator;
					String saveFilePath = this.getAbsoluteRootPath()
							+ "attachments" + java.io.File.separator
							+ "medical_history" + java.io.File.separator;
					loginUser.setAge(0);
					if(loginUser.getPoints()==null){
						loginUser.setPoints(0);
					}
					this.userService.saveUser(loginUser, images, tempFilePath,
							saveFilePath, drugUDRelation, singupType);
					
					if(mapProjectId==null||mapProjectId.length()==0){
						mapProjectId = "0";
					}
					System.out.println("sync----------");
					this.SyncPatientDataPost(loginUser,Integer.parseInt(mapProjectId));
				}

			} else { // ******* 我要推荐 *******
				// 验证码校验
				String sessionVerificationCode = (String) session
						.getAttribute(CupidStrutsConstants.MOBILE_VERIFICATION_CODE);
				String verificationCode = request
						.getParameter("verificationCode");
				System.out.println("--------------------sessioncode:"
						+ sessionVerificationCode + ",mobileCode:"
						+ verificationCode);
				if (StringUtils.isBlank(sessionVerificationCode)
						|| StringUtils.isBlank(verificationCode)
						|| !verificationCode.equals(sessionVerificationCode)) {
					// 验证码不正确
					flag = 1;
					isSave = false;
				}
				
				if(loginUser != null){
					isexist = drugProjectService.isExist(loginUser.getId()
							.toString(), diseaseId.toString());
					if (isexist) {
						flag = 3;
						isSave = false;
					}
				}
				
				
				if (isSave) {
					
					if (loginUser != null) {
						loginUser.setRealName(user.getRealName());
						loginUser.setMobilePhone(user.getMobilePhone());
						loginUser.setSex(user.getSex());
						loginUser.setBirthDate(user.getBirthDate());
						loginUser.setProvince(user.getProvince());
						loginUser.setCity(user.getCity());
						loginUser.setAddress(user.getAddress());
						loginUser.setMedicalHistoryDescription(user
								.getMedicalHistoryDescription());
					}
					else{
						Date date = new Date();
						user.setPassword("MTExMTExMTE=");
						user.setRegDate(date);
						user.setRegistrationDate(date);
						user.setStatus(1);
						user.setUserType(3);
						user.setAge(0);
					}
				// 推荐用户、项目及推荐人之间的关联信息
				DrugUDRelation drugUDRelation = new DrugUDRelation();
				drugUDRelation.setDiseaseId(this.diseaseId);
				drugUDRelation.setSignupDate(new Date());
				// 扫描二维码进入，填写我要报名，获取二维码上推荐人userId
				referrerId = (String) session
						.getAttribute(CupidStrutsConstants.REFERRER_USER_ID);
				if (StringUtils.isNotBlank(referrerId)
						&& !referrerId.equals("null")) {
					drugUDRelation.setReferrerId(Long.valueOf(referrerId));
				}

				// 病历附件上传的路径(zysq/attachments/medical_history/)
				String tempFilePath = this.getAbsoluteRootPath()
						+ "attachments" + java.io.File.separator
						+ "medical_history" + java.io.File.separator + "temp"
						+ java.io.File.separator;
				String saveFilePath = this.getAbsoluteRootPath()
						+ "attachments" + java.io.File.separator
						+ "medical_history" + java.io.File.separator;
				if(loginUser!=null){
					if(loginUser.getPoints()==null){
						loginUser.setPoints(0);
					}
					this.userService.saveUser(loginUser, images, tempFilePath,
							saveFilePath, drugUDRelation, singupType);
				}
				else{
					if(user.getPoints()==null){
						user.setPoints(0);
					}
				this.userService.saveUser(user, images, tempFilePath,
						saveFilePath, drugUDRelation, singupType);
				}
				if(mapProjectId==null||mapProjectId.length()==0){
					mapProjectId = "0";
				}
				
				this.SyncPatientDataPost(user,Integer.parseInt(mapProjectId));
			}
		  }
		} catch (Exception e) {
			flag = 2;
			e.printStackTrace();
			System.out.println("ex----------"+e);
			logger.error("保存用户信息异常：" + e.getMessage());
		}

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		jo.put("flag", "" + flag);
		out.print(jo.toString());

		return null;
	}

	/**
	 * 上传病历附件(用户选择后立即上传，一次一个文件)
	 * 
	 * @return
	 * @throws IOException
	 */
	public String uploadMedicalHistory() throws IOException {
		int flag = 0;
		String imgId = null;
		String imgName = null;

		if (myFile != null && myFile.length > 0) {
			User loginUser = (User) request.getSession().getAttribute(
					CupidStrutsConstants.SESSION_USER);
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try {
				String fileExt = myFileFileName[0];
				fileExt = fileExt.substring(fileExt.lastIndexOf("."))
						.toLowerCase();
				// 只能上传文件，过滤不可上传的文件类型
				String fileFilt = ".jpg|.png";
				if (fileFilt.indexOf(fileExt) <= -1) {
					flag = 1;
				}

				// 判断文件大小,不能超过3M
				long length = myFile[0].length();
				if (length > 3 * 1048576) {
					flag = 2;
				}

				if (flag == 0) {
					String saveFilePath = this.getAbsoluteRootPath()
							+ "attachments" + java.io.File.separator
							+ "medical_history" + java.io.File.separator
							+ "temp" + java.io.File.separator;
					File file = new File(saveFilePath);
					if (!file.exists()) {
						file.mkdirs();
					}

					// 为保证文件不被覆盖，重新生成唯一的文件名：当前登录用户ID-当前时间毫秒数-上传文件名
					String uid = "";
					if (loginUser != null) {
						uid = "" + loginUser.getId();
					}
					imgId = uid + "_" + System.currentTimeMillis() + "_img";
					imgName = imgId + "_" + myFileFileName[0];
					file = new File(saveFilePath + imgName);
					if (!file.exists()) {
						file.createNewFile();
					}
					fos = new FileOutputStream(file);
					fis = new FileInputStream(myFile[0]);
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.flush();
					fos.close();
					fis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				flag = 3;
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
				} catch (Exception e) {
					throw new CupidRuntimeException(e.getMessage());
				}
				try {
					if (fis != null) {
						fis.close();
					}
				} catch (Exception e) {
					throw new CupidRuntimeException(e.getMessage());
				}
			}
		}

		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		/*
		 * JSONObject jo = new JSONObject(); jo.put("flag", ""+flag);
		 * jo.put("imgId", imgId); jo.put("imgName", imgName);
		 * out.print(jo.toString());
		 */
		out.print("<script>parent.showMedicalHistoryImg(" + flag + ", \""
				+ imgId + "\", \"" + imgName + "\");</script>");

		return null;
	}

	/**
	 * 用户删除上传的病历附件
	 * 
	 * @return
	 * @throws IOException
	 */
	public String deleteTempFile() throws IOException {
		try {
			String fileName = request.getParameter("imgName");
			if (StringUtils.isNotBlank(fileName)) {
				// 病历附件上传的临时路径(zysq/attachments/medical_history/temp/)
				String saveFilePath = this.getAbsoluteRootPath()
						+ "attachments" + java.io.File.separator
						+ "medical_history" + java.io.File.separator + "temp"
						+ java.io.File.separator;
				File file = new File(saveFilePath + fileName);
				if (file.exists()) {
					file.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 完善个人信息
	 * 
	 * @return
	 */
	public String perfectUserInfo() {
		String userid = request.getParameter("userid");
		if(userid==null||userid.length()==0){
			String openid = (String) request.getSession().getAttribute(
					CupidStrutsConstants.WXOPENID);
			 //String openid="oEAHRvneDnARQWfvf-lfctl69Y0M";
			if (openid != null && !openid.equals("")){
				User user = new User();
				user.setOpenId(openid);
				user.setRegDate(new Date());
				user.setNickName("-");
				userService.saveOrUpdateEntity(user);
				userid= user.getId().toString();
				System.out.println("-------新的userid---------"+userid);
			}
		}
		if (userid != null && !userid.equals("")) {
			User user = userService.getUserById(Long.valueOf(userid));
			String userpic = user.getUserPic();
			if (userpic != null && !userpic.equals("")) {
				if (userpic.indexOf("userpic") > 0) {
					// 不是微信头像，要读取头像的完整路径
					userpic = UploadUtil.getImgUrl() + user.getUserPic();
				} else {
					userpic = user.getUserPic();
				}
			} else {
				userpic = user.getHeadimgurl();// 没有上传过图片的获取微信默认图片
			}
			user.setUserPic(userpic);
			request.setAttribute("userinfo", user);
			if(user.getBirthDate()==null){
				  request.setAttribute("birthDate", "");
				}
				else{
					request.setAttribute("birthDate", new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthDate()));
				}
		}
		
		return "perfectUserInfo";
	}

	/**
	 * 保存个人完善资料
	 * 
	 * @return
	 */
	public String savePerfectUserInfo() {
		try {
			String realName = request.getParameter("realName");
			String mobilePhone = request.getParameter("mobilePhone");
			String sex = request.getParameter("sex");
			String birthDate = request.getParameter("birthDate");
			String province = request.getParameter("province");
			String city = request.getParameter("city");
			String address = request.getParameter("address");
			String hospital = request.getParameter("hospital");
			String department = request.getParameter("department");
			String userid = request.getParameter("userid");
			String picpath = request.getParameter("picpath");
			String position = request.getParameter("position");
			if (userid != null && !userid.equals("")) {
				User user = userService.getUserById(Long.valueOf(userid));
				user.setRealName(realName);
				user.setMobilePhone(mobilePhone);
				user.setSex(sex);
				user.setBirthDate(DateUtil.getDateFromShort(birthDate));
				user.setProvince(province);
				user.setCity(city);
				user.setAddress(address);
				user.setHospital(hospital);
				user.setDepartment(department);
				user.setPosition(position);
				if (picpath != null && !picpath.equals("")) {
					user.setUserPic(picpath);
				} else {
					user.setUserPic(user.getHeadimgurl());
				}
				// 第一次完善个人信息，添加积分
				// 审核通过添加积分1000
				DrugScoreLog score = new DrugScoreLog();
				// 判断是否已经添加过积分
				boolean isexist = drugScoreLogService.isExsit(user.getOpenId(),
						"4");

				if (!isexist) {
					// 不存在时，并且是审核通过的状态，加分
					score.setOpenId(user.getOpenId());
					score.setAction(IDrugScoreLogService.ACTION_ADD);
					score.setScore(IDrugScoreLogService.SCORE_PERFECT_INFO);
					score.setSource(IDrugScoreLogService.SOURC_ACTION_PERFECT_INFO);
					score.setOpdate(new Date());
					drugScoreLogService.saveOrUpdateDrugScore(score);
					user.setPoints(user.getPoints()
							+ IDrugScoreLogService.SCORE_PERFECT_INFO);
				}
				user.setAge(0);
				userService.saveOrUpdateEntity(user);
				
				this.SyncPatientDataPost(user,0);

				response.getWriter().write("true");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 上传认证用户头像
	 * 
	 * @return
	 */
	public String uploadUserpic() {
		try {
			response.setContentType("text/html");
			String picpath = "";
			if (userPicfile != null) {
				picpath = UploadUtil.upLoadImage(userPicfile, "userpic");
			}
			response.getWriter().write(picpath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查看用户找药二维码
	 * 
	 * @return
	 */
	public String userQRCode() {
		try {
			String userid = request.getParameter("userid");
			if (userid != null && !userid.equals("")) {
				User user = userService.getUserById(Long.valueOf(userid));
				String qrCode = user.getTwoDimensionCode();
				if (qrCode == null || qrCode.equals("")) {
					AdminService adminService = (AdminService) ServerBeanFactory
							.getBean("adminService");
					String appId = adminService
							.getConfigParam(ConfigParam.APPID);
					String redirect_uri = adminService
							.getConfigParam(ConfigParam.WX_SERVER_CTX);
					String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
							+ appId
							+ "&redirect_uri="
							+ URLEncoder.encode(redirect_uri, "UTF-8")
							+ "?"
							+ CupidStrutsConstants.REFERRER_USER_ID
							+ "="
							+ userid
							+ "&response_type=code&scope=snsapi_userinfo&state=79#wechat_redirect";
					// String
					// saveDir="D:\\"+UploadUtil.UPLOADSIMAGES+"\\"+"QR_code";
					String saveDir = UploadUtil.getSaveUrl()
							+ UploadUtil.UPLOADSIMAGES + "\\QR_code";
					String picname = UUID.randomUUID().toString() + ".png";
					String picSavePath = saveDir + File.separator + picname; // 保存路径
					System.out.println(picSavePath);
					File fileDirect = new File(saveDir);
					if (!fileDirect.exists()) {
						// 该目录不存在，则创建目录
						fileDirect.mkdirs();
					}
					// 二维码不存在，需要生成二维码
					TwoDimensionCode code = new TwoDimensionCode();
					String content = url;
					System.out.println(content);
					code.encoderQRCode(content, picSavePath, "png", 20);
					// 保存二维码路径
					user.setTwoDimensionCode(picSavePath.replace(
							UploadUtil.getSaveUrl(), ""));
					userService.saveOrUpdateEntity(user);
				}
				System.out.println(UploadUtil.getImgUrl());
				request.setAttribute("qr_code",
						UploadUtil.getImgUrl() + user.getTwoDimensionCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "qrCode";
	}

	/**
	 * 用户个人中心(我的)
	 * 
	 * @return
	 */

	public String userCenter() {
		try {
			String openid = (String) request.getSession().getAttribute(
					CupidStrutsConstants.WXOPENID);
			if (openid == null || openid.equals("")) {
				openid = "ohXRbxJRAvMg3avhgMgfatRrQRdU";
				//openid="oEAHRvneDnARQWfvf-lfctl69Y0M";
			}

			User user = userService.getUserByOpenid(openid);
			if (user != null) {
				// 与用户有关的项目
				List relationlist = drugProjectService
						.getRelationDrugProjects(user.getId().toString());
				String userpic = user.getUserPic();// 用户自己上传图像
				if (userpic != null && !userpic.equals("")) {
					if (userpic.indexOf("userpic") > 0) {
						userpic = UploadUtil.getImgUrl() + user.getUserPic();
					} else {
						userpic = user.getUserPic();
					}
				} else {
					// 微信头像
					userpic = user.getHeadimgurl();
				}

				user.setUserPic(userpic);
				request.setAttribute("userinfo", user);
				request.setAttribute("relationlist", relationlist);
				request.setAttribute("userid", user.getId());

				// 查看资质认证信息
				DrugQualification qualification = drugQualificationService
						.getQualificationByUserId(user.getId().toString());
				request.setAttribute("qualification", qualification);

				// 查看推荐的患者信息(首页显示一条)
				List recommendlist = userService.getRecommendByUserid(user
						.getId().toString());
				if (recommendlist != null && recommendlist.size() > 0) {
					request.setAttribute("recommend", recommendlist.get(0));
				}

				// 查看个人主动报名的信息信息(如果有数据只有一条)
				List activelist = userService.getActiveEnteredUserid(user
						.getId().toString());
				if (activelist != null && activelist.size() > 0) {
					request.setAttribute("active", activelist.get(0));
				}

				// 查看个人积分
				Integer score = drugScoreLogService
						.getTotalScoreByOpenid(openid);
				request.setAttribute("score", score);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "userCenter";
	}

	/**
	 * 查看更多（我推荐的患者）
	 * 
	 * @return
	 */
	public String getMoreRecommendList() {
		String userid = request.getParameter("userid");
		// 查看推荐的患者信息(首页显示一条)
		List recommendlist = userService.getRecommendByUserid(userid);
		request.setAttribute("recommendlist", recommendlist);
		return "moreRecommend";
	}

	/**
	 * 报名人员
	 * 
	 * @return
	 */
	public String relationManager() {
		getList();
		if (request.getParameter("pop") != null) {
			request.setAttribute("pop", "1");
		}
		return "relationManager";

	}

	/**
	 * 报名人员列表
	 * 
	 * @return
	 */
	public String getList() {
		try {
			Page<Object> page = new Page<Object>();
			Map<String, Object> paramap = new HashMap<String, Object>();
			this.setPageParams(page);
			page.setPaginationSize(7);
			String realname = request.getParameter("realname");
			if (StringUtils.isNotEmpty(realname)) {
				paramap.put("realname", realname);
				request.setAttribute("realname", realname);
			}

			String diseaseName = request.getParameter("diseaseName");
			if (StringUtils.isNotEmpty(diseaseName)) {
				paramap.put("diseaseName", diseaseName);
				request.setAttribute("diseaseName", diseaseName);
			}
			String auditStatus = request.getParameter("auditStatus");
			if (StringUtils.isNotEmpty(auditStatus)
					&& !auditStatus.equals("100")) {
				paramap.put("auditStatus", auditStatus);
				request.setAttribute("auditStatus", auditStatus);
			}
			page = drugProjectService.findPageForRelation(page, paramap);
			List<Integer> li = page.getPageNos();
			String listUrl = request.getContextPath()
					+ "/admin/drugUDRelation_getList.Q";
			request.setAttribute("listUrl", listUrl);
			request.setAttribute("page", page);
			request.setAttribute("li", li);
			request.setAttribute("obj", page.getData());
			request.setAttribute("pop", request.getParameter("pop"));
			List objlist = page.getData();
			for (int i = 0; i < objlist.size(); i++) {
				System.out.println("===>"+objlist.get(i).toString());

			}
			System.out.println(page.getData());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "relationList";
	}

	/**
	 * 邀请人管理
	 * 
	 * @return
	 */
	public String inviterManager() {
		getInviterList();
		if (request.getParameter("pop") != null) {
			request.setAttribute("pop", "1");
		}
		return "inviterManager";
	}

	/**
	 * 邀请人列表
	 * 
	 * @return
	 */
	public String getInviterList() {
		try {
			Page<Object> page = new Page<Object>();
			Map<String, Object> paramap = new HashMap<String, Object>();
			this.setPageParams(page);
			page.setPaginationSize(7);
			String realname = request.getParameter("realname");
			if (StringUtils.isNotEmpty(realname)) {
				paramap.put("realname", realname);
				request.setAttribute("realname", realname);
			}

			String nickname = request.getParameter("nickname");
			if (StringUtils.isNotEmpty(nickname)) {
				paramap.put("nickname", nickname);
				request.setAttribute("nickname", nickname);
			}

			page = userService.getAllInviterList(page, paramap);
			List<Integer> li = page.getPageNos();
			String listUrl = request.getContextPath()
					+ "/admin/drugUDRelation_getInviterList.Q";
			request.setAttribute("listUrl", listUrl);
			request.setAttribute("page", page);
			request.setAttribute("li", li);
			request.setAttribute("obj", page.getData());
			request.setAttribute("pop", request.getParameter("pop"));
			List objlist = page.getData();
			for (int i = 0; i < objlist.size(); i++) {
				System.out.println(objlist.get(i).toString());

			}
			System.out.println(page.getData());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "inviterList";
	}

	/**
	 * 通过用户id查询该邀请人邀请的所有人列表
	 * 
	 * @return
	 */
	public String getBeInvitedManager() {
		try {
			if (request.getParameter("pop") != null) {
				request.setAttribute("pop", "1");
			}
			getBeInvitedList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "beInvitedManager";
	}

	/**
	 * 通过用户id查询该邀请人邀请的所有人列表
	 * 
	 * @return
	 */
	public String getBeInvitedList() {
		try {
			Page<Object> page = new Page<Object>();
			Map<String, Object> paramap = new HashMap<String, Object>();
			this.setPageParams(page);
			page.setPaginationSize(7);

			String userid = request.getParameter("userid");
			page = userService.getBeInvitedListByUserid(page,
					Integer.valueOf(userid));
			List<Integer> li = page.getPageNos();
			String listUrl = request.getContextPath()
					+ "/admin/drugUDRelation_getBeInvitedList.Q?userid="
					+ userid;
			request.setAttribute("listUrl", listUrl);
			request.setAttribute("page", page);
			request.setAttribute("li", li);
			request.setAttribute("obj", page.getData());
			request.setAttribute("pop", request.getParameter("pop"));
			List objlist = page.getData();
			for (int i = 0; i < objlist.size(); i++) {
				System.out.println(objlist.get(i).toString());

			}
			System.out.println(page.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "beInvitedList";
	}

	/**
	 * 推荐人员(后台管理)
	 */
	public String recommendedManager() {
		getRecommendedList();
		if (request.getParameter("pop") != null) {
			request.setAttribute("pop", "1");
		}
		return "recommendedManager";

	}

	/**
	 * 推荐人员列表(后台管理)
	 * 
	 * @return
	 */

	public String getRecommendedList() {
		try {
			Page<Object> page = new Page<Object>();
			Map<String, Object> paramap = new HashMap<String, Object>();
			this.setPageParams(page);
			page.setPaginationSize(7);
			String realname = request.getParameter("realname");
			if (StringUtils.isNotEmpty(realname)) {
				paramap.put("realname", realname);
				request.setAttribute("realname", realname);
			}
			page = drugProjectService.findPageForRecommendedPerson(page,
					paramap);
			List<Integer> li = page.getPageNos();
			String listUrl = request.getContextPath()
					+ "/admin/drugUDRelation_getRecommendedList.Q";
			request.setAttribute("listUrl", listUrl);
			request.setAttribute("page", page);
			request.setAttribute("li", li);
			request.setAttribute("obj", page.getData());
			request.setAttribute("pop", request.getParameter("pop"));
			List objlist = page.getData();
			for (int i = 0; i < objlist.size(); i++) {
				System.out.println(objlist.get(i).toString());

			}
			System.out.println(page.getData());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "recommendedList";

	}

	/**
	 * 推荐用户(后台管理)
	 */
	public String usersManager() {
		getUsersList();
		if (request.getParameter("pop") != null) {
			request.setAttribute("pop", "1");
		}
		return "usersManager";

	}

	/**
	 * 推荐用户列表(后台管理)
	 * 
	 * @return
	 */
	public String getUsersList() {
		try {
			Page<Object> page = new Page<Object>();
			Map<String, Object> paramap = new HashMap<String, Object>();
			this.setPageParams(page);
			page.setPaginationSize(7);
			String realname = request.getParameter("realname");
			String referrerId = request.getParameter("referrerId");
			if (StringUtils.isNotEmpty(realname)) {
				paramap.put("realname", realname);
				request.setAttribute("realname", realname);
			}
			if (StringUtils.isNotEmpty(referrerId)) {
				paramap.put("referrerId", referrerId);
				request.setAttribute("referrerId", referrerId);
			}
			page = drugProjectService.findPageForUsers(page, paramap);
			List<Integer> li = page.getPageNos();
			String listUrl = request.getContextPath()
					+ "/admin/drugUDRelation_getUsersList.Q";
			request.setAttribute("listUrl", listUrl);
			request.setAttribute("page", page);
			request.setAttribute("li", li);
			request.setAttribute("obj", page.getData());
			request.setAttribute("pop", request.getParameter("pop"));
			List objlist = page.getData();
			for (int i = 0; i < objlist.size(); i++) {
				System.out.println(objlist.get(i).toString());

			}
			System.out.println(page.getData());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "usersList";

	}

	public String getSingupType() {
		return singupType;
	}

	public void setSingupType(String singupType) {
		this.singupType = singupType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public File[] getMyFile() {
		return myFile;
	}

	public void setMyFile(File[] myFile) {
		this.myFile = myFile;
	}

	public void setMyFileContentType(String[] contentType) {
		this.myFileContentType = contentType;
	}

	public void setMyFileFileName(String[] fileName) {
		this.myFileFileName = fileName;
	}

	public String[] getMyFileContentType() {
		return myFileContentType;
	}

	public String[] getMyFileFileName() {
		return myFileFileName;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public Long getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(Long diseaseId) {
		this.diseaseId = diseaseId;
	}

	public File getUserPicfile() {
		return userPicfile;
	}

	public void setUserPicfile(File userPicfile) {
		this.userPicfile = userPicfile;
	}

	public IDrugProjectService getDrugProjectService() {
		return drugProjectService;
	}

	public void setDrugProjectService(IDrugProjectService drugProjectService) {
		this.drugProjectService = drugProjectService;
	}

	public IDrugQualificationService getDrugQualificationService() {
		return drugQualificationService;
	}

	public void setDrugQualificationService(
			IDrugQualificationService drugQualificationService) {
		this.drugQualificationService = drugQualificationService;
	}

	public void setDrugScoreLogService(IDrugScoreLogService drugScoreLogService) {
		this.drugScoreLogService = drugScoreLogService;
	}

	public static void main(String[] args) {
		StringBuffer result = new StringBuffer();
		result.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		result.append("<SubmitResult xmlns=\"http://121.199.16.178/\">");
		result.append("<code>2</code>");
		result.append("<msg>提交成功</msg>");
		result.append("<smsid>403320248</smsid>");
		result.append("</SubmitResult>");

		try {
			SAXReader sax = new SAXReader();
			byte[] bytes = result.toString().getBytes();
			InputStream in = new ByteArrayInputStream(bytes);
			InputStreamReader strInStream = new InputStreamReader(in, "utf-8");
			// 获得dom4j的文档对象
			Document root = sax.read(strInStream);
			Element rootEle = (Element) root.selectSingleNode("//SubmitResult");
			List<Element> elements = rootEle.elements();
			for (Element ele : elements) {
				// dataMap.put(ele.getName(), ele.getText());
				System.out.println(ele.getName() + "==========="
						+ ele.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
