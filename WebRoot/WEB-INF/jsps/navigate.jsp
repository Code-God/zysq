<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function(){
		$.getJSON('<%=request.getContextPath()%>/public/productcat_category.Q',function(data){
			if(data){
				var catHtml = '';
				for(var i=0; i<data.length; i++){
					var category = data[i];
					var html = '<li class="mod_cate_li"><div class="mod_cate_r1"><h3><a href="<%=request.getContextPath()%>/public/list.Q?code=' + category.code + '">' + category.name + '</a></h3><i class="mod_cate_arrow"></i><div class="mod_subcate"><div class="mod_subcate_main">';
					var children1 = category.children;
					if(children1){
						for(var j=0; j<children1.length; j++){
							var category2 = children1[j];
							var html2 = '<dl><dt>' + category2.name + '</dt><dd>';
							var children2 = category2.children;
							if(children2){
								for(var k=0; k<children2.length; k++){
									var category3 = children2[k];
									var html3 = '<a href="<%=request.getContextPath()%>/cat/list/' + category3.code + '">' + category3.name + '</a>';
									html2 += html3;
								}
							}
							html2 += '</dd></dl>';
							html += html2;
						}
					}
					html += '</div></div></div></li>';
					
					catHtml += html;
				}
				$('#frist_list').html(catHtml);
				
				/*左侧菜单*/
				$("#category_container .mod_cate_bd").slide({
				type:"menu",
				titCell:".mod_cate_li",
				targetCell:".mod_subcate",
				delayTime:0,
				triggerTime:10,
				defaultPlay:false,
				returnDefault:true
			    });
			}
		});
	});
</script>
<div class="wf_nav" id="j_nav">
  <div class="grid_c1">
    <div class="mod_cate ${ !empty isIndex ? 'mod_cate_on' : '' }" id="category_container">
      <div class="mod_cate_hd"><a href="<%=request.getContextPath() %>/index.html">全部商品分类</a>${ empty isIndex ? "<i class='mod_cate_hd_arrow'></i>" : "" }</div>
      <ul class="mod_cate_bd" id="frist_list">
	  </ul>
    </div>
    <div class="mod_nav">
      <ul class="mod_nav_ul" id="j_hornav">
        <li class="mod_nav_li"><a target="_blank" href="<%=request.getContextPath() %>/index.html">首页</a></li>
        <li class="mod_nav_li"><a target="_blank" href="#">大闸蟹</a></li>
        <li class="mod_nav_li"><a target="_blank" href="#">早餐</a></li>
        <li class="mod_nav_li"><a target="_blank" href="#">澳洲牛肉</a></li>
        <li class="mod_nav_li"><a target="_blank" href="#">进口牛奶</a></li>
        <li class="mod_nav_li"><a target="_blank" href="#">上海当日达</a></li>
      </ul>
    </div>
  </div>
</div>