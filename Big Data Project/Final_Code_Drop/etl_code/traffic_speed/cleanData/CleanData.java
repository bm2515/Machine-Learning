package project.cleanData;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import static org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader.KEY_VALUE_SEPERATOR;

public class CleanData {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: Find Range <input path> <output path>");
            System.exit(-1);
        }
        Job job = new Job();
        final Configuration conf = job.getConfiguration();
        conf.set("mapred.textoutputformat.separator", ",");
        job.setJarByClass(CleanData.class);
        job.setJobName("Clean Data");
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.setMapperClass(CleanDataMapper.class);
        job.setReducerClass(CleanDataReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}