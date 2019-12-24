package project.profileData;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class ProfileDataMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] line = value.toString().split(",");
        if (!line[2].equals("travelTime(secs)")) {
            context.write(new Text("speed(mph)"), new Text(line[1]));
            context.write(new Text("travelTime(secs)"), new Text(line[2]));
            context.write(new Text("observationTime(GMT)"), new Text(line[3]));
            context.write(new Text("latitudeStart"), new Text(line[4]));
            context.write(new Text("longitudeStart"), new Text(line[5]));
            context.write(new Text("latitudeEnd"), new Text(line[6]));
            context.write(new Text("longitudeEnd"), new Text(line[7]));
            context.write(new Text("borough"), new Text(line[8]));
            context.write(new Text("address"), new Text(line[9]));
        }
    }
}