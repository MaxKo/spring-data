package kerberos.spring.management.service;

import kerberos.spring.management.dao.UserDao;
import kerberos.spring.management.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired(required = true)
    private UserDao userDao;

    public Iterable<User> getAllUsers(){
        return userDao.findAll();
    }
}
