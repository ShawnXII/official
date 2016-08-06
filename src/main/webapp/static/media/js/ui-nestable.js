var UINestable = function () {
    return {
        //main function to initiate the module 
    	init: function () {
            $('#menu-list').nestable();
            $('#menu-list').nestable().on('change', function(){ 
            	 var r = $('.dd').nestable('serialize'); 
            });
        }
    };
}();