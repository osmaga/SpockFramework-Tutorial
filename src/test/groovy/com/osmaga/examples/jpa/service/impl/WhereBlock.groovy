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

import java.time.LocalDate

class WhereBlock extends Specification {

    def setupSpec() {
        CostsUtil.init(TestConstants.DEFAULT_ENGINEERING_FEE, TestConstants.DEFAULT_LAWYER_FEE, TestConstants.DEFAULT_JOURNALIST_FEE, TestConstants.DEFAULT_LATE_FEE_PERCENTAGE)
    }

    def studentRepository = Mock(StudentRepository)

    def trackingUtils = Mock(TrackingUtils)

    @Subject
    def subject = new StudentServiceImpl(studentRepository, trackingUtils)

    // Data tables using 'where' block
    def "Should return expected fee"() {
        when:
        def fee = subject.getSemesterFee(career, dueDate)

        then:
        fee != null
        fee == expectedFee

        where:
        career             | dueDate                      | expectedFee
        Career.ENGINEERING | LocalDate.now().minusDays(1) | 25200
        Career.ENGINEERING | LocalDate.now()              | 24000
    }

    // Using objects and calculations on 'where' block
    def "Should return expected fee using objects in data table"() {
        when:
        def fee = subject.getSemesterFee(student)

        then:
        fee != null
        fee == expectedFee

        where:
        student                                                                               | expectedFee
        Student.builder().career(Career.LAWYER).dueDate(LocalDate.now().minusDays(1)).build() | TestConstants.DEFAULT_LAWYER_FEE + TestConstants.DEFAULT_LAWYER_FEE * TestConstants.DEFAULT_LATE_FEE_PERCENTAGE / 100
        TestUtils.getEngineerWithExpiredDebt()                                                | 25200
    }
}
