select violation_count.issignshown, violation_count.violations/all_count.all as violation_rate from (select issignshown, count(differencespeedandlimit) as violations from mergedtable where differencespeedandlimit > 15 group by issignshown) violation_count join (select issignshown, count(issignshown) as all from mergedtable group by issignshown) all_count on violation_count.issignshown = all_count.issignshown;

select violation_count.windspeed as windspeed, violation_count.violations/all_count.all as violation_rate from (select windspeed, count(differencespeedandlimit) as violations from mergedtable where differencespeedandlimit > 15 group by windspeed) violation_count join (select windspeed, count(windspeed) as all from mergedtable group by windspeed) all_count on violation_count.windspeed = all_count.windspeed order by violation_rate;

select corr(windspeed,differencespeedandlimit) from mergedtable;

select violation_count.pcp as pcp, violation_count.violations/all_count.all as violation_rate from (select pcp, count(differencespeedandlimit) as violations from mergedtable where differencespeedandlimit > 15 group by pcp) violation_count join (select pcp, count(pcp) as all from mergedtable group by pcp) all_count on violation_count.pcp = all_count.pcp order by pcp limit 20;

select corr(pcp,differencespeedandlimit) from mergedtable;

select violation_count.visibility as visibility, violation_count.violations/all_count.all as violation_rate from (select visibility, count(differencespeedandlimit) as violations from mergedtable where differencespeedandlimit > 15 group by visibility) violation_count join (select visibility, count(visibility) as all from mergedtable group by visibility) all_count on violation_count.visibility = all_count.visibility order by visibility limit 20;

select corr(visibility,differencespeedandlimit) from mergedtable;

select violation_count.temperature as temperature, violation_count.violations/all_count.all as violation_rate from (select temperature, count(differencespeedandlimit) as violations from mergedtable where differencespeedandlimit > 15 group by temperature) violation_count join (select temperature, count(temperature) as all from mergedtable group by temperature) all_count on violation_count.temperature = all_count.temperature order by temperature;

select corr(temperature,differencespeedandlimit) from mergedtable;