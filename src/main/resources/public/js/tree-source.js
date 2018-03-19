/**
 * Created by lzz on 2018/3/19.
 */


$("input[name='modal-input-address']").bind('keypress', function (event) {
    if (event.keyCode == "13") {
        window.location.href = window.location.href + "&address=" + $(this).val().trim();
    }
})

$(function () {
    var to = false;
    $('#search-node').keyup(function () {
        if(to) { clearTimeout(to); }
        to = setTimeout(function () {
            var v = $('#search-node').val();
            $('#jstree').jstree(true).search(v);
        }, 250);
    });

    var path = getUrlParam('path');
    if( typeof path == "undefined" || path == null ){
        path = "/";
    }
    var address = getUrlParam('address');
    if( typeof address == "undefined" || address == null ){
        $("#address-input-modal").modal("show");
    }
    window.address = address;
    $('#jstree').jstree({
        "core" : {
            "animation" : 0,
            "check_callback" : true,
            "themes" : { "stripes" : false },
            'data' : {
                'url' : function (node) {
                    return '/get-path-children?address=' + window.address + '&path=' + path +"&sourceType=" + getUrlParam('type');
                },
                'data' : function (node) {
                    return { 'id' : node.id };
                }
            }
        },
        "types" : {
            "default" : {
                "icon" : "glyphicon glyphicon-flash"
            },
            "leaf" : {
                "icon" : "glyphicon glyphicon-flash"
            },
            "tmp-leaf" : {
                "icon" : "glyphicon glyphicon-flash"
            },
            "ellipsis" : {
                "icon" : "glyphicon glyphicon-flash"
            },
            "parent" : {
                "icon" : "glyphicon glyphicon-ok"
            }
        },
        "plugins" : [ "search","types","state" ]
    });


    $('#jstree').on("changed.jstree", function (e, data) {
        var path = data.selected[0];
        $("#address-path").text(path);
        $("#input-path").val(path);
        console.log(path);
    });

    $("#jstree").on("open_node.jstree", function (e, data) {
        var children = data.node.children;
        if( children.length == 1 && children[0].endsWith("/...") ){

            var url = '/get-path-children?address=' + window.address + '&path=' + data.node.id + "&sourceType=" + getUrlParam('type');
            console.log( url );
            $.get(url, function(result){
                var ref = $("#jstree").jstree(true);
                ref.delete_node(children[0]);
                result.children.forEach(function (element) {
                    ref.create_node(data.node, element);
                })
            });
        }
    });

});

