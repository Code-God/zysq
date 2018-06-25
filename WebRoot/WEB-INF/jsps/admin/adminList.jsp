<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped with-check">
              <thead>
                <tr>
                  <th> </th>
                  <th>用户名</th>
                  <th>角色</th>
                  <th>所属区域</th>
                  <th>公司名称</th>
                  <th>邮箱</th>
                  <th>手机</th>
                  <th>登陆次数</th>
                  <th>最后登陆时间</th>
                  <th>状态</th>
                  <th <s:if test='#request.pop == "1"'>style = "display:none"</s:if>>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.adminList.size>0">
                <s:iterator value="#request.adminList" var="admin">
                 <tr>
                  <td>
                  <s:if test='#request.pop == "1"'>
                   	<label>
	                  <input type="radio"  name="radioUser"  value='<s:property value="id"/>|<s:property value="username"/>' onclick='checkUser(this)'/>
                  	</label>
                  </s:if>
                  <s:else>
	                  <input type="checkbox" name="adminIds" value='<s:property value="id"/>'/>
                  </s:else>
                  </td>
                  <td><s:property value="username"/></td>
                  <td><s:property value="roleString"/></td>
                  <td><font color="#228B22"><s:property value="area"/></font></td>
                  <td> <s:property value="company"/> </td>
                  <td><s:property value="email"/></td>
                  <td><s:property value="phone"/></td>
                  <td><s:property value="logCount"/></td>
                  <td><s:date name="lastLoginDate" format="yyyy-MM-dd hh:mm:ss" /></td>
                  <td>
                  	<s:if test="#admin.status==1">启用</s:if>
                  	<s:else>禁用</s:else>
                  </td>
                  <td <s:if test='#request.pop == "1"'>style = "display:none"</s:if>><button class="label label-info btn btn-primary btn-mini" onclick="updateAdmin('<s:property value="username"/>');">修改</button></td>
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
          <tags:ajaxPagination scripts="ajaxGetAdminList" divId="adminTableId" queryFormId="adminQueryForm" page="${page }"></tags:ajaxPagination>
          <!--
          <div class="pagination alternate">
              <ul>
                <li class="disabled"><a href="#">上一页</a></li>
                <li class="active"> <a href="#">1</a> </li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">下一页</a></li>
              </ul>
            </div>
      -->
