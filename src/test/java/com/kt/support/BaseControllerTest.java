package com.kt.support;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.security.DefaultCurrentUser;
import com.kt.security.TechUpAuthenticationToken;

public abstract class BaseControllerTest {
	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	protected TechUpAuthenticationToken createAuthToken(Long id, String loginId) {
		return new TechUpAuthenticationToken(
			new DefaultCurrentUser(id, loginId),
			Set.of()
		);
	}
}
