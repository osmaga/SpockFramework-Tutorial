package com.osmaga.examples.jpa.service.impl;

import com.osmaga.examples.jpa.constants.Country;
import com.osmaga.examples.jpa.constants.Subject;
import com.osmaga.examples.jpa.exceptions.TeacherAddressNotFoundException;
import com.osmaga.examples.jpa.exceptions.TeacherNotFoundException;
import com.osmaga.examples.jpa.model.Address;
import com.osmaga.examples.jpa.model.Teacher;
import com.osmaga.examples.jpa.repository.AddressRepository;
import com.osmaga.examples.jpa.repository.TeacherRepository;
import com.osmaga.examples.jpa.service.TeacherService;
import com.osmaga.examples.jpa.util.TrackingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final AddressRepository addressRepository;

    private final TrackingUtils trackingUtils;

    public Teacher updateTeacherSubjects(long teacherId, Collection<Subject> subjectsTaught) {
        log.info("updating {}", trackingUtils.getCurrentTraceId());
        Teacher teacherToUpdate = teacherRepository.findById(teacherId).orElseThrow(TeacherNotFoundException::new);

        if (subjectsTaught != null) {
            teacherToUpdate.setSubjectsTaught(subjectsTaught);
        }

        return teacherRepository.save(teacherToUpdate);
    }

    @Override
    public List<Address> getAddressesByTeacherCountry(Country country) {
        List<Address> addresses = new ArrayList<>();
        final List<Teacher> teachers = teacherRepository.findByCountry(country);
        for (Teacher teacher : teachers) {
            try {
                addresses.add(addressRepository.findByUserId(teacher.getId()).orElseThrow(TeacherAddressNotFoundException::new));
            } catch (Exception e) {
                log.error("Error finding address for teacher {}", teacher.getFirstName() + " " + teacher.getLastName());
            }
        }
        return addresses;
    }
}
