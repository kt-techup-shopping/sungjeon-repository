package com.shop.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.common.encoder.PasswordEncoder;
import com.shop.common.exception.CustomException;
import com.shop.common.exception.ErrorCode;
import com.shop.common.support.Preconditions;
import com.shop.domain.user.Status;
import com.shop.jwt.JwtService;
import com.shop.repository.user.UserRepository;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final RefreshTokenService refreshTokenService;

	public Pair<String, String> login(String loginId, String password) {
		var user = userRepository.findByLoginId(loginId)
			.orElseThrow(() -> new CustomException(ErrorCode.FAIL_LOGIN));
		// Bcrypt로 암호화된 정보	-> 단방향 해시암호화 -> 기본 5번 해시알고리즘을 돌림
		// 요청들어온 password를 또 해시알고리즘돌려서 맞는지를 비교
		// 비밀번호가 일치하는지 반드시
		Preconditions.validate(passwordEncoder.matches(password, user.getPassword()), ErrorCode.FAIL_LOGIN);
		Preconditions.validate(user.getStatus() == Status.ACTIVE, ErrorCode.ACCOUNT_INACTIVATED);

		var accessExp = jwtService.getAccessExpiration();
		var refreshExp = jwtService.getRefreshExpiration();

		var accessToken = jwtService.issue(user.getId(), user.getRole(), accessExp);
		var refreshToken = jwtService.issue(user.getId(), user.getRole(), refreshExp);

		long refreshTtlMs = refreshExp.getTime() - System.currentTimeMillis();
		refreshTokenService.save(user.getId(), refreshToken, refreshTtlMs);

		return Pair.of(accessToken, refreshToken);
	}

	public Pair<String, String> refresh(String refreshToken) {
		Long userId;
		try {
			userId = jwtService.parseId(refreshToken);
		} catch (JwtException | IllegalArgumentException e) {
			throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
		}

		var user = userRepository.findByIdOrThrow(userId, ErrorCode.NOT_FOUND_USER);

		var newAccessExp = jwtService.getAccessExpiration();
		var newRefreshExp = jwtService.getRefreshExpiration();

		var newAccessToken = jwtService.issue(user.getId(), user.getRole(), newAccessExp);
		var newRefreshToken = jwtService.issue(user.getId(), user.getRole(), newRefreshExp);

		long newRefreshTtlMs = newRefreshExp.getTime() - System.currentTimeMillis();
		refreshTokenService.save(user.getId(), refreshToken, newRefreshTtlMs);

		return Pair.of(newAccessToken, newRefreshToken);

	}

	public void logout(long id) {
		refreshTokenService.delete(id);
	}
}
