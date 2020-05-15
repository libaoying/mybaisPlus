const TR = "<tr><td>@1</td><td>@2</td><td>@3</td></tr>"
const path=  window.location.pathname.replace("/table.html","");
$(function () {
    let table=getUrlParam("table");
    getDetails(table);
});


/**
 * 表详情接口
 * @param opt
 */

function  getDetails(table) {
    $.ajax({
        type: "get",
        url: path+"/table/details/" + table,
        async: false,
        success: function (result) {
            console.log(result);
            if (result.code!==0){
                alert(result.message);
                return;
            }
            let data=result.data;
            //取得table-ul元素
            let $table_ul = $('#mytable');
            for (let i = 0; i < data.length; i++) {
                let temp_tr = TR.replace("@1", data[i].columnName).replace("@2", data[i].columnType).replace("@3", data[i].colunmCommint);
                $table_ul.append($(temp_tr))
            }
        }
    });
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return encodeURI(r[2]); return null; //返回参数值
}

