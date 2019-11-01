package kerberos.spring.management.mapper;

import kerberos.spring.management.dto.AddressDto;
import kerberos.spring.management.dto.UserDto;
import kerberos.spring.management.entity.Address;
import kerberos.spring.management.entity.User;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressFacadeMapper {

    private MapperFactory mapperFactory;

    public AddressFacadeMapper() {
        this.mapperFactory =  new DefaultMapperFactory.Builder().build();
    }

    @Bean("user")
    public MapperFacade  getUserMapperFacade(){
        mapperFactory.classMap(Address.class, AddressDto.class).byDefault();

        return mapperFactory.getMapperFacade();
    }

    @Bean("address")
    public MapperFacade  getUserAddressFacade(){
        mapperFactory.classMap(Address.class, AddressDto.class).byDefault();

        return mapperFactory.getMapperFacade();
    }
}
