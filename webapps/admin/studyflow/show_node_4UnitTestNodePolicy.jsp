<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
</head>

<body>
<form action="">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：训练引擎 &gt; 流程定义 &gt; 节点详情</td>
  </tr>
</table>
<table width="100%" border="0" align="center" >
  <tr>
	<td>
      <table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  bgcolor="#BEDAF1">
          <tr>
            <td width="50%" align="left"   class="txt12blueh">节点信息</td>    
            <td width="50%" align="right"  class="txt12blue" >
              <span  style="cursor:hand" onClick="show('table2');hide('table1')">修改</span>|
              <span  style="cursor:hand" onClick="">删除</span>|
              <span style="cursor:hand"  onclick="show('table1');" >显示</span>|
              <span  style="cursor:hand" onClick="hide('table1');">隐藏</span>
            </td>
          </tr> 
      </table>
      <table id="table1" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点名称：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">单元测试一</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点类型：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">单元测试</td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">训练顺序：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">001</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父节点：</td>
            <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">基础训练</td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点说明：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF" colspan="3">基础训练单元一</td>
          </tr>
      </table>
      <table id="table2" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" style="display:xxx">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点名称：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                <input class="logininputmanage" type="text" name="textfield9" id="textfield9"/>
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点类型：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                <select name="papercategory">
                    <option>缺省</option>
                    <option>模块评测</option>
                    <option>阶段测试</option>
                    <option>训练单元</option>
                    <option>单元测试</option>
                </select>
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">训练顺序：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                <input class="logininputmanage" type="text" name="textfield9" id="textfield9" size="10"/>
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父节点：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                <select name="papercategory">
                    <option>无</option>
                    <option>基础训练</option>
                    <option>--基础阶段训练一</option>
                </select>
            </td>
          </tr>
          <tr>
            <td width="17%"  align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点说明：</td>
            <td align="left" valign="top" bgcolor="#FFFFFF" colspan="3">
                <textarea name="textarea" id="textarea" cols="35" rows="2"></textarea>
            </td>
          </tr>
          <tr>
            <td height="70" align="center" bgcolor="#FFFFFF" colspan="4"><table width="121" border="0">
              <tr>
                <td><input type="button" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="  取 消  " class="btn_2k3"  onClick="show('table1');hide('table2');"/></td>
              </tr>
            </table></td>
          </tr>
      </table>
    </td>
  </tr>
  <tr height="8">
    <td></td>
  </tr>
</table>
<table width="100%" border="0" align="center" >
  <tr>
    <td >
	  <table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
	    <tr>
	      <td  width="50%" align="left" class="txt12blueh">流转策略</td>
          <td align="right"  class="txt12blue">
              <span  style="cursor:hand" onClick="show('table12');hide('table11');">修改</span>|
              <span  style="cursor:hand" onClick="">删除</span>|
              <span style="cursor:hand"  onclick="show('table11');" >显示</span>|
              <span  style="cursor:hand" onClick="hide('table11');">隐藏</span>
          </td>
	    </tr>
	  </table>
      <table id="table11" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重复训练范围</td>
            <td align="left" bgcolor="#FFFFFF" colspan="3">本级</td>
          </tr>
      </table>
      <table id="table12" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3"  >
          <tr height="45">
            <td align="left" valign="middle" bgcolor="#FFFFFF" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <input type="radio" name="radio" id="radio"/>使用策略模板&nbsp;&nbsp;
               <input type="button" value="选择模板" class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <input type="radio" name="radio" id="radio"/>继承父节点策略&nbsp;&nbsp;<input type="button" value=" 继承 " class="btn_2k3"/>&nbsp;&nbsp;
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重复训练范围</td>
            <td align="left" bgcolor="#FFFFFF" colspan="3">
                <select name="papercategory">
                    <option>本级</option>
                    <option>上级</option>
                    <option>上上级</option>
                </select>
            </td>
          </tr>
          <tr>
            <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
                <tr>
                    <td align="center">
                    <input type="button" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" value="  取 消  " class="btn_2k3" onClick="show('table11');hide('table12');"/>
                    </td>
                </tr>
              </table>
            </td>
          </tr>
      </table>
    </td>
  </tr>
