/**
 * 
 */
package com.ambow.trainingengine.util;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.util.Tool;

/**
 * @author yuanjunqi
 * 
 */
public class CloudCalculate extends Configured implements Tool {
	/**
	 * 完成输入的<key,value>到中间结果的映射
	 * @author yuanjunqi
	 *
	 */
	public static class MapClass extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, IntWritable> {
		DataObject object = DataObject.getInstance();
		private final static IntWritable one = new IntWritable();
		private Text word = new Text();

		public void map(LongWritable key, Text value,
				OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			String line = value.toString();
			word.set(line);
			output.collect(word, one);
			object.getList().add(line);

		}
	}

	/**
	 * 对中间结果做合并，形成最终结果
	 */
	public static class ReduceClass extends MapReduceBase implements
			Reducer<Text, IntWritable, Text, DoubleWritable> {
		Similarity sim = new Similarity();
		DataObject object = DataObject.getInstance();

		public void reduce(Text key, Iterator<IntWritable> values,
				OutputCollector<Text, DoubleWritable> output, Reporter reporter)
				throws IOException {

			object.getList().remove(key.toString());
			String str1 = key.toString();
			String[] str1Arr = str1.split(":");
			for (int i = 0; i < object.getList().size(); i++) {
				String data = (String) object.getList().get(i);
				String[] dataArr = data.split(":");
				if(str1Arr[0].equals(dataArr[0])){
					continue;
				}
				if(str1Arr.length <2 || dataArr.length<2){
					continue;
				}
				double s = sim.sim(str1Arr[1], dataArr[1]);
				System.out.println("str1="+str1Arr[0]+",str2="+dataArr[0]+",sim="+s);
				if(s>=0.9){		
					output.collect(new Text(str1Arr[0]), new DoubleWritable(s));
					output.collect(new Text(dataArr[0]), new DoubleWritable(s));
				}
			}
			
		}
	}

	/**
	 * 任务执行方法
	 */
	public int run(String[] args) throws Exception {
		JobConf conf = new JobConf(getConf(), CloudCalculate.class);
		conf.setJobName("calculate");
		// the keys are words (strings)
		conf.setOutputKeyClass(Text.class);
		// the values are counts (ints)
		conf.setOutputValueClass(IntWritable.class);
		conf.setMapperClass(MapClass.class);
		conf.setReducerClass(ReduceClass.class);
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		JobClient.runJob(conf);
		return 0;
	}
}
