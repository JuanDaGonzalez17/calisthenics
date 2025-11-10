package com.estructura.rocketleague.repository;

import com.estructura.rocketleague.entity.Boost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoostRepository extends JpaRepository<Boost, Long> {
}
