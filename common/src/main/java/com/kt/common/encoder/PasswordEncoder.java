package com.kt.common.encoder;

public interface PasswordEncoder {
	String encode(CharSequence rawPassword);

	boolean matches(String rawPassword, String encodedPassword);
}
