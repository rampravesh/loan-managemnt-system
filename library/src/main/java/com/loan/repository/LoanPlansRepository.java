package com.loan.repository;

import com.loan.entities.LoanPlans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LoanPlansRepository extends JpaRepository<LoanPlans, Integer>, JpaSpecificationExecutor<LoanPlans> {

}