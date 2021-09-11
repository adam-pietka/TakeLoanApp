package com.example.takeloanapp.mapper;

import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.domain.LoanApplicationsList;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.dto.CustomerDto;
import com.example.takeloanapp.repository.LoanApplicationListRepository;
import com.example.takeloanapp.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerMapper {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanApplicationListRepository loanApplicationListRepository;

    public Customer mapToCustomer(final CustomerDto customerDto){

        return new Customer(
                customerDto.getId(),
                customerDto.getName(),
                customerDto.getSurname(),
                customerDto.getPhoneNumber(),
                customerDto.getAddressStreet(),
                customerDto.getAddressNumber(),
                customerDto.getAddressPostCode(),
                customerDto.getAddressCity(),
                customerDto.getPeselNumber(),
                customerDto.getNipNumber(),
                customerDto.getIdType(),
                customerDto.getIdNumber(),
                customerDto.getMailAddress(),
                customerDto.isActive(),
                customerDto.getRegistrationDate(),
                customerDto.getClosedDate(),
                customerDto.getLoansId().stream()
                            .map(loanRepository::findById).map(Optional::orElseThrow)
                            .collect(Collectors.toList()),
                customerDto.getLoansApplicationId().stream()
                        .map(loanApplicationListRepository::findById).map(Optional::orElseThrow)
                        .collect(Collectors.toList())
        );
    }

    public CustomerDto mapToCustomerDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                customer.getPhoneNumber(),
                customer.getAddressStreet(),
                customer.getAddressNumber(),
                customer.getAddressPostCode(),
                customer.getAddressCity(),
                customer.getPeselNumber(),
                customer.getNipNumber(),
                customer.getIdType(),
                customer.getIdNumber(),
                customer.getMailAddress(),
                customer.isActive(),
                customer.getRegistrationDate(),
                customer.getClosedDate(),
                customer.getLoansList().stream()
                        .map(Loans::getId)
                        .collect(Collectors.toList()),
                customer.getLoanApplicationsLists().stream()
                        .map(LoanApplicationsList::getId)
                        .collect(Collectors.toList())
        );
    }

    public List<CustomerDto> mapToCustomerDtoList(final List<Customer> customerList){
        return customerList.stream()
                .map(this::mapToCustomerDto)
                .collect(Collectors.toList());
    }
}