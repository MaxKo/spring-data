package kerberos.spring.management.helper;

import kerberos.spring.management.dto.UserDto;
import kerberos.spring.management.entity.User;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
class AddressFacadeMapper {
    private MapperFactory mapperFactory;
    CustomMapper customMapper = new UserCustomMapper();

    public AddressFacadeMapper() {
         this.mapperFactory =  new DefaultMapperFactory.Builder().build();

        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter("dateType", new DateConverter());
    }

    @Bean
    public MapperFacade  getUserMapperFacade() {
        mapperFactory.classMap(User.class, UserDto.class)
                .fieldMap("birthDate", "birthDate").converter("dateType").add()
                .byDefault()
                .customize(customMapper)
                .register();

        return mapperFactory.getMapperFacade();
    }
}
