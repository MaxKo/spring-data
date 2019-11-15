package kerberos.spring.management.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kerberos.spring.management.entity.Address;

@Repository
public interface AddressRepository  extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.user.id = ?1")
    List<Address> getAddressesBy(Long userId);
}
