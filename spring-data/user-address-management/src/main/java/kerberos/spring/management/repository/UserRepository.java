package kerberos.spring.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kerberos.spring.management.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { }
