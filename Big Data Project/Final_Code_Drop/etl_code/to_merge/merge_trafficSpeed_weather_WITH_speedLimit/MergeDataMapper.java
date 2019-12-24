package project.mergeData;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MergeDataMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] line = value.toString().split(",");

        context.write(new Text(line[0]), new Text(line[1] + "," + line[2] + "," + line[3] + "," + line[4] + "," + line[5] + "," +
                line[6] + "," + line[7] + "," + line[8] + "," + line[9] + "," + line[10] + "," + line[11] + "," + line[12] + "," + line[13]));
    }
}

