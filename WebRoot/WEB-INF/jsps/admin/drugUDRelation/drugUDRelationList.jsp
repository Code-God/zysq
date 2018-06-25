<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>					
						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>序号</th>
									<th>时间</th>
									<th>姓名</th>
									<th>省市</th>
									<th>出生日期</th>
									<th>联系方式</th>
									<th>推荐人</th>
									<th>项目名称</th>
									<th>报名状态</th>
								</tr>
							</thead>
							<tbody id="dataList">
								<s:if test="#request.obj.size>0">
									<s:iterator value="#request.obj" var="objitem" status="index">
										<tr>												
											<td>
												 <s:property value="#index.index+1"/>
											</td>
											<td>${objitem[6]}</td>
											<td>${objitem[1]}</td>
											<td>${objitem[7]}/${objitem[8]}</td>
											<td>${objitem[9]}</td>
											<td>${objitem[2]}</td>
											<td>${objitem[3]}</td>
											<td>${objitem[4]}</td>
											<td>
											
												<s:if test="#objitem[5]==null || #qulifitem[5]==0">
													未审核
												</s:if>
												<s:elseif test="#objitem[5]==11">
													病史审核通过
												</s:elseif>
												
												<s:elseif test="#objitem[5]==12">
													病史审核未通过
												</s:elseif>
												
												<s:elseif test="#objitem[5]==21">
													联系中通过
												</s:elseif>
												
												<s:elseif test="#objitem[5]==22">
													联系中未通过
												</s:elseif>
												
												<s:elseif test="#objitem[5]==31">
													医院筛选通过
												</s:elseif>
												
												<s:elseif test="#objitem[5]==32">
													医院筛选未通过
												</s:elseif>
												
												<s:elseif test="#objitem[5]==41">
													审核通过
												</s:elseif>
												
												<s:elseif test="#objitem[5]==42">
													审核未通过
												</s:elseif>
												<s:else>
													待审核
												</s:else>
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
						<tags:ajaxPagination scripts="ajaxGetRelationList" divId="relationTableId" queryFormId="relationQueryForm" page="${page }" ></tags:ajaxPagination>
						</tbody>
						</table>