package com.laon.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "BaseInterestRates")
public class BaseInterestRates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "LoanType")
    private String loanType;

    @Column(name = "BaseInterestRate")
    private Float baseInterestRate;

}
