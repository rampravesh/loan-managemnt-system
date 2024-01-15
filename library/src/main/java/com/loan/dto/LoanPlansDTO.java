package com.loan.dto;


import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class LoanPlansDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer planId;

    private String planName;

    private Integer loanTypeId;

    private Integer principleAmount;

    private Integer tenure;

    private Float interestRate;

    private Integer interestAmount;

    private Integer totalPayable;

    private Float emi;

    private Date planValidity;

    private Date planAddedOn;

}
