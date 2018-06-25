(function () {
    //弹框
    $.Box = function () {
        //通用弹框
        this.alertBox = function (btn,obj,close) {
            btn.on('click',function () {
                obj.fadeIn(300);
                $(".mark").fadeIn(300);
                $("body,html").css("overflow","hidden");
            });
            close.on('click',function () {
                obj.fadeOut(300);
                $(".mark").fadeOut(300);
                $("body,html").css("overflow","visible");
            });
        };
    };

    //获取地址栏参数值
    $.getUrlParam = function(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r  = window.location.search.substr(1).match(reg);
        if (r!=null) return unescape(r[2]); return null;
    };

    //通用切换
    $(".c-switch-item").on("click",function () {
        $(this).closest(".c-switch").find(".c-switch-item").removeClass("cur");
        $(this).addClass("cur");
    });

    //单选点击
    $(document).on('click',".radio-list label",function(){
        $(this).parent().find("label").removeClass("cur");
        $(this).addClass("cur");
    });
    //多选点击
    $(document).on('click',".checkbox-list label",function(){
        if($(this).hasClass("cur")){
            $(this).removeClass("cur");
        }else {
            $(this).addClass("cur");
        }
    });

    //判断当前菜单
    var active = 0;  //赋值
    if($(".fixed-menu")){
        if(active==1){
            $(".fixed-menu ul li").removeClass("cur").eq(0).addClass("cur");  //选中 找新药
        }else if(active==3) {
            $(".fixed-menu ul li").removeClass("cur").eq(2).addClass("cur");  //选中 我的
        }else {
            $(".fixed-menu ul li").removeClass("cur");  //都不选中
        }
    }

    //返回上一页
    $(".back").on("click",function () {
        window.history.go(-1);
    });


    //手机号码验证
    function validateMobile(mobile,mobileInp)
    {
        if(mobile.length==0)
        {
            alert('请输入手机号码！');
            mobileInp.focus();
            return false;
        }
        if(mobile.length!=11)
        {
            alert('请输入有效的手机号码！');
            mobileInp.focus();
            return false;
        }

        var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        if(!myreg.test(mobile))
        {
            alert('请输入有效的手机号码！');
            mobileInp.focus();
            return false;
        }
        return true;
    }

    // 反序列化 $.param
    $.unserialize = function(str){
        var data = {};
        str && str.split('&').forEach(function(item){
            item = item.split('=');
            var key = decodeURIComponent(item[0]),
                val = decodeURIComponent(item[1]);
            if(key.indexOf("[]")>=0){
                key = key.replace("[]",'');
                if(!(key in data)){
                    data[key] = [];
                }
                data[key].push(val);
            }else{
                data[key] = val;
            }
        });
        return data;
    }

    //获取全部表单数据（json类型）
    $.fn.serializeObject = function(){
        var $form = $('<form></form>');
        $(this).children().clone().appendTo($form);
        return $.unserialize($form.serialize());
    };
    //获取全部表单数据（序列化类型）
    $.fn.serializeNew = function(){
        var $form = $('<form></form>');
        $(this).children().clone().appendTo($form);
        //给select重新赋值
        $(this).find("select").each(function(index,element){
            var thisVal = $(this).val();
            var name =  $(this).attr("name");
            $form.find("select[name='"+name+"']").val(thisVal);
        });
        return $form.serialize();
    };

    //上传图片
    $.UpImg = function(){
        this.up = function(button,upEven){
            button.click(function() {
                var sub = $(".file");
                if (/msie/i.test(navigator.userAgent)) //IE
                {
                    sub.fireEvent("onclick");
                } else {
                    var e = document.createEvent('MouseEvent');
                    e.initEvent('click', false, false);
                    sub.get(0).dispatchEvent(e);
                }
            });

            $(".file").on("change",function(){
                drawTempPhoto();
            });

            //绘制照片
            function drawTempPhoto() {
                //检验是否为图像文件
                var file = $(".file").prop('files')[0];
                if (!/image\/\w+/.test(file.type)) {
                    alert("看清楚哦，这个需要图片！");
                    return false;
                }
                var reader = new FileReader();
                //将文件以Data URL形式读入页面
                reader.readAsDataURL(file);
                reader.onload = function(e) {

                    //预览效果
                    var img = upEven[0];
                    //加载图片，此处的this.result为base64格式
                    img.src = this.result;

                }

            }
        };


        //上传多个图片
        this.upImgs = function(button){
            var upEven = "";
            button.click(function() {
                var sub = $(".file");
                if (/msie/i.test(navigator.userAgent)) //IE
                {
                    sub.fireEvent("onclick");
                } else {
                    var e = document.createEvent('MouseEvent');
                    e.initEvent('click', false, false);
                    sub.get(0).dispatchEvent(e);
                }
            });

            $(".file").on("change",function(){
                var file = $(".file").val();
                var obj = button.closest("form").submit();
                drawTempPhoto();
                imgSubmit(file,obj);
            });

            //单个图片提交
            function imgSubmit(val,obj){
                var fileName = val;
                var ldot = fileName.lastIndexOf(".");
                var ext = fileName.substring(ldot + 1);
                if (!(ext && /^(jpg|JPG|png|PNG)$/.test(ext))) {
                    alert("您上传的病历格式不对，仅支持jpg|png格式，请重新选择！");
                    return;
                }
                obj.submit();
            }

            //绘制照片
            function drawTempPhoto() {
                //检验是否为图像文件
                var file = $(".file").prop('files')[0];
                if (!/image\/\w+/.test(file.type)) {
                    alert("看清楚哦，这个需要图片！");
                    return false;
                }

                var html = $(".form-imgs>.form-img:eq(0)").clone(true).removeClass("template");  //复制模板
                button.before(html);
                upEven = button.prev(".form-img").find("img");

                var reader = new FileReader();
                //将文件以Data URL形式读入页面
                reader.readAsDataURL(file);
                reader.onload = function(e) {

                    //预览效果
                    var img = upEven[0];
                    //加载图片，此处的this.result为base64格式
                    img.src = this.result;

                }

            }
        }
    };


    //日期转换
    $.extend({
        myTime: {
            /**
             * 当前时间戳
             * @return <int>        unix时间戳(秒)
             */
            CurTime: function(){
                return Date.parse(new Date())/1000;
            },
            /**
             * 日期 转换为 Unix时间戳
             * @param <string> 2014-01-01 20:20:20  日期格式
             * @return <int>        unix时间戳(秒)
             */
            DateToUnix: function(string) {
                var f = string.split(' ', 2);
                var d = (f[0] ? f[0] : '').split('-', 3);
                var t = (f[1] ? f[1] : '').split(':', 3);
                return (new Date(
                        parseInt(d[0], 10) || null,
                        (parseInt(d[1], 10) || 1) - 1,
                        parseInt(d[2], 10) || null,
                        parseInt(t[0], 10) || null,
                        parseInt(t[1], 10) || null,
                        parseInt(t[2], 10) || null
                    )).getTime() / 1000;
            },
            /**
             * 时间戳转换日期
             * @param <int> unixTime    待时间戳(秒)
             * @param <bool> isFull    返回完整时间(Y-m-d 或者 Y-m-d H:i:s)
             * @param <int>  timeZone   时区
             */
            UnixToDate: function(unixTime, isFull, timeZone) {
                if (typeof (timeZone) == 'number')
                {
                    unixTime = parseInt(unixTime) + parseInt(timeZone) * 60 * 60;
                }
                var time = new Date(unixTime * 1000);
                var ymdhis = "";
                ymdhis += time.getUTCFullYear() + "-";
                ymdhis += (time.getUTCMonth()+1) + "-";
                ymdhis += time.getUTCDate();
                if (isFull === true)
                {
                    ymdhis += " " + time.getUTCHours() + ":";
                    ymdhis += time.getUTCMinutes() + ":";
                    ymdhis += time.getUTCSeconds();
                }
                return ymdhis;
            }
        }
    });


    //表单文本
    $.Text = function (){
        //textarea默认文本
        this.defaultText = function(obj,dtext){  //obj：对象  text：默认文本
            obj.on("focus",function(){
                var enterText = $(this).val();
                if(enterText==dtext){
                    $(this).removeClass("c6");
                    $(this).val("");
                }
            })

            obj.blur("focus",function(){
                var enterText = $(this).val();
                if(enterText==""){
                    $(this).addClass("c6");
                    $(this).val(dtext);
                }
            })
        }

        //重置input内容
        this.deleteText = function(){
            $(".text-delete").on("click",function(){
                $(this).parent().find(".mod-input").val("");
//                $(".staging-money-num").attr("placeholder","默认文本")
            })
        }

        //输入监听
        this.wordMonitor = function(obj,writeObj,maxNum){
            obj.on('input propertychange', function() {
                var thisNum = obj.val().length;
                writeObj.html(thisNum);
                if(thisNum>maxNum){
                    writeObj.addClass("c4");
                }else{
                    if(writeObj.hasClass("c4")){
                        writeObj.removeClass("c4");
                    }
                }
            });
        }


        //短信验证
        this.verification = function(button,mobileInp){
            button.on("click",function(){
                var mobile = mobileInp.val();

                if(validateMobile(mobile,mobileInp)){
                    var obj = $(this);
                    if( !obj.hasClass("verification-no")){
                        obj.addClass("verification-no");
                        obj.html("60s");
                        var t = 59;
                        var timer = setInterval(function(){
                            if(t>0){
                                obj.html(t+"s");
                                t--;
                            }else{
                                obj.removeClass("verification-no");
                                obj.html("获取验证码");
                                clearInterval(timer);
                            }
                        },1000)
                    }
                }
            })
        }

    }
})();