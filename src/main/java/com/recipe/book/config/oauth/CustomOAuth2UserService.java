package com.recipe.book.config.oauth;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.recipe.book.config.oauth.dto.SessionUser;
import com.recipe.book.domain.user.User;
import com.recipe.book.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
	private final UserRepository userRepository;
	private final HttpSession httpSession;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
		//현재 로그인 진행 중인 서비스를 구분하는 코드 (구글, 네이버 ...)
		String registrationId = userRequest.
				getClientRegistration().
				getRegistrationId();
		
		//OAuth2 로그인 진행 시 키가되는 필드 값, 구글의 기본 코드는 "sub"라고 함
		String userNameAttributeName = userRequest.
				getClientRegistration().
				getProviderDetails().
				getUserInfoEndpoint().
				getUserNameAttributeName();
		
		OAuthAttributes attributes = OAuthAttributes.
										of(registrationId, userNameAttributeName,
										   oAuth2User.getAttributes());
		User user = seveOrUpdate(attributes);
		
		httpSession.setAttribute("user", user);
		
		return new DefaultOAuth2User(
				Collections.singleton(new
						SimpleGrantedAuthority(user.getRoleKey())),
						attributes.getAttributes(),
						attributes.getNameAttributeKey());
	}
	
	private User seveOrUpdate(OAuthAttributes attributes) {
		System.out.println("saveOrUpdate!!!!");
		System.out.println(attributes.getEmail());
		System.out.println(attributes.getName());
		
		
		User user = userRepository.findByEmail(attributes.getEmail())
								  .map(e -> e.update(attributes.getName(), attributes.getPicture()))
								  .orElse(attributes.toEntitiy());
		return userRepository.save(user);
	}
	
}
