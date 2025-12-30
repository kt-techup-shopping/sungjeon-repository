package com.shop.integration.eventlistener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.shop.integration.slack.NotifyApi;
import com.shop.support.Message;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationListener {
	private final NotifyApi notifyApi;

	@EventListener(Message.class)
	public void onMessage(Message message) {
		notifyApi.notify(message.message());
	}
}
