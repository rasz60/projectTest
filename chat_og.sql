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

CREATE SEQUENCE CHAT_ROOM_SEQ NOCACHE; --roomNum 번호 증가

-------------userinfo 수정 -------------
alter table userinfo
 modify userNick varchar2(70);

alter table userinfo
 modify userProfileMsg varchar2(900); --한글 300자까지 입력됨
-----------------------------------------
 
alter table chatRoom
 add constraint chatRoom_nick_fk foreign key(pubnick) references userinfo(usernick)ON DELETE CASCADE; --채팅리스트에 보여지는 닉네임이 userinfo의 닉네임을 참조하며 유저 탈퇴시 채팅리스트에서 해당 채팅룸 사라짐

alter table chatRoom
 add constraint chatRoom_nick2_fk foreign key(subnick) references userinfo(usernick) ON DELETE CASCADE; --채팅리스트에 보여지는 닉네임이 userinfo의 닉네임을 참조하며 유저 탈퇴시 채팅리스트에서 해당 채팅룸 사라짐

-----------채팅리스트에 보여지는 닉네임이 userinfo의 닉네임을 참조하며 유저 닉네임 업데이트시 채팅리스트에서 해당 닉네임 자동 업데이트------------
--트리거 두개 주석 풀고 각각 드래그해서 ctrl+enter 해 
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
 
CREATE SEQUENCE CHAT_MSG_SEQ NOCACHE; --m_num 번호 증가

ALTER TABLE chatmessage
ADD CONSTRAINT msg_cascade_chat
  FOREIGN KEY (m_roomId)
  REFERENCES chatRoom(roomId)
  ON DELETE CASCADE; --채팅방 나가면(신버전에 나가기 기능 포함) chatmessage DB의 해당 방 메세지 내용들 다 삭제