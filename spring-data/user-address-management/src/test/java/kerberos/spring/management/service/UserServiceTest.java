package kerberos.spring.management.service;

import kerberos.spring.management.entity.User;
import kerberos.spring.management.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    UserService usm = Mockito.mock(UserService.class);
    UserRepository urs = Mockito.spy(UserRepository.class);

    @BeforeEach
    private void setUp() {
        User user = new User(5, "John");

        List<User> data = new ArrayList<>();
        data.add(user);
        Mockito.when(usm.getAllUsers()).thenReturn(data);

        Mockito.when(usm.getUserById(Mockito.any())).thenReturn(java.util.Optional.of(user));

        Mockito.doReturn(new User(10L, "saved")).when(usm).save(user);//.getData()
    }

    @Test
    void getAllUsers() {
        List<User> users =  Lists.newArrayList(usm.getAllUsers());
        System.out.println(users);

        assertEquals(5L, users.get(0).getId());
    }

    @Test
    void getUserById() {
        User user = usm.getUserById(5l).get();

        assertEquals(5L, user.getId());
        assertEquals("John", user.getUsername());
    }

    @Test
    void save() {
        User user = new User(1L, "test");

        User savedUser = usm.save(user);

        Mockito.verify(usm).save(Mockito.any());
    }
}