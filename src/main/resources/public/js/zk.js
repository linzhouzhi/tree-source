/**
 * Created by lzz on 2018/3/12.
 */

$("#save-node").click(function () {
    var reqdata = {};
    reqdata.address = window.address;
    reqdata.path = $("#input-path").val();
    reqdata.data = $("#node-data").val();
    $.ajax({
        type : "post",
        contentType: 'application/json',
        url : "/update_node",
        data : JSON.stringify(reqdata),
        dataType:'json',
        success : function(data){
            console.log(data);
            $("#node-detail").html(syntaxHighlight(data));
        }
    });
});

$("#delete-node").click(function () {
    var reqdata = {};
    reqdata.path = $("#input-path").val();
    reqdata.address = window.address;
    $.ajax({
        type : "post",
        contentType: 'application/json',
        url : "/delete_node",
        data : JSON.stringify(reqdata),
        dataType:'json',
        success : function(data){
            console.log(data);
            $("#node-detail").html(syntaxHighlight(data));
        }
    });
});

