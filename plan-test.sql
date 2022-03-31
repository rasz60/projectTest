CREATE TABLE plan_dt(
    planDtNum NUMBER(38) NOT NULL PRIMARY KEY,
    planNum NUMBER(38) NOT NULL,
    placeName VARCHAR2(50), 
	placeCount VARCHAR2(50), 
    planDay VARCHAR2(100) NOT NULL,
    planDate VARCHAR2(100) NOT NULL,
    startTime  VARCHAR2(100),
    endTime  VARCHAR2(100),
    theme VARCHAR2(100),
    latitude VARCHAR2(20), 
    longitude VARCHAR2(20), 
    address VARCHAR2(50),
    category VARCHAR2(100), 
    transportation VARCHAR2(100),
    details VARCHAR2(1000)
);
create SEQUENCE plan_dt_seq nocache;

CREATE TABLE plan_mst(
    plannum NUMBER(38) NOT NULL PRIMARY KEY,
    planName VARCHAR2(200) NOT NULL,
    startDate VARCHAR2(100) NOT NULL,
    endDate VARCHAR2(100) NOT NULL,
    dateCount VARCHAR2(100) NOT NULL,
    eventColor VARCHAR2(100) NOT NULL
);
CREATE SEQUENCE plan_mst_seq nocache;

SELECT * FROM plan_dt;
SELECT * FROM plan_mst;

DROP TABLE plan_mst;
DROP SEQUENCE plan_mst_seq;

DROP TABLE plan_dt;
DROP SEQUENCE plan_dt_seq;
commit;