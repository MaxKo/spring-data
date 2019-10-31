package kerberos.spring.management.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Address> addresses = new ArrayList<Address>();

    public User(){};
    public User(String name) {
        this.username = name;
    }

    public User(long id, String username) {
        this.username = username;
        this.id = id;
    }

    @Override
    public String toString() {
        String result = "User [id="  + id +  ", username=" + username;

        result += ", addresses [ "
                    + addresses.stream()
                        .map(a -> String.valueOf(a.toString()))
                        .collect(Collectors.joining(",")) + "]";

        return result  + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
