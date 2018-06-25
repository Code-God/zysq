//禁止按键F5
document.onkeydown = function(e){
    e = window.event || e;
    var keycode = e.keyCode || e.which;
    if( keycode = 116){
        if(window.event){// ie
            try{e.keyCode = 0;}catch(e){}
            e.returnValue = false;
        }else{// ff
            e.preventDefault();
        }
    }
}

//禁止鼠标右键菜单
document.oncontextmenu = function(e){
         return false;
}