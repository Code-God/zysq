<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
						<table class="table table-bordered table-striped with-check">
							<thead>
								<tr>
									<th>
										<input type="checkbox" id="title-table-checkbox" name="title-table-checkbox" />
									</th>
									<th>
										序号
									</th>
									<th>
										广告类型
									</th>
									<th>
										广告图片
									</th>
									<th>
										广告地址
									</th>
									<th>
										有效期
									</th>
									<th>
										操作
									</th>
								</tr>
							</thead>
							<tbody>
								<s:if test="#request.page.data.size>0">
									<c:forEach items="${page.data}" var="ad" varStatus="num">
										<tr>
											<td>
												<input type="checkbox" name="ids" value="${ad.id }" />
											</td>
											<td>
												${num.index + 1}
											</td>
											<td>
												<c:choose>
													<c:when test="${ad.adType == 1}">普通广告</c:when>
													<c:when test="${ad.adType == 2}">幻灯片广告</c:when>
													<c:otherwise>普通广告</c:otherwise>
												</c:choose>
											</td>
											<td>
												<a href="<%=request.getContextPath()%>/${ad.picUrl}" target="_blank">${ad.picUrl}</a>
											</td>
											<td>
												${ad.url}
											</td>
											<td>
												<c:choose>
													<c:when test="${ad.dueDate == null}"><font color="green">永久有效</font></c:when>
													<c:when test="${ad.expire}"><font color="red">已过期</font></c:when>
													<c:when test="${ad.dueDate != null && ad.expire == false}">${ad.dueDate}</c:when>
													<c:otherwise></c:otherwise>
												</c:choose>
											</td>
											<td>
												<button class="label label-info btn btn-primary btn-mini"
													onclick="addOrUpdate('${ad.id }');">
													修改
												</button>
											</td>
										</tr>
									</c:forEach>
								</s:if>
								<s:else>
									<tr>
										<td colspan="9">
											<div class="alert alert-block">
												<h4 align="center" class="alert-heading">
													暂时没有符合条件的记录！
												</h4>
											</div>
										</td>
									</tr>
								</s:else>
							</tbody>
						</table>
						<tags:ajaxPagination scripts="ajaxGetProductsList" divId="adTableId" queryFormId="adQueryForm" page="${page }"></tags:ajaxPagination>