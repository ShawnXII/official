var TableEditable = function () {

    return {
        //main function to initiate the module
        init: function (msg) {
            var oTable = $('#tableData').dataTable({
                "aLengthMenu": [
                    [10, 20, 50, -1],
                    [10, 20, 50, 100] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 20,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "每页 _MENU_ 条",
                    "oPaginate": {
                        "sPrevious": "上一页",
                        "sNext": "下一页",
                        "sFirst" : "首页",
                        "sLast" : "未页"
                    },
                    "sProcessing":"正在处理...",
                    "sEmptyTable" : "没有找到记录",
                    "sInfo" : "总记录数_TOTAL_,当前显示_START_至_END_",
                    "sInfoEmpty" : "",
                    "sSearch" : "搜索:"
                },
                "bSort": false
            });      
        }
    };
}();