<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
  
  <script language="javaScript" type="text/javascript">
	function replacePImg(obj) {
		var imgObj = $(obj).find("img");
		$(imgObj).each( function() {
			var src = $(this).attr("src");
			if (src.indexOf("fatcow_002")!=-1)  src = src.replace("fatcow_002", "fatcow_003");
			else if (src.indexOf("fatcow_003")!=-1) src = src.replace("fatcow_003", "fatcow_002");
			$(this).attr("src", src);
		});	
	}

	function replaceIImg(obj) {
		var imgObj = $(obj).find("img");
		$(imgObj).each( function() {
			var src = $(this).attr("src");
			if (src.indexOf("fatcow_152")!=-1)  src = src.replace("fatcow_152", "fatcow_151");
			else if (src.indexOf("fatcow_151")!=-1) src = src.replace("fatcow_151", "fatcow_152");
			$(this).attr("src", src);
		});	
	}

	function hrefTitle(id) {
		location.href = "#pt" + id;
	}
  </script>