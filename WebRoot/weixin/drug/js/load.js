/**
 * Created by raytine on 2017/3/9.
 */
$(document).ready(function(){ 
	//首次加载
	var pageStatus = true; //状态
	var page = 1;     //当前页数
	var itemname = $("#itemname").val();
	var  lode = function(){
	    if(pageStatus){
	    	pageStatus = false;
	    	
	        $.ajax({
	        	type: "GET",
	             url: "/zysq/drug/index_getCDEData.Q",
	             data: {key:itemname,pageIndex:page},
	             dataType: "JSON",
	             contentType: "application/x-www-form-urlencoded; charset=utf8",
	             scriptCharset: 'utf8',
	            beforeSend:function() {
	                //开始加载中效果
	                $(".show-more-kj").find(".circle,.circle1").fadeIn(100);
	            },
	            complete:function(data) {
	                //结束加载中效果
	                $(".show-more-kj").find(".circle").fadeOut(300);
	                $(".show-more-kj").find(".circle1").fadeOut(500);
	            }
	        }).done(function(data){
	            if(data==""||data==undefined||data==null||data==0){
	                $(".show-more-kj").hide();
	                return false;
	            }
	
	            htmlList = "";
	            $.each(data,function(index,value){
	                htmlList += '<a class="panel_search_body" href="/zysq/drug/index_getJsureDetailed.Q?id='+value[0]+'">';
	                htmlList += '<strong>'+value[2]+'</strong>';
	                htmlList += '<p>'+value[3]+'</p>';
	                htmlList += '<span>来源：CDE</span>&nbsp;&nbsp;<span>登记号：'+value[1]+'</span>';
	                htmlList += '</a>';
	            });
	            $('.panel_search').append(htmlList);

                    if(data.length<10){
	                return false;
	            }
	            
	            pageStatus = true;
	
	            page+= 1;
	        });
	    }else {
	        $(".show-more-kj").hide();
	    }
	};
	
	lode();
	
	//滑动到底部加载更多
	$(window).scroll(function(){
	    //滚动到底部加载
	    if($(document).scrollTop() >= $(document).height() - $(window).height()){
	        lode();
	    }
	});
}); 