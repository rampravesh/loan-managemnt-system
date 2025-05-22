package com.loan.dto;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BaseInterestRatesDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String loanType;

    private BigDecimal baseInterestRate;

}
