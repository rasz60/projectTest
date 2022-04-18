/* notice_board table */
CREATE TABLE notice_board (
 bid NUMBER(10) PRIMARY KEY, 
 bName VARCHAR2(200 BYTE) NOT NULL,
 bTitle VARCHAR2(50 BYTE) NOT NULL,
 bContent VARCHAR2(2000 BYTE) NOT NULL,
 bDate DATE DEFAULT SYSDATE NOT NULL,
 bHit NUMBER(38) DEFAULT 0 NOT NULL
);

CREATE SEQUENCE notice_board_seq NOCACHE;