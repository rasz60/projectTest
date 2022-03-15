DROP TABLE test_calendar;

CREATE TABLE test_calendar (
    planNum NUMBER(38) NOT NULL PRIMARY KEY,
    planName VARCHAR2(200) NOT NULL,
    startDate VARCHAR2(100) NOT NULL,
    endDate VARCHAR2(100) NOT NULL
);

CREATE SEQUENCE planNum_seq 
                INCREMENT BY 1
                START WITH 1
                MINVALUE 1
                MAXVALUE 9999
                NOCYCLE
                NOCACHE
                NOORDER;

INSERT INTO test_calendar (plannum, planName, startDate, endDate) VALUES (plannum_seq.NEXTVAL,'test_plan1', '2022-01-02', '2022-01-07');
INSERT INTO test_calendar (plannum, planName, startDate, endDate) VALUES (plannum_seq.NEXTVAL,'test_plan2', '2022-03-08', '2022-03-11');
INSERT INTO test_calendar (plannum, planName, startDate, endDate) VALUES (plannum_seq.NEXTVAL,'test_plan3', '2022-03-12', '2022-03-13');
INSERT INTO test_calendar (plannum, planName, startDate, endDate) VALUES (plannum_seq.NEXTVAL,'test_plan4', '2022-04-14', '2022-04-17');
INSERT INTO test_calendar (plannum, planName, startDate, endDate) VALUES (plannum_seq.NEXTVAL,'test_plan5', '2022-05-03', '2022-05-06');
INSERT INTO test_calendar (plannum, planName, startDate, endDate) VALUES (plannum_seq.NEXTVAL,'test_plan6', '2022-02-01', '2022-02-05');
INSERT INTO test_calendar (plannum, planName, startDate, endDate) VALUES (plannum_seq.NEXTVAL,'test_plan7', '2022-03-16', '2022-03-17');

commit;

select * from test_calendar;