<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld"%>
            <table class="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>区域</th>
                  <th>商品编码</th>
                  <th>商品名称</th>
                  <th>库存</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <c:if test="${fn:length(cities) > 0}">
              	<c:forEach var="city" items="${cities}">
	              	<tr>
	                  <td>${city.name }</td>
	                  <td>${productCode }</td>
	                  <td>${productName }</td>
	                  <c:if test="${fn:length(page.data) == 0}">
	                  	<td><input type="text" value="--" name="stock" readonly autocomplete="off" onkeyup="this.value=this.value.replace(/[^0-9]+/g,'')"  onafterpaste="this.value=this.value.replace(/[^0-9]+/g,'')"/>
	                  	<input type="hidden" value="--" name="raw"/></td>
	                  </c:if>
	                  <c:if test="${fn:length(page.data) > 0}">
	                  	<c:set var="flag" value="false"/>
	                  	<c:forEach var="stock" items="${page.data}">
	                  		<c:if test="${city.code == stock.cityCode}"><td><input type="text" value="${stock.stock }" name="stock" readonly autocomplete="off" onkeyup="this.value=this.value.replace(/[^0-9]+/g,'')"  onafterpaste="this.value=this.value.replace(/[^0-9]+/g,'')"/>
	                  		<input type="hidden" value="${stock.stock }" name="raw"/></td><c:set var="flag" value="true"/></c:if>
	                  	</c:forEach>
	                  	<c:if test="${flag == false}"><td><input type="text" value="--" name="stock" readonly autocomplete="off" onkeyup="this.value=this.value.replace(/[^0-9]+/g,'')"  onafterpaste="this.value=this.value.replace(/[^0-9]+/g,'')"/>
	                  	<input type="hidden" value="--" name="raw"/></td></c:if>
	                  </c:if>
	                  <td>
	                  	<button class="label label-info btn btn-primary btn-mini" onclick="editStock(this);">修改库存</button>
	                  	<button class="label label-info btn btn-primary btn-mini" onclick="saveStock(this,${city.code },'${productCode }');" style="display:none;">保存</button>
	                  	<button class="label label-info btn btn-primary btn-mini" onclick="canel(this);" style="display:none;">取消</button>
	                  </td>
	                </tr>
	              </c:forEach>
              </c:if>
              <c:if test="${fn:length(cities) == 0}">
              	<tr>
					<td colspan="5">
						 <div class="alert alert-block"> 
			               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
			             </div>
					</td>
				</tr>
              </c:if>
              </tbody>
            </table>
          <tags:ajaxPagination scripts="ajaxGetProductsStockList" divId="stockTableId" queryFormId="stockQueryForm" page="${page }"></tags:ajaxPagination>
