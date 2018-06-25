package com.base.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

public class AsynchronousPagerTag extends TagSupport {

	private static final long serialVersionUID = 2034837560872860385L;

	String url;

	String renderToDivId;

	String ajaxMethodStr = "";

	String divBodyStr = "";

	String tableId = "";

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		Object obj;
		try {
			obj = ExpressionEvaluatorManager.evaluate("url", url, String.class, this.pageContext);
			this.url = String.valueOf(obj);
		} catch (JspException e) {
			this.url = "";
		}
	}

	public String getRenderToDivId() {
		return "'#" + this.renderToDivId + "'";
	}

	public void setRenderToDivId(String renderToDivId) {
		Object obj;
		try {
			obj = ExpressionEvaluatorManager.evaluate("renderToDivId", renderToDivId, String.class, this.pageContext);
			this.renderToDivId = String.valueOf(obj);
		} catch (JspException e) {
			this.renderToDivId = "";
		}
	}

	public int doStartTag() throws JspTagException {
		return 6;
	}

	public int doEndTag() throws JspTagException {
		JspWriter out = this.pageContext.getOut();
		try {
			out.println(getAjaxMethodStr());
			out.println(getDivBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 6;
	}

	public String getAjaxMethodStr() {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		Integer totalPage = (Integer) request.getAttribute("totalPage");
		StringBuffer methodStr = new StringBuffer();
		methodStr.append("<script>").append("\n");
		methodStr.append(" //第一个方法").append("\n");
		methodStr.append("function goToPage(){").append("\n");
		methodStr.append("var page=document.getElementById('goToPageId').value;").append("\n");
		methodStr.append("var totalePage=" + totalPage).append("\n");
		methodStr.append("var reg=/^[1-9]+[0-9]*$/;//以1-9开头，后面为任意数字").append("\n");
		methodStr.append("var result =  reg.test(page);").append("\n");
		methodStr.append("if(result){").append("\n");
		methodStr.append("if(null==page||page==''){").append("\n");
		methodStr.append("return;").append("\n");
		methodStr.append("}else{").append("\n");
		methodStr.append("if(page<=1){").append("\n");
		methodStr.append("page=1;").append("\n");
		methodStr.append("}else if(page>=totalePage){").append("\n");
		methodStr.append("page=totalePage").append("\n");
		methodStr.append("}").append("\n");
		methodStr.append("}").append("\n");
		methodStr.append("AsynchronousQuery(page);").append("\n");
		methodStr.append("}else{").append("\n");
		methodStr.append("		alert('请输入大于零的整数！');").append("\n");
		methodStr.append("return;").append("\n");
		methodStr.append("}").append("\n");
		methodStr.append(" }").append("\n");
		methodStr.append(" //第二个方法").append("\n");
		methodStr.append("function AsynchronousQuery(page){").append("\n");
		methodStr.append("if(arguments.length==0){AsynchronousQuery(document.getElementById('currentPage').value);return;}")
				.append("\n");
		methodStr.append("var startIndex;").append("\n");
		methodStr.append("var pageSize=document.getElementById('pageSize').value;").append("\n");
		methodStr.append("var AsynPageUrl=document.getElementById('AsynPageUrl').value;").append("\n");
		methodStr.append("AsynPageUrl =encodeURI(AsynPageUrl); ").append("\n");
		methodStr.append("if(page==1){").append("\n");
		methodStr.append("startIndex=0;").append("\n");
		methodStr.append("}else{").append("\n");
		methodStr.append("startIndex=(page-1)*pageSize;").append("\n");
		methodStr.append("}").append("\n");
		methodStr.append("jQuery.ajax(");
		methodStr.append("{").append("\n");
		methodStr.append("\tdata:{").append("\n");
		methodStr.append("startIndex: startIndex,").append("\n");
		methodStr.append("currentPage: page").append("\n");
		methodStr.append("},").append("\n");
		methodStr.append("dataType: 'html',").append("\n");
		methodStr.append("type: 'post',").append("\n");
		methodStr.append("url:AsynPageUrl,").append("\n");
		methodStr.append("success: function(html){").append("\n");
		// 在包含次分页的标签的页面里设置一个标志session.invalidate，包含session.invalidate的就是正常返回，session过期是返回到index.jsp，她没理由包含session.invalidate字符，据此判断session是否过期
		methodStr.append("if(html.indexOf('session.invalidate')==-1){").append("\n");
		methodStr.append("window.location.href='/love/Login.Q';").append("\n");
		methodStr.append(" } else {").append("\n");
		methodStr.append("$(" + getRenderToDivId() + ").html('');");
		methodStr.append("$(" + getRenderToDivId() + ").html(html);").append("\n");
		methodStr.append("}").append("\n");
		methodStr.append("},").append("\n");
		methodStr.append("complete: function(XMLHttpRequest, textStatus){").append("\n");
		methodStr.append("var tableId=$('#tableId').val();").append("\n");
		methodStr.append("var oddColor=$('#oddColor').val();").append("\n");
		methodStr.append("var evenColor=$('#evenColor').val();").append("\n");
		methodStr.append("var mouseOverColor=$('#mouseOverColor').val();").append("\n");
		methodStr.append("} ").append("\n");
		methodStr.append("}").append("\n");
		methodStr.append(");").append("\n");
		methodStr.append("}").append("\n");
		methodStr.append("</script>").append("\n");
		return (this.ajaxMethodStr = methodStr.toString());
	}

	public String getDivBody() {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		Integer totalPage = (Integer) request.getAttribute("totalPage");
		int outPage = 7;
		if (totalPage != null) {
			int step;
			Integer asyPageSize = (Integer) request.getAttribute("asyPageSize");
			Integer currentPage = (Integer) request.getAttribute("currentPage");
			String AsynPageUrl = getUrl();
			Integer AsynTotalSize = (Integer) request.getAttribute("AsynTotalSize");
			int displayPage = (totalPage.intValue() > 7) ? 7 : totalPage.intValue();
			StringBuffer divStr = new StringBuffer();
			divStr.append("<div class='fenye_css' >").append("\n");
			divStr.append("<input type='hidden' name='pageSize' id='pageSize' value='" + asyPageSize + "' />").append("\n");
			// divStr.append("<input type='hidden' name='currentPage' id='oddColor' value='" + oddColor + "'
			// />").append("\n");
			// divStr.append("<input type='hidden' name='currentPage' id='evenColor' value='" + evenColor + "' />")
			// .append("\n");
			// divStr.append("<input type='hidden' name='currentPage' id='mouseOverColor' value='" + mouseOverColor + "' />")
			// .append("\n");
			divStr.append("<input type='hidden' name='currentPage' id='currentPage' value='" + currentPage + "' />").append(
					"\n");
			divStr.append("<input type='hidden' name='totalSize' id='totalSize' value='" + AsynTotalSize + "' />").append(
					"\n");
			divStr.append("<input type='hidden' name='tableId' id='tableId' value='" + this.tableId + "' />").append("\n");
			divStr.append("<input type='hidden' name='AsynPageUrl' id='AsynPageUrl' value='" + AsynPageUrl + "' />").append(
					"\n");
			divStr.append("<input type='hidden' name='totalPage' id='totalPage' value='" + totalPage + "' />").append("\n");
			divStr.append("<div class='fenye_coun'>").append("\n");
			divStr.append("<h1>页数：" + currentPage + "/" + totalPage + "页  总计：" + AsynTotalSize + "条数据 </h1>").append("\n");
			divStr.append("<h2>");
			if (currentPage.intValue() <= 1) {
				divStr
						.append(
								"<span onmouseout=\"this.className='h2_span'\"  class=\"h2_span\">"
										+ "<i><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/pager/vf2.gif\"/><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/pager/vf2.gif\"/></i></span>")
						.append("\n");
			}
			if (currentPage.intValue() > 1) {
				divStr
						.append(
								"<span onmouseout=\"this.className='h2_span'\" onclick=\"AsynchronousQuery(1);\" onmouseover=\"this.className='h2_span_on'\" class=\"h2_span\">"
										+ "<i><a  title='第一页' href=\"###\" onclick=\"AsynchronousQuery(1);\"><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/pager/vf2.gif\"/><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/pager/vf2.gif\"/></a></i></span>")
						.append("\n");
			}
			if (currentPage.intValue() >= outPage) {
				int lastDisplayPage = (currentPage.intValue() + 4 >= totalPage.intValue()) ? totalPage.intValue()
						: currentPage.intValue() + 4;
				for (step = currentPage.intValue() - 2; step <= lastDisplayPage; ++step)
					if (currentPage.intValue() == step) {
						divStr.append("<em><i>" + step + "</i></em>").append("\n");
					} else {
						divStr
								.append(
										"<span onmouseout=\"this.className='h2_span'\" onmouseover=\"this.className='h2_span_on'\" class=\"h2_span\" onclick=\"AsynchronousQuery("
												+ step + ");\"><i>" + step + "</i></span>").append("\n");
					}
			} else {
				for (step = 1; step <= displayPage; ++step)
					if (currentPage.intValue() == step)
						divStr.append("<em><i>" + step + "</i></em>").append("\n");
					else
						divStr
								.append(
										"<span onmouseout=\"this.className='h2_span'\" onmouseover=\"this.className='h2_span_on'\" class=\"h2_span\" onclick=\"AsynchronousQuery("
												+ step + ");\"><i>" + step + "</i></span>").append("\n");
			}
			if ((totalPage.intValue() > outPage) && (currentPage.intValue() + 4 < totalPage.intValue())) {
				divStr.append("<em><i>...</i></em>").append("\n");
				divStr
						.append(
								"<span onmouseout=\"this.className='h2_span'\" onmouseover=\"this.className='h2_span_on'\" class=\"h2_span\" onclick=\"AsynchronousQuery("
										+ totalPage + ");\"><i>" + totalPage + "</i></span>").append("\n");
			}
			if (currentPage.intValue() >= totalPage.intValue()) {
				divStr
						.append(
								"<span class=\"h2_span\">"
										+ "<i><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/pager/vf3.gif\"/><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/pager/vf3.gif\"/></i></span>")
						.append("\n");
			} else {
				divStr
						.append(
								"<span onmouseout=\"this.className='h2_span'\" onclick=\"AsynchronousQuery("+ totalPage +")\"  onmouseover=\"this.className='h2_span_on'\" class=\"h2_span\">"
										+ "<i><a  title='最后一页' href=\"###\" onclick=\"AsynchronousQuery("
										+ totalPage
										+ ");\"><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/pager/vf3.gif\"/><img height=\"10\" width=\"5\" class=\"feng_img\" src=\"../images/pager/vf3.gif\"/></a></i></span>")
						.append("\n");
			}
			divStr.append("<span >转到第</span>");
			divStr.append("<input type='text' id='goToPageId' style='width:25px;border:1px #dbdbdb solid'/>").append("\n");
			divStr.append("<span >页</span>");
			divStr
					.append(
							"<input style=\"border:1px #d5d5d5 solid;background:url(../images/pager/2_r2_c2.jpg); width:40px; height:20px; cursor: pointer; border:none; margin:0 0 0 4px;color:#444\" value='转到' type=button "
									+ "onclick='goToPage();'/>").append("\n");
			divStr.append("</h2>");
			divStr.append("</div>").append("\n");
			divStr.append("</div>").append("\n");
			return (this.divBodyStr = divStr.toString());
		}
		return "";
	}

	public void setAjaxMethodStr(String ajaxMethodStr) {
		this.ajaxMethodStr = ajaxMethodStr;
	}

	public String getDivBodyStr() {
		return this.divBodyStr;
	}

	public void setDivBodyStr(String divBodyStr) {
		this.divBodyStr = divBodyStr;
	}

	public String getTableId() {
		return this.tableId;
	}

	public void setTableId(String tableId) {
		Object obj;
		try {
			obj = ExpressionEvaluatorManager.evaluate("tableId", tableId, String.class, this.pageContext);
			this.tableId = String.valueOf(obj);
		} catch (JspException e) {
			this.tableId = "";
		}
	}
}