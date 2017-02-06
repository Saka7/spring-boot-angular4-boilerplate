package com.app.service;

import com.app.BaseTest;
import com.app.entity.User;
import com.app.exception.UserNotFoundException;
import com.app.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.app.util.DummyDataGenerator.getUsers;

public class UserServiceTest extends BaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(timeout = 3000)
    public void findByIdTest() {
        User user = getUsers(1).get(0);
        Mockito.when(userRepository.findOne(Mockito.anyLong())).thenReturn(user);
        User fetchedUser = userService.findById(1L);

        Mockito.verify(userRepository, Mockito.times(1)).findOne(Mockito.anyLong());
        Assert.assertNotNull("Fetched user shouldn't be NULL", fetchedUser);
        Assert.assertEquals("Should return appropriate user", user, fetchedUser);
    }

    @Test(timeout = 5000)
    public void findByNameTest() {
        User user = getUsers(1).get(0);
        Mockito.when(userRepository.findByName(Mockito.anyString())).thenReturn(user);
        User fetchedUser = userService.findByName("Random name");

        Mockito.verify(userRepository, Mockito.times(1)).findByName(Mockito.anyString());
        Assert.assertNotNull("Fetched user shouldn't be NULL", fetchedUser);
        Assert.assertEquals("Should return appropriate user", user, fetchedUser);
    }

    @Test(timeout = 5000)
    public void findByEmailTest() {
        User user = getUsers(1).get(0);
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(user);
        User fetchedUser = userService.findByEmail("random@email.com");

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(Mockito.anyString());
        Assert.assertNotNull("Fetched user shouldn't be NULL", fetchedUser);
        Assert.assertEquals("Should return appropriate user", user, fetchedUser);
    }

    @Test(timeout = 10000)
    public void findAllTest() {
        List<User> users = getUsers(10);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<User> fetchedUsers = userService.findAll();

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Assert.assertNotNull("Fetched users shouldn't be NULL", fetchedUsers);
        Assert.assertEquals("Fetched users should have appropriate size",
                fetchedUsers.size(), users.size());

        fetchedUsers.forEach(user -> {
            Assert.assertNotNull("Any of the fetched users shouldn't be NULL", user);
            Assert.assertTrue("Fetched users should contain each user", users.contains(user));
        });
    }

    @Test(timeout = 5000)
    public void addUserTest() {
        User user = getUsers(1).get(0);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        User fetchedUser = userService.save(user);

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Assert.assertNotNull("Fetched user shouldn't be NULL", fetchedUser);
        Assert.assertEquals("Should return appropriate user", user, fetchedUser);
    }

    @Test(timeout = 5000)
    public void updateUserTest() {
        User user = getUsers(1).get(0);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(userRepository.exists(Mockito.anyLong())).thenReturn(true);
        user.setName("Changed name");
        user.setEmail("changed@email.com");
        User fetchedUser = userService.save(user);

        Mockito.verify(userRepository, Mockito.times(1)).exists(Mockito.anyLong());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Assert.assertNotNull("Fetched user shouldn't be NULL", fetchedUser);
        Assert.assertEquals("Should return appropriate user", user, fetchedUser);
    }

    @Test(timeout=3000)
    public void deleteUserTest() {
        Mockito.when(userRepository.exists(Mockito.anyLong())).thenReturn(true);
        userService.delete(1L);
        Mockito.verify(userRepository, Mockito.times(1)).exists(Mockito.anyLong());
        Mockito.verify(userRepository, Mockito.times(1)).delete(Mockito.anyLong());
    }

    @Test(timeout = 3000, expected = UserNotFoundException.class)
    public void deleteUserWhichDoesNotExistTest() {
        Mockito.when(userRepository.exists(Mockito.anyLong())).thenReturn(false);
        userService.delete(1L);
        Mockito.verify(userRepository, Mockito.times(1)).exists(Mockito.anyLong());
    }

}