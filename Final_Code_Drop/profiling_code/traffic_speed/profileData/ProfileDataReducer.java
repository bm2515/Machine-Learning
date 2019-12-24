package project.profileData;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProfileDataReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        switch (key.toString()) {
            case "speed(mph)":
            case "travelTime(secs)":
                float maxCalc = (float)-99999.0;
                float minCalc = (float)99999.0;

                for (Text value : values) {
                    Float val = Float.parseFloat(value.toString());
                    if (maxCalc < val) {
                        maxCalc = val;
                    }
                    if (minCalc > val) {
                        minCalc = val;
                    }
                }
                context.write(key, new Text("min: " + minCalc + " max: " + maxCalc));
                break;

            case "borough":
            case "address":
                int maxLength = -99999;
                int minLength = 99999;

                for (Text value : values) {
                    int val = value.toString().length();
                    if (maxLength < val) {
                        maxLength = val;
                    }
                    if (minLength > val) {
                        minLength = val;
                    }
                }
                context.write(key, new Text("min-length: " + minLength + " max-length: " + maxLength));
                break;

            case "observationTime(GMT)":
                LocalDateTime minDateTime = LocalDateTime.MAX;
                LocalDateTime maxDateTime = LocalDateTime.MIN;

                for (Text value : values) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
                    LocalDateTime val = LocalDateTime.parse(value.toString(), formatter);
                    if (maxDateTime.isBefore(val)) {
                        maxDateTime = val;
                    }
                    if (minDateTime.isAfter(val)) {
                        minDateTime = val;
                    }
                }
                context.write(key, new Text("min-date: " + minDateTime + " max-date: " + maxDateTime));
                break;

            case "latitudeStart":
            case "latitudeEnd":
            case "longitudeStart":
            case "longitudeEnd":
                float maxCoordinate = (float)-99999.0;
                float minCoordinate = (float)99999.0;
                for (Text value : values) {
                    float val = Float.parseFloat(value.toString());
                    if (maxCoordinate < val) {
                        maxCoordinate = val;
                    }
                    if (minCoordinate > val) {
                        minCoordinate = val;
                    }
                }
                context.write(key, new Text("min-coordinate: " + minCoordinate + " max-coordinate: " + maxCoordinate));
                break;
            default:
        }
    }
}