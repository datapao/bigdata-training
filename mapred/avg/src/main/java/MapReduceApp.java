import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class MapReduceApp {

    public static class MyMapper
            extends Mapper<Object, Text, Text, Text>{

        private Text word = new Text();

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            try {
                if (value.toString().equals("")){
                    return;
                }
                String[] vals = value.toString().split(" ");
                String name = vals[0];
                String amountStr = vals[1] + "_" + 1;

                word.set(name);

                context.write(word, new Text(amountStr));
            } catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
    }

    public static class MyReducer
            extends Reducer<Text,Text,Text,FloatWritable> {

        public void reduce(Text key, Iterable<Text> values,
                           Context context
        ) throws IOException, InterruptedException {
            try {
                int sum = 0;
                int cnt = 0;
                for (Text val : values) {
                    String[] V = val.toString().split("_");
                    sum += Integer.parseInt(V[0]);
                    cnt += Integer.parseInt(V[1]);
                }
                context.write(key, new FloatWritable(sum/(float)cnt));
            } catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
    }


    public static class MyCombiner
            extends Reducer<Text,Text,Text,Text> {

        public void reduce(Text key, Iterable<Text> values,
                           Context context
        ) throws IOException, InterruptedException {
            try {
                int sum = 0;
                int cnt = 0;
                for (Text val : values) {
                    String[] V = val.toString().split("_");
                    sum += Integer.parseInt(V[0]);
                    cnt += Integer.parseInt(V[1]);
                }
                context.write(key, new Text(sum + "_" + cnt));
            } catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(MapReduceApp.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);
        job.setCombinerClass(MyCombiner.class);
        job.setOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputValueClass(FloatWritable.class);
        FileInputFormat.addInputPath(job, new Path("input"));
        FileOutputFormat.setOutputPath(job, new Path("output"));
        boolean success = job.waitForCompletion(true);
        System.exit(success ? 0 : 1);
    }
}
