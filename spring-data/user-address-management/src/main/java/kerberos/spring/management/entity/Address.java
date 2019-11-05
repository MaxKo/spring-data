package kerberos.spring.management.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="Addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String address;
    private String country;

    public Address() {}
    public Address(String address, String country) {
        this.address = address;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address [id=" + id + ", address=" + address + ", country=" + country + "]";
    }

}
