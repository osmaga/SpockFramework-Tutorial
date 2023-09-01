package com.osmaga.examples.jpa.service;

import com.osmaga.examples.jpa.constants.Country;
import com.osmaga.examples.jpa.model.Address;
import com.osmaga.examples.jpa.model.Teacher;

import java.util.List;
import java.util.Map;

public interface TeacherService {

    List<Address> getAddressesByTeacherCountry(Country country);

    Map<Country, List<Teacher>> getTeachersByCountry();
}
