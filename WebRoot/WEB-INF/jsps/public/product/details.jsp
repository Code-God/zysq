<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<title>【<%=Version.getInstance().getSystemTitle() %>】-${prd.name }</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/main.css">
		<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.js"></script>
		<script src="<%=request.getContextPath() %>/js/jqPaginator.js"></script>
		<script src="<%=request.getContextPath() %>/js/jquery.jqzoom.js"></script>
		<script src="<%=request.getContextPath() %>/js/base.js"></script>
		<script src="<%=request.getContextPath() %>/js/jquery.SuperSlide.2.1.1.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$(".mod_cate").hover(function(){
				  $(this).toggleClass("mod_cate_on");
			  });
			});
			
			function intocart(){
				$("#ShopCartForm").submit();
			}
		</script>
	</head>
	<body>
	<%@include file="/WEB-INF/jsps/header.jsp"%>
	<%@include file="/WEB-INF/jsps/searchHeader.jsp"%>
	<%@include file="/WEB-INF/jsps/navigate.jsp"%>
		<!----页面导航------>
		<div class="linknav">
			<div class="breadcrumb">${link }</div>
		</div>
		<!--列表内容-->
		<div class="wf_content scontent" id="container">
			<div class="pWrap">
				<div class="productIntro mb_10">
					<div class="pItems">
						<div class="pItemsMain">
							<div class="pItemsName">
								<div class="cm">
									<h1 title="${prd.name }">
										${prd.name }
									</h1>
								</div>
							</div>
							<div class="pItemsPrice">
								<div class="priceBox">
									<c:choose>
										<c:when test="${prd.prdDisPrice != null && prd.prdDisPrice > 0}">
											<span class="dt">优选价：</span><span class="rmb">￥</span><strong class="price">${prd.prdDisPrice }</strong><span class="dt"></span><span class="dd">￥${prd.prdPrice }</span>
										</c:when>
										<c:otherwise><span class="dt">价格：</span><span class="rmb">￥</span><strong class="price">${prd.prdPrice }</strong></c:otherwise>
									</c:choose>
								</div>
								<div class="boxWb"></div>
							</div>
							<div class="clear"></div>
							
							<form action="<%=request.getContextPath() %>/private/shopping_add.Q" method="get" class="form-horizontal" id="ShopCartForm">
								<input type="hidden" name="prdCode" id="prdCode" value="${prd.prdCode}" />
								<div class="pItemsChoose">
									<div class="chooseBtns">
										<div class="pAmount">
											<span>
												<input type="text" id="num" name="num" class="text" value="1" autocomplete="off" readonly>
											</span>
											<span><a href="javascript:;" id="add-sell-num"
												class="p-add">+</a> <a href="javascript:;"
												id="reduce-sell-num" class="p-reduce disable">-</a>
											</span>
										</div>
										<div class="pBtn" id="cart-add-btn-sf">
											<a id="btnToCart" style="display:none;" href="javascript:intocart();"><b></b>加入购物车</a>
											<a id="btnLack" class="lack" style="display:none;">暂时缺货</a>
										</div>
										<div style="line-height: 45px;">
											&nbsp;库存：<span id="stock">0</span>
										</div>
									</div>
								</div>
							</form>
							
						</div>
						<div class="pView">
							<div id="preview" class="spec-preview">
								<span class="jqzoom" style="width: 350px; height: 355px;">
									<img jqimg="${imgPath }${prd.picUrl1 }" src="${imgPath }${prd.picUrl1 }"  style="width: 350px; height: 355px;"/>
								</span>
							</div>
							<!--缩图开始-->
							<div class="spec-scroll"><a class="prev">&lt;</a><a class="next">&gt;</a>
								<div class="items">
									<ul>
										<c:forEach items="${picList}" var="pic">
											<c:if test="${pic != null}">
											<li>
												<img alt="${prd.name }" bimg="${imgPath }${pic }" src="${imgPath }${pic }"
													onmousemove="preview(this);">
											</li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</div>
							<!--缩图结束-->
						</div>
					</div>
					<div class="pItemsSide">
						<div class="points">
							<ul id="points-sf">
								<li>
									<img src="<%=request.getContextPath() %>/images/productattr1.png">
								</li>
								<li>
									<img src="<%=request.getContextPath() %>/images/productattr2.png">
								</li>
								<li style="border-right: none;">
									<img src="<%=request.getContextPath() %>/images/productattr3.png">
								</li>
							</ul>
						</div>
						<div class="pdetail">
							<ul>
								<li>
									规格：${prd.prdStandard }
								</li>
								<li>
									商品编号：${prd.prdCode }
								</li>
							</ul>
						</div>
						<div class="pcommdetail">
							<dl class="prate">
								<dt>
									好评度：
								</dt>
								<dd>
									<div class="dd">
										<span id="positive-sf" style=""></span>
									</div>
									<strong><span id="positive-num-sf">
									<c:if test="${one == 0}">
									100
									</c:if>
									<c:if test="${one > 0}">
									<fmt:formatNumber value="${one/all * 100 }" pattern="#0" />
									</c:if>
									</span><span>%</span>
									</strong>
									<div style="display: none" id="score"></div>
								</dd>
							</dl>
						</div>
						<div class="pcollect">
							<c:if test="${prd.favourite == false}"><a href="<%=request.getContextPath() %>/private/account_addFavorite.Q?prdCode=${prd.prdCode }" target="_blank">收藏商品</a></c:if>
							<c:if test="${prd.favourite == true}"><a href="javascript:void(0);">已收藏</a></c:if>
							<div class="pwindow" id="pfavorite">
								<div class="cm">
									<div class="hd">
										<span class="getit">收藏成功</span><span class="showOther"><a
											href="" target="_blank">查看收藏夹</a>
										</span>
									</div>
									<div class="pclose" onclick="$('#pfavorite').hide();"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clear mb_10"></div>
			<div class="pWrap">
				<div class="main-box">
					<div class="show_tab">
						<ul class="show_tabs">
							<li>
								<a href="#">商品介绍</a>
							</li>
							<li>
								<a href="#">品牌故事</a>
							</li>
							<li>
								<a href="#">评价（${all }条）</a>
							</li>
						</ul>
						<!-- / tabs -->
						<div class="tab_content">
							<div class="tabs_item">
								<div class="clear mb_10"></div>
								<div class="product_info">
									${prd.prdDesc }
								</div>
							</div>
							<div class="tabs_item">
								<h3>
									${prd.prdStory }
								</h3>
							</div>
							<div class="tabs_item">
							</div>
						</div>
						<!-- / tab_content -->
					</div>
					<div class="pCont">
						<div class="commentList">
							<div class="pt">
								<h3 class="pTop">
									用户评价
								</h3>
								<div class="commDetail">
									<div class="pScore">
										好评度
										<strong><font id="user-comment-sf">
										<c:if test="${one > 0}">
										<fmt:formatNumber value="${one/all * 100 }" pattern="#0" />
										</c:if>
										<c:if test="${one == 0}">
										100
										</c:if>
										</font><span>%</span>
										</strong>
									</div>
									<div class="pPercent" id="sorce-star-sf">
										<dl>
											<dt>
												好评
											</dt>
											<dd class="pBar">
												<div style="width: ${one > 0 ? one/all * 100 : 100 }px;"></div>
											</dd>
											<dd>
												<c:if test="${one > 0}">
												<fmt:formatNumber value="${one/all * 100 }" pattern="#0" />%
												</c:if>
												<c:if test="${one == 0}">
												100%
												</c:if>
											</dd>
										</dl>
										<dl>
											<dt>
												中评
											</dt>
											<dd class="pBar">
												<div style="width: ${two > 0 ? two/all * 100 : 0 }px;"></div>
											</dd>
											<dd>
												<c:if test="${two > 0}">
												<fmt:formatNumber value="${two/all * 100 }" pattern="#0" />%
												</c:if>
												<c:if test="${two == 0}">
												0%
												</c:if>
											</dd>
										</dl>
										<dl>
											<dt>
												差评
											</dt>
											<dd class="pBar">
												<div style="width: ${three > 0 ? three/all * 100 : 0 }px;"></div>
											</dd>
											<dd>
												<c:if test="${three > 0}">
												<fmt:formatNumber value="${three/all * 100 }" pattern="#0" />%
												</c:if>
												<c:if test="${three == 0}">
												0%
												</c:if>
											</dd>
										</dl>
									</div>
									<div class="pBtns">
										<div>
											购买过商品，参与评价晒单，可获得积分哦~~
										</div>
										<div>
											<a class="pbtn2" href="javascript:void(0);" rel="nofollow">马上评价</a><span class="showMore"><a
												target="_blank" href="javascript:void(0);" rel="nofollow">[如何获取积分]</a> </span>
										</div>
									</div>
								</div>
							</div>
							<div class="commentAll" id="comment-filter-sf">
								<h3 class="curr">
									<a href="javascript:;" data-type="0">全部评价<font>(${all })</font></a>
								</h3>
								<h3 class="">
									<a href="javascript:;" data-type="1">好评<font>(${one })</font></a>
								</h3>
								<h3 class="">
									<a href="javascript:;" data-type="2">中评<font>(${two })</font></a>
								</h3>
								<h3 class="">
									<a href="javascript:;" data-type="3">差评<font>(${three })</font></a>
								</h3>
							</div>
							<!-- 异步内容显示区 -->
							<ul id="ajaxList" class="pComment">
							</ul>
							<!-- 异步内容模版 -->
							<ul id="ajaxListTemp" style="display: none;">
								<li>
									<div class="user">
										<div class="uIcon">
											<img src="images/huiyuan.jpg" border="0">
										</div>
										<div class="uName">
											普通会员
										</div>
										<div class="uLevel">
											#nickName
										</div>
									</div>
									<div class="item">
										<div class="topic">
											<span class="s"><b class="star#stars"></b> </span><span class="t">
												#pdate
											</span>
										</div>
										<div class="itemCont">
											<dl class="c">
												<dt>
													评价内容：
												</dt>
												<dd>
													#content
												</dd>
											</dl>
										</div>