</table>
<table width="100%" border="0" align="center" >
  <tr>
    <td >
	  <table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
	    <tr>
	      <td width="50%" align="left" class="txt12blueh">训练策略</td>
          <td align="right"  class="txt12blue" >
			  <div v=trainPocliy url="{surl:"",eurl:"",uurl:""}">
              <span style="cursor:hand" onClick="show('table14');hide('table13');" opt="opt" >修改</span>|
              <span style="cursor:hand" onClick="">删除</span>|
              <span style="cursor:hand" onClick="show('table13');" opt="opt">显示</span>|
              <span style="cursor:hand" onClick="hide('table13');" opt="opt">隐藏</span>
			  </div>
          </td>
	    </tr>
	  </table>
	  <div id="s_trainPocliy">
      <table id="table13" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">全卷预览时间：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">允许设置疑问标记：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看解析：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看答案：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否随机出题：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否颠倒答案顺序：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过正确率：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做通过正确率：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题类型：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题顺序：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            </td>
          </tr>
      </table>
	  </div>
	  <div id="e_trainPocliy">
      <table id="table14" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" >
          <tr height="45">
            <td align="left" valign="middle" bgcolor="#FFFFFF" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <input type="radio" name="radio" id="radio"/>使用策略模板&nbsp;&nbsp;
               <input type="button" value="选择模板" class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <input type="radio" name="radio" id="radio"/>继承父节点策略&nbsp;&nbsp;<input type="button" value=" 继承 " class="btn_2k3"/>&nbsp;&nbsp;
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">全卷预览时间：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                <input class="logininputmanage" type="text" name="textfield9" id="textfield9" size="10"/>
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">允许设置疑问标记：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
                <input type="checkbox" name="checkbox" id="checkbox" />
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看解析：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                <input type="radio" name="radio" id="radio" value="radio" />随时
                <input type="radio" name="radio" id="radio" value="radio" />做题后
                <input type="radio" name="radio" id="radio" value="radio" />正确后
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看答案：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
                <input type="radio" name="radio" id="radio" value="radio" />随时
                <input type="radio" name="radio" id="radio" value="radio" />做题后
                <input type="radio" name="radio" id="radio" value="radio" />正确后
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否随机出题：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                <input type="checkbox" name="checkbox" id="checkbox" />
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否颠倒答案顺序：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
                <input type="checkbox" name="checkbox" id="checkbox" />
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过正确率：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                <input class="logininputmanage" type="text" name="textfield9" id="textfield9" size="10"/> %
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做通过正确率：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
                <input class="logininputmanage" type="text" name="textfield9" id="textfield9" size="10"/> %
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题类型：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                <select name="papercategory">
                    <option>全部</option>
                    <option>错题</option>
                    <option>未答题</option>
                    <option>错题&未答题</option>
                    <option>零星题</option>
                    <option>半星题</option>
                    <option>一星题</option>
                    <option>二星题</option>
                    <option>三星题</option>
                    <option>四星题</option>
                    <option>五星题</option>
                </select>
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题顺序：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
                <select name="papercategory">
                    <option>无</option>
                    <option>错题</option>
                    <option>新题</option>
                    <option>星题</option>
                </select>
                <img src="../images/arrow3.gif" width="11" height="9" align="absmiddle">
                <select name="papercategory">
                    <option>无</option>
                    <option>错题</option>
                    <option>新题</option>
                    <option>星题</option>
                </select>
                <img src="../images/arrow3.gif" width="11" height="9" align="absmiddle">
                <select name="papercategory">
                    <option>无</option>
                    <option>错题</option>
                    <option>新题</option>
                    <option>星题</option>
                </select>
            </td>
          </tr>
          <tr>
            <td height="70" align="center" bgcolor="#FFFFFF" colspan="4"><table width="121" border="0">
              <tr>
                <td><input type="button" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="  取 消  " class="btn_2k3"  onClick="show('table13');hide('table14');"/></td>
              </tr>
            </table></td>
          </tr>
      </table>
	  </div>
    </td>
  </tr>
