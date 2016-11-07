// QQ表情插件
(function($){  
	$.fn.qqFace = {
		dct:[
		     {name:'[微笑]',code:'[em_1]'},
             {name:'[瘪嘴]',code:'[em_2]'},
             {name:'[色]',code:'[em_3]'},
             {name:'[发呆]',code:'[em_4]'},
             {name:'[流泪]',code:'[em_5]'},
             {name:'[害羞]',code:'[em_6]'},
             {name:'[闭嘴]',code:'[em_7]'},
             {name:'[睡]',code:'[em_8]'},
             {name:'[委屈]',code:'[em_9]'},
             {name:'[尴尬]',code:'[em_10]'},
             {name:'[怒]',code:'[em_11]'},
             {name:'[调皮]',code:'[em_12]'},
             {name:'[呲牙]',code:'[em_13]'},
             {name:'[惊讶]',code:'[em_14]'},
             {name:'[难过]',code:'[em_15]'},
             {name:'[脸红]',code:'[em_16]'},
             {name:'[狂躁]',code:'[em_17]'},
             {name:'[吐]',code:'[em_18]'},
             {name:'[偷笑]',code:'[em_19]'},
             {name:'[愉快]',code:'[em_20]'},
             {name:'[白眼]',code:'[em_21]'},
             {name:'[傲慢]',code:'[em_22]'},
             {name:'[饿]',code:'[em_23]'},
             {name:'[困]',code:'[em_24]'},
             {name:'[惊恐]',code:'[em_25]'},
             {name:'[流汗]',code:'[em_26]'},
             {name:'[憨笑]',code:'[em_27]'},
             {name:'[悠闲]',code:'[em_28]'},
             {name:'[奋斗]',code:'[em_29]'},
             {name:'[咒骂]',code:'[em_30]'},
             {name:'[疑问]',code:'[em_31]'},
             {name:'[嘘]',code:'[em_32]'},
             {name:'[晕]',code:'[em_33]'},
             {name:'[抓狂]',code:'[em_34]'},
             {name:'[衰]',code:'[em_35]'},
             {name:'[敲打]',code:'[em_36]'},
             {name:'[再见]',code:'[em_37]'},
             {name:'[擦汗]',code:'[em_38]'},
             {name:'[抠鼻]',code:'[em_39]'},
             {name:'[囧]',code:'[em_40]'},
             {name:'[坏笑]',code:'[em_41]'},
             {name:'[左哼哼]',code:'[em_42]'},
             {name:'[右哼哼]',code:'[em_43]'},
             {name:'[哈欠]',code:'[em_44]'},
             {name:'[鄙视]',code:'[em_45]'},
             {name:'[委屈]',code:'[em_46]'},
             {name:'[快哭了]',code:'[em_47]'},
             {name:'[阴险]',code:'[em_48]'},
             {name:'[亲亲]',code:'[em_49]'},
             {name:'[惊吓]',code:'[em_50]'},
             {name:'[可怜]',code:'[em_51]'},
             {name:'[抱抱]',code:'[em_52]'},
             {name:'[月亮]',code:'[em_53]'},
             {name:'[太阳]',code:'[em_54]'},
             {name:'[炸弹]',code:'[em_55]'},
             {name:'[骷髅]',code:'[em_56]'},
             {name:'[菜刀]',code:'[em_57]'},
             {name:'[猪]',code:'[em_58]'},
             {name:'[西瓜]',code:'[em_59]'},
             {name:'[咖啡]',code:'[em_60]'},
             {name:'[饭]',code:'[em_61]'},
             {name:'[爱心]',code:'[em_62]'},
             {name:'[赞]',code:'[em_63]'},
             {name:'[弱]',code:'[em_64]'},
             {name:'[握手]',code:'[em_65]'},
             {name:'[胜利]',code:'[em_66]'},
             {name:'[抱拳]',code:'[em_67]'},
             {name:'[勾引]',code:'[em_68]'},
             {name:'[OK]',code:'[em_69]'},
             {name:'[NO]',code:'[em_70]'},
             {name:'[玫瑰]',code:'[em_71]'},
             {name:'[凋谢]',code:'[em_72]'},
             {name:'[嘴唇]',code:'[em_73]'},
             {name:'[爱]',code:'[em_74]'},
             {name:'[飞吻]',code:'[em_75]'},
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
	        str = str.replace(/\[em_([0-9]*)\]/g,"<img src='/resources/js/qqface/face/$1.gif'/>");
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