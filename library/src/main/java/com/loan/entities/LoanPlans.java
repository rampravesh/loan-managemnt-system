package com.loan.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Added for safety, though not strictly needed by the provided hashCode/equals

@Getter
@Setter
@ToString(exclude = "loanPlansHistories")
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
    private BigDecimal principleAmount;

    @Column(name = "Tenure")
    private Integer tenure;

    @Column(name = "InterestRate")
    private BigDecimal interestRate;

    @Column(name = "InterestAmount")
    private BigDecimal interestAmount;

    @Column(name = "TotalPayable")
    private BigDecimal totalPayable;

    @Column(name = "EMI")
    private BigDecimal emi;

    @Column(name = "PlanValidity")
    private Date planValidity;

    @Column(name = "PlanAddedOn")
    private Date planAddedOn;

// TODO: This relationship mapping to BaseInterestRates was commented out. Review if it should be enabled, updated (e.g., CascadeType), or removed in the future.
//    @ManyToOne(
//            fetch = FetchType.LAZY,
//            cascade = {CascadeType.ALL}
//    )
//    @JoinColumn(
//            name = "LoanTypeId",
//            referencedColumnName = "id"
//    )
//    private BaseInterestRates baseInterestRate;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "loanPlans",cascade = {CascadeType.PERSIST})
    private List<LoanPlansHistory> loanPlansHistories = new ArrayList();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanPlans loanPlans = (LoanPlans) o;
        return planId != null && planId.equals(loanPlans.planId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
