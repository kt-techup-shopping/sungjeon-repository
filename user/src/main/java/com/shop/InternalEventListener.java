package com.shop;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.shop.support.VisitorEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InternalEventListener {
	private final VisitStatService visitStatService;

	@Async
	@EventListener(VisitorEvent.class)
	public void onVisitorEvent(VisitorEvent event) {
		visitStatService.create(
			event.userId(),
			event.ip(),
			event.userAgent()
		);
	}
}
