package com.jim.sec01.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.jim.sec01.dto.JoinDto;
import com.jim.sec01.dto.MessageTO;

public class MemberDao implements MemberIdao{
	@Autowired //�ʵ��Ŀ� ���� Autowired
	private SqlSession sqlSession;
	//filed autowired ������ SqlSessionTemplate��ü��(���� �������̽�)
	
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
		//�α��� ó���ô� id�� �����ϹǷ� 1���� select�Ǿ� selectOne
		return result;
	}
	
	// �޼��� ����Ʈ
		@Override
		public ArrayList<MessageTO> messageList(MessageTO to) {

			String nick = to.getNick();
			// �޼��� ����Ʈ�� ��Ÿ�� �͵� �������� - ���� �ֱ� �޼���, ������� profile ����, ������� nick
			ArrayList<MessageTO> list = (ArrayList)sqlSession.selectList("message_list", to);
			//List selectList(query_id, '����') -> id�� ���� select���� �����ϸ鼭 ����(���������� ����� ����)�� ����. 
			//to��ü���� nick�� �̿��ϱ� ����
			System.out.println(list);
			for (MessageTO mto : list) { //for(Object obj : list)
				mto.setNick(nick);
				// ���� ����ڰ� �ش� room���� ������ �޼����� ������ �����´�.
				int unread = sqlSession.selectOne("count_unread", mto);
				// ���� ����ڰ� �޼����� �ְ�޴� ��� profile�� �����´�.
				String profile = sqlSession.selectOne("get_other_profile",mto);
				// ������ �޼��� ������ mto�� set�Ѵ�.
				mto.setUnread(unread);
				// �޼��� ����� �����ʻ����� mto�� set�Ѵ�.
				mto.setProfile(profile);
				// �޼��� ��� nick�� �����Ѵ�. other_nick
				if (nick.equals(mto.getSend_nick())) {
					mto.setOther_nick(mto.getRecv_nick());
				} else {
					mto.setOther_nick(mto.getSend_nick());
				}
			}

			return list; //�̸޼��� �����׽�Ʈ �غ���..
		}


		// room �� �޼��� ������ �����´�.
		@Override
		public ArrayList<MessageTO> roomContentList(MessageTO to) {
			
			System.out.println("room : " + to.getRoom());
			System.out.println("recv_nick : " + to.getRecv_nick());
			System.out.println("nick : " + to.getNick());
			// �޼��� ������ �����´�
			ArrayList<MessageTO> clist = (ArrayList) sqlSession.selectList("room_content_list", to);

			// �ش� ���� �޼����� �� �޴� ����� ���������� nick�� �޼����� ��� ���� ó���Ѵ�
			sqlSession.update("message_read_chk", to);

			return clist;
		}
		
		// �޼��� list���� �޼����� ������.
		@Override
		public int messageSendInlist(MessageTO to) {
			
			// �޼�������Ʈ���� �������� �����ʿ��� �������� �����ϱ� ����
			if(to.getRoom() == 0) {	// room�� 0�̶�� �����ʿ��� �����Ŵ�
				int exist_chat = sqlSession.selectOne("exist_chat", to);
				// �����ʿ��� �������� �޼��� �����̾�� ù�޼����� �ɰ�츦 �����ϱ� ����
				if(exist_chat == 0) {	// �޼��� ������ ��� 0�̸� message ���̺��� room �ִ��� ���ؼ� to�� set �Ѵ�.
					int max_room = sqlSession.selectOne("max_room", to);
					to.setRoom(max_room+1);
				}else {		// �޼��� ������ �ִٸ� �ش� room ��ȣ�� �����´�.
					//int room = Integer.parseInt(sqlSession.selectOne("select_room", to) );
					int room = sqlSession.selectOne("select_room", to);
					to.setRoom(room);
				}
			}
			
			int flag = sqlSession.insert("messageSendInlist",to);
			return flag;
		}
}
