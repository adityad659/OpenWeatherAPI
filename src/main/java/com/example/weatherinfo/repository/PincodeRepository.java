package com.example.weatherinfo.repository;

import com.example.weatherinfo.model.Pincode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PincodeRepository extends JpaRepository<Pincode, Long> {

	Optional<Pincode> findByPincodeAndCountryCode(String pincode, String countryCode);
}
