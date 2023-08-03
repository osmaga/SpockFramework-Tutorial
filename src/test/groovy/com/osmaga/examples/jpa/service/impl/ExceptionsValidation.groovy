package com.osmaga.examples.jpa.service.impl

import com.osmaga.examples.jpa.constants.Career
import com.osmaga.examples.jpa.constants.ErrorCode
import com.osmaga.examples.jpa.exceptions.BadRequestException
import com.osmaga.examples.jpa.model.Student
import com.osmaga.examples.jpa.repository.StudentRepository
import com.osmaga.examples.jpa.service.utils.TestConstants
import com.osmaga.examples.jpa.util.CostsUtil
import com.osmaga.examples.jpa.util.TrackingUtils
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class ExceptionsValidation extends Specification {

    def studentRepository = Mock(StudentRepository)

    def trackingUtils = Mock(TrackingUtils)

    def setupSpec() {
        CostsUtil.init(TestConstants.DEFAULT_ENGINEERING_FEE, TestConstants.DEFAULT_LAWYER_FEE, TestConstants.DEFAULT_JOURNALIST_FEE, TestConstants.DEFAULT_LATE_FEE_PERCENTAGE)
    }

    @Subject
    def subject = new StudentServiceImpl(studentRepository, trackingUtils)

    // Basic test
    def "Adding student should return student info"() {
        given:
        def student = Student.builder()
                .firstName(TestConstants.DEFAULT_FIRST_NAME)
                .lastName(TestConstants.DEFAULT_LAST_NAME)
                .dueDate(LocalDate.now())
                .career(Career.ENGINEERING)
                .discountFee(1000)
                .discountReason("Ethnic minority")
                .build()

        when:
        def result = subject.addStudent(student)

        then:
        studentRepository.save(_) >> new Student()
        result != null
    }


    // Added BadRequestException NOT thrown validation
    def "Adding student shouldn't throw BadRequestException"() {
        given:
        def student = Student.builder()
                .firstName(TestConstants.DEFAULT_FIRST_NAME)
                .lastName(TestConstants.DEFAULT_LAST_NAME)
                .dueDate(LocalDate.now())
                .career(Career.ENGINEERING)
                .discountFee(1000)
                .discountReason("Ethnic minority")
                .build()

        when:
        def result = subject.addStudent(student)

        then:
        studentRepository.save(_) >> new Student()
        result != null

        notThrown(BadRequestException)
    }



    // Checking exception thrown. Useful to boost coverage!
    def "When student receive a discount should have a discount reason"() {
        given: "Student with discount and no reason"
        def student = Student.builder()
                .firstName(TestConstants.DEFAULT_FIRST_NAME)
                .lastName(TestConstants.DEFAULT_LAST_NAME)
                .dueDate(LocalDate.now())
                .career(Career.ENGINEERING)
                .discountFee(1000)
                .build()

        when:
        def result = subject.addStudent(student)

        then:
        def ex = thrown(BadRequestException)
        ex.errorCode == ErrorCode.DISCOUNT_WITHOUT_REASON
    }




    // We also can check no exception is thrown
    def "Adding student shouldn't throw any exception"() {
        given:
        def student = Student.builder()
                .firstName(TestConstants.DEFAULT_FIRST_NAME)
                .lastName(TestConstants.DEFAULT_LAST_NAME)
                .dueDate(LocalDate.now())
                .career(Career.ENGINEERING)
                .discountFee(1000)
                .discountReason("Ethnic minority")
                .build()

        when:
        def result = subject.addStudent(student)

        then:
        noExceptionThrown()
    }
}
