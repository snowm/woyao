/**
 * Created by Luozhongdao on 2016/9/20 0020.
 */   

define([],function(){
	
	
	
	
	
	
	$("#upbtn").on('click',function(){
		var formData = new FormData($("#uploadForm")[0]);  
	     $.ajax({  
	          url: 'admin/upload/file' ,  
	          type: 'POST',  
	          data: formData,  
	          async: false,  
	          cache: false,  
	          contentType: false,  
	          processData: false,  
	          success: function (returndata) {  
	              console.log(returndata);  
	          },  
	          error: function (returndata) {  
	        	  console.log(returndata);  
	          }  
	     });  
	})
});