package com.laon.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class BaseInterestRatesDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String loanType;

    private Float baseInterestRate;

}
