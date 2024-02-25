package org.example.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record CustomBirthday(LocalDate birthdate) {

    public long getAge() {
        return ChronoUnit.YEARS.between(birthdate, LocalDate.now());
    }

}
