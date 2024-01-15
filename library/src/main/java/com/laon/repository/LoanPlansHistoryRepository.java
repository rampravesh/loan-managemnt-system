package com.laon.repository;

import com.laon.entities.LoanPlansHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LoanPlansHistoryRepository extends JpaRepository<LoanPlansHistory, Integer>, JpaSpecificationExecutor<LoanPlansHistory> {

}