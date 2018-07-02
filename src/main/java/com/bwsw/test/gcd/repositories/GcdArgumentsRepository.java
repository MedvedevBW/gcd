package com.bwsw.test.gcd.repositories;

import com.bwsw.test.gcd.entities.GcdArgumentsBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GcdArgumentsRepository extends JpaRepository<GcdArgumentsBase, Long> {
    Optional<GcdArgumentsBase> findByGcdArguments(long first, long second);
}