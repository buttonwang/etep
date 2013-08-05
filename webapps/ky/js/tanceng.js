//44A的UI界面div
var popup_dragging = false;
var popup_target;
var popup_mouseX;
var popup_mouseY;
var popup_mouseposX;
var popup_mouseposY;
var popup_oldfunction;
function popup_display(x)
{
  var win = window.open();
  for (var i in x) win.document.write(i+' = '+x[i]+'<br>');
}
// ----- popup_mousedown -------------------------------------------------------
function popup_mousedown(e)
{
  var ie = navigator.appName == "Microsoft Internet Explorer";
  if ( ie && window.event.button != 1) return;
  if (!ie && e.button            != 0) return;
  popup_dragging = true;
  popup_target   = this['target'];
  popup_mouseX   = ie ? window.event.clientX : e.clientX;
  popup_mouseY   = ie ? window.event.clientY : e.clientY;
  if (ie)
       popup_oldfunction      = document.onselectstart;
  else popup_oldfunction      = document.onmousedown;
  if (ie)
       document.onselectstart = new Function("return false;");
  else document.onmousedown   = new Function("return false;");
}
// ----- popup_mousemove -------------------------------------------------------
function popup_mousemove(e)
{
  if (!popup_dragging) return;
  var ie      = navigator.appName == "Microsoft Internet Explorer";
  var element = document.getElementById(popup_target);
  var mouseX = ie ? window.event.clientX : e.clientX;
  var mouseY = ie ? window.event.clientY : e.clientY;
  element.style.left = (element.offsetLeft+mouseX-popup_mouseX)+'px';
  element.style.top  = (element.offsetTop +mouseY-popup_mouseY)+'px';
  popup_mouseX = ie ? window.event.clientX : e.clientX;
  popup_mouseY = ie ? window.event.clientY : e.clientY;
}
// ----- popup_mouseup ---------------------------------------------------------
function popup_mouseup(e)
{
  if (!popup_dragging) return;
  popup_dragging = false;
  var ie      = navigator.appName == "Microsoft Internet Explorer";
  var element = document.getElementById(popup_target);
  if (ie)
       document.onselectstart = popup_oldfunction;
  else document.onmousedown   = popup_oldfunction;
}
// ----- popup_exit ------------------------------------------------------------
function popup_exit(e)
{
  var ie      = navigator.appName == "Microsoft Internet Explorer";
  var element = document.getElementById(popup_target);
  popup_mouseup(e);
  element.style.visibility = 'hidden';
  element.style.display    = 'none';
}
//在此检测是否输入了内容
function checkTextArea(obj){
	var value=obj.value;
	var id=obj.id;
	var content_img_src="../images/file_yi.gif";
	var nocontent_img_src="../images/file_yi1.gif";
	var i=parseInt(id.charAt(id.length-1));
	var name="transImgButton"+i;
	var imgEle=document.getElementById(name);
	if(value!=null&&trim(value).length>0){
		imgEle.src=content_img_src;
	}else{
		imgEle.src=nocontent_img_src;
	}
}

// ----- popup_show ------------------------------------------------------------
function popup_show(name1,name2,name3)//��'popup' 'popup_darg' 'popup_exit'
{
  element      = document.getElementById(name1);
  drag_element = document.getElementById(name2);
  exit_element = document.getElementById(name3);
  //ok_btn	   = document.getElementById(name4);
  element.style.position   = "absolute";
  element.style.visibility = "visible";
  element.style.display    = "block";
  var pop_left_value=document.documentElement.scrollLeft+popup_mouseposX-450;
  if(pop_left_value<=20)pop_left_value=30;
    element.style.left = pop_left_value+'px';
  var height=element.offsetHeight;
  	//alert("h"+height);
    element.style.top  = (document.documentElement.scrollTop +popup_mouseposY-height-20)+'px';
  drag_element['target']   = name1;
  drag_element.onmousedown = popup_mousedown;
  exit_element.onclick     = popup_exit;
  //ok_btn.onclick		   = popup.exit;
}
// ----- popup_mousepos --------------------------------------------------------
function popup_mousepos(e)
{
  var ie = navigator.appName == "Microsoft Internet Explorer";
  popup_mouseposX = ie ? window.event.clientX : e.clientX;
  popup_mouseposY = ie ? window.event.clientY : e.clientY;
}
// ----- Attach Events ---------------------------------------------------------
if (navigator.appName == "Microsoft Internet Explorer")
     document.attachEvent('onmousedown', popup_mousepos);
else document.addEventListener('mousedown', popup_mousepos, false);
if (navigator.appName == "Microsoft Internet Explorer")
     document.attachEvent('onmousemove', popup_mousemove);
else document.addEventListener('mousemove', popup_mousemove, false);
if (navigator.appName == "Microsoft Internet Explorer")
     document.attachEvent('onmouseup', popup_mouseup);
else document.addEventListener('mouseup', popup_mouseup, false);