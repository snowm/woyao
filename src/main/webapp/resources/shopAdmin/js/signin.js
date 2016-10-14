/**
 * Created by Luozhongdao on 2016/9/21 0021.
 */
var c = jQuery.cookie('change-skin');
if (c && c == 'greyjoy') {
    jQuery('.btn-success').addClass('btn-orange').removeClass('btn-success');
} else if(c && c == 'dodgerblue') {
    jQuery('.btn-success').addClass('btn-primary').removeClass('btn-success');
} else if (c && c == 'katniss') {
    jQuery('.btn-success').addClass('btn-primary').removeClass('btn-success');
}