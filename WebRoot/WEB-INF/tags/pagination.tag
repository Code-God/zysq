<%@tag  import="java.util.*" pageEncoding="UTF-8"%>
<%@ attribute name="page" type="com.tekview.apex.itsm.common.util.Page" required="true"%>
<%@ attribute name="action" type="java.lang.String" required="true"%>
<%@ attribute name="form" type="java.lang.String" required="true"%>
<%@ attribute name="autoParam" type="java.lang.Boolean" required="true"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<% 
	int current = page.getCurrPageNo();
	int totalPage = page.getTotalPageCount();
	int pageSize = page.getPageSize();
	
	if(current > totalPage){
		current = totalPage;
	}
	
	request.setAttribute("current", current);
	request.setAttribute("totalPage", totalPage);
	request.setAttribute("pageSize", pageSize);
	

 %>
 <script type="text/javascript">
<!--
	function jumpPage(pageNo){
		if(pageNo=="") return ;
		if(pageNo > ${totalPage} ){
			pageNo = ${totalPage};
		}
		$("input[name='page.currPageNo']").each(function(){
			$(this).val(pageNo);
		});
		
		document.${form}.action="${action}";
		document.${form}.submit();
	}
	
	function changePageSize(pageSize){
		$("input[name='page.currPageNo']").each(function(){
			$(this).val(1);
		});
		$("input[name='page.pageSize']").each(function(){
			$(this).val(pageSize);
		});
		document.${form}.action="${action}";
		document.${form}.submit();
	}
//-->
</script>
<%if (autoParam) {%>
<form id="${form}" name="${form}" action="" method="post">
<%
	Map map = request.getParameterMap();
	if(map != null){
		Set<String> keySet = map.keySet();
		if(!map.containsKey("page.currPageNo")){
			%>
			<input type="hidden" id="page.currPageNo" name="page.currPageNo" value="${current}"/>
			<% 
		}
		if(!map.containsKey("page.pageSize")){
			%>
			<input type="hidden" id="page.pageSize" name="page.pageSize" value="${pageSize} "/>
			<% 
		}
		for(String s :keySet){
			%>
			<input type="hidden" id="<%=s %>" name="<%=s %>" value="<%=request.getParameter(s) %>"/>
			<% 
		}
	}
	
 %>
</form>
<%} %>
<div class="fenye_coun">
	<h1>  页数：${ current}/${totalPage}页  总计：${page.totalCount}条数据  </h1>
	<h2> 
		<span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span">
			<i>
				<%if (current == 1 ){%>
				<a href="javascript:void(0);">
					<img class="feng_img" src="${ctx}/images/cmdb_n/vf2.gif" height="10" width="5">
					<img class="feng_img" src="${ctx}/images/cmdb_n/vf2.gif" height="10" width="5">
				</a>
				<%} else { %>
				<a  href="javascript:void(0);" onclick="jumpPage(1);return false;">
					<img class="feng_img" src="${ctx}/images/cmdb_n/vf2.gif" height="10" width="5">
					<img class="feng_img" src="${ctx}/images/cmdb_n/vf2.gif" height="10" width="5">
				</a>
				<%} %>
			</i>
		</span>
		<c:forEach items="${page.pageNos}" var="i">
			<c:choose>
				 <c:when test="${i == current}">
				 	<span class="h2_span_on"><i>${i }</i></span>	
				 </c:when>
				 <c:otherwise>
				  	<span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span" onclick="jumpPage(${i });return false;"><i>${i }</i></span> 
				 </c:otherwise>
			</c:choose>
		</c:forEach>
		<span onmouseout="this.className='h2_span'" onmouseover="this.className='h2_span_on'" class="h2_span">
			<i>
				<% if(current == totalPage){  %>
				<a  href="javascript:void(0);">
					<img class="feng_img" src="${ctx}/images/cmdb_n/vf3.gif" height="10" width="5">
					<img class="feng_img" src="${ctx}/images/cmdb_n/vf3.gif" height="10" width="5">
				</a>
				<% }else { %>
				<a href="javascript:void(0);" onclick="jumpPage(${totalPage});return false;">
					<img class="feng_img" src="${ctx}/images/cmdb_n/vf3.gif" height="10" width="5">
					<img class="feng_img" src="${ctx}/images/cmdb_n/vf3.gif" height="10" width="5">
				</a>
				<%}%>				
			</i>
		</span>
		&nbsp;&nbsp;
		<span style="height:10px;line-height:12px;">
			转到第 
			<input value="" id="destPage" style="border: 1px solid rgb(219, 219, 219); width: 25px;" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" > 页 
			<input style="border: medium none ; margin: 0pt 0pt 0pt 4px; background: transparent url(../images/cmdb_n/2_r2_c2.jpg) repeat scroll 0% 0%; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; width: 40px; height: 20px; cursor: pointer; color: rgb(68, 68, 68);" value="转到" onclick="jumpPage(document.getElementById('destPage').value);" type="button"> 
			每页显示&nbsp;
			<select id="pageSize" name="page.pageSize" onchange="changePageSize(this.value)">
				<option value="10" <%if( pageSize == 10) {%>selected="selected"<%} %> >10</option>
				<option value="20" <%if( pageSize == 20) {%>selected="selected"<%} %> >20</option>
				<option value="30" <%if( pageSize == 30) {%>selected="selected"<%} %> >30</option>
				<option value="40" <%if( pageSize == 40) {%>selected="selected"<%} %> >40</option>
				<option value="50" <%if( pageSize == 50) {%>selected="selected"<%} %> >50</option>
				<option value="60" <%if( pageSize == 60) {%>selected="selected"<%} %> >60</option>
				<option value="70" <%if( pageSize == 70) {%>selected="selected"<%} %> >70</option>
				<option value="80" <%if( pageSize == 80) {%>selected="selected"<%} %> >80</option>
				<option value="90" <%if( pageSize == 90) {%>selected="selected"<%} %> >90</option>
				<option value="100" <%if( pageSize == 100) {%>selected="selected"<%} %> >100</option>
			</select> 
		</span>
	</h2>	
</div>

