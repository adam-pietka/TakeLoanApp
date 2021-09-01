package com.example.takeloanapp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "Loan_Repayment_History")
public class LoanRepaymentHistory {

    private Long id;
    private BigDecimal RepaymentAmount;



    @Id
    @GeneratedValue
    @NotNull
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
