package kerberos.spring.management.controller;

import kerberos.spring.management.config.JsonSerializerBean;
import kerberos.spring.management.controller.exception.UserNotFoundException;
import kerberos.spring.management.dto.AddressDto;
import kerberos.spring.management.entity.Address;
import kerberos.spring.management.service.AddressService;
import kerberos.spring.management.service.UserService;
import ma.glasnost.orika.MapperFacade;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AddressController {
    final static Logger logger = Logger.getLogger(AddressController.class);

    @Autowired(required = true)
    private AddressService addressService;

    @Autowired(required = true)
    private UserService userService;

    @Autowired
    private MapperFacade mapper;

    @Autowired(required = true)
    private JsonSerializerBean js;

    @GetMapping("/address/{addressId}")
    public ResponseEntity<String> getAddressById(@PathVariable final Long addressId) {
        Optional<Address> addressOptional = addressService.getAddressById(addressId);

        AddressDto result = mapper.map(addressOptional.get(), AddressDto.class);

        return ResponseEntity.ok(js.toJson(result));
    }

    @GetMapping("/addresses")
    public ResponseEntity<String> getAddressesByUserId(@RequestParam(value = "userId") Long userId) {
        userService.getUserById(userId).orElseThrow(UserNotFoundException::new);

        List<Address> addresses = addressService.getAddressesByUserId(userId);

        List<AddressDto> result = new ArrayList<AddressDto>();

        mapper.mapAsCollection(addresses, result, AddressDto.class);

        return ResponseEntity.ok(js.toJson(result));
    }
}
