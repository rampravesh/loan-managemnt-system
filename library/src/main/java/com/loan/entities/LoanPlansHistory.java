package com.loan.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType; // Keep existing imports that are still needed
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects; // For Objects.hash if a more complex hashCode was needed, and good practice for equals.

@Getter
@Setter
@ToString(exclude = "loanPlans")
@Entity
@Table(name = "LoanPlansHistory")
public class LoanPlansHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "UpdateDate")
    private Date updateDate;

    @Column(name = "UpdateReason")
    private String updateReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LoanPlanId",referencedColumnName = "PlanId")
    private LoanPlans loanPlans;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanPlansHistory that = (LoanPlansHistory) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
