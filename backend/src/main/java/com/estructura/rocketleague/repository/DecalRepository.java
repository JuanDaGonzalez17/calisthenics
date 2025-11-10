package com.estructura.rocketleague.repository;

import com.estructura.rocketleague.entity.Decal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecalRepository extends JpaRepository<Decal, Long> {
}
