package com.base.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 分页标签 ，三种用法：
 * <li> 直接请求action，无查询条件的：<oss:pager action="/TestA/index.do"></oss:pager>
 * <li> 通过get方式，有参数的：<oss:pager action="/TestA/index.do" qparams="name" qvalues="jack"/>
 * <li> 通过form提交查询条件的：<oss:pager formId="listForm" />
 * 
 * 详细请查看《Tekview开发帮助手册》
 * 
 * @author Jacky.wang
 */
public class PagingTag extends TagSupport {

	/** 提交查询的FORM id，为空则表示无查询条件 */
	private String formId;

	/** 获取dao，查找 */
	private String action;

	/** get方式传递的参数 */
	private String qparams;

	/** get方式传递的值列表 */
	private String qvalues;

	/** get方式传递参数时的分隔符 */
	private String separator;

	/** 每页显示条数 */
	private String pageSize = "20";

	private static String DEFAULT_PAGE_SIZE = "20";

	private static final long serialVersionUID = 3792772881051214395L;

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		JspWriter out = this.pageContext.getOut();
		int pageSize = 0;
		pageSize = getPageSize(request, Integer.valueOf(this.pageSize));
		int pageNo = Integer.parseInt((request.getAttribute("pageNo") == null) ? "1" : request.getAttribute("pageNo").toString());
		int totalSize = Integer.parseInt((request.getAttribute("totalSize") == null) ? "0" : request.getAttribute("totalSize").toString());
		int totalPage = totalSize / pageSize + ((totalSize % pageSize == 0) ? 0 : 1);
		if (pageNo > totalPage)
			pageNo = totalPage;
		StringBuffer sb = new StringBuffer();
		request.setAttribute("pageSize", Integer.valueOf(pageSize));
		printScript(pageSize, pageNo, totalPage, sb);
		String finalSep = ",";
		if (this.action != null) {// 说明是第二种情况，是否带参数就要看getQparams和getQvalues方法是否为空了
			if ((getQparams() != null) && (getQvalues() != null)) {
				// 组装查询条件
				String param = ((getQparams() == null) ? "" : getQparams())+" ";//由于1,2,,, param.split(finalSep)的结果为[1,2]所以在这里增加一个" "
				String values = ((getQvalues() == null) ? "" : getQvalues())+" ";//由于1,2,,, param.split(finalSep)的结果为[1,2]所以在这里增加一个" "
				if (getSeparator() != null) {
					finalSep = getSeparator();
				}
				String[] paramSplit = param.split(finalSep);
				String[] valueSplit = values.split(finalSep);
				if (paramSplit.length != valueSplit.length) {
					try {
						out.println("分页标签错误：参数和值的个数不匹配，请检查。");
						return SKIP_PAGE;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				StringBuffer finalActionUrl = new StringBuffer(this.action);
				sb.append("<form action=\"" + finalActionUrl + "\" id=\"pagerForm\">").append("\n");
				if (getQparams() != null) {
					for (int i = 0; i < paramSplit.length; i++)
						sb.append("<input type=\"hidden\" name=\"" + paramSplit[i].trim() + "\"  value=\"" + valueSplit[i].trim() + "\">").append("\n");
				}
				sb.append("<input type=\"hidden\" name=\"start\" id=\"start\"  value=\"" + ((pageNo - 1) * pageSize) + "\">").append("\n");
				sb.append("<input type=\"hidden\" name=\"pageNo\" id=\"pageNo\"  value=\"" + pageNo + "\">").append("\n");
				sb.append("<input type=\"hidden\" name=\"pageSize\" id=\"pageSize\" value=\"" + pageSize + "\">").append("\n");
				sb.append("</form>").append("\n");
				setFormId("pagerForm");
			} else if (((getQparams() != null) && (getQvalues() == null)) || ((getQparams() == null) && (getQvalues() != null))) {
				try {
					out.println("标签的参数不正确，请检查。");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return super.doEndTag();
			}
		}
		writePagerByForm(pageNo, totalPage, totalSize, sb);
		try {
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	private void printScript(int pageSize, int pageNo, int totalPage, StringBuffer sb) {
		sb.append("<script>").append("\n");
		sb.append("function doPagerQuery(formId, step){").append("\n");
		sb.append("var reg=/^[1-9]+[0-9]*$/;//以1-9开头，后面为任意数字").append("\n");
		sb.append("var result =  reg.test(step);").append("\n");
		sb.append("if(!result && step != 'L' && step != 'F' && step != 'S'){").append("\n");
		sb.append("		try{if(Ext){}}catch(e){Ext=false};try{if(art){}}catch(e){art=false};	if(Ext&&Ext.MessageBox){Ext.MessageBox.show({title: '警告',msg: '请输入大于零的整数！',buttons : Ext.MessageBox.OK,icon : Ext.MessageBox.WARNING,fn : function(returnValue) {}});}else{if(art&&art.dialog){art.dialog({lock:true,content:'请输入大于零的整数！',icon:'error',yesFn: true});}else{alert('请输入大于零的整数！');}}").append("\n");
		//sb.append("		if(Ext.MessageBox){Ext.MessageBox.show({title: '警告',msg: '请输入大于零的整数！',buttons : Ext.MessageBox.OK,icon : Ext.MessageBox.WARNING,fn : function(returnValue) {}});}else{alert('请输入大于零的整数！');}").append("\n");
		sb.append("		document.getElementById('destPage').select();").append("\n");
		sb.append("		return;").append("\n");
		sb.append("}").append("\n");
		sb.append("if(step >= 1){").append("\n");
		sb.append("\t\tif(step >= " + totalPage + "){").append("\n");
		sb.append("   \t\tdocument.getElementById(\"pageNo\").value = " + totalPage + ";").append("\n");
		sb.append("\t\t\tdocument.getElementById(\"start\").value = (" + totalPage + " - 1) * " + pageSize + ";").append("\n");
		sb.append("\t\t}else{").append("\n");
		sb.append("\t\tdocument.getElementById(\"pageNo\").value = step;").append("\n");
		sb.append("\t\tdocument.getElementById(\"start\").value = (step - 1) * " + pageSize + ";").append("\n");
		sb.append("\t}").append("\n");
		sb.append("}else if(step == -1){//上一页").append("\n");
		if (pageNo - 1 <= 0) {
			sb.append("\tdocument.getElementById(\"pageNo\").value = \"1\";//防止负数").append("\n");
			sb.append("document.getElementById(\"start\").value = (step - 1) * " + pageSize + ";").append("\n");
		} else {
			sb.append(" document.getElementById(\"pageNo\").value = " + (pageNo - 1) + ";").append("\n");
			sb.append("document.getElementById(\"start\").value = (step - 1) * " + pageSize + ";").append("\n");
		}
		sb.append("}else if(step == \"F\"){//第一页").append("\n");
		sb.append("\t document.getElementById(\"pageNo\").value = \"1\";").append("\n");
		sb.append("\t document.getElementById(\"start\").value = \"0\";").append("\n");
		sb.append("}else if(step == \"S\"){//指定页").append("\n");
		sb.append("\t document.getElementById(\"pageNo\").value = \"" + pageNo + "\";").append("\n");
		sb.append("document.getElementById(\"start\").value = (step - 1) * " + pageSize + ";").append("\n");
		sb.append("}else if(step == \"L\"){//最后页").append("\n");
		sb.append("\t document.getElementById(\"pageNo\").value = \"" + totalPage + "\";").append("\n");
		sb.append("document.getElementById(\"start\").value = (" + totalPage + " - 1) * " + pageSize + ";").append("\n");
		sb.append("}").append("\n");
		sb.append("document.getElementById(formId).submit();").append("\n");
		sb.append("}").append("\n");
		sb.append("</script>").append("\n");
	}

	private int getPageSize(HttpServletRequest request, int pageSize) {
		if(request.getSession().getAttribute("pageSize") != null){//用户指定的每页显示条数，优先级最高。
			request.setAttribute("pageSize", Integer.valueOf(request.getSession().getAttribute("pageSize").toString()));
			pageSize = Integer.valueOf(request.getSession().getAttribute("pageSize").toString());
			return pageSize;
		}
		if (this.getAction() != null) {
			if (this.pageSize != null) {
				pageSize = Integer.valueOf(this.pageSize).intValue();
				request.setAttribute("pageSize", Integer.valueOf(pageSize));
			}
			if (request.getAttribute("pageSize") != null)
				pageSize = Integer.valueOf(request.getAttribute("pageSize").toString()).intValue();
			if (pageSize == 0)
				pageSize = 20;
		} else if(this.getFormId() != null){
			return  Integer.valueOf(this.pageSize == null ? "10" : this.pageSize).intValue();
		}else{
			pageSize = Integer.parseInt((request.getAttribute("pageSize") == null) ? DEFAULT_PAGE_SIZE : request.getAttribute("pageSize").toString());
		}
		return pageSize;
	}

	/**
	 * 
	 * <div class="fenye_css">
              <div class="fenye_coun">
                <h1> 页数：1/20页  总计：200条数据 </h1>
                <h2> 
                <span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span"><i><a title="" href=""><img height="10" width="5" class="feng_img" src="../images/cmdb_n/vf2.gif"/><img height="10" width="5" class="feng_img" src="../images/cmdb_n/vf2.gif"/>
                </a></i></span>
                <span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span"><i><a title="" href=""><img height="10" width="5" class="feng_img" src="../images/cmdb_n/vf2.gif"/>
                </a></i></span>
                <span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span"><i><a title="" href="">1</a></i></span>
                <em>2</em>
                <span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span"><i>2</i></span>
                <span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span"><i>3</i></span>
                <span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span"><i>4</i></span>
                <span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span"><i>5</i></span>
                <span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span"><i>6</i></span>
                <span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span"><i>7</i></span>
                <span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span"><i><a title="" href="">
                <img height="10" width="5" class="feng_img" src="../images/cmdb_n/vf3.gif"/>
                <img height="10" width="5" class="feng_img" src="../images/cmdb_n/vf3.gif"/>
                </a></i></span>
                <span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span"><i><a title="" href=""><img height="10" width="5" class="feng_img" src="../images/cmdb_n/vf3.gif"/>
                </a></i></span>
                </h2>
              </div>
            </div>
	 * 
	 * 
	 * @param pageNo
	 * @param totalPage
	 * @param totalSize
	 * @param sb
	 */
	private void writePagerByForm(int pageNo, int totalPage, int totalSize, StringBuffer sb) {
		if (totalPage > 0) {
			sb.append("<div class=\"fenye_css\">").append("\n");
			sb.append("		<div class=\"fenye_coun\">").append("\n");
			sb.append("		<h1> 页数："+ pageNo +"/"+ totalPage +"页  总计："+ totalSize +"条数据 </h1>").append("\n");
			sb.append("		 <h2> ").append("\n");
			if (pageNo == 1)
				sb.append("<span onmouseout=\"this.className='h2_span'\"  class=\"h2_span\">" +
						"<i><a title='' href=\"###\" ><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/cmdb_n/vf2.gif\"/><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/cmdb_n/vf2.gif\"/></a></i></span>").append("\n");
			else {
				sb.append("<span onmouseout=\"this.className='h2_span'\" onmouseover=\"this.className='h2_span_on'\" class=\"h2_span\">" +
						"<i><a title='' href=\"###\" onclick=\"doPagerQuery('" + this.formId + "', 'F');return false;\"><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/cmdb_n/vf2.gif\"/><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/cmdb_n/vf2.gif\"/></a></i></span>").append("\n");
			}
			int outPutPages = 7;
			int displayPages = (totalPage > outPutPages) ? outPutPages : totalPage;
			if (pageNo >= outPutPages) {
				int lastDisplayPage = (pageNo + 4 >= totalPage) ? totalPage : pageNo + 4;
				for (int i = pageNo - 3; i < lastDisplayPage; ++i)
					if (i + 1 == pageNo) {
						sb.append("<span  class=\"h2_span_on\"><i>"+(i + 1)+"</i></span>").append("\n");
					} else
						sb.append("<span onmouseout=\"this.className='h2_span'\" onmouseover=\"this.className='h2_span_on'\" class=\"h2_span\" onclick=\"doPagerQuery('" + this.formId + "', '" + (i + 1) + "');return false;\"><i>"+(i + 1)+"</i></span>").append("\n");
			} else {
				for (int i = 0; i < displayPages; ++i) {
					if (i + 1 == pageNo) {
						sb.append("<span class=\"h2_span_on\"><i>"+(i + 1)+"</i></span>").append("\n");
					} else
						sb.append("<span onmouseout=\"this.className='h2_span'\" onmouseover=\"this.className='h2_span_on'\" class=\"h2_span\" onclick=\"doPagerQuery('" + this.formId + "', '" + (i + 1) + "');return false;\"><i>"+(i + 1)+"</i></span>").append("\n");

				}
			}
			if ((totalPage > outPutPages + 5) && (pageNo + 4 < totalPage)) {
				sb.append("<span class=\"h2_span\" style=\"border:none;\">...</span>").append("\n");
				sb.append("<span onmouseout=\"this.className='h2_span'\" onmouseover=\"this.className='h2_span_on'\" class=\"h2_span\" onclick=\"doPagerQuery('" + this.formId + "', 'L');return false;\"><i>"+totalPage+"</i></span>").append("\n");
			}
			if (pageNo < totalPage)
				sb.append("<span onmouseout=\"this.className='h2_span'\" onmouseover=\"this.className='h2_span_on'\" class=\"h2_span\">" +
						"<i><a title='' href=\"###\" onclick=\"doPagerQuery('" + this.formId + "', 'L');return false;\"><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/cmdb_n/vf3.gif\"/><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/cmdb_n/vf3.gif\"/></a></i></span>").append("\n");
			else {
				sb.append("<span class=\"h2_span\">" +
						"<i><a title='' href=\"###\" ><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/cmdb_n/vf3.gif\"/><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/cmdb_n/vf3.gif\"/></a></i></span>").append("\n");
			}
			StringBuffer psizeSetter = new StringBuffer("每页显示&nbsp;<select id='pagerSel' onchange=\"document.getElementById('pageSize').value=this.value;document.getElementById('"+ this.formId +"').submit();\">");
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			for(int ii = 10; ii< 110; ii +=10 ){
				String sel_status = "";
				if(ii ==  this.getPageSize(request, Integer.valueOf(pageSize))){
					sel_status = "selected";
				}
				psizeSetter.append("<option value='"+ ii +"' "+ sel_status +">"+ ii +"</option>");
			}
			psizeSetter.append("</select> ");
//			"<option value='10' "+ sel_1 +">10</option>" +
//					"<option value='20'>20</option>" +
//					"<option value='30'>30</option>" +
//					"<option value='40'>40</option>" +
//					"<option value='50'>50</option>" +
//					"<option value='80'>80</option>" +
//					"<option value='100'>100</option>" +
//					"</select>&nbsp;条";
			//<input type="button" onclick="goToPage();" value="转到" style="border: medium none ; margin: 0pt 0pt 0pt 4px; background: transparent url(../images/cmdb_n/2_r2_c2.jpg) repeat scroll 0% 0%; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; width: 40px; height: 20px; cursor: pointer; color: rgb(68, 68, 68);"/>
			sb.append("&nbsp;&nbsp;<span style='height:10px;line-height:12px;'> 转到第 <input type=text value='' id='destPage' style='border: 1px solid rgb(219, 219, 219); width: 25px;'> 页 <input  style=\"border: medium none ; margin: 0pt 0pt 0pt 4px; background: transparent url(../images/cmdb_n/2_r2_c2.jpg) repeat scroll 0% 0%; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; width: 40px; height: 20px; cursor: pointer; color: rgb(68, 68, 68);\" value='转到' type=button onclick=\"doPagerQuery('"
									+ this.formId + "', " + "document.getElementById('destPage').value);\"> "+ psizeSetter.toString() +"</span>").append("\n");
			sb.append("		</h2>").append("\n");
			sb.append("	</div>").append("\n");
			sb.append("</div>").append("\n");
		}
	}

	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	public String getFormId() {
		return this.formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public static void main(String[] args) {
		StringBuffer finalActionUrl = new StringBuffer("/a/b.do").append("?");
		String finalSep = ",";
		String[] split = "name,pass".split(finalSep);
		String[] vs = "tom,123".split(finalSep);
		for (int i = 0; i < split.length; ++i)
			if (i < split.length - 1)
				finalActionUrl.append(split[i]).append("=").append(vs[i]).append("&");
			else
				finalActionUrl.append(split[i]).append("=").append(vs[i]);
		System.out.println(finalActionUrl);
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getQparams() {
		return this.qparams;
	}

	public void setQparams(String qparams) {
		this.qparams = qparams;
	}

	public String getQvalues() {
		return this.qvalues;
	}

	public void setQvalues(String qvalues) {
		this.qvalues = qvalues;
	}

	public String getSeparator() {
		return this.separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
}