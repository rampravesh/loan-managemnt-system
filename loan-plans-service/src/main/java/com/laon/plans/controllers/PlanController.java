package com.laon.plans.controllers;

import com.laon.dto.BaseInterestRatesDTO;
import com.laon.plans.service.PlanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlanController {
    private PlanService planService;

    @GetMapping("/getAllInterestRates")
    List<BaseInterestRatesDTO> getAllInerestRates(){
       return planService.getAllInterestRates();
    }
}
