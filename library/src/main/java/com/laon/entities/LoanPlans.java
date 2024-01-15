package com.laon.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "LoanPlans")
public class LoanPlans implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PlanId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planId;

    @Column(name = "PlanName")
    private String planName;

    @Column(name = "LoanTypeId")
    private Integer loanTypeId;

    @Column(name = "PrincipleAmount")
    private Integer principleAmount;

    @Column(name = "Tenure")
    private Integer tenure;

    @Column(name = "InterestRate")
    private Float interestRate;

    @Column(name = "InterestAmount")
    private Integer interestAmount;

    @Column(name = "TotalPayable")
    private Integer totalPayable;

    @Column(name = "EMI")
    private Float emi;

    @Column(name = "PlanValidity")
    private Date planValidity;

    @Column(name = "PlanAddedOn")
    private Date planAddedOn;

}
