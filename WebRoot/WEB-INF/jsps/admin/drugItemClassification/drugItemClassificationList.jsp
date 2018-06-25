<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>					
						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>类型名称</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="dataList">
								<s:if test="#request.itemClasslist.size>0">
									<s:iterator value="#request.itemClasslist" var="admin">
										<tr>												
											<td>
											<input type="checkbox" name="itemclassid" value="<s:property value="id"/>">
											<s:property value="classificationName" />
											</td>
										
											<td><button	class="label label-info btn btn-primary btn-mini"
													onclick="updateItemClass('<s:property value="id"/>');">修改</button></td>
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
						<tags:ajaxPagination scripts="ajaxGetItemClassList" divId="itemClassTableId" queryFormId="itemClassQueryForm" page="${page }" ></tags:ajaxPagination>
						</tbody>
						</table>