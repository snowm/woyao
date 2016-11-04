// QQ表情插件
(function($){  
	$.fn.qqFace = {
		dct:[
		     {name:'[脱光]',code:'[em_1]'},
             {name:'[傻逼]',code:'[em_2]'},
        ],
		init:function(options){
			var defaults = {
					id : 'facebox',// QQ表情插件
(function($){  
	$.fn.qqFace = {
		dct:[
		     {name:'[脱光]',code:'[em_1]'},
             {name:'[傻逼]',code:'[em_2]'},
             {name:'[哦]',code:'[em_3]'},
             {name:'[骚起来]',code:'[em_4]'},
             {name:'[骚笑]',code:'[em_5]'},
             {name:'[瞧一瞧]',code:'[em_6]'},
        ],
		init:function(options){
			var defaults = {
					id : 'facebox',
					path : 'face/',
					assign : 'sendIpt',
					tip : 'em_',
					container:'faceCtn'
				};
				var option = $.extend(defaults, options);
				var assign = $('#'+option.assign);
				var id = option.id;
				var path = option.path;
				var tip = option.tip;
				var buttons = option.btn;
				var container = option.container;
				if(assign.length<=0){
					alert('缺少表情赋值对象。');
					return false;
				}

			    var dct = this.dct;
			    
				$(buttons).click(function(e){
					var strFace, labFace;
					if($('#'+id).length<=0){
						strFace = '<div id="'+id+'" style="position:absolute;width:94%;display:none;margin:0 auto;z-index:1;" class="qqFace">' +
									  '<table style="width: 100%;margin: 5px 0 20px 0" border="0" cellspacing="0" cellpadding="0"><tr>';
						for(var i=1; i<=75; i++){
							labFace = '['+tip+i+']';
							
							
							  for(var j = 0 ; j < dct.length ; j++){
								  if(labFace == dct[j].code){
									  labFace = dct[j].name;
								  }
						      }
							  
							
							strFace += '<td><img style="width: 28px" src="'+path+i+'.gif" onclick="$(\'#'+option.assign+'\').setCaret();$(\'#'+option.assign+'\').insertAtCaret(\'' + labFace + '\');" /></td>';
							if( i % 6 == 0 ) strFace += '</tr><tr>';
						}
						strFace += '</tr></table></div>';
					}

					$("." + container).append(strFace);
					$('#'+id).css('left','3%').css('top',0).show();
					e.stopPropagation();
				});
		},
		replaceEm:function(str){
			var dct = this.dct;
			var oldStr = str;
	        for(var i = 0 ; i < dct.length ; i++){
	        	str = ReplaceAll(str,dct[i].name,dct[i].code);
	    	}
	        str = str.replace(/\</g,'&lt;');
	        str = str.replace(/\>/g,'&gt;');
	        str = str.replace(/\n/g,'<br/>');
	        str = str.replace(/\[em_([0-9]*)\]/g,"<img src='/resources/js/qqface/face/$1.gif' style='width:.4rem'/>");
	        return str;
		}
	}

	   function ReplaceAll(str, sptr, sptr1){
	        while (str.indexOf(sptr) >= 0){
	           str = str.replace(sptr, sptr1);
	        }
	        return str;
	    }
})(jQuery);

jQuery.extend({ 
unselectContents: function(){ 
	if(window.getSelection) 
		window.getSelection().removeAllRanges(); 
	else if(document.selection) 
		document.selection.empty(); 
	} 
}); 
jQuery.fn.extend({ 
	selectContents: function(){ 
		$(this).each(function(i){ 
			var node = this; 
			var selection, range, doc, win; 
			if ((doc = node.ownerDocument) && (win = doc.defaultView) && typeof win.getSelection != 'undefined' && typeof doc.createRange != 'undefined' && (selection = window.getSelection()) && typeof selection.removeAllRanges != 'undefined'){ 
				range = doc.createRange(); 
				range.selectNode(node); 
				if(i == 0){ 
					selection.removeAllRanges(); 
				} 
				selection.addRange(range); 
			} else if (document.body && typeof document.body.createTextRange != 'undefined' && (range = document.body.createTextRange())){ 
				range.moveToElementText(node); 
				range.select(); 
			} 
		}); 
	}, 

	setCaret: function(){
		//if (!$.support.leadingWhitespace) return;
		//var initSetCaret = function(){
		//	var textObj = $(this).get(0);
		//	textObj.caretPos = document.selection.createRange().duplicate();
		//};
		//$(this).click(initSetCaret).select(initSetCaret).keyup(initSetCaret);
	},

	insertAtCaret: function(textFeildValue){ 
		var textObj = $(this).get(0); 
		if(document.all && textObj.createTextRange && textObj.caretPos){ 
			var caretPos=textObj.caretPos; 
			caretPos.text = caretPos.text.charAt(caretPos.text.length-1) == '' ? 
			textFeildValue+'' : textFeildValue; 
		} else if(textObj.setSelectionRange){ 
			var rangeStart=textObj.selectionStart; 
			var rangeEnd=textObj.selectionEnd; 
			var tempStr1=textObj.value.substring(0,rangeStart); 
			var tempStr2=textObj.value.substring(rangeEnd); 
			textObj.value=tempStr1+textFeildValue+tempStr2; 
			textObj.focus(); 
			var len=textFeildValue.length; 
			textObj.setSelectionRange(rangeStart+len,rangeStart+len); 
			textObj.blur(); 
		}else{ 
			textObj.value+=textFeildValue; 
		} 
	} 
});
					path : 'face/',
					assign : 'sendIpt',
					tip : 'em_',
					container:'faceCtn'
				};
				var option = $.extend(defaults, options);
				var assign = $('#'+option.assign);
				var id = option.id;
				var path = option.path;
				var tip = option.tip;
				var buttons = option.btn;
				var container = option.container;
				if(assign.length<=0){
					alert('缺少表情赋值对象。');
					return false;
				}

			    var dct = this.dct;
			    
				$(buttons).click(function(e){
					var strFace, labFace;
					if($('#'+id).length<=0){
						strFace = '<div id="'+id+'" style="position:absolute;width:94%;display:none;margin:0 auto;z-index:1;" class="qqFace">' +
									  '<table style="width: 100%;margin: 5px 0 20px 0" border="0" cellspacing="0" cellpadding="0"><tr>';
						for(var i=1; i<=75; i++){
							labFace = '['+tip+i+']';
							
							
							  for(var j = 0 ; j < dct.length ; j++){
								  if(labFace == dct[j].code){
									  labFace = dct[j].name;
								  }
						      }
							  
							
							strFace += '<td><img style="width: 28px" src="'+path+i+'.gif" onclick="$(\'#'+option.assign+'\').setCaret();$(\'#'+option.assign+'\').insertAtCaret(\'' + labFace + '\');" /></td>';
							if( i % 6 == 0 ) strFace += '</tr><tr>';
						}
						strFace += '</tr></table></div>';
					}

					$("." + container).append(strFace);
					$('#'+id).css('left','3%').css('top',0).show();
					e.stopPropagation();
				});
		}
	}

})(jQuery);

