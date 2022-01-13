package com.example.takeloanapp.service;

import com.example.takeloanapp.controller.LoansController;
import com.example.takeloanapp.controller.LoansInstalmentsController;
import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.dto.LoansDto;
import com.example.takeloanapp.mapper.LoansMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanClosingProcessServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanClosingProcessServices.class);

    @Autowired
    LoansController loansController;
    @Autowired
    LoanArrearsService loanArrearsService;
    @Autowired
    LoansMapper loansMapper;
    @Autowired
    private LoansInstalmentsController loansInstalmentsController;

    public void checkLoansToBeClosed() throws LoanNotFoundException {
        LOGGER.info("Starting checking loans to be closed......");

        List<LoansDto> loansDtoList = loansController.getAllLoans().stream()
                .filter(LoansDto::isActive)
                .filter(record -> record.getEndDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList());
        for ( LoansDto checkedLoan: loansDtoList ) {
            Loans loans = loansMapper.mapToLoan(checkedLoan);
            BigDecimal dueAmount =  loanArrearsService.checkInstallmentsDue(loans);
            BigDecimal sumOfAllPayments = loanArrearsService.sumAmountOfPayedInstallments(loansInstalmentsController.loanRepaymentByLoanId(loans.getId())).getBigDecimalAmount();
            if (sumOfAllPayments.compareTo(dueAmount) == 0){
                LOGGER.info("sum ar all done payment is equal to due amount");
                
            }
            if (sumOfAllPayments.compareTo(dueAmount) == -1){
                LOGGER.info("sum ar all done payment is smaller that due amount.");
            }
            if (sumOfAllPayments.compareTo(dueAmount) == 1){
                LOGGER.info("sum ar all done payment is higher that due amount.");
            }
        }
    }
}
