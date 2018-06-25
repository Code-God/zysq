<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<title>【<%=Version.getInstance().getSystemTitle() %>】</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/main.css">
		<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.js"></script>
		<script src="<%=request.getContextPath() %>/js/jqPaginator.js"></script>
		<script src="<%=request.getContextPath() %>/js/jquery.SuperSlide.2.1.1.js" type="text/javascript"></script>
	</head>

	<body>
		<%@include file="/WEB-INF/jsps/header.jsp"%>
		<%@include file="/WEB-INF/jsps/searchHeader.jsp"%>
		<%@include file="/WEB-INF/jsps/navigate.jsp"%>
		<!----页面导航------>
		<div class="linknav">
			<div class="breadcrumb">
				<c:if test="${not empty topProductCat}">
					<strong><a href="<%=request.getContextPath() %>/public/list.Q?code=${topProductCat.code }">${topProductCat.name }</a></strong>
					<span>
						<c:if test="${not empty currTwoCat}">
							&nbsp;&gt;&nbsp;<a href="<%=request.getContextPath() %>/public/list.Q?code=${currTwoCat.code }">${currTwoCat.name }</a>
						</c:if>
						<c:if test="${not empty currThreeCat}">
							&nbsp;&gt;&nbsp;<h1>${currThreeCat.name }</h1>
						</c:if>
					</span>
				</c:if>
				<c:if test="${empty topProductCat}">
					<h1>“${keyword }”的商品筛选</h1>
				</c:if>
			</div>
		</div>
		<!--列表内容-->
		<div class="wf_content scontent" id="container">
			<div class="grid_c2a">
				<div class="grid_s sside">
					<div class="catlist mb_10" id="cateall">
						<c:if test="${not empty topProductCat}">
							<div class="ct">
								<h2>
									${topProductCat.name }
								</h2>
							</div>
						</c:if>
						<div class="cm">
							<c:forEach var="prdCatTwo" items="${productCatTwo}">
								<div class="catitem<c:if test="${currTwoCat.id == prdCatTwo.id }"> curr</c:if>">
									<h3>
										<b></b>${prdCatTwo.name }
									</h3>
									<ul>
										<c:forEach var="prdCatThree" items="${productCatThree}">
											<c:if test="${prdCatTwo.id == prdCatThree.parentId}">
												<li>
													<a href="<%=request.getContextPath() %>/public/list.Q?code=${prdCatThree.code }<c:if test="${not empty keyword }">&keyword=${keyword }</c:if>" class="trackref<c:if test="${currThreeCat.id == prdCatThree.id }"> cur</c:if>">${prdCatThree.name }</a>
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</c:forEach>
						</div>
					</div>
					<%@include file="/WEB-INF/jsps/justLook.jsp"%>
				</div>
				<div class="grid_m smain">
					<div class="grid_m_inner">
						<div class="r-filter">
							<div class="f-sort">
								<div class="total">
									共<span>0</span>个商品
								</div>
							</div>
							<div class="clear"></div>
						</div>
						<div class="main-wrap2">
							<div class="searchTip">
								<h2>
									很抱歉，没有找到相关商品。
								</h2>
								<h3>
									建议您：
								</h3>
								<ol>
									<li>
										看看其它相似分类
									</li>
									<li>
										通过搜索查找您需要的商品
									</li>
								</ol>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				if(!$(".catlist .catitem").hasClass("curr")){
					$(".catlist .catitem:first").addClass('curr');
				}
				$(".catitem b").click(function(){
					if($(this).parents(".catitem").hasClass('curr')){
						$(this).parents(".catitem").removeClass('curr');
						}else{
						$(this).parents(".catitem").addClass('curr').siblings().removeClass('curr');
						}
					})
				$(".list-all li").hover(function(){
				  $(this).toggleClass("curr");
					});	
				$(".v-show").click(function(){
					if($(this).children(".s-more").hasClass('hide')){
					   $(this).children(".s-more").removeClass('hide');
					   $(this).children(".s-less").addClass('hide');
					}else{
					   $(this).children(".s-more").addClass('hide');
					   $(this).children(".s-less").removeClass('hide');
					}
				})
			   $(".mod_cate").hover(function(){
					  $(this).toggleClass("mod_cate_on");
			   });
			});
		</script>
		<!----footerStart------>
		<%@include file="/WEB-INF/jsps/foot.jsp"%>
	</body>
</html>

