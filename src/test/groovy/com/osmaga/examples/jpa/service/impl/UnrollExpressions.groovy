package com.osmaga.examples.jpa.service.impl


import com.osmaga.examples.jpa.repository.StudentRepository
import com.osmaga.examples.jpa.service.utils.TestConstants
import com.osmaga.examples.jpa.service.utils.TestUtils
import com.osmaga.examples.jpa.util.CostsUtil
import com.osmaga.examples.jpa.util.TrackingUtils
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll


class UnrollExpressions extends Specification {

    def setupSpec() {
        CostsUtil.init(TestConstants.DEFAULT_ENGINEERING_FEE, TestConstants.DEFAULT_LAWYER_FEE, TestConstants.DEFAULT_JOURNALIST_FEE, TestConstants.DEFAULT_LATE_FEE_PERCENTAGE)
    }

    def studentRepository = Mock(StudentRepository)
    def trackingUtils = Mock(TrackingUtils)

    @Subject
    def subject = new StudentServiceImpl(studentRepository, trackingUtils)

    // Same old
    def "Should return expected fee using objects in data table"() {
        when:
        def fee = subject.getSemesterFee(student)

        then:
        fee != null
        fee == expectedFee

        where:
        student                                                                               | expectedFee
        TestUtils.getEngineerWithExpiredDebt()                                                | 25200
        TestUtils.getLawyerWithExpiredDebt()                                                | 23100
        TestUtils.getJournalistWithExpiredDebt()                                                | 21000
        TestUtils.getEngineerWithoutExpiredDebt()                                                | 24000
        TestUtils.getLawyerWithoutExpiredDebt()                                                | 22000
        TestUtils.getJournalistWithoutExpiredDebt()                                                | 20000
    }

    // Using expressions
    def "#student.career with due date #student.dueDate should have a fee of #expectedFee"() {
        when:
        def fee = subject.getSemesterFee(student)

        then:
        fee != null
        fee == expectedFee

        where:
        student                                                                               | expectedFee
        TestUtils.getEngineerWithExpiredDebt()                                                | 25200
        TestUtils.getLawyerWithExpiredDebt()                                                | 23100
        TestUtils.getJournalistWithExpiredDebt()                                                | 21000
        TestUtils.getEngineerWithoutExpiredDebt()                                                | 24000
        TestUtils.getLawyerWithoutExpiredDebt()                                                | 22000
        TestUtils.getJournalistWithoutExpiredDebt()                                                | 20000
    }

    // Using expressions without changing test name
    @Unroll("#student.career with due date #student.dueDate should have a fee of #expectedFee")
    def "Should return expected fee using objects in data table - unrolling"() {
        when:
        def fee = subject.getSemesterFee(student)

        then:
        fee != null
        fee == expectedFee

        where:
        student                                                                               | expectedFee
        TestUtils.getEngineerWithExpiredDebt()                                                | 25200
        TestUtils.getLawyerWithExpiredDebt()                                                | 23100
        TestUtils.getJournalistWithExpiredDebt()                                                | 21000
        TestUtils.getEngineerWithoutExpiredDebt()                                                | 24000
        TestUtils.getLawyerWithoutExpiredDebt()                                                | 22000
        TestUtils.getJournalistWithoutExpiredDebt()                                                | 20000
    }

}
