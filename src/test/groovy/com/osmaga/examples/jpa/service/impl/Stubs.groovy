package com.osmaga.examples.jpa.service.impl


import com.osmaga.examples.jpa.constants.Country
import com.osmaga.examples.jpa.constants.Subject
import com.osmaga.examples.jpa.repository.AddressRepository
import com.osmaga.examples.jpa.repository.TeacherRepository
import com.osmaga.examples.jpa.service.utils.TestUtils
import com.osmaga.examples.jpa.util.TrackingUtils
import spock.lang.Specification

/**
Making collaborators respond to method calls in a certain way
 **/
class Stubs extends Specification {

    def teacherRepository = Mock(TeacherRepository)
    def addressRepository = Mock(AddressRepository)
    def trackingUtils = Mock(TrackingUtils)

    def subject = new TeacherServiceImpl(teacherRepository, addressRepository, trackingUtils)


    def "Respond to method calls with fixed values. Here we aren't checking cardinality"() {
        given:
        def currentTeacher = TestUtils.getTeacher()
        def newSubjectList = [ Subject.CRIMINAL_LAW, Subject.HUMANISM ].asList()

        when:
        subject.updateTeacherSubjects(1, newSubjectList)

        then:
        teacherRepository.findById(1) >> Optional.of(currentTeacher)
        teacherRepository.save(_) >> TestUtils.getMathematicsTeacher()
        trackingUtils.getCurrentTraceId() >> "12345678"
    }

    def "Respond with a sequence of values"() {
        given:
        def country = Country.COLOMBIA

        when:
        def result = subject.getAddressesByTeacherCountry(country)

        then:
        1 * teacherRepository.findByCountry(country) >> TestUtils.getTeacherList(3)
        3 * addressRepository.findByUserId(_) >>> [ TestUtils.getOptionalAddress(), TestUtils.getOptionalAddressWithPhone(), TestUtils.getOptionalAddress() ]

        result.size() == 3
    }

    def "Respond with closures"() {
        given:
        def country = Country.COLOMBIA

        when:
        def result = subject.getAddressesByTeacherCountry(country)

        then:
        1 * teacherRepository.findByCountry(country) >> TestUtils.getTeacherList(1)
        1 * addressRepository.findByUserId(_) >> { throw new SocketTimeoutException() }

        result.size() == 0
    }

    def "Respond with closures 2"() {
        given:
        def country = Country.COLOMBIA

        when:
        def result = subject.getAddressesByTeacherCountry(country)

        then:
        1 * teacherRepository.findByCountry(country) >> TestUtils.getTeacherList(1)
        1 * addressRepository.findByUserId(_) >> { def address = TestUtils.getAddress(); return Optional.of(address) }

        result.size() == 1
    }

    def "All kind of responses chained"() {
        given:
        def country = Country.COLOMBIA

        when:
        def result = subject.getAddressesByTeacherCountry(country)

        then:
        1 * teacherRepository.findByCountry(country) >> TestUtils.getTeacherList(5)
        5 * addressRepository.findByUserId(_) >>> [ TestUtils.getOptionalAddress(), TestUtils.getOptionalAddressWithPhone() ] >> { throw new SocketTimeoutException() } >> TestUtils.getOptionalAddress()
        // We could add one more response to improve branch coverage (TeacherAddressNotFoundException)

        result.size() == 4
    }
}
