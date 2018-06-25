<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>


<c:if test="${!empty justLook}">
<div class="finalbuy mb_10">
	<div class="mt">
		<h2>
			用户最近浏览记录
		</h2>
	</div>
	<div class="mc">
		<ul>
			<c:forEach items="${justLook}" var="prd">
			<li>
				<div class="pImg">
					<a href="<%=request.getContextPath() %>/public/details.Q?id=${prd.id }"><img src="${imgPath }${prd.prdCode }_small.jpg" /> </a>
				</div>
				<div class="title-a">
					<a>${prd.name }</a>
				</div>
				<div class="price">
					<b>￥${prd.prdDisPrice }</b><font>￥${prd.prdPrice }</font>
				</div>
			</li>
			</c:forEach>
		</ul>
	</div>
</div>
</c:if>