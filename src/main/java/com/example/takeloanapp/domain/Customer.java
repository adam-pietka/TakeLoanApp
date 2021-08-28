package com.example.takeloanapp.domain;


import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String addressStreet;
    private String addressNumber;
    private String addressPostCode;
    private String addressCity;
    private String nipNumber;
    private String eMail;
    private boolean isActive;
    private LocalDate registrationDate;
    private LocalDate closedDate;
    private List<Loans> loansList = new ArrayList<>(); //ManyToMany

    public Customer(){
    }

    public Customer(String name, String surname, String phoneNumber, String addressStreet, String addressNumber, String addressPostCode, String addressCity, String eMail) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.addressStreet = addressStreet;
        this.addressNumber = addressNumber;
        this.addressPostCode = addressPostCode;
        this.addressCity = addressCity;
        this.eMail = eMail;
    }

    public Customer(String name, String surname, String phoneNumber, String addressStreet, String addressNumber, String addressPostCode, String addressCity, String nipNumber, String eMail) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.addressStreet = addressStreet;
        this.addressNumber = addressNumber;
        this.addressPostCode = addressPostCode;
        this.addressCity = addressCity;
        this.nipNumber = nipNumber;
        this.eMail = eMail;
    }

    public Customer(Long id, String name, String surname, String phoneNumber, String addressStreet, String addressNumber, String addressPostCode, String addressCity, String nipNumber, String eMail, boolean isActive, LocalDate registrationDate, LocalDate closedDate, List<Loans> loansList) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.addressStreet = addressStreet;
        this.addressNumber = addressNumber;
        this.addressPostCode = addressPostCode;
        this.addressCity = addressCity;
        this.nipNumber = nipNumber;
        this.eMail = eMail;
        this.isActive = isActive;
        this.registrationDate = registrationDate;
        this.closedDate = closedDate;
        this.loansList = loansList;
    }

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "CUSTOMER_ID", unique = true)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public String getAddressPostCode() {
        return addressPostCode;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public String getNipNumber() {
        return nipNumber;
    }

    public String getEmail() {
        return eMail;
    }

    public boolean isActive() {
        return isActive;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public LocalDate getClosedDate() {
        return closedDate;
    }

    @OneToMany(
            targetEntity = Loans.class,
            mappedBy = "id",
            fetch = FetchType.LAZY
    )
    public List<Loans> getLoansList() {
        return loansList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public void setAddressPostCode(String addressPostCode) {
        this.addressPostCode = addressPostCode;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public void setNipNumber(String nipNumber) {
        this.nipNumber = nipNumber;
    }

    public void setEmail(String eMail) {
        this.eMail = eMail;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setClosedDate(LocalDate closedDate) {
        this.closedDate = closedDate;
    }

    public void setLoansList(List<Loans> loansList) {
        this.loansList = loansList;
    }
}