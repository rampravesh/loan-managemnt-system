package com.laon.plans.service.impl;

import com.laon.dto.BaseInterestRatesDTO;
import com.laon.entities.BaseInterestRates;
import com.laon.plans.service.PlanService;
import com.laon.repository.BaseInterestRatesRepository;
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
    private ModelMapper modelMapper;

    @Override
    public List<BaseInterestRatesDTO> getAllInterestRates() {
        log.info("getAllInterestRates started");
        List<BaseInterestRates> baseInterestRates = baseInterestRatesRepository.findAll();
        List<BaseInterestRatesDTO> baseInterestRatesDTOS = baseInterestRates.stream().map(item -> modelMapper.map(item,
                BaseInterestRatesDTO.class)).collect(Collectors.toList());
        log.info("getAllInterestRates completed");
        return baseInterestRatesDTOS;
    }
}
