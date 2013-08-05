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
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">节点组</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点类型：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">节点组</td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">训练顺序：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">001</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父节点：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">基础训练</td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点说明：</td>
            <td align="left" valign="top" bgcolor="#FFFFFF" colspan="3">基础训练单元一</td>
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
            <td height="70" align="center" bgcolor="#FFFFFF" colspan="4"><table width="300" border="0">
              <tr>
                <td><input type="button" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="  取 消  " class="btn_2k3"  onClick="show('table1');hide('table2');"/></td>
              </tr>
            </table></td>
          </tr>
      </table>
    </td>
  </tr>
</table>
<table width="100%" border="0" align="center" >
  <tr>
	<td>
      <table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  bgcolor="#E9F0F6">
          <tr>
            <td width="50%" align="left"   class="txt12blueh">流程策略</td>
            <td width="50%" align="right"  class="txt12blue" >
              <span  style="cursor:hand" onClick="show('table4');hide('table3');">修改</span>|
              <span  style="cursor:hand" onClick="">删除</span>|
              <span style="cursor:hand"  onclick="show('table3');" >显示</span>|
              <span  style="cursor:hand" onClick="hide('table3');">隐藏</span>
            </td>
          </tr> 
      </table>
      <table id="table3" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否为显示模块：</td>
            <td width="83%" align="left" bgcolor="#FFFFFF">是</td>
          </tr>
      </table>
      <table id="table4" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" style="display:xxx">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否为显示模块：</td>
            <td width="83%" align="left" bgcolor="#FFFFFF">
                <input type="checkbox" name="checkbox" id="checkbox" />
            </td>
          </tr>
          <tr>
            <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
                <tr>
                    <td align="center">
                    <input type="button" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" value="  取 消  " class="btn_2k3" onClick="show('table3');hide('table4');"/>
                    </td>
                </tr>
              </table>
            </td>
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
	      <td  width="50%" align="left"    class="txt12blueh">模块评测类节点流转策略</td>
          <td align="right"  class="txt12blue">
              <span  style="cursor:hand" onClick="show('table6');hide('table5');">修改</span>|
              <span  style="cursor:hand" onClick="">删除</span>|
              <span style="cursor:hand"  onclick="show('table5');" >显示</span>|
              <span  style="cursor:hand" onClick="hide('table5');">隐藏</span>
          </td>
	    </tr>
	  </table>
      <table id="table5" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">暂无</td>
            <td width="83%" align="left" bgcolor="#FFFFFF">暂无</td>
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
	      <td  width="50%" align="left"    class="txt12blueh">阶段测试类节点流转策略</td>
          <td align="right"  class="txt12blue">
              <span  style="cursor:hand" onClick="show('table8');hide('table7');">修改</span>|
              <span  style="cursor:hand" onClick="">删除</span>|
              <span style="cursor:hand"  onclick="show('table7');" >显示</span>|
              <span  style="cursor:hand" onClick="hide('table7');">隐藏</span>
          </td>
	    </tr>
	  </table>
      <table id="table7" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流转条件：</td>
            <td width="83%" align="left" bgcolor="#FFFFFF">
              <table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#E3E3E3">
                  <tr align="center"  bgcolor="#FFFFFF" class="txt12blue" >
                    <td>序号</td>
                    <td>起始值</td>
                    <td>结束值</td>
                    <td>跳转位置</td>
                  </tr>
                  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
                    <td>1</td>
                    <td>0</td>
                    <td>50</td>
                    <td>基础训练1</td>
                  </tr>   
                  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
                    <td>2</td>
                    <td>56</td>
                    <td>75</td>
                    <td>基础训练1</td>
                  </tr>   
                  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
                    <td>3</td>
                    <td>76</td>
                    <td>90</td>
                    <td>基础训练1</td>
                  </tr>   
                  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
                    <td>4</td>
                    <td>90</td>
                    <td>100</td>
                    <td>基础训练1</td>
                  </tr>   
              </table>
            </td>
          </tr>
      </table>
      <table id="table8" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" style="display:xxx">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">选择策略模板：</td>
            <td width="83%" align="left" bgcolor="#FFFFFF">
                <select name="papercategory">
                    <option>无</option>
                    <option>基础训练</option>
                    <option>完形填空</option>
                </select>
            </td>
          </tr>
          <tr>
            <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
                <tr>
                    <td align="center">
                    <input type="button" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" value="  取 消  " class="btn_2k3" onClick="show('table7');hide('table8');"/>
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
	      <td  width="50%" align="left"    class="txt12blueh">训练单元类节点流转策略</td>
          <td align="right"  class="txt12blue">
              <span  style="cursor:hand" onClick="show('table10');hide('table9');">修改</span>|
              <span  style="cursor:hand" onClick="">删除</span>|
              <span style="cursor:hand"  onclick="show('table9');" >显示</span>|
              <span  style="cursor:hand" onClick="hide('table9');">隐藏</span>
          </td>
	    </tr>
	  </table>
      <table id="table9" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">往前</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">不通过</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">返回</td>
          </tr>
      </table>
      <table id="table10" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" style="display:xxx">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">选择策略模板：</td>
            <td width="83%" align="left" bgcolor="#FFFFFF">
                <select name="papercategory">
                    <option>无</option>
                    <option>基础训练</option>
                    <option>完形填空</option>
                </select>
            </td>
          </tr>
          <tr>
            <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
                <tr>
                    <td align="center">
                    <input type="button" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" value="  取 消  " class="btn_2k3" onClick="show('table9');hide('table10');"/>
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
	      <td  width="50%" align="left" class="txt12blueh">单元测试类节点流转策略</td>
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
      <table id="table12" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" style="display:xxx">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">选择策略模板：</td>
            <td width="83%" align="left" bgcolor="#FFFFFF">
                <select name="papercategory">
                    <option>无</option>
                    <option>基础训练</option>
                    <option>完形填空</option>
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
	      <td  width="50%" align="left" class="txt12blueh">训练策略</td>
          <td align="right"  class="txt12blue">
              <span  style="cursor:hand" onClick="show('table14');hide('table13');">修改</span>|
              <span  style="cursor:hand" onClick="">删除</span>|
              <span style="cursor:hand"  onclick="show('table13');" >显示</span>|
              <span  style="cursor:hand" onClick="hide('table13');">隐藏</span>
          </td>
	    </tr>
	  </table>
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
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做通过正确率：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题类型：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题顺序：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过是否动态出题：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题类型：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题比率：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示内容：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            </td>
            <td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示相关动作：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文内容：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            </td>
            <td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文相关动作：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示内容：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            </td>
            <td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示相关动作：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文内容：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            </td>
            <td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文相关动作：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示内容：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            </td>
            <td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示相关动作：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错译文内容：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            </td>
            <td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错译文相关动作：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            </td>
          </tr>
      </table>
      <table id="table14" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" style="display:xxx">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">选择策略模板：</td>
            <td width="83%" align="left" bgcolor="#FFFFFF">
                <select name="papercategory">
                    <option>无</option>
                    <option>基础训练</option>
                    <option>完形填空</option>
                </select>
            </td>
          </tr>
          <tr>
            <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
                <tr>
                    <td align="center">
                    <input type="button" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" value="  取 消  " class="btn_2k3" onClick="show('table13');hide('table14');"/>
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
	      <td  width="50%" align="left" class="txt12blueh">组卷策略</td>
          <td align="right"  class="txt12blue">
              <span  style="cursor:hand" onClick="show('table16');show('table161');hide('table15');">修改</span>|
              <span  style="cursor:hand" onClick="">删除</span>|
              <span style="cursor:hand"  onclick="show('table19');" >显示</span>|
              <span  style="cursor:hand" onClick="hide('table19');">隐藏</span>
          </td>
	    </tr>
	  </table>
      <table id="table19" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr id="table15">
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">组卷方式：</td>
            <td width="83%" align="left" bgcolor="#FFFFFF">自动组卷</td>
          </tr>
          <tr id="table16" style="display:xxx">
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">选择策略模板：</td>
            <td width="83%" align="left" bgcolor="#FFFFFF">
                <select name="papercategory">
                    <option>无</option>
                    <option>基础训练</option>
                    <option>完形填空</option>
                </select>
            </td>
          </tr>
          <tr id="table161" style="display:xxx">
            <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
                <tr>
                    <td align="center">
                    <input type="button" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" value="  取 消  " class="btn_2k3" onClick="show('table15');hide('table16');hide('table161');"/>
                    </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td colspan="2" bgcolor="#FFFFFF">
              <table width="100%" border="0" align="center" cellpadding="6" cellspacing="1">
                  <tr>
                    <td width="50%" align="left" class="txt12blueh">组卷条件</td>    
                    <td width="50%" align="right"  class="txt12blue" >
                      <span  style="cursor:hand" onClick="show('table18');hide('table17');">修改</span>|
                      <span  style="cursor:hand" onClick="">删除</span>
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
                  </tr>   
              </table>
              <table id="table18" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" style="display:xxx">
                  <tr>
                    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">选择策略模板：</td>
                    <td width="83%" align="left" bgcolor="#FFFFFF">
                        <select name="papercategory">
                            <option>无</option>
                            <option>基础训练</option>
                            <option>完形填空</option>
                        </select>
                    </td>
                  </tr>
                  <tr>
                    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
                      <table border="0" width="100%">
                        <tr>
                            <td align="center">
                            <input type="button" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="button" value="  取 消  " class="btn_2k3" onClick="show('table17');hide('table18');"/>
                            </td>
                        </tr>
                      </table>
                    </td>
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