<!--										<div class="pLike">-->
<!--											<a title="赞" href="javascript:void(0);"><b></b>赞</a>-->
<!--										</div>-->
									</div>
									<div class="corner">
										<div class="aBg"></div>
										<div class="aCt"></div>
									</div>
								</li>
							</ul>
							<div id="pagination"></div>
						</div>
					</div>
				</div>
				<div class="left-box">
					<div class="samelist">
						<h2 class="t">
							相关分类
						</h2>
						<ul class="pClass">
							<li>
								<c:forEach var="pc" items="${pcMap}">
									<c:forEach var="two" items="${pc.value.childList}">
										<c:if test="${two.code == lastPc.code}">
								  		<strong><a href="<%=request.getContextPath() %>/public/list.Q?code=${two.code }" title="${two.name }" class="trackref">${two.name }</a></strong>
						        		<c:forEach var="three" items="${two.childList}">
						              	<a href="<%=request.getContextPath() %>/public/list.Q?code=${three.code }" title="${three.name }" class="trackref">${three.name }</a>
						              	</c:forEach>
						              	</c:if>
						          	</c:forEach>
						        </c:forEach>
							</li>
						</ul>
						<div class="clear"></div>
					</div>
					
					<jsp:include page="/WEB-INF/jsps/justLook.jsp"></jsp:include>
					
				</div>
				<div class="clear"></div>
			</div>
		</div>

		<jsp:include page="/WEB-INF/jsps/foot.jsp"></jsp:include>
		<script src="<%=request.getContextPath() %>/js/public/shopping.js"></script>
		<script type="text/javascript">
			$(document).ready(function() { 
				$('.show_tab ul.show_tabs').addClass('active').find('> li:eq(0)').addClass('current');
				
				$('.show_tab ul.show_tabs li a').click(function (g) { 
					var tab = $(this).closest('.show_tab'), 
					index = $(this).closest('li').index();
					
					tab.find('ul.show_tabs > li').removeClass('current');
					$(this).closest('li').addClass('current');
					
					tab.find('.tab_content').find('div.tabs_item').not('div.tabs_item:eq(' + index + ')').slideUp();
					tab.find('.tab_content').find('div.tabs_item:eq(' + index + ')').slideDown();
					
					g.preventDefault();
				});
					
				// 初始化分页控件
				$.jqPaginator('#pagination', {
					totalPages: 100,
				    visiblePages: 10,
				    currentPage: 1,
			        wrapper:'<ul class="pagination"></ul>',
			        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
			        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
			        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
			        last: '<li class="last"><a href="javascript:void(0);">末页</a></li>',
			        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
			    });
			    
				// 获取HTML模版，替换填充数据
				var temp = $("#ajaxListTemp").html();
				// 页面初始化加载
				var funSu = function(page){
					var html = ""; 
					$.each(page.data, function(index, obj) {
						// 复制模版 替换数据
						var sb = temp;
						sb = sb.replace(new RegExp("#nickName", "gm"), obj.nickName);
						sb = sb.replace(new RegExp("#pdate", "gm"), obj.showDate);
						sb = sb.replace(new RegExp("#content", "gm"), obj.content);
						sb = sb.replace(new RegExp("#stars", "gm"), obj.stars);
						html += sb;		
					});
					$("#ajaxList").html(html);
					// 初始化分页点击
					initPageClick(page);
				}
				
				// 初始化加载
				var url = "<%=request.getContextPath()%>/public/details_getPager.Q?stars=0&prdCode=${prd.prdCode}";
				$.getJSON(url, funSu);
				
				// 初始化分页组件
				var initPageClick = function(page){
					// 改变参数
					$('#pagination').jqPaginator('option', {
				        totalCounts: page.totalCounts,
				        totalPages: page.totalPageCount,
				        currentPage: page.currPageNo,
				    });
				    // 绑定点击事件
				    $(".pagination li").not(".disabled").click(function() {
						var currPageNo = $(this).attr("jp-data");
						var pageUrl = url + "&currPageNo=" + currPageNo;
						console.log(pageUrl);
						$.getJSON(pageUrl, funSu);
					});
				}
				$('#comment-filter-sf a').click(function(){
					// update css
					$('#comment-filter-sf h3').removeClass('curr');
					$(this).parent().addClass('curr');
					var type = $(this).attr('data-type');
					// update data
					url = url.replace(new RegExp("stars=[0-9]", "gm"), 'stars=' + type);
					$.getJSON(url, funSu);
				});
			});
			
			function getStock(){
				// 获取当前商品库存数
				var stock = 0;
				var url = "<%=request.getContextPath()%>/public/details_getStock.Q?prdCode=${prd.prdCode}";
				var txtNum = $('#num');
				var btnAdd = $('#add-sell-num');
				var btnReduce = $('#reduce-sell-num');
				
				$.getJSON(url, function(json){
					stock = json.stock;
					$('#stock').html(stock);
					// 没有库存
					if(stock == 0){
						$('#btnLack').show();
						txtNum.val(0);
						btnAdd.addClass('disable');
					} else {
						$('#btnToCart').show();
					}
				});
				
				// 增加
				btnAdd.click(function(){
					adds(stock, txtNum);
					updateCss(txtNum.val(), stock, btnAdd, btnReduce);
				});
				// 减少
				btnReduce.click(function(){
					reduce(stock, txtNum);
					updateCss(txtNum.val(), stock, btnAdd, btnReduce);
				});
			}
		</script>
</body>
</html>