/**
 * Created by raytine on 2017/3/21.
 */
(function(){
    //上传图片
    var up = new $.UpImg;
    var btn = $(".up-img");
    up.upImgs(btn);

    //上传图片验证及设置id
    function showMedicalHistoryImg(flag, imgId, imgName){
        //var flag = response.flag;
        if (flag == "1") {
            alert("您上传的病历格式不对，请重新选择！");
        }
        else if (flag == "2") {
            alert("您上传的病历大于3M，请重新选择！");
        }
        else if (flag == "3") {
            alert("病历上传失败！");
        }
        else {
            $(".up-img").prev(".form-img").attr("imgname",imgName);
        }
    }

    //添加删除事件
    $(".img-delete").on('click',function(){
        var imgName = $(this).parent().attr("imgname");
        // 删除服务端的临时附件
        if (imgName != '') {
            $.ajax({
                url: '${ctx}/drug/user_deleteTempFile.Q',
                type: "post",
                data: { "imgName": imgName },
                dataType:"json",
                success:function(result){
                }
            });
        }
        $(this).parent(".form-img").remove();
    });

    //图片放大预览
    var box = new $.Box;
    var btn4 = $(".form-img");
    var boxObj = $(".img-box");
    var imgClose = $(".img-box-close");
    box.alertBox(btn4,boxObj,imgClose);

    $(".form-img").on("click",function () {
        var src = $(this).find("img").attr("src");
        boxObj.find("img").attr("src",src);
    });

    //打开弹框
    var btn2 = $(".xy-btn");
    var obj = $(".box");
    var close = $(".box-close");
    box.alertBox(btn2,obj,close);

    //日历
    $(function () {
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
    });

    //同意协议
    $(".form-xy-icon").on("click",function () {
        if($(this).hasClass("active")){
            $(this).removeClass("active");
            $(".form-xy input").removeAttr("checked");
        }else {
            $(this).addClass("active");
            $(".form-xy input").attr("checked",'true');
        }
    });
    $(".again").on("click",function () {
        $(".form-xy-icon").addClass("active");
        $(".form-xy input").attr("checked",'true');
        $(".box-close").click();
    });

    //获取验证码
    var text = new $.Text;
    var btn3 = $(".get-yzm");
    var mobileInp = $(".mobile");
    text.verification(btn3,mobileInp);


    //输出自定义表单
    lodeFormStatus = true;
    function lodeForm(){
        var html="";
        var projectId = $("input[name='projectId']").val();
        var singupType = $("input[name='singupType']").val();
        if(projectId==""||projectId<=0||projectId==null){
            console.log(projectId);
            lodeFormStatus = false;
            return false;
        }
        //var data={"total":3,"pageCount":1,"pageIndex":1,"titles":[{"Column":"Type","Name":"类型","IsSort":false,"SortKey":null,"SortDire":null},{"Column":"Name","Name":"字段名","IsSort":false,"SortKey":null,"SortDire":null},{"Column":"IsShow","Name":"是否默认显示","IsSort":false,"SortKey":null,"SortDire":null},{"Column":"CreateTime","Name":"创建时间","IsSort":false,"SortKey":null,"SortDire":null}],"rows":[{"ID":26,"ProjectId":0,"Phase":null,"Field":"test","Name":"test","Type":"单行文本","TypeId":2,"Options":null,"IsRequired":1,"IsShow":1,"Attribute":null,"IsGroup":0,"GroupName":0,"Status":0,"CreateTime":"2017-09-20"},{"ID":27,"ProjectId":0,"Phase":null,"Field":"test2","Name":"test2","Type":"单选","TypeId":3,"Options":"tset|tset1|tset12","IsRequired":2,"IsShow":1,"Attribute":null,"IsGroup":0,"GroupName":0,"Status":0,"CreateTime":"2017-09-20"},{"ID":28,"ProjectId":0,"Phase":null,"Field":"test3","Name":"第三方","Type":"下拉菜单","TypeId":5,"Options":"是啥2|是啥23|是啥236","IsRequired":2,"IsShow":1,"Attribute":null,"IsGroup":0,"GroupName":0,"Status":0,"CreateTime":"2017-09-20"}]};
        $.ajax({
            url: '${ctx}/drug/user_getCustomData.Q?projectId='+projectId,
            type: "get",
            data: {},
            dataType:"json",
            success:function(data){
                var data = data.rows;
                if(data==""||data==null){
                    lodeFormStatus = false;
                    console.log("the data:"+data);
                    return false;
                }
                console.log(data);
                $.each(data, function(i, val) {
                    if(val.IsRequired==1){
                        var isMustClass = 'required';
                    }else {
                        var isMustClass = '';
                    }
                    if(val.IsShow==1){
                        switch (val.Type){
                            case 1: //多行文本
                                html+= '<div class="form-item '+isMustClass+'">';
                                html+= '<label><b>*</b>'+val.Name+'</label>';
                                html+= '<textarea class="form-item-input" name="'+val.Field+'" rows="3" cols=""></textarea>';
                                html+= '</div>';
                                break;
                            case 2:  //单行文本
                                html+= '<div class="form-item '+isMustClass+'"">';
                                html+= '<label><b>*</b>'+val.Name+'</label>';
                                html+= '<input class="form-item-input" type="text" name="'+val.Field+'" value=""/>';
                                html+= '</div>';
                                break;
                            case 3:  //单选
                                var OptionsArr = val.Options.split("|");
                                html+= '<div class="form-item-selects '+isMustClass+'"">';
                                html+= '<label><b>*</b>'+val.Name+'</label>';
                                html+= '<div class="radio-list clearafter">';
                                $.each(OptionsArr, function(key, v) {
                                    html+= '<label>';
                                    html+= '<input type="radio" value="'+(key+1)+'" name="'+val.Field+'">'+v;
                                    html+= '</label>';
                                });
                                html+= '</div>';
                                html+= '</div>';
                                break;
                            case 4:  //复选
                                var OptionsArr = val.Options.split("|");
                                html+= '<div class="form-item-selects '+isMustClass+'"">';
                                html+= '<label><b>*</b>'+val.Name+'</label>';
                                html+= '<div class="checkbox-list clearafter">';
                                $.each(OptionsArr, function(key, v) {
                                    html+= '<label>';
                                    html+= '<input type="checkbox" value="'+(key+1)+'" name="'+val.Field+'">'+v;
                                    html+= '<i class="multiple"></i>';
                                    html+= '</label>';
                                });
                                html+= '</div>';
                                html+= '</div>';
                                break;
                            case 5:  //下拉选择
                                var OptionsArr = val.Options.split("|");
                                html+= '<div class="form-item-selects '+isMustClass+'"">';
                                html+= '<label><b>*</b>'+val.Name+'</label>';
                                html+= '<select class="form-item-input sp-status" name="'+val.Field+'">';
                                html+= '<option value="">请选择</option>';
                                $.each(OptionsArr, function(key, v) {
                                    html+= '<option value="'+(key+1)+'">'+v+'</option>';
                                });
                                html+= '</select>';
                                html+= '</div>';
                                break;
                            default:
                                break;
                        }
                    }
                });
                $(".dynamicForm").append(html);
            },
            error:function(data,status){
                alert("服务器繁忙，请稍后刷新页面。")
            }
        });
    }
    lodeForm();


    //选择弹框--状态
    //$.fn.mScroller = function() {
    //    this.scroller({
    //        btnClass : 'sqh_color_56 font_16 sqh_line_height_15 margin_10 sqh_font_type',
    //        preset: 'select',
    //        lang: 'zh',
    //        theme: 'default',
    //        mode: 'scroller',
    //        display: 'modal',
    //        animate: 'pop',
    //        height:'50',
    //        minWidth: 300,
    //        onSelect:function(valueText){
    //            //$(this).find('option').removeAttr("selected");
    //            //$(this).find('option:contains('+valueText+')').click();
    //        }
    //    });
    //    return this;
    //};
    //
    //$('.sp-status').mScroller();
})();