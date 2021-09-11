package com.example.takeloanapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerDto {

    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String addressStreet;
    private String addressNumber;
    private String addressPostCode;
    private String addressCity;
    private String peselNumber;
    private String nipNumber;
    private String idType;
    private String idNumber;
    private String mailAddress;
    private boolean isActive;
    private LocalDate registrationDate;
    private LocalDate closedDate;
    private List<Long> loansId = new ArrayList<>();
    private List<Long> loansApplicationId = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDto)) return false;

        CustomerDto that = (CustomerDto) o;

        if (isActive != that.isActive) return false;
        if (!name.equals(that.name)) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (addressStreet != null ? !addressStreet.equals(that.addressStreet) : that.addressStreet != null)
            return false;
        if (addressNumber != null ? !addressNumber.equals(that.addressNumber) : that.addressNumber != null)
            return false;
        if (addressPostCode != null ? !addressPostCode.equals(that.addressPostCode) : that.addressPostCode != null)
            return false;
        if (addressCity != null ? !addressCity.equals(that.addressCity) : that.addressCity != null) return false;
        if (!peselNumber.equals(that.peselNumber)) return false;
        if (idType != null ? !idType.equals(that.idType) : that.idType != null) return false;
        if (idNumber != null ? !idNumber.equals(that.idNumber) : that.idNumber != null) return false;
        if (mailAddress != null ? !mailAddress.equals(that.mailAddress) : that.mailAddress != null) return false;
        return registrationDate != null ? registrationDate.equals(that.registrationDate) : that.registrationDate == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (addressStreet != null ? addressStreet.hashCode() : 0);
        result = 31 * result + (addressNumber != null ? addressNumber.hashCode() : 0);
        result = 31 * result + (addressPostCode != null ? addressPostCode.hashCode() : 0);
        result = 31 * result + (addressCity != null ? addressCity.hashCode() : 0);
        result = 31 * result + peselNumber.hashCode();
        result = 31 * result + (idType != null ? idType.hashCode() : 0);
        result = 31 * result + (idNumber != null ? idNumber.hashCode() : 0);
        result = 31 * result + (mailAddress != null ? mailAddress.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        return result;
    }
}
