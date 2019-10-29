package kerberos.spring.management.dao;

import kerberos.spring.management.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {


}
