import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class MapReduceApp {

    public static class MyReducer extends Reducer<IntWritable, IntWritable, IntWritable, NullWritable> {

        @Override
        protected void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable i : values) {
                sum += i.get();
            }
            context.write(new IntWritable(sum), NullWritable.get());
        }

        public static class MyMapper
                extends Mapper<Object, Text, IntWritable, NullWritable> {

            private Text word = new Text();

            public int counter;

            protected void setup(org.apache.hadoop.mapreduce.Mapper.Context context) {
                counter = 0;
            }

            public void map(Object key, Text value, Context context) {
                try {
                    if (value.toString().equals("")) {
                        return;
                    }
                    counter++;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    throw e;
                }
            }

            protected void cleanup(org.apache.hadoop.mapreduce.Mapper.Context context)
                    throws IOException,
                    InterruptedException {
                context.write(new IntWritable(1), new IntWritable(counter));
            }
        }

        public static void main(String[] args) throws Exception {
            Configuration conf = new Configuration();
            Path outputDir = new Path("output");
            File outputF = new File(outputDir.getName());
            if (outputF.exists()) {
                FileUtils.forceDelete(outputF); //delete directory
            }
            Job job = Job.getInstance(conf, "word count");
            job.setJarByClass(MapReduceApp.class);
            job.setMapperClass(MyMapper.class);
            job.setReducerClass(MyReducer.class);
            job.setNumReduceTasks(1);
            job.setMapOutputKeyClass(IntWritable.class);
            job.setMapOutputValueClass(IntWritable.class);
            job.setOutputKeyClass(IntWritable.class);
            job.setOutputValueClass(NullWritable.class);
            FileInputFormat.addInputPath(job, new Path("input"));
            FileOutputFormat.setOutputPath(job, outputDir);
            boolean success = job.waitForCompletion(true);
            System.exit(success ? 0 : 1);
        }
    }
}
