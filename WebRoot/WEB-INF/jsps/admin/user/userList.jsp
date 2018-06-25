<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped with-check">
              <thead>
                <tr>
                  <th width="8%">昵称</th>
                  <th width="4%">姓名</th>
                  <th width="4%">性别</th>
                  <th width="4%">年龄</th>
                  <th width="10%">联系电话</th>
                  <th width="10%">所在城市</th>
                  <th width="14%">通讯地址</th>
                  <th width="10%">医院科室</th>
                  <th width="10%">职位</th>
                  <th width="10%">注册时间</th>
                  <th width="6%">积分</th>
                  <!-- <th width="8%" name="auditStatusTdList">审核状态</th> -->
                  <th width="10%">用户类型</th> 
                  <th width="10%">操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.userlist.size>0">
                <s:iterator value="#request.userlist" var="user">
                 <tr>
                  <td><input type="checkbox" name="adminIds" value='<s:property value="id"/>'/><s:property value="nickName"/></td>
                   <td><s:property value="realName"/></a></td>
                 <%--  <td><a href='<%=request.getContextPath()%>/admin/user_detail.Q?id=<s:property value="id"/>' target="_blank"><s:property value="realName"/></a></td> --%>
                  <td><s:if test="#user.sex == 1">男</s:if><s:elseif test="#user.sex == 0">未知</s:elseif><s:else>女</s:else></td>
                  <td><s:if test="#user.age == 0">-</s:if><s:else><s:property value="age"/></s:else></td>
                  <td><s:property value="mobilePhone"/></td>
                  <td><s:property value="city"/></td>
                  <td><s:property value="address"/></td>
                  <td><s:property value="hospital"/>--<s:property value="department"/></td>
                  <td>
                  <s:if test="#user.position == 1">主任医师</s:if>
	                  <s:elseif test="#user.position == 2">副主任医师</s:elseif>
	                  <s:elseif test="#user.position == 3">主治医生</s:elseif>
	                  <s:elseif test="#user.position == 4">住院总</s:elseif>
	                  <s:elseif test="#user.position == 5">实习医生</s:elseif>
	                  <s:elseif test="#user.position == 9">其他</s:elseif>
	                  <s:else>-</s:else>
                  </td>
                  <td><s:date name="regDate" format="yyyy-MM-dd HH:mm:ss" /></td>
                  <%-- <td name="auditStatusTdList">
                  	  <s:if test="#user.userType == 3">
		                  <s:if test="#user.auditStatus == 11">病史审核通过</s:if>
		                  <s:elseif test="#user.auditStatus == 12">病史审核未通过</s:elseif>
		                  <s:elseif test="#user.auditStatus == 21">联系中通过</s:elseif>
		                  <s:elseif test="#user.auditStatus == 22">联系中未通过</s:elseif>
		                  <s:elseif test="#user.auditStatus == 31">医院筛选通过</s:elseif>
		                  <s:elseif test="#user.auditStatus == 32">医院筛选未通过</s:elseif>
		                  <s:elseif test="#user.auditStatus == 41">审核通过</s:elseif>
		                  <s:elseif test="#user.auditStatus == 42">审核未通过</s:elseif>
	                  	  <s:else>待审核</s:else>
                  	  </s:if>
	                  <s:else>&nbsp;</s:else>
                  </td> --%>
                  	<td><s:property value="points"/></td>
                   <td>
	                  <s:if test="#user.userType == 1">医生</s:if>
	                  <s:elseif test="#user.userType == 2">护士</s:elseif>
	                  <s:elseif test="#user.userType == 3">患者</s:elseif>
	                  <s:else>其他</s:else>
                  </td>
                  <td><%-- 
                  	<s:if test="#user.status==1"><font color=blue>启用</font></s:if>
                  	<s:else><font color=red>禁用</font></s:else> --%>
                  	
											<button	class="label label-info btn btn-primary btn-mini"
													onclick="getUser('<s:property value="id"/>');">积分兑现</button>&nbsp;&nbsp;&nbsp;
											<button	class="label label-info btn btn-primary btn-mini"
													onclick="getpointsDetail('<s:property value="openId"/>');">积分记录</button>
                  </td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="12">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination scripts="ajaxGetUserList" divId="userTableId" queryFormId="userQueryForm" page="${page }"></tags:ajaxPagination>
          
