create external table trafficspeed (id int, speed double, travelTime double, observationTime bigint, latitudeStart double,
longitudeStart double, latitudeEnd double, longitudeEnd double, borough string, address string)
row format delimited fields terminated by ','
location '/user/ry856/sharedData/TrafficSpeed/outputCleanDataNew/';


select AVG(speed) as `average_speed`, borough from project GROUP BY borough ORDER BY borough;


SELECT A.borough AS `borough`, A.observationyear AS `observation_year`, AVG(s) AS `average_speed` FROM
(select borough, date_format(observationtime, 'yyyy') observationyear, avg(speed) s from project GROUP BY borough, date_format(observationtime, 'yyyy')) A
GROUP BY A.borough, A.observationyear ORDER BY A.borough, observationyear;
