
import kerberos.spring.management.dto.AddressDto;
import kerberos.spring.management.dto.UserDto;
import kerberos.spring.management.entity.Address;
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

    //CustomMapper<User, UserDto> customMapper;

    class UserCustomMapper extends CustomMapper<User, UserDto> {

    }

    @Before
    public void before() {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        //customMapper = new UserCustomMapper();
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

    @Test
    public void convertWithEmbeddedEntityToDto() {
        mapperFactory.classMap(User.class, UserDto.class)
                .field("username", "username")
                .field("id", "id")
                .field("addresses", "addresses")
                .register();

        MapperFacade mapper = mapperFactory.getMapperFacade();

        User userEntity = new User(25L, "Claire");
        userEntity.getAddresses().add(new Address("address 1", "US"));

        UserDto userDto = mapper.map(userEntity, UserDto.class);

        assertEquals(userEntity.getUsername(), userDto.getUsername());
        assertEquals(userEntity.getId(), userDto.getId());

        assertEquals(userEntity.getAddresses().size(), userDto.getAddresses().size());
        assertEquals(userEntity.getAddresses().get(0).getAddress(), userDto.getAddresses().get(0).getAddress());

    }

    @Test
    public void convertWithEmbeddedDtoToEntityReverse() {
        mapperFactory.classMap(User.class, UserDto.class)
                .field("username", "username")
                .field("id", "id")
                .field("addresses", "addresses")
                .register();

        MapperFacade mapper = mapperFactory.getMapperFacade();

        UserDto userDto = new UserDto(25L, "Claire");
        userDto.getAddresses().add(new AddressDto("address 1", "US"));

        User userEntity = mapper.map(userDto, User.class);

        assertEquals(userDto.getUsername(), userEntity.getUsername());
        assertEquals(userDto.getId(), userEntity.getId());

        assertEquals(userDto.getAddresses().size(), userEntity.getAddresses().size());
        assertEquals(userDto.getAddresses().get(0).getAddress(), userEntity.getAddresses().get(0).getAddress());
    }


    @Test
    public void convertWithEmbeddedDtoToEntityReverseWithBackReference() {
        mapperFactory.classMap(User.class, UserDto.class)
                .field("username", "username")
                .field("id", "id")
                .field("addresses", "addresses")
                .register();

        MapperFacade mapper = mapperFactory.getMapperFacade();

        UserDto userDto = new UserDto(25L, "Claire");
        userDto.getAddresses().add(new AddressDto("address 1", "US"));
        userDto.getAddresses().get(0).setUser(userDto);


        User userEntity = mapper.map(userDto, User.class);

        assertEquals(userDto.getUsername(), userEntity.getUsername());
        assertEquals(userDto.getId(), userEntity.getId());

        assertEquals(userDto.getAddresses().size(), userEntity.getAddresses().size());
        assertEquals(userDto.getAddresses().get(0).getAddress(), userEntity.getAddresses().get(0).getAddress());

        assertEquals(userDto.getId(), userEntity.getAddresses().get(0).getUser().getId());
    }


    @Test
    public void givenSrcAndDest_whenMapsByObject_thenCorrect() {
        mapperFactory.classMap(User.class, UserDto.class).byDefault();
        MapperFacade mapper = mapperFactory.getMapperFacade();


        UserDto userDto = new UserDto(25L, "Claire");
        userDto.getAddresses().add(new AddressDto("address 1", "US"));
        userDto.getAddresses().get(0).setUser(userDto);


        User userEntity = mapper.map(userDto, User.class);

        assertEquals(userDto.getUsername(), userEntity.getUsername());
        assertEquals(userDto.getId(), userEntity.getId());

        assertEquals(userDto.getAddresses().size(), userEntity.getAddresses().size());
        assertEquals(userDto.getAddresses().get(0).getAddress(), userEntity.getAddresses().get(0).getAddress());

        assertEquals(userDto.getId(), userEntity.getAddresses().get(0).getUser().getId());
    }
}
