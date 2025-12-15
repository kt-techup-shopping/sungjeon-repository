package com.kt.common.support;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditEntity {
	//시간도 기록했으니까
	//만든사람
	//수정한사람\

	// 그래서 누가 하는 jpa한테 알려줘야함
	// AuditorAware

	@CreatedBy
	protected Long createdBy;

	@LastModifiedBy
	protected Long updatedBy;
}
