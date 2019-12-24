package project.featureEngineering;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FeatureEngineeringReducer extends Reducer<Text, Text, Text, Text> {

    /***
     output file structure

     key = id
     columns=
     speed(mph)          // inputFields[0]
     traveTime(secs)     // inputFields[1]
     latitudeStart       // inputFields[3]
     longitudeStart      // inputFields[4]
     latitudeEnd         // inputFields[5]
     longitudeEnd        // inputFields[6]
     statenIsland        // 1 or 0
     brooklyn            // 1 or 0
     manhattan           // 1 or 0
     queens              // 1 or 0
     monday              // 1 or 0
     tuesday             // 1 or 0
     wednesday           // 1 or 0
     thursday            // 1 or 0
     friday              // 1 or 0
     saturday            // 1 or 0
     january             // 1 or 0
     february            // 1 or 0
     march               // 1 or 0
     april               // 1 or 0
     may                 // 1 or 0
     june                // 1 or 0
     july                // 1 or 0
     august              // 1 or 0
     september           // 1 or 0
     october             // 1 or 0
     november            // 1 or 0
     0_5                 // 1 or 0
     5_10                // 1 or 0
     10_15               // 1 or 0
     15_20               // 1 or 0

     Encoding Categorical Variables:

     borough has the fields 'Staten Island', 'Brooklyn', 'Manhattan', Queens' and 'Bronx'.
     Columns: statenIsland, brooklyn, manhattan, queens
     when all columns are set to 0, it means that the selected borough is 'Bronx'.

     Potential days of week are, 'Monday','Tuesday','Wednesday','Thursday','Friday','Saturday' and 'Sunday'.
     Columns: monday, tuesday, wednesday, thursday, friday, saturday
     when all columns are set to 0, it means that the selected day is 'Sunday'.

     Potential months: January, February, March, April, May, June, July, August, September, October, November and December.
     Columns: january, february, march, april, may, june, july, august, september, october, november.
     when all columns are set to 0, it means that the selected month is 'December'.

     Potential hour range during the day: 0-5, 5-10, 10-15, 15-20, 20-24
     Columns: 0_5, 5_10, 10_15, 15_20
     when all columns are set to 0, it means that the selected month is '20-24'.
     */

    private HashMap<String, Integer> boroughsMap = new HashMap<>();
    private String[] daysOfWeek = new String[7];
    private String[] hourRanges = new String[5];
    //{"0_5", "5_10", "10_15", "15_20", "20_24"}
    private String[] months = new String[12];

    private String[] boroughs = new String[5];

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        if (key.toString().contains("id")) { // this is to output the column name
                context.write(key, new Text(values.toString()));
        } else {
            boroughsMap.put("staten island", 0);
            boroughsMap.put("brooklyn", 1);
            boroughsMap.put("manhattan", 2);
            boroughsMap.put("queens", 3);
            boroughsMap.put("bronx", 4);


            for (Text value : values) {
                Arrays.fill(daysOfWeek, "0");
                Arrays.fill(hourRanges, "0");
                Arrays.fill(months, "0");
                Arrays.fill(boroughs, "0");
                String[] inputFields = value.toString().split(",");

                LocalDateTime obsTime = LocalDateTime.parse(inputFields[2]);

                int dayOfWeek = obsTime.getDayOfWeek().getValue();
                int hour = obsTime.getHour();
                int month = obsTime.getMonth().getValue();

                daysOfWeek[dayOfWeek - 1] = "1";
                if (hour> 0.0) {
                    hourRanges[(int) (Math.ceil(hour / 5.0) - 1)] = "1";
                } else {
                    hourRanges[0] = "1";
                }

                months[month - 1] = "1";
                int index = boroughsMap.get(inputFields[7].toLowerCase());
                boroughs[index] = "1";

                context.write(key, new Text(
                        inputFields[0] + "," + // speed(mph)
                                inputFields[1] + "," + // travelTime(secs)
                                inputFields[3] + "," + // latitudeStart
                                inputFields[4] + "," + // longitudeStart
                                inputFields[5] + "," + // latitudeEnd
                                inputFields[6] + "," + // longitudeEnd
                                boroughs[0] + "," + // statenIsland
                                boroughs[1] + "," + // brooklyn
                                boroughs[2] + "," + // manhattan
                                boroughs[3] + "," + // queens
                                daysOfWeek[0] + "," + // monday
                                daysOfWeek[1] + "," + // tuesday
                                daysOfWeek[2] + "," + // wednesday
                                daysOfWeek[3] + "," + // thursday
                                daysOfWeek[4] + "," + // friday
                                daysOfWeek[5] + "," + // saturday
                                months[0] + "," + // january
                                months[1] + "," + // february
                                months[2] + "," + // march
                                months[3] + "," + // april
                                months[4] + "," + // may
                                months[5] + "," + // june
                                months[6] + "," + // july
                                months[7] + "," + // august
                                months[8] + "," + // september
                                months[9] + "," + // october
                                months[10] + "," + // november
                                hourRanges[0] + "," + // 00:00-05:00
                                hourRanges[1] + "," + // 05:00-10:00
                                hourRanges[2] + "," + // 10:00-15:00
                                hourRanges[3]         // 15:00-20:00
                ));
            }
        }
    }
}
