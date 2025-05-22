package com.loan.dto;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Data
public class LoanPlansDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer planId;

    private String planName;

    private Integer loanTypeId;

    private BigDecimal principleAmount;

    private Integer tenure;

    private BigDecimal interestRate;

    private BigDecimal interestAmount;

    private BigDecimal totalPayable;

    private BigDecimal emi;

    private Date planValidity;

    private Date planAddedOn;

}
