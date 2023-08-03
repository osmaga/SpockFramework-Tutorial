package com.osmaga.examples.jpa.service.utils

import com.osmaga.examples.jpa.constants.Career
import com.osmaga.examples.jpa.model.Student

import java.time.LocalDate

class TestUtils {

    static Student getEngineerWithExpiredDebt() {
        return Student.builder()
                .career(Career.ENGINEERING)
                .dueDate(LocalDate.now() - 1)
                .build()
    }

    static Student getLawyerWithExpiredDebt() {
        return Student.builder()
                .career(Career.LAWYER)
                .dueDate(LocalDate.now() - 1)
                .build()
    }

    static Student getJournalistWithExpiredDebt() {
        return Student.builder()
                .career(Career.JOURNALIST)
                .dueDate(LocalDate.now() - 1)
                .build()
    }

    static Student getEngineerWithoutExpiredDebt() {
        return Student.builder()
                .career(Career.ENGINEERING)
                .dueDate(LocalDate.now())
                .discountFee(1000)
                .discountReason("Early payment")
                .build()
    }

    static Student getLawyerWithoutExpiredDebt() {
        return Student.builder()
                .career(Career.LAWYER)
                .dueDate(LocalDate.now())
                .build()
    }

    static Student getJournalistWithoutExpiredDebt() {
        return Student.builder()
                .career(Career.JOURNALIST)
                .dueDate(LocalDate.now())
                .build()
    }
}
