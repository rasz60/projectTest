package com.jim.sec01.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.jim.sec01.dto.JoinDto;
import com.jim.sec01.dto.MessageTO;

public class MemberDao implements MemberIdao{
	@Autowired //필드방식에 의한 Autowired
	private SqlSession sqlSession;
	//filed autowired 실제는 SqlSessionTemplate객체임(상위 인터페이스)
	
	//=====join=====
	@Override
	public String join(JoinDto dto) {
		int res = sqlSession.insert("join", dto);
		System.out.println(res);
		String result = null;
		if(res > 0)
			result = "success";
		else
			result = "failed";
		
		return result;
	}
	
	
	//====log in====
	@Override
	public JoinDto login(String bId) {
		System.out.println(bId);
		JoinDto result = sqlSession.selectOne("login",bId);
		//로그인 처리시는 id는 유일하므로 1개만 select되어 selectOne
		return result;
	}
	
	// 메세지 리스트
		@Override
		public ArrayList<MessageTO> messageList(MessageTO to) {

			String nick = to.getNick();
			// 메세지 리스트에 나타낼 것들 가져오기 - 가장 최근 메세지, 보낸사람 profile 사진, 보낸사람 nick
			ArrayList<MessageTO> list = (ArrayList)sqlSession.selectList("message_list", to);
			//List selectList(query_id, '조건') -> id에 대한 select문을 실행하면서 조건(쿼리문에서 사용할 인자)를 전달. 
			//to객체안의 nick을 이용하기 위해
			System.out.println(list);
			for (MessageTO mto : list) { //for(Object obj : list)
				mto.setNick(nick);
				// 현재 사용자가 해당 room에서 안읽은 메세지의 갯수를 가져온다.
				int unread = sqlSession.selectOne("count_unread", mto);
				// 현재 사용자가 메세지를 주고받는 상대 profile을 가져온다.
				String profile = sqlSession.selectOne("get_other_profile",mto);
				// 안읽은 메세지 갯수를 mto에 set한다.
				mto.setUnread(unread);
				// 메세지 상대의 프로필사진을 mto에 set한다.
				mto.setProfile(profile);
				// 메세지 상대 nick을 세팅한다. other_nick
				if (nick.equals(mto.getSend_nick())) {
					mto.setOther_nick(mto.getRecv_nick());
				} else {
					mto.setOther_nick(mto.getSend_nick());
				}
			}

			return list; //이메서드 단위테스트 해보기..
		}


		// room 별 메세지 내용을 가져온다.
		@Override
		public ArrayList<MessageTO> roomContentList(MessageTO to) {
			
			System.out.println("room : " + to.getRoom());
			System.out.println("recv_nick : " + to.getRecv_nick());
			System.out.println("nick : " + to.getNick());
			// 메세지 내역을 가져온다
			ArrayList<MessageTO> clist = (ArrayList) sqlSession.selectList("room_content_list", to);

			// 해당 방의 메세지들 중 받는 사람이 현재사용자의 nick인 메세지를 모두 읽음 처리한다
			sqlSession.update("message_read_chk", to);

			return clist;
		}
		
		// 메세지 list에서 메세지를 보낸다.
		@Override
		public int messageSendInlist(MessageTO to) {
			
			// 메세지리스트에서 보낸건지 프로필에서 보낸건지 구분하기 위함
			if(to.getRoom() == 0) {	// room이 0이라면 프로필에서 보낸거다
				int exist_chat = sqlSession.selectOne("exist_chat", to);
				// 프로필에서 보낸것중 메세지 내역이없어서 첫메세지가 될경우를 구분하기 위함
				if(exist_chat == 0) {	// 메세지 내역이 없어서 0이면 message 테이블의 room 최댓값을 구해서 to에 set 한다.
					int max_room = sqlSession.selectOne("max_room", to);
					to.setRoom(max_room+1);
				}else {		// 메세지 내역이 있다면 해당 room 번호를 가져온다.
					//int room = Integer.parseInt(sqlSession.selectOne("select_room", to) );
					int room = sqlSession.selectOne("select_room", to);
					to.setRoom(room);
				}
			}
			
			int flag = sqlSession.insert("messageSendInlist",to);
			return flag;
		}
}
