<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped with-check">
              <thead>
                <tr>
                  <th><input type="checkbox" id="title-table-checkbox" name="title-table-checkbox" /></th>
                  <th>模块</th>
                  <th>操作人</th>
                  <th>操作内容</th>
                  <th>操作时间</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.sysloglist.size>0">
                <s:iterator value="#request.sysloglist" var="syslog">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                  <td><s:property value="module"/></td>
                   <td><s:property value="operater"/>  </td>
                   <td><s:property value="opdesc"/>
                  </td>
                  <td><s:date name="opDate" format="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="5">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination scripts="ajaxGetSyslogList" divId="syslogTableId" queryFormId="syslogForm" page="${page }"></tags:ajaxPagination>
