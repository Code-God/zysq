<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>【<%=Version.getInstance().getSystemTitle() %>】</title>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
	    <script src="<%=request.getContextPath() %>/js/jquery/jquery.js"></script>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/js/jstree/themes/default/style.min.css" />
	    <script src="<%=request.getContextPath() %>/js/jstree/jstree.min.js"></script>
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/common/config.js"></SCRIPT>
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/admin/org.js"></SCRIPT>
		<script type="text/javascript">
		</script>
	</head>
	<body onload="loadOrg()">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m2,msub21" />

		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span4">
						<div class="widget-box">
							<c:if test="${isAdmin || isNormalAdmin}">
							    <!-- 如果不是系统管理员，不显示此按钮 -->
								<button class="btn btn-primary" onclick="addCompany(1)">新增地区</button>
							</c:if>
							<div class="widget-title">
								<span class="icon"> <i class="icon-file"></i> </span>
								<h5>
									平台组织架构总览
								</h5>
							</div>
							<div class="widget-content nopadding">
								 <div id="myTree"></div>
							</div>
						</div>

					</div>
					<!-- 右侧区域 -->
					<div class="span8">
					
						<div id="info1" >
						<!-- 
							<c:if test="${isAdmin || fn:length(orgCode) < 12 }"> 
								<button class="btn btn-success"  onclick="showAdd()">新增子节点</button>
							</c:if>
						 -->
							<button class="btn btn-success"  onclick="showModify()">修改节点</button>
							<c:if test="${isAdmin }">
								<button class="btn btn-success"  onclick="deleteNode()">删除节点</button>
							</c:if>
							<div id="addOrg" style="display: none;">
								<hr>
								节点名称：<input type="text" id="newOrgName" >
								<input type="hidden" id="op" value="a">
								<button class="btn btn-inverse" onclick="addSubOrg()">确定</button>
								<button class="btn btn-inverse" onclick="cancelAdd()">取消</button>
							</div>
							<hr>
							<!-- 只有管理员或者分销商才能看到此内容，普通管理员看不到 -->
							<c:if test="${isAdmin || isFenXiao}">
							<div class="row-fluid">
								<div class="span6">
									<div class="widget-box">
										<div class="widget-title"> <span class="icon"> <i class="icon-arrow-right"></i> </span>
								            <h5>基本设置</h5>
								         </div>
										 <div class="widget-content nopadding">
											 <div style="margin: 15px">
									          	<ul class="activity-list">
									          		<li class="clearfix"><!-- 权限设置 -->
										          		<span id="orgName"></span>&nbsp;管理员：<input type="text" id="charger" style="width: 60px">
										          		<button class="btn btn-success" onclick="selUser()" id="selCharger">选择</button>
														<input type="hidden" id="orgId" value="0">
														<input type="hidden" id="currentNodeName" value="">
														<!-- 此负责人ID -->
														<input type="hidden" id="userId" value="0"> 
														<button class="btn btn-primary" onclick="updateCharger()" id="updCharger">更新</button>
									                </li>
									                <li>
														<!-- 佣金设置 -->
														所属地区： <span class="date badge badge-info" id="orgCity"></span><br>
									                </li>
									                <li style="display: none;">
														<!-- 佣金设置 -->
														下级分销商佣金：<input type="text" id="commission" value='10' style="width: 20px">%
														&nbsp;&nbsp;<font color="green">分销客佣金：</font><input type="text" id="personCommission" value='5' style="width: 20px">%
														<button class="btn btn-primary" onclick="updateCommission()">更新</button><br>
									                </li>
									                <li  style="display: none;">
														<!-- 佣金设置 -->
														运费设置：<input type="text" id="deliverFee" value="10" style="width: 20px">元；
														订单满<input type="text" id="baoyou" value="100" style="width: 50px">元包邮(0元表示不包邮)
														<button class="btn btn-primary" id="deliverBut" onclick="updateDeliverFee()">更新</button><br>
									                </li>
									                <li id="mallDiv" style="display: none;">
									                	微商城有效期： <input type="text" id="mallexpire" value=''  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="span5 wdateinput">
								                		<button class="btn btn-primary" onclick="updateMallExpire(1)"  id="mallexpireBut">更新</button>
									                	<div class="alert alert-error">
											            	<strong>提示：</strong> 如果设置为空，则表示时间无限制。 
											            </div>
									                </li>
									             </ul>
								             </div>
								          </div>
									</div>
								</div>
								<div class="span6"   >
									<div class="widget-box">
										<div class="widget-title"> <span class="icon"> <i class="icon-arrow-right"></i> </span>
								            <h5>微信公众号信息</h5>
								         </div>
										 <div class="widget-content nopadding">
										 	<div style="margin: 15px">
									          	<ul  class="activity-list">
									          		<li class="clearfix">
									          			APPID:<input type="text" id="appid" name="appid" value='' style="width: 200px"><br>
														APPSecret:<input type="text" id="appsecret" name="appsecret" value='' style="width: 200px"><br>
														原始ID:<input type="text" id="wxID" name="wxID" value='' style="width: 200px"><br>
														默认多客服账号:<input type="text" id="kfAccount" name="kfAccount" value='' style="width: 200px">
														<button class="btn btn-primary" onclick="updateWxconfig()">更新</button><br>
									                </li>
									                <li>
														<button class="btn btn-primary" onclick="showWxMallUrl()">显示微信公众号微商城URL</button>
														<button class="btn btn-danger" onclick="$('#wxMallUrl').hide()">隐藏</button><br>
														<div class="alert alert-error">
											            	<strong>提示：</strong> 可以复制此url作为公众号自定义菜单的入口。 
											            </div>
														<div class="alert" id="wxMallUrl"></div>
									                </li>
									             </ul>
										 	</div>
								          </div>
									</div>
								</div>
							</div>
							<!-- 分销客相关设置 -->
							<div class="row-fluid" style="display: none;">
								<div class="span12">
									<div class="widget-box">
										<div class="widget-title"> <span class="icon"> <i class="icon-arrow-right"></i> </span>
								            <h5>微信关注、转发设置</h5>
								         </div>
										 <div class="widget-content nopadding">
										 	<div style="margin: 15px">
									          	<ul  class="activity-list">
									          		<li class="clearfix">
									          			提示关注的图文链接:<input type="text" id="attHintUrl" name="attHintUrl" value='' placeholder="用来提示用户进行关注公众号，需复制预先撰写的图文url" style="width: 60%"><br>
									          			分享微商城时的标题:<input type="text" id="shareTitle" name="shareTitle" value='' style="width: 60%"><br>
														分享微商城时的描述:<input type="text" id="shareDesc" name="shareDesc" value='' style="width: 60%"><br>
														分享微商城时的图片:<input type="text" id="shareLogo" name="shareLogo" value='' style="width: 60%">
														<div class="alert alert-error">
											            	<strong>提示：</strong> 请从公众号素材库（建议把图片上传至公众号素材库后复制链接）或者从其他网络来源复制图片链接。<br>如：http://mmbiz.qpic.cn/mmbiz/lCDXqg6rZadWvSSX8Bp06GQfTpObxM1Hf3CARlgZKzNOTSLJWFBSLs6ia7BH2LniaDibpTjjXtRicglv03HN8Qhkmw/640?wx_fmt=jpeg&wxfrom=5 
											            </div>
														<button class="btn btn-primary" onclick="updateShareConfig()">更新</button><br>
									                </li>
									             </ul>
										 	</div>
								          </div>
									</div>
								
								</div>
							</div>
							</c:if>
							
							
						</div>
						<div id="info2">
							<h1 style="color: #00008B">请点击左侧的树节点进行管理。</h1>
						</div>
						<!-- 总经销 -->
						<div id="info33" style="display: none;"><!-- topOrgName, telephone, email -->
							<div class="widget-box">
								<div class="widget-title"> <span class="icon"> <i class="icon-arrow-right"></i> </span>
						            <h5>新开通地区</h5>
						         </div>
								 <div class="widget-content ">
						          	<ul>
						                <li>
											地区选择：
											<SELECT   id="province"
					onchange="set_city(this, document.getElementById('city')); "
					class=login_text_input>
					<option value=0>请选择</option>
					<option value=浙江省>浙江省</option>
					<!-- 
					<option value=台湾>台湾</option>
					<option value=马来西亚>马来西亚</option>
					<option value=北京>北京</option>
					<option value=上海>上海</option>
					<option value=江苏省>江苏省</option>
					<option value=天津>天津</option>
					<option value=重庆>重庆</option>
					<option value=河北省>河北省</option>
					<option value=山西省>山西省</option>
					<option value=辽宁省>辽宁省</option>
					<option value=吉林省>吉林省</option>
					<option value=黑龙江省>黑龙江省</option>
					<option value=安徽省>安徽省</option>
					<option value=福建省>福建省</option>
					<option value=江西省>江西省</option>
					<option value=山东省>山东省</option>
					<option value=河南省>河南省</option>
					<option value=湖北省>湖北省</option>
					<option value=湖南省>湖南省</option>
					<option value=广东省>广东省</option>
					<option value=海南省>海南省</option>
					<option value=四川省>四川省</option>
					<option value=贵州省>贵州省</option>
					<option value=云南省>云南省</option>
					<option value=陕西省>陕西省</option>
					<option value=甘肃省>甘肃省</option>
					<option value=青海省>青海省</option>
					<option value=内蒙古>内蒙古</option>
					<option value=广西>广西</option>
					<option value=西藏>西藏</option>
					<option value=宁夏>宁夏</option>
					<option value=新疆>新疆</option>
					<option value=香港>香港</option>
					<option value=澳门>澳门</option>
					 -->
				</SELECT> -
				<select id="city" class=login_text_input  >
					<option value=0>请选择</option>
				</select>
				 <span style="color: #F00;display:none" id="provinceSpan">请选择省份！</span>
				 <span style="color: #F00;display:none" id="citySpan">请选择所属城市！</span>

											<br>
											公司名称：<input type="text" id="topOrgName" ><br>
											联系电话：<input type="text" id="telephone" ><br>
											邮箱：<input type="text" id="email" ><br>
											<button class="btn btn-inverse" onclick="addCompany(2)">确定</button>
											<button class="btn btn-inverse" onclick="$('#info33').hide()">取消</button>
						                </li>
						             </ul>
						          </div>
							</div>
						</div>
						<div id="tableDiv" >
							<div class="widget-box">
								<div class="widget-title"> <span class="icon"> <i class="icon-arrow-right"></i> </span>
						            <h5>会员列表</h5>
						         </div>
								 <div class="widget-content ">
						          	 	<table class="table table-bordered">
						          	 		<thead>
								                <tr>
								                 <th>序号</th><th>OpenID</th> 
								                 <th>昵称</th><th>性别</th><th>所属分组</th><th>电话</th>
								                 <th>状态</th>
								                 <!-- 
								                 <th>最后登录时间</th>
								                  -->
								                 <th>操作</th>
								                </tr>
								              </thead>
											<TBODY id="dataList">
												 
											</TBODY>
										</table>
										<SPAN id="pageBar">
										</SPAN>
						          </div>
							</div>
						</div>
					</div>
				</div>

			<input type="hidden" id="isroot" value="">
			</div>
			<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
			<script src="<%=request.getContextPath()%>/js/dpicker/WdatePicker.js" type="text/javascript"></script>
			<script src="<%=contextPath %>/js/common/city.js"></script>
	</body>
</html>
