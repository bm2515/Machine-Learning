create external table traffic_volume (id string, Segment_ID string, Road string, From_Ad string, To_Ad string, Direction string, Date string, 12_1AM int,1_2AM int, 2_3AM int, 3_4AM int, 4_5AM int, 5_6AM int, 6_7AM int, 7_8AM int, 8_9AM int, 9_10AM int, 10_11AM int, 11_12PM int, 12_1PM int, 1_2PM int, 2_3PM int, 3_4PM int, 4_5PM int, 5_6PM int, 6_7PM int, 7_8PM int, 8_9PM int, 9_10PM int, 10_11PM int, 11_12AM int) row format delimited fields terminated by ',' stored as textfile location '/user/yy1420/test/';

ALTER TABLE traffic_volume SET TBLPROPERTIES ("skip.header.line.count"="1");

select * from traffic_volume where 12_1am is null or 1_2am is null or 2_3am is null or 3_4am is null or 4_5am is null or 5_6am is null or 6_7am is null or 7_8am is null or 8_9am is null or 9_10am is null or 10_11am is null or 11_12pm is null or 12_1pm is null or 1_2pm is null or 2_3pm is null or 3_4pm is null or 4_5pm is null or 5_6pm is null or 6_7pm is null or 7_8pm is null or 8_9pm is null or 9_10pm is null or 10_11pm is nll pr 11_12am is null;

create table new_traffic_volume as select id, Segment_ID, Road, From_Ad, To_Ad, Direction, Date, 12_1AM,1_2AM, 2_3AM, 3_4AM, 4_5AM, 5_6AM, 6_7AM, 7_8AM, 8_9AM, 9_10AM, 10_11AM, 11_12PM, 12_1PM, 1_2PM, 2_3PM, 3_4PM, 4_5PM, 5_6PM, 6_7PM, 7_8PM, 8_9PM, 9_10PM, 10_11PM, 11_12AM, date_format(from_unixtime(unix_timestamp(Date,'MM/dd/yyyy')),'u') as Day from traffic_volume;

select * from new_traffic_volume;

create table volume_by_date as select sum(12_1AM) as 12_1AM ,sum(1_2AM) as 1_2AM, sum(2_3AM) as 2_3AM, sum(3_4AM) as 3_4AM, sum(4_5AM) as 4_5AM, sum(5_6AM) as 5_6AM , sum(6_7AM) as 6_7AM , sum(7_8AM) as 7_8AM, sum(8_9AM) as 8_9AM, sum(9_10AM) as 9_10AM, sum(10_11AM) as 10_11AM, sum(11_12PM) as 11_12PM, sum(12_1PM) as 12_1PM, sum(1_2PM) as 1_2PM, sum(2_3PM) as 2_3PM, sum(3_4PM) as 3_4PM, sum(4_5PM) as 4_5PM, sum(5_6PM) as 5_6PM, sum(6_7PM) as 6_7PM, sum(7_8PM) as 7_8PM, sum(8_9PM) as 8_9PM, sum(9_10PM) as 9_10PM, sum(10_11PM) as 10_11PM, sum(11_12AM) as 11_12AM,min(Day) as Day from new_traffic_volume Group by Date;

select * from volume_by_date;

create table volume_by_day as select AVG(12_1AM) as 12_1AM, AVG(1_2AM) as 1_2AM, AVG(2_3AM) as 2_3AM, AVG(3_4AM) as 3_4AM, AVG(4_5AM) as 4_5AM, AVG(5_6AM) as 5_6AM, AVG(6_7AM) as 6_7AM, AVG(7_8AM) as 7_8AM, AVG(8_9AM) as 8_9AM, AVG(9_10AM) as 9_10AM, AVG(10_11AM) as 10_11AM , AVG(11_12PM) as 11_12PM, AVG(12_1PM) as 12_1PM, AVG(1_2PM) as 1_2PM, AVG(2_3PM) as 2_3PM, AVG(3_4PM) as 3_4PM, AVG(4_5PM) as 4_5PM, AVG(5_6PM) as 5_6PM, AVG(6_7PM) as 6_7PM, AVG(7_8PM) as 7_8PM, AVG(8_9PM) as 8_9PM, AVG(9_10PM) as 9_10PM, AVG(10_11PM) as 10_11PM, AVG(11_12AM) as 11_12AM from volume_by_date Group by Day;

select * from volume_by_day;

create table volume_by_street as select Upper(Road) as Road, Direction,AVG(12_1AM) as 12_1AM, AVG(1_2AM) as 1_2AM, AVG(2_3AM) as 2_3AM, AVG(3_4AM) as 3_4AM, AVG(4_5AM) as 4_5AM, AVG(5_6AM) as 5_6AM, AVG(6_7AM) as 6_7AM, AVG(7_8AM) as 7_8AM, AVG(8_9AM) as 8_9AM, AVG(9_10AM) as 9_10AM, AVG(10_11AM) as 10_11AM , AVG(11_12PM) as 11_12PM, AVG(12_1PM) as 12_1PM, AVG(1_2PM) as 1_2PM, AVG(2_3PM) as 2_3PM, AVG(3_4PM) as 3_4PM, AVG(4_5PM) as 4_5PM, AVG(5_6PM) as 5_6PM, AVG(6_7PM) as 6_7PM, AVG(7_8PM) as 7_8PM, AVG(8_9PM) as 8_9PM, AVG(9_10PM) as 9_10PM, AVG(10_11PM) as 10_11PM, AVG(11_12AM) as 11_12AM from traffic_volume Group by Upper(Road), Direction;

select * from volume_by_street;

create table volume_by_street_total as select Upper(Road) as Road, Direction,AVG(12_1AM)+ AVG(1_2AM)+AVG(2_3AM)+AVG(3_4AM)+AVG(4_5AM) + AVG(5_6AM) + AVG(6_7AM) + AVG(7_8AM) + AVG(8_9AM) + AVG(9_10AM) + AVG(10_11AM) + AVG(11_12PM) + AVG(12_1PM) + AVG(1_2PM) + AVG(2_3PM) + AVG(3_4PM) + AVG(4_5PM) + AVG(5_6PM) + AVG(6_7PM) + AVG(7_8PM) + AVG(8_9PM) + AVG(9_10PM) + AVG(10_11PM)+AVG(11_12AM) as Total from traffic_volume Group by Upper(Road), Direction;

select * from volume_by_street_total order by total DESC;



