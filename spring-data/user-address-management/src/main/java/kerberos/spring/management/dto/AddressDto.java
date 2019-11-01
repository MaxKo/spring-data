package kerberos.spring.management.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class    AddressDto {

    @JsonBackReference
    private UserDto user;

    private String address;

    private String country;

    public AddressDto() { }

    public AddressDto(UserDto user, String address, String country) {
        this.user = user;
        this.address = address;
        this.country = country;
    }

    public AddressDto(String address, String country) {
        this.address = address;
        this.country = country;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
