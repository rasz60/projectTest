CREATE TABLE chatRoom(
 roomNum    number(10),
 roomId     varchar2(200) primary key,
 pubId      varchar2(50) not null,
 subId      varchar2(50) not null,
 pubImg     varchar2(300),
 subImg     varchar2(300),
 pubNick    varchar2(70),
 subNick    varchar2(70) 
);

CREATE SEQUENCE CHAT_ROOM_SEQ NOCACHE; --roomNum ��ȣ ����

-------------userinfo ���� -------------
alter table userinfo
 modify userNick varchar2(70);

alter table userinfo
 modify userProfileMsg varchar2(900); --�ѱ� 300�ڱ��� �Էµ�
-----------------------------------------
 
alter table chatRoom
 add constraint chatRoom_nick_fk foreign key(pubnick) references userinfo(usernick)ON DELETE CASCADE; --ä�ø���Ʈ�� �������� �г����� userinfo�� �г����� �����ϸ� ���� Ż��� ä�ø���Ʈ���� �ش� ä�÷� �����

alter table chatRoom
 add constraint chatRoom_nick2_fk foreign key(subnick) references userinfo(usernick) ON DELETE CASCADE; --ä�ø���Ʈ�� �������� �г����� userinfo�� �г����� �����ϸ� ���� Ż��� ä�ø���Ʈ���� �ش� ä�÷� �����

-----------ä�ø���Ʈ�� �������� �г����� userinfo�� �г����� �����ϸ� ���� �г��� ������Ʈ�� ä�ø���Ʈ���� �ش� �г��� �ڵ� ������Ʈ------------
--Ʈ���� �ΰ� �ּ� Ǯ�� ���� �巡���ؼ� ctrl+enter �� 
/*create or replace TRIGGER chatRoom_nick_update 
 after UPDATE OF usernick ON userinfo FOR EACH ROW 
 BEGIN UPDATE chatroom 
 SET pubnick = :NEW.usernick 
 WHERE pubnick = :OLD.usernick;
end;*/

/*create or replace TRIGGER chatRoom_nick2_update 
 after UPDATE OF usernick ON userinfo FOR EACH ROW 
 BEGIN UPDATE chatroom 
 SET subnick = :NEW.usernick 
 WHERE subnick = :OLD.usernick;
end;*/
---------------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE chatMessage(
 m_roomId     varchar2(200) not null,
 m_pubId      varchar2(50) not null,
 m_pubNick    varchar2(70) not null,
 m_subId      varchar2(50) not null,
 m_subNick    varchar2(70) not null,
 m_sendTime   varchar2(70) default null,
 m_pubImg     varchar2(300),
 m_subImg     varchar2(300),
 m_num        number(20) primary key,
 m_sendId     varchar2(50),
 m_pubMsg     varchar2(2000),
 m_subMsg     varchar2(2000) 
);
 
CREATE SEQUENCE CHAT_MSG_SEQ NOCACHE; --m_num ��ȣ ����

ALTER TABLE chatmessage
ADD CONSTRAINT msg_cascade_chat
  FOREIGN KEY (m_roomId)
  REFERENCES chatRoom(roomId)
  ON DELETE CASCADE; --ä�ù� ������(�Ź����� ������ ��� ����) chatmessage DB�� �ش� �� �޼��� ����� �� ����