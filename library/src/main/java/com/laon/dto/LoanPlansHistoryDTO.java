package com.laon.dto;


import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class LoanPlansHistoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Date updateDate;

    private String updateReason;

    private Integer loanPlanId;

}
