package project.cleanData;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CleanDataMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] line = value.toString().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        if (!line[0].equals("ID")) {
            if (!line[3].equals("-101")) {
                context.write(new Text(line[0]),new Text(line[1]+","+line[2]+","+line[4]+","+
                        line[6]+","+line[11].toLowerCase()+","+line[12]));
            }
        }
        else {
            context.write(new Text("id"), new Text("speed(mph),travelTime(secs),observationTime(GMT)," +
                    "latitudeStart,longitudeStart,latitudeEnd,longitudeEnd,borough,address"));
        }
    }
}
