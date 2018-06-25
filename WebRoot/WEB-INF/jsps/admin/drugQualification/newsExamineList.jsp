<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>					
						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>接收方</th>
									<th>发送方</th>
									<th>创建时间</th>
									<th>消息类型</th>
									<th>图片链接</th>
									<th>审核状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="dataList">
								<s:if test="#request.qulifiList.size>0">
									<s:iterator value="#request.qulifiList" var="qulifitem">
										<tr>												
											<td>
											<input type="checkbox" name="itemclassid" value="<s:property value="id"/>">找药神器公众号
											</td>
											<td>${qulifitem[3]}</td>
											<td id="adminOPenid" hidden>${qulifitem[1]}</td> <!-- 公众号openid -->
											<td id="openid" hidden>${qulifitem[2]}</td> <!-- 用户openid -->
											<td>${qulifitem[5]}</td>
											<td>${qulifitem[4]}</td>
											<td><a href="${imgurlpath}${qulifitem[6]}">图片链接</a></td>
											<td hidden>${qulifitem[7]}</td> <!-- 媒体id -->
											<td hidden>${qulifitem[8]}</td> <!-- 消息ID -->
											<td>
												<s:if test="#qulifitem[9]==null || #qulifitem[9]==0">
													未审核
												</s:if>
												<s:elseif test="#qulifitem[9]==1">
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