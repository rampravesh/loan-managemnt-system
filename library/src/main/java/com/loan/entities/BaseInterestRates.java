package com.loan.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects; // For Objects.hash if a more complex hashCode was needed, and good practice for equals.

@Getter
@Setter
@ToString
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
    private BigDecimal baseInterestRate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseInterestRates that = (BaseInterestRates) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
