package com.example.takeloanapp.repository;

import com.example.takeloanapp.domain.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface LoanRepository extends CrudRepository<Loans, Long> {

    @Override
    Loans save(Loans loans);

    @Override
    Optional<Loans> findById(Long id);

    @Override
    List<Loans> findAll();

    void deleteById(Loans id);

}