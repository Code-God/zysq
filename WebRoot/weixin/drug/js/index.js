/**
 * Created by raytine on 2017/3/20.
 */
(function(){
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        autoplay: 3000,
        autoplayDisableOnInteraction: false
    });

    //显示搜索
    $(".search-contend").on('click',function(){
        $(".search").addClass("show");
        $(".search-input input").focus();
        $(".index-mark,.search-list").fadeIn(500);
        $(".swiper-container,.contend,.fixed-menu").addClass("blur");
        $("body,html").css("overflow","hidden");
    });

    //隐藏搜索
    $(".index-mark").on('click',function(){
        $(".search").removeClass("show");
        $(".index-mark,.search-list").fadeOut(500);
        $(".swiper-container,.contend,.fixed-menu").removeClass("blur");
        $(".search-input input").val("");
        $("body,html").css("overflow","");
    })
})();