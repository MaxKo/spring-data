package kerberos.spring.management.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    private String id;
    private String username;

    private String birthDate;

    @JsonManagedReference
    private List<AddressDto> addresses = new ArrayList<AddressDto>();

    public UserDto() {}

    public UserDto(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<AddressDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDto> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        String result = "UserDto[";

        result += "id:" + id;
        result += ", username:" + username;
        result += ", birthdate: " + ((birthDate != null) ? birthDate : "");
        result += ", addresses: [";
        result += (addresses != null) ? addresses.stream().map(a -> a.toString()).collect( Collectors.joining( "," )) : "";
        result += "]";
        result += "]";

        return result;
    }
}
