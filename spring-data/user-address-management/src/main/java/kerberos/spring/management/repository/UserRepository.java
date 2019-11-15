package kerberos.spring.management.repository;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kerberos.spring.management.entity.User;

@Repository
=======
import kerberos.spring.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
>>>>>>> 9c6ed6c4d06f7eda245225e4a71e04fcbc3028cd
public interface UserRepository extends JpaRepository<User, Long> { }
