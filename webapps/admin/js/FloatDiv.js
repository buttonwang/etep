function FloatDiv(divId,left,top){
	setInterval("FloatDiv_keep('"+divId+"',"+left+","+top+")",10);
}
function FloatDiv_keep(divId,left,top) {
	var div=document.getElementById(divId);
	div.style.position='absolute';
	div.style.left=(left+document.body.scrollLeft).toString()+'px';
	div.style.top=(top+document.body.scrollTop).toString()+'px';
} 