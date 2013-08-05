package com.ambow.trainingengine.report.web.action;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;


import com.opensymphony.webwork.ServletActionContext;
/* 
 * <p>Description: 把chart文件流换成是通过HttpServletResponse 
 *    对象获取到的输出流在浏览器中输出</p> 
 * author: zhangtao 
 * @version 1.0  
 */ 
@SuppressWarnings("serial")
public class GetChartResult{ 

	public String execute(){ 

		int zeroStarNum = 0;
		int halfStarNum = 0;
		int oneStarNum = 0;
		int twoStarNum = 0;
		int threeStarNum = 0;
		int fourStarNum = 0;
		int fiveStarNum = 0;
		int newQuestionNum = 0;	
			
			zeroStarNum = Integer.parseInt((this.get_zero()==null||this.get_zero().equals(""))?"0":this.get_zero());
			halfStarNum = Integer.parseInt((this.get_half()==null||this.get_zero().equals(""))?"0":this.get_half());
			oneStarNum = Integer.parseInt((this.get_one()==null||this.get_zero().equals(""))?"0":this.get_one());
			twoStarNum = Integer.parseInt((this.get_two()==null||this.get_zero().equals(""))?"0":this.get_two());
			threeStarNum = Integer.parseInt((this.get_three()==null||this.get_zero().equals(""))?"0":this.get_three());
			fourStarNum = Integer.parseInt((this.get_four()==null||this.get_zero().equals(""))?"0":this.get_four());
			fiveStarNum = Integer.parseInt((this.get_five()==null||this.get_zero().equals(""))?"0":this.get_five());
		
		if(this.get_new()==null||"".equals(this.get_new())){
			this.makePieChart(zeroStarNum,halfStarNum,oneStarNum,twoStarNum,threeStarNum,fourStarNum,fiveStarNum);
		}
		else{
			newQuestionNum = Integer.parseInt(this.get_new());			
			makePieChart(zeroStarNum,halfStarNum,oneStarNum,twoStarNum,threeStarNum,fourStarNum,fiveStarNum,newQuestionNum);
		}
        
		return null;
		
    } 
    @SuppressWarnings("deprecation")
	private void makePieChart(int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8)
    {
    	DefaultPieDataset pieDataset = new DefaultPieDataset();
		pieDataset.setValue("0星", new Integer(arg1));
		pieDataset.setValue("半星", new Integer(arg2));
		pieDataset.setValue("1星", new Integer(arg3));
		pieDataset.setValue("2星", new Integer(arg4));
		pieDataset.setValue("3星", new Integer(arg5));
		pieDataset.setValue("4星", new Integer(arg6));
		pieDataset.setValue("5星", new Integer(arg7));
    	pieDataset.setValue("New", Integer.valueOf(arg8));
        JFreeChart chart = ChartFactory.createPieChart("",pieDataset,true,true,false);
        //chart.addSubtitle(new TextTitle("啊啊啊setCircular(true);", new Font("黑体", 0, 12)));
        chart.setBackgroundPaint(Color.white);   //设定背景色为白色
       // chart.clearSubtitles();
        PiePlot piePlot = (PiePlot)chart.getPlot(); //获得 plot：CategoryPlot！！
        piePlot.setBackgroundPaint(Color.white); //设定图表数据显示部分背景色
        piePlot.setLabelFont(new Font("黑体",0,12));
        piePlot.setIgnoreNullValues(true);
        piePlot.setIgnoreZeroValues(true);
        piePlot.setLabelGenerator(new org.jfree.chart.labels.StandardPieSectionLabelGenerator("{0}={1}({2})",NumberFormat.getNumberInstance(),new DecimalFormat("0%")));
        //把文件流换成是通过HttpServletResponse对象获取到的输出流 
        HttpServletResponse response = ServletActionContext.getResponse(); 
        OutputStream os;
		try {
			os = response.getOutputStream();
			if(_type != null && _type.equals("mpc")){
				ChartUtilities.writeChartAsPNG(os, chart, 300, 205);
			}else{
				ChartUtilities.writeChartAsPNG(os, chart, 400, 275);
			}
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
    
    //不需要生成new的百分比
    private void makePieChart(int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7)
    {    	
    	DefaultPieDataset pieDataset = new DefaultPieDataset();
    	
    	pieDataset.setValue("0星", new Integer(arg1));
		pieDataset.setValue("半星", new Integer(arg2));
	    pieDataset.setValue("1星", new Integer(arg3));
	   	pieDataset.setValue("2星", new Integer(arg4));
	    pieDataset.setValue("3星", new Integer(arg5));
	    pieDataset.setValue("4星", new Integer(arg6));
	   	pieDataset.setValue("5星", new Integer(arg7));
		
        JFreeChart chart = ChartFactory.createPieChart("",pieDataset,true,true,false);
        chart.setBackgroundPaint(Color.white);   //设定背景色为白色
        //chart.addSubtitle(new TextTitle("啊啊啊setCircular(true);", new Font("黑体", 0, 12)));
      //  chart.clearSubtitles();
        PiePlot piePlot = (PiePlot)chart.getPlot(); //获得 plot：CategoryPlot！！
        piePlot.setLabelFont(new Font("黑体",0,12));
        piePlot.setBackgroundPaint(Color.white); //设定图表数据显示部分背景色       
        piePlot.setLabelGenerator(new org.jfree.chart.labels.StandardPieSectionLabelGenerator("{0}={1}({2})",NumberFormat.getNumberInstance(),new DecimalFormat("0%")));
        piePlot.setIgnoreNullValues(true);
        piePlot.setIgnoreZeroValues(true);
        //把文件流换成是通过HttpServletResponse对象获取到的输出流 
        HttpServletResponse response = ServletActionContext.getResponse(); 
        OutputStream os;
		try {
			os = response.getOutputStream();
			if(_type != null && _type.equals("mpc")){
				ChartUtilities.writeChartAsPNG(os, chart, 300, 179);
			}else{
				ChartUtilities.writeChartAsPNG(os, chart, 400, 275);
			}
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
    private String _zero;
    private String _half;
    private String _one;
    private String _two;
    private String _three;
    private String _four;
    private String _five;
    private String _new;
    private String _type;
	public String get_type() {
		return _type;
	}
	public void set_type(String _type) {
		this._type = _type;
	}
	public String get_zero() {
		return _zero;
	}
	public void set_zero(String _zero) {
		this._zero = _zero;
	}
	public String get_half() {
		return _half;
	}
	public void set_half(String _half) {
		this._half = _half;
	}
	public String get_one() {
		return _one;
	}
	public void set_one(String _one) {
		this._one = _one;
	}
	public String get_two() {
		return _two;
	}
	public void set_two(String _two) {
		this._two = _two;
	}
	public String get_three() {
		return _three;
	}
	public void set_three(String _three) {
		this._three = _three;
	}
	public String get_four() {
		return _four;
	}
	public void set_four(String _four) {
		this._four = _four;
	}
	public String get_five() {
		return _five;
	}
	public void set_five(String _five) {
		this._five = _five;
	}
	public String get_new() {
		return _new;
	}
	public void set_new(String _new) {
		this._new = _new;
	}
	
    
} 
