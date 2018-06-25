<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped with-check">
              <thead>
                <tr>
                  <th><input type="checkbox" id="title-table-checkbox" name="title-table-checkbox" /></th>
                  <th>商品名</th>
                  <th>商品编码</th>
                  <th>评价用户</th>
                  <th>评价级别</th>
                  <th>评价内容</th>
                  <th>评价日期</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.commentslist.size>0">
                <s:iterator value="#request.commentslist" var="comments">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                  <td> <s:property value="prdName"/> </td>
                   <td> <s:property value="prdid"/>  </td>
                  <td><s:property value="pjerName"/></td>
                  <td>
                  <s:if test="#comments.stars==1">很不满意</s:if>
                  	<s:elseif test="#comments.stars==2">不满意</s:elseif>
                  	<s:elseif test="#comments.stars==3">一般</s:elseif>
                  	<s:elseif test="#comments.stars==4">满意</s:elseif>
                  	<s:else>非常满意</s:else>
                  </td>
                  <td><s:property value="content"/></td>
                  <td><s:property value="pjDate"/> </td>
                  <td><!-- 
                  	<button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate('<s:property value="id"/>');">回复</button>
                   -->
                  </td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="8">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination scripts="ajaxGetCommentsList" divId="commentsTableId" queryFormId="commentsQueryForm" page="${page }"></tags:ajaxPagination>