</table>
<table width="100%" border="0" align="center" >
  <tr>
    <td >
	  <table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
	    <tr>
	      <td  width="50%" align="left" class="txt12blueh">组卷策略</td>
          <td align="right"  class="txt12blue">
              <span  style="cursor:hand" onClick="show('table16');show('table161');show('table162');hide('table15');">修改</span>|
              <span  style="cursor:hand" onClick="">删除</span>|
              <span style="cursor:hand"  onclick="show('table19');" >显示</span>|
              <span  style="cursor:hand" onClick="hide('table19');">隐藏</span>
          </td>
	    </tr>
	  </table>
      <table id="table19" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr id="table15">
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">组卷方式：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">自动组卷</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">使用试卷：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF"><a href="../itembank/show_paper.htm">考研基础强化训练11</a></td>
          </tr>
          <tr height="45" id="table16" style="display:xxx">
            <td align="left" valign="middle" bgcolor="#FFFFFF" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <input type="radio" name="radio" id="radio"/>使用策略模板&nbsp;&nbsp;
               <input type="button" value="选择模板" class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <input type="radio" name="radio" id="radio"/>继承父节点策略&nbsp;&nbsp;<input type="button" value=" 继承 " class="btn_2k3"/>&nbsp;&nbsp;
            </td>
          </tr>
          <tr id="table161" style="display:xxx">
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">组卷方式：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
                <select name="papercategory">
                    <option>手工组卷</option>
                    <option>自动组卷</option>
                    <option>动态出题</option>
                </select>
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">使用试卷：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
                <input class="logininputmanage" type="text" name="textfield9" id="textfield9" />&nbsp;&nbsp;
               <input type="button" value="选择" class="btn_2k3"/>&nbsp;&nbsp;
            </td>
          </tr>
          <tr id="table162" style="display:xxx">
            <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
                <tr>
                    <td align="center">
                    <input type="button" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" value="  取 消  " class="btn_2k3" onClick="show('table15');hide('table16');hide('table161');hide('table162');"/>
                    </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td colspan="4" bgcolor="#FFFFFF">
              <table width="100%" border="0" align="center" cellpadding="6" cellspacing="1">
                  <tr>
                    <td width="50%" align="left" class="txt12blueh">组卷条件</td>    
                    <td width="50%" align="right"  class="txt12blue" >
                      <span  style="cursor:hand" onClick="show('table21');">使用模板&继承</span>
                      <span  style="cursor:hand" onClick="show('table18');">新增条件</span>
                      <span  style="cursor:hand">删除全部</span>
                      <span  style="cursor:hand">预览试题</span>
                    </td>
                  </tr> 
              </table>
              <table id="table21" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" style="display:xxx">
                  <tr height="45">
                    <td align="left" valign="middle" bgcolor="#FFFFFF" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                       <input type="radio" name="radio" id="radio"/>使用策略模板&nbsp;&nbsp;
                       <input type="button" value="选择模板" class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                       <input type="radio" name="radio" id="radio"/>继承父节点策略&nbsp;&nbsp;<input type="button" value=" 继承 " class="btn_2k3"/>&nbsp;&nbsp;
                    </td>
                  </tr>
                  <tr>
                    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
                      <table border="0" width="100%">
                        <tr>
                            <td align="center">
                            <input type="button" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="button" value="  取 消  " class="btn_2k3" onClick="hide('table21');"/>
                            </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
              </table>
              <table id="table18" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" style="display:xxx">
                  <tr>
                    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">地区：</td>
                    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                        <select name="papercategory">
                            <option>全国I</option>
                            <option>全国II</option>
                            <option>北京</option>
                            <option>上海</option>
                            <option>浙江</option>
                        </select>
                    </td>
                    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学科：</td>
                    <td width="33%" align="left" bgcolor="#FFFFFF">
                        <select name="papercategory">
                            <option>语文</option>
                            <option>数学</option>
                            <option>英语</option>
                            <option>物理</option>
                            <option>化学</option>
                        </select>
                    </td>
                  </tr>
                  <tr>
                    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
                    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                        <select name="papercategory">
                            <option>完形填空</option>
                            <option>阅读理解</option>
                            <option>写作</option>
                        </select>
                    </td>
                    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
                    <td width="33%" align="left" bgcolor="#FFFFFF">
                        <input class="logininputmanage" type="text" name="textfield9" id="textfield9"/>
                    </td>
                  </tr>
                  <tr>
                    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
                    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                        <select name="papercategory">
                            <option>真题</option>
                            <option>模拟</option>
                            <option>自编</option>
                            <option>专项</option>    		
                        </select>
                    </td>
                    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">原始套卷：</td>
                    <td width="33%" align="left" bgcolor="#FFFFFF">
                        <input class="logininputmanage" type="text" name="textfield9" id="textfield9"/>
                    </td>
                  </tr>  
                  <tr>
                    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题难易度：</td>
                    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                        <input class="logininputmanage" type="text" name="textfield9" id="textfield9"/>
                    </td>
                    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题效度：</td>
                    <td width="33%" align="left" bgcolor="#FFFFFF">
                        <input class="logininputmanage" type="text" name="textfield9" id="textfield9"/>
                    </td>
                  </tr>
                  <tr>
                    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题数： </td>
                    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF" >
                        <input class="logininputmanage" type="text" name="textfield9" id="textfield9"/>
                    </td>
                    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
                    <td width="33%" align="left" bgcolor="#FFFFFF">
                    </td>
                  </tr>
                  <tr>
                    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
                      <table border="0" width="100%">
                        <tr>
                            <td align="center">
                            <input type="button" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="button" value="  取 消  " class="btn_2k3" onClick="hide('table18');"/>
                            </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
              </table>
              <table id="table17" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#E3E3E3">
                  <tr align="center"  bgcolor="#FFFFFF" class="txt12blue">
                    <td>序号</td>
                    <td>地区</td>
                    <td>学科</td>
                    <td>题型</td>
                    <td>试题年份</td>
                    <td>试题来源</td>
                    <td>原始套卷</td>
                    <td>试题难度</td>
                    <td>试题效度</td>
                    <td>题数</td>
                    <td>操作</td>
                  </tr>
                  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
                    <td>1</td>
                    <td>全国I</td>
                    <td>英语</td>
                    <td>完形填空</td>
                    <td>2004</td>
                    <td>真题</td>
                    <td>无</td>
                    <td>60</td>
                    <td>无</td>
                    <td>5</td>
                    <td><a href="edit_paper_assembling_req.htm">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="">删除</a></td>
                  </tr>
                  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
                    <td>2</td>
                    <td>全国I</td>
                    <td>英语</td>
                    <td>完形填空</td>
                    <td>2004</td>
                    <td>真题</td>
                    <td>无</td>
                    <td>60</td>
                    <td>无</td>
                    <td>5</td>
                    <td><a href="edit_paper_assembling_req.htm">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="">删除</a></td>
                  </tr>
                  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
                    <td>3</td>
                    <td>全国I</td>
                    <td>英语</td>
                    <td>完形填空</td>
                    <td>2004</td>
                    <td>真题</td>
                    <td>无</td>
                    <td>60</td>
                    <td>无</td>
                    <td>5</td>
                    <td><a href="edit_paper_assembling_req.htm">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="">删除</a></td>
                  </tr>
              </table>
            </td>
          </tr>
      </table>
    </td>
  </tr>
  <tr height="10">
    <td></td>
  </tr>
</table>
</form>
</body>
</html>
