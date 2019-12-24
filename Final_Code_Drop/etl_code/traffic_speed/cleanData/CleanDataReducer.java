package project.cleanData;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CleanDataReducer extends Reducer<Text, Text, Text, Text> {

    private float[] nycLatRange = {(float)40.495992, (float)40.915568};
    private float[] nycLogRange = {(float)-74.257159, (float)-73.699215};


    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        if (key.toString().equals("id")) { // this is to output the column name
            for (Text value : values) {
                context.write(key, new Text(value.toString()));
            }
        } else {
            for (Text value : values) {

                // columns: speed, travelTime, observationTime,linkPoint,borough, address
                // this regex is used to split on comma unless the comma is inside a pair of quotes.
                // need to keep all location coordinates as one field
                String[] inputFields = value.toString().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                //change date format to LocalDateTime
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:m:s a");
                inputFields[2] = LocalDateTime.parse(inputFields[2], formatter).format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

                // grab all lat/long pairs from input
                String[] allPairs = inputFields[3].replace("\"", "").split(" ");
                float latStart =  Float.parseFloat(allPairs[0].split(",")[0]);
                float longStart =  Float.parseFloat(allPairs[0].split(",")[1]);

                // find the last good coordinate and set it as the end of the sensor
                String lastGoodPair = allPairs[0];
                for (String pair : allPairs) {
                    if (pair.contains(",")) {
                        try {
                            float latitude = Float.parseFloat(pair.split(",")[0]);
                            float longitude = Float.parseFloat(pair.split(",")[1]);

                            //ensure the coordinate doesn't have any missing digits and it's within the NYC coordinates
                            if ((latitude >= nycLatRange[0] && latitude <= nycLatRange[1]) &&
                                    (longitude >= nycLogRange[0] && longitude <= nycLogRange[1])) {
                                lastGoodPair = pair;
                            }
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                }
                float latEnd = Float.parseFloat(lastGoodPair.split(",")[0]);
                float longEnd = Float.parseFloat(lastGoodPair.split(",")[1]);

                // speed(mph),travelTime(secs),observationTime(GMT),latitudeStart,
                // longitudeStart,latitudeEnd,longitudeEnd,borough,address
                context.write(key, new Text(inputFields[0] + "," + inputFields[1] + "," + inputFields[2] + "," +
                        latStart + "," + longStart + "," + latEnd + "," + longEnd + "," + inputFields[4] + "," + inputFields[5]));
            }
        }
    }
}



