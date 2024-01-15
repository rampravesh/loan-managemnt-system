package com.loan.repository;

import com.loan.entities.LoanPlansHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LoanPlansHistoryRepository extends JpaRepository<LoanPlansHistory, Integer>, JpaSpecificationExecutor<LoanPlansHistory> {

}