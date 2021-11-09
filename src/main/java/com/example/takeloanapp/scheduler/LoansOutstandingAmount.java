package com.example.takeloanapp.scheduler;

import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.service.LoanService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LoansOutstandingAmount {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoansOutstandingAmount.class);

    private LoanService loanService;

    public void checkOutstandingAmount(){
        LOGGER.info("Starting checkOutstandingAmount......");
        List<Loans> allLoans = loanService.getAllLoans();

        allLoans.stream()
                .filter(loans-> loans.isClosed() == false)
                .filter(loans -> loans.getDayOfInstalmentRepayment() == LocalDate.now().getDayOfMonth())
                .collect(Collectors.toList());

    }
}
