package com.jim.sec01.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jim.sec01.dao.MemberDao;
import com.jim.sec01.dto.JoinDto;
import com.jim.sec01.util.Constant;

public class CustomUserDetailsService implements UserDetailsService {

	@Override //UserDetailsService�� �ִ� �ݹ� �߻� �޼���
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//username�� id�� �ǹ�
		MemberDao mdao = Constant.mdao;
		JoinDto dto = mdao.login(username);
		System.out.println("dto " + dto);
		if(dto ==null) {
			//id�� �ش��ϴ� ���ڵ� ����
			System.out.println("null"); //�α��� ����
			throw new UsernameNotFoundException("No user found with username");
		}
		String pw = dto.getPpw(); //�̰��� DB���� ������ ppw�̹Ƿ� ��ȣȭ�� �н�����
		Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		//access������ role�� �������� ���� �� �־� collectionó��
		//role��(���� ���� ��, ROLE_USER,ROLE_ADMIN,ROLE_MANAGE ��)�� �����ϴ� ����Ʈ ��ü
		//db�� authority�÷��� �ְ� �����ߴٸ� dto.getAuthority()�� ���� ���� new SimpleGrantedAuthority("������")
		roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		UserDetails user = new User(username, pw, roles);
		//spring security���� UserDetails��ü�� �̿��Ͽ� �α��� üũ �� ����
		Constant.username = username;
		return user;
	}

}
