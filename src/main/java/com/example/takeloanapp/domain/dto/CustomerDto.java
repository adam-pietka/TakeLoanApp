package com.example.takeloanapp.domain.dto;

import com.example.takeloanapp.domain.Loans;
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
    private String nipNumber;
    private String eMail;
    private boolean isActive;
    private LocalDate registrationDate;
    private LocalDate closedDate;
    private List<Long> loansId = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDto)) return false;

        CustomerDto that = (CustomerDto) o;

        if (isActive != that.isActive) return false;
        if (!id.equals(that.id)) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (eMail != null ? !eMail.equals(that.eMail) : that.eMail != null) return false;
        if (registrationDate != null ? !registrationDate.equals(that.registrationDate) : that.registrationDate != null)
            return false;
        return closedDate != null ? closedDate.equals(that.closedDate) : that.closedDate == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (eMail != null ? eMail.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + (closedDate != null ? closedDate.hashCode() : 0);
        return result;
    }
}
