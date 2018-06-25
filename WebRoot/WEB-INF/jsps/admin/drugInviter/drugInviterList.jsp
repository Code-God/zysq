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
									<th>联系方式</th>
									<th>性别</th>
									<th>年龄</th>
									<th>所在城市</th>
									<th>通讯地址</th>
									<th>用户类型</th>
									<th>注册时间</th>
									<th>邀请人数</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="dataList">
								<s:if test="#request.obj.size>0">
									<s:iterator value="#request.obj" var="objitem" status="index">
										<tr>												
											<td>
												 <s:property value="#index.index+1"/>
											</td>
											<td>${objitem[1] }</td>
											<td>${objitem[2] }</td>
											<td>${objitem[3] }</td>											
											<td>
											<c:choose>
												<c:when test="${objitem[6]==1 }">男</c:when>
												<c:when test="${objitem[6]==2 }">女</c:when>
												<c:otherwise>未知</c:otherwise>
											</c:choose>
											</td>
											<td>${objitem[7] }</td>
											<td>${objitem[8] }</td>
											<td>${objitem[9] }</td>
											<td>
												<c:choose>
													<c:when test="${objitem[10]==1 }">医生</c:when>
													<c:when test="${objitem[10]==2 }">护士</c:when>
													<c:when test="${objitem[10]==3 }">患者</c:when>
													<c:otherwise>其他</c:otherwise>
												</c:choose>
											
											</td>
											<td> ${objitem[11] }</td>
											<td>${objitem[5] }</td>
											<td>
											<button	class="label label-info btn btn-primary btn-mini"
													onclick="getBeInviter(${objitem[0]});">查看</button>
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
						<tags:ajaxPagination scripts="ajaxGetInviterList" divId="inviterTableId" queryFormId="inviterQueryForm" page="${page }" ></tags:ajaxPagination>
						</tbody>
						</table>