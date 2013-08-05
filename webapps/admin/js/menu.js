var temp, temp2, cookieArray, cookieArray2, cookieCount;
function initiate()
{
  cookieCount=0;
  if(document.cookie)
  {
    cookieArray=document.cookie.split(";");
    cookieArray2=new Array();
    for(i in cookieArray)
    {
      cookieArray2[cookieArray[i].split("=")[0].replace(/ /g,"")]=cookieArray[i].split("=")[1].replace(/ /g,"");
    }
  }
  cookieArray=(document.cookie.indexOf(menu_id + "_state=")>=0) ? cookieArray2[menu_id + "_state"].split(","):new Array();  
  temp=document.getElementById("containerul");
	if(temp != null)
	{
  	for(var o=0;o<temp.getElementsByTagName("li").length;o++)
  	{
    	if(temp.getElementsByTagName("li")[o].getElementsByTagName("ul").length>0)
    	{
      	temp2				= document.createElement("span");
      	temp2.className			= "symbols";
      	temp2.style.backgroundImage	= (cookieArray.length>0)?((cookieArray[cookieCount]=="true")?"url(tree/minus.png)":"url(tree/plus.png)"):"url(tree/plus.png)";
      	temp2.onclick=function()
      	{
        	showhide(this.parentNode);
        	writeCookie();
      	}
      	temp.getElementsByTagName("li")[o].insertBefore(temp2,temp.getElementsByTagName("li")[o].firstChild)
      	temp.getElementsByTagName("li")[o].getElementsByTagName("ul")[0].style.display = "none";
      	if(cookieArray[cookieCount]=="true")
      	{
        	showhide(temp.getElementsByTagName("li")[o]);
      	}
      	cookieCount++;
    	}
    	else
    	{
      	temp2	= document.createElement("span");
      	temp2.className			= "symbols";
      	temp2.style.backgroundImage	= "url(tree/FolderUp.gif)";
      	temp.getElementsByTagName("li")[o].insertBefore(temp2,temp.getElementsByTagName("li")[o].firstChild);
    	}
		}
 	}
}

function showhide(el)
{
  el.getElementsByTagName("ul")[0].style.display=(el.getElementsByTagName("ul")[0].style.display=="block")?"none":"block";
  el.getElementsByTagName("span")[0].style.backgroundImage=(el.getElementsByTagName("ul")[0].style.display=="block")?"url(tree/minus.png)":"url(tree/plus.png)";
}

function writeCookie()
{
	cookieArray=new Array()
  for(var q=0;q<temp.getElementsByTagName("li").length;q++)
  {
    if(temp.getElementsByTagName("li")[q].childNodes.length>0)
    {
      if(temp.getElementsByTagName("li")[q].childNodes[0].nodeName=="SPAN" && temp.getElementsByTagName("li")[q].getElementsByTagName("ul").length>0)
      {
        cookieArray[cookieArray.length]=(temp.getElementsByTagName("li")[q].getElementsByTagName("ul")[0].style.display=="block");
      }
    }
  }
  document.cookie=menu_id + "_state="+cookieArray.join(",")+";expires="+new Date(new Date().getTime() + 365*24*60*60*1000).toGMTString();
}