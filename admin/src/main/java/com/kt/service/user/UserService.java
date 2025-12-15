package com.kt.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.common.exception.ErrorCode;
import com.kt.domain.user.User;
import com.kt.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

// 구현체가 하나 이상 필요로해야 인터페이스가 의미가있다
// 인터페이스 : 구현체 1:1로 다 나눠야하나
// 관례를 지키려고 추상화를 굳이하는 것을 관습적추상화
// 인터페이스로 굳이 나눴을때 불편한 점

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final UserRepository userRepository;

	// Pageable 인터페이스
	public Page<User> search(Pageable pageable, String keyword) {
		return userRepository.findAllByNameContaining(keyword, pageable);
	}

	public User detail(Long id) {
		return userRepository.findByIdOrThrow(id, ErrorCode.NOT_FOUND_USER);
	}

	public void update(Long id, String name, String email, String mobile) {
		var user = userRepository.findByIdOrThrow(id, ErrorCode.NOT_FOUND_USER);

		user.update(name, email, mobile);
	}
}
