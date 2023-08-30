package com.osmaga.examples.jpa.service;

import com.osmaga.examples.jpa.constants.Country;
import com.osmaga.examples.jpa.model.Address;

import java.util.List;

public interface TeacherService {

    List<Address> getAddressesByTeacherCountry(Country country);
}
