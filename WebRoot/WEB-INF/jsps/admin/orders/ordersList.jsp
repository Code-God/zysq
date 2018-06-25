<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped with-check" id="commentsTableId">
              <thead>
                <tr>
                  <th><input type="checkbox" id="title-table-checkbox" name="title-table-checkbox" /></th>
                  <th>订单号</th>
                  <th>订单金额</th>
                  <th>订单状态</th>
                  <th>下单用户</th>
                  <th>下单时间</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.orderslist.size>0">
                <s:iterator value="#request.orderslist" var="orders">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                  <td><a href='<%=request.getContextPath()%>/admin/orders_ordersDetai.Q?orderNo=<s:property value="orderNo"/>' target="_blank"> <s:property value="orderNo"/> </a></td>
                   <td><s:property value="fee"/>  </td>
                   <td>
                  <s:if test="#orders.status==0">未支付</s:if>
                  	<s:elseif test="#orders.status==1"><font color="green">已支付</font></s:elseif>
                  	<s:elseif test="#orders.status==2">已发货</s:elseif>
                  	<s:elseif test="#orders.status==3">已完成</s:elseif>
                  	<s:else><font color="red">已取消</font></s:else>
                  </td>
                  <td>  <s:property value="user.nickName"/> <s:property value="user.username"/></td>
                  <td><s:date name="odate" format="yyyy-MM-dd HH:mm:ss" /></td>
                  <td>
                  <s:if test="#orders.status==1">
                  <button class="label label-info btn btn-primary btn-mini" onclick="changeOrderState('<s:property value="orderNo"/>',2);">发货</button>
                  </s:if>
                  <s:if test="#orders.status==2">
                  <button class="label label-info btn btn-primary btn-mini" onclick="changeOrderState('<s:property value="orderNo"/>',3);">完成</button>
                  </s:if>
                  	 <button class="label label-info btn btn-primary btn-mini" onclick="detail('<s:property value="orderNo"/>');">详情</button>
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
				<tr>
						<td colspan="7">
							 <div class="alert alert-block"> 
				                         <tags:ajaxPagination scripts="ajaxGetCommentsList" divId="commentsTableId" queryFormId="commentsQueryForm" page="${page }"></tags:ajaxPagination>
				             </div>
						</td>
					</tr>
              </tbody>
            </table>
