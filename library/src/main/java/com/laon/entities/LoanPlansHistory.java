package com.laon.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Data
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

    @Column(name = "LoanPlanId")
    private Integer loanPlanId;

}
