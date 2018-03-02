package uploading;

import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class upload {
	
	
	public static class uploadMapper extends Mapper<Object, Text, Text, IntWritable>{
		
		private final static MySqlHelper mh = new MySqlHelper();
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
//		String delim = "\1";
//		String[] num = new String[3];
		String ip;
		@Override
		protected void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			int i = 0;
			while(itr.hasMoreTokens()){
				word.set(itr.nextToken());
				ip = word.toString();
//				System.out.println(num[i]);
				i++;
				context.write(word, one);
			}
			//API_num[]
			mh.Insertrecord(ip);
		}
	}
	
	
	
	public static class uploadReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		private IntWritable result = new IntWritable();
		
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Context context)
				throws IOException, InterruptedException {
			int sum  = 0;
			for(IntWritable i:values){
				sum += i.get();
			}
			result.set(sum);
			context.write(key,result);//Context鏈哄埗
		}

		
		
	}
	
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		System.out.println(Arrays.toString(otherArgs));
		if(otherArgs.length != 2){
			System.exit(2);
		}

		Job job = new Job(conf,"upload");
		job.setJarByClass(upload.class);
		job.setMapperClass(uploadMapper.class);
		job.setCombinerClass(uploadReducer.class);
		job.setReducerClass(uploadReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		
		System.exit(job.waitForCompletion(true)?0:1);
		uploadMapper.mh.Close();
	}
	
}
