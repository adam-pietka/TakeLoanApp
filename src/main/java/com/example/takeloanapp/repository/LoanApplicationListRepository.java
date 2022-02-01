package com.example.takeloanapp.repository;

import com.example.takeloanapp.domain.LoanApplicationsList;
import com.example.takeloanapp.domain.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface LoanApplicationListRepository extends CrudRepository<LoanApplicationsList, Long> {

    @Override
    LoanApplicationsList save(LoanApplicationsList loanApplicationsList);

    @Override
    Optional<LoanApplicationsList> findById(Long id);

    @Override
    List<LoanApplicationsList> findAll();

    void deleteById(Long id);
}