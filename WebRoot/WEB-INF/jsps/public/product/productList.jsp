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
								<div class="sort">
									<ul>
										<!-- 
										<c:if test="${sorter=='sales'}">
											<li sort="sales" class="curr">
												<div <c:if test="${order=='desc'}">class="up"</c:if><c:if test="${order=='asc'}">class="down"</c:if>><a>销量<b></b></a></div>
											</li>
										</c:if>
										<c:if test="${sorter!='sales'}">
											<li sort="sales">
												<a>销量</a>
											</li>
										</c:if>
										 -->
										<c:if test="${sorter=='disPrice'}">
											<li sort="disPrice" class="curr">
												<div <c:if test="${order=='desc'}">class="up"</c:if><c:if test="${order=='asc'}">class="down"</c:if>><a>价格<b></b></a></div>
											</li>
										</c:if>
										<c:if test="${sorter!='disPrice'}">
											<li sort="disPrice">
												<a>价格</a>
											</li>
										</c:if>
										<c:if test="${sorter=='createDate'}">
											<li sort="createDate" class="curr">
												<div <c:if test="${order=='desc'}">class="up"</c:if><c:if test="${order=='asc'}">class="down"</c:if>><a>上架<b></b></a></div>
											</li>
										</c:if>
										<c:if test="${sorter!='createDate'}">
											<li sort="createDate">
												<a>上架</a>
											</li>
										</c:if>
									</ul>
								</div>
								<div class="total">共<span>${totalCount }</span>个商品</div>
							</div>
							<div class="clear"></div>
						</div>
						<div class="p-list">
							<form action="<%=request.getContextPath() %>/public/list.Q" method="get" id="queryForm">
								<input type="hidden" value="${sorter==null?'disPrice':sorter }" name="sort" id="sort"/>
								<input type="hidden" value="${order==null?'asc':order }" name="order" id="order"/>
								<input type="hidden" value="${code }" name="code"/>
								<input type="hidden" value="${keyword }" name="keyword"/>
								
							</form>
							<ul class="list-all">
								<c:forEach var="product" items="${products}">
									<form action="<%=request.getContextPath() %>/private/shopping_add.Q" method="get">
										<li>
											<div class="l-wrap">
												<div class="pic">
													<a class="trackref" href="<%=request.getContextPath() %>/prd/${product.id }"> <img src="${imgPath }${product.prdCode }_small.jpg" width="210" height="210" class="lazy"
															alt="${product.name }"> </a>
												</div>
												<div class="price">
													<c:if test="${not empty product.prdDisPrice}">
														<span class="p-now">￥<strong>${product.prdDisPrice }</strong>
														</span>
														<span class="p-nor">￥${product.prdPrice }</span>
														<span class="active">直降</span>
													</c:if>
													<c:if test="${empty product.prdDisPrice}">
														<span class="p-now">￥<strong>${product.prdPrice }</strong>
														</span>
													</c:if>
												</div>
												<div class="title-c">
													<a name="" class="trackref" href="<%=request.getContextPath() %>/public/details.Q?id=${product.id }" title="${product.name }">${product.name }</a>
												</div>
												<div class="comment">
													<!--<a href="" target="_blank">已有616人评价</a>  -->
												</div>
												<div class="action">
													<div class="p-num">
														<span> <input type="text" name="num" class="numberInp" value="1" autocomplete="off" onkeyup="this.value=this.value.replace(/[^0-9]+/g,'1')" onafterpaste="this.value=this.value.replace(/[^0-9]+/g,'1')"/> 
																<input type="hidden" name="prdCode" value="${product.prdCode }"/>
														</span>
														<span> <a class="p-add">+</a> <a class="p-reduce disable">-</a> </span>
													</div>
		
													<div class="p-btn">
														<a>加入购物车</a>
													</div>
													<span class="clear"></span>
												</div>
											</div>
										</li>
									</form>
								</c:forEach>
								<div class="clear"></div>
							</ul>
							<div id="pagination"></div>
							<div class="clear"></div>
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
				$("#category_container .mod_cate_bd").slide({
				type:"menu",
				titCell:".mod_cate_li",
				targetCell:".mod_subcate",
				delayTime:0,
				triggerTime:10,
				defaultPlay:false,
				returnDefault:true
			    });
			
			
			    $.jqPaginator('#pagination', {
			        totalPages: ${pages},
			        visiblePages: 10,
			        currentPage: ${currPage},
			        wrapper:'<ul class="pagination"></ul>',
			        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
			        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
			        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
			        last: '<li class="last"><a href="javascript:void(0);">末页</a></li>',
			        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>'
			    });
			    
			    var url = window.location.href;
			    $(".pagination li").not(".disabled").click(function () {
					var currPageNo = $(this).attr("jp-data");
					var pageUrl = "";
					if(url.indexOf("page") != -1){
						pageUrl = url.replace(/page=\d+/g, "page=" + currPageNo);
					}else{
						pageUrl = url + "&page=" + currPageNo;
					}
					window.location.href = pageUrl;
				});
				
				$(".p-add").click(function(){
					var input = $(this).parent().prev().children("input.numberInp");
					input.val(input.val() * 1 + 1);
					if(input.val() * 1 > 1){
						$(this).next().removeClass("disable");
					}
				});
				$(".p-reduce").click(function(){
					var input = $(this).parent().prev().children("input.numberInp");
					if(input.val()*1 > 1){
						input.val(input.val() * 1 - 1);
					}
					if(input.val() * 1 == 1){
						$(this).addClass("disable");
					}
				});
				
				$(".p-btn").click(function(){
					$(this).parents("form").submit()
				});
				
				String.prototype.getValue= function(param) {  
				    var reg = new RegExp("(^|&)"+ param +"=([^&]*)(&|$)");  
				    var r = this.substr(this.indexOf("\?")+1).match(reg);
				    if (r!=null) return unescape(r[2]); return null;  
			    }
			    
				$(".sort li").click(function(){
					var css = "up";
					if($(this).hasClass("curr")){
						if($(this).children("div").hasClass("up")){
							css = "down";
						}
					}
					var html = '<div class="' + css + '"><a>销量<b></b></a></div>';
					$(this).parent().children("li").removeClass("curr");
					$(this).addClass("curr");
					
					var css = "up";
					// 重复点排序为逆序
					if ($(this).hasClass("curr")) {
						if ($(this).children().hasClass("up")) {
							css = "down";
						}
					}
					// 恢复默认css
					var oldTxt = $(".sort .curr").text();
					$(".sort .curr").html("<a>" + oldTxt + "</a>");
					// 更新当前排序方式css
					var txt = $(this).first().text();
					$(this).html("<div class='" + css + "'><a>" + txt + "<b></b></a></div>");
					// 当前排序
					$(".sort li").removeClass("curr");
					$(this).addClass("curr");
					$("#sort").val($(this).attr("sort"));
					if (css == "down") {
						$("#order").val("asc");
					}else{
						$("#order").val("desc");
					}
					
					$("#queryForm").submit();
				});
			});
			</script>
		<!----footerStart------>
		<%@include file="/WEB-INF/jsps/foot.jsp"%>
	</body>
</html>

