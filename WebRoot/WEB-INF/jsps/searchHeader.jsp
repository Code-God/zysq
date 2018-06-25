<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/public/index.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/public/search.js"></script>
<style>
#shelper {
overflow: hidden;
position: absolute;
top: 35px;
left: 0px;
width: 360px;
border: 1px solid #63D172;
background: #fff;
-moz-box-shadow: 0 0 5px #999;
-webkit-box-shadow: 0 0 5px #999;
box-shadow: 0 0 5px #999;
}
#shelper li {
line-height: 20px;
font-size: 16px;
padding: 5px;
}
#shelper li:link{
	//border:1px solid #3B9966;
}
#shelper li:hover{
	//border:1px solid #3B9966;
	background:#63D172;
	color:#FFFFFF;
}
</style>
<div class="wf_header">
  <div class="grid_c1">
    <div class="mod_logo">
      <h1><a href="<%=request.getContextPath() %>/index.html"><img src="<%=request.getContextPath() %>/images/logo.jpg" alt="吴方商城"></a></h1>
    </div>
    <div class="mod_city">
      <div class="mod_city_choose">
        <p class="mod_city_tit">送至</p>
        <div class="mod_city_con" id="j_city_con" fill="true"> <a href="javascript:" class="mod_city_goal"><span id="j_city_name">${sessionScope.CURR_CITY.name }</span><i>&nbsp;</i></a>
          <div class="mod_city_list">
            <div class="mod_storage_con" id="j_storage_con">
              <dl class="mod_storage_item">
                <dd class="mod_storage_city"></dd>
              </dl>
              <p class="mod_storage_state">商品暂时只支持配送至中国大陆地区</p>
            </div>
            <div class="mod_storage_ing hide">
              <p>正在切换<span class="c_tx0">...</span></p>
            </div>
            <div class="mod_storage_error hide">
              <p><i class="ico_info_mini"></i>切换失败，请<a href="javascript:;" class="lk_0">返回重试</a></p>
            </div>
          </div>
        </div>
        <p class="mod_city_prom hide" id="j_dispatch_prom" style="display:block;"><a target="_blank" href="" title="购物满99元免运费">购物满99元免运费</a></p>
      </div>
    </div>
    <div class="search_cart_wrap" id="j_search">
     <div class="mod_seach">
      <form name="search" action="<%=request.getContextPath() %>/public/list.Q" method="get">
      	<input id="searchTxt" class="mod_search_con mod_search_txt no_cur" type="text" name="keyword" accesskey="s" tabindex="8" autocomplete="off" x-webkit-speech="" x-webkit-grammar="builtin:search" value="${keyword }"/>
      	<a>
      	<ul id="shelper" style="display: none">
		<li>&nbsp;</li>
		<li>&nbsp;</li>
		<li>&nbsp;</li>
		<li>&nbsp;</li>
      	</ul>
      	</a>
        <input class="mod_search_btn" tabindex="9" hotname="I.HEADER.SEARCHBTN" value="搜索" type="submit" >
      </form>
      </div>
      <div class="mod_mark"></div>
    </div>
  </div>
</div>