package com.laon.repository;

import com.laon.entities.LoanPlans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LoanPlansRepository extends JpaRepository<LoanPlans, Integer>, JpaSpecificationExecutor<LoanPlans> {

}