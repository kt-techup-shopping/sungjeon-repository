package com.shop.repository.visitstat;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.domain.visitstat.VisitStat;

public interface VisitStatRepository extends JpaRepository<VisitStat, Long> {
}
