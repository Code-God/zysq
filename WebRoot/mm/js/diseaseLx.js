/**
 * Created by raytine on 2017/9/21.
 */
(function(){
    //联想项目
    $('#projectName').autocomplete({
        lookup: function (query, done) {
            var value = $("#projectName").val();
            $.ajax({
                type:'post',
                data: {name:value}, //获取到id为patientName的输入框的值，通过ajax传到后台
                url:"/zysq/drug/user_getProjectData.Q", //后台获取数据的url
                dataType: "text",
                success:function(data) {
                    data = eval("(" + data + ")");
                    //数组转换为插件需要结构
                    $.each(data.suggestions,function(k,v){
                        //匹配当前输入内容的Id
                        if(v.value==value){
                            $("#projectId").val(v.data);
                        }else {
                            $("#projectId").val("");
                        }
                    });
                    done(data);
                },
                error:function(data){
                    console.log(data);
                }
            });
        },
        onSelect: function (suggestion) {
            console.log('You selected: ' + suggestion.value + ', ' + suggestion.data);
            $("#projectId").val(suggestion.data)
        }
    });
})();
