<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>					
						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>疾病名称</th>
									<th>登记号</th>
									<th>疾病概要</th>
									<th>状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="dataList">
								<s:if test="#request.diseaseDictList.size>0">
									<s:iterator value="#request.diseaseDictList" var="diseaseDict">
										<tr>												
											<td>
											<input type="checkbox" name="diseaseItemId" value="<s:property value="id"/>">
											<s:property value="dicDiseaseName"/></td>
											<td><s:property value="ctrId" /></td>
											<td><s:property value="dicDiseaseProfile" /></td>
											<td>
											<s:if test="#diseaseDict.state==1">
											 进行中_尚未招募
											   </s:if>
											<s:elseif test="#diseaseDict.state==2">
											   进行中_招募中
											   </s:elseif>
											   <s:elseif test="#diseaseDict.state==3">
											 进行中_招募完成
											   </s:elseif>
											   <s:elseif test="#diseaseDict.state==4">
											 已完成
											   </s:elseif>
											   <s:elseif test="#diseaseDict.state==5">
												 主动暂停
											   </s:elseif>
											   <s:else>
											   		被叫停
											   </s:else>
											</td>
											<td><button	class="label label-info btn btn-primary btn-mini"
													onclick="updateDisease('<s:property value="id"/>');">修改</button></td>
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