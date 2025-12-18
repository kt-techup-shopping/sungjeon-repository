package com.shop.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.shop.domain.user.Role;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {
	private final JwtProperties jwtProperties;

	public String issue(Long id, Role role, Date expiration) {
		// id 값은 jwt의 식별자 같은 개념 -> User의 id값
		// claims -> jwt안에 들어갈 정보를 Map형태로 넣는데 id, 1

		// 2가지의 토큰으로 웹에서는 제어
		// access token -> 짧은 유효기간 : 5분 -> 리프레시토큰으로 새로운 액세스토큰 발급
		// refresh token -> 긴 유효기간 : 12시간 ->만료되면 로그인 다시 해야댐

		return Jwts.builder()
			.issuer("sungjeon")
			.subject(id.toString())
			.claim("role", role.name())
			.issuedAt(new Date())
			.id(id.toString())
			.expiration(expiration)
			.signWith(jwtProperties.getSecret())
			.compact();
	}

	public Date getAccessExpiration() {
		return jwtProperties.getAccessTokenExpiration();
	}

	public Date getRefreshExpiration() {
		return jwtProperties.getRefreshTokenExpiration();
	}

	public boolean validate(String token) {
		try {
			Jwts.parser()
				.verifyWith(jwtProperties.getSecret())
				.build()
				.parseSignedClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public Long parseId(String token) {
		System.out.println(token);
		var id = Jwts.parser()
			.verifyWith(jwtProperties.getSecret())
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getSubject();

		return Long.valueOf(id);
	}

	public Role parseRole(String token) {
		String role = Jwts.parser()
			.verifyWith(jwtProperties.getSecret())
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("role", String.class);

		return Role.valueOf(role);
	}
}
