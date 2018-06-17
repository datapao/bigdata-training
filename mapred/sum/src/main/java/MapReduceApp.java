import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;

public class MapReduceApp {

    public static class MyMapper
            extends Mapper<Object, Text, Text, IntWritable>{

        private Text word = new Text();

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            try {
                if (value.toString().equals("")){
                    return;
                }
                String[] vals = value.toString().split(" ");
                String name = vals[0];
                int amount = Integer.parseInt(vals[1]);

                word.set(name);
                context.write(word, new IntWritable(amount));
            } catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
    }

    public static class MyReducer
            extends Reducer<Text,IntWritable,Text,IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context
        ) throws IOException, InterruptedException {
            try {
                int sum = 0;
                for (IntWritable val : values) {
                    sum += val.get();
                }
                result.set(sum);
                context.write(key, result);
            } catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Path outputDir = new Path("output");
        File outputF = new File(outputDir.getName());
        if (outputF.exists()) {
            FileUtils.forceDelete(outputF); //delete directory
        }
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(MapReduceApp.class);
        job.setMapperClass(MyMapper.class);
        job.setNumReduceTasks(4);
        job.setReducerClass(MyReducer.class);
        //job.setCombinerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable .class);
        FileInputFormat.addInputPath(job, new Path("input"));
        FileOutputFormat.setOutputPath(job, outputDir);
        boolean success = job.waitForCompletion(true);
        System.exit(success ? 0 : 1);
    }
}
