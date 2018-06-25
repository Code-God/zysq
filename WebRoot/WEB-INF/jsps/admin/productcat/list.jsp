<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped with-check">
              <thead>
                <tr>
                  <th><input type="checkbox" id="title-table-checkbox" name="title-table-checkbox" /></th>
                  <th>序号</th>
                  <th>分类名称</th>
                  <th>分类编码</th>
                  <!-- 
                  <th>图片的URL</th>
                   -->
                  <th>是否是推荐</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
              	<c:forEach items="${page.data}" var="x" varStatus="num">
              	<tr>
                  <td><input type="checkbox" name="ids" value="${x.id }"/></td>
                  <td>${num.index + 1}</td>
                  <td>${x.name}</td>
                  <td>${x.code}</td>
                  <!-- 
                  <td><a href="${imgPath}${x.picUrl}" target="_blank">${x.picUrl}</a></td>
                   -->
                  <td>
                  	<c:choose>
                  		<c:when test="${x.recommend == 0}">不推荐</c:when>
                  		<c:when test="${x.recommend == 1}">推荐</c:when>
                  		<c:otherwise>不推荐</c:otherwise>
                  	</c:choose>
                  </td>
                  <td>
                  	<button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate('${x.id }');">修改</button>
                  </td>
                </tr>
              	</c:forEach>
               </s:if>
                <s:else>
					<tr>
						<td colspan="9">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination scripts="ajaxGetProductsList" divId="productTableId" queryFormId="productCatQueryForm" page="${page }"></tags:ajaxPagination>
