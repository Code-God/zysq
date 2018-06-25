<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>	

						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>序号</th>									
									<th>姓名</th>
									<th>昵称</th>
									<th>联系电话</th>
									<th>邀请时间</th>
									<th>性别</th>
									<th>年龄</th>
									<th>所在城市</th>
									<th>通讯地址</th>
									<th>用户类型</th>
								</tr>
							</thead>
							<tbody id="dataList">
								<s:if test="#request.obj.size>0">
									<s:iterator value="#request.obj" var="objitem" status="index">
										<tr>												
											<td>
												 <s:property value="#index.index+1"/>
											</td>
											<td>${objitem.realName }</td>
											<td>${objitem.nickName }</td>
											<td>${objitem.mobilePhone}</td>
											<td>${objitem.regDate }</td>
											<td>
												<c:choose>
													<c:when test="${objitem.sex==1 }">男</c:when>
													<c:when test="${objitem.sex==2 }">女</c:when>
													<c:otherwise>未知</c:otherwise>
												</c:choose>
											</td>
											<td>${objitem.age }</td>
											<td>${objitem.city }</td>
											<td>${objitem.address }</td>
											<td>
												<c:choose>
													<c:when test="${objitem.userType==1 }">医生</c:when>
													<c:when test="${objitem.userType==2 }">护士</c:when>
													<c:when test="${objitem.userType==3 }">患者</c:when>
													<c:otherwise>其他</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</s:iterator>
								</s:if>
								<s:else>
									<tr>
										<td colspan="11">
											<div class="alert alert-block">
												<h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
											</div>
										</td>
									</tr>
								</s:else>
							</tbody>
						</table>
						<tags:ajaxPagination scripts="ajaxGetBeInvitedList" divId="beinvitedId" queryFormId="beInvitedQueryForm" page="${page }" ></tags:ajaxPagination>
						</tbody>
						</table>
