package project.featureEngineering;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FeatureEngineeringMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] line = value.toString().split(",");

        if (!line[2].equals("travelTime(secs)")) {
            context.write(
                    new Text(line[0]),
                    new Text(
                            line[1] + "," + line[2] + "," + line[3] + "," +
                            line[4] + "," + line[5] + "," + line[6] + "," +
                            line[7] + "," + line[8] + "," + line[9]));
        } else {
            context.write(new Text("id"), new Text(
                    "speed(mph),travelTime(secs),observationTime(GMT)," +
                    "latitudeStart,longitudeStart,latitudeEnd,longitudeEnd," +
                    "statenIsland,brooklyn,manhattan,queens," +
                    "monday,tuesday,wednesday,thursday,friday,saturday," +
                    "january,february,march,april,may,june,july,august,september,october,november." +
                    "0_5,5_10,10_15,15_20"));
        }
    }
}
