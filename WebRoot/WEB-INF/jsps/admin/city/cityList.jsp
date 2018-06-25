<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>序号</th>
                  <th>城市名称</th>
                  <th>城市编码</th>
                  <th>是否支持</th>
                  <th>是否热门</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
              	<c:forEach items="${page.data}" var="city" varStatus="num">
              	<tr>
                  <td>${num.index + 1}</td>
                  <td>${city.name}</td>
                  <td>${city.code}</td>
                  <td>
				  	<c:choose>
                  		<c:when test="${city.support == true}"><font color="red">支持</font></c:when>
                  		<c:when test="${city.support == false}">不支持</c:when>
                  	</c:choose>
				  </td>
                  <td>
                  	<c:choose>
                  		<c:when test="${city.hot == true}"><font color="red">热门</font></c:when>
                  		<c:when test="${city.hot == false}">普通</c:when>
                  	</c:choose>
                  </td>
                  <td>
                  	<c:choose>
                  		<c:when test="${city.support == true}"><button class="label label-info btn btn-primary btn-mini" onclick="setSupport('${city.id }', false);">取消支持</button></c:when>
                  		<c:when test="${city.support == false}"><button class="label label-info btn btn-primary btn-mini" onclick="setSupport('${city.id }', true);">设置支持</button></c:when>
                  	</c:choose>
                  	<c:choose>
                  		<c:when test="${city.hot == true}"><button class="label label-info btn btn-primary btn-mini" onclick="setHot('${city.id }', false);">取消热门</button></c:when>
                  		<c:when test="${city.hot == false}"><button class="label label-info btn btn-primary btn-mini" onclick="setHot('${city.id }', true);">设置热门</button></c:when>
                  	</c:choose>
                  	
                  </td>
                </tr>
              	</c:forEach>
               </s:if>
                <s:else>
					<tr>
						<td colspan="6">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination scripts="ajaxGetProductsList" divId="cityTableId" queryFormId="cityQueryForm" page="${page }"></tags:ajaxPagination>
