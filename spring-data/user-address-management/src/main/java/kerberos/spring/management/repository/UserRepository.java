package kerberos.spring.management.repository;

import kerberos.spring.management.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends CrudRepository<User, Long> { }
