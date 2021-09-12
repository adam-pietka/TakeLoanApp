package com.example.takeloanapp.repository;

import com.example.takeloanapp.domain.LoanCashFlow;
import com.example.takeloanapp.domain.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface LoanCashFlowRepository extends CrudRepository<LoanCashFlow, Long> {

    @Override
    LoanCashFlow save(LoanCashFlow loanCashFlow);

    @Override
    Optional<LoanCashFlow> findById(Long aLong);

    List<LoanCashFlow> findAll();

    List<LoanCashFlow> findAllByLoans(Loans loans);

}
