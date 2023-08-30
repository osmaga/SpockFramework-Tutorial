package com.osmaga.examples.jpa.service.impl

import com.osmaga.examples.jpa.constants.Career
import com.osmaga.examples.jpa.model.Student
import com.osmaga.examples.jpa.repository.StudentRepository
import com.osmaga.examples.jpa.service.utils.TestConstants
import com.osmaga.examples.jpa.service.utils.TestUtils
import com.osmaga.examples.jpa.util.CostsUtil
import com.osmaga.examples.jpa.util.TrackingUtils
import spock.lang.Specification
import spock.lang.Subject

class MockConstraints extends Specification {

    def studentRepository = Mock(StudentRepository)
    def trackingUtils = Mock(TrackingUtils)

    def setupSpec() {
        CostsUtil.init(TestConstants.DEFAULT_ENGINEERING_FEE, TestConstants.DEFAULT_LAWYER_FEE, TestConstants.DEFAULT_JOURNALIST_FEE, TestConstants.DEFAULT_LATE_FEE_PERCENTAGE)
    }

    @Subject
    def subject = new StudentServiceImpl(studentRepository, trackingUtils)

    def "Argument 1 - exact match. Introducing variables"() {
        given:
        def studentId = 1

        when:
        subject.getStudent(studentId)

        then:
        1 * studentRepository.findById(studentId) >> Optional.of(TestUtils.getEngineerWithoutExpiredDebt())
    }

    def "Argument 2 - any argument including null"() {
        when:
        subject.getStudent(123456)

        then:
        1 * studentRepository.findById(_) >> Optional.of(TestUtils.getEngineerWithoutExpiredDebt())

        when:
        subject.getStudent(null)

        then:
        1 * studentRepository.findById(_) >> Optional.of(TestUtils.getEngineerWithoutExpiredDebt())
    }

    def "Argument 3 - object class validation for any-argument constraint"() {
        when:
        subject.getStudent(123456)

        then:
        1 * studentRepository.findById(_ as Long) >> Optional.of(TestUtils.getEngineerWithoutExpiredDebt())

        when:
        subject.addStudent(TestUtils.getEngineerWithoutExpiredDebt())

        then:
        1 * studentRepository.save(_ as Student)
    }

    def "Argument 4 - argument attributes validation"() {
        when:
        subject.updateStudentCareer(1, Career.LAWYER)

        then:
        1 * studentRepository.findById(1) >> Optional.of(TestUtils.getEngineerWithoutExpiredDebt())
        3 * trackingUtils.getCurrentTraceId()

        1 * studentRepository.save({ it.career == Career.LAWYER})
    }

    def "Argument 4 - argument attributes validation"() {
        when:
        subject.updateStudentCareer(1, Career.LAWYER)

        then:
        1 * studentRepository.findById(1) >> Optional.of(TestUtils.getEngineerWithoutExpiredDebt())
        3 * trackingUtils.getCurrentTraceId()

        1 * studentRepository.save({
            it.career == Career.LAWYER
            it.semesterFee > 0
        })
    }

    def "Argument 5 - argument attributes validation declaring variables"() {
        given:
        def studentId = 1
        def newCareer = Career.LAWYER
        def student = TestUtils.getEngineerWithoutExpiredDebt()

        when:
        subject.updateStudentCareer(studentId, newCareer)

        then:
        1 * studentRepository.findById(studentId) >> Optional.of(student)
        3 * trackingUtils.getCurrentTraceId()

        1 * studentRepository.save({
            it.career == newCareer
            it.semesterFee > 0
            it.discountFee == TestConstants.DEFAULT_DISCOUNT_FEE * 0.5
            it.semesterFee == TestConstants.DEFAULT_LAWYER_FEE - it.discountFee
            it.dueDate == student.dueDate
        })
    }

    def "Argument 6 - argument attributes validation using tables - Improving branch coverage"() {
        when:
        subject.updateStudentCareer(studentId, newCareer)

        then:
        1 * studentRepository.findById(studentId) >> Optional.of(student)
        3 * trackingUtils.getCurrentTraceId()

        1 * studentRepository.save({
            it.career == newCareer
            it.semesterFee > 0
            it.dueDate == student.dueDate
        })

        where:
        studentId | newCareer | student
        1 | Career.LAWYER | TestUtils.getEngineerWithoutExpiredDebt()  // Same previous test (Argument 5)
        2 | Career.JOURNALIST | TestUtils.getEngineerWithoutExpiredDebt()
    }

    // table
}
