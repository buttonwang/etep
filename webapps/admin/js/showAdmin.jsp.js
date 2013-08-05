$(function(){
	try{window.parent.setIframeHeight($("body").height())}catch(e){}
	
	$("#noteSummary").change(function(){
		$(this).attr("hasEdit","true");
	})
	
})

function setIframeHeight(height){
	$("#learnNote_iframe").height(height);
}

function getDevotes(){
	return $("#devote_i").val();
}

//增加或消除笔记
function setUserLeanNote(map, obj) {
	var map = map||{};
	var user = map.uid||"";
	var note = map.note||"";
	var checked = map.checked;
	
	var jnote = $("#noteSummary");//noteSummary
	var jdevote = $("#devote_i");//devote_i
	var allNote = jnote.text();
	var devote = jdevote.val();
	if (checked==false) {
		var reg = new RegExp(note + "\r?", "g");
		allNote = allNote.replace(reg, "");
		allNote = allNote.replace(/^\s/, "").replace(/\s$/, "");
		var reg = new RegExp(user+",?");
		devote = devote.replace(reg, "").replace(/^,/, "").replace(/,$/, "");
	} else {
		allNote = allNote + "\r" +note;
		allNote = allNote.replace(/^\s/, "").replace(/\s$/, "");
		devote = devote + "," + user;
		devote = devote.replace(/^,/, "");
	}
	jnote.text(allNote);
	jdevote.val(devote);
	$("#devoteSpan").text(devote);
}