create table wuser (
id varchar2(100) primary key,
pwd varchar2(100) not null,
name varchar2(20) not null,
birth varchar2(20) not null,
mail varchar2(100) not null,
phone varchar2(20) not null,
nick varchar2(50) unique not null,
rdate date not null,
profile varchar2(100) default 'default_profile.png' ,
greet varchar2(1000) default 'æ»≥Á«œººø‰'
);

create table message(
  no number(4) primary key,
  room number(4) not null,
  send_nick varchar2(50) not null,
  recv_nick varchar2(50) not null,
  send_time date not null,
  read_time date not null,
  content varchar2(1000) not null,
  read_chk number(1) not null,
  constraint m_send_nick_fk foreign key(send_nick) references userdb(pid)
  on delete cascade,
  constraint m_recv_nick_fk foreign key(recv_nick) references userdb(pid)
  on delete cascade
);

CREATE SEQUENCE message_seq NOCACHE;

insert into message values(message_seq.nextval,2,'jim@gmail.com','jmdh1004@gmail.com',sysdate,sysdate,'µ≈∂Û',0);

select no, room, send_nick, recv_nick, send_time, read_time, content, read_chk 
    	from message 
    	where no in (select max(no) from message group by room) and (send_nick = 'jim@gmail.com' or recv_nick= 'jim@gmail.com') 
    	order by no desc;
