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

	@Override //UserDetailsService에 있는 콜백 추상 메서드
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//username은 id를 의미
		MemberDao mdao = Constant.mdao;
		JoinDto dto = mdao.login(username);
		System.out.println("dto " + dto);
		if(dto ==null) {
			//id에 해당하는 레코드 없음
			System.out.println("null"); //로그인 실패
			throw new UsernameNotFoundException("No user found with username");
		}
		String pw = dto.getPpw(); //이값은 DB에서 가져온 ppw이므로 암호화된 패스워드
		Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		//access권한인 role은 여러개를 가질 수 있어 collection처리
		//role값(권한 구분 값, ROLE_USER,ROLE_ADMIN,ROLE_MANAGE 등)을 저장하는 리스트 객체
		//db에 authority컬럼을 주고 저장했다면 dto.getAuthority()로 얻은 값을 new SimpleGrantedAuthority("얻은값")
		roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		UserDetails user = new User(username, pw, roles);
		//spring security에서 UserDetails객체를 이용하여 로그인 체크 및 설정
		Constant.username = username;
		return user;
	}

}
