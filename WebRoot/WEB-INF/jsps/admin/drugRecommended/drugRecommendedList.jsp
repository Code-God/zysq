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
									<th>报名时间</th>
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
											<td>${objitem[1]}</td>
											<td>${objitem[2]}</td>
											<td>${objitem[3]}</td>
											<td>${objitem[4]}</td>
											<td><button	class="label label-info btn btn-primary btn-mini"
													onclick="getUsersList('${objitem[0]}');">查看</button></td>
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
						<tags:ajaxPagination scripts="ajaxGetRecommendedList" divId="recommendedTableId" queryFormId="recommendedQueryForm" page="${page }" ></tags:ajaxPagination>
						</tbody>
						</table>