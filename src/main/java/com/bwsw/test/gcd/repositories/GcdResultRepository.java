package com.bwsw.test.gcd.repositories;

import com.bwsw.test.gcd.entities.GcdResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GcdResultRepository extends JpaRepository<GcdResult, Long> {
    GcdResult findByGcdArgumentsId(Long gcdArgumentsId);
}
