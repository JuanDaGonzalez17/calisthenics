package com.estructura.rocketleague.repository;
import com.estructura.rocketleague.entity.Wheels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WheelsRepository extends JpaRepository<Wheels, Long> {
}