jQuery.extend({ 
unselectContents: function(){ 
	if(window.getSelection) 
		window.getSelection().removeAllRanges(); 
	else if(document.selection) 
		document.selection.empty(); 
	} 
}); 
jQuery.fn.extend({ 
	selectContents: function(){ 
		$(this).each(function(i){ 
			var node = this; 
			var selection, range, doc, win; 
			if ((doc = node.ownerDocument) && (win = doc.defaultView) && typeof win.getSelection != 'undefined' && typeof doc.createRange != 'undefined' && (selection = window.getSelection()) && typeof selection.removeAllRanges != 'undefined'){ 
				range = doc.createRange(); 
				range.selectNode(node); 
				if(i == 0){ 
					selection.removeAllRanges(); 
				} 
				selection.addRange(range); 
			} else if (document.body && typeof document.body.createTextRange != 'undefined' && (range = document.body.createTextRange())){ 
				range.moveToElementText(node); 
				range.select(); 
			} 
		}); 
	}, 

	setCaret: function(){
		//if (!$.support.leadingWhitespace) return;
		//var initSetCaret = function(){
		//	var textObj = $(this).get(0);
		//	textObj.caretPos = document.selection.createRange().duplicate();
		//};
		//$(this).click(initSetCaret).select(initSetCaret).keyup(initSetCaret);
	},

	insertAtCaret: function(textFeildValue){ 
		var textObj = $(this).get(0); 
		if(document.all && textObj.createTextRange && textObj.caretPos){ 
			var caretPos=textObj.caretPos; 
			caretPos.text = caretPos.text.charAt(caretPos.text.length-1) == '' ? 
			textFeildValue+'' : textFeildValue; 
		} else if(textObj.setSelectionRange){ 
			var rangeStart=textObj.selectionStart; 
			var rangeEnd=textObj.selectionEnd; 
			var tempStr1=textObj.value.substring(0,rangeStart); 
			var tempStr2=textObj.value.substring(rangeEnd); 
			textObj.value=tempStr1+textFeildValue+tempStr2; 
			textObj.focus(); 
			var len=textFeildValue.length; 
			textObj.setSelectionRange(rangeStart+len,rangeStart+len); 
			textObj.blur(); 
		}else{ 
			textObj.value+=textFeildValue; 
		} 
	} 
});