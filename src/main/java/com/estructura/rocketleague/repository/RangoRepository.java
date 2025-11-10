package com.estructura.rocketleague.repository;

import com.estructura.rocketleague.entity.Rango;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RangoRepository extends JpaRepository<Rango, Long> {
}
