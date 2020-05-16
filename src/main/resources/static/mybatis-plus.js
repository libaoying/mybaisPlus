const path=  window.location.pathname.replace("/mybatisplus-ui.html","");
const TR = "<tr><td>@1</td><td><a id='@2' href='"+path+"/table.html?table=@table' >详情</a></td><td><button id='@3' onclick=\"generator(this)\">代码生成</button></td></tr>"
$(function () {
    tableList();
});

/**
 * 表列表
 */
function tableList() {
    $.ajax({
        type: "GET",
        url: path+"/table/list",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
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
                let temp_tr = TR.replace("@table",data[i]).replace("@1", data[i]).replace("@2", data[i]).replace("@3", data[i]);
                $table_ul.append($(temp_tr))
            }
        }
    });
}

/**
 * 生成代码请求
 * @param opt
 */
function generator(e) {
    console.log(e)
    $.ajax({
        type: "get",
        url: path+"/table/generator/" + e.id,
        async: false,
        success: function (result) {
            alert(result.message);
        }
    });
}


