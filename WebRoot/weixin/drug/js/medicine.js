// JavaScript Document
/////////////////////////////////////////////////////////////////////////

$(document).ready(function () {
    $(".panel").find(".open").click(function () {
        $(this).siblings(".most").show();
        $(this).siblings(".minority").hide();
        $(this).hide();
        $(this).siblings(".retract").show();
    });
    $(".panel").find(".retract").click(function () {
        $(this).siblings(".minority").show();
        $(this).siblings(".most").hide();
        $(this).siblings(".open").show();
        $(this).hide();
    });


    /////////////////////////////////////////////////////////////////////////
    $("#essential_t").click(function () {
        $(".essential").show().siblings().hide();
        $("#essential_t").addClass("one").siblings().removeClass("one");
    });
    $("#sponsor_t").click(function () {
        $(".sponsor").show().siblings().hide();
        $("#sponsor_t").addClass("one").siblings().removeClass("one");
    });
    $("#experiment_t").click(function () {
        $(".experiment").show().siblings().hide();
        $("#experiment_t").addClass("one").siblings().removeClass("one");
    });
    $("#researcher_t").click(function () {
        $(".researcher").show().siblings().hide();
        $("#researcher_t").addClass("one").siblings().removeClass("one");
    });

    /////////////////////////////////////////////////////////////////////////
    $(".patient_submit").click(function(){
        $("#mask").css("height", $(document).height());
        $("#mask").css("width", $(document).width());
        $(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
        $("#mask").show();
        $(".box").show();
    });
    $(".close_box").click(function(){
        $("#mask").hide();
        $(".box").hide();
		$(document.body).css({"overflow-x":"hidden","overflow-y":"scroll"});
    });

});

/////////////////////////////////////////////////////////////////////////

$(document).ready(function () {
    $("input,textarea").focus(function () {
        $("#search_input").css("background-color", "#f8f8f8");
        $(".nav").hide();
    });
    $("input,textarea").blur(function () {
        $("#search_input").css("background-color", "#ffffff");
        $(".nav").show();
    });

});
/////////////////////////////下面是main代码//////////////////////////////////////

function showmask() {
    $("#mask").css("height", $(document).height());
    $("#mask").css("width", $(document).width());
}
/////////////////////////////////////////////////////////////////////////
