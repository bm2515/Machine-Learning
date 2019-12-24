package project.mergeData;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class MergeDataReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int i = 0;
        float differenceFromSpeedLimit = 0;
        float speedLimit = 0;
        String signShown = "";

        Path pt = new Path("hdfs://dumbo/user/ry856/ssharedData/dataToMerge/speedlimit/output.csvv");//Location of file in HDFS
        FileSystem fs = FileSystem.get(new Configuration());
        BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
        String line;
        line = br.readLine();

        for (Text value : values) {
            String[] inputFields = value.toString().split(",");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

            LocalDateTime date = LocalDateTime.parse(inputFields[2], formatter);
            Month month = date.getMonth();
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            int hour = date.getHour();

            float latStart = Float.parseFloat(inputFields[3]);
            float longStart = Float.parseFloat(inputFields[4]);
            float latEnd = Float.parseFloat(inputFields[5]);
            float longEnd = Float.parseFloat(inputFields[6]);

            float speedLimitLatStart = 0;
            float speedLimitLongStart = 0;
            float speedLimitLatEnd = 0;
            float speedLimitLongEnd = 0;

            while (line != null && i != 1) {
                String[] speedLimitLine = line.split(",");
                speedLimitLatStart = Float.parseFloat(speedLimitLine[2]);
                speedLimitLongStart = Float.parseFloat(speedLimitLine[1]);
                speedLimitLatEnd = Float.parseFloat(speedLimitLine[4]);
                speedLimitLongEnd = Float.parseFloat(speedLimitLine[3]);
                if (((Math.abs(latStart - speedLimitLatStart) < 0.0005 && Math.abs(longStart - speedLimitLongStart) < 0.0005) ||
                        (Math.abs(latEnd - speedLimitLatStart) < 0.0005 && Math.abs(longEnd - speedLimitLongStart) < 0.0005)) ||
                        ((Math.abs(latStart - speedLimitLatEnd) < 0.0005 && Math.abs(longStart - speedLimitLongEnd) < 0.0005) ||
                                (Math.abs(latEnd - speedLimitLatEnd) < 0.0005 && Math.abs(longEnd - speedLimitLongEnd) < 0.0005))) {
                    speedLimit = Float.parseFloat(speedLimitLine[0]);
                    if (speedLimit == 0)
                        speedLimit = 25;
                    signShown = speedLimitLine[6];
                    i = 1;
                }
                line = br.readLine();
            }

            if (i == 1) {
                differenceFromSpeedLimit = Float.parseFloat(inputFields[0]) - speedLimit;
                context.write(key, new Text(inputFields[0] + "," + inputFields[1] + "," + inputFields[2] + "," +
                        month + "," + dayOfWeek + "," + hour + "," +
                        inputFields[3] + "," + inputFields[4] + "," + inputFields[5] + "," + inputFields[6] + "," + inputFields[7]
                        + "," + inputFields[8] + "," + inputFields[9] + "," + inputFields[10] + "," + inputFields[11] + "," +
                        inputFields[12] + "," + speedLimit + "," + signShown + "," + differenceFromSpeedLimit));
            }
        }
        br.close();
    }
}