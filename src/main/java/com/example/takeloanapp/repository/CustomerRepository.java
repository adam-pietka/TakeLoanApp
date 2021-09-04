package com.example.takeloanapp.repository;

import com.example.takeloanapp.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Override
    Customer save(Customer customer);

    @Override
    Optional<Customer> findById(Long id);

    @Override
    List<Customer> findAll();

    void deleteById(Long id);

    Optional<Customer> findByPeselNumber(String peselNumber);

    Optional<Customer> findByIdNumber(String peselNumber);

}