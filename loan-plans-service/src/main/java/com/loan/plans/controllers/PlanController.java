package com.loan.plans.controllers;

import com.loan.dto.BaseInterestRatesDTO;
import com.loan.dto.LoanPlansDTO;
import com.loan.plans.constants.ControllerConstants;
import com.loan.plans.service.PlanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ControllerConstants.BASE_URL)
public class PlanController {
    private PlanService planService;

    @GetMapping("/check")
    public String check(){
        return "hello";
    }

    @RequestMapping(value = ControllerConstants.INTEREST_RATES,method = RequestMethod.GET)
    public List<BaseInterestRatesDTO> getAllInerestRates(){
       return planService.getAllInterestRates();
    }

    @RequestMapping(value = ControllerConstants.LOAN_PLANS,method = RequestMethod.GET)
    public List<LoanPlansDTO> getLoanPlans(){
        return planService.getLoanPlans();
    }

    @RequestMapping(value = ControllerConstants.GET_LOAN_PLANS_BY_ID,method = RequestMethod.GET)
    public LoanPlansDTO getLoanPlans(@PathVariable(name="loanId") Integer loanId){
        return planService.getLoanPlan(loanId);
    }

    @RequestMapping(value = ControllerConstants.GET_LOAN_PLANS_BY_ID,method = RequestMethod.PUT)
    public LoanPlansDTO updateLoanPlan(@PathVariable(name="loanId") Integer loanId,
     @Valid @RequestBody(required = true) LoanPlansDTO loanPlansDTO){
        return planService.update(loanId,loanPlansDTO);
    }
    @RequestMapping(value = ControllerConstants.LOAN_PLANS,method = RequestMethod.POST)
    public LoanPlansDTO updateLoanPlan(@Valid @RequestBody(required = true) LoanPlansDTO loanPlansDTO){
        return planService.create(loanPlansDTO);
    }
}
