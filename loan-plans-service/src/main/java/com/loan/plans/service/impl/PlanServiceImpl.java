package com.loan.plans.service.impl;

import com.loan.dto.BaseInterestRatesDTO;
import com.loan.dto.LoanPlansDTO;
import com.loan.entities.BaseInterestRates;
import com.loan.entities.LoanPlans;
import com.loan.plans.service.PlanService;
import com.loan.repository.BaseInterestRatesRepository;
import com.loan.repository.LoanPlansRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlanServiceImpl implements PlanService {
    @Autowired
    private BaseInterestRatesRepository baseInterestRatesRepository;

    @Autowired
    private LoanPlansRepository loanPlansRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<BaseInterestRatesDTO> getAllInterestRates() {
//        log.info("getAllInterestRates started");
        List<BaseInterestRates> baseInterestRates = baseInterestRatesRepository.findAll();
        List<BaseInterestRatesDTO> baseInterestRatesDTOS = baseInterestRates.stream().map(item -> modelMapper.map(item,
                BaseInterestRatesDTO.class)).collect(Collectors.toList());
//        log.info("getAllInterestRates completed");
        return baseInterestRatesDTOS;
    }

    @Override
    public List<LoanPlansDTO> getLoanPlans() {
        List<LoanPlans> loanPlans = loanPlansRepository.findAll();
        List<LoanPlansDTO> loanPlansDTOS = loanPlans.stream().map(item -> modelMapper.map(item,
                LoanPlansDTO.class)).collect(Collectors.toList());
        return loanPlansDTOS;
    }

    @Override
    public LoanPlansDTO getLoanPlan(Integer loanId) {
        LoanPlansDTO loanPlansDTO = null;
        LoanPlans loanPlan = loanPlansRepository.findById(loanId).get();
        loanPlansDTO = modelMapper.map(loanPlan, LoanPlansDTO.class);
        return loanPlansDTO;
    }

    @Override
    public LoanPlansDTO update(Integer loanId, LoanPlansDTO loanPlansDTO) {
        LoanPlansDTO updatedPlanDto = null;
        LoanPlans loanPlan = loanPlansRepository.findById(loanId).get();
        //TODO update loanPlan other feild
        loanPlan.setPlanName(loanPlansDTO.getPlanName());
        updatedPlanDto = modelMapper.map(loanPlan, LoanPlansDTO.class);
        return loanPlansDTO;
    }

    @Override
    public LoanPlansDTO create(LoanPlansDTO loanPlansDTO) {
        LoanPlansDTO updatedPlanDto = null;
        LoanPlans loanPlan = new LoanPlans();
        //TODO update loanPlan other feild
        loanPlan = modelMapper.map(loanPlansDTO, LoanPlans.class);
        loanPlansRepository.save(loanPlan);
        updatedPlanDto = modelMapper.map(loanPlan, LoanPlansDTO.class);
        return updatedPlanDto;
    }
}
