// This sample starts with a normal textarea and provides the ability to switch back
//  and forth between the textarea and FCKeditor. It uses the JavaScript API to do the
//	operations so it will work even if the internal implementation changes.

function Toggle()
{
	// Try to get the FCKeditor instance, if available.
	var oEditor ;
	if ( typeof( FCKeditorAPI ) != 'undefined' )
		oEditor = FCKeditorAPI.GetInstance( 'DataFCKeditor' ) ;
 
	// Get the _Textarea and _FCKeditor DIVs.
	var eTextareaDiv	= document.getElementById( 'Textarea' ) ;
	var eFCKeditorDiv	= document.getElementById( 'FCKeditor' ) ;
 
	// If the _Textarea DIV is visible, switch to FCKeditor.
	if ( eTextareaDiv.style.display != 'none' )
	{
		// If it is the first time, create the editor.
		if ( !oEditor )
		{
			CreateEditor() ;
		}
		else
		{
			// Set the current text in the textarea to the editor.
			oEditor.SetData( document.getElementById('DataTextarea').value ) ;
		}
 
		// Switch the DIVs display.
		eTextareaDiv.style.display = 'none' ;
		eFCKeditorDiv.style.display = '' ;
 
		// This is a hack for Gecko 1.0.x ... it stops editing when the editor is hidden.
		if ( oEditor && !document.all )
		{
			if ( oEditor.EditMode == FCK_EDITMODE_WYSIWYG )
				oEditor.MakeEditable() ;
		}
	}
	else
	{
		// Set the textarea value to the editor value.
		document.getElementById('DataTextarea').value = oEditor.GetXHTML() ;
 
		// Switch the DIVs display.
		eTextareaDiv.style.display = '' ;
		eFCKeditorDiv.style.display = 'none' ;
	}
}
 
function CreateEditor()
{
	// Copy the value of the current textarea, to the textarea that will be used by the editor.
	document.getElementById('DataFCKeditor').value = document.getElementById('DataTextarea').value ;
 
	// Automatically calculates the editor base path based on the _samples directory.
	// This is usefull only for these samples. A real application should use something like this:
	// oFCKeditor.BasePath = '/fckeditor/' ;	// '/fckeditor/' is the default value.
	//var sBasePath = document.location.href.substring(0,document.location.href.lastIndexOf('_samples')) ;
	var sBasePath = "../js/fckeditor/";
 	
	// Create an instance of FCKeditor (using the target textarea as the name).
	var oFCKeditor = new FCKeditor( 'DataFCKeditor' ) ;
	oFCKeditor.BasePath = sBasePath ;
	oFCKeditor.Config['ToolbarStartExpanded'] = false ;
	oFCKeditor.Width = '100%' ;
	oFCKeditor.Height = '300' ;
	oFCKeditor.ReplaceTextarea() ;
}
 
// The FCKeditor_OnComplete function is a special function called everytime an
// editor instance is completely loaded and available for API interactions.
function FCKeditor_OnComplete( editorInstance )
{
	// Enable the switch button. It is disabled at startup, waiting the editor to be loaded.
	
	//document.getElementById('BtnSwitchTextarea').disabled = false ;
}
 
function PrepareSave()
{
	// If the textarea isn't visible update the content from the editor.
	if ( document.getElementById( 'Textarea' ).style.display == 'none' )
	{
		var oEditor = FCKeditorAPI.GetInstance( 'DataFCKeditor' ) ;
		document.getElementById( 'DataTextarea' ).value = oEditor.GetXHTML() ;
		alert(document.getElementById( 'DataTextarea' ).value);
	}
}

//指定textarea的name转化为fckedit
function replacTextArea(textareaname)
{
	// Copy the value of the current textarea, to the textarea that will be used by the editor.
	//document.getElementById('DataFCKeditor').value = document.getElementById('DataTextarea').value ;
 
	// Automatically calculates the editor base path based on the _samples directory.
	// This is usefull only for these samples. A real application should use something like this:
	// oFCKeditor.BasePath = '/fckeditor/' ;	// '/fckeditor/' is the default value.
	//var sBasePath = document.location.href.substring(0,document.location.href.lastIndexOf('_samples')) ;
	var sBasePath = "../js/fckeditor/";
 	
	// Create an instance of FCKeditor (using the target textarea as the name).
	var oFCKeditor = new FCKeditor( textareaname ) ;
	oFCKeditor.BasePath = sBasePath ;
	oFCKeditor.Config['ToolbarStartExpanded'] = false ;
	oFCKeditor.Width = '100%' ;
	oFCKeditor.Height = '150' ;
	oFCKeditor.ReplaceTextarea() ;
}

//指定textarea的name转化为fckedit
function makeFCKeditor(textareaname,row)
{
	//var sBasePath = document.location.href.substring(0,document.location.href.lastIndexOf('_samples')) ;
	var sBasePath = "../js/fckeditor/";
	// Create an instance of FCKeditor (using the target textarea as the name).
	var oFCKeditor = new FCKeditor( textareaname ) ;
	oFCKeditor.BasePath = sBasePath ;
	oFCKeditor.ToolbarSet="ete2";
	oFCKeditor.Config['ToolbarStartExpanded'] = false ;
	oFCKeditor.Width = '100%' ;
	oFCKeditor.Height = (row||3)*30;
	oFCKeditor.ReplaceTextarea() ;
}
try{
	function makeFCKeditorByJobj(jneedFck){
		jneedFck.each(function(){
				var id = $(this).attr("id")||new G().nextStrId("_textarea");
				$(this).attr("id",id);
				try{
					makeFCKeditor(id,$(this).attr("rows"));
				}catch(e){					
				}
			})
	}
	$(function(){
		var jfckContent = $("span[_showFckId!=]");
		var fckSel = "textarea[fck=fck]"
		if(jfckContent.size()>0){
			jfckContent.click(function(){ 
				try{
					var cid =$(this).attr("_showFckId")||"";
					if(cid!=""){
						makeFCKeditorByJobj($("#"+cid).find( fckSel ));
					}
				}catch(e){
				}
			})
		}else{
			makeFCKeditorByJobj($(fckSel))
		}
	})
}catch(e){
}
