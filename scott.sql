create table plan_test(
    planId NUMBER(38) NOT NULL PRIMARY KEY,
    planName VARCHAR2(200) NOT NULL,
    startDate VARCHAR2(100) NOT NULL,
    endDate VARCHAR2(100) NOT NULL,
    theme VARCHAR2(100) NOT NULL,
    latitude varchar2(20) not null, 
    longitude varchar2(20) not null, 
    placeName varchar2(50) not null, 
    placecount varchar2(50) not null, 
    category varchar2(100) not null, 
    address varchar2(50) not null
);

create SEQUENCE plan_test_seq nocache;

commit;