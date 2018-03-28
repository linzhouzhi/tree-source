/**
 * Created by lzz on 2018/3/22.
 */


window.tree_node_type = {
    "default" : {
        "icon" : "glyphicon glyphicon-flash"
    },
    "table" : {
        "icon" : "fa fa-table"
    },
    "tmp-leaf" : {
        "icon" : "glyphicon glyphicon-flash"
    },
    "ellipsis" : {
        "icon" : "glyphicon glyphicon-flash"
    },
    "database" : {
        "icon" : "fa fa-database"
    },
    "root" : {
        "icon" : "fa fa-flag"
    }
};

$(document).ready(function () {
    var data = getMeta();
    if( typeof  data != "undefined" ){
        if( typeof  data.table != "undefined" &&  data.table != "" ){
            var sql = "select * from " + data.table + " limit 100";
            $("#sql-textarea").val( sql );
            $("#run-sql").click();
        }
    }
    $("#sql-textarea").autoTextarea({
        maxHeight:420,
        minHeight:100,
    });
    sql_init();
    $(".highlightTextarea").css("width", "100%");
});

$(document).on('input propertychange focus', '#sql-textarea', function() {
    //$(this).highlightTextarea('setWords', ['select','table']);
    sql_init();
});

function sql_init() {
    $("#sql-textarea").highlightTextarea({
        words: [{
            color: '#ADF0FF',
            words: 'select'
        }, {
            color: '#ADF0FF',
            words: 'where'
        }, {
            color: '#FFFF00',
            words: '{'
        }, {
            color: '#FFFF00',
            words: '$'
        }, {
            color: '#FFFF00',
            words: '}'
        }, {
            words: 'limit'
        }]
    });

    var content = $("#sql-textarea").val();
    var count = content.length;
    $("#sql_input_num").text(count);
}

function init_page(address, path, data){
    console.log(data);
    $("#right-content-iframe").attr("src", "/mysql/content?address=" + address + "&path=" + path);
}

function getMeta() {
    var data = {"table":""};
    var path_str = getUrlParam("path");
    if( null == path_str || "null" == path_str || !path_str ){
        return;
    }
    var paths = path_str.split("/");
    if( paths.length >= 2 ) {
        data.databases = paths[1];
    }
    if( paths.length == 3 ){
        data.table = paths[2];
    }
    data.address = getUrlParam("address");
    return data;
}

$("#run-sql").click(function () {
    var data = getMeta();
    data.sql = $("#sql-textarea").val();
    $("#sql-result").empty();
    post("/mysql/query", data, function (obj) {
        if( obj.code == 0 ){
            var result = obj.res;
            var size = result.length;
            var row = "<tbody><tr>";
            for(var i=0; i< size; i++){
                var item = result[i];
                var head = "<thead><tr>";
                for (var key in item){
                    head += "<th>" + key + "</th>";
                    row += "<td>" + item[key] + "</td>";
                }
                head += "</tr></thead>";
                row += "</tr>";
                if( i == 0 ){
                    $("#sql-result").append( head );
                }
            }
            row += "</tbody>";
            $("#sql-result").append( row );
        }else{
            $("#sql-result").html("<b>" + obj.res + "</b>");
        }
    });
});