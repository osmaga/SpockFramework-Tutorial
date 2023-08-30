package com.osmaga.examples.jpa.service.utils

import com.osmaga.examples.jpa.constants.Career
import com.osmaga.examples.jpa.constants.Subject
import com.osmaga.examples.jpa.model.Address
import com.osmaga.examples.jpa.model.Student
import com.osmaga.examples.jpa.model.Teacher

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
                .discountFee(TestConstants.DEFAULT_DISCOUNT_FEE)
                .discountReason("Early payment")
                .build()
    }

    static Student getLawyerWithoutExpiredDebt() {
        return Student.builder()
                .career(Career.LAWYER)
                .dueDate(LocalDate.now())
                .discountFee(TestConstants.DEFAULT_DISCOUNT_FEE)
                .discountReason("Early payment")
                .build()
    }

    static Student getJournalistWithoutExpiredDebt() {
        return Student.builder()
                .career(Career.JOURNALIST)
                .dueDate(LocalDate.now())
                .discountFee(TestConstants.DEFAULT_DISCOUNT_FEE)
                .discountReason("Early payment")
                .build()
    }

    static Teacher getTeacher() {
        return Teacher.builder()
            .id(TestConstants.DEFAULT_USER_ID)
            .firstName(TestConstants.DEFAULT_FIRST_NAME)
            .lastName(TestConstants.DEFAULT_LAST_NAME)
            .subjectsTaught([ Subject.CRIMINAL_LAW, Subject.HUMANISM, Subject.REPORTING ].asList())
            .build()
    }

    static Teacher getMathematicsTeacher() {
        return Teacher.builder()
                .id(TestConstants.DEFAULT_USER_ID)
                .firstName(TestConstants.DEFAULT_FIRST_NAME)
                .lastName(TestConstants.DEFAULT_LAST_NAME)
                .subjectsTaught([ Subject.MATHEMATICS ].asList())
                .build()
    }

    static List<Teacher> getTeacherList(int size) {
        List<Teacher> teacherList = new ArrayList<>(size)
        for (size; size > 0; size --) {
            teacherList.add(getTeacher())
        }

        return teacherList
    }

    static Optional<Address> getOptionalAddress() {
        return Optional.of(getAddress())
    }

    static Address getAddress() {
        return Address.builder()
                .street(TestConstants.DEFAULT_STREET)
                .number(TestConstants.DEFAULT_NUMBER)
                .build()
    }

    static Optional<Address> getOptionalAddressWithPhone() {
        return Optional.of(getAddressWithPhone())
    }

    static Address getAddressWithPhone() {
        return Address.builder()
                .street(TestConstants.DEFAULT_STREET)
                .number(TestConstants.DEFAULT_NUMBER)
                .phone(TestConstants.DEFAULT_PHONE_NUMBER)
                .build()
    }
}
