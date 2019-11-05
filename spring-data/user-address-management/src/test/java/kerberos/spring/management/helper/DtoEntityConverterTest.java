package kerberos.spring.management.helper;

import kerberos.spring.management.UserAddressManagementApplication;
import kerberos.spring.management.dto.AddressDto;
import kerberos.spring.management.dto.UserDto;
import kerberos.spring.management.entity.Address;
import kerberos.spring.management.entity.User;
import ma.glasnost.orika.MapperFacade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UserAddressManagementApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT   )
public class DtoEntityConverterTest {

   @Autowired
    private MapperFacade mapper;

    @Test
    public void convertEntityToDto() {
        User userEntity = new User(25L, "Claire");
        UserDto userDto = mapper.map(userEntity, UserDto.class);

        assertEquals(userDto.getUsername(), userEntity.getUsername());
        assertEquals(userDto.getId(), userEntity.getId().toString());
    }

    @Test
    public void convertWithEmbeddedEntityToDto() {
        User userEntity = new User(25L, "Claire");
        userEntity.getAddresses().add(new Address("address 1", "US"));

        UserDto userDto = mapper.map(userEntity, UserDto.class);

        assertEquals(userEntity.getUsername(), userDto.getUsername());
        assertEquals(userEntity.getId().toString(), userDto.getId());

        assertEquals(userEntity.getAddresses().size(), userDto.getAddresses().size());
        assertEquals(userEntity.getAddresses().get(0).getAddress(), userDto.getAddresses().get(0).getAddress());

    }

    @Test
    public void convertWithEmbeddedDtoToEntityReverse() {
        UserDto userDto = new UserDto("25", "Claire");
        userDto.getAddresses().add(new AddressDto("address 1", "US"));

        User userEntity = mapper.map(userDto, User.class);

        assertEquals(userDto.getUsername(), userEntity.getUsername());
        assertEquals(userDto.getId(), userEntity.getId().toString());

        assertEquals(userDto.getAddresses().size(), userEntity.getAddresses().size());
        assertEquals(userDto.getAddresses().get(0).getAddress(), userEntity.getAddresses().get(0).getAddress());
    }

    @Test
    public void convertWithEmbeddedDtoToEntityReverseWithBackReference() {
        UserDto userDto = new UserDto("25", "Claire");
        userDto.getAddresses().add(new AddressDto("address 1", "US"));
        userDto.getAddresses().get(0).setUser(userDto);

        User userEntity = mapper.map(userDto, User.class);

        assertEquals(userDto.getUsername(), userEntity.getUsername());
        assertEquals(userDto.getId(), userEntity.getId().toString());

        assertEquals(userDto.getAddresses().size(), userEntity.getAddresses().size());
        assertEquals(userDto.getAddresses().get(0).getAddress(), userEntity.getAddresses().get(0).getAddress());

        assertEquals(userDto.getId(), userEntity.getAddresses().get(0).getUser().getId().toString());
    }


    @Test
    public void givenSrcAndDest_whenMapsByObject_thenCorrect() {
        UserDto userDto = new UserDto("25", "Claire");
        userDto.getAddresses().add(new AddressDto("address 1", "US"));
        userDto.getAddresses().get(0).setUser(userDto);

        User userEntity = mapper.map(userDto, User.class);

        assertEquals(userDto.getUsername(), userEntity.getUsername());
        assertEquals(userDto.getId(), userEntity.getId().toString());

        assertEquals(userDto.getAddresses().size(), userEntity.getAddresses().size());
        assertEquals(userDto.getAddresses().get(0).getAddress(), userEntity.getAddresses().get(0).getAddress());

        assertEquals(userDto.getId(), userEntity.getAddresses().get(0).getUser().getId().toString());
    }

    @Test
    public void customDataMapperFromDto() {
        UserDto userDto = new UserDto("25", "Claire");
        userDto.setBirthDate("2019-11-04 12:34:48");

        User user =  mapper.map(userDto, User.class);

        assertEquals(java.util.Optional.of(25L), java.util.Optional.of(user.getId()));

        assertEquals(2019, user.getBirthDate().getYear());
        assertEquals(11, user.getBirthDate().getMonthValue());

        System.out.println(user);
    }

    @Test
    public void customDataMapperToDto() {
        User user = new User(1, "John");

        user.setBirthDate(LocalDateTime.of(1980, 11, 10, 20, 11, 0));

        UserDto userDto =  mapper.map(user, UserDto.class);

        System.out.println(userDto);
        assertEquals("1980-11-10 20:11:00", userDto.getBirthDate());
    }
}
