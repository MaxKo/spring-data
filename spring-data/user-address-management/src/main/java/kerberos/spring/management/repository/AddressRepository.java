package kerberos.spring.management.repository;


<<<<<<< HEAD
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kerberos.spring.management.entity.Address;

@Repository
=======
import kerberos.spring.management.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(exported = false)
>>>>>>> 9c6ed6c4d06f7eda245225e4a71e04fcbc3028cd
public interface AddressRepository  extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.user.id = ?1")
    List<Address> getAddressesBy(Long userId);
}
