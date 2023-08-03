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
import spock.lang.Unroll

class MockCardinality extends Specification {

    def studentRepository = Mock(StudentRepository)
    def trackingUtils = Mock(TrackingUtils)

    def setupSpec() {
        CostsUtil.init(TestConstants.DEFAULT_ENGINEERING_FEE, TestConstants.DEFAULT_LAWYER_FEE, TestConstants.DEFAULT_JOURNALIST_FEE, TestConstants.DEFAULT_LATE_FEE_PERCENTAGE)
    }

    @Subject
    def subject = new StudentServiceImpl(studentRepository, trackingUtils)

    def "Cardinality 0 - zero calls"() {
        when:
        subject.addStudent(TestUtils.getEngineerWithoutExpiredDebt())

        then:
        // zero calls
        0 * studentRepository.findById(1)
        0 * trackingUtils.getCurrentTraceId()
    }

    def "Cardinality 1 - one call"() {
        when:
        subject.getStudent(1)

        then:
        // one call
        1 * studentRepository.findById(1) >> Optional.of(TestUtils.getEngineerWithoutExpiredDebt())
        1 * trackingUtils.getCurrentTraceId()

        // no further calls
        0 * _
    }

    // Rewriting 'Cardinality 0'... Declaring '0 * _' is strict, i.e. we have to declare all subject interactions
    def "Cardinality 0 - again"() {
        when:
        subject.addStudent(TestUtils.getEngineerWithoutExpiredDebt())

        then:
        // zero calls
        0 * studentRepository.findById(1)
        0 * trackingUtils.getCurrentTraceId()

        // no further calls
        0 * _

        1 * studentRepository.save(_)  // the order doesn't matter
    }

    def "Cardinality 2 - multiple calls (exact)"() {
        when:
        subject.updateStudentCareer(1, Career.LAWYER)

        then:
        1 * studentRepository.findById(1) >> Optional.of(TestUtils.getEngineerWithoutExpiredDebt())
        1 * studentRepository.save(_)

        // multiple calls (exact)
        3 * trackingUtils.getCurrentTraceId()
    }

    def "Cardinality 3 - multiple calls (range)"() {
        when:
        subject.updateStudentCareer(1, Career.LAWYER)

        then:
        1 * studentRepository.findById(1) >> Optional.of(TestUtils.getEngineerWithoutExpiredDebt())
        1 * studentRepository.save(_)

        // multiple calls (range)
        (1..3) * trackingUtils.getCurrentTraceId()
    }

    def "Cardinality 4 - any number of calls"() {
        when:
        subject.updateStudentCareer(1, Career.LAWYER)

        then:
        1 * studentRepository.findById(1) >> Optional.of(TestUtils.getEngineerWithoutExpiredDebt())
        1 * studentRepository.save(_)

        // any number of calls
        _ * trackingUtils.getCurrentTraceId()
    }

    def "Cardinality 5 - parametrized calls"() {
        when:
        subject.deleteById(1)

        then:
        trackingUtilsCalls * trackingUtils.getCurrentTraceId()
        1 * studentRepository.findById(1) >> Optional.ofNullable(studentFound)
        deleteCalls * studentRepository.delete(_)

        where:
        studentFound                              | deleteCalls | trackingUtilsCalls
        TestUtils.getEngineerWithoutExpiredDebt() | 1           | 2
        null                                      | 0           | 1
    }

}
