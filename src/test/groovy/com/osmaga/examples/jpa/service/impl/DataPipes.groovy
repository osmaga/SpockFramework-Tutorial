package com.osmaga.examples.jpa.service.impl


import com.osmaga.examples.jpa.exceptions.BadRequestException
import com.osmaga.examples.jpa.repository.AddressRepository
import com.osmaga.examples.jpa.repository.TeacherRepository
import com.osmaga.examples.jpa.service.utils.TestConstants
import com.osmaga.examples.jpa.util.TrackingUtils
import spock.lang.Specification

class DataPipes extends Specification {

    def teacherRepository = Mock(TeacherRepository)
    def addressRepository = Mock(AddressRepository)
    def trackingUtils = Mock(TrackingUtils)

    def subject = new TeacherServiceImpl(teacherRepository, addressRepository, trackingUtils)

    def "Using data pipes to test several edge cases and improve branch coverage"() {
        when:
        subject.updateTeacherSubjects(TestConstants.DEFAULT_USER_ID, subjectsTaught)

        then:
        thrown(BadRequestException)

        where:
        subjectsTaught << [ null, Collections.emptyList() ]
    }

    def "Also we can define multiple variable values in data pipes - Would be more readable using a data table"() {
        when:
        subject.updateTeacherSubjects(userId, subjectsTaught)

        then:
        thrown(BadRequestException)

        where:
        userId << [ null, null, TestConstants.DEFAULT_USER_ID, TestConstants.DEFAULT_USER_ID]
        subjectsTaught << [ null, Collections.emptyList(), null, Collections.emptyList() ]
    }
}
