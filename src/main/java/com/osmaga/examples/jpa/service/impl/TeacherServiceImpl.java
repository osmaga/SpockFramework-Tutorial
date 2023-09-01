package com.osmaga.examples.jpa.service.impl;

import com.osmaga.examples.jpa.constants.Country;
import com.osmaga.examples.jpa.constants.ErrorCode;
import com.osmaga.examples.jpa.constants.Subject;
import com.osmaga.examples.jpa.exceptions.BadRequestException;
import com.osmaga.examples.jpa.exceptions.TeacherAddressNotFoundException;
import com.osmaga.examples.jpa.exceptions.TeacherNotFoundException;
import com.osmaga.examples.jpa.model.Address;
import com.osmaga.examples.jpa.model.Teacher;
import com.osmaga.examples.jpa.repository.AddressRepository;
import com.osmaga.examples.jpa.repository.TeacherRepository;
import com.osmaga.examples.jpa.service.TeacherService;
import com.osmaga.examples.jpa.util.CountryUtil;
import com.osmaga.examples.jpa.util.TrackingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final AddressRepository addressRepository;

    private final TrackingUtils trackingUtils;

    public Teacher updateTeacherSubjects(Long teacherId, Collection<Subject> subjectsTaught) {
        log.info("updating {}", trackingUtils.getCurrentTraceId());

        if (subjectsTaught != null && !subjectsTaught.isEmpty() && teacherId != null) {
            Teacher teacherToUpdate = teacherRepository.findById(teacherId).orElseThrow(TeacherNotFoundException::new);
            teacherToUpdate.setSubjectsTaught(subjectsTaught);
            return teacherRepository.save(teacherToUpdate);
        } else {
            throw new BadRequestException(ErrorCode.SUBJECTS_EMPTY);
        }
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

    @Override
    public Map<Country, List<Teacher>> getTeachersByCountry() {
        Map<Country, List<Teacher>> result = new HashMap<>();
        List<String> countriesList = CountryUtil.getCountriesList();
        List<Country> countries = countriesList
                .stream()
                .map(Country::valueOf)
                .collect(Collectors.toList());

        countries.forEach(country -> {
                List<Teacher> teachers = teacherRepository.findByCountry(country);
                result.put(country, teachers);
        });

        return result;
    }

    public Map<Country, List<Teacher>> getTeachersByCountry2() {
        Map<Country, List<Teacher>> result = new HashMap<>();
        List<String> countriesList = getCountriesList();
        List<Country> countries = countriesList
                .stream()
                .map(Country::valueOf)
                .collect(Collectors.toList());

        countries.forEach(country -> {
            List<Teacher> teachers = teacherRepository.findByCountry(country);
            result.put(country, teachers);
        });

        return result;
    }

    public List<String> getCountriesList() {
        return CountryUtil.getCountriesList();
    }
}
