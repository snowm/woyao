define([],function(){

    $(function(){
    	var userController=avalon.define({
    		$id:"userController",
    		user:false,
    		btnUser:function(){
    			userController.user=true;
    		},    		
    	});
    	console.log("load user-manage");
    	avalon.scan();        
    });
});