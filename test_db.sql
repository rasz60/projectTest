CREATE TABLE test_calendar (
    planName VARCHAR2(200) NOT NULL,
    startDate VARCHAR2(100) NOT NULL,
    endDate VARCHAR2(100) NOT NULL
);

INSERT INTO test_calendar (planName, startDate, endDate) VALUES ('test_plan1', '2022-01-02', '2022-01-07');
INSERT INTO test_calendar (planName, startDate, endDate) VALUES ('test_plan2', '2022-03-08', '2022-03-11');
INSERT INTO test_calendar (planName, startDate, endDate) VALUES ('test_plan3', '2022-03-12T07:00:00+09:00', '2022-03-12T07:30:00+09:00');
INSERT INTO test_calendar (planName, startDate, endDate) VALUES ('test_plan4', '2022-04-14', '2022-04-17');
INSERT INTO test_calendar (planName, startDate, endDate) VALUES ('test_plan5', '2022-05-03', '2022-05-06');
INSERT INTO test_calendar (planName, startDate, endDate) VALUES ('test_plan6', '2022-02-01', '2022-02-05');
INSERT INTO test_calendar (planName, startDate, endDate) VALUES ('test_plan7', '2022-03-16T18:00:00+09:00', '2022-03-16T23:00:00+09:00');