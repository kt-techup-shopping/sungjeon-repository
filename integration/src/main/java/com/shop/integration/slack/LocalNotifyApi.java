package com.shop.integration.slack;

import org.springframework.stereotype.Component;

import com.shop.profile.LocalProfile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@LocalProfile
public class LocalNotifyApi implements NotifyApi {
	@Override
	public void notify(String message) {
		log.info(message);
	}
}
