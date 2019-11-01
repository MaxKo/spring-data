package kerberos.spring.management.repository;


import kerberos.spring.management.entity.Address;
import kerberos.spring.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository  extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.user.id = ?1")
    List<Address> getAddressesBy(Long userId);

    @Query("SELECT a FROM Address a WHERE a.user = ?1")
    List<Address> getAddressesBy(User user);
}
