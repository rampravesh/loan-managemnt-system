package com.loan.repository;

import com.loan.entities.BaseInterestRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseInterestRatesRepository extends JpaRepository<BaseInterestRates, Integer>, JpaSpecificationExecutor<BaseInterestRates> {

}