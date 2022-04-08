package com.project.init.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.init.feed.dao.UserDao;
import com.project.init.feed.dto.UserDto;
import com.project.init.util.Constant;

public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		UserDao udao = Constant.udao;
		UserDto dto = udao.login(username);
		System.out.println("dto" + dto);
		if(dto == null) {
			System.out.println("null");
			throw new UsernameNotFoundException("No user found with username");
			//������ ��ť��Ƽ���� ���ܸ� ó���Ͽ� �α��� ���� ó��
		}
		String pw = dto.getUserPw(); // ��ȣȭ�� �н�����
		Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		//role��(���� ���� ��, ROLE_USER,ROLE_ADMIN,ROLE_MANAGE ��)�� �����ϴ� ����Ʈ ��ü
		//db�� authority�÷��� �ְ� �����ߴٸ� dto.getAuthority()�� ���� ���� new SimpleGrantedAuthority("���� ��");
		roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		UserDetails user = new User(username, pw, roles);
		//���� id,pw,roles�� �̿��Ͽ� UserDetails��ü�� ����� ��ȯ����
		
		return user; //spring���� pw��ġ ���� üũ�ϰ� ���� ���� ���� �۾� ����
	}

}