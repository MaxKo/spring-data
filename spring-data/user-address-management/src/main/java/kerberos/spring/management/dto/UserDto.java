package kerberos.spring.management.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
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
