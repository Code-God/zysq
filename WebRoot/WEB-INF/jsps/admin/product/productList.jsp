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
                  <th>商品规格</th>
                  <th>商品价格</th>
                  <!-- 
                  <th>全网销量</th>
                   -->
                  <th>折扣价</th>
                  <th>所属分类</th>
                  <th>展示方式</th>
                  <th>发布日期</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.productslist.size>0">
                <s:iterator value="#request.productslist" var="product">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                  <td><s:property value="name"/></td>
                   <td><font color=red><s:property value="prdCode"/></font></td>
                  <td><s:property value="prdStandard"/></td>
                  <td><s:property value="prdPrice"/></td>
                  <!-- 
                  <td><span id="sctxt_<s:property value="id"/>" style="color:red"><s:property value="prdSaleCount"/></span>
                  <button onclick="showScModDiv(<s:property value="id"/>)" class="label label-info btn btn-primary btn-mini">修改</button>
                  <div id="prdSCDiv_<s:property value="id"/>" style="display: none;">
                  	<input type="text" id="sc_<s:property value="id"/>" style="width: 40px"><br>
                  	<button onclick="modCount(<s:property value="id"/>)" class=" btn-success btn-mini">确定</button>
                  	<button onclick="hideDiv(<s:property value="id"/>)" class=" btn-success btn-mini">取消</button>
                  </div>
                  </td>
                   -->
                  <td><s:property value="prdDisPrice"/></td>
                  <td><s:property value="prdCatName"/></td>
                  <td>
                  	<s:if test="#product.recommend==0">一行两个</s:if>
                  	<s:elseif test="#product.recommend==1"><font color=red>推荐</font></s:elseif>
                  	<s:else>横向大图</s:else>
                  </td>
                  <td><s:date name="createDate" format="yyyy-MM-dd hh:mm:ss" /></td>
                  <td>
                  <!-- 
                  	<button class="label label-info btn btn-primary btn-mini" onclick="viewStock('<s:property value="prdCode"/>');">查看库存</button>
                   -->
                  	<button class="label label-info btn btn-primary btn-mini" onclick="detail('<s:property value="id"/>');">详情</button>
                  	<button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate('<s:property value="id"/>');">修改</button>
                  </td>
                </tr>
               </s:iterator>
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
          <tags:ajaxPagination scripts="ajaxGetProductsList" divId="productTableId" queryFormId="productsQueryForm" page="${page }"></tags:ajaxPagination>
