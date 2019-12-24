create external table mergedtable (id int,speed_mph double,travelTime_secs double,observationTime bigint,
month string,dayOfWeek string,hour int,latitudeStart double,longitudeStart double,latitudeEnd double,
longitudeEnd double,borough string,address string,windspeed double,visibility double,temperature double,pcp double,
speedLimit double, isSignShown string, differenceSpeedAndLimit double)
row format delimited fields terminated by ','
location '/user/ry856/sharedData/FinalMergedData/speedlimit_weather_trafficspeed';


SELECT A.id, A.address, cnt
    FROM (SELECT id, address, COUNT(differencespeedandlimit) as cnt
              FROM mergedtable
              GROUP BY id, address
    ) A
    GROUP BY A.id, A.address, cnt
    ORDER BY cnt DESC
    LIMIT 50;


SELECT id, borough, address, count(*) AS num_violations
    FROM mergedtable
    WHERE differencespeedandlimit > 15
    GROUP BY id, borough, address
    HAVING num_violations > 100000
    ORDER BY num_violations DESC
    LIMIT 10;

SELECT A.id, borough, address, A.dayofweek, A.cnt AS 'count_violations'
    FROM (SELECT id, borough, address, dayofweek, COUNT(differencespeedandlimit) cnt
              FROM mergedtable
              WHERE differencespeedandlimit > 15
              GROUP BY id, dayofweek, borough, address
    ) A
    GROUP BY A.cnt, A.id, A.dayofweek, A.borough, A.address
    HAVING A.cnt > 10000
    ORDER BY cnt DESC, dayofweek
    LIMIT 20;


SELECT A.id, A.borough, A.address, A.dayofweek, A.hour, cnt
    FROM (SELECT id, borough, address, dayofweek, hour, COUNT(differencespeedandlimit) cnt
              FROM mergedtable
              WHERE differencespeedandlimit > 15
              GROUP BY id, hour, dayofweek, borough, address
    ) A
    GROUP BY A.cnt, A.id, A.hour, A.dayofweek, A.borough, A.address
    ORDER BY cnt DESC, hour ASC
    LIMIT 40;
