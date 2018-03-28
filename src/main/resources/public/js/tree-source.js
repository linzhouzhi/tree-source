/**
 * Created by lzz on 2018/3/19.
 */

window.tree_node_type = {
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
};

$(document).ready(function () {
    var address = getUrlParam("address");
    $("#show-address").data( "address", address );
    if( address ){
        $("#show-address").text( address.split(",")[0] );
    }
});

$("#save-tree").click(function () {
    $(".save-modal").removeClass("hidden");
    $("#address-input-modal").modal("show");
    var type = getUrlParam("type");
    var address = getUrlParam("address");
    if( type == "mysql" ){
        getUrlParam("address");
    }
    $('[name="modal-input-address"]').val( address );
});

$("#save-address-footer-button").click(function () {
    var data = {};
    data.address = $('[name="modal-input-address"]').val();
    data.type = getUrlParam("type");
    data.showName = $('[name="modal-show-name"]').val();
    post("/save-source", data, function (obj) {
        console.log( obj );
    });
});

$("input[name='modal-input-address']").bind('keypress', function (event) {
    if (event.keyCode == "13") {
        var address_str = $(this).val().trim();
        window.location.href = window.location.href + "&address=" + address_str;
    }
})

var xOffset = -5;
var yOffset = 40;
$(document).on("mouseover mouseout","li.jstree-node",function(e){
    if(e.type == "mouseover"){
        this.t = this.id;
        this.title = "";
        var c = (this.t != "") ? "" + this.t : "";
        $("body").append("<div id='preview' style='border:1px solid #c7c7c7; background:#ffffff; padding:5px; display:none; position:absolute;'>"+ c +"</div>");
        $("#preview")
            .css("top",(e.pageY - yOffset) + "px")
            .css("left",(e.pageX + xOffset) + "px")
            .fadeIn("fast");
    }else if(e.type == "mouseout"){
        $("#preview").remove();
    }
});
$(document).on("mousemove","li.jstree-node", function (e) {
    $("#preview")
        .css("top",(e.pageY - yOffset) + "px")
        .css("left",(e.pageX + xOffset) + "px");
});


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
    if( typeof address == "undefined" || address == null || address == "" ){
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
        "types" : window.tree_node_type,
        "plugins" : [ "search","types","state" ]
    });

    $('#jstree').on("changed.jstree", function (e, data) {
        window.childrenNodes = [];
        var path = data.selected[0];
        $("#address-path").text(path);
        if(data.node){
            if( data.node.children.length == 1 && data.node.children[0].substring( data.node.children[0].length - 3 ) == "..." ){
                open_tree_ajax(e, data);
            }else{
                window.childrenNodes = data.node.children;
            }
        }
        init_page( window.address, path, window.childrenNodes );
    });

    $("#jstree").on("open_node.jstree", function (e, data) {
        open_tree_ajax(e, data);
    });

    function open_tree_ajax(e, data) {
        var children = data.node.children;
        if( children.length == 1 && children[0].endsWith("/...") ){

            var url = '/get-path-children?address=' + window.address + '&path=' + data.node.id + "&sourceType=" + getUrlParam('type');
            window.childrenNodes = [];
            get(url, function(result){
                var ref = $("#jstree").jstree(true);
                ref.delete_node(children[0]);
                result.children.forEach(function (element) {
                    window.childrenNodes.push( element.id );
                    ref.create_node(data.node, element);
                })
            });
        }
    }

});

