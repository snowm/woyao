/**
 * Created by Administrator on 2016/10/6 0006.
 */
define([],function(){

    $(function(){
    	var chatController=avalon.define({
    		$id:"chatController",
    		chat:false,
    		btnChat:function(){
    			chatController.chat=true;
    		},
    	});
    	console.log("load chat-manage");
    	avalon.scan();
        
    });


});