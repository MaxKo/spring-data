package kerberos.spring.management.mapper;

import kerberos.spring.management.dto.UserDto;
import kerberos.spring.management.entity.User;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserFacadeMapper {

    private MapperFactory mapperFactory;

    public UserFacadeMapper() {
        this.mapperFactory =  new DefaultMapperFactory.Builder().build();
    }

    @Bean
    public MapperFacade  getUserMapperFacade(){
        mapperFactory.classMap(User.class, UserDto.class).byDefault();

        return mapperFactory.getMapperFacade();
    }
}
