package com.shop.repository.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.domain.category.Category;
import com.shop.exception.CustomException;
import com.shop.exception.ErrorCode;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByParentId(Long parentId);

	default Category findByIdOrThrow(Long id, ErrorCode errorCode) {
		return findById(id).orElseThrow(() -> new CustomException(errorCode));
	}

	List<Category> findByParentIsNull();
}