package com.loan.plans.service;

import com.loan.dto.BaseInterestRatesDTO;
import com.loan.dto.LoanPlansDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PlanService {
    List<BaseInterestRatesDTO> getAllInterestRates();

    List<LoanPlansDTO> getLoanPlans();

    LoanPlansDTO getLoanPlan(Integer loanId);

    LoanPlansDTO update(Integer loanId, LoanPlansDTO loanPlansDTO);

    LoanPlansDTO create(LoanPlansDTO loanPlansDTO);
}
