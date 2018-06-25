<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>					
						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>姓名</th>
									<th>性别</th>
									<th>医院</th>
									<th>科室</th>
									<th>职称</th>
									<th>审核状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="dataList">
								<s:if test="#request.qulifiList.size>0">
									<s:iterator value="#request.qulifiList" var="qulifitem">
										<tr>												
											<td>
											<input type="checkbox" name="itemclassid" value="<s:property value="id"/>">
												${qulifitem[1]}
											</td>
											<td>
												<s:if test="#qulifitem[2]==2">
													女
												</s:if>
												<s:else>
													男
												</s:else>
											</td>
											<td>${qulifitem[3]}</td>
											<td>${qulifitem[4]}</td>
											<td>${qulifitem[5]}</td>
											<td>
												<s:if test="#qulifitem[6]==null || #qulifitem[6]==0">
													未审核
												</s:if>
												<s:elseif test="#qulifitem[6]==1">
													审核通过
												</s:elseif>
												<s:else>
													审核不通过
												</s:else>
											</td>
											<td><button	class="label label-info btn btn-primary btn-mini"
													onclick="updateQualification('${qulifitem[0]}');">审核</button></td>
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
						<tags:ajaxPagination scripts="ajaxGetQualificationList" divId="qualificationTableId" queryFormId="qualificationQueryForm" page="${page }" ></tags:ajaxPagination>
						</tbody>
						</table>