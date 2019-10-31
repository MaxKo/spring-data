
import kerberos.spring.management.dto.UserDto;
import kerberos.spring.management.entity.User;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DtoEntityConverterTest {
    MapperFactory mapperFactory;

    CustomMapper<User, UserDto> customMapper;

    class UserCustomMapper extends CustomMapper<User, UserDto> {

    }

    @Before
    public void before() {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        customMapper = new UserCustomMapper();
    }

    @Test
    public void convertEntityToDto() {
        mapperFactory.classMap(User.class, UserDto.class)
                .field("username", "username")
                .field("id", "id")
                .register();

        MapperFacade mapper = mapperFactory.getMapperFacade();

        User userEntity = new User(25L, "Claire");
        UserDto userDto = mapper.map(userEntity, UserDto.class);

        assertEquals(userDto.getUsername(), userEntity.getUsername());
        assertEquals(userDto.getId(), userEntity.getId());
    }

}
