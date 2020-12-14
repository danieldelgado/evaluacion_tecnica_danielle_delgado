package com.bcp.reactive.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import java.util.Map;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.bcp.reactive.entity.Role;
import com.bcp.reactive.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UserService userService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		com.bcp.reactive.entity.User dbUser = userService.findByEmail(authentication.getName());
		Map<String, Object> additionalInfo = new HashMap<>();
		Map<String, Object> user = new HashMap<>();
		user.put("id", dbUser.getId());
		user.put("username", dbUser.getEmail());
		user.put("email", dbUser.getEmail());
		user.put("active", dbUser.getId());
		user.put("created", dbUser.getId());
		additionalInfo.put("user", user);
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}
}