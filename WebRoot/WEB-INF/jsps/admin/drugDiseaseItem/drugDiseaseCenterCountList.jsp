<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>					
						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>中心名称</th>
									<th>数量</th>
									<th>申办方</th>
								</tr>
							</thead>
							<tbody id="dataList">
								<s:if test="#request.diseaseCenterCountList.size>0">
									<s:iterator value="#request.diseaseCenterCountList" var="admin">
										<tr>																				
											<td><s:property value="#admin[0]" /></td>
											<td><s:property value="#admin[1]" /></td>
                                            <td><s:property value="#admin[2]" escape="false" /></td>
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
						<tags:ajaxPagination scripts="ajaxGetDiseaseList" divId="diseaseTableId" queryFormId="diseaseQueryForm" page="${page }" ></tags:ajaxPagination>
						</tbody>
						</table>