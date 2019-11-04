package kerberos.spring.management.service;

import kerberos.spring.management.entity.Address;
import kerberos.spring.management.repository.AddressRepository;
import kerberos.spring.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired(required = true)
    private AddressRepository addressRepository;

    @Autowired(required = true)
    private UserRepository userRepository;

    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.getAddressesBy(userId);
    }

    public Optional<Address> getAddressById(Long addressId) {
        return addressRepository.findById(addressId);
    }

    public Iterable<Address> getAllAddresses() {
        return addressRepository.findAll();
    }
}
