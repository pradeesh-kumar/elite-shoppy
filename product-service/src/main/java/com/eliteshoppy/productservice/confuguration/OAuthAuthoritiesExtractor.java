package com.eliteshoppy.productservice.confuguration;

import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class OAuthAuthoritiesExtractor implements AuthoritiesExtractor {

	@Getter
	private String username;
	@Getter
	private String userId;
	@Getter
	private String userType;
	
	@Override
	public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
		this.username = map.get("username").toString();
		this.userId = map.get("id").toString();
		this.userType = map.get("userType").toString();
		return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + map.get("userType").toString());
	}

}