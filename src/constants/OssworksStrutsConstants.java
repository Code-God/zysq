package constants;

import java.util.HashMap;
import java.util.Map;

/**
 * struts常量定义:<br>
 * 这里存放的是客户端使用的常量，SESSION里的KEY一律以SESSION_为前缀
 * request里的常量KEY一律以REQUEST_为前缀
 * 
 * @author chenchen
 * @version 1.0
 * @since Apex OssWorks 5.5
 */
public class OssworksStrutsConstants {
	
	
	public static Map<String,String>returnUrl = new HashMap<String,String>();
    static{
    	returnUrl.put("ISSUE_POOL_URL", "/issue/issueMgt.do?menuId=m0_0");//事件工单池的返回地址
    	returnUrl.put("MY_ISSUE_URL", "/issue/issueMgt.do?menuId=m0_2");//我的工单返回地址
    	returnUrl.put("LAST_RESOLVED_URL", "/issue/lastestResolveIssue.do?menuId=m0_3");//最近解决工单的返回地址
    	returnUrl.put("LAST_UPDATE_URL", "/issue/lastestUpdateIssue.do?menuId=m0_4");//最近更新的返回地址
    	returnUrl.put("LAST_ADD_URL", "/issue/lastestAddIssue.do?menuId=m0_5");//最近增加的返回地址
    	returnUrl.put("ISSUE_CREATE_URL", "/servicedesk/creatIssueIncWiz.do?menuId=m1_1&next=1");//创建工单的返回地址
    	returnUrl.put("INCIDENT_MANAGE_URL", "/incident/incidentRequest.do?menuId=m3_0");//事件管理返回地址
    	returnUrl.put("PROBLEM_MANAGE_URL", "/problem/problemManage.do?menuId=m3_1");//问题管理返回地址
    	returnUrl.put("KNO_DETAIL", "/knowledge/knowledgeDetail.do?to=detail&method=detail&id=");//
    }
    public static String getReturnUrl(String type){
    	String url = returnUrl.get(type);
    	if(null == url)
    		url = "/issue/issueMgt.do?menuId=m0_0";
    	return url;
    }
	/**
	 * mapping.findForward("success")
	 */
	public static final String FORWARD_SUCCESS = "success";
	
	public static final String FORWARD_ERROR = "error";
	
	public static final String FORWARD_ERROR_DELETE = "error_delete";
	
	public static final String FORWARD_ERROR_ISSUE = "error_issue"; 
	
	public static final String INDEX_FILE_IS_BEING_CREATED = "error_index";
	
	public static final String FEEDBACK = "feedback";

	public static final String USERREQUESTMGT = "/servicedesk/userRequestMgt.do";
	
	public static final String ISSUE_DETAIL = "issueDetail";

	public static final String PAGE = "page";
	
	public static final String _0 = "0";

	public static final String _1 = "1";

	public static final String _2 = "2";

	public static final String _3 = "3";

	public static final String ISSUE_TYPES = "issueTypes";

	public static final String SOURCELIST = "sourceList";

	public static final String SUBTYPES = "subTypes";
	
	public static final String FIXED_HANDLERS = "fixedHandlers";

	public static final String INCFORM = "incForm";

	public static final String PROBLEMFORM = "problemForm";

	public static final String RFCFORM = "rfcForm";
	
	public static final String MENU_ID = "menuId";
	
	public static final String ISSUE_CODE = "ISSUE_CODE";
	
	public static final String VIEW_ISSUE_DETAIL = "/cwf/front/viewCwfIssueDetail.do?&method=viewCwfIssueDetail&flag=waitDispose&" + ISSUE_CODE + "=";
	
	public static final String CURRENTUSER = "currentUser";
	public static final String CURRENTUSER_REALNAME = "currentUserRealName";
	
	/**
	 * 工单之间的关系
	 */
	public static final String ISSUE_RELATION = "issue_relation";

	/**
	 * 再现用户列表(session未失效的仍算其中)
	 */
	public final static String ONLINE_USERS_LIST = "ONLINE_USERS_LIST";
	
	/**
	 * 资源监控中，当前显示的用户视图
	 */
	public final static String VIEW_SHOW_NAME= "VIEW_SHOW_NAME";
	
	/**
	 * 资源监控中，左边隐藏的视图数目
	 */
	public final static String NUMBER_LEFT_HIDE= "NUMBER_RIGHT_HIDE";

	/**
	 * 用户姓名key，存放到HttpSession中引用用户名
	 */
	public final static String SESSION_USERNAME = "com.tekview.apex.platform.username";
	/**
	 * 登陆者id，存放到HttpSession中引用用户id
	 */
	public final static String SESSION_USE_ID = "com.tekview.apex.platform.userId";
	
	/**
	 * 是否是自助式服务台用户
	 */
	public final static String IS_SSUSER = "IS_SSUSER";
	/**
	 * 删除工单时返回的地址
	 */
	public final static String SESSION_RETURN_URL = "return_url";
	
	/**
	 * 创建工单时取消返回的地址
	 */
    public final static String SESSION_CANCEL_URL = "cancel_url";
    
	public static final String ISSUE_CODE_ERROR = "issueCodeError";
	
	/**
	 * request 里存放工单详情对象的KEY
	 */
	public static final String REQUEST_KEY_ISSUE_DETAIL = "REQUEST_KEY_ISSUE_DETAIL";
	
	public static final String ORDER_MONITOR_LIST = "ORDER_MONITOR_LIST";
	
	public static final String CMDB_CI_LIST = "cilist";
	
	/**
	 * 上传附件时的错误
	 */
	public static final String UPLOAD_FILE_ERROR = "upload_file_error";
	/**变更管理struts资源文件key名称与struts配置文件对应*/
	public static final String STRUTS_MESSGERESOURCES_RFC="rfc";
	public static final String STRUTS_MESSGERESOURCES_COMMON= "common";
	/**资产struts资源文件key名称与struts配置文件对应*/
	public static final String STRUTS_MESSGERESOURCES_ASSET= "asset";
	/** 解决时间 */
	public static final String RESOLVE_TIME = "RESOLVE_TIME";
	
	public static final String ATTENTION_CI = "Attention_CI";
	
	public static final String STRUTS_MESSGERESOURCES_CMDB= "cmdb";
	public static final String STRUTS_MESSGERESOURCES_ERROR= "error";
	
	public static final String CMDB_LOCKSATUS= "CMDB_LOCKSATUS";
	
	public static final String CWF_ISSUE_TASk= "CWF_ISSUE_TASk";
	
	public static final String STRUTS_MESSGERESOURCES_BIZ= "biz";
}
