package com.wfsc.actions.comments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import model.bo.wxmall.Pj;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
import com.wfsc.common.bo.comment.Comments;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.comment.ICommentsService;
import com.wfsc.services.system.ISystemLogService;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("CommentsAction")
@Scope("prototype")
public class CommentsAction extends DispatchPagerAction {

	private static final long serialVersionUID = -6840813332299260353L;

	@Resource(name = "commentsService")
	private ICommentsService commentsService;
	
	@Autowired
	private ISystemLogService systemLogService;
	
	private Comments comments;

	public String manager(){
		list();
		return "manager";
	}
	
	public String list(){
		Page<Pj> page = new Page<Pj>();
		Map<String,Object> paramap = new HashMap<String,Object>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		String prdCode = request.getParameter("prdCode");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(StringUtils.isNotEmpty(prdCode)){
			paramap.put("prdCode", prdCode);
			request.setAttribute("prdCode", prdCode);
		}
		if(StringUtils.isNotEmpty(startTime)){
			paramap.put("startTime", startTime);
			request.setAttribute("startTime", startTime);
		}
		if(StringUtils.isNotEmpty(endTime)){
			paramap.put("endTime", endTime);
			request.setAttribute("endTime", endTime);
		}
		
		page = commentsService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = request.getContextPath() + "/admin/comments_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("commentslist", page.getData());
		return "list";
	}
	



	public String commentsInput() {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			comments = commentsService.getCommentsById(Long.valueOf(id));
		}else{
			comments = new Comments();
		}
		
		
		return "add";
	}
	
	public String detail() {
		String id = request.getParameter("id");
		comments = commentsService.getCommentsById(Long.valueOf(id));
		return "detail";
	}
	
	public String reply(){
		String id = request.getParameter("commentsId");
		String resContent = request.getParameter("resContent");
		Comments c = commentsService.getCommentsById(Long.valueOf(id));
		c.setResContent(resContent);
		commentsService.saveOrUpdateEntity(c);
		return "ok";
	}
	
	public String deleteByIds(){
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		commentsService.deleteByIds(idList);
		
		Admin user = getCurrentAdminUser();
		SystemLog systemLog = new SystemLog(SystemLog.MODULE_COMMENT, user.getUsername(), "删除评论");
		systemLogService.saveSystemLog(systemLog);
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Comments getComments() {
		return comments;
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}


	
	


	
}
