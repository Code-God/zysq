<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5><span style="color:#51a351;">${user.nickName}</span>的兑现积分列表</h5>
         </div>
            <table class="table table-bordered table-striped with-check">
              <thead>
                <tr>
                  <th width="10%">序号</th>
                  <th width="20%">兑换积分数</th>
                  <th width="30%">兑换时间</th>
                  <th width="40%">备注</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.exchangeList.size>0">
                <s:iterator value="#request.exchangeList" var="score" status="st">
                 <tr>
                  <td><s:property value="#st.index+1"/></td>
                   <td id="nickName" hidden="hidden"><s:property value="nickName"/></td>
                  <td><s:property value="score"/></td>
                  <td><s:date name="opdate" format="yyyy-MM-dd HH:mm:ss" /></td>
                  <td>
                  	管理员操作
                  </td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="10">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination scripts="ajaxGetUserList" divId="userTableId" queryFormId="userQueryForm" page="${page }"></tags:ajaxPagination>
          
