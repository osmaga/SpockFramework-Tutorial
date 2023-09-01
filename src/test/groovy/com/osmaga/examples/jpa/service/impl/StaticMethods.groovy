package com.osmaga.examples.jpa.service.impl

import com.osmaga.examples.jpa.repository.AddressRepository
import com.osmaga.examples.jpa.repository.TeacherRepository
import com.osmaga.examples.jpa.util.CountryUtil
import com.osmaga.examples.jpa.util.TrackingUtils
import org.mockito.Mockito
import spock.lang.Specification

class StaticMethods extends Specification {

    def setupSpec() {
        Mockito.mockStatic(CountryUtil)
    }

    def teacherRepository = Mock(TeacherRepository)
    def addressRepository = Mock(AddressRepository)
    def trackingUtils = Mock(TrackingUtils)

    def subject = new TeacherServiceImpl(teacherRepository, addressRepository, trackingUtils)

    def "Mock static call by using Mockito"() {
        given:
        Mockito.when(CountryUtil.getCountriesList()).thenReturn(["MEXICO"])

        when:
        subject.getTeachersByCountry()

        then:
        1 * teacherRepository.findByCountry(_)
    }

    def "Mock static call by wrapping static call and spying on subject - Not recommended"() {
        given:
        def c = Spy(subject)

        when:
        c.getTeachersByCountry2()

        then:
        1 * c.getCountriesList() >> ["MEXICO"]
        1 * teacherRepository.findByCountry(_)
    }
}
