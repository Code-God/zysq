/**
 * Created by raytine on 2017/9/25.
 */
(function(){
    var currYear = (new Date()).getFullYear();
    var opt={};
    opt.date = {preset : 'date'};
    opt.default = {
        theme: 'android-ics light', //皮肤样式
        display: 'modal', //显示方式
        mode: 'scroller', //日期选择模式
        dateFormat: 'yy-mm-dd',
        lang: 'zh',
        showNow: true,
        nowText: "今天",
        startYear: currYear - 100, //开始年份
        endYear: currYear //结束年份
    };
    $("#birthDate").mobiscroll($.extend(opt['date'], opt['default']));
})();