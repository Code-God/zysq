<%@tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ attribute name="page" type="com.base.util.Page" required="true"%>
<%@ attribute name="scripts" type="java.lang.String" required="true"%>
<%@ attribute name="queryFormId" type="java.lang.String" required="true"%>
<%@ attribute name="divId" type="java.lang.String" required="true"%>
<%@ attribute name="callBack" type="java.lang.String" required="false"%>
<link href="<%=request.getContextPath()%>/css/fenye.css" rel="stylesheet" type="text/css" />
<%
	int current = page.getCurrPageNo();
	int totalPage = page.getTotalPageCount();
	int pageSize = page.getPageSize();	
	request.setAttribute("current", current);
	request.setAttribute("totalPage", totalPage);
	request.setAttribute("pageSize", pageSize);
 %>

<script type="text/javascript">
function ${scripts} (pageNo){
	if(pageNo=="") return ;
	if(pageNo > ${page.totalPageCount} ){
		pageNo = ${page.totalPageCount};
	}
	var listUrl = '${listUrl}';
	if(!listUrl){
		return ;
	}
	var pageSize = $("#pageSize").val();
	if(listUrl.indexOf('?')<0){
		listUrl += "?currPageNo="+pageNo+"&pageSize="+pageSize;
	}else{
		listUrl += "&currPageNo="+pageNo+"&pageSize="+pageSize;
	}
	$.ajax({
		url:listUrl,
		type:"post",
		data:$('#${queryFormId}').serialize(),
		dataType:"html",
		success:function(result){
				$("#${divId}").html(result);
				<%
				if(callBack !=null && callBack.length() > 0){
				%>
				${callBack};
				<%
					}
				%>
		}
	});
}

</script>
 <style type="text/css">
        h2 {
            width: 550px;
        }
    </style>
<div class="fenye_coun">
	<h1>  页数：${ current}/${totalPage}页  总计：${page.totalCount}条 </h1>
	<h2> 
		<span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span">
			<i>
				<%if (current == 1 ){%>
				<a href="javascript:void(0);">
					<img class="feng_img" src="<%=request.getContextPath() %>/images/vf2.gif" height="10" width="5">
					<img class="feng_img" src="<%=request.getContextPath() %>/images/vf2.gif" height="10" width="5">
				</a>
				<%} else { %>
				<a  href="javascript:void(0);" onclick="javascript: ${scripts}(1);return false;">
					<img class="feng_img" src="<%=request.getContextPath() %>/images/vf2.gif" height="10" width="5">
					<img class="feng_img" src="<%=request.getContextPath() %>/images/vf2.gif" height="10" width="5">
				</a>
				<%} %>
			</i>
		</span>
		<!--<s:iterator value="#request.li" var="pi">
			<s:if test="#pi==#current">
				<span class="h2_span_on"><i><s:property value="pi"/> </i></span>	
			</s:if>
			<s:else>
			<span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span" onclick="${scripts}('<s:property value="pi"/>');return false;"><i><s:property value="pi"/></i></span>
			</s:else>
		</s:iterator>-->
		<c:forEach items="${li}" var="i">
			<c:choose>
				 <c:when test="${i == current}">
				 	<span class="h2_span_on"><i>${i }</i></span>	
				 </c:when>
				 <c:otherwise>
				  	<span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span" onclick="${scripts}(${i});return false;"><i>${i }</i></span> 
				 </c:otherwise>
			</c:choose>
		</c:forEach>
		<span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span">
			<i>
				<% if(current == totalPage){  %>
				<a  href="javascript:void(0);">
					<img class="feng_img" src="<%=request.getContextPath() %>/images/vf3.gif" height="10" width="5">
					<img class="feng_img" src="<%=request.getContextPath() %>/images/vf3.gif" height="10" width="5">
				</a>
				<% }else { %>
				<a href="javascript:void(0);" onclick="javascript: ${scripts}(${totalPage });return false;">
					<img class="feng_img" src="<%=request.getContextPath() %>/images/vf3.gif" height="10" width="5">
					<img class="feng_img" src="<%=request.getContextPath() %>/images/vf3.gif" height="10" width="5">
				</a>
				<%}%>				
			</i>
		</span>
		&nbsp;&nbsp;
		<span style="height:10px;line-height:12px;">
			转到第 
			<input value="" id="destPage" style="border: 1px solid rgb(219, 219, 219); width: 25px;height:14px;" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" > 页 
			<input style="border: medium none ; margin: 0pt 0pt 0pt 4px; background: transparent url(<%=request.getContextPath() %>/images/2_r2_c2.jpg) repeat scroll 0% 0%; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; width: 40px; height: 20px; cursor: pointer; color: rgb(68, 68, 68);" value="转到" onclick="${scripts}(document.getElementById('destPage').value);" type="button"> 
			每页&nbsp;
			<select id="pageSize" name="page.pageSize" onchange="javascript: ${scripts}(1)" style="width: 55px;height:24px;">
				<option value="10" <%if( pageSize == 10) {%>selected="selected"<%} %> >10</option>
				<option value="20" <%if( pageSize == 20) {%>selected="selected"<%} %> >20</option>
				<option value="30" <%if( pageSize == 30) {%>selected="selected"<%} %> >30</option>
			</select> 
			&nbsp;条
		</span>
	</h2>	
</div>

