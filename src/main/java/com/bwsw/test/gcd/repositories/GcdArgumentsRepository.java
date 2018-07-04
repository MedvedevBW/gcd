package com.bwsw.test.gcd.repositories;

import com.bwsw.test.gcd.entities.GcdArgumentsBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GcdArgumentsRepository extends JpaRepository<GcdArgumentsBase, Long> {
    List<GcdArgumentsBase> findByFirstAndSecond(long first, long second);
}