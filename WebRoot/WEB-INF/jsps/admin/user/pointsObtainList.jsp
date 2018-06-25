<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5><span style="color:#51a351;">${user.nickName}</span>的获取积分列表</h5>
         </div>
            <table class="table table-bordered table-striped with-check">
              <thead>
                <tr>
                  <th width="10%">序号</th>
                  <th width="20%">获得积分数</th>
                  <th width="30%">获取时间</th>
                  <th width="40%">积分来源</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.obtainList.size>0">
                <s:iterator value="#request.obtainList" var="score" status="st">
                 <tr>
                  <td><s:property value="#st.index+1"/></td>
                  <td><s:property value="score"/></td>
                  <td><s:date name="opdate" format="yyyy-MM-dd HH:mm:ss" /></td>
                  <td>
                  <s:if test="#score.source==0">
                  	基本积分
                  </s:if>
                  <s:elseif test="#score.source==1">
                 	 完成推荐加分
                  </s:elseif>
                  <s:elseif test="#score.source==2">
                 	完成报名加分
                  </s:elseif>
                  <s:elseif test="#score.source==3">
                 	医生认证结束加分
                  </s:elseif>
                  <s:elseif test="#score.source==4">
                 	完善个人信息加分
                  </s:elseif>
                  <s:elseif test="#score.source==6">
                 	推荐好友成功得分
                  </s:elseif>
                   <s:else>
                 	人工审核通过加分
                  </s:else>
                
                  
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
          
