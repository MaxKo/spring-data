package kerberos.spring.management.controller;

import kerberos.spring.management.application.UserAddressManagementApplication;
import kerberos.spring.management.entity.Address;
import kerberos.spring.management.entity.User;
import kerberos.spring.management.service.AddressService;
import kerberos.spring.management.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AddressController {
    final static Logger logger = Logger.getLogger(AddressController.class);

    @Autowired(required = true)
    private AddressService addressService;

    @GetMapping("/address/{addressId}")
    public Optional<Address> getAddressById(@PathVariable final Long addressId) {
        return addressService.getAddressById(addressId);
    }

    @GetMapping("/addresses")
    public List<Address> getAddressesByUserId(@RequestParam(value = "userId") Long userId) {
        logger.debug("getAddressesbyId: " + userId);
        return addressService.getAddressesByUserId(userId);
    }
    /*
    @GetMapping("/addresses")
    public Iterable<Address> getAllUsers() {
        return addressService.getAllAddresses();
    }*/
}
