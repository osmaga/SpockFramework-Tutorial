package com.osmaga.examples.jpa.repository;

import com.osmaga.examples.jpa.model.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {

    Optional<Address> findByUserId(long userId);

}
